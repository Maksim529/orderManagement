
package vip.xiaonuo.main.modular.tbcustomerorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.api.auth.entity.SysUser;
import vip.xiaonuo.common.consts.SymbolConstant;
import vip.xiaonuo.common.enums.CommonStatusEnum;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.config.FtpSupportConfig;
import vip.xiaonuo.main.modular.sysConfig.service.ProductTypeService;
import vip.xiaonuo.main.modular.sysConfig.service.TbFactoryMerchandiserService;
import vip.xiaonuo.main.modular.tbcustomeraccount.entity.TbCustomerAccount;
import vip.xiaonuo.main.modular.tbcustomeraccount.service.TbCustomerAccountService;
import vip.xiaonuo.main.modular.tbcustomerask.entity.TbCustomerAsk;
import vip.xiaonuo.main.modular.tbcustomerask.service.TbCustomerAskService;
import vip.xiaonuo.main.modular.tbcustomerask.util.UploadFileUtil;
import vip.xiaonuo.main.modular.tbcustomerorder.dto.TbCustomerOrderDTO;
import vip.xiaonuo.main.modular.tbcustomerorder.dto.TbCustomerOrderDetailDTO;
import vip.xiaonuo.main.modular.tbcustomerorder.dto.TbOrderDistributeDTO;
import vip.xiaonuo.main.modular.tbcustomerorder.dto.TbOrderDistributeDetailDTO;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrder;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrderDetail;
import vip.xiaonuo.main.modular.tbcustomerorder.enums.TbCustomerOrderExceptionEnum;
import vip.xiaonuo.main.modular.tbcustomerorder.mapper.TbCustomerOrderMapper;
import vip.xiaonuo.main.modular.tbcustomerorder.param.TbCustomerOrderDetailParam;
import vip.xiaonuo.main.modular.tbcustomerorder.param.TbCustomerOrderParam;
import vip.xiaonuo.main.modular.tbcustomerorder.service.TbCustomerOrderDetailService;
import vip.xiaonuo.main.modular.tbcustomerorder.service.TbCustomerOrderService;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.CustomerOrderExportVO;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.CustomerOrderRollInfoVO;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.TbCustomerOrderContractVO;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.TbCustomerOrderVO;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.entity.TbFactoryOrderDetail;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.service.TbFactoryOrderDetailService;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.vo.WorkOrderDetailVO;
import vip.xiaonuo.main.modular.tborderevaluation.entity.TbOrderEvaluation;
import vip.xiaonuo.main.modular.tborderevaluation.service.TbOrderEvaluationService;
import vip.xiaonuo.main.modular.tbworkerorderspeed.entity.TbWorkerorderSpeed;
import vip.xiaonuo.main.modular.tbworkerorderspeed.service.TbWorkerorderSpeedService;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkOrder;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkOrderService;
import vip.xiaonuo.main.modular.tbworkorder.vo.TbWorkOrderVO;
import vip.xiaonuo.main.modular.user.service.SysUserService;
import vip.xiaonuo.sys.modular.consts.service.SysConfigService;
import vip.xiaonuo.sys.modular.dict.service.SysDictDataService;
import vip.xiaonuo.sys.modular.emp.service.SysEmpService;
import vip.xiaonuo.sys.modular.file.entity.SysFileInfo;
import vip.xiaonuo.sys.modular.file.param.SysFileInfoParam;
import vip.xiaonuo.sys.modular.file.service.SysFileInfoService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 生产订单service接口实现类
 *
 * @author 邾茂星
 * @date 2022-01-12 08:59:51
 */
@Service
@Slf4j
public class TbCustomerOrderServiceImpl extends ServiceImpl<TbCustomerOrderMapper, TbCustomerOrder> implements TbCustomerOrderService {
    @Autowired
    private TbCustomerOrderDetailService tbCustomerOrderDetailService;
    @Autowired
    private FtpSupportConfig ftpSupportConfig;
    @Autowired
    private SysFileInfoService sysFileInfoServiceImpl;
    @Autowired
    private SysDictDataService sysDictDataService;
    @Autowired
    private TbFactoryOrderDetailService tbFactoryOrderDetailService;
    @Autowired
    private TbWorkOrderService tbWorkOrderService;
    @Autowired
    private TbWorkerorderSpeedService tbWorkerorderSpeedService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TbCustomerAskService tbCustomerAskService;
    @Autowired
    private SysUserService sysUserService;
    @Resource
    private SysFileInfoService sysFileInfoService;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private SysEmpService sysEmpService;
    @Autowired
    private TbCustomerAccountService tbCustomerAccountService;
    @Autowired
    private TbOrderEvaluationService tbOrderEvaluationService;
    @Autowired
    private TbFactoryMerchandiserService tbFactoryMerchandiserService;

    @Value("${dingTalk.sjRobotUrl}")
    private String sjRobotUrl;

    @Value("${dingTalk.pcManageUrl}")
    private String pcManageUrl;

    @Value("${dingTalk.gdAppUrl}")
    private String gdAppUrl;

    /**
     * 分页查询订单和工单列表
     *
     * @param tbCustomerOrderParam
     * @return
     * @author wan
     * @date 2022-1-15 13:40:40
     */
    @Override
    public PageResult<TbCustomerOrderVO> findOrderPage(TbCustomerOrderParam tbCustomerOrderParam) {
        QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerOrderParam)) {
            // 根据所属客户 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerId())) {
                queryWrapper.eq("a.customer_id", tbCustomerOrderParam.getCustomerId());
            }
            // 根据状态 查询
            queryWrapper.gt("a.status", 1).ne("a.status", 6);
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getStatus())) {
                queryWrapper.eq("a.status", tbCustomerOrderParam.getStatus());
            }
            // 根据品类 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCategory())) {
                queryWrapper.eq("a.category", tbCustomerOrderParam.getCategory());
            }
            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getOrderNo())) {
                queryWrapper.like("a.order_no", tbCustomerOrderParam.getOrderNo());
            }
            // 根据款号 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerSku())) {
                queryWrapper.like("a.customer_sku", tbCustomerOrderParam.getCustomerSku());
            }
            // 根据生产工单号
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getWorkOrderNo())) {
                queryWrapper.like("d.orderNoList", tbCustomerOrderParam.getWorkOrderNo());
            }
        }
        queryWrapper.orderByDesc("a.id");
        Page<TbCustomerOrderVO> page = baseMapper.findPage(PageFactory.defaultPage(), queryWrapper);
        if (page != null) {
            List<TbCustomerOrderVO> records = page.getRecords();
            if (CollUtil.isNotEmpty(records)) {
                records.forEach(order -> {
                    Long orderId = order.getId();
                    //查询订单详情信息
                    List<TbCustomerOrderDetail> detailList = getOrderDataByOrderId(orderId);
                    order.setOrderDetailList(detailList);
                    //查询工单信息
                    List<TbWorkOrderVO> workOrderVOS = tbWorkOrderService.getWorkOrderDataByOrderId(orderId);
                    workOrderVOS.forEach(tbWorkOrderVO -> {
                        LambdaQueryWrapper<TbWorkerorderSpeed> speedQuery = new LambdaQueryWrapper<>();
                        speedQuery.eq(TbWorkerorderSpeed::getWorkOrderId, tbWorkOrderVO.getId());
                        TbWorkerorderSpeed tbWorkerorderSpeed = tbWorkerorderSpeedService.getOne(speedQuery);
                        tbWorkOrderVO.setWorkerorderSpeed(tbWorkerorderSpeed);
                        LambdaQueryWrapper<TbFactoryOrderDetail> factoryOrderDetailQuery = new LambdaQueryWrapper<>();
                        factoryOrderDetailQuery.eq(TbFactoryOrderDetail::getFactoryId, tbWorkOrderVO.getId());
                        List<TbFactoryOrderDetail> list = tbFactoryOrderDetailService.list(factoryOrderDetailQuery);
                        tbWorkOrderVO.setOrderDetailList(list);
                    });
                    String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
                    order.setPic(ossUrl + SymbolConstant.LEFT_DIVIDE + order.getPic());
                    order.setChildren(workOrderVOS);
                });
            }
        }
        return new PageResult<>(page);
    }

    @Override
    public PageResult<TbCustomerOrder> page(TbCustomerOrderParam tbCustomerOrderParam) {
        QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerOrderParam)) {

            // 根据所属客户 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerId())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getCustomerId, tbCustomerOrderParam.getCustomerId());
            }
            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getOrderNo())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getOrderNo, tbCustomerOrderParam.getOrderNo());
            }
            // 根据客户款号 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerSku())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getCustomerSku, tbCustomerOrderParam.getCustomerSku());
            }
            // 根据图片 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getPic())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getPic, tbCustomerOrderParam.getPic());
            }
            // 根据品类 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCategory())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getCategory, tbCustomerOrderParam.getCategory());
            }
            // 根据生产类型（1=FOB  2=CMT） 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getProduceType())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getProduceType, tbCustomerOrderParam.getProduceType());
            }
            // 根据单位（字典枚举） 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getUnit())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getUnit, tbCustomerOrderParam.getUnit());
            }
            // 根据价格 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getPrice())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getPrice, tbCustomerOrderParam.getPrice());
            }
            // 根据交期 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getGivedate())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getGivedate, tbCustomerOrderParam.getGivedate());
            }
            // 根据收货地址 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getReceiveAddr())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getReceiveAddr, tbCustomerOrderParam.getReceiveAddr());
            }
            // 根据省市区 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getAreacode())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getAreacode, tbCustomerOrderParam.getAreacode());
            }
            // 根据总数量 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getAmount())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getAmount, tbCustomerOrderParam.getAmount());
            }
            // 根据是否后台创建 0=小程序 1=PC 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getSyscreated())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getSyscreated, tbCustomerOrderParam.getSyscreated());
            }
            // 根据订单类型 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getOrderType())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getOrderType, tbCustomerOrderParam.getOrderType());
            }
            // 根据询价单_id 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getAskId())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getAskId, tbCustomerOrderParam.getAskId());
            }
            // 根据接单负责人id 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getPrincipal())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getPrincipal, tbCustomerOrderParam.getPrincipal());
            }
            // 根据状态 查询
            Integer status = tbCustomerOrderParam.getStatus();
            if(status != null){
                queryWrapper.lambda().eq(TbCustomerOrder::getStatus, status);
            }else {
                queryWrapper.lambda().ne(TbCustomerOrder::getStatus, -1);
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public PageResult<TbCustomerOrderVO> findPage(TbCustomerOrderParam tbCustomerOrderParam) {
        QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerOrderParam)) {
            Integer requestType = tbCustomerOrderParam.getRequestType();
            Long accountId = tbCustomerOrderParam.getCustomerId();
            if (requestType != null && requestType == 1 && accountId != null) {
                //小程序端查询同所属公司的需求订单
                Long customerId = tbCustomerAccountService.getCustomerIdByAccountId(accountId);
                if (customerId != null && customerId != 0) {
                    queryWrapper.eq("c.customer_id", customerId);
                } else {
                    queryWrapper.eq("a.customer_id", accountId);
                }
            } else {
                // 根据所属客户 查询
                if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerId())) {
                    queryWrapper.eq("a.customer_id", tbCustomerOrderParam.getCustomerId());
                }
            }
            // 根据状态 查询
            Integer status = tbCustomerOrderParam.getStatus();
            if(status != null){
                queryWrapper.eq("a.status", status);
            }else {
                queryWrapper.ne("a.status", -1);
            }
            // 根据品类 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCategory())) {
                queryWrapper.eq("a.category", tbCustomerOrderParam.getCategory());
            }
            // 根据生产类型 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getProduceType())) {
                queryWrapper.eq("a.produce_type", tbCustomerOrderParam.getProduceType());
            }
            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getOrderNo())) {
                queryWrapper.like("a.order_no", tbCustomerOrderParam.getOrderNo());
            }
            // 根据款号 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerSku())) {
                queryWrapper.like("a.customer_sku", tbCustomerOrderParam.getCustomerSku());
            }
            // 根据生产工单号
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getWorkOrderNo())) {
                queryWrapper.like("orderNoList", tbCustomerOrderParam.getWorkOrderNo());
            }
            // 收货人
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getReceivePerson())) {
                queryWrapper.like("a.receive_person", tbCustomerOrderParam.getReceivePerson());
            }
            // 接单负责人
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getPrincipalName())) {
                queryWrapper.like("a.principal_name", tbCustomerOrderParam.getPrincipalName());
            }
            // 交期开始时间
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getBeginGivedate())) {
                String beginGivedate = tbCustomerOrderParam.getBeginGivedate();
                Date beginDate = DateUtil.beginOfDay(DateUtil.parse(beginGivedate));
                queryWrapper.ge("a.givedate", beginDate);
            }
            // 交期结束时间
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getEndGivedate())) {
                String endGivedate = tbCustomerOrderParam.getEndGivedate();
                Date endDate = DateUtil.endOfDay(DateUtil.parse(endGivedate));
                queryWrapper.le("a.givedate", endDate);
            }
            if(ObjectUtil.isNotEmpty(tbCustomerOrderParam.getStatusArray())){
                queryWrapper.in("a.status", tbCustomerOrderParam.getStatusArray().split(","));
            }
        }
        queryWrapper.orderByDesc("a.id");
        Integer pageNo = tbCustomerOrderParam.getPageNo();
        Integer pageSize = tbCustomerOrderParam.getPageSize();
        Page<Object> pg = new Page<>();
        if (pageNo != null) {
            pg = new Page<>(pageNo, pageSize);
        } else {
            pg = PageFactory.defaultPage();
        }
        Page<TbCustomerOrderVO> page = baseMapper.findPage(pg, queryWrapper);
        if (page != null) {
            List<TbCustomerOrderVO> records = page.getRecords();
            if (CollUtil.isNotEmpty(records)) {
                String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
                records.forEach(order -> {
                    Long orderId = order.getId();
                    String pic = order.getPic();
                    if (StrUtil.isNotBlank(pic) && StrUtil.isNotBlank(ossUrl)) {
                        order.setPic(ossUrl + SymbolConstant.LEFT_DIVIDE + pic);
                    }
                    //品类名称
                    Long category = order.getCategory();
                    String names = productTypeService.getNamesById(category, null);
                    order.setStrCategory(names);
                    //查询订单详情信息
                    List<TbCustomerOrderDetail> detailList = getOrderDataByOrderId(orderId);
                    order.setOrderDetailList(detailList);
                    //查询生产进度
                    List<TbWorkerorderSpeed> tbWorkerorderSpeeds = getworkOrderByOrderId(orderId);
                    order.setWorkerorderSpeedList(tbWorkerorderSpeeds);
                });
            }
        }
        return new PageResult<>(page);
    }

    @Override
    public List<TbCustomerOrder> list(TbCustomerOrderParam tbCustomerOrderParam) {
        return this.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(TbCustomerOrderDTO tbCustomerOrderDTO) {
        TbCustomerOrder tbCustomerOrder = new TbCustomerOrder();
        BeanUtil.copyProperties(tbCustomerOrderDTO, tbCustomerOrder);
        tbCustomerOrder.setOrderType(1);
        tbCustomerOrder.setUnit(getUnitPieceId());
        //设置接单负责人及状态
        tbCustomerOrder = setPrincipal(tbCustomerOrder);
        //款式图路径（询价单）
        String picPath = tbCustomerOrderDTO.getPicPath();
        if (StrUtil.isNotBlank(picPath)) {
            String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
            picPath = picPath.replaceAll(ossUrl, "");
            picPath = picPath.substring(1);
            tbCustomerOrder.setPic(picPath);
        }
        //新增订单时分配状态设为未分配
        tbCustomerOrder.setDistributeStatus(0);
        Integer syscreated = tbCustomerOrder.getSyscreated();
        if (syscreated != null && syscreated == 1) {
            //PC后端创建 status=6 默认客户已接受价格
            tbCustomerOrder.setStatus(6);
        }
        this.save(tbCustomerOrder);
        Long askId = tbCustomerOrder.getAskId();
        //保存子表信息
        Long customerOrderId = tbCustomerOrder.getId();
        //详情图
        String[] detailPaths = tbCustomerOrderDTO.getDetailPaths();
        saveFileInfo(detailPaths, customerOrderId, 2);
        List<TbCustomerOrderDetailDTO> detailList = tbCustomerOrderDTO.getDetailList();
        if (CollUtil.isNotEmpty(detailList)) {
            //根据订单id删除详情表记录
            QueryWrapper<TbCustomerOrderDetail> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(TbCustomerOrderDetail::getOrderId, customerOrderId);
            TbCustomerOrderDetail tbCustomerOrderDetail = new TbCustomerOrderDetail();
            tbCustomerOrderDetail.setStatus(CommonStatusEnum.DELETED.getCode());
            tbCustomerOrderDetailService.update(tbCustomerOrderDetail, queryWrapper);
            //添加详情表
            detailList.forEach(detail -> {
                TbCustomerOrderDetailParam detailParam = new TbCustomerOrderDetailParam();
                BeanUtil.copyProperties(detail, detailParam);
                detailParam.setOrderId(customerOrderId);
                tbCustomerOrderDetailService.save(detailParam);
            });
        }
        //修改询价单状态
        if (askId != null) {
            TbCustomerAsk tbCustomerAsk = new TbCustomerAsk();
            tbCustomerAsk.setId(askId);
            tbCustomerAsk.setAskStatus(3000);
            tbCustomerAskService.updateById(tbCustomerAsk);
        }
        //发送钉钉通知-开启线程
        if (syscreated == null || syscreated == 0) {
            ThreadUtil.execute(() -> sendMessageOrder(customerOrderId));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbCustomerOrderParam> tbCustomerOrderParamList) {
        tbCustomerOrderParamList.forEach(tbCustomerOrderParam -> {
            this.removeById(tbCustomerOrderParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbCustomerOrderDTO tbCustomerOrderDTO) {
        Long customerOrderId = tbCustomerOrderDTO.getId();
        TbCustomerOrder tbCustomerOrder = this.getById(customerOrderId);
        if (ObjectUtil.isNull(tbCustomerOrder)) {
            throw new ServiceException(TbCustomerOrderExceptionEnum.NOT_EXIST);
        }
        BeanUtil.copyProperties(tbCustomerOrderDTO, tbCustomerOrder);
        tbCustomerOrder.setOrderType(1);
        tbCustomerOrder.setUnit(getUnitPieceId());
        //设置接单负责人及状态
        tbCustomerOrder = setPrincipal(tbCustomerOrder);
        this.updateById(tbCustomerOrder);
        //保存子表信息
        List<TbCustomerOrderDetailDTO> detailList = tbCustomerOrderDTO.getDetailList();
        if (CollUtil.isNotEmpty(detailList)) {
            //根据订单id删除详情表记录
            QueryWrapper<TbCustomerOrderDetail> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(TbCustomerOrderDetail::getOrderId, customerOrderId);
            TbCustomerOrderDetail tbCustomerOrderDetail = new TbCustomerOrderDetail();
            tbCustomerOrderDetail.setStatus(CommonStatusEnum.DELETED.getCode());
            tbCustomerOrderDetailService.update(tbCustomerOrderDetail, queryWrapper);

            detailList.forEach(detail -> {
                TbCustomerOrderDetailParam detailParam = new TbCustomerOrderDetailParam();
                BeanUtil.copyProperties(detail, detailParam);
                detailParam.setOrderId(customerOrderId);
                tbCustomerOrderDetailService.save(detailParam);
            });
        }

    }

    /**
     * @param tbCustomerOrder
     * @return TbCustomerOrder
     * @Description: 设置 接单负责人及订单状态
     * @author 邾茂星
     * @date 2022/1/14 14:49
     */
    private TbCustomerOrder setPrincipal(TbCustomerOrder tbCustomerOrder) {
        if (tbCustomerOrder != null) {
            tbCustomerOrder.setStatus(0);
            //询价单id
            Long askId = tbCustomerOrder.getAskId();
            //接单负责人
            Long principal = tbCustomerOrder.getPrincipal();
            if (principal != null) {
                SysUser sysUser = sysUserService.getById(principal);
                if (sysUser != null) {
                    tbCustomerOrder.setPrincipalName(sysUser.getName());
                    tbCustomerOrder.setStatus(1);
                }
            } else if (askId != null && principal == null) {
                //根据询价单，查看报价人
                TbCustomerAsk customerAsk = tbCustomerAskService.getById(askId);
                if (customerAsk != null) {
                    Long processUser = customerAsk.getProcessUser();
                    if (processUser != null) {
                        tbCustomerOrder.setStatus(1);
                        tbCustomerOrder.setPrincipal(processUser);
                        tbCustomerOrder.setPrincipalName(customerAsk.getProcessUserName());
                    }
                }
            }
        }
        return tbCustomerOrder;
    }

    @Override
    public TbCustomerOrder detail(TbCustomerOrderParam tbCustomerOrderParam) {
        return this.queryTbCustomerOrder(tbCustomerOrderParam);
    }

    /**
     * 获取生产订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    private TbCustomerOrder queryTbCustomerOrder(TbCustomerOrderParam tbCustomerOrderParam) {
        TbCustomerOrder tbCustomerOrder = this.getById(tbCustomerOrderParam.getId());
        if (ObjectUtil.isNull(tbCustomerOrder)) {
            throw new ServiceException(TbCustomerOrderExceptionEnum.NOT_EXIST);
        }
        return tbCustomerOrder;
    }

    @Override
    public void export(TbCustomerOrderParam tbCustomerOrderParam) {
        QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerOrderParam)) {
            Integer requestType = tbCustomerOrderParam.getRequestType();
            Long accountId = tbCustomerOrderParam.getCustomerId();
            if (requestType != null && requestType == 1 && accountId != null) {
                //小程序端查询同所属公司的需求订单
                Long customerId = tbCustomerAccountService.getCustomerIdByAccountId(accountId);
                if (customerId != null && customerId != 0) {
                    queryWrapper.eq("c.customer_id", customerId);
                } else {
                    queryWrapper.eq("a.customer_id", accountId);
                }
            } else {
                // 根据所属客户 查询
                if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerId())) {
                    queryWrapper.eq("a.customer_id", tbCustomerOrderParam.getCustomerId());
                }
            }
            // 根据状态 查询
            Integer status = tbCustomerOrderParam.getStatus();
            if(status != null){
                queryWrapper.eq("a.status", status);
            }else {
                queryWrapper.ne("a.status", -1);
            }
            // 根据品类 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCategory())) {
                queryWrapper.eq("a.category", tbCustomerOrderParam.getCategory());
            }
            // 根据生产类型 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getProduceType())) {
                queryWrapper.eq("a.produce_type", tbCustomerOrderParam.getProduceType());
            }
            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getOrderNo())) {
                queryWrapper.like("a.order_no", tbCustomerOrderParam.getOrderNo());
            }
            // 根据款号 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerSku())) {
                queryWrapper.like("a.customer_sku", tbCustomerOrderParam.getCustomerSku());
            }
            // 根据生产工单号
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getWorkOrderNo())) {
                queryWrapper.like("orderNoList", tbCustomerOrderParam.getWorkOrderNo());
            }
            // 收货人
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getReceivePerson())) {
                queryWrapper.like("a.receive_person", tbCustomerOrderParam.getReceivePerson());
            }
            // 接单负责人
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getPrincipalName())) {
                queryWrapper.like("a.principal_name", tbCustomerOrderParam.getPrincipalName());
            }
            // 交期开始时间
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getBeginGivedate())) {
                String beginGivedate = tbCustomerOrderParam.getBeginGivedate();
                Date beginDate = DateUtil.beginOfDay(DateUtil.parse(beginGivedate));
                queryWrapper.ge("a.givedate", beginDate);
            }
            // 交期结束时间
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getEndGivedate())) {
                String endGivedate = tbCustomerOrderParam.getEndGivedate();
                Date endDate = DateUtil.endOfDay(DateUtil.parse(endGivedate));
                queryWrapper.le("a.givedate", endDate);
            }
            if(ObjectUtil.isNotEmpty(tbCustomerOrderParam.getStatusArray())){
                queryWrapper.in("a.status", tbCustomerOrderParam.getStatusArray().split(","));
            }
        }
        queryWrapper.orderByDesc("a.id");
        List<CustomerOrderExportVO> exportVOList = baseMapper.export(queryWrapper);
        String code = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        PoiUtil.exportExcelWithStream("生产订单_" + code + ".xls", CustomerOrderExportVO.class, exportVOList);
    }

    /**
     * @param customeerId
     * @return String
     * @Description: 生成订单号
     * @author 邾茂星
     * @date 2022/1/12 9:30
     */
    @Override
    public String createOrderNo(Long customeerId) {
        String orderNo = "";
        if (customeerId != null) {
            //查询客户code
            TbCustomerAccount tbCustomerAccount = tbCustomerAccountService.getById(customeerId);
            String code = tbCustomerAccount.getCode();
            if (StrUtil.isBlank(code)) {
                //设置库户编码
                code = tbCustomerAccountService.createCustomerCode();
                tbCustomerAccount.setCode(code);
                tbCustomerAccountService.updateById(tbCustomerAccount);
            }
            //查询该客户下的订单个数
            QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(TbCustomerOrder::getCustomerId, customeerId);
            int count = this.count(queryWrapper) + 1;
            String strNum = String.valueOf(count);
            if (strNum.length() < 4) {
                strNum = String.format("%04d", count);
            }
            orderNo = code + strNum;
        }
        return orderNo;
    }

    /**
     * 上传文件
     *
     * @param files
     * @param orderNo
     * @param type
     * @return
     */
    @Override
    public int uploadFile(MultipartFile[] files, String orderNo, Integer type) {
        int resultNum = 0;
        if (files != null && StrUtil.isNotBlank(orderNo) && type != null) {
            QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("id");
            queryWrapper.eq("order_no", orderNo).last("limit 1");
            TbCustomerOrder tbCustomerOrder = this.getOne(queryWrapper);
            if (tbCustomerOrder != null) {
                for (MultipartFile file : files) {
                    // 获取文件原始名称
                    String originalFilename = file.getOriginalFilename();
                    // 获取文件后缀
                    String fileSuffix = null;
                    if (ObjectUtil.isNotEmpty(originalFilename)) {
                        fileSuffix = StrUtil.subAfter(originalFilename, SymbolConstant.PERIOD, true);
                    }
                    SysFileInfo sysFileInfo = new SysFileInfo();
                    sysFileInfo.setBusinessDataId(tbCustomerOrder.getId());
                    switch (type) {
                        case 1:
                            //款式图
                            sysFileInfo.setBusinessType(6);
                            break;
                        case 2:
                            //详情图
                            sysFileInfo.setBusinessType(2);
                            break;
                        case 3:
                            //合同
                            sysFileInfo.setBusinessType(3);
                            break;
                    }
                    // 上传
                    Long fileId = sysFileInfoService.uploadFile(file, sysFileInfo);
                    // 生成文件的路径
                    String path = fileId + SymbolConstant.PERIOD + fileSuffix;
                    //1=款式图 2=详情图 3=合同
                    if (type == 1) {
                        tbCustomerOrder.setPic(path);
                        this.updateById(tbCustomerOrder);
                    }
                    /* 合同附件上传完后，由前端调取改状态接口
                    if (type == 3) {
                        //已签约
                        tbCustomerOrder.setStatus(2);
                        //更新状态
                        this.updateById(tbCustomerOrder);
                    }*/
                    resultNum++;
                }
            }
        }
        return resultNum;
    }

    /**
     * 上传文件 - FTP
     *
     * @param files
     * @param orderNo
     * @param type
     * @return
     */
    public int uploadFileFTP(MultipartFile[] files, String orderNo, Integer type) {
        int resultNum = 0;
        if (files != null && StrUtil.isNotBlank(orderNo) && type != null) {
            Ftp ftp = new Ftp(ftpSupportConfig.getFtpUrl(), ftpSupportConfig.getFtpPort(),
                    ftpSupportConfig.getFtpAcc(), ftpSupportConfig.getFtpPwd());
            try {
                String styleDir = ftpSupportConfig.getPicDir() + "/customerOrder/" + orderNo;
                if (!ftp.existFile(styleDir)) {
                    ftp.mkdir(styleDir);
                }
                for (MultipartFile fileUp : files) {
                    File file = UploadFileUtil.multipartFileToFile(fileUp);
                    ftp.upload(styleDir, file);
                    //文件路径
                    String fileName = file.getName();
                    String suffix = fileName.substring(fileName.lastIndexOf("."));
                    String uuid = IdUtil.simpleUUID();
                    String path = styleDir + File.separator + uuid + suffix;
                    QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
                    queryWrapper.select("id");
                    queryWrapper.eq("order_no", orderNo).last("limit 1");
                    TbCustomerOrder tbCustomerOrder = this.getOne(queryWrapper);
                    if (tbCustomerOrder != null) {
                        //1=款式图 2=详情图 3=合同
                        if (type == 1) {
                            tbCustomerOrder.setPic(path);
                            this.updateById(tbCustomerOrder);
                        } else {
                            //记录到File表
                            SysFileInfoParam sysFileInfo = new SysFileInfoParam();
                            sysFileInfo.setFileLocation(4);
                            sysFileInfo.setBusinessDataId(tbCustomerOrder.getId());
                            sysFileInfo.setBusinessType(2);
                            sysFileInfo.setFileOriginName(fileName);
                            sysFileInfo.setFileObjectName(fileName);
                            sysFileInfo.setFilePath(path);
                            sysFileInfoServiceImpl.add(sysFileInfo);
                            if (type == 3) {
                                //上传合同文件，修改 订单状态
                                tbCustomerOrder.setStatus(2);
                                this.updateById(tbCustomerOrder);
                            }
                        }
                        resultNum++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    ftp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultNum;
    }

    @Override
    public Long publish(TbCustomerOrderDTO tbCustomerOrderDTO, MultipartFile file) {
        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        // 获取文件后缀
        String fileSuffix = null;
        if (ObjectUtil.isNotEmpty(originalFilename)) {
            fileSuffix = StrUtil.subAfter(originalFilename, SymbolConstant.PERIOD, true);
        }
        //款式图路径（询价单）
        String picPath = tbCustomerOrderDTO.getPicPath();
        if (StrUtil.isNotBlank(picPath)) {
            String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
            picPath = picPath.replaceAll(ossUrl, "");
            picPath = picPath.substring(1);
            tbCustomerOrderDTO.setPic(picPath);
        }
        if (file != null) {
            // 上传
            Long fileId = sysFileInfoService.uploadFile(file);
            tbCustomerOrderDTO.setPic(fileId + SymbolConstant.PERIOD + fileSuffix);
        }
        //保存工单
        return this.addBackId(tbCustomerOrderDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long addBackId(TbCustomerOrderDTO tbCustomerOrderDTO) {
        TbCustomerOrder tbCustomerOrder = new TbCustomerOrder();
        BeanUtil.copyProperties(tbCustomerOrderDTO, tbCustomerOrder);
        tbCustomerOrder.setOrderType(1);
        //单位件
        tbCustomerOrder.setUnit(getUnitPieceId());
        //新增订单时默认订单分配状态为未分配
        tbCustomerOrder.setDistributeStatus(0);
        this.save(tbCustomerOrder);
        Long customerOrderId = tbCustomerOrder.getId();
        //详情图
        String[] detailPaths = tbCustomerOrderDTO.getDetailPaths();
        saveFileInfo(detailPaths, customerOrderId, 2);
        //保存子表信息
        List<TbCustomerOrderDetailDTO> detailList = tbCustomerOrderDTO.getDetailList();
        if (CollUtil.isNotEmpty(detailList)) {
            detailList.forEach(detail -> {
                TbCustomerOrderDetailParam detailParam = new TbCustomerOrderDetailParam();
                BeanUtil.copyProperties(detail, detailParam);
                detailParam.setOrderId(customerOrderId);
                tbCustomerOrderDetailService.save(detailParam);
            });
        }
        //修改询价单状态 = 已转生产单
        Long askId = tbCustomerOrder.getAskId();
        if (askId != null) {
            TbCustomerAsk tbCustomerAsk = new TbCustomerAsk();
            tbCustomerAsk.setId(askId);
            tbCustomerAsk.setAskStatus(3000);
            tbCustomerAskService.updateById(tbCustomerAsk);
        }
        return tbCustomerOrder.getId();
    }

    /**
     * @param detailPaths
     * @param businessDataId
     * @param businessType
     * @Description: 保存附件信息
     * @author 邾茂星
     * @date 2022/1/19 10:29
     */
    private void saveFileInfo(String[] detailPaths, Long businessDataId, int businessType) {
        if (detailPaths != null && detailPaths.length > 0) {
            String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
            for (String path : detailPaths) {
                path = path.replaceAll(ossUrl, "");
                path = path.substring(1);
                //查询
                QueryWrapper<SysFileInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SysFileInfo::getFilePath, path);
                queryWrapper.last("limit 1");
                SysFileInfo sysFileInfo = sysFileInfoService.getOne(queryWrapper);
                if (sysFileInfo != null) {
                    sysFileInfo.setBusinessDataId(businessDataId);
                    sysFileInfo.setBusinessType(businessType);
                    // 生成文件的唯一id
                    Long fileId = IdWorker.getId();
                    sysFileInfo.setId(fileId);
                    sysFileInfoService.save(sysFileInfo);
                }
            }
        }
    }

    /**
     * 查询单位 件id
     *
     * @return
     */
    private Long getUnitPieceId() {
        Long id = 0L;
        List<Dict> dictList = sysDictDataService.findListByDictTypeCode("product_unit");
        if (CollUtil.isNotEmpty(dictList)) {
            for (Dict dict : dictList) {
                String value = dict.getStr("value");
                if ("件".equals(value)) {
                    id = dict.getLong("id");
                    break;
                }
            }
        }
        return id;
    }

    /**
     * 分配订单生成工单
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void distribute(TbOrderDistributeDTO dto) {
        Long orderId = dto.getOrderId();
        //0.先更新订单状态为生产中
        TbCustomerOrder tbCustomerOrder = new TbCustomerOrder();
        tbCustomerOrder.setId(orderId);
        tbCustomerOrder.setStatus(3);
        Long surplusCounts = dto.getSurplusCounts();
        //判断是否分配完全
        if (surplusCounts > 0) {
            tbCustomerOrder.setDistributeStatus(1);
        } else {
            tbCustomerOrder.setDistributeStatus(2);
        }
        this.updateById(tbCustomerOrder);
        //1.先把订单信息复制下来给工单上
        TbCustomerOrder customerOrder = this.getById(orderId);
        TbWorkOrder tbWorkOrder = new TbWorkOrder();
        tbWorkOrder.setOrderId(customerOrder.getId());
        tbWorkOrder.setCustomerSku(customerOrder.getCustomerSku());
        tbWorkOrder.setPic(customerOrder.getPic());
        tbWorkOrder.setCategory(customerOrder.getCategory());
        tbWorkOrder.setProduceType(customerOrder.getProduceType().intValue());
        tbWorkOrder.setUnit(customerOrder.getUnit().intValue());
        tbWorkOrder.setPrice(customerOrder.getPrice());
        tbWorkOrder.setGivedate(customerOrder.getGivedate());
        tbWorkOrder.setReceiveAddr(customerOrder.getReceiveAddr());
        tbWorkOrder.setOrderCounts(dto.getOrderCounts());
        tbWorkOrder.setFactoryrId(dto.getFactoryrId());
        //生成工单号
        String workOrderNo = createWorkOrderNo(customerOrder.getOrderNo(), customerOrder.getId());
        tbWorkOrder.setWorkOrderNo(workOrderNo);
        tbWorkOrder.setIscomplted(0);
        tbWorkOrder.setStatus(0);
        tbWorkOrderService.save(tbWorkOrder);
        Long workOrderId = tbWorkOrder.getId();

        //tbWorkOrder.setIscomplted();
        List<TbOrderDistributeDetailDTO> detailList = dto.getDetailList();
        //2.修改订单详细数量
        for (TbOrderDistributeDetailDTO detailDTO : detailList) {
            if (detailDTO.getCount() == 0) {
                //派发数量为0则不修改
                continue;
            }
            TbCustomerOrderDetail customerOrderDetail = new TbCustomerOrderDetail();
            customerOrderDetail.setId(detailDTO.getId());
            customerOrderDetail.setPushCount(detailDTO.getPushCountTotal());
            tbCustomerOrderDetailService.updateById(customerOrderDetail);

        }
        //3.插入工单详细页面
        for (TbOrderDistributeDetailDTO detailDTO : detailList) {
            if (detailDTO.getCount() == 0) {
                //派发数量为0则建工单详情
                continue;
            }
            TbFactoryOrderDetail factoryOrderDetail = new TbFactoryOrderDetail();
            factoryOrderDetail.setFactoryId(tbWorkOrder.getId());
            factoryOrderDetail.setColorId(detailDTO.getColorId());
            factoryOrderDetail.setColorName(detailDTO.getColorName());
            factoryOrderDetail.setCount(detailDTO.getCount());
            factoryOrderDetail.setSize(detailDTO.getSize());
            tbFactoryOrderDetailService.save(factoryOrderDetail);

        }
        //发送消息-跟单员 开启线程
        ThreadUtil.execute(() -> sendMerchandiser(workOrderId));
    }

    /**
     * 根据订单编号生成工单编号
     *
     * @param orderNo 订单编号
     * @param orderId 订单ID
     * @return
     */
    private String createWorkOrderNo(String orderNo, Long orderId) {
        int count = 1;
        if (!this.redisTemplate.hasKey(orderNo)) {
            //查询数据库看该订单下是否有工单
            LambdaQueryWrapper<TbWorkOrder> query = new LambdaQueryWrapper<>();
            query.eq(TbWorkOrder::getOrderId, orderId);
            query.orderByDesc(TbWorkOrder::getWorkOrderNo);
            query.last("limit 1 ");
            TbWorkOrder tbWorkOrder = tbWorkOrderService.getOne(query);
            if (ObjectUtils.isNotEmpty(tbWorkOrder)) {
                //此情况为数据库实际有数据,但redis的数据已经丢失 ,我们得重新把流水号放进redis
                String workOrderNo = tbWorkOrder.getWorkOrderNo();
                String countCode = workOrderNo.substring(workOrderNo.length() - 2);
                count = new Integer(countCode);
                count++;
            }
            this.redisTemplate.opsForValue().set(orderNo, String.valueOf(count));
        } else {
            count = Objects.requireNonNull(this.redisTemplate.opsForValue().increment(orderNo).intValue());
        }
        String codeString = "";
        if (count < 10) {
            codeString = orderNo + "JG0" + count;
        } else {
            codeString = orderNo + "JG" + count;
        }
        return codeString;
    }

    /**
     * 分配订单生成工单,先获取订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public List<TbCustomerOrderDetail> getDistributeDetail(Long orderId) {
        LambdaQueryWrapper<TbCustomerOrderDetail> query = new LambdaQueryWrapper<>();
        query.eq(TbCustomerOrderDetail::getOrderId, orderId);
        query.eq(TbCustomerOrderDetail::getStatus, CommonStatusEnum.ENABLE.getCode());
        return tbCustomerOrderDetailService.list(query);
    }

    /**
     * @param customeerId
     * @param status
     * @return int
     * @Description: 统计数量
     * @author 邾茂星
     * @date 2022/1/14 15:00
     */
    @Override
    public int countNumber(Long customeerId, Integer status) {
        int countNumber = 0;
        QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
        if (customeerId != null) {
            queryWrapper.lambda().eq(TbCustomerOrder::getCustomerId, customeerId);
        }
        if (status != null) {
            queryWrapper.lambda().eq(TbCustomerOrder::getStatus, status);
        }
        countNumber = this.count(queryWrapper);
        return countNumber;
    }

    @Override
    public int uploadPublishPhoto(Long id, MultipartFile uploadPhoto) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setBusinessDataId(id);
        sysFileInfo.setBusinessType(2);
        // 上传
        sysFileInfoService.uploadFile(uploadPhoto, sysFileInfo);
        return 1;
    }

    @Override
    public TbCustomerOrderVO getDetailById(Long orderId) {
        TbCustomerOrderVO tbCustomerOrderVO = new TbCustomerOrderVO();
        if (orderId != null) {
            QueryWrapper<TbCustomerOrder> qw = new QueryWrapper<>();
            qw.eq("a.id", orderId);
            Page<TbCustomerOrderVO> page = baseMapper.findPage(PageFactory.defaultPage(), qw);
            String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
            if (page != null && ObjectUtil.isNotNull(page.getRecords())) {
                tbCustomerOrderVO = page.getRecords().get(0);
                String pic = tbCustomerOrderVO.getPic();
                if (StrUtil.isNotBlank(pic) && StrUtil.isNotBlank(ossUrl)) {
                    tbCustomerOrderVO.setPic(ossUrl + SymbolConstant.LEFT_DIVIDE + pic);
                }
                //品类名称
                Long category = tbCustomerOrderVO.getCategory();
                String names = productTypeService.getNamesById(category, null);
                tbCustomerOrderVO.setStrCategory(names);
            }
            //查询订单详情信息
            List<TbCustomerOrderDetail> detailList = getOrderDataByOrderId(orderId);
            if (CollUtil.isNotEmpty(detailList)) {
                List<String> sizes = detailList.stream().map(detail -> detail.getSize()).collect(Collectors.toList());
                sizes = sizes.stream().distinct().collect(Collectors.toList());
                tbCustomerOrderVO.setSizeList(sizes);

                List<Long> colorIds = detailList.stream().map(detail -> detail.getColorId()).collect(Collectors.toList());
                colorIds = colorIds.stream().distinct().collect(Collectors.toList());
                tbCustomerOrderVO.setColorIdList(colorIds);
            }
            tbCustomerOrderVO.setOrderDetailList(detailList);
            //查询生产进度
            List<TbWorkerorderSpeed> tbWorkerorderSpeeds = getworkOrderByOrderId(orderId);
            tbCustomerOrderVO.setWorkerorderSpeedList(tbWorkerorderSpeeds);
            //查询详情图
            SysFileInfoParam sysFileInfoParam = new SysFileInfoParam();
            sysFileInfoParam.setBusinessDataId(orderId);
            sysFileInfoParam.setBusinessType(2);
            List<String> filePaths = sysFileInfoServiceImpl.findFilePath(sysFileInfoParam);
            List<String> newFilePaths = new ArrayList<>();
            if (CollUtil.isNotEmpty(filePaths)) {
                for (String filePath : filePaths) {
                    newFilePaths.add(ossUrl + SymbolConstant.LEFT_DIVIDE + filePath);
                }
            }
            tbCustomerOrderVO.setFilePathList(newFilePaths);
        }
        return tbCustomerOrderVO;
    }

    /**
     * @param orderId
     * @return List<TbCustomerOrderDetail>
     * @Description: 生产订单id查询，订单数量详情
     * @author 邾茂星
     * @date 2022/1/15 11:30
     */
    private List<TbCustomerOrderDetail> getOrderDataByOrderId(Long orderId) {
        List<TbCustomerOrderDetail> detailList = new ArrayList<>();
        if (orderId != null) {
            QueryWrapper<TbCustomerOrderDetail> qwOrderDetail = new QueryWrapper<>();
            qwOrderDetail.lambda().eq(TbCustomerOrderDetail::getOrderId, orderId)
                    .eq(TbCustomerOrderDetail::getStatus, CommonStatusEnum.ENABLE.getCode());
            detailList = tbCustomerOrderDetailService.list(qwOrderDetail);
        }
        return detailList;
    }

    /**
     * @param orderId
     * @return List<TbWorkerorderSpeed>
     * @Description: 生产订单id，查询生产进度
     * @author 邾茂星
     * @date 2022/1/15 13:13
     */
    private List<TbWorkerorderSpeed> getworkOrderByOrderId(Long orderId) {
        List<TbWorkOrder> workOrderList = new ArrayList<>();
        List<TbWorkerorderSpeed> workerorderSpeedList = new ArrayList<>();
        if (orderId != null) {
            QueryWrapper<TbWorkOrder> qwWorkOrder = new QueryWrapper<>();
            qwWorkOrder.select("id").lambda().eq(TbWorkOrder::getOrderId, orderId);
            qwWorkOrder.orderByAsc("id");
            workOrderList = tbWorkOrderService.list(qwWorkOrder);
        }
        if (CollUtil.isNotEmpty(workOrderList)) {
            workOrderList.forEach(workOrder -> {
                Long workOrderId = workOrder.getId();
                QueryWrapper<TbWorkerorderSpeed> qwWorkerSpeed = new QueryWrapper<>();
                qwWorkerSpeed.lambda().eq(TbWorkerorderSpeed::getWorkOrderId, workOrderId);
                TbWorkerorderSpeed workerorderSpeed = tbWorkerorderSpeedService.getOne(qwWorkerSpeed);
                if (workerorderSpeed != null) {
                    workerorderSpeedList.add(workerorderSpeed);
                }
            });
        }
        return workerorderSpeedList;
    }

    /**
     * @param id
     * @param status
     * @return int
     * @Description: 保存订单状态
     * @author 邾茂星
     * @date 2022/1/17 10:57
     */
    @Override
    public int saveStatus(Long id, Integer status) {
        int resultNum = 0;
        if (id != null && status != null) {
            TbCustomerOrder tbCustomerOrder = new TbCustomerOrder();
            tbCustomerOrder.setId(id);
            tbCustomerOrder.setStatus(status);
            if (status == 2) {
                //判断是否有合同
                LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysFileInfo::getBusinessDataId, id);
                queryWrapper.eq(SysFileInfo::getBusinessType, 3);
                int count = sysFileInfoService.count(queryWrapper);
                if(count == 0){
                    throw new ServiceException(ResponseData.DEFAULT_ERROR_CODE, "无上传合同，操作失败！");
                }
            }
            if (status == 4) {
                //客户确认收货
                tbCustomerOrder.setConfirmReceiptTime(new Date());
            }
            if (status == 5) {
                //关单，判断生产工单是否完结
                List<TbWorkOrderVO> tbWorkOrderVOS = tbWorkOrderService.getWorkOrderDataByOrderId(id);
                if (CollUtil.isNotEmpty(tbWorkOrderVOS)) {
                    for (TbWorkOrderVO tbWorkOrderVO : tbWorkOrderVOS) {
                        Integer status1 = tbWorkOrderVO.getStatus();
                        if (status1 < 80) {
                            throw new ServiceException(ResponseData.DEFAULT_ERROR_CODE, "生产工单未结单，不可关闭订单。");
                        }
                    }
                }
            }
            if (this.updateById(tbCustomerOrder)) {
                resultNum = 1;
            }
            if (status == 2) {
                //发送钉钉通知-发单员
                ThreadUtil.execute(() -> sendBillMsg(id));
            }
        }
        return resultNum;
    }

    @Override
    public Map<String, Integer> totalCounts(Long customerId, Long accountId) {
        //询价单
        Map<String, Integer> map = new HashMap<>();
        QueryWrapper<TbCustomerAsk> ask = new QueryWrapper<>();
        if (customerId != null && customerId != 0L) {
            ask.eq("b.customer_id", customerId);
        } else {
            ask.eq("a.account_id", accountId);
        }
        int askTotal = tbCustomerAskService.countNumber(ask);
        //未询价
        ask.eq("a.ask_status", 1000);
        int noQuotedAsk = tbCustomerAskService.countNumber(ask);
        //已询价
        ask = new QueryWrapper<>();
        if (customerId != null && customerId != 0L) {
            ask.eq("b.customer_id", customerId);
        } else {
            ask.eq("a.account_id", accountId);
        }
        ask.eq("a.ask_status", 2000);
        int quatedAsk = tbCustomerAskService.countNumber(ask);
        map.put("noQuotedAsk", noQuotedAsk);
        map.put("quatedAsk", quatedAsk);
        map.put("askTotal", askTotal);
        //生产单
        int noQuatedOrder = this.countOrderNumber(customerId, accountId, 0); //未询价
        int quatedOrder = this.countOrderNumber(customerId, accountId, 1); //已询价
        int writedOrder = this.countOrderNumber(customerId, accountId, 2); //已签约
        int workingOrder = this.countOrderNumber(customerId, accountId, 3); //生产中
        int shippedOrder = this.countOrderNumber(customerId, accountId, 4); //已发货
        int closedOrder = this.countOrderNumber(customerId, accountId, 5); //已关单
        int orderTotal = this.countOrderNumber(customerId, accountId, null);
        map.put("noQuatedOrder", noQuatedOrder);
        map.put("quatedOrder", quatedOrder);
        map.put("writedOrder", writedOrder);
        map.put("workingOrder", workingOrder);
        map.put("shippedOrder", shippedOrder);
        map.put("closedOrder", closedOrder);
        map.put("orderTotal", orderTotal);
        return map;
    }

    private int countOrderNumber(Long customerId, Long accountId, Integer status) {
        int countNumber = 0;
        QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
        if (customerId != null  && customerId != 0L) {
            queryWrapper.eq("b.customer_id", customerId);
        } else {
            queryWrapper.eq("a.customer_id", accountId);
        }
        if (status != null) {
            queryWrapper.eq("a.status", status);
        }
        countNumber = baseMapper.countNumber(queryWrapper);
        return countNumber;
    }

    /**
     * @return List<CustomerOrderRollInfoVO>
     * @Description: 生产订单_滚动信息
     * @author 邾茂星
     * @date 2022/1/17 13:22
     */
    @Override
    public List<CustomerOrderRollInfoVO> findRollInfo(Integer type) {
        List<CustomerOrderRollInfoVO> list = new ArrayList<>();
        if (type == 1) {
            //询价单
            list = tbCustomerAskService.findRollInfo();
        } else {
            //生产订单
            QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("a.id");
            //限制滚动消息查询4条
            queryWrapper.last(" limit 4");
            list = baseMapper.findRollInfo(queryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                list.forEach(order -> {
                    //状态
                    String statusName = baseMapper.getWorkOrderStatusName(order.getId());
                    order.setStatusName(statusName);
                });
            }
        }
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(entity -> {
                //品类id
                Long category = entity.getCategory();
                String names = productTypeService.getNamesById(category, null);
                entity.setCategoryName(names);
            });
        }
        return list;
    }

    /**
     * @param tbCustomerOrderContractVO
     * @return PageResult<TbCustomerOrderContractVO>
     * @Description: 销售订单合同分页查询
     * @author 邾茂星
     * @date 2022/1/19 11:08
     */
    @Override
    public PageResult<TbCustomerOrderContractVO> salesContractPage(TbCustomerOrderContractVO tbCustomerOrderContractVO) {
        QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerOrderContractVO)) {
            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderContractVO.getOrderNo())) {
                queryWrapper.like("a.order_no", tbCustomerOrderContractVO.getOrderNo());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderContractVO.getStatus())) {
                queryWrapper.eq("a.status", tbCustomerOrderContractVO.getStatus());
            }
            // 根据搜索值 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderContractVO.getSearchValue())) {
                String searchValue = tbCustomerOrderContractVO.getSearchValue();
                queryWrapper.and(wrapper -> {
                    wrapper.like("a.order_no", searchValue)
                            .or().like("b.name", searchValue)
                            .or().like("b.owner", searchValue);
                });
            }
        }
        queryWrapper.orderByDesc("a.id");
        return new PageResult<>(baseMapper.salesContractPage(PageFactory.defaultPage(), queryWrapper));
    }

    /**
     * @param orderId
     * @return List<String>
     * @Description: 查看合同附件路径
     * @author 邾茂星
     * @date 2022/1/19 12:07
     */
    @Override
    public List<String> checkContractFilePath(Long orderId, Integer type) {
        List<String> filePaths = new ArrayList<>();
        if (orderId != null && type != null) {
            SysFileInfoParam sysFileInfoParam = new SysFileInfoParam();
            sysFileInfoParam.setBusinessDataId(orderId);
            sysFileInfoParam.setBusinessType(type);
            List<String> filePathList = sysFileInfoService.findFilePath(sysFileInfoParam);
            if (CollUtil.isNotEmpty(filePathList)) {
                String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
                filePathList.forEach(path -> {
                    path = ossUrl + SymbolConstant.LEFT_DIVIDE + path;
                    filePaths.add(path);
                });
            }
        }
        return filePaths;
    }

    /**
     * @param id
     * @Description: 发送需求订单通知-钉钉
     * @author 邾茂星
     * @date 2022/3/4 10:50
     */
    public void sendMessageOrder(Long id) {
        TbCustomerOrder customerOrder = this.getById(id);
        String orderNo = customerOrder.getOrderNo();
        Long category = customerOrder.getCategory();
        String pic = customerOrder.getPic();
        if (StrUtil.isBlank(pic)) {
            for (int i = 0; i < 5; i++) {
                ThreadUtil.sleep(5, TimeUnit.SECONDS);
                TbCustomerOrder order = this.getById(id);
                if (StrUtil.isNotBlank(order.getPic())) {
                    pic = order.getPic();
                    break;
                }
            }
        }
        if (StrUtil.isNotBlank(pic)) {
            String names = productTypeService.getNamesById(category, null);
            String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
            String picUrl = ossUrl + SymbolConstant.LEFT_DIVIDE + pic;
            DingTalkClient client = new DefaultDingTalkClient(sjRobotUrl);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("markdown");
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle("需求订单通知");
            markdown.setText("#### 需求订单：" + orderNo + " \n\n" +
                    "> " + names + "\n\n" +
                    "> ![screenshot](" + picUrl + ")\n\n" +
                    "> #### [PC端 立即处理](" + pcManageUrl + ") \n");
            request.setMarkdown(markdown);
            try {
                client.execute(request);
                ThreadUtil.sleep(1, TimeUnit.SECONDS);
                //需求单通知人
                String orderUserids = sysConfigService.getSysConfigValueByCode("dingTalk_order_userids");
                if (StrUtil.isNotBlank(orderUserids)) {
                    request.setMsgtype("text");
                    OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
                    text.setContent("需求单通知：您有新的需求单信息，请注意查收！");
                    request.setText(text);
                    OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                    at.setAtUserIds(Arrays.asList(orderUserids.split(";")));
                    request.setAt(at);
                    client.execute(request);
                }
            } catch (ApiException e) {
                e.printStackTrace();
                log.error(StrUtil.format("发送需求订单钉钉群通知失败！需求订单号：{}", orderNo));
            }
        }
    }

    /**
     * @param workOrderId
     * @Description: 发送跟单通知-钉钉
     * @author 邾茂星
     * @date 2022/3/4 13:18
     */
    public void sendMerchandiser(Long workOrderId) {
        try {
            TbWorkOrder workOrder = tbWorkOrderService.getById(workOrderId);
            Long orderId = workOrder.getOrderId();
            TbCustomerOrder customerOrder = this.getById(orderId);
            String workOrderNo = workOrder.getWorkOrderNo();
            //工厂id
            Long factoryrId = workOrder.getFactoryrId();
            Long category = customerOrder.getCategory();
            String pic = customerOrder.getPic();
            String names = productTypeService.getNamesById(category, null);
            String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
            String picUrl = ossUrl + SymbolConstant.LEFT_DIVIDE + pic;
            DingTalkClient client = new DefaultDingTalkClient(sjRobotUrl);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("markdown");
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle("生产加工单通知");
            markdown.setText("#### 加工单号：" + workOrderNo + " \n\n" +
                    "> " + names + "\n\n" +
                    "> ![screenshot](" + picUrl + ")\n\n" +
                    "> #### [立即处理](" + gdAppUrl + ") \n");
            request.setMarkdown(markdown);
            client.execute(request);
            //@跟单员
            ThreadUtil.sleep(1, TimeUnit.SECONDS);
            List<String> jobNumList = tbFactoryMerchandiserService.getJobNumList(factoryrId);
            if (ObjectUtil.isNotEmpty(jobNumList)) {
                request.setMsgtype("text");
                OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
                text.setContent("跟单通知：您有新的跟单信息，请注意查收！");
                request.setText(text);
                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                at.setAtUserIds(jobNumList);
                request.setAt(at);
                client.execute(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(StrUtil.format("发送跟单钉钉群通知失败！加工单id：{}", workOrderId));
        }
    }

    /**
     * @param orderId
     * @Description: 发送钉钉通知-发单员
     * @author 邾茂星
     * @date 2022/3/21 10:35
     */
    public void sendBillMsg(Long orderId) {
        TbCustomerOrder customerOrder = this.getById(orderId);
        String orderNo = customerOrder.getOrderNo();
        DingTalkClient client = new DefaultDingTalkClient(sjRobotUrl);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        try {
            String billUserids = sysConfigService.getSysConfigValueByCode("dingTalk_bill_userids");
            if (StrUtil.isNotBlank(billUserids)) {
                request.setMsgtype("text");
                OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
                text.setContent(StrUtil.format("发单通知：您有新的发单消息，请注意查收！\n需求订单号：【 {} 】", orderNo));
                request.setText(text);
                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                at.setAtUserIds(Arrays.asList(billUserids.split(";")));
                request.setAt(at);
                client.execute(request);
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error(StrUtil.format("发单钉钉群通知失败！需求订单号：{}", orderNo));
        }
    }

    /**
     * @param orderId
     * @return List<WorkOrderDetailVO>
     * @Description: 查看订单，裁剪信息
     * @author 邾茂星
     * @date 2022/3/15 16:03
     */
    @Override
    public List<WorkOrderDetailVO> checkTailorInfo(Long orderId) {
        List<WorkOrderDetailVO> list = new ArrayList<>();
        TbCustomerOrderDetailParam tbCustomerOrderDetailParam = new TbCustomerOrderDetailParam();
        tbCustomerOrderDetailParam.setOrderId(orderId);
        tbCustomerOrderDetailParam.setSearchStatus(0);
        List<TbCustomerOrderDetail> detailList = tbCustomerOrderDetailService.list(tbCustomerOrderDetailParam);
        if (ObjectUtil.isNotEmpty(detailList)) {
            for (TbCustomerOrderDetail tbCustomerOrderDetail : detailList) {
                Long colorId = tbCustomerOrderDetail.getColorId();
                String size = tbCustomerOrderDetail.getSize();
                WorkOrderDetailVO workOrderDetailVO = new WorkOrderDetailVO();
                workOrderDetailVO.setColorId(colorId);
                workOrderDetailVO.setColorName(tbCustomerOrderDetail.getColorName());
                workOrderDetailVO.setSize(size);
                workOrderDetailVO.setCount(tbCustomerOrderDetail.getCount().intValue());
                //裁剪已报工数量
                QueryWrapper<WorkOrderDetailVO> queryWrapper = new QueryWrapper();
                queryWrapper.eq("a.id", orderId);
                queryWrapper.eq("c.color_id", colorId);
                queryWrapper.eq("c.size", size);
                List<WorkOrderDetailVO> tailorNums = baseMapper.getTailorNum(queryWrapper);
                if (ObjectUtil.isNotEmpty(tailorNums)) {
                    Integer processedNum = tailorNums.get(0).getProcessedNum();
                    workOrderDetailVO.setProcessedNum(processedNum);
                }
                list.add(workOrderDetailVO);
            }
        }
        return list;
    }

    /**
     * @param orderId
     * @return Map<Object>
     * @Description: 查看订单状态步骤图
     * @author 邾茂星
     * @date 2022/3/16 9:11
     */
    @Override
    public Map<String, Object> checkStatusStep(Long orderId) {
        Map<String, Object> map = new HashMap<>();
        List<Dict> dictList = new LinkedList<>();
        int nodeNum = 0;
        String desc = "";
        //询价单时间
        LambdaQueryWrapper<TbCustomerOrder> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(TbCustomerOrder::getId, orderId);
        TbCustomerOrder customerOrder = this.getOne(orderWrapper);
        Long askId = customerOrder.getAskId();
        if (ObjectUtil.isNotEmpty(askId)) {
            TbCustomerAsk customerAsk = tbCustomerAskService.getById(askId);
            Date createTime = customerAsk.getCreateTime();
            desc = DateUtil.format(createTime, "yyyy-MM-dd");
        } else {
            desc = "无";
        }
        Dict dict1 = new Dict();
        dict1.set("title", "询价");
        dict1.set("desc", desc);
        dictList.add(dict1);
        //下单时间及总数量
        Dict dict2 = new Dict();
        desc = DateUtil.format(customerOrder.getCreateTime(), "yyyy-MM-dd");
        //下单数量
        Integer amount = customerOrder.getAmount();
        desc = StrUtil.format(desc + " 总数量：{}件", amount);
        dict2.set("title", "下单");
        dict2.set("desc", desc);
        nodeNum = 1;
        dictList.add(dict2);
        //合同
        Dict dict3 = new Dict();
        LambdaQueryWrapper<SysFileInfo> fileWrapper = new LambdaQueryWrapper<>();
        fileWrapper.select(SysFileInfo::getCreateTime)
                .eq(SysFileInfo::getBusinessDataId, orderId)
                .eq(SysFileInfo::getBusinessType, 3)
                .orderByAsc(SysFileInfo::getId)
                .last("limit 1");
        SysFileInfo sysFileInfo = sysFileInfoService.getOne(fileWrapper);
        if (ObjectUtil.isNotEmpty(sysFileInfo)) {
            desc = DateUtil.format(sysFileInfo.getCreateTime(), "yyyy-MM-dd");
            nodeNum = 2;
        } else {
            desc = "";
        }
        dict3.set("title", "合同");
        dict3.set("desc", desc);
        dictList.add(dict3);
        //齐套
        Dict dict4 = getStatusDict(orderId, 1);
        if (ObjectUtil.isNotEmpty(dict4) && ObjectUtil.isNotEmpty(dict4.get("desc"))) {
            nodeNum = 3;
        }
        dictList.add(dict4);
        //裁剪，实裁数量
        Dict dict5 = getStatusDict(orderId, 2);
        if (ObjectUtil.isNotEmpty(dict5) && ObjectUtil.isNotEmpty(dict5.get("desc"))) {
            //实裁数量
            Integer statusNum = tbWorkerorderSpeedService.getStatusNum(orderId, 1);
            String res = dict5.get("desc").toString();
            res = StrUtil.format(res + " 实裁数量：{}", statusNum);
            dict5.set("desc", res);
            nodeNum = 4;
        }
        dictList.add(dict5);
        //上线时间
        Dict dict6 = getStatusDict(orderId, 3);
        if (ObjectUtil.isNotEmpty(dict6) && ObjectUtil.isNotEmpty(dict6.get("desc"))) {
            nodeNum = 5;
        }
        dictList.add(dict6);
        //缝制完成时间，完成数量
        Dict dict7 = getStatusDict(orderId, 4);
        if (ObjectUtil.isNotEmpty(dict7) && ObjectUtil.isNotEmpty(dict7.get("desc"))) {
            //缝制数量
            Integer statusNum = tbWorkerorderSpeedService.getStatusNum(orderId, 2);
            String res = dict7.get("desc").toString();
            res = StrUtil.format(res + " 缝制数量：{}", statusNum);
            dict7.set("desc", res);
            nodeNum = 6;
        }
        dictList.add(dict7);
        //质检时间
        Dict dict8 = getStatusDict(orderId, 5);
        if (ObjectUtil.isNotEmpty(dict8) && ObjectUtil.isNotEmpty(dict8.get("desc"))) {
            nodeNum = 7;
        }
        dictList.add(dict8);
        //发货时间
        Dict dict9 = getStatusDict(orderId, 6);
        if (ObjectUtil.isNotEmpty(dict9) && ObjectUtil.isNotEmpty(dict9.get("desc"))) {
            nodeNum = 8;
        }
        dictList.add(dict9);
        //收货时间
        Dict dict10 = new Dict();
        dict10.put("title", "确认收货");
        Date confirmReceiptTime = customerOrder.getConfirmReceiptTime();
        if (confirmReceiptTime != null) {
            dict10.put("desc", DateUtil.format(confirmReceiptTime, "yyyy-MM-dd"));
            nodeNum = 9;
        }
        dictList.add(dict10);
        //评分时间
        Dict dict11 = new Dict();
        dict11.put("title", "评价");
        QueryWrapper<TbOrderEvaluation> qwEval = new QueryWrapper<>();
        qwEval.select("DATE_FORMAT(create_time, '%Y-%m-%d') as dateStr").eq("order_id", orderId).last("limit 1");
        Map<String, Object> mapObject = tbOrderEvaluationService.getMap(qwEval);
        if (ObjectUtil.isNotEmpty(mapObject)) {
            desc = mapObject.get("dateStr").toString();
            nodeNum = 10;
        } else {
            desc = "";
        }
        dict11.put("desc", desc);
        dictList.add(dict11);
        map.put("dictList", dictList);
        map.put("nodeNum", nodeNum);
        return map;
    }

    private Dict getStatusDict(Long orderId, Integer type) {
        Dict dict = new Dict();
        String title = "";
        String desc = "";
        Integer businessType = 0;
        boolean isImages = false;
        boolean isVideos = false;
        QueryWrapper qwImages = new QueryWrapper();
        qwImages.eq("a.id", orderId);
        qwImages.eq("c.status", CommonStatusEnum.ENABLE);
        qwImages.in("c.file_suffix", "jpg", "png", "gif", "jpeg", "jfif");

        QueryWrapper qwVideos = new QueryWrapper();
        qwVideos.eq("a.id", orderId);
        qwVideos.eq("c.status", CommonStatusEnum.ENABLE);
        qwVideos.in("c.file_suffix", "mp4", "avi", "flv", "mpeg", "rmvb");
        switch (type) {
            case 1:
                title = "齐套日期";
                businessType = 7;
                break;
            case 2:
                title = "裁剪";
                businessType = 9;
                break;
            case 3:
                title = "上线日期";
                businessType = 10;
                break;
            case 4:
                title = "缝制完成";
                businessType = 11;
                break;
            case 5:
                title = "质检日期";
                businessType = 12;
                break;
            case 6:
                title = "发货时间";
                businessType = 8;
                break;
            default:
                break;
        }
        if (StrUtil.isNotBlank(title)) {
            desc = tbWorkerorderSpeedService.getStatusDesc(orderId, type);
        }
        dict.put("title", title);
        dict.put("desc", desc);
        dict.put("businessType", businessType);
        qwImages.eq("c.business_type", businessType);
        int countImagesNumber = baseMapper.countFileNumber(qwImages);
        if (countImagesNumber > 0) {
            isImages = true;
        }
        dict.put("isImages", isImages);
        qwVideos.eq("c.business_type", businessType);
        int countVideosNumber = baseMapper.countFileNumber(qwVideos);
        if (countVideosNumber > 0) {
            isVideos = true;
        }
        dict.put("isVideos", isVideos);
        return dict;
    }

    /**
     * @param orderId
     * @param businessType
     * @param fileType
     * @return List<String>
     * @Description: 获取订单附件URL
     * @author 邾茂星
     * @date 2022/3/28 16:21
     */
    @Override
    public List<String> orderFileUrlList(Long orderId, Integer businessType, Integer fileType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("c.status", CommonStatusEnum.ENABLE);
        if (orderId != null) {
            queryWrapper.eq("a.id", orderId);
        }
        if (businessType != null) {
            queryWrapper.eq("c.business_type", businessType);
        }
        if (fileType != null) {
            if (fileType == 1) {
                //图片
                queryWrapper.in("c.file_suffix", "jpg", "png", "gif", "jpeg", "jfif");
            }
            if (fileType == 2) {
                //视频
                queryWrapper.in("c.file_suffix", "mp4", "avi", "flv", "mpeg", "rmvb");
            }
        }
        queryWrapper.orderByAsc("c.id");
        List<String> list = baseMapper.orderFileUrlList(queryWrapper);
        return list;
    }
}

