
package vip.xiaonuo.main.modular.tbcustomer.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 客户
 *
 * @author 邾茂星
 * @date 2022-01-14 08:42:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_customer")
public class TbCustomer extends BaseEntity {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 工厂名
     */
    @Excel(name = "工厂名")
    private String name;

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
     * 公司代码
     */
    @Excel(name = "公司代码")
    private String code;

    /**
     * 是否后台创建
     */
    @Excel(name = "是否后台创建")
    private Integer syscreated;

    /**
     * 状态（1、合作中 2、停止合作 3、潜在客户）
     */
    @Excel(name = "状态（1、合作中 2、停止合作 3、潜在客户）")
    private Integer status;

}
