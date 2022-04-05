
package vip.xiaonuo.main.modular.tbcustomerorder.param;

import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 订单明细参数类
 *
 * @author 邾茂星
 * @date 2022-01-12 10:09:05
 */
@Data
public class TbCustomerOrderDetailParam extends BaseParam {

    /**
     *
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 订单id
     */
    @NotNull(message = "订单id不能为空，请检查orderId参数", groups = {add.class, edit.class})
    private Long orderId;

    /**
     * 颜色
     */
    @NotNull(message = "颜色不能为空，请检查colorId参数", groups = {add.class, edit.class})
    private Long colorId;

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

    private String colorName;

    private Long pushCount;
}
