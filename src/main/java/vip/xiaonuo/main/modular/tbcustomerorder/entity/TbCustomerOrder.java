
package vip.xiaonuo.main.modular.tbcustomerorder.entity;

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
 * 生产订单
 *
 * @author 邾茂星
 * @date 2022-01-12 08:59:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_customer_order")
public class TbCustomerOrder extends BaseEntity {

    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 所属客户
     */
    @Excel(name = "所属客户")
    private Long customerId;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderNo;

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
     * 生产类型（1=FOB  2=CMT）
     */
    @Excel(name = "生产类型（1=FOB  2=CMT）")
    private Long produceType;

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
     * 交期
     */
    @Excel(name = "交期", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date givedate;

    /**
     * 收货地址
     */
    @Excel(name = "收货地址")
    private String receiveAddr;

    @Excel(name = "收货电话")
    private String receivePhone;

    @Excel(name = "收货人")
    private String receivePerson;

    /**
     * 省市区
     */
    @Excel(name = "省市区")
    private String areacode;

    /**
     * 总数量
     */
    @Excel(name = "总数量")
    private Integer amount;

    /**
     * 是否后台创建 0=小程序 1=PC
     */
    @Excel(name = "是否后台创建 0=小程序 1=PC")
    private Integer syscreated;

    /**
     * 订单类型
     */
    @Excel(name = "订单类型")
    private Integer orderType;

    /**
     * 询价单_id
     */
    @Excel(name = "询价单_id")
    private Long askId;

    /**
     * 接单负责人id
     */
    @Excel(name = "接单负责人id")
    private Long principal;

    @Excel(name = "接单负责人")
    private String principalName;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    @Excel(name = "备注")
    private String remark;

    /**
     * 分配状态(0.未分配  1.部分分配   2.已分配)
     */
    @Excel(name = "分配状态")
    private Integer distributeStatus;

    @Excel(name = "确认收货日期")
    private Date confirmReceiptTime;
}
