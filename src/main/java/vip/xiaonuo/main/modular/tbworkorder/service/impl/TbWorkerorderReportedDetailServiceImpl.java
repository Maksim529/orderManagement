
package vip.xiaonuo.main.modular.tbworkorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkerorderReportedDetail;
import vip.xiaonuo.main.modular.tbworkorder.enums.TbWorkerorderReportedDetailExceptionEnum;
import vip.xiaonuo.main.modular.tbworkorder.mapper.TbWorkerorderReportedDetailMapper;
import vip.xiaonuo.main.modular.tbworkorder.param.TbWorkerorderReportedDetailParam;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkerorderReportedDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报工明细service接口实现类
 *
 * @author xwx
 * @date 2022-03-14 14:23:40
 */
@Service
public class TbWorkerorderReportedDetailServiceImpl extends ServiceImpl<TbWorkerorderReportedDetailMapper, TbWorkerorderReportedDetail> implements TbWorkerorderReportedDetailService {

    @Override
    public PageResult<TbWorkerorderReportedDetail> page(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam) {
        QueryWrapper<TbWorkerorderReportedDetail> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbWorkerorderReportedDetailParam)) {

            // 根据工单id 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedDetailParam.getWorkOrderId())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedDetail::getWorkOrderId, tbWorkerorderReportedDetailParam.getWorkOrderId());
            }
            // 根据报工id 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedDetailParam.getReportedId())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedDetail::getReportedId, tbWorkerorderReportedDetailParam.getReportedId());
            }
            // 根据颜色id 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedDetailParam.getColorId())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedDetail::getColorId, tbWorkerorderReportedDetailParam.getColorId());
            }
            // 根据颜色名 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedDetailParam.getColorName())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedDetail::getColorName, tbWorkerorderReportedDetailParam.getColorName());
            }
            // 根据尺码 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedDetailParam.getSize())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedDetail::getSize, tbWorkerorderReportedDetailParam.getSize());
            }
            // 根据数量 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderReportedDetailParam.getCount())) {
                queryWrapper.lambda().eq(TbWorkerorderReportedDetail::getCount, tbWorkerorderReportedDetailParam.getCount());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbWorkerorderReportedDetail> list(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam) {
        return this.list();
    }

    @Override
    public void add(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam) {
        TbWorkerorderReportedDetail tbWorkerorderReportedDetail = new TbWorkerorderReportedDetail();
        BeanUtil.copyProperties(tbWorkerorderReportedDetailParam, tbWorkerorderReportedDetail);
        this.save(tbWorkerorderReportedDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbWorkerorderReportedDetailParam> tbWorkerorderReportedDetailParamList) {
        tbWorkerorderReportedDetailParamList.forEach(tbWorkerorderReportedDetailParam -> {
            this.removeById(tbWorkerorderReportedDetailParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam) {
        TbWorkerorderReportedDetail tbWorkerorderReportedDetail = this.queryTbWorkerorderReportedDetail(tbWorkerorderReportedDetailParam);
        BeanUtil.copyProperties(tbWorkerorderReportedDetailParam, tbWorkerorderReportedDetail);
        this.updateById(tbWorkerorderReportedDetail);
    }

    @Override
    public TbWorkerorderReportedDetail detail(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam) {
        return this.queryTbWorkerorderReportedDetail(tbWorkerorderReportedDetailParam);
    }

    /**
     * 获取报工明细
     *
     * @author xwx
     * @date 2022-03-14 14:23:40
     */
    private TbWorkerorderReportedDetail queryTbWorkerorderReportedDetail(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam) {
        TbWorkerorderReportedDetail tbWorkerorderReportedDetail = this.getById(tbWorkerorderReportedDetailParam.getId());
        if (ObjectUtil.isNull(tbWorkerorderReportedDetail)) {
            throw new ServiceException(TbWorkerorderReportedDetailExceptionEnum.NOT_EXIST);
        }
        return tbWorkerorderReportedDetail;
    }

    @Override
    public void export(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam) {
        List<TbWorkerorderReportedDetail> list = this.list(tbWorkerorderReportedDetailParam);
        PoiUtil.exportExcelWithStream("SnowyTbWorkerorderReportedDetail.xls", TbWorkerorderReportedDetail.class, list);
    }

}
