
package vip.xiaonuo.main.modular.company.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.common.consts.CommonConstant;
import vip.xiaonuo.common.enums.CommonStatusEnum;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.company.entity.Company;
import vip.xiaonuo.main.modular.company.enums.CompanyExceptionEnum;
import vip.xiaonuo.main.modular.company.mapper.CompanyMapper;
import vip.xiaonuo.main.modular.company.param.CompanyParam;
import vip.xiaonuo.main.modular.company.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 客户管理service接口实现类
 *
 * @author 邾茂星
 * @date 2021-12-30 20:34:18
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Override
    public PageResult<Company> page(CompanyParam companyParam) {
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(companyParam)) {

            // 根据公司名称 查询
            if (ObjectUtil.isNotEmpty(companyParam.getCompanyName())) {
                queryWrapper.lambda().like(Company::getCompanyName, companyParam.getCompanyName());
            }
            // 根据负责人 查询
            if (ObjectUtil.isNotEmpty(companyParam.getManager())) {
                queryWrapper.lambda().eq(Company::getManager, companyParam.getManager());
            }
            // 根据联系电话 查询
            if (ObjectUtil.isNotEmpty(companyParam.getManagerTel())) {
                queryWrapper.lambda().eq(Company::getManagerTel, companyParam.getManagerTel());
            }
            // 根据备注 查询
            if (ObjectUtil.isNotEmpty(companyParam.getRemark())) {
                queryWrapper.lambda().eq(Company::getRemark, companyParam.getRemark());
            }
            // 根据统一征信代码 查询
            if (ObjectUtil.isNotEmpty(companyParam.getCompanyNo())) {
                queryWrapper.lambda().eq(Company::getCompanyNo, companyParam.getCompanyNo());
            }
            // 根据状态 查询
            if (ObjectUtil.isNotEmpty(companyParam.getStatus())) {
                queryWrapper.lambda().eq(Company::getStatus, companyParam.getStatus());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Company> list(CompanyParam companyParam) {
        return this.list();
    }

    @Override
    public void add(CompanyParam companyParam) {
        Company company = new Company();
        BeanUtil.copyProperties(companyParam, company);
        this.save(company);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CompanyParam> companyParamList) {
        companyParamList.forEach(companyParam -> {
            this.removeById(companyParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CompanyParam companyParam) {
        Company company = this.queryCompany(companyParam);
        BeanUtil.copyProperties(companyParam, company);
        this.updateById(company);
    }

    @Override
    public Company detail(CompanyParam companyParam) {
        return this.queryCompany(companyParam);
    }

    /**
     * 获取客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    private Company queryCompany(CompanyParam companyParam) {
        Company company = this.getById(companyParam.getId());
        if (ObjectUtil.isNull(company)) {
            throw new ServiceException(CompanyExceptionEnum.NOT_EXIST);
        }
        return company;
    }

    @Override
    public void export(CompanyParam companyParam) {
        List<Company> list = this.list(companyParam);
        PoiUtil.exportExcelWithStream("SnowyCompany.xls", Company.class, list);
    }

}
