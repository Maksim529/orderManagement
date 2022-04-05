package vip.xiaonuo.main.modular.tbcustomerorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import vip.xiaonuo.main.modular.tbcustomer.entity.TbCustomer;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrderDetail;
import vip.xiaonuo.main.modular.tbworkerorderspeed.entity.TbWorkerorderSpeed;
import vip.xiaonuo.main.modular.tbworkorder.vo.TbWorkOrderVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 邾茂星
 * @Description: 生产订单 VO
 * @date 2022/1/14 11:01
 */
@Data
public class TbCustomerOrderVO extends BaseEntity {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("所属客户id")
    private Long customerId;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("客户款号")
    private String customerSku;

    @ApiModelProperty("款式图")
    private String pic;

    @ApiModelProperty("品类id")
    private Long category;

    @ApiModelProperty("品类名称")
    private String strCategory;

    @ApiModelProperty("生产类型（1=FOB  2=CMT）")
    private Long produceType;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("交期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    private Integer amount;

    @ApiModelProperty("是否后台创建 0=小程序 1=PC")
    private Integer syscreated;

    @ApiModelProperty("询价单_id")
    private Long askId;

    @ApiModelProperty("接单负责人id")
    private Long principal;

    @ApiModelProperty("接单负责人")
    private String principalName;

    @ApiModelProperty("接单负责人电话")
    private String principalPhone;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("客户信息")
    private TbCustomer tbCustomer;

    /**
     * 分配状态(0.未分配  1.部分分配   2.已分配)
     */
    @ApiModelProperty("分配状态")
    private Integer distributeStatus;

    @ApiModelProperty("订单明细")
    private List<TbCustomerOrderDetail> orderDetailList;

    @ApiModelProperty("生产进度")
    private List<TbWorkOrderVO> children;

    @ApiModelProperty("生产进度")
    private List<TbWorkerorderSpeed> workerorderSpeedList;

    @ApiModelProperty("详细图路径")
    private List<String> filePathList;

    @ApiModelProperty("颜色id")
    private List<Long> colorIdList;

    @ApiModelProperty("尺码集合")
    private List<String> sizeList;
}
