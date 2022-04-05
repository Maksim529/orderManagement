package vip.xiaonuo.main.modular.tbcustomerorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.xiaonuo.main.modular.tbcustomer.entity.TbCustomer;

import java.util.Date;

/**
 * @Description: 销售订单合同 VO
 * @author 邾茂星
 * @date 2022/1/19 11:02
 */
@Data
public class TbCustomerOrderContractVO {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("所属客户id")
    private Long customerId;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("接单负责人id")
    private Long principal;

    @ApiModelProperty("接单负责人")
    private String principalName;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("客户信息")
    private TbCustomer tbCustomer;

    @ApiModelProperty("订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("搜索值")
    private String searchValue;
}
