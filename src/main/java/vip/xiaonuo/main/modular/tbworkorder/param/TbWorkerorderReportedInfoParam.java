
package vip.xiaonuo.main.modular.tbworkorder.param;

import vip.xiaonuo.common.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.math.BigDecimal;

/**
* 报工记录参数类
 *
 * @author xwx
 * @date 2022-03-14 14:10:21
*/
@Data
public class TbWorkerorderReportedInfoParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 工单id
     */
    @NotNull(message = "工单id不能为空，请检查workOrderId参数", groups = {add.class, edit.class})
    private Long workOrderId;

    /**
     * 报工类型
     */
    @NotNull(message = "报工类型不能为空，请检查type参数", groups = {add.class, edit.class})
    private Integer type;

    /**
     * 报工类型名称
     */
    @NotBlank(message = "报工类型名称不能为空，请检查typeName参数", groups = {add.class, edit.class})
    private String typeName;

    /**
     * 提交数量
     */
    @NotNull(message = "提交数量不能为空，请检查submitNum参数", groups = {add.class, edit.class})
    private BigDecimal submitNum;

    /**
     * 提交时间
     */
    @NotNull(message = "提交时间不能为空，请检查submitDate参数", groups = {add.class, edit.class})
    private String submitDate;

    /**
     * 状态 0=正常 1=暂停 2=删除
     */
    @NotNull(message = "状态 0=正常 1=暂停 2=删除不能为空，请检查status参数", groups = {add.class, edit.class})
    private Integer status;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空，请检查remark参数", groups = {add.class, edit.class})
    private String remark;

    /**
     * 创建人名称
     */
    @NotBlank(message = "创建人名称不能为空，请检查createUserName参数", groups = {add.class, edit.class})
    private String createUserName;

}
