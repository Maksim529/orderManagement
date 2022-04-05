
package vip.xiaonuo.main.modular.tbcustomerask.param;

import lombok.Data;
import vip.xiaonuo.common.pojo.base.param.BaseParam;

import java.math.BigDecimal;

/**
* 客户询价单参数类
 *
 * @author 邾茂星
 * @date 2022-01-10 15:02:32
*/
@Data
public class TbCustomerAskParam extends BaseParam {

    /**
     * 
     */
    private Long id;

    /**
     * 询价单号
     */
    private String askOrderNo;

    /**
     * 客户款号
     */
    private String customerSku;

    /**
     * 图片
     */
    private String pic;

    /**
     * 品类
     */
    private Long category;

    /**
     * 状态（1、CMT2、FOB）
     */
    private Integer produceType;

    /**
     * 颜色
     */
    private Long color;

    /**
     * 尺码（字典枚举）
     */
    private Long size;

    /**
     * 单位（字典枚举）
     */
    private Long unit;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 订单状态
     */
    private Integer askStatus;

    /**
     * 订单数量
     */
    private Long counts;

    /**
     * 期望交期
     */
    private String givedate;

    /**
     * 期望交期
     */
    private String materialReachDate;

    /**
     * 收货地址
     */
    private String receiveAddr;

    /**
     * 其他要求备注
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
     * 
     */
    private Integer servicePriceTime;

    private Long accountId;

    /**
     * 客户账号名称
     */
    private String customerAccountName;
}
