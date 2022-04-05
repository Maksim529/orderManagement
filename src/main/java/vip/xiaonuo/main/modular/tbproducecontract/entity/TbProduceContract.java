
package vip.xiaonuo.main.modular.tbproducecontract.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 加工合同
 *
 * @author wjc
 * @date 2022-01-20 10:11:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_produce_contract")
public class TbProduceContract extends BaseEntity {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String contractNo;

    /**
     * 订单id
     */
    @Excel(name = "订单id")
    private Long workOrderId;

    /**
     * 工厂方
     */
    @Excel(name = "工厂方")
    private Long factoryId;

    /**
     * 乙方
     */
    @Excel(name = "乙方")
    private String service;

    /**
     * 
     */
    @Excel(name = "")
    private Integer syscreated;

}
