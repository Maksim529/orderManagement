package vip.xiaonuo.main.modular.tbcustomerorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 邾茂星
 * @Description: 客户订单滚动展示 VO
 * @date 2022/1/17 13:12
 */
@Data
public class CustomerOrderRollInfoVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户电话")
    private String customerPhone;

    @ApiModelProperty("客户公司名称")
    private String companyName;

    @ApiModelProperty("总数量")
    private Integer amount;

    @ApiModelProperty("品类id")
    private Long category;

    @ApiModelProperty("品类名称")
    private String categoryName;

    @ApiModelProperty("状态")
    private String statusName;
}
