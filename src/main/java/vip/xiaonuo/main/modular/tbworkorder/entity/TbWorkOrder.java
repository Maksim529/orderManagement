
package vip.xiaonuo.main.modular.tbworkorder.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工厂工单
 *
 * @author wjc
 * @date 2022-01-13 14:29:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_work_order")
public class TbWorkOrder extends BaseEntity {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private Long orderId;

    /**
     * 工单号
     */
    @Excel(name = "工单号")
    private String workOrderNo;

    /**
     * 客户款号
     */
    @Excel(name = "客户款号")
    private String customerSku;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String pic;

    /**
     * 品类
     */
    @Excel(name = "品类")
    private Long category;

    /**
     * 生产类型（1、CMT2、FOB）
     */
    @Excel(name = "生产类型（1、CMT2、FOB）")
    private Integer produceType;

    /**
     * 颜色
     */
    @Excel(name = "颜色")
    private Integer color;

    /**
     * 尺码（字典枚举）
     */
    @Excel(name = "尺码（字典枚举）")
    private String size;

    /**
     * 单位（字典枚举）
     */
    @Excel(name = "单位（字典枚举）")
    private Integer unit;

    /**
     * 价格
     */
    @Excel(name = "价格")
    private BigDecimal price;

    /**
     * 交期
     */
    @Excel(name = "交期", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date givedate;

    /**
     * 单量
     */
    @Excel(name = "单量")
    private Long orderCounts;

    /**
     * 收货地址
     */
    @Excel(name = "收货地址")
    private String receiveAddr;

    /**
     * 所属客户
     */
    @Excel(name = "所属客户")
    private Long factoryrId;

    /**
     * 
     */
    @Excel(name = "")
    private Integer syscreated;

    /**
     * 0 未结单，1 已结单
     */
    @Excel(name = "0 未结单，1 已结单")
    private Integer iscomplted;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Long confirmUser;

    /**
     * 创建人(默认管理员创建)
     */
    @Excel(name = "创建人(默认管理员创建)", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date confirmTime;

    /**
     * 跟单员
     */
    @Excel(name = "跟单员")
    private String merchandiser;

    /**
     * 跟单员id
     */
    @Excel(name = "跟单员id")
    private Long merchandiserId;

    /**
     * 工单状态(1、未齐套、2、已裁剪、3、一裁剪未缝制 4、已缝制未后整 5、未质检 6、已质检未发货  7、已发货  8、已结单)
     */
    @Excel(name = "工单状态")
    private Integer status;

}
