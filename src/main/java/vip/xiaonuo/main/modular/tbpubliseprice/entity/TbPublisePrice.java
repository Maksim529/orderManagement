
package vip.xiaonuo.main.modular.tbpubliseprice.entity;

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
 * 报价记录
 *
 * @author 邾茂星
 * @date 2022-01-11 10:11:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_publise_price")
public class TbPublisePrice extends BaseEntity {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 询价单id
     */
    @Excel(name = "询价单id")
    private Long askOrderId;

    /**
     * 服务方报价
     */
    @Excel(name = "服务方报价区间低")
    private BigDecimal servicePrice;

    /**
     * 报价人
     */
    @Excel(name = "报价人")
    private Long publicPriceUser;

    /**
     * 报价次
     */
    @Excel(name = "报价次")
    private Integer publicPriceTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String note;

    /**
     * 所属客户
     */
    @Excel(name = "所属客户")
    private Long customerId;

    /**
     * 是否后台创建
     */
    @Excel(name = "是否后台创建")
    private Integer syscreated;


    @Excel(name = "服务方报价区间高")
    private BigDecimal servicePriceHigh;

    /**
     * 承诺交期
     */
    private Date promiseDate;


}
