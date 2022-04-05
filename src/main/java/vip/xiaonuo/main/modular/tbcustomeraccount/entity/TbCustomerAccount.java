
package vip.xiaonuo.main.modular.tbcustomeraccount.entity;

import com.baomidou.mybatisplus.annotation.*;
import rx.BackpressureOverflow;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 客户账号
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_customer_account")
public class TbCustomerAccount extends BaseEntity {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号名
     */
    @Excel(name = "账号名")
    private String account;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String owner;

    /**
     * 联系人电话
     */
    @Excel(name = "联系人电话")
    private String ownerTel;

    /**
     * 状态（1、使用中 2、停止使用 3、注销）
     */
    @Excel(name = "状态（1、使用中 2、停止使用 3、注销）")
    private Integer status;

    /**
     * 所属客户
     */
    @Excel(name = "所属客户")
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private Long customerId;

    /**
     * openid
     */
    @Excel(name = "openid")
    private String openid;

    @Excel(name = "客户编码")
    private String code;

    @Excel(name = "邀请人id")
    private Long inviterId;

    @Excel(name = "邀请时间")
    private Date inviteTime;

    @TableField(exist = false)
    private String customerName;

}
