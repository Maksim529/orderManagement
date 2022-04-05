package vip.xiaonuo.main.modular.tbcustomerorder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 邾茂星
 * @Description: 生产订单DTO
 * @date 2022/1/12 10:20
 */
@Data
public class TbCustomerOrderDTO extends BaseParam {

    @ApiModelProperty("订单id")
    @NotNull(message = "订单id不能为空", groups = {BaseParam.edit.class})
    private Long id;

    @ApiModelProperty("客户id")
    @NotNull(message = "客户id不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private Long customerId;

    @ApiModelProperty("订单号")
    @NotBlank(message = "订单号不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String orderNo;

    @ApiModelProperty("客户款号")
    @NotBlank(message = "客户款号不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String customerSku;

    @ApiModelProperty("品类")
    @NotNull(message = "品类不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private Long category;

    @ApiModelProperty("生产类型（1=FOB  2=CMT）")
    @NotNull(message = "生产类型不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private Long produceType;

    @ApiModelProperty("价格 元/件")
    @NotNull(message = "价格不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private BigDecimal price;

    @ApiModelProperty("交期")
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date givedate;

    @ApiModelProperty("收货地址")
    private String receiveAddr;

    @ApiModelProperty("收货电话")
    private String receivePhone;

    @ApiModelProperty("收货人")
    private String receivePerson;

    @ApiModelProperty("省市区")
    private String areacode;

    @ApiModelProperty("总数量")
    @NotNull(message = "总数量不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private Integer amount;

    @ApiModelProperty("询价单id")
    private Long askId;

    @ApiModelProperty("接单负责人id,报价人id")
    private Long principal;

    @ApiModelProperty("状态")
    @NotNull(message = "状态不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private Integer status;

    @ApiModelProperty("创建类型 0=小程序 1=PC")
    @NotNull(message = "创建类型不能为空", groups = {BaseParam.add.class})
    private Integer syscreated;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("订单明细")
    @NotEmpty(message = "订单明细", groups = {BaseParam.add.class, BaseParam.edit.class})
    private List<TbCustomerOrderDetailDTO> detailList;

    /**
     * 图片
     */
    private String pic;

    @ApiModelProperty("款式图路径")
    private String picPath;

    @ApiModelProperty("详情图路径")
    private String[] detailPaths;
}
