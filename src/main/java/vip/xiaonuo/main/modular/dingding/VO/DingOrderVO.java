package vip.xiaonuo.main.modular.dingding.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 邾茂星
 * @date 2022/1/18 10:56
 */
@Data
public class DingOrderVO {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("订单号")
    private String workOrderNo;
    @ApiModelProperty("客户款号")
    private String customerSku;
    @ApiModelProperty("品类id")
    private Long category;
    @ApiModelProperty("品类名称")
    private String strCategory;
    @ApiModelProperty("生产类型（1=FOB  2=CMT）")
    private Long produceType;
    @ApiModelProperty("总数量")
    private Integer orderCounts;
}
