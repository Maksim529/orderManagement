
package vip.xiaonuo.main.modular.tbcustomeraccount.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbcustomeraccount.entity.TbCustomerAccount;
import vip.xiaonuo.main.modular.tbcustomeraccount.param.TbCustomerAccountParam;

import java.util.List;

/**
 * 客户账号service接口
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:03
 */
public interface TbCustomerAccountService extends IService<TbCustomerAccount> {

    /**
     * 查询客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    PageResult<TbCustomerAccount> page(TbCustomerAccountParam tbCustomerAccountParam);


    /**
     * 模糊查询
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */

    PageResult<TbCustomerAccount> search(TbCustomerAccountParam tbCustomerAccountParam);

    /**
     * 客户账号列表
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    List<TbCustomerAccount> list(TbCustomerAccountParam tbCustomerAccountParam);

    /**
     * 添加客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    void add(TbCustomerAccountParam tbCustomerAccountParam);

    /**
     * 删除客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    void delete(List<TbCustomerAccountParam> tbCustomerAccountParamList);

    /**
     * 编辑客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    void edit(TbCustomerAccountParam tbCustomerAccountParam);

    /**
     * 查看客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    TbCustomerAccount detail(TbCustomerAccountParam tbCustomerAccountParam);

    /**
     * 导出客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    void export(TbCustomerAccountParam tbCustomerAccountParam);

    /**
     * @return String
     * @Description: 创建code
     * @author 邾茂星
     * @date 2022/3/8 9:44
     */
    String createCustomerCode();

    /**
     * @param accountId
     * @return Long
     * @Description: 根据账号id，查询所属公司id
     * @author 邾茂星
     * @date 2022/3/24 15:27
     */
    Long getCustomerIdByAccountId(Long accountId);

    /**
     * @param id
     * @return TbCustomerAccount
     * @Description: id 查询
     * @author 邾茂星
     * @date 2022/3/30 16:44
     */
    TbCustomerAccount getById(Long id);

    /**
     * @param id
     * @Description: 清除RedisKey
     * @author 邾茂星
     * @date 2022/4/1 12:20
     */
    boolean delRedisKey(Long id);

    List<TbCustomerAccount> getByCustomerId(Long customerId);
}
