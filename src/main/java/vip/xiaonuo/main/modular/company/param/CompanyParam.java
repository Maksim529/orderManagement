
package vip.xiaonuo.main.modular.company.param;

import vip.xiaonuo.common.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
* 客户管理参数类
 *
 * @author 邾茂星
 * @date 2021-12-30 20:34:18
*/
@Data
public class CompanyParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 公司名称
     */
    @NotBlank(message = "公司名称不能为空，请检查companyName参数", groups = {add.class, edit.class})
    private String companyName;

    /**
     * 负责人
     */
    @NotBlank(message = "负责人不能为空，请检查manager参数", groups = {add.class, edit.class})
    private String manager;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空，请检查managerTel参数", groups = {add.class, edit.class})
    private String managerTel;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空，请检查remark参数", groups = {add.class, edit.class})
    private String remark;

    /**
     * 统一征信代码
     */
    @NotBlank(message = "统一征信代码不能为空，请检查companyNo参数", groups = {add.class, edit.class})
    private String companyNo;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空，请检查status参数", groups = {add.class, edit.class})
    private Integer status;

}
