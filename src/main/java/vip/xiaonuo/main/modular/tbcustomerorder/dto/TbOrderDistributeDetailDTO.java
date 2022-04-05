package vip.xiaonuo.main.modular.tbcustomerorder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 分配订单生成工单时的详细数量
 * @author 邾茂星
 * @date 2022/1/13 14:40
 */
@Data
public class TbOrderDistributeDetailDTO extends BaseParam {

    @ApiModelProperty("订单明细id")
    @NotNull(message = "订单明细id不能为空")
    private Long id;

    @ApiModelProperty("订单id")
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    @ApiModelProperty("颜色")
    @NotNull(message = "颜色不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private Long colorId;

    @ApiModelProperty("颜色名")
    @NotNull(message = "颜色不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String colorName;

    @ApiModelProperty("尺码")
    @NotBlank(message = "尺码不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String size;

    @ApiModelProperty("数量")
    @NotNull(message = "数量不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private Long count;

    @ApiModelProperty("总派发量")
    @NotNull(message = "数量不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private Long pushCountTotal;
}
