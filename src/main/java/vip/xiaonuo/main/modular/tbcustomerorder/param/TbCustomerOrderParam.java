
package vip.xiaonuo.main.modular.tbcustomerorder.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.common.pojo.base.param.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
* 生产订单参数类
 *
 * @author 邾茂星
 * @date 2022-01-12 08:59:51
*/
@Data
public class TbCustomerOrderParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 所属客户
     */
    @NotNull(message = "所属客户不能为空，请检查customerId参数", groups = {add.class, edit.class})
    private Long customerId;

    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空，请检查orderNo参数", groups = {add.class, edit.class})
    private String orderNo;

    /**
     * 生产工单号
     */
    private String workOrderNo;

    /**
     * 客户款号
     */
    @NotBlank(message = "客户款号不能为空，请检查customerSku参数", groups = {add.class, edit.class})
    private String customerSku;

    /**
     * 图片
     */
    @NotBlank(message = "图片不能为空，请检查pic参数", groups = {add.class, edit.class})
    private String pic;

    /**
     * 品类
     */
    @NotNull(message = "品类不能为空，请检查category参数", groups = {add.class, edit.class})
    private Long category;

    /**
     * 生产类型（1=FOB  2=CMT）
     */
    @NotNull(message = "生产类型（1=FOB  2=CMT）不能为空，请检查produceType参数", groups = {add.class, edit.class})
    private Long produceType;

    /**
     * 单位（字典枚举）
     */
    @NotNull(message = "单位（字典枚举）不能为空，请检查unit参数", groups = {add.class, edit.class})
    private Long unit;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空，请检查price参数", groups = {add.class, edit.class})
    private BigDecimal price;

    /**
     * 交期
     */
    @NotNull(message = "交期不能为空，请检查givedate参数", groups = {add.class, edit.class})
    private String givedate;

    @ApiModelProperty("交期开始时间")
    private String beginGivedate;

    @ApiModelProperty("交期结束")
    private String endGivedate;

    /**
     * 收货地址
     */
    @NotBlank(message = "收货地址不能为空，请检查receiveAddr参数", groups = {add.class, edit.class})
    private String receiveAddr;

    private String receivePerson;

    /**
     * 省市区
     */
    @NotBlank(message = "省市区不能为空，请检查areacode参数", groups = {add.class, edit.class})
    private String areacode;

    /**
     * 总数量
     */
    @NotNull(message = "总数量不能为空，请检查amount参数", groups = {add.class, edit.class})
    private Integer amount;

    /**
     * 是否后台创建 0=小程序 1=PC
     */
    @NotNull(message = "是否后台创建 0=小程序 1=PC不能为空，请检查syscreated参数", groups = {add.class, edit.class})
    private Integer syscreated;

    /**
     * 订单类型
     */
    @NotNull(message = "订单类型不能为空，请检查orderType参数", groups = {add.class, edit.class})
    private Integer orderType;

    /**
     * 询价单_id
     */
    @NotNull(message = "询价单_id不能为空，请检查askId参数", groups = {add.class, edit.class})
    private Long askId;

    /**
     * 接单负责人id
     */
    @NotNull(message = "接单负责人id不能为空，请检查principal参数", groups = {add.class, edit.class})
    private Long principal;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空，请检查status参数", groups = {add.class, edit.class})
    private Integer status;

    @ApiModelProperty("页码")
    private Integer pageNo;

    @ApiModelProperty("条数")
    private Integer pageSize;

    @ApiModelProperty("请求类型 1=小程序")
    private Integer requestType;

    @ApiModelProperty("接单负责人")
    private String principalName;

    @ApiModelProperty("状态数组")
    private String statusArray;
}
