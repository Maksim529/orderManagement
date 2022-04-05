package vip.xiaonuo.main.modular.tbfactoryorderdetail.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Description: 工单数量信息
 * @author 邾茂星
 * @date 2022/3/14 9:34
 */
@Data
public class WorkOrderAmountInfoVO {

    @ApiModelProperty("工单明细")
    private List<WorkOrderDetailVO> workOrderDetailVOList;

    @ApiModelProperty("颜色list")
    private Set<String> colorList;

    @ApiModelProperty("尺码list")
    private Set<String> sizeList;

}
