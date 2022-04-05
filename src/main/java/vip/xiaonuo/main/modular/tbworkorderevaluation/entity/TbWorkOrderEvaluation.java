/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Snowy源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuobase/snowy-cloud
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuobase/snowy-cloud
6.若您的项目无法满足以上几点，可申请商业授权，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.main.modular.tbworkorderevaluation.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 工单评价
 *
 * @author maksim
 * @date 2022-01-11 17:17:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_work_order_evaluation")
public class TbWorkOrderEvaluation extends BaseEntity {

    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 物流
     */
    @Excel(name = "物流")
    private Integer logistics;

    /**
     * 其他
     */
    @Excel(name = "其他")
    private Integer other;

    /**
     * 时效
     */
    @Excel(name = "时效")
    private Integer prescription;

    /**
     * 品质
     */
    @Excel(name = "品质")
    private Integer quality;

    /**
     * 服务态度
     */
    @Excel(name = "服务态度")
    private Integer serviceAttitude;

    /**
     * 订单id
     */
    @Excel(name = "订单id")
    private Long workerOrderId;

    @TableField(exist = false)
    private String workOrderNo;

    /**
     * 物流备注
     */
    @Excel(name = "物流备注")
    private String logisticsNote;

    /**
     * 品质备注
     */
    @Excel(name = "品质备注")
    private String qualityNote;

    /**
     * 时效备注
     */
    @Excel(name = "时效备注")
    private String prescriptionNote;

    /**
     * 服务态度备注
     */
    @Excel(name = "服务态度备注")
    private String serviceAttitudeNote;

    /**
     * 其他备注
     */
    @Excel(name = "其他备注")
    private String otherNote;

    /**
     *  工厂名称
     */
    @TableField(exist = false)
    private String factoryName;

    /**
     *  工单-单量
     */
    @TableField(exist = false)
    private Long workOrderCount;
}
