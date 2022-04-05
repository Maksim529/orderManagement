package vip.xiaonuo.main.modular.tbworkorder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 工单报工 dto
 * @author 邾茂星
 * @date 2022/3/14 14:31
 */
@Data
public class WorkOrderReportedDTO {

    @ApiModelProperty("工单id")
    @NotNull(message = "工单id不能为空")
    private Long workOrderId;

    @ApiModelProperty("报工类型 1=裁剪")
    @NotNull(message = "报工类型不能为空")
    private Integer type;

    @ApiModelProperty("提交数量")
    private BigDecimal submitNum;

    @ApiModelProperty("提交日期")
    private Date submitDate;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("报工明细")
    private List<WorkOrderReportedDetailDTO> reportedDetailDTOList;

    @ApiModelProperty("报工人id")
    private Long createUser;

    @ApiModelProperty("报工人")
    private String createUserName;
}
