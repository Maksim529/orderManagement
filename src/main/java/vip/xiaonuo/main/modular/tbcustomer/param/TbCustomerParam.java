
package vip.xiaonuo.main.modular.tbcustomer.param;

import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 客户参数类
 *
 * @author 邾茂星
 * @date 2022-01-14 08:42:18
*/
@Data
public class TbCustomerParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 工厂名
     */
    @NotBlank(message = "工厂名不能为空，请检查name参数", groups = {add.class, edit.class})
    private String name;

    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空，请检查owner参数", groups = {add.class, edit.class})
    private String owner;

    /**
     * 联系人电话
     */
    @NotBlank(message = "联系人电话不能为空，请检查ownerTel参数", groups = {add.class, edit.class})
    private String ownerTel;

    /**
     * 公司代码
     */
    @NotBlank(message = "公司代码不能为空，请检查code参数", groups = {add.class, edit.class})
    private String code;

    /**
     * 是否后台创建
     */
    @NotNull(message = "是否后台创建不能为空，请检查syscreated参数", groups = {add.class, edit.class})
    private Integer syscreated;

    /**
     * 状态（1、合作中 2、停止合作 3、潜在客户）
     */
    @NotNull(message = "状态（1、合作中 2、停止合作 3、潜在客户）不能为空，请检查status参数", groups = {add.class, edit.class})
    private Integer status;

}
