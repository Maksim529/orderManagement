
package vip.xiaonuo.main.modular.tbfactoryorderdetail.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import vip.xiaonuo.common.consts.CommonConstant;
import vip.xiaonuo.common.enums.CommonStatusEnum;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.entity.TbFactoryOrderDetail;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.enums.TbFactoryOrderDetailExceptionEnum;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.mapper.TbFactoryOrderDetailMapper;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.param.TbFactoryOrderDetailParam;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.service.TbFactoryOrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.vo.WorkOrderAmountInfoVO;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.vo.WorkOrderDetailVO;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkerorderReportedDetail;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkerorderReportedDetailService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 工单明细service接口实现类
 *
 * @author wjc
 * @date 2022-01-13 13:35:25
 */
@Service
public class TbFactoryOrderDetailServiceImpl extends ServiceImpl<TbFactoryOrderDetailMapper, TbFactoryOrderDetail> implements TbFactoryOrderDetailService {
    @Autowired
    private TbWorkerorderReportedDetailService tbWorkerorderReportedDetailService;

    @Override
    public PageResult<TbFactoryOrderDetail> page(TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        QueryWrapper<TbFactoryOrderDetail> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbFactoryOrderDetailParam)) {

            // 根据订单id 查询
            if (ObjectUtil.isNotEmpty(tbFactoryOrderDetailParam.getFactoryId())) {
                queryWrapper.lambda().eq(TbFactoryOrderDetail::getFactoryId, tbFactoryOrderDetailParam.getFactoryId());
            }
            // 根据工厂方 查询
            if (ObjectUtil.isNotEmpty(tbFactoryOrderDetailParam.getColorId())) {
                queryWrapper.lambda().eq(TbFactoryOrderDetail::getColorId, tbFactoryOrderDetailParam.getColorId());
            }
            // 根据颜色名 查询
            if (ObjectUtil.isNotEmpty(tbFactoryOrderDetailParam.getColorName())) {
                queryWrapper.lambda().eq(TbFactoryOrderDetail::getColorName, tbFactoryOrderDetailParam.getColorName());
            }
            // 根据乙方 查询
            if (ObjectUtil.isNotEmpty(tbFactoryOrderDetailParam.getSize())) {
                queryWrapper.lambda().eq(TbFactoryOrderDetail::getSize, tbFactoryOrderDetailParam.getSize());
            }
            // 根据数量 查询
            if (ObjectUtil.isNotEmpty(tbFactoryOrderDetailParam.getCount())) {
                queryWrapper.lambda().eq(TbFactoryOrderDetail::getCount, tbFactoryOrderDetailParam.getCount());
            }
            // 根据是否后台创建 查询
            if (ObjectUtil.isNotEmpty(tbFactoryOrderDetailParam.getSyscreated())) {
                queryWrapper.lambda().eq(TbFactoryOrderDetail::getSyscreated, tbFactoryOrderDetailParam.getSyscreated());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbFactoryOrderDetail> list(TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        return this.list();
    }

    @Override
    public void add(TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        TbFactoryOrderDetail tbFactoryOrderDetail = new TbFactoryOrderDetail();
        BeanUtil.copyProperties(tbFactoryOrderDetailParam, tbFactoryOrderDetail);
        this.save(tbFactoryOrderDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbFactoryOrderDetailParam> tbFactoryOrderDetailParamList) {
        tbFactoryOrderDetailParamList.forEach(tbFactoryOrderDetailParam -> {
            this.removeById(tbFactoryOrderDetailParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        TbFactoryOrderDetail tbFactoryOrderDetail = this.queryTbFactoryOrderDetail(tbFactoryOrderDetailParam);
        BeanUtil.copyProperties(tbFactoryOrderDetailParam, tbFactoryOrderDetail);
        this.updateById(tbFactoryOrderDetail);
    }

    @Override
    public TbFactoryOrderDetail detail(TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        return this.queryTbFactoryOrderDetail(tbFactoryOrderDetailParam);
    }

    /**
     * 获取工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    private TbFactoryOrderDetail queryTbFactoryOrderDetail(TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        TbFactoryOrderDetail tbFactoryOrderDetail = this.getById(tbFactoryOrderDetailParam.getId());
        if (ObjectUtil.isNull(tbFactoryOrderDetail)) {
            throw new ServiceException(TbFactoryOrderDetailExceptionEnum.NOT_EXIST);
        }
        return tbFactoryOrderDetail;
    }

    @Override
    public void export(TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        List<TbFactoryOrderDetail> list = this.list(tbFactoryOrderDetailParam);
        PoiUtil.exportExcelWithStream("SnowyTbFactoryOrderDetail.xls", TbFactoryOrderDetail.class, list);
    }

    /**
     * @Description: 查询工单数量明细
     * @author 邾茂星
     * @date 2022/3/14 9:39
     * @param workOrderId
     * @return WorkOrderAmountInfoVO
     */
    @Override
    public WorkOrderAmountInfoVO getAmountInfo(Long workOrderId) {
        WorkOrderAmountInfoVO amountInfoVO = new WorkOrderAmountInfoVO();
        if(ObjectUtil.isNull(workOrderId)){
            return amountInfoVO;
        }
        LambdaQueryWrapper<TbFactoryOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbFactoryOrderDetail::getFactoryId, workOrderId);
        List<TbFactoryOrderDetail> list = this.list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list)){
            List<WorkOrderDetailVO> workOrderDetailVOList = new ArrayList<>();
            Set<String> colorList = new HashSet<>();
            Set<String> sizeList = new HashSet<>();
            list.forEach(detail -> {
                Long colorId = detail.getColorId();
                String size = detail.getSize();
                colorList.add(colorId.toString());
                sizeList.add(size);
                WorkOrderDetailVO workOrderDetailVO = new WorkOrderDetailVO();
                BeanUtil.copyProperties(detail, workOrderDetailVO);
                //已报工数量
                QueryWrapper<TbWorkerorderReportedDetail> qw = new QueryWrapper<>();
                qw.select("sum(count) as count")
                        .eq("work_order_id", workOrderId)
                        .eq("color_id", colorId)
                        .eq("size", size);
                TbWorkerorderReportedDetail reportedDetail = tbWorkerorderReportedDetailService.getOne(qw);
                if(reportedDetail != null){
                    Long count = reportedDetail.getCount();
                    if(count != null){
                        workOrderDetailVO.setProcessedNum(count.intValue());
                    }
                }
                workOrderDetailVOList.add(workOrderDetailVO);
            });
            amountInfoVO.setWorkOrderDetailVOList(workOrderDetailVOList);
            amountInfoVO.setColorList(colorList);
            amountInfoVO.setSizeList(sizeList);
        }
        return amountInfoVO;
    }
}
