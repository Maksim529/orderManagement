
package vip.xiaonuo.main.modular.tbworkorder.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 报工明细
 *
 * @author xwx
 * @date 2022-03-14 14:23:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_workerorder_reported_detail")
public class TbWorkerorderReportedDetail extends BaseEntity {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 工单id
     */
    @Excel(name = "工单id")
    private Long workOrderId;

    /**
     * 报工id
     */
    @Excel(name = "报工id")
    private Long reportedId;

    /**
     * 颜色id
     */
    @Excel(name = "颜色id")
    private Long colorId;

    /**
     * 颜色名
     */
    @Excel(name = "颜色名")
    private String colorName;

    /**
     * 尺码
     */
    @Excel(name = "尺码")
    private String size;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Long count;

}
