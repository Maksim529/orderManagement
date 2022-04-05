
package vip.xiaonuo.main.modular.tbworkerorderexception.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrder;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.TbCustomerOrderVO;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkOrder;
import vip.xiaonuo.sys.modular.file.entity.SysFileInfo;

/**
 * 生产异常反馈
 *
 * @author 邾茂星
 * @date 2022-01-20 09:23:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_worker_order_exception")
public class TbWorkerOrderException extends BaseEntity {

    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 异常编号
     */
    @Excel(name = "异常编号")
    private String code;

    /**
     * 工单id
     */
    @Excel(name = "工单id")
    private Long workOrderId;

    /**
     * 异常分类
     */
    @Excel(name = "异常分类")
    private Long exceptionType;

    /**
     * 异常描述
     */
    @Excel(name = "异常描述")
    private String exceptionContent;

    /**
     * 平台处理情况
     */
    @Excel(name = "平台处理情况")
    private String processedContent;

    /**
     * 负责人
     */
    @Excel(name = "负责人")
    private Long functionaryId;

    /**
     * 负责人名称
     */
    @Excel(name = "负责人名称")
    private String functionaryName;

    /**
     * 处理人
     */
    @Excel(name = "处理人")
    private Long processedId;

    /**
     * 处理人名称
     */
    @Excel(name = "处理人名称")
    private String processedName;

    @Excel(name = "状态")
    private Integer status;

    /**
     * 处理时间
     */
    @Excel(name = "处理时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date processedTime;

    @TableField(exist = false)
    private List<SysFileInfo> sysFileInfoList;

    @ApiModelProperty("工单编号")
    @TableField(exist = false)
    private String workOrderNo;

    @ApiModelProperty("异常分类名称")
    @TableField(exist = false)
    private String exceptionTypeName;

    @ApiModelProperty("异常分类code")
    @TableField(exist = false)
    private String exceptionTypeCode;

    @ApiModelProperty("订单信息")
    @TableField(exist = false)
    private TbCustomerOrderVO tbCustomerOrderVO;

    @ApiModelProperty("工单信息")
    @TableField(exist = false)
    private TbWorkOrder tbWorkOrder;

}
