
package vip.xiaonuo.main.modular.sysConfig.param;

import vip.xiaonuo.common.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
* 品类参数类
 *
 * @author 邾茂星
 * @date 2022-01-10 13:13:25
*/
@Data
public class ProductTypeParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 类名
     */
    @NotBlank(message = "类名不能为空，请检查typeName参数", groups = {add.class, edit.class})
    private String typeName;

    /**
     * 父id
     */
    @NotNull(message = "父id不能为空，请检查pid参数", groups = {add.class, edit.class})
    private Long pid;

    /**
     * 品类尺码配置
     */
    private Long categorySize;

}
