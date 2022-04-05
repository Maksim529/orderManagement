
package vip.xiaonuo.main.modular.tbworkorder.param;

import vip.xiaonuo.common.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
* 报工明细参数类
 *
 * @author xwx
 * @date 2022-03-14 14:23:40
*/
@Data
public class TbWorkerorderReportedDetailParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 工单id
     */
    @NotNull(message = "工单id不能为空，请检查workOrderId参数", groups = {add.class, edit.class})
    private Long workOrderId;

    /**
     * 报工id
     */
    @NotNull(message = "报工id不能为空，请检查reportedId参数", groups = {add.class, edit.class})
    private Long reportedId;

    /**
     * 颜色id
     */
    @NotNull(message = "颜色id不能为空，请检查colorId参数", groups = {add.class, edit.class})
    private Long colorId;

    /**
     * 颜色名
     */
    @NotBlank(message = "颜色名不能为空，请检查colorName参数", groups = {add.class, edit.class})
    private String colorName;

    /**
     * 尺码
     */
    @NotBlank(message = "尺码不能为空，请检查size参数", groups = {add.class, edit.class})
    private String size;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空，请检查count参数", groups = {add.class, edit.class})
    private Long count;

}
