
package vip.xiaonuo.main.modular.tbfactoryorderdetail.param;

import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 工单明细参数类
 *
 * @author wjc
 * @date 2022-01-13 13:35:25
*/
@Data
public class TbFactoryOrderDetailParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 订单id
     */
    @NotNull(message = "订单id不能为空，请检查factoryId参数", groups = {add.class, edit.class})
    private Long factoryId;

    /**
     * 工厂方
     */
    @NotNull(message = "工厂方不能为空，请检查colorId参数", groups = {add.class, edit.class})
    private Long colorId;

    /**
     * 颜色名
     */
    @NotBlank(message = "颜色名不能为空，请检查colorName参数", groups = {add.class, edit.class})
    private String colorName;

    /**
     * 乙方
     */
    @NotBlank(message = "乙方不能为空，请检查size参数", groups = {add.class, edit.class})
    private String size;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空，请检查count参数", groups = {add.class, edit.class})
    private Long count;

    /**
     * 是否后台创建
     */
    @NotNull(message = "是否后台创建不能为空，请检查syscreated参数", groups = {add.class, edit.class})
    private Integer syscreated;

}
