
package vip.xiaonuo.main.modular.tbcustomeraccount.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.main.modular.tbcustomeraccount.entity.TbCustomerAccount;

/**
 * 客户账号
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:03
 */
public interface TbCustomerAccountMapper extends BaseMapper<TbCustomerAccount> {

    Page<TbCustomerAccount> findPage(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);
}
