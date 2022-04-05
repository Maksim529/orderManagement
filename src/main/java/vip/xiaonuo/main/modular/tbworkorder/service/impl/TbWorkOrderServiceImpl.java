
package vip.xiaonuo.main.modular.tbworkorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.consts.FileTypeConstant;
import vip.xiaonuo.common.consts.SymbolConstant;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.jwt.JwtTokenUtil;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.core.util.SMSUtil;
import vip.xiaonuo.main.modular.dingding.VO.DingOrderVO;
import vip.xiaonuo.main.modular.sysConfig.entity.ProductType;
import vip.xiaonuo.main.modular.sysConfig.service.ProductTypeService;
import vip.xiaonuo.main.modular.sysConfig.service.TbFactoryMerchandiserService;
import vip.xiaonuo.main.modular.tbcustomeraccount.entity.TbCustomerAccount;
import vip.xiaonuo.main.modular.tbcustomeraccount.service.TbCustomerAccountService;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrder;
import vip.xiaonuo.main.modular.tbcustomerorder.service.TbCustomerOrderService;
import vip.xiaonuo.main.modular.tbworkerorderspeed.entity.TbWorkerorderSpeed;
import vip.xiaonuo.main.modular.tbworkerorderspeed.service.TbWorkerorderSpeedService;
import vip.xiaonuo.main.modular.tbworkorder.dto.WorkOrderReportedDTO;
import vip.xiaonuo.main.modular.tbworkorder.dto.WorkOrderReportedDetailDTO;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkOrder;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkerorderReportedDetail;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkerorderReportedInfo;
import vip.xiaonuo.main.modular.tbworkorder.enums.TbWorkOrderExceptionEnum;
import vip.xiaonuo.main.modular.tbworkorder.mapper.TbWorkOrderMapper;
import vip.xiaonuo.main.modular.tbworkorder.param.TbWorkOrderParam;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkOrderService;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkerorderReportedDetailService;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkerorderReportedInfoService;
import vip.xiaonuo.main.modular.tbworkorder.vo.TbWorkOrderInfoVO;
import vip.xiaonuo.main.modular.tbworkorder.vo.TbWorkOrderVO;
import vip.xiaonuo.sys.modular.consts.service.SysConfigService;
import vip.xiaonuo.sys.modular.file.entity.SysFileInfo;
import vip.xiaonuo.sys.modular.file.service.SysFileInfoService;

import java.math.BigDecimal;
import java.util.*;

/**
 * 工厂工单service接口实现类
 *
 * @author wjc
 * @date 2022-01-13 14:29:32
 */
@Service
public class TbWorkOrderServiceImpl extends ServiceImpl<TbWorkOrderMapper, TbWorkOrder> implements TbWorkOrderService {
    @Autowired
    private SysFileInfoService sysFileInfoService;
    @Autowired
    private TbCustomerOrderService tbCustomerOrderService;
    @Autowired
    private TbCustomerAccountService tbCustomerAccountService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private TbWorkerorderSpeedService tbWorkerorderSpeedService;
    @Autowired
    private TbWorkerorderReportedInfoService tbWorkerorderReportedInfoService;
    @Autowired
    private TbWorkerorderReportedDetailService tbWorkerorderReportedDetailService;
    @Autowired
    private TbFactoryMerchandiserService tbFactoryMerchandiserService;

    @Override
    public PageResult<TbWorkOrder> page(TbWorkOrderParam tbWorkOrderParam) {
        QueryWrapper<TbWorkOrder> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbWorkOrderParam)) {

            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getOrderId())) {
                queryWrapper.lambda().eq(TbWorkOrder::getOrderId, tbWorkOrderParam.getOrderId());
            }
            // 根据工单号 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getWorkOrderNo())) {
                queryWrapper.lambda().eq(TbWorkOrder::getWorkOrderNo, tbWorkOrderParam.getWorkOrderNo());
            }
            // 根据客户款号 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getCustomerSku())) {
                queryWrapper.lambda().eq(TbWorkOrder::getCustomerSku, tbWorkOrderParam.getCustomerSku());
            }
            // 根据图片 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getPic())) {
                queryWrapper.lambda().eq(TbWorkOrder::getPic, tbWorkOrderParam.getPic());
            }
            // 根据品类 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getCategory())) {
                queryWrapper.lambda().eq(TbWorkOrder::getCategory, tbWorkOrderParam.getCategory());
            }
            // 根据生产类型（1、CMT2、FOB） 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getProduceType())) {
                queryWrapper.lambda().eq(TbWorkOrder::getProduceType, tbWorkOrderParam.getProduceType());
            }
            // 根据颜色 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getColor())) {
                queryWrapper.lambda().eq(TbWorkOrder::getColor, tbWorkOrderParam.getColor());
            }
            // 根据尺码（字典枚举） 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getSize())) {
                queryWrapper.lambda().eq(TbWorkOrder::getSize, tbWorkOrderParam.getSize());
            }
            // 根据单位（字典枚举） 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getUnit())) {
                queryWrapper.lambda().eq(TbWorkOrder::getUnit, tbWorkOrderParam.getUnit());
            }
            // 根据价格 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getPrice())) {
                queryWrapper.lambda().eq(TbWorkOrder::getPrice, tbWorkOrderParam.getPrice());
            }
            // 根据交期 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getGivedate())) {
                queryWrapper.lambda().eq(TbWorkOrder::getGivedate, tbWorkOrderParam.getGivedate());
            }
            // 根据单量 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getOrderCounts())) {
                queryWrapper.lambda().eq(TbWorkOrder::getOrderCounts, tbWorkOrderParam.getOrderCounts());
            }
            // 根据收货地址 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getReceiveAddr())) {
                queryWrapper.lambda().eq(TbWorkOrder::getReceiveAddr, tbWorkOrderParam.getReceiveAddr());
            }
            // 根据所属客户 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getFactoryrId())) {
                queryWrapper.lambda().eq(TbWorkOrder::getFactoryrId, tbWorkOrderParam.getFactoryrId());
            }
            // 根据 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getSyscreated())) {
                queryWrapper.lambda().eq(TbWorkOrder::getSyscreated, tbWorkOrderParam.getSyscreated());
            }
            // 根据0 未结单，1 已结单 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getIscomplted())) {
                queryWrapper.lambda().eq(TbWorkOrder::getIscomplted, tbWorkOrderParam.getIscomplted());
            }
            // 根据创建时间 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getConfirmUser())) {
                queryWrapper.lambda().eq(TbWorkOrder::getConfirmUser, tbWorkOrderParam.getConfirmUser());
            }
            // 根据创建人(默认管理员创建) 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getConfirmTime())) {
                queryWrapper.lambda().eq(TbWorkOrder::getConfirmTime, tbWorkOrderParam.getConfirmTime());
            }
            // 根据跟单员 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getMerchandiser())) {
                queryWrapper.lambda().eq(TbWorkOrder::getMerchandiser, tbWorkOrderParam.getMerchandiser());
            }
            // 根据跟单员id 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderParam.getMerchandiserId())) {
                queryWrapper.lambda().eq(TbWorkOrder::getMerchandiserId, tbWorkOrderParam.getMerchandiserId());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbWorkOrder> list(TbWorkOrderParam tbWorkOrderParam) {
        return this.list();
    }

    @Override
    public void add(TbWorkOrderParam tbWorkOrderParam) {
        TbWorkOrder tbWorkOrder = new TbWorkOrder();
        BeanUtil.copyProperties(tbWorkOrderParam, tbWorkOrder);
        this.save(tbWorkOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbWorkOrderParam> tbWorkOrderParamList) {
        tbWorkOrderParamList.forEach(tbWorkOrderParam -> {
            this.removeById(tbWorkOrderParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbWorkOrderParam tbWorkOrderParam) {
        TbWorkOrder tbWorkOrder = this.queryTbWorkOrder(tbWorkOrderParam);
        BeanUtil.copyProperties(tbWorkOrderParam, tbWorkOrder);
        this.updateById(tbWorkOrder);
    }

    @Override
    public TbWorkOrder detail(TbWorkOrderParam tbWorkOrderParam) {
        return this.queryTbWorkOrder(tbWorkOrderParam);
    }

    /**
     * 获取工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    private TbWorkOrder queryTbWorkOrder(TbWorkOrderParam tbWorkOrderParam) {
        TbWorkOrder tbWorkOrder = this.getById(tbWorkOrderParam.getId());
        if (ObjectUtil.isNull(tbWorkOrder)) {
            throw new ServiceException(TbWorkOrderExceptionEnum.NOT_EXIST);
        }
        return tbWorkOrder;
    }

    @Override
    public void export(TbWorkOrderParam tbWorkOrderParam) {
        List<TbWorkOrder> list = this.list(tbWorkOrderParam);
        PoiUtil.exportExcelWithStream("SnowyTbWorkOrder.xls", TbWorkOrder.class, list);
    }

    /**
     * 根据订单ID返回工单VO
     *
     * @param orderId
     * @return
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    @Override
    public List<TbWorkOrderVO> getWorkOrderDataByOrderId(Long orderId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("a.order_id", orderId);
        return baseMapper.findVOs(query);
    }

    /**
     * 查询当前跟单员下所负责的所有工单的不同状态的数量
     *
     * @return
     * @author wjc
     * @date 2022-1-18 09:47:43
     */
    @Override
    public Map<Integer, Integer> countByUser() {
        Map<Integer, Integer> result = new HashMap<>(10);
        QueryWrapper<TbWorkOrder> query = new QueryWrapper<>();
        query.lambda().eq(TbWorkOrder::getMerchandiserId, JwtTokenUtil.getId());
        //过滤掉状态非空和非0 未生效的工单
        query.lambda().ne(TbWorkOrder::getStatus, 0);
        query.isNotNull("status");
        query.select("DISTINCT status");
        List<Object> status = this.baseMapper.selectObjs(query);
        //未查询出订单
        if (CollectionUtil.isEmpty(status)) {
            return null;
        }
        status.forEach(item -> {
            LambdaQueryWrapper<TbWorkOrder> itemQuery = new LambdaQueryWrapper<>();
            itemQuery.eq(TbWorkOrder::getMerchandiserId, JwtTokenUtil.getId());
            itemQuery.eq(TbWorkOrder::getStatus, item);
            int count = this.count(itemQuery);
            result.put((Integer) item, count);
        });
        return result;
    }

    /**
     * 根据跟单员以及跟单数量查询工单信息分页
     *
     * @return
     */
    @Override
    public PageResult<DingOrderVO> pageByStatus(Integer status) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("a.merchandiser_id", JwtTokenUtil.getId());
        queryWrapper.ne("a.status", 0);
        if (ObjectUtil.isNotEmpty(status)) {
            queryWrapper.eq("a.status", status);
        }
        Page<DingOrderVO> dingOrderVOPage = this.baseMapper.pageByStatus(PageFactory.defaultPage(), queryWrapper);
        List<DingOrderVO> orders = dingOrderVOPage.getRecords();
        orders.forEach(order -> {
            //获取完整的品类名称
            String strCategory = productTypeService.getNamesById(order.getCategory(), "");
            order.setStrCategory(strCategory);
        });
        return new PageResult<>(dingOrderVOPage);
    }

    /**
     * 工单提交转入生产
     *
     * @param workOrderId
     * @author wjc
     * @date 2022-1-19 08:48:05
     */
    @Override
    public void commit(Long workOrderId) {
        LambdaQueryWrapper<SysFileInfo> fileQuery = new LambdaQueryWrapper<>();
        fileQuery.eq(SysFileInfo::getBusinessDataId, workOrderId);
        fileQuery.eq(SysFileInfo::getBusinessType, 4);
        int count = sysFileInfoService.count(fileQuery);
        if (count <= 0) {
            throw new ServiceException(500, "未找到有效的合同信息");
        }

        TbWorkOrder tbWorkOrder = new TbWorkOrder();
        tbWorkOrder.setId(workOrderId);
        tbWorkOrder.setStatus(10);
        this.updateById(tbWorkOrder);

        //创建生产工单进度表
        TbWorkerorderSpeed tbWorkerorderSpeed = new TbWorkerorderSpeed();
        tbWorkerorderSpeed.setWorkOrderId(workOrderId);
        //接单时间
        tbWorkerorderSpeed.setRecieveOrderTime(new Date());
        tbWorkerorderSpeedService.save(tbWorkerorderSpeed);
    }

    /**
     * @param tbWorkOrderInfoVO
     * @return TbWorkOrderInfoVO
     * @Description: 查询工单信息
     * @author 邾茂星
     * @date 2022/1/19 15:26
     */
    @Override
    public PageResult<TbWorkOrderInfoVO> searchOrder(TbWorkOrderInfoVO tbWorkOrderInfoVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (ObjectUtil.isNotNull(tbWorkOrderInfoVO)) {
            if (ObjectUtil.isNotNull(tbWorkOrderInfoVO.getOperatorId())) {
             //根据当前操作人id，查询所跟单的工厂ids
                List<Long> factoryIdList = tbFactoryMerchandiserService.getFactoryIdListByUserId(tbWorkOrderInfoVO.getOperatorId());
                if(ObjectUtil.isNotEmpty(factoryIdList)){
                    queryWrapper.in("a.factoryr_id", factoryIdList);
                }else {
                    queryWrapper.eq("a.merchandiser_id", tbWorkOrderInfoVO.getOperatorId());
                }
            }
            if (ObjectUtil.isNotNull(tbWorkOrderInfoVO.getMerchandiserId())) {
                queryWrapper.eq("a.merchandiser_id", tbWorkOrderInfoVO.getMerchandiserId());
            }
            if (ObjectUtil.isNotNull(tbWorkOrderInfoVO.getFactoryName())) {
                queryWrapper.like("b.name", tbWorkOrderInfoVO.getFactoryName());
            }
            if (ObjectUtil.isNotNull(tbWorkOrderInfoVO.getWorkOrderNo())) {
                queryWrapper.like("a.work_order_no", tbWorkOrderInfoVO.getWorkOrderNo());
            }
            if (ObjectUtil.isNotNull(tbWorkOrderInfoVO.getOrderNo())) {
                queryWrapper.like("c.order_no", tbWorkOrderInfoVO.getOrderNo());
            }
            if (ObjectUtil.isNotNull(tbWorkOrderInfoVO.getCustomerSku())) {
                queryWrapper.like("c.customer_sku", tbWorkOrderInfoVO.getCustomerSku());
            }
        }
        queryWrapper.orderByDesc("a.id");
        Integer pageNo = tbWorkOrderInfoVO.getPageNo();
        Integer pageSize = tbWorkOrderInfoVO.getPageSize();
        Page<Object> pg = new Page<>();
        if (pageNo != null) {
            pg = new Page<>(pageNo, pageSize);
        } else {
            pg = PageFactory.defaultPage();
        }
        Page<TbWorkOrderInfoVO> tbWorkOrderInfoVOPage = baseMapper.searchOrder(pg, queryWrapper);
        List<TbWorkOrderInfoVO> records = tbWorkOrderInfoVOPage.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            records.forEach(workOrderInfoVO -> {
                String pic = workOrderInfoVO.getPic();
                if (StrUtil.isNotBlank(pic)) {
                    String ossUrl = sysFileInfoService.joinOSSUrl(pic);
                    workOrderInfoVO.setPic(ossUrl);
                }
            });
        }
        return new PageResult<>(tbWorkOrderInfoVOPage);
    }

    /**
     * @param files 文件
     * @param id    工单ID
     * @param type  文件类型
     * @return TbWorkOrderInfoVO
     * @Description: 上传工单附件
     * @author wjc
     * @date 2022-1-20 09:02:28
     */
    @Override
    public void uploadFile(MultipartFile[] files, Long id, Integer type) {
        int resultNum = 0;
        if (files != null && ObjectUtil.isNotNull(id) && type != null) {
            for (MultipartFile file : files) {
                //获取文件原始名称
                String originalFilename = file.getOriginalFilename();
                // 获取文件后缀
                String fileSuffix = null;
                if (ObjectUtil.isNotEmpty(originalFilename)) {
                    fileSuffix = StrUtil.subAfter(originalFilename, SymbolConstant.PERIOD, true);
                }
                TbWorkOrder workOrder = this.getById(id);
                if (workOrder != null) {
                    SysFileInfo sysFileInfo = new SysFileInfo();
                    sysFileInfo.setBusinessDataId(id);
                    if (type == 4) {
                        //验证合同格式
                        /*if (!Arrays.asList(FileTypeConstant.CONTRACT_TYPE).contains(fileSuffix.toLowerCase())) {
                            throw new ServiceException(500, "文件格式校验失败");
                        }*/
                        //合同附件
                        sysFileInfo.setBusinessType(4);
                    }
                    // 上传
                    Long fileId = sysFileInfoService.uploadFile(file, sysFileInfo);
                    // 生成文件的路径
                    // String path = fileId + SymbolConstant.PERIOD + fileSuffix;
                }
            }
        }
    }

    /**
     * 通过订单Id获取所有该订单的合同文件信息
     *
     * @param id
     * @return
     */
    @Override
    public List<SysFileInfo> getContractPath(Long id) {
        LambdaQueryWrapper<SysFileInfo> query = new LambdaQueryWrapper<>();
        query.eq(SysFileInfo::getBusinessDataId, id);
        List<SysFileInfo> list = sysFileInfoService.list(query);
        list.forEach(item -> {
            String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
            item.setFilePath(ossUrl + SymbolConstant.LEFT_DIVIDE + item.getFilePath());
        });
        return list;
    }

    /**
     * 完成生产工单并结单
     *
     * @param workOrderId
     * @return
     */
    @Override
    public void finish(Long workOrderId) {
        TbWorkOrder workOrder = this.getById(workOrderId);
        if (workOrder.getStatus() != 70) {
            throw new ServiceException(500, "工单未发货,请和跟单员" + workOrder.getMerchandiser() + "确认该工单是否发货");
        }
        workOrder.setStatus(80);
        workOrder.setIscomplted(1);
        this.updateById(workOrder);
    }

    @Override
    public void sendSMS(Dict dict) {
        String workOrderId = dict.get("workOrderId").toString();
        Integer status = (Integer) dict.get("status");
        if (StrUtil.isNotBlank(workOrderId)) {
            TbWorkOrder tbWorkOrder = this.getById(workOrderId);
            Long orderId = tbWorkOrder.getOrderId();
            TbCustomerOrder tbCustomerOrder = tbCustomerOrderService.getById(orderId);
            Long categoryId = tbCustomerOrder.getCategory();
            ProductType productType = productTypeService.getById(categoryId);
            String typeName = productType.getTypeName();
            Long customerId = tbCustomerOrder.getCustomerId();
            TbCustomerAccount customerAccount = tbCustomerAccountService.getById(customerId);
            String ownerTel = customerAccount.getOwnerTel();
            String content = "尊敬的顾客：您的订单有新的消息，请前往微信小程序'尚加智造'中查看。感谢您的支持！";
            //已发货
            if (status == 70) {
                content = StrUtil.format("尊敬的顾客：您的订单【{}】已发货，请前往微信小程序'尚加智造'中查看。感谢您的支持！", typeName);
            }
            List<Map<String, Object>> list = new ArrayList();
            Map<String, Object> map = new HashMap<>();
            map.put("to", ownerTel);
            list.add(map);
            SMSUtil.sendSjSMS(list, content);
        }
    }

    /**
     * @param dto
     * @return Long
     * @Description: 保存-报工
     * @author 邾茂星
     * @date 2022/3/14 15:06
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveReported(WorkOrderReportedDTO dto) {
        Long resultId = null;
        TbWorkerorderReportedInfo workerOrderReportedInfo = new TbWorkerorderReportedInfo();
        BeanUtil.copyProperties(dto, workerOrderReportedInfo);
        Long workOrderId = dto.getWorkOrderId();
        Integer type = dto.getType();
        if (type == 1) {
            workerOrderReportedInfo.setTypeName("裁剪");
        }
        workerOrderReportedInfo.setSubmitDate(new Date());
        long id = IdWorker.getId();
        workerOrderReportedInfo.setId(id);
        int cutNum = 0;
        List<WorkOrderReportedDetailDTO> reportedDetailDTOList = dto.getReportedDetailDTOList();
        if (ObjectUtil.isNotEmpty(reportedDetailDTOList)) {
            for (WorkOrderReportedDetailDTO reportedDetailDTO : reportedDetailDTOList) {
                Long count = reportedDetailDTO.getCount();
                if (count != null && count != 0L) {
                    cutNum += count;
                }
            }
        }
        workerOrderReportedInfo.setSubmitNum(new BigDecimal(cutNum));
        tbWorkerorderReportedInfoService.save(workerOrderReportedInfo);
        resultId = workerOrderReportedInfo.getWorkOrderId();
        if (ObjectUtil.isNotEmpty(reportedDetailDTOList)) {
            reportedDetailDTOList.forEach(reportedDetailDTO ->{
                Long count = reportedDetailDTO.getCount();
                if(count != null && count != 0L){
                    TbWorkerorderReportedDetail detail = new TbWorkerorderReportedDetail();
                    BeanUtil.copyProperties(reportedDetailDTO, detail);
                    detail.setWorkOrderId(workOrderId);
                    detail.setReportedId(id);
                    tbWorkerorderReportedDetailService.save(detail);
                }
            });
        }
        //修改工单进度表-裁剪数量
        LambdaQueryWrapper<TbWorkerorderSpeed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbWorkerorderSpeed::getWorkOrderId, workOrderId);
        TbWorkerorderSpeed speed = tbWorkerorderSpeedService.getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(speed)){
            Integer sumNum = speed.getCutNum();
            if(sumNum == null){
                sumNum = 0;
            }
            speed.setCutNum(sumNum + cutNum);
            tbWorkerorderSpeedService.updateById(speed);
        }
        return resultId;
    }
}
