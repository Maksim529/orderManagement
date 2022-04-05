
package vip.xiaonuo.main.modular.company.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.company.entity.Company;
import vip.xiaonuo.main.modular.company.param.CompanyParam;
import java.util.List;

/**
 * 客户管理service接口
 *
 * @author 邾茂星
 * @date 2021-12-30 20:34:18
 */
public interface CompanyService extends IService<Company> {

    /**
     * 查询客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    PageResult<Company> page(CompanyParam companyParam);

    /**
     * 客户管理列表
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    List<Company> list(CompanyParam companyParam);

    /**
     * 添加客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    void add(CompanyParam companyParam);

    /**
     * 删除客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    void delete(List<CompanyParam> companyParamList);

    /**
     * 编辑客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    void edit(CompanyParam companyParam);

    /**
     * 查看客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
     Company detail(CompanyParam companyParam);

    /**
     * 导出客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
     void export(CompanyParam companyParam);

}
