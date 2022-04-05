
package vip.xiaonuo.main.modular.sizeconfig.param;

import vip.xiaonuo.common.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
* 尺码配置参数类
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:59
*/
@Data
public class SizeConfigParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 尺码组名称
     */
    @NotBlank(message = "尺码组名称不能为空，请检查cateName参数", groups = {add.class, edit.class})
    private String cateName;

    /**
     * 尺码信息
     */
    @NotBlank(message = "尺码信息不能为空，请检查sizeInfor参数", groups = {add.class, edit.class})
    private String sizeInfor;

    /**
     * 是否默认
     */
    @NotNull(message = "是否默认不能为空，请检查isDefault参数", groups = {add.class, edit.class})
    private Integer isDefault;

}
