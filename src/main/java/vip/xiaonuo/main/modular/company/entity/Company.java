
package vip.xiaonuo.main.modular.company.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 客户管理
 *
 * @author 邾茂星
 * @date 2021-12-30 20:34:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_company")
public class Company extends BaseEntity {

    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 负责人
     */
    @Excel(name = "负责人")
    private String manager;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String managerTel;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 统一征信代码
     */
    @Excel(name = "统一征信代码")
    private String companyNo;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

}
