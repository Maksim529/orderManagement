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
package vip.xiaonuo.main.modular.tbworkerorderspeed.param;

import vip.xiaonuo.common.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
* 生产进度参数类
 *
 * @author maksim
 * @date 2022-01-19 16:04:54
*/
@Data
public class TbWorkerorderSpeedParam extends BaseParam {

    /**
     * 后整时间
     */
    @NotNull(message = "后整时间不能为空，请检查afterCompletion参数", groups = {add.class, edit.class})
    private String afterCompletion;

    /**
     * 裁剪完成时间
     */
    @NotNull(message = "裁剪完成时间不能为空，请检查cutCompletion参数", groups = {add.class, edit.class})
    private String cutCompletion;

    /**
     * 实裁数量
     */
    @NotNull(message = "实裁数量不能为空，请检查cutNum参数", groups = {add.class, edit.class})
    private Integer cutNum;

    /**
     * 组检时间
     */
    @NotNull(message = "组检时间不能为空，请检查groupInspection参数", groups = {add.class, edit.class})
    private String groupInspection;

    /**
     * 
     */
    @NotNull(message = "不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 下线时间
     */
    @NotNull(message = "下线时间不能为空，请检查offlineTime参数", groups = {add.class, edit.class})
    private String offlineTime;

    /**
     * 上线时间
     */
    @NotNull(message = "上线时间不能为空，请检查onlineTime参数", groups = {add.class, edit.class})
    private String onlineTime;

    /**
     * 装箱发货时间
     */
    @NotNull(message = "装箱发货时间不能为空，请检查packingDelivery参数", groups = {add.class, edit.class})
    private String packingDelivery;

    /**
     * 齐套日期
     */
    @NotNull(message = "齐套日期不能为空，请检查qtDate参数", groups = {add.class, edit.class})
    private String qtDate;

    /**
     * 接单时间
     */
    @NotNull(message = "接单时间不能为空，请检查recieveOrderTime参数", groups = {add.class, edit.class})
    private String recieveOrderTime;

    /**
     * 缝制完成时间
     */
    @NotNull(message = "缝制完成时间不能为空，请检查sewingCompletion参数", groups = {add.class, edit.class})
    private String sewingCompletion;

    /**
     * 缝制完成数量
     */
    @NotNull(message = "缝制完成数量不能为空，请检查sewingCompletionNum参数", groups = {add.class, edit.class})
    private Integer sewingCompletionNum;

    /**
     * 实际出货数量
     */
    @NotNull(message = "实际出货数量不能为空，请检查shippingQty参数", groups = {add.class, edit.class})
    private Integer shippingQty;

    /**
     * 是否后台创建
     */
    @NotNull(message = "是否后台创建不能为空，请检查syscreated参数", groups = {add.class, edit.class})
    private Integer syscreated;

    /**
     * 总检时间
     */
    @NotNull(message = "总检时间不能为空，请检查totalInspection参数", groups = {add.class, edit.class})
    private String totalInspection;

    /**
     * 订单id
     */
    @NotNull(message = "订单id不能为空，请检查workOrderId参数", groups = {add.class, edit.class})
    private Long workOrderId;

    private String workOrderNo;
}
