package vip.xiaonuo.main.modular.tbcustomerorder.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 邾茂星
 * @Description: 生产订单导出 VO
 * @date 2022/1/22 10:28
 */
@Data
public class CustomerOrderExportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "订单号", width = 30)
    private String orderNo;
    @Excel(name = "客户")
    private String customerName;
    @Excel(name = "客户电话", width = 20)
    private String customerTel;
    @Excel(name = "客户款号", width = 30)
    private String customerSku;
    @Excel(name = "品类", width = 20)
    private String strCategory;
    @Excel(name = "生产类型", width = 20)
    private String produceType;
    @Excel(name = "价格")
    private BigDecimal price;
    @Excel(name = "总数量")
    private Integer amount;
    @Excel(name = "交期", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date givedate;
    @Excel(name = "省市区", width = 30)
    private String areacode;
    @Excel(name = "收货地址", width = 20)
    private String receiveAddr;
    @Excel(name = "收货人")
    private String receivePerson;
    @Excel(name = "收货电话", width = 20)
    private String receivePhone;
    @Excel(name = "接单负责人")
    private String principalName;
    @Excel(name = "状态")
    private String strStatus;
    @Excel(name = "创建时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date createTime;
    @Excel(name = "备注", width = 50)
    private String remark;

    /**
     *  品类
     */
    private Long category;

}
