package vip.xiaonuo.main.modular.tbworkorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 报工记录 VO
 * @author 邾茂星
 * @date 2022/3/21 16:54
 */
@Data
public class WorkOrderReportedInfoVO {

    private Long id;

    private String typeName;

    private String createUserName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date submitDate;

    private BigDecimal submitNum;

    private List<WorkOrderReportedDetailVO> detailList;
}
