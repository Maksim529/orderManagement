
package vip.xiaonuo.main.modular.tbproducecontract.param;

import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 加工合同参数类
 *
 * @author wjc
 * @date 2022-01-20 10:11:34
*/
@Data
public class TbProduceContractParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空，请检查contractNo参数", groups = {add.class, edit.class})
    private String contractNo;

    /**
     * 订单id
     */
    @NotNull(message = "订单id不能为空，请检查workOrderId参数", groups = {add.class, edit.class})
    private Long workOrderId;

    /**
     * 工厂方
     */
    @NotNull(message = "工厂方不能为空，请检查factoryId参数", groups = {add.class, edit.class})
    private Long factoryId;

    /**
     * 乙方
     */
    @NotBlank(message = "乙方不能为空，请检查service参数", groups = {add.class, edit.class})
    private String service;

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查syscreated参数", groups = {add.class, edit.class})
    private Integer syscreated;

}
