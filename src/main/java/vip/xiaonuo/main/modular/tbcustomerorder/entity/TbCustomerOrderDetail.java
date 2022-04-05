
package vip.xiaonuo.main.modular.tbcustomerorder.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;

/**
 * 订单明细
 *
 * @author 邾茂星
 * @date 2022-01-12 10:09:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_customer_order_detail")
public class TbCustomerOrderDetail extends BaseEntity {

    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单id
     */
    @Excel(name = "订单id")
    private Long orderId;

    /**
     * 颜色
     */
    @Excel(name = "颜色")
    private Long colorId;

    /**
     * 尺码
     */
    @Excel(name = "尺码")
    private String size;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Long count;

    @Excel(name = "颜色")
    private String colorName;

    @Excel(name = "已派放量")
    private Long pushCount;

    @Excel(name = "状态")
    private Integer status;

}
