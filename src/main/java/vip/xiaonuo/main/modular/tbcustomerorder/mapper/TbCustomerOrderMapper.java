
package vip.xiaonuo.main.modular.tbcustomerorder.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.main.modular.sysConfig.entity.ProductType;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrder;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.CustomerOrderExportVO;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.CustomerOrderRollInfoVO;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.TbCustomerOrderContractVO;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.TbCustomerOrderVO;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.vo.WorkOrderDetailVO;

import java.util.List;

/**
 * 生产订单
 *
 * @author 邾茂星
 * @date 2022-01-12 08:59:51
 */
public interface TbCustomerOrderMapper extends BaseMapper<TbCustomerOrder> {

    /**
     * 获取分页列表
     *
     * @param page         分页参数
     * @param queryWrapper 查询参数
     */
    Page<TbCustomerOrderVO> findPage(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);

    /**
     * @Description: 订单滚动展示
     * @author 邾茂星
     * @date 2022/1/17 13:25
     * @param queryWrapper
     * @return List<CustomerOrderRollInfoVO>
     */
    List<CustomerOrderRollInfoVO> findRollInfo(@Param("ew") QueryWrapper queryWrapper);

    /**
     * @Description: 销售订单合同 分页列表
     * @author 邾茂星
     * @date 2022/1/19 11:09
     * @param page
     * @param queryWrapper
     * @return Page<TbCustomerOrderContractVO>
     */
    Page<TbCustomerOrderContractVO> salesContractPage(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);

    /**
     * @Description: 导出Excel
     * @author 邾茂星
     * @date 2022/1/22 10:47
     * @param queryWrapper
     * @return List<CustomerOrderExportVO>
     */
    List<CustomerOrderExportVO> export(@Param("ew") QueryWrapper queryWrapper);

    /**
     * @Description: 查询订单，裁切数量
     * @author 邾茂星
     * @date 2022/3/15 16:23
     * @param queryWrapper
     * @return List<WorkOrderDetailVO>
     */
    List<WorkOrderDetailVO> getTailorNum(@Param("ew") QueryWrapper queryWrapper);

    /**
     * @Description: 统计数量
     * @author 邾茂星
     * @date 2022/3/25 14:04
     * @param queryWrapper
     * @return int
     */
    int countNumber(@Param("ew") QueryWrapper queryWrapper);

    /**
     * @Description: 查询工单状态（最慢）
     * @author 邾茂星
     * @date 2022/3/28 14:11
     * @param id
     * @return String
     */
    String getWorkOrderStatusName(Long id);

    int countFileNumber(@Param("ew") QueryWrapper queryWrapper);

    List<String> orderFileUrlList(@Param("ew") QueryWrapper queryWrapper);
}
