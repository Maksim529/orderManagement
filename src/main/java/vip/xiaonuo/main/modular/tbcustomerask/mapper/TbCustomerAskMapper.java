
package vip.xiaonuo.main.modular.tbcustomerask.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.main.modular.tbcustomerask.entity.TbCustomerAsk;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.CustomerOrderRollInfoVO;

import java.util.List;

/**
 * 客户询价单
 *
 * @author 邾茂星
 * @date 2022-01-10 15:02:32
 */
public interface TbCustomerAskMapper extends BaseMapper<TbCustomerAsk> {

//    List<TbCustomerAskVO> customerAskList(@Param("entity") TbCustomerAskParam tbCustomerAskParam);

    /**
     * @param queryWrapper
     * @return List<CustomerOrderRollInfoVO>
     * @Description: 询价单滚动展示
     * @author 邾茂星
     * @date 2022/1/18 9:31
     */
    List<CustomerOrderRollInfoVO> findRollInfo(@Param("ew") QueryWrapper queryWrapper);

    /**
     * @param queryWrapper
     * @return int
     * @Description: 统计数量
     * @author 邾茂星
     * @date 2022/3/24 14:03
     */
    int countNumber(@Param("ew") QueryWrapper queryWrapper);

    /**
     * @Description: 分页查询
     * @author 邾茂星
     * @date 2022/3/24 15:37
     * @param page
     * @param queryWrapper
     * @return Page<TbCustomerAsk>
     */
    Page<TbCustomerAsk> findPage(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);
}
