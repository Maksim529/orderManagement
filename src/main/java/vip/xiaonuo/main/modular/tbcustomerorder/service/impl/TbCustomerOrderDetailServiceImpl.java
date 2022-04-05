
package vip.xiaonuo.main.modular.tbcustomerorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrderDetail;
import vip.xiaonuo.main.modular.tbcustomerorder.enums.TbCustomerOrderDetailExceptionEnum;
import vip.xiaonuo.main.modular.tbcustomerorder.mapper.TbCustomerOrderDetailMapper;
import vip.xiaonuo.main.modular.tbcustomerorder.param.TbCustomerOrderDetailParam;
import vip.xiaonuo.main.modular.tbcustomerorder.service.TbCustomerOrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.sys.modular.dict.entity.SysDictData;
import vip.xiaonuo.sys.modular.dict.service.SysDictDataService;

import java.util.List;

/**
 * 订单明细service接口实现类
 *
 * @author 邾茂星
 * @date 2022-01-12 10:09:05
 */
@Service
public class TbCustomerOrderDetailServiceImpl extends ServiceImpl<TbCustomerOrderDetailMapper, TbCustomerOrderDetail> implements TbCustomerOrderDetailService {
    @Autowired
    private SysDictDataService sysDictDataService;

    @Override
    public PageResult<TbCustomerOrderDetail> page(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        QueryWrapper<TbCustomerOrderDetail> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerOrderDetailParam)) {

            // 根据订单id 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderDetailParam.getOrderId())) {
                queryWrapper.lambda().eq(TbCustomerOrderDetail::getOrderId, tbCustomerOrderDetailParam.getOrderId());
            }
            // 根据颜色 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderDetailParam.getColorId())) {
                queryWrapper.lambda().eq(TbCustomerOrderDetail::getColorId, tbCustomerOrderDetailParam.getColorId());
            }
            // 根据尺码 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderDetailParam.getSize())) {
                queryWrapper.lambda().eq(TbCustomerOrderDetail::getSize, tbCustomerOrderDetailParam.getSize());
            }
            // 根据数量 查询
            if (ObjectUtil.isNotEmpty(tbCustomerOrderDetailParam.getCount())) {
                queryWrapper.lambda().eq(TbCustomerOrderDetail::getCount, tbCustomerOrderDetailParam.getCount());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbCustomerOrderDetail> list(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        LambdaQueryWrapper<TbCustomerOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tbCustomerOrderDetailParam.getOrderId())){
            queryWrapper.eq(TbCustomerOrderDetail::getOrderId, tbCustomerOrderDetailParam.getOrderId());
        }
        if(ObjectUtil.isNotEmpty(tbCustomerOrderDetailParam.getSearchStatus())){
            queryWrapper.eq(TbCustomerOrderDetail::getStatus, tbCustomerOrderDetailParam.getSearchStatus());
        }
        return this.list(queryWrapper);
    }

    @Override
    public void add(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        setColorName(tbCustomerOrderDetailParam);
        TbCustomerOrderDetail tbCustomerOrderDetail = new TbCustomerOrderDetail();
        BeanUtil.copyProperties(tbCustomerOrderDetailParam, tbCustomerOrderDetail);
        this.save(tbCustomerOrderDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbCustomerOrderDetailParam> tbCustomerOrderDetailParamList) {
        tbCustomerOrderDetailParamList.forEach(tbCustomerOrderDetailParam -> {
            this.removeById(tbCustomerOrderDetailParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        setColorName(tbCustomerOrderDetailParam);
        TbCustomerOrderDetail tbCustomerOrderDetail = this.queryTbCustomerOrderDetail(tbCustomerOrderDetailParam);
        BeanUtil.copyProperties(tbCustomerOrderDetailParam, tbCustomerOrderDetail);
        this.updateById(tbCustomerOrderDetail);
    }

    @Override
    public TbCustomerOrderDetail detail(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        return this.queryTbCustomerOrderDetail(tbCustomerOrderDetailParam);
    }

    /**
     * 获取订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    private TbCustomerOrderDetail queryTbCustomerOrderDetail(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        TbCustomerOrderDetail tbCustomerOrderDetail = this.getById(tbCustomerOrderDetailParam.getId());
        if (ObjectUtil.isNull(tbCustomerOrderDetail)) {
            throw new ServiceException(TbCustomerOrderDetailExceptionEnum.NOT_EXIST);
        }
        return tbCustomerOrderDetail;
    }

    @Override
    public void export(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        List<TbCustomerOrderDetail> list = this.list(tbCustomerOrderDetailParam);
        PoiUtil.exportExcelWithStream("SnowyTbCustomerOrderDetail.xls", TbCustomerOrderDetail.class, list);
    }

    @Override
    public void save(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        if(tbCustomerOrderDetailParam != null){
            Long count = tbCustomerOrderDetailParam.getCount();
            if(ObjectUtil.isNotNull(count) && count > 0L){
                setColorName(tbCustomerOrderDetailParam);
                Long id = tbCustomerOrderDetailParam.getId();
                if(id == null){
                    //添加
                    this.add(tbCustomerOrderDetailParam);
                }else {
                    //编辑
                    this.edit(tbCustomerOrderDetailParam);
                }
            }
        }
    }

    /**
     * @Description: 设置颜色名称
     * @author 邾茂星
     * @date 2022/1/13 11:01
     * @param tbCustomerOrderDetailParam
     * @return TbCustomerOrderDetailParam
     */
    private TbCustomerOrderDetailParam setColorName(TbCustomerOrderDetailParam tbCustomerOrderDetailParam){
        if(tbCustomerOrderDetailParam != null){
            Long colorId = tbCustomerOrderDetailParam.getColorId();
            if(colorId != null){
                SysDictData sysDictData = sysDictDataService.getById(colorId);
                if(sysDictData != null){
                    tbCustomerOrderDetailParam.setColorName(sysDictData.getValue());
                }
            }
        }
        return tbCustomerOrderDetailParam;
    }
}
