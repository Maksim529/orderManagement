
package vip.xiaonuo.main.modular.tbcustomer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbcustomer.entity.TbCustomer;
import vip.xiaonuo.main.modular.tbcustomer.param.TbCustomerParam;
import java.util.List;

/**
 * 客户service接口
 *
 * @author 邾茂星
 * @date 2022-01-14 08:42:18
 */
public interface TbCustomerService extends IService<TbCustomer> {

    /**
     * 查询客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    PageResult<TbCustomer> page(TbCustomerParam tbCustomerParam);

    /**
     * 客户列表
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    List<TbCustomer> list(TbCustomerParam tbCustomerParam);

    /**
     * 添加客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    void add(TbCustomerParam tbCustomerParam);

    /**
     * 删除客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    void delete(List<TbCustomerParam> tbCustomerParamList);

    /**
     * 编辑客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    void edit(TbCustomerParam tbCustomerParam);

    /**
     * 查看客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
     TbCustomer detail(TbCustomerParam tbCustomerParam);

    /**
     * 导出客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
     void export(TbCustomerParam tbCustomerParam);

}
