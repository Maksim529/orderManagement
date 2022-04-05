
package vip.xiaonuo.main.modular.tbworkorder.param;

import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
* 工厂工单参数类
 *
 * @author wjc
 * @date 2022-01-13 14:29:32
*/
@Data
public class TbWorkOrderParam extends BaseParam {

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 订单号
     */
    @NotNull(message = "订单号不能为空，请检查orderId参数", groups = {add.class, edit.class})
    private Long orderId;

    /**
     * 工单号
     */
    @NotBlank(message = "工单号不能为空，请检查workOrderNo参数", groups = {add.class, edit.class})
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
     * 生产类型（1、CMT2、FOB）
     */
    @NotNull(message = "生产类型（1、CMT2、FOB）不能为空，请检查produceType参数", groups = {add.class, edit.class})
    private Integer produceType;

    /**
     * 颜色
     */
    @NotNull(message = "颜色不能为空，请检查color参数", groups = {add.class, edit.class})
    private Integer color;

    /**
     * 尺码（字典枚举）
     */
    @NotBlank(message = "尺码（字典枚举）不能为空，请检查size参数", groups = {add.class, edit.class})
    private String size;

    /**
     * 单位（字典枚举）
     */
    @NotNull(message = "单位（字典枚举）不能为空，请检查unit参数", groups = {add.class, edit.class})
    private Integer unit;

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

    /**
     * 单量
     */
    @NotNull(message = "单量不能为空，请检查orderCounts参数", groups = {add.class, edit.class})
    private Long orderCounts;

    /**
     * 收货地址
     */
    @NotBlank(message = "收货地址不能为空，请检查receiveAddr参数", groups = {add.class, edit.class})
    private String receiveAddr;

    /**
     * 所属客户
     */
    @NotNull(message = "所属客户不能为空，请检查factoryrId参数", groups = {add.class, edit.class})
    private Long factoryrId;

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查syscreated参数", groups = {add.class, edit.class})
    private Integer syscreated;

    /**
     * 0 未结单，1 已结单
     */
    @NotNull(message = "0 未结单，1 已结单不能为空，请检查iscomplted参数", groups = {add.class, edit.class})
    private Integer iscomplted;

    /**
     * 创建时间
     */
    @NotNull(message = "创建时间不能为空，请检查confirmUser参数", groups = {add.class, edit.class})
    private Long confirmUser;

    /**
     * 创建人(默认管理员创建)
     */
    @NotNull(message = "创建人(默认管理员创建)不能为空，请检查confirmTime参数", groups = {add.class, edit.class})
    private String confirmTime;

    /**
     * 跟单员
     */
    private String merchandiser;

    /**
     * 跟单员id
     */
    private Long merchandiserId;

    /**
     * 工单状态(1、未齐套、2、已裁剪、3、一裁剪未缝制 4、已缝制未后整 5、未质检 6、已质检未发货 7、完成生产)
     */
    private Integer status;
}
