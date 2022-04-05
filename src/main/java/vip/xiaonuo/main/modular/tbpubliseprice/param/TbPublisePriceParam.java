
package vip.xiaonuo.main.modular.tbpubliseprice.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
* 报价记录参数类
 *
 * @author 邾茂星
 * @date 2022-01-11 10:11:12
*/
@Data
public class TbPublisePriceParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 询价单id
     */
    @NotNull(message = "询价单id不能为空，请检查askOrderId参数", groups = {add.class, edit.class})
    private Long askOrderId;

    /**
     * 服务方报价
     */
    @NotNull(message = "服务方报价区间1不能为空，请检查servicePrice参数", groups = {add.class, edit.class})
    private BigDecimal servicePrice;


    /**
     * 服务方报价
     */
    @NotNull(message = "服务方报价区间2不能为空，请检查servicePrice参数", groups = {add.class, edit.class})
    private BigDecimal servicePriceHigh;

    /**
     * 报价人
     */
    private Long publicPriceUser;

    /**
     * 报价次
     */
    @NotNull(message = "报价次数不能为空，publicPriceTime", groups = {add.class, edit.class})
    private Integer publicPriceTime;

    /**
     * 备注
     */
    private String note;

    /**
     * 所属客户
     */
    private Long customerId;

    /**
     * 是否后台创建
     */
    private Integer syscreated;

    /**
     * 承诺交期
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date promiseDate;

}
