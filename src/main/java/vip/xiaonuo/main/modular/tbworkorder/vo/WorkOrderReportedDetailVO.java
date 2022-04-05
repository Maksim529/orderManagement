package vip.xiaonuo.main.modular.tbworkorder.vo;

import lombok.Data;

@Data
public class WorkOrderReportedDetailVO {

    /**
     * 工单id
     */
    private Long workOrderId;

    /**
     * 报工id
     */
    private Long reportedId;

    /**
     * 颜色id
     */
    private Long colorId;

    /**
     * 颜色名
     */
    private String colorName;

    /**
     * 尺码
     */
    private String size;

    /**
     * 数量
     */
    private Long count;
}
