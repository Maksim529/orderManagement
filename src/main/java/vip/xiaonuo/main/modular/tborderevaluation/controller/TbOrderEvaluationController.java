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
package vip.xiaonuo.main.modular.tborderevaluation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tborderevaluation.param.TbOrderEvaluationParam;
import vip.xiaonuo.main.modular.tborderevaluation.service.TbOrderEvaluationService;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单评价控制器
 *
 * @author maksim
 * @date 2022-01-11 17:12:53
 */
@RestController
@Api(tags = "订单评价")
public class TbOrderEvaluationController {

    @Resource
    private TbOrderEvaluationService tbOrderEvaluationService;

    /**
     * 查询订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    @Permission
    @GetMapping("/tbOrderEvaluation/page")
    @BusinessLog(title = "订单评价_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(TbOrderEvaluationParam tbOrderEvaluationParam) {
        return new SuccessResponseData(tbOrderEvaluationService.page(tbOrderEvaluationParam));
    }

    /**
     * 添加订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
//    @Permission
    @PostMapping("/tbOrderEvaluation/add")
    @BusinessLog(title = "订单评价_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(TbOrderEvaluationParam.add.class) TbOrderEvaluationParam tbOrderEvaluationParam) {
            tbOrderEvaluationService.add(tbOrderEvaluationParam);
        return new SuccessResponseData();
    }

    /**
     * 删除订单评价，可批量删除
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    @Permission
    @PostMapping("/tbOrderEvaluation/delete")
    @BusinessLog(title = "订单评价_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(TbOrderEvaluationParam.delete.class) List<TbOrderEvaluationParam> tbOrderEvaluationParamList) {
            tbOrderEvaluationService.delete(tbOrderEvaluationParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    @Permission
    @PostMapping("/tbOrderEvaluation/edit")
    @BusinessLog(title = "订单评价_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(TbOrderEvaluationParam.edit.class) TbOrderEvaluationParam tbOrderEvaluationParam) {
            tbOrderEvaluationService.edit(tbOrderEvaluationParam);
        return new SuccessResponseData();
    }

    /**
     * 查看订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    @Permission
    @GetMapping("/tbOrderEvaluation/detail")
    @BusinessLog(title = "订单评价_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(TbOrderEvaluationParam.detail.class) TbOrderEvaluationParam tbOrderEvaluationParam) {
        return new SuccessResponseData(tbOrderEvaluationService.detail(tbOrderEvaluationParam));
    }

    /**
     * 订单评价列表
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    @Permission
    @GetMapping("/tbOrderEvaluation/list")
    @BusinessLog(title = "订单评价_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(TbOrderEvaluationParam tbOrderEvaluationParam) {
        return new SuccessResponseData(tbOrderEvaluationService.list(tbOrderEvaluationParam));
    }

    /**
     * 导出订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    @Permission
    @GetMapping("/tbOrderEvaluation/export")
    @BusinessLog(title = "订单评价_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(TbOrderEvaluationParam tbOrderEvaluationParam) {
        tbOrderEvaluationService.export(tbOrderEvaluationParam);
    }

    @GetMapping("/tbOrderEvaluation/getByOrderId")
    @BusinessLog(title = "查看订单评价", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("订单评价_查看")
    public ResponseData getByOrderId(@RequestParam("orderId") Long orderId) {
        return new SuccessResponseData(tbOrderEvaluationService.getByOrderId(orderId));
    }

}
