
package vip.xiaonuo.main.modular.tbfactoryorderdetail.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;

/**
 * 工单明细
 *
 * @author wjc
 * @date 2022-01-13 13:35:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_factory_order_detail")
public class TbFactoryOrderDetail extends BaseEntity {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单id
     */
    @Excel(name = "订单id")
    private Long factoryId;

    /**
     * 颜色
     */
    @Excel(name = "颜色")
    private Long colorId;

    /**
     * 颜色名
     */
    @Excel(name = "颜色名")
    private String colorName;

    /**
     * 乙方
     */
    @Excel(name = "乙方")
    private String size;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Long count;

    /**
     * 是否后台创建
     */
    @Excel(name = "是否后台创建")
    private Integer syscreated;

}
