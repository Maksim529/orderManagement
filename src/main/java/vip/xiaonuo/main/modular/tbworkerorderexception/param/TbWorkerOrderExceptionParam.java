
package vip.xiaonuo.main.modular.tbworkerorderexception.param;

import io.swagger.annotations.ApiModelProperty;
import vip.xiaonuo.common.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * 生产异常反馈参数类
 *
 * @author 邾茂星
 * @date 2022-01-20 09:23:33
 */
@Data
public class TbWorkerOrderExceptionParam extends BaseParam {

    /**
     *
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 异常编号
     */
    @NotBlank(message = "异常编号不能为空，请检查code参数")
    private String code;

    /**
     * 工单id
     */
    @NotNull(message = "工单id不能为空，请检查workOrderId参数", groups = {add.class, edit.class})
    private Long workOrderId;

    /**
     * 异常分类
     */
    @NotNull(message = "异常分类不能为空，请检查exceptionType参数", groups = {add.class, edit.class})
    private Long exceptionType;

    /**
     * 异常描述
     */
    @NotBlank(message = "异常描述不能为空，请检查exceptionContent参数")
    private String exceptionContent;

    /**
     * 平台处理情况
     */
    @NotBlank(message = "平台处理情况不能为空，请检查processedContent参数")
    private String processedContent;

    /**
     * 负责人
     */
    @NotNull(message = "负责人不能为空，请检查functionaryId参数", groups = {add.class})
    private Long functionaryId;

    /**
     * 负责人名称
     */
    @NotBlank(message = "负责人名称不能为空，请检查functionaryName参数")
    private String functionaryName;

    /**
     * 处理人
     */
    @NotNull(message = "处理人不能为空，请检查processedId参数")
    private Long processedId;

    /**
     * 处理人名称
     */
    @NotBlank(message = "处理人名称不能为空，请检查processedName参数")
    private String processedName;

    /**
     * 处理时间
     */
    @NotNull(message = "处理时间不能为空，请检查processedTime参数")
    private String processedTime;

    @ApiModelProperty("页码")
    private Integer pageNo;

    @ApiModelProperty("条数")
    private Integer pageSize;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("当前操作人id")
    private Long operatorId;
}
