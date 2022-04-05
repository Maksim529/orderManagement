package vip.xiaonuo.main.modular.tbworkorder.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import vip.xiaonuo.main.modular.sysConfig.entity.Factory;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.entity.TbFactoryOrderDetail;
import vip.xiaonuo.main.modular.tbworkerorderspeed.entity.TbWorkerorderSpeed;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 邾茂星
 * @date 2022/1/15 13:49
 */
@Data
public class TbWorkOrderVO extends BaseEntity {

    /**
     *
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    private Long orderId;

    /**
     * 工单号
     */
    @ApiModelProperty("工单号")
    private String workOrderNo;

    /**
     * 客户款号
     */
    @ApiModelProperty("客户款号")
    private String customerSku;

    /**
     * 图片
     */
    @ApiModelProperty("图片")
    private String pic;

    /**
     * 品类
     */
    @ApiModelProperty("品类")
    private Long category;

    /**
     * 生产类型（1、CMT2、FOB）
     */
    @ApiModelProperty("生产类型（1、CMT2、FOB）")
    private Integer produceType;

    /**
     * 颜色
     */
    @ApiModelProperty("颜色")
    private Integer color;

    /**
     * 尺码（字典枚举）
     */
    @ApiModelProperty("尺码（字典枚举）")
    private String size;

    /**
     * 单位（字典枚举）
     */
    @ApiModelProperty("单位（字典枚举）")
    private Integer unit;

    /**
     * 价格
     */
    @ApiModelProperty("价格")
    private BigDecimal price;

    /**
     * 交期
     */
    @ApiModelProperty("交期")
    private Date givedate;

    /**
     * 单量
     */
    @ApiModelProperty("单量")
    private Long amount;

    /**
     * 收货地址
     */
    @ApiModelProperty("收货地址")
    private String receiveAddr;

    /**
     * 所属客户
     */
    @ApiModelProperty("所属客户")
    private Long factoryrId;

    /**
     *
     */
    @ApiModelProperty("syscreated")
    private Integer syscreated;

    /**
     * 0 未结单，1 已结单
     */
    @ApiModelProperty("0 未结单，1 已结单")
    private Integer iscomplted;

    /**
     * 确认人
     */
    @ApiModelProperty("确认人")
    private Long confirmUser;

    /**
     * 确认时间
     */
    @ApiModelProperty("确认时间")
    private Date confirmTime;

    /**
     * 跟单员
     */
    @ApiModelProperty("跟单员")
    private String merchandiser;

    /**
     * 工单状态(1、未齐套、2、已裁剪、3、一裁剪未缝制 4、已缝制未后整 5、未质检 6、已质检未发货 7、完成生产)
     */
    @ApiModelProperty("工单状态(1、未齐套、2、已裁剪、3、一裁剪未缝制 4、已缝制未后整 5、未质检 6、已质检未发货 7、完成生产)")
    private Integer status;
    /**
     * 跟单员id
     */
    @ApiModelProperty("跟单员id")
    private Long merchandiserId;

    @ApiModelProperty("品类名称")
    private String strCategory;

    @ApiModelProperty("工厂客户信息")
    private Factory tbCustomer;

    @ApiModelProperty("订单明细")
    private List<TbFactoryOrderDetail> orderDetailList;

    @ApiModelProperty("生产进度")
    private TbWorkerorderSpeed workerorderSpeed;
}
