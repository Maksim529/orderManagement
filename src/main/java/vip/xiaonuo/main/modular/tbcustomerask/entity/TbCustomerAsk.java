
package vip.xiaonuo.main.modular.tbcustomerask.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户询价单
 *
 * @author 邾茂星
 * @date 2022-01-10 16:16:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_customer_ask")
public class TbCustomerAsk extends BaseEntity {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 询价单号
     */
    @Excel(name = "询价单号")
    private String askOrderNo;

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
     * 状态（1、CMT2、FOB）
     */
    @Excel(name = "状态（1、CMT2、FOB）")
    private Integer produceType;

    /**
     * 颜色
     */
    @Excel(name = "颜色")
    private Long color;

    /**
     * 尺码（字典枚举）
     */
    @Excel(name = "尺码（字典枚举）")
    private Long size;

    /**
     * 单位（字典枚举）
     */
    @Excel(name = "单位（字典枚举）")
    private Long unit;

    /**
     * 价格
     */
    @Excel(name = "价格")
    private BigDecimal price;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态 未报价1000 已报价2000")
    private Integer askStatus;

    /**
     * 处理时间
     */
    @Excel(name = "处理时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date processTime;

    /**
     * 处理人
     */
    @Excel(name = "处理人")
    private Long processUser;

    @Excel(name = "处理人名称")
    private String processUserName;

    /**
     * 订单数量
     */
    @Excel(name = "订单数量")
    private Long counts;

    /**
     * 期望交期
     */
    @Excel(name = "期望交期", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date givedate;

    /**
     * 期望交期
     */
    @Excel(name = "期望交期", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date materialReachDate;

    /**
     * 收货地址
     */
    @Excel(name = "收货地址")
    private String receiveAddr;

    /**
     * 其他要求备注
     */
    @Excel(name = "其他要求备注")
    private String note;

    /**
     * 所属客户
     */
    @Excel(name = "账号id")
    private Long accountId;

    /**
     * 是否后台创建
     */
    @Excel(name = "是否后台创建")
    private Integer syscreated;

    /**
     * 
     */
    @Excel(name = "报价次数")
    private Integer servicePriceTime;

    /**
     * 客户账号名称
     */
    @TableField(exist = false)
    private String customerAccountName;


}
