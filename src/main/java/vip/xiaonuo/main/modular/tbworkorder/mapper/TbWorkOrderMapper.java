
package vip.xiaonuo.main.modular.tbworkorder.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.main.modular.dingding.VO.DingOrderVO;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkOrder;
import vip.xiaonuo.main.modular.tbworkorder.vo.TbWorkOrderInfoVO;
import vip.xiaonuo.main.modular.tbworkorder.vo.TbWorkOrderVO;

import java.util.List;

/**
 * 工厂工单
 *
 * @author wjc
 * @date 2022-01-13 14:29:32
 */
public interface TbWorkOrderMapper extends BaseMapper<TbWorkOrder> {

    /**
     * 获取分页列表
     *
     * @param queryWrapper 查询参数
     */
    List<TbWorkOrderVO> findVOs(@Param("ew") QueryWrapper queryWrapper);


    /**
     * 根据跟单员以及跟单数量查询工单信息分页
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<DingOrderVO> pageByStatus(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);

    /**
     * @Description: 查询工单信息
     * @author 邾茂星
     * @date 2022/1/19 15:28
     * @param queryWrapper
     * @return List<TbWorkOrderInfoVO>
     */
    Page<TbWorkOrderInfoVO> searchOrder(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);
}
