
package vip.xiaonuo.main.modular.sysConfig.param;

import vip.xiaonuo.common.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
* 工厂跟单员参数类
 *
 * @author xwx
 * @date 2022-03-22 13:27:53
*/
@Data
public class TbFactoryMerchandiserParam extends BaseParam {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 工厂id
     */
    @NotNull(message = "工厂id不能为空，请检查factoryId参数", groups = {add.class, edit.class})
    private Long factoryId;

    /**
     * 跟单员id
     */
    @NotNull(message = "跟单员id不能为空，请检查userId参数", groups = {add.class, edit.class})
    private Long userId;

    /**
     * 跟单员名称
     */
    @NotBlank(message = "跟单员名称不能为空，请检查userName参数", groups = {add.class, edit.class})
    private String userName;

    /**
     * 状态：0=正常；1=停用；2=删除
     */
    @NotNull(message = "状态：0=正常；1=停用；2=删除不能为空，请检查status参数", groups = {add.class, edit.class})
    private Integer status;

}
