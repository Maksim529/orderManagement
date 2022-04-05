package vip.xiaonuo.main.modular.tbworkorder.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 工单信息 VO
 * @author 邾茂星
 * @date 2022/1/19 14:15
 */
@Data
public class TbWorkOrderInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("加工单id")
    private Long workOrderId;

    @ApiModelProperty("加工单号")
    private String workOrderNo;

    @ApiModelProperty("工厂id")
    private Long factoryrId;

    @ApiModelProperty("跟单员")
    private String merchandiser;

    @ApiModelProperty("跟单员id")
    private Long merchandiserId;

    @ApiModelProperty("工厂名称")
    private String factoryName;

    @ApiModelProperty("订单id")
    private Long customerOrderId;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("客户款号")
    private String customerSku;

    @ApiModelProperty("单量")
    private Integer amount;

    @ApiModelProperty("收货地址")
    private String receiveAddr;

    @ApiModelProperty("款式图路径")
    private String pic;

    @ApiModelProperty("页码")
    private Integer pageNo;

    @ApiModelProperty("条数")
    private Integer pageSize;

    @ApiModelProperty("当前操作人id")
    private Long operatorId;
}
