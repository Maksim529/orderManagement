package vip.xiaonuo.main.modular.tbfactoryorderdetail.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 工单明细
 * @author 邾茂星
 * @date 2022/3/14 9:23
 */
@Data
public class WorkOrderDetailVO {

    @ApiModelProperty("颜色id")
    private Long colorId;

    @ApiModelProperty("颜色")
    private String colorName;

    @ApiModelProperty("尺码")
    private String size;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("已处理数量")
    private Integer processedNum = 0;

}
