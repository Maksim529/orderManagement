
package vip.xiaonuo.main.modular.sysConfig.param;

import io.swagger.annotations.ApiModelProperty;
import vip.xiaonuo.common.pojo.base.param.BaseParam;
import lombok.Data;
import vip.xiaonuo.main.modular.sysConfig.entity.TbFactoryMerchandiser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
* 工厂信息参数类
 *
 * @author 邾茂星
 * @date 2022-01-10 16:37:50
*/
@Data
public class FactoryParam extends BaseParam {

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
     * 地址
     */
    @NotBlank(message = "地址不能为空，请检查address参数", groups = {add.class, edit.class})
    private String address;

    /**
     * 状态（1、合作中 2、停止合作 3、潜在客户）
     */
    @NotNull(message = "状态（1、合作中 2、停止合作 3、潜在客户）不能为空，请检查status参数", groups = {add.class, edit.class})
    private Integer status;

    @ApiModelProperty("跟单员idList")
    @NotNull(message = "跟单员id不能为空", groups = {add.class, edit.class})
    private List<Long> userIdList;

}
