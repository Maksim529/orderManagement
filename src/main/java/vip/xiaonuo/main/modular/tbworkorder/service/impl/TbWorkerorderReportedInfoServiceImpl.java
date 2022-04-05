
package vip.xiaonuo.main.modular.tbworkorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonStatusEnum;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkerorderReportedDetail;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkerorderReportedInfo;
import vip.xiaonuo.main.modular.tbworkorder.enums.TbWorkerorderReportedInfoExceptionEnum;
import vip.xiaonuo.main.modular.tbworkorder.mapper.TbWorkerorderReportedInfoMapper;
import vip.xiaonuo.main.modular.tbworkorder.param.TbWorkerorderReportedInfoParam;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkerorderReportedDetailService;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkerorderReportedInfoService;
import vip.xiaonuo.main.modular.tbworkorder.vo.WorkOrderReportedDetailVO;
import vip.xiaonuo.main.modular.tbworkorder.vo.WorkOrderReportedInfoVO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 报工记录service接口实现类
 *
 * @author xwx
 * @date 2022-03-14 14:10:21
 */
@Service
public class TbWorkerorderReportedInfoServiceImpl extends ServiceImpl<TbWorkerorderReportedInfoMapper, TbWorkerorderReportedInfo> implements TbWorkerorderReportedInfoService {
    @Autowired
    private TbWorkerorderReportedDetailService tbWorkerorderReportedDetailService;

    @Override
    public PageResult<TbWorkerorderReportedInfo> page(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam) {
        QueryWrapper<TbWorkerorderReportedInfo> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbWorkerorderReportedInfoParam)) {

            // 根据工单id 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedInfoParam.getWorkOrderId())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedInfo::getWorkOrderId, tbWorkerorderReportedInfoParam.getWorkOrderId());
            }
            // 根据报工类型 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedInfoParam.getType())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedInfo::getType, tbWorkerorderReportedInfoParam.getType());
            }
            // 根据报工类型名称 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedInfoParam.getTypeName())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedInfo::getTypeName, tbWorkerorderReportedInfoParam.getTypeName());
            }
            // 根据提交数量 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedInfoParam.getSubmitNum())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedInfo::getSubmitNum, tbWorkerorderReportedInfoParam.getSubmitNum());
            }
            // 根据提交时间 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedInfoParam.getSubmitDate())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedInfo::getSubmitDate, tbWorkerorderReportedInfoParam.getSubmitDate());
            }
            // 根据状态 0=正常 1=暂停 2=删除 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedInfoParam.getStatus())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedInfo::getStatus, tbWorkerorderReportedInfoParam.getStatus());
            }
            // 根据备注 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedInfoParam.getRemark())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedInfo::getRemark, tbWorkerorderReportedInfoParam.getRemark());
            }
            // 根据创建人名称 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedInfoParam.getCreateUserName())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedInfo::getCreateUserName, tbWorkerorderReportedInfoParam.getCreateUserName());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbWorkerorderReportedInfo> list(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam) {
        return this.list();
    }

    @Override
    public void add(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam) {
        TbWorkerorderReportedInfo tbWorkerorderReportedInfo = new TbWorkerorderReportedInfo();
        BeanUtil.copyProperties(tbWorkerorderReportedInfoParam, tbWorkerorderReportedInfo);
        this.save(tbWorkerorderReportedInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbWorkerorderReportedInfoParam> tbWorkerorderReportedInfoParamList) {
        tbWorkerorderReportedInfoParamList.forEach(tbWorkerorderReportedInfoParam -> {
            this.removeById(tbWorkerorderReportedInfoParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam) {
        TbWorkerorderReportedInfo tbWorkerorderReportedInfo = this.queryTbWorkerorderReportedInfo(tbWorkerorderReportedInfoParam);
        BeanUtil.copyProperties(tbWorkerorderReportedInfoParam, tbWorkerorderReportedInfo);
        this.updateById(tbWorkerorderReportedInfo);
    }

    @Override
    public TbWorkerorderReportedInfo detail(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam) {
        return this.queryTbWorkerorderReportedInfo(tbWorkerorderReportedInfoParam);
    }

    /**
     * 获取报工记录
     *
     * @author xwx
     * @date 2022-03-14 14:10:21
     */
    private TbWorkerorderReportedInfo queryTbWorkerorderReportedInfo(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam) {
        TbWorkerorderReportedInfo tbWorkerorderReportedInfo = this.getById(tbWorkerorderReportedInfoParam.getId());
        if (ObjectUtil.isNull(tbWorkerorderReportedInfo)) {
            throw new ServiceException(TbWorkerorderReportedInfoExceptionEnum.NOT_EXIST);
        }
        return tbWorkerorderReportedInfo;
    }

    @Override
    public void export(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam) {
        List<TbWorkerorderReportedInfo> list = this.list(tbWorkerorderReportedInfoParam);
        PoiUtil.exportExcelWithStream("SnowyTbWorkerorderReportedInfo.xls", TbWorkerorderReportedInfo.class, list);
    }

    @Override
    public List<WorkOrderReportedInfoVO> getReportedInfoList(Long workOrderId, Integer type) {
        List<WorkOrderReportedInfoVO> voList = new LinkedList<>();
        LambdaQueryWrapper<TbWorkerorderReportedInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TbWorkerorderReportedInfo::getTypeName, TbWorkerorderReportedInfo::getCreateUserName,
                TbWorkerorderReportedInfo::getSubmitDate, TbWorkerorderReportedInfo::getSubmitNum,
                TbWorkerorderReportedInfo::getId);
        queryWrapper.eq(TbWorkerorderReportedInfo::getWorkOrderId, workOrderId)
                .eq(TbWorkerorderReportedInfo::getStatus, CommonStatusEnum.ENABLE);
        if (type != null && type != 0) {
            queryWrapper.eq(TbWorkerorderReportedInfo::getType, type);
        }
        queryWrapper.orderByAsc(TbWorkerorderReportedInfo::getId);
        List<TbWorkerorderReportedInfo> list = baseMapper.selectList(queryWrapper);
        if (ObjectUtil.isNotEmpty(list)) {
            list.forEach(info -> {
                Long id = info.getId();
                WorkOrderReportedInfoVO vo = new WorkOrderReportedInfoVO();
                BeanUtil.copyProperties(info, vo);
                //查询报工明细
                LambdaQueryWrapper<TbWorkerorderReportedDetail> qw = new LambdaQueryWrapper<>();
                qw.eq(TbWorkerorderReportedDetail::getReportedId, id)
                        .ne(TbWorkerorderReportedDetail::getCount, 0);
                qw.orderByAsc(TbWorkerorderReportedDetail::getId);
                List<TbWorkerorderReportedDetail> reportedDetails = tbWorkerorderReportedDetailService.list(qw);
                if (ObjectUtil.isNotEmpty(reportedDetails)) {
                    List<WorkOrderReportedDetailVO> detailList = new ArrayList<>();
                    reportedDetails.forEach(detail -> {
                        WorkOrderReportedDetailVO workOrderReportedDetailVO = new WorkOrderReportedDetailVO();
                        BeanUtil.copyProperties(detail, workOrderReportedDetailVO);
                        detailList.add(workOrderReportedDetailVO);
                    });
                    vo.setDetailList(detailList);
                }
                voList.add(vo);
            });
        }
        return voList;
    }
}
