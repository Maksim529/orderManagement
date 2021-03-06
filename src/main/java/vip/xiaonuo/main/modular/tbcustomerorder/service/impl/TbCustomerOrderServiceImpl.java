
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
 * ????????????service???????????????
 *
 * @author ?????????
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
     * ?????????????????????????????????
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
            // ?????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerId())) {
                queryWrapper.eq("a.customer_id", tbCustomerOrderParam.getCustomerId());
            }
            // ???????????? ??????
            queryWrapper.gt("a.status", 1).ne("a.status", 6);
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getStatus())) {
                queryWrapper.eq("a.status", tbCustomerOrderParam.getStatus());
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCategory())) {
                queryWrapper.eq("a.category", tbCustomerOrderParam.getCategory());
            }
            // ??????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getOrderNo())) {
                queryWrapper.like("a.order_no", tbCustomerOrderParam.getOrderNo());
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerSku())) {
                queryWrapper.like("a.customer_sku", tbCustomerOrderParam.getCustomerSku());
            }
            // ?????????????????????
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
                    //????????????????????????
                    List<TbCustomerOrderDetail> detailList = getOrderDataByOrderId(orderId);
                    order.setOrderDetailList(detailList);
                    //??????????????????
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

            // ?????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerId())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getCustomerId, tbCustomerOrderParam.getCustomerId());
            }
            // ??????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getOrderNo())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getOrderNo, tbCustomerOrderParam.getOrderNo());
            }
            // ?????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerSku())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getCustomerSku, tbCustomerOrderParam.getCustomerSku());
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getPic())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getPic, tbCustomerOrderParam.getPic());
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCategory())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getCategory, tbCustomerOrderParam.getCategory());
            }
            // ?????????????????????1=FOB  2=CMT??? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getProduceType())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getProduceType, tbCustomerOrderParam.getProduceType());
            }
            // ?????????????????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getUnit())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getUnit, tbCustomerOrderParam.getUnit());
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getPrice())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getPrice, tbCustomerOrderParam.getPrice());
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getGivedate())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getGivedate, tbCustomerOrderParam.getGivedate());
            }
            // ?????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getReceiveAddr())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getReceiveAddr, tbCustomerOrderParam.getReceiveAddr());
            }
            // ??????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getAreacode())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getAreacode, tbCustomerOrderParam.getAreacode());
            }
            // ??????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getAmount())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getAmount, tbCustomerOrderParam.getAmount());
            }
            // ???????????????????????? 0=????????? 1=PC ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getSyscreated())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getSyscreated, tbCustomerOrderParam.getSyscreated());
            }
            // ?????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getOrderType())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getOrderType, tbCustomerOrderParam.getOrderType());
            }
            // ???????????????_id ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getAskId())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getAskId, tbCustomerOrderParam.getAskId());
            }
            // ?????????????????????id ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getPrincipal())) {
                queryWrapper.lambda().eq(TbCustomerOrder::getPrincipal, tbCustomerOrderParam.getPrincipal());
            }
            // ???????????? ??????
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
                //????????????????????????????????????????????????
                Long customerId = tbCustomerAccountService.getCustomerIdByAccountId(accountId);
                if (customerId != null && customerId != 0) {
                    queryWrapper.eq("c.customer_id", customerId);
                } else {
                    queryWrapper.eq("a.customer_id", accountId);
                }
            } else {
                // ?????????????????? ??????
                if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerId())) {
                    queryWrapper.eq("a.customer_id", tbCustomerOrderParam.getCustomerId());
                }
            }
            // ???????????? ??????
            Integer status = tbCustomerOrderParam.getStatus();
            if(status != null){
                queryWrapper.eq("a.status", status);
            }else {
                queryWrapper.ne("a.status", -1);
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCategory())) {
                queryWrapper.eq("a.category", tbCustomerOrderParam.getCategory());
            }
            // ?????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getProduceType())) {
                queryWrapper.eq("a.produce_type", tbCustomerOrderParam.getProduceType());
            }
            // ??????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getOrderNo())) {
                queryWrapper.like("a.order_no", tbCustomerOrderParam.getOrderNo());
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerSku())) {
                queryWrapper.like("a.customer_sku", tbCustomerOrderParam.getCustomerSku());
            }
            // ?????????????????????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getWorkOrderNo())) {
                queryWrapper.like("orderNoList", tbCustomerOrderParam.getWorkOrderNo());
            }
            // ?????????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getReceivePerson())) {
                queryWrapper.like("a.receive_person", tbCustomerOrderParam.getReceivePerson());
            }
            // ???????????????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getPrincipalName())) {
                queryWrapper.like("a.principal_name", tbCustomerOrderParam.getPrincipalName());
            }
            // ??????????????????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getBeginGivedate())) {
                String beginGivedate = tbCustomerOrderParam.getBeginGivedate();
                Date beginDate = DateUtil.beginOfDay(DateUtil.parse(beginGivedate));
                queryWrapper.ge("a.givedate", beginDate);
            }
            // ??????????????????
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
                    //????????????
                    Long category = order.getCategory();
                    String names = productTypeService.getNamesById(category, null);
                    order.setStrCategory(names);
                    //????????????????????????
                    List<TbCustomerOrderDetail> detailList = getOrderDataByOrderId(orderId);
                    order.setOrderDetailList(detailList);
                    //??????????????????
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
        //??????????????????????????????
        tbCustomerOrder = setPrincipal(tbCustomerOrder);
        //??????????????????????????????
        String picPath = tbCustomerOrderDTO.getPicPath();
        if (StrUtil.isNotBlank(picPath)) {
            String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
            picPath = picPath.replaceAll(ossUrl, "");
            picPath = picPath.substring(1);
            tbCustomerOrder.setPic(picPath);
        }
        //??????????????????????????????????????????
        tbCustomerOrder.setDistributeStatus(0);
        Integer syscreated = tbCustomerOrder.getSyscreated();
        if (syscreated != null && syscreated == 1) {
            //PC???????????? status=6 ???????????????????????????
            tbCustomerOrder.setStatus(6);
        }
        this.save(tbCustomerOrder);
        Long askId = tbCustomerOrder.getAskId();
        //??????????????????
        Long customerOrderId = tbCustomerOrder.getId();
        //?????????
        String[] detailPaths = tbCustomerOrderDTO.getDetailPaths();
        saveFileInfo(detailPaths, customerOrderId, 2);
        List<TbCustomerOrderDetailDTO> detailList = tbCustomerOrderDTO.getDetailList();
        if (CollUtil.isNotEmpty(detailList)) {
            //????????????id?????????????????????
            QueryWrapper<TbCustomerOrderDetail> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(TbCustomerOrderDetail::getOrderId, customerOrderId);
            TbCustomerOrderDetail tbCustomerOrderDetail = new TbCustomerOrderDetail();
            tbCustomerOrderDetail.setStatus(CommonStatusEnum.DELETED.getCode());
            tbCustomerOrderDetailService.update(tbCustomerOrderDetail, queryWrapper);
            //???????????????
            detailList.forEach(detail -> {
                TbCustomerOrderDetailParam detailParam = new TbCustomerOrderDetailParam();
                BeanUtil.copyProperties(detail, detailParam);
                detailParam.setOrderId(customerOrderId);
                tbCustomerOrderDetailService.save(detailParam);
            });
        }
        //?????????????????????
        if (askId != null) {
            TbCustomerAsk tbCustomerAsk = new TbCustomerAsk();
            tbCustomerAsk.setId(askId);
            tbCustomerAsk.setAskStatus(3000);
            tbCustomerAskService.updateById(tbCustomerAsk);
        }
        //??????????????????-????????????
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
        //??????????????????????????????
        tbCustomerOrder = setPrincipal(tbCustomerOrder);
        this.updateById(tbCustomerOrder);
        //??????????????????
        List<TbCustomerOrderDetailDTO> detailList = tbCustomerOrderDTO.getDetailList();
        if (CollUtil.isNotEmpty(detailList)) {
            //????????????id?????????????????????
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
     * @Description: ?????? ??????????????????????????????
     * @author ?????????
     * @date 2022/1/14 14:49
     */
    private TbCustomerOrder setPrincipal(TbCustomerOrder tbCustomerOrder) {
        if (tbCustomerOrder != null) {
            tbCustomerOrder.setStatus(0);
            //?????????id
            Long askId = tbCustomerOrder.getAskId();
            //???????????????
            Long principal = tbCustomerOrder.getPrincipal();
            if (principal != null) {
                SysUser sysUser = sysUserService.getById(principal);
                if (sysUser != null) {
                    tbCustomerOrder.setPrincipalName(sysUser.getName());
                    tbCustomerOrder.setStatus(1);
                }
            } else if (askId != null && principal == null) {
                //?????????????????????????????????
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
     * ??????????????????
     *
     * @author ?????????
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
                //????????????????????????????????????????????????
                Long customerId = tbCustomerAccountService.getCustomerIdByAccountId(accountId);
                if (customerId != null && customerId != 0) {
                    queryWrapper.eq("c.customer_id", customerId);
                } else {
                    queryWrapper.eq("a.customer_id", accountId);
                }
            } else {
                // ?????????????????? ??????
                if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerId())) {
                    queryWrapper.eq("a.customer_id", tbCustomerOrderParam.getCustomerId());
                }
            }
            // ???????????? ??????
            Integer status = tbCustomerOrderParam.getStatus();
            if(status != null){
                queryWrapper.eq("a.status", status);
            }else {
                queryWrapper.ne("a.status", -1);
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCategory())) {
                queryWrapper.eq("a.category", tbCustomerOrderParam.getCategory());
            }
            // ?????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getProduceType())) {
                queryWrapper.eq("a.produce_type", tbCustomerOrderParam.getProduceType());
            }
            // ??????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getOrderNo())) {
                queryWrapper.like("a.order_no", tbCustomerOrderParam.getOrderNo());
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getCustomerSku())) {
                queryWrapper.like("a.customer_sku", tbCustomerOrderParam.getCustomerSku());
            }
            // ?????????????????????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getWorkOrderNo())) {
                queryWrapper.like("orderNoList", tbCustomerOrderParam.getWorkOrderNo());
            }
            // ?????????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getReceivePerson())) {
                queryWrapper.like("a.receive_person", tbCustomerOrderParam.getReceivePerson());
            }
            // ???????????????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getPrincipalName())) {
                queryWrapper.like("a.principal_name", tbCustomerOrderParam.getPrincipalName());
            }
            // ??????????????????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderParam.getBeginGivedate())) {
                String beginGivedate = tbCustomerOrderParam.getBeginGivedate();
                Date beginDate = DateUtil.beginOfDay(DateUtil.parse(beginGivedate));
                queryWrapper.ge("a.givedate", beginDate);
            }
            // ??????????????????
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
        PoiUtil.exportExcelWithStream("????????????_" + code + ".xls", CustomerOrderExportVO.class, exportVOList);
    }

    /**
     * @param customeerId
     * @return String
     * @Description: ???????????????
     * @author ?????????
     * @date 2022/1/12 9:30
     */
    @Override
    public String createOrderNo(Long customeerId) {
        String orderNo = "";
        if (customeerId != null) {
            //????????????code
            TbCustomerAccount tbCustomerAccount = tbCustomerAccountService.getById(customeerId);
            String code = tbCustomerAccount.getCode();
            if (StrUtil.isBlank(code)) {
                //??????????????????
                code = tbCustomerAccountService.createCustomerCode();
                tbCustomerAccount.setCode(code);
                tbCustomerAccountService.updateById(tbCustomerAccount);
            }
            //?????????????????????????????????
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
     * ????????????
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
                    // ????????????????????????
                    String originalFilename = file.getOriginalFilename();
                    // ??????????????????
                    String fileSuffix = null;
                    if (ObjectUtil.isNotEmpty(originalFilename)) {
                        fileSuffix = StrUtil.subAfter(originalFilename, SymbolConstant.PERIOD, true);
                    }
                    SysFileInfo sysFileInfo = new SysFileInfo();
                    sysFileInfo.setBusinessDataId(tbCustomerOrder.getId());
                    switch (type) {
                        case 1:
                            //?????????
                            sysFileInfo.setBusinessType(6);
                            break;
                        case 2:
                            //?????????
                            sysFileInfo.setBusinessType(2);
                            break;
                        case 3:
                            //??????
                            sysFileInfo.setBusinessType(3);
                            break;
                    }
                    // ??????
                    Long fileId = sysFileInfoService.uploadFile(file, sysFileInfo);
                    // ?????????????????????
                    String path = fileId + SymbolConstant.PERIOD + fileSuffix;
                    //1=????????? 2=????????? 3=??????
                    if (type == 1) {
                        tbCustomerOrder.setPic(path);
                        this.updateById(tbCustomerOrder);
                    }
                    /* ?????????????????????????????????????????????????????????
                    if (type == 3) {
                        //?????????
                        tbCustomerOrder.setStatus(2);
                        //????????????
                        this.updateById(tbCustomerOrder);
                    }*/
                    resultNum++;
                }
            }
        }
        return resultNum;
    }

    /**
     * ???????????? - FTP
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
                    //????????????
                    String fileName = file.getName();
                    String suffix = fileName.substring(fileName.lastIndexOf("."));
                    String uuid = IdUtil.simpleUUID();
                    String path = styleDir + File.separator + uuid + suffix;
                    QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
                    queryWrapper.select("id");
                    queryWrapper.eq("order_no", orderNo).last("limit 1");
                    TbCustomerOrder tbCustomerOrder = this.getOne(queryWrapper);
                    if (tbCustomerOrder != null) {
                        //1=????????? 2=????????? 3=??????
                        if (type == 1) {
                            tbCustomerOrder.setPic(path);
                            this.updateById(tbCustomerOrder);
                        } else {
                            //?????????File???
                            SysFileInfoParam sysFileInfo = new SysFileInfoParam();
                            sysFileInfo.setFileLocation(4);
                            sysFileInfo.setBusinessDataId(tbCustomerOrder.getId());
                            sysFileInfo.setBusinessType(2);
                            sysFileInfo.setFileOriginName(fileName);
                            sysFileInfo.setFileObjectName(fileName);
                            sysFileInfo.setFilePath(path);
                            sysFileInfoServiceImpl.add(sysFileInfo);
                            if (type == 3) {
                                //??????????????????????????? ????????????
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
        // ????????????????????????
        String originalFilename = file.getOriginalFilename();
        // ??????????????????
        String fileSuffix = null;
        if (ObjectUtil.isNotEmpty(originalFilename)) {
            fileSuffix = StrUtil.subAfter(originalFilename, SymbolConstant.PERIOD, true);
        }
        //??????????????????????????????
        String picPath = tbCustomerOrderDTO.getPicPath();
        if (StrUtil.isNotBlank(picPath)) {
            String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
            picPath = picPath.replaceAll(ossUrl, "");
            picPath = picPath.substring(1);
            tbCustomerOrderDTO.setPic(picPath);
        }
        if (file != null) {
            // ??????
            Long fileId = sysFileInfoService.uploadFile(file);
            tbCustomerOrderDTO.setPic(fileId + SymbolConstant.PERIOD + fileSuffix);
        }
        //????????????
        return this.addBackId(tbCustomerOrderDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long addBackId(TbCustomerOrderDTO tbCustomerOrderDTO) {
        TbCustomerOrder tbCustomerOrder = new TbCustomerOrder();
        BeanUtil.copyProperties(tbCustomerOrderDTO, tbCustomerOrder);
        tbCustomerOrder.setOrderType(1);
        //?????????
        tbCustomerOrder.setUnit(getUnitPieceId());
        //???????????????????????????????????????????????????
        tbCustomerOrder.setDistributeStatus(0);
        this.save(tbCustomerOrder);
        Long customerOrderId = tbCustomerOrder.getId();
        //?????????
        String[] detailPaths = tbCustomerOrderDTO.getDetailPaths();
        saveFileInfo(detailPaths, customerOrderId, 2);
        //??????????????????
        List<TbCustomerOrderDetailDTO> detailList = tbCustomerOrderDTO.getDetailList();
        if (CollUtil.isNotEmpty(detailList)) {
            detailList.forEach(detail -> {
                TbCustomerOrderDetailParam detailParam = new TbCustomerOrderDetailParam();
                BeanUtil.copyProperties(detail, detailParam);
                detailParam.setOrderId(customerOrderId);
                tbCustomerOrderDetailService.save(detailParam);
            });
        }
        //????????????????????? = ???????????????
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
     * @Description: ??????????????????
     * @author ?????????
     * @date 2022/1/19 10:29
     */
    private void saveFileInfo(String[] detailPaths, Long businessDataId, int businessType) {
        if (detailPaths != null && detailPaths.length > 0) {
            String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
            for (String path : detailPaths) {
                path = path.replaceAll(ossUrl, "");
                path = path.substring(1);
                //??????
                QueryWrapper<SysFileInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SysFileInfo::getFilePath, path);
                queryWrapper.last("limit 1");
                SysFileInfo sysFileInfo = sysFileInfoService.getOne(queryWrapper);
                if (sysFileInfo != null) {
                    sysFileInfo.setBusinessDataId(businessDataId);
                    sysFileInfo.setBusinessType(businessType);
                    // ?????????????????????id
                    Long fileId = IdWorker.getId();
                    sysFileInfo.setId(fileId);
                    sysFileInfoService.save(sysFileInfo);
                }
            }
        }
    }

    /**
     * ???????????? ???id
     *
     * @return
     */
    private Long getUnitPieceId() {
        Long id = 0L;
        List<Dict> dictList = sysDictDataService.findListByDictTypeCode("product_unit");
        if (CollUtil.isNotEmpty(dictList)) {
            for (Dict dict : dictList) {
                String value = dict.getStr("value");
                if ("???".equals(value)) {
                    id = dict.getLong("id");
                    break;
                }
            }
        }
        return id;
    }

    /**
     * ????????????????????????
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void distribute(TbOrderDistributeDTO dto) {
        Long orderId = dto.getOrderId();
        //0.?????????????????????????????????
        TbCustomerOrder tbCustomerOrder = new TbCustomerOrder();
        tbCustomerOrder.setId(orderId);
        tbCustomerOrder.setStatus(3);
        Long surplusCounts = dto.getSurplusCounts();
        //????????????????????????
        if (surplusCounts > 0) {
            tbCustomerOrder.setDistributeStatus(1);
        } else {
            tbCustomerOrder.setDistributeStatus(2);
        }
        this.updateById(tbCustomerOrder);
        //1.??????????????????????????????????????????
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
        //???????????????
        String workOrderNo = createWorkOrderNo(customerOrder.getOrderNo(), customerOrder.getId());
        tbWorkOrder.setWorkOrderNo(workOrderNo);
        tbWorkOrder.setIscomplted(0);
        tbWorkOrder.setStatus(0);
        tbWorkOrderService.save(tbWorkOrder);
        Long workOrderId = tbWorkOrder.getId();

        //tbWorkOrder.setIscomplted();
        List<TbOrderDistributeDetailDTO> detailList = dto.getDetailList();
        //2.????????????????????????
        for (TbOrderDistributeDetailDTO detailDTO : detailList) {
            if (detailDTO.getCount() == 0) {
                //???????????????0????????????
                continue;
            }
            TbCustomerOrderDetail customerOrderDetail = new TbCustomerOrderDetail();
            customerOrderDetail.setId(detailDTO.getId());
            customerOrderDetail.setPushCount(detailDTO.getPushCountTotal());
            tbCustomerOrderDetailService.updateById(customerOrderDetail);

        }
        //3.????????????????????????
        for (TbOrderDistributeDetailDTO detailDTO : detailList) {
            if (detailDTO.getCount() == 0) {
                //???????????????0??????????????????
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
        //????????????-????????? ????????????
        ThreadUtil.execute(() -> sendMerchandiser(workOrderId));
    }

    /**
     * ????????????????????????????????????
     *
     * @param orderNo ????????????
     * @param orderId ??????ID
     * @return
     */
    private String createWorkOrderNo(String orderNo, Long orderId) {
        int count = 1;
        if (!this.redisTemplate.hasKey(orderNo)) {
            //?????????????????????????????????????????????
            LambdaQueryWrapper<TbWorkOrder> query = new LambdaQueryWrapper<>();
            query.eq(TbWorkOrder::getOrderId, orderId);
            query.orderByDesc(TbWorkOrder::getWorkOrderNo);
            query.last("limit 1 ");
            TbWorkOrder tbWorkOrder = tbWorkOrderService.getOne(query);
            if (ObjectUtils.isNotEmpty(tbWorkOrder)) {
                //????????????????????????????????????,???redis????????????????????? ,?????????????????????????????????redis
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
     * ????????????????????????,?????????????????????
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
     * @Description: ????????????
     * @author ?????????
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
        // ??????
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
                //????????????
                Long category = tbCustomerOrderVO.getCategory();
                String names = productTypeService.getNamesById(category, null);
                tbCustomerOrderVO.setStrCategory(names);
            }
            //????????????????????????
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
            //??????????????????
            List<TbWorkerorderSpeed> tbWorkerorderSpeeds = getworkOrderByOrderId(orderId);
            tbCustomerOrderVO.setWorkerorderSpeedList(tbWorkerorderSpeeds);
            //???????????????
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
     * @Description: ????????????id???????????????????????????
     * @author ?????????
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
     * @Description: ????????????id?????????????????????
     * @author ?????????
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
     * @Description: ??????????????????
     * @author ?????????
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
                //?????????????????????
                LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysFileInfo::getBusinessDataId, id);
                queryWrapper.eq(SysFileInfo::getBusinessType, 3);
                int count = sysFileInfoService.count(queryWrapper);
                if(count == 0){
                    throw new ServiceException(ResponseData.DEFAULT_ERROR_CODE, "?????????????????????????????????");
                }
            }
            if (status == 4) {
                //??????????????????
                tbCustomerOrder.setConfirmReceiptTime(new Date());
            }
            if (status == 5) {
                //???????????????????????????????????????
                List<TbWorkOrderVO> tbWorkOrderVOS = tbWorkOrderService.getWorkOrderDataByOrderId(id);
                if (CollUtil.isNotEmpty(tbWorkOrderVOS)) {
                    for (TbWorkOrderVO tbWorkOrderVO : tbWorkOrderVOS) {
                        Integer status1 = tbWorkOrderVO.getStatus();
                        if (status1 < 80) {
                            throw new ServiceException(ResponseData.DEFAULT_ERROR_CODE, "?????????????????????????????????????????????");
                        }
                    }
                }
            }
            if (this.updateById(tbCustomerOrder)) {
                resultNum = 1;
            }
            if (status == 2) {
                //??????????????????-?????????
                ThreadUtil.execute(() -> sendBillMsg(id));
            }
        }
        return resultNum;
    }

    @Override
    public Map<String, Integer> totalCounts(Long customerId, Long accountId) {
        //?????????
        Map<String, Integer> map = new HashMap<>();
        QueryWrapper<TbCustomerAsk> ask = new QueryWrapper<>();
        if (customerId != null && customerId != 0L) {
            ask.eq("b.customer_id", customerId);
        } else {
            ask.eq("a.account_id", accountId);
        }
        int askTotal = tbCustomerAskService.countNumber(ask);
        //?????????
        ask.eq("a.ask_status", 1000);
        int noQuotedAsk = tbCustomerAskService.countNumber(ask);
        //?????????
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
        //?????????
        int noQuatedOrder = this.countOrderNumber(customerId, accountId, 0); //?????????
        int quatedOrder = this.countOrderNumber(customerId, accountId, 1); //?????????
        int writedOrder = this.countOrderNumber(customerId, accountId, 2); //?????????
        int workingOrder = this.countOrderNumber(customerId, accountId, 3); //?????????
        int shippedOrder = this.countOrderNumber(customerId, accountId, 4); //?????????
        int closedOrder = this.countOrderNumber(customerId, accountId, 5); //?????????
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
     * @Description: ????????????_????????????
     * @author ?????????
     * @date 2022/1/17 13:22
     */
    @Override
    public List<CustomerOrderRollInfoVO> findRollInfo(Integer type) {
        List<CustomerOrderRollInfoVO> list = new ArrayList<>();
        if (type == 1) {
            //?????????
            list = tbCustomerAskService.findRollInfo();
        } else {
            //????????????
            QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("a.id");
            //????????????????????????4???
            queryWrapper.last(" limit 4");
            list = baseMapper.findRollInfo(queryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                list.forEach(order -> {
                    //??????
                    String statusName = baseMapper.getWorkOrderStatusName(order.getId());
                    order.setStatusName(statusName);
                });
            }
        }
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(entity -> {
                //??????id
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
     * @Description: ??????????????????????????????
     * @author ?????????
     * @date 2022/1/19 11:08
     */
    @Override
    public PageResult<TbCustomerOrderContractVO> salesContractPage(TbCustomerOrderContractVO tbCustomerOrderContractVO) {
        QueryWrapper<TbCustomerOrder> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerOrderContractVO)) {
            // ??????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderContractVO.getOrderNo())) {
                queryWrapper.like("a.order_no", tbCustomerOrderContractVO.getOrderNo());
            }
            // ???????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerOrderContractVO.getStatus())) {
                queryWrapper.eq("a.status", tbCustomerOrderContractVO.getStatus());
            }
            // ??????????????? ??????
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
     * @Description: ????????????????????????
     * @author ?????????
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
     * @Description: ????????????????????????-??????
     * @author ?????????
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
            markdown.setTitle("??????????????????");
            markdown.setText("#### ???????????????" + orderNo + " \n\n" +
                    "> " + names + "\n\n" +
                    "> ![screenshot](" + picUrl + ")\n\n" +
                    "> #### [PC??? ????????????](" + pcManageUrl + ") \n");
            request.setMarkdown(markdown);
            try {
                client.execute(request);
                ThreadUtil.sleep(1, TimeUnit.SECONDS);
                //??????????????????
                String orderUserids = sysConfigService.getSysConfigValueByCode("dingTalk_order_userids");
                if (StrUtil.isNotBlank(orderUserids)) {
                    request.setMsgtype("text");
                    OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
                    text.setContent("??????????????????????????????????????????????????????????????????");
                    request.setText(text);
                    OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                    at.setAtUserIds(Arrays.asList(orderUserids.split(";")));
                    request.setAt(at);
                    client.execute(request);
                }
            } catch (ApiException e) {
                e.printStackTrace();
                log.error(StrUtil.format("????????????????????????????????????????????????????????????{}", orderNo));
            }
        }
    }

    /**
     * @param workOrderId
     * @Description: ??????????????????-??????
     * @author ?????????
     * @date 2022/3/4 13:18
     */
    public void sendMerchandiser(Long workOrderId) {
        try {
            TbWorkOrder workOrder = tbWorkOrderService.getById(workOrderId);
            Long orderId = workOrder.getOrderId();
            TbCustomerOrder customerOrder = this.getById(orderId);
            String workOrderNo = workOrder.getWorkOrderNo();
            //??????id
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
            markdown.setTitle("?????????????????????");
            markdown.setText("#### ???????????????" + workOrderNo + " \n\n" +
                    "> " + names + "\n\n" +
                    "> ![screenshot](" + picUrl + ")\n\n" +
                    "> #### [????????????](" + gdAppUrl + ") \n");
            request.setMarkdown(markdown);
            client.execute(request);
            //@?????????
            ThreadUtil.sleep(1, TimeUnit.SECONDS);
            List<String> jobNumList = tbFactoryMerchandiserService.getJobNumList(factoryrId);
            if (ObjectUtil.isNotEmpty(jobNumList)) {
                request.setMsgtype("text");
                OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
                text.setContent("????????????????????????????????????????????????????????????");
                request.setText(text);
                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                at.setAtUserIds(jobNumList);
                request.setAt(at);
                client.execute(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(StrUtil.format("?????????????????????????????????????????????id???{}", workOrderId));
        }
    }

    /**
     * @param orderId
     * @Description: ??????????????????-?????????
     * @author ?????????
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
                text.setContent(StrUtil.format("????????????????????????????????????????????????????????????\n????????????????????? {} ???", orderNo));
                request.setText(text);
                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                at.setAtUserIds(Arrays.asList(billUserids.split(";")));
                request.setAt(at);
                client.execute(request);
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error(StrUtil.format("????????????????????????????????????????????????{}", orderNo));
        }
    }

    /**
     * @param orderId
     * @return List<WorkOrderDetailVO>
     * @Description: ???????????????????????????
     * @author ?????????
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
                //?????????????????????
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
     * @Description: ???????????????????????????
     * @author ?????????
     * @date 2022/3/16 9:11
     */
    @Override
    public Map<String, Object> checkStatusStep(Long orderId) {
        Map<String, Object> map = new HashMap<>();
        List<Dict> dictList = new LinkedList<>();
        int nodeNum = 0;
        String desc = "";
        //???????????????
        LambdaQueryWrapper<TbCustomerOrder> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(TbCustomerOrder::getId, orderId);
        TbCustomerOrder customerOrder = this.getOne(orderWrapper);
        Long askId = customerOrder.getAskId();
        if (ObjectUtil.isNotEmpty(askId)) {
            TbCustomerAsk customerAsk = tbCustomerAskService.getById(askId);
            Date createTime = customerAsk.getCreateTime();
            desc = DateUtil.format(createTime, "yyyy-MM-dd");
        } else {
            desc = "???";
        }
        Dict dict1 = new Dict();
        dict1.set("title", "??????");
        dict1.set("desc", desc);
        dictList.add(dict1);
        //????????????????????????
        Dict dict2 = new Dict();
        desc = DateUtil.format(customerOrder.getCreateTime(), "yyyy-MM-dd");
        //????????????
        Integer amount = customerOrder.getAmount();
        desc = StrUtil.format(desc + " ????????????{}???", amount);
        dict2.set("title", "??????");
        dict2.set("desc", desc);
        nodeNum = 1;
        dictList.add(dict2);
        //??????
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
        dict3.set("title", "??????");
        dict3.set("desc", desc);
        dictList.add(dict3);
        //??????
        Dict dict4 = getStatusDict(orderId, 1);
        if (ObjectUtil.isNotEmpty(dict4) && ObjectUtil.isNotEmpty(dict4.get("desc"))) {
            nodeNum = 3;
        }
        dictList.add(dict4);
        //?????????????????????
        Dict dict5 = getStatusDict(orderId, 2);
        if (ObjectUtil.isNotEmpty(dict5) && ObjectUtil.isNotEmpty(dict5.get("desc"))) {
            //????????????
            Integer statusNum = tbWorkerorderSpeedService.getStatusNum(orderId, 1);
            String res = dict5.get("desc").toString();
            res = StrUtil.format(res + " ???????????????{}", statusNum);
            dict5.set("desc", res);
            nodeNum = 4;
        }
        dictList.add(dict5);
        //????????????
        Dict dict6 = getStatusDict(orderId, 3);
        if (ObjectUtil.isNotEmpty(dict6) && ObjectUtil.isNotEmpty(dict6.get("desc"))) {
            nodeNum = 5;
        }
        dictList.add(dict6);
        //?????????????????????????????????
        Dict dict7 = getStatusDict(orderId, 4);
        if (ObjectUtil.isNotEmpty(dict7) && ObjectUtil.isNotEmpty(dict7.get("desc"))) {
            //????????????
            Integer statusNum = tbWorkerorderSpeedService.getStatusNum(orderId, 2);
            String res = dict7.get("desc").toString();
            res = StrUtil.format(res + " ???????????????{}", statusNum);
            dict7.set("desc", res);
            nodeNum = 6;
        }
        dictList.add(dict7);
        //????????????
        Dict dict8 = getStatusDict(orderId, 5);
        if (ObjectUtil.isNotEmpty(dict8) && ObjectUtil.isNotEmpty(dict8.get("desc"))) {
            nodeNum = 7;
        }
        dictList.add(dict8);
        //????????????
        Dict dict9 = getStatusDict(orderId, 6);
        if (ObjectUtil.isNotEmpty(dict9) && ObjectUtil.isNotEmpty(dict9.get("desc"))) {
            nodeNum = 8;
        }
        dictList.add(dict9);
        //????????????
        Dict dict10 = new Dict();
        dict10.put("title", "????????????");
        Date confirmReceiptTime = customerOrder.getConfirmReceiptTime();
        if (confirmReceiptTime != null) {
            dict10.put("desc", DateUtil.format(confirmReceiptTime, "yyyy-MM-dd"));
            nodeNum = 9;
        }
        dictList.add(dict10);
        //????????????
        Dict dict11 = new Dict();
        dict11.put("title", "??????");
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
                title = "????????????";
                businessType = 7;
                break;
            case 2:
                title = "??????";
                businessType = 9;
                break;
            case 3:
                title = "????????????";
                businessType = 10;
                break;
            case 4:
                title = "????????????";
                businessType = 11;
                break;
            case 5:
                title = "????????????";
                businessType = 12;
                break;
            case 6:
                title = "????????????";
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
     * @Description: ??????????????????URL
     * @author ?????????
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
                //??????
                queryWrapper.in("c.file_suffix", "jpg", "png", "gif", "jpeg", "jfif");
            }
            if (fileType == 2) {
                //??????
                queryWrapper.in("c.file_suffix", "mp4", "avi", "flv", "mpeg", "rmvb");
            }
        }
        queryWrapper.orderByAsc("c.id");
        List<String> list = baseMapper.orderFileUrlList(queryWrapper);
        return list;
    }
}

