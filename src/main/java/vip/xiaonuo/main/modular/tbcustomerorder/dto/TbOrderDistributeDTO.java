package vip.xiaonuo.main.modular.tbcustomerorder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 分配工单时的参数
 * @author 邾茂星
 * @date 2022/1/13 14:15
 */
@Data
public class TbOrderDistributeDTO extends BaseParam {

    @ApiModelProperty("订单id")
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    /**
     * 跟单员
     */
    @ApiModelProperty("跟单员")
    private String merchandiser;

    /**
     * 跟单员id
     */
    @ApiModelProperty("跟单员id")
    private Long merchandiserId;

    /**
     * 所属工厂
     */
    @ApiModelProperty("所属工厂")
    @NotNull(message = "所属工厂不能为空，请检查factoryrId参数")
    private Long factoryrId;


    /**
     * 单量
     */
    @ApiModelProperty("单量")
    @NotNull(message = "单量不能为空，请检查orderCounts参数")
    private Long orderCounts;

    /**
     * 单量
     */
    @ApiModelProperty("剩余单量")
    @NotNull(message = "剩余单量不能为空，请检查orderCounts参数")
    private Long surplusCounts;

    @ApiModelProperty("订单明细")
    @NotEmpty(message = "订单明细")
    private List<TbOrderDistributeDetailDTO> detailList;

}
