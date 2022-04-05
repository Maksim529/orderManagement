
package vip.xiaonuo.main.modular.tbworkorder.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;
import java.math.BigDecimal;

/**
 * 报工记录
 *
 * @author xwx
 * @date 2022-03-14 14:10:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_workerorder_reported_info")
public class TbWorkerorderReportedInfo extends BaseEntity {

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
     * 报工类型
     */
    @Excel(name = "报工类型")
    private Integer type;

    /**
     * 报工类型名称
     */
    @Excel(name = "报工类型名称")
    private String typeName;

    /**
     * 提交数量
     */
    @Excel(name = "提交数量")
    private BigDecimal submitNum;

    /**
     * 提交时间
     */
    @Excel(name = "提交时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date submitDate;

    /**
     * 状态 0=正常 1=暂停 2=删除
     */
    @Excel(name = "状态 0=正常 1=暂停 2=删除")
    private Integer status;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 创建人名称
     */
    @Excel(name = "创建人名称")
    private String createUserName;

}
