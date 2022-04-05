package vip.xiaonuo.main.modular.tbworkorder.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @Description: 报工明细 dto
 * @author 邾茂星
 * @date 2022/3/14 14:46
 */
@Data
public class WorkOrderReportedDetailDTO {

    @ApiModelProperty("颜色id")
    private Long colorId;

    @ApiModelProperty("颜色名")
    private String colorName;

    @ApiModelProperty("尺码")
    private String size;

    @ApiModelProperty("数量")
    private Long count;

}
