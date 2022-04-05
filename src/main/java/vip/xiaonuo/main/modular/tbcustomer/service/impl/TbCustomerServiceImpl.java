
package vip.xiaonuo.main.modular.tbcustomer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.tbcustomer.entity.TbCustomer;
import vip.xiaonuo.main.modular.tbcustomer.enums.TbCustomerExceptionEnum;
import vip.xiaonuo.main.modular.tbcustomer.mapper.TbCustomerMapper;
import vip.xiaonuo.main.modular.tbcustomer.param.TbCustomerParam;
import vip.xiaonuo.main.modular.tbcustomer.service.TbCustomerService;
import vip.xiaonuo.main.modular.tbcustomeraccount.entity.TbCustomerAccount;

import java.util.List;

/**
 * 客户service接口实现类
 *
 * @author 邾茂星
 * @date 2022-01-14 08:42:18
 */
@Service
public class TbCustomerServiceImpl extends ServiceImpl<TbCustomerMapper, TbCustomer> implements TbCustomerService {

    @Override
    public PageResult<TbCustomer> page(TbCustomerParam tbCustomerParam) {
        LambdaQueryWrapper<TbCustomer> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerParam)) {

            // 根据工厂名 查询
            if (ObjectUtil.isNotEmpty(tbCustomerParam.getName())) {
                queryWrapper.like(TbCustomer::getName, tbCustomerParam.getName());
            }
            // 根据联系人 查询
            if (ObjectUtil.isNotEmpty(tbCustomerParam.getOwner())) {
                queryWrapper.like(TbCustomer::getOwner, tbCustomerParam.getOwner());
            }
            // 根据联系人电话 查询
            if (ObjectUtil.isNotEmpty(tbCustomerParam.getOwnerTel())) {
                queryWrapper.like(TbCustomer::getOwnerTel, tbCustomerParam.getOwnerTel());
            }
            // 根据公司代码 查询
            if (ObjectUtil.isNotEmpty(tbCustomerParam.getCode())) {
                queryWrapper.like(TbCustomer::getCode, tbCustomerParam.getCode());
            }
            // 根据是否后台创建 查询
            if (ObjectUtil.isNotEmpty(tbCustomerParam.getSyscreated())) {
                queryWrapper.eq(TbCustomer::getSyscreated, tbCustomerParam.getSyscreated());
            }
            // 根据状态（1、合作中 2、停止合作 3、潜在客户） 查询
            if (ObjectUtil.isNotEmpty(tbCustomerParam.getStatus())) {
                queryWrapper.eq(TbCustomer::getStatus, tbCustomerParam.getStatus());
            }
        }
        queryWrapper.orderByDesc(TbCustomer::getId);
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbCustomer> list(TbCustomerParam tbCustomerParam) {
        return this.list();
    }

    @Override
    public void add(TbCustomerParam tbCustomerParam) {
        TbCustomer tbCustomer = new TbCustomer();
        BeanUtil.copyProperties(tbCustomerParam, tbCustomer);
        this.save(tbCustomer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbCustomerParam> tbCustomerParamList) {
        tbCustomerParamList.forEach(tbCustomerParam -> {
            this.removeById(tbCustomerParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbCustomerParam tbCustomerParam) {
        TbCustomer tbCustomer = this.queryTbCustomer(tbCustomerParam);
        BeanUtil.copyProperties(tbCustomerParam, tbCustomer);
        this.updateById(tbCustomer);
    }

    @Override
    public TbCustomer detail(TbCustomerParam tbCustomerParam) {
        return this.queryTbCustomer(tbCustomerParam);
    }

    /**
     * 获取客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    private TbCustomer queryTbCustomer(TbCustomerParam tbCustomerParam) {
        TbCustomer tbCustomer = this.getById(tbCustomerParam.getId());
        if (ObjectUtil.isNull(tbCustomer)) {
            throw new ServiceException(TbCustomerExceptionEnum.NOT_EXIST);
        }
        return tbCustomer;
    }

    @Override
    public void export(TbCustomerParam tbCustomerParam) {
        List<TbCustomer> list = this.list(tbCustomerParam);
        PoiUtil.exportExcelWithStream("SnowyTbCustomer.xls", TbCustomer.class, list);
    }

}
