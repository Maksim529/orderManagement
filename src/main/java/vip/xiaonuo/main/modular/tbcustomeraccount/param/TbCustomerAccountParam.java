
package vip.xiaonuo.main.modular.tbcustomeraccount.param;

import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 客户账号参数类
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:03
*/
@Data
public class TbCustomerAccountParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 账号名
     */
    private String account;

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
     * 状态（1、使用中 2、停止使用 3、注销）
     */
    @NotNull(message = "状态（1、使用中 2、停止使用 3、注销）不能为空，请检查status参数", groups = {add.class, edit.class})
    private Integer status;

    /**
     * 所属客户
     */
    private Long customerId;

    /**
     * openid
     */
    @NotBlank(message = "openid不能为空，请检查openid参数")
    private String openid;

    private String customerName;
}
