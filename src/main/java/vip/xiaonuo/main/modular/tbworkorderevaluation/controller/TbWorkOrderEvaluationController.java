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
package vip.xiaonuo.main.modular.tbworkorderevaluation.controller;

import io.swagger.annotations.Api;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tbworkorderevaluation.param.TbWorkOrderEvaluationParam;
import vip.xiaonuo.main.modular.tbworkorderevaluation.service.TbWorkOrderEvaluationService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 工单评价控制器
 *
 * @author maksim
 * @date 2022-01-11 17:17:47
 */
@RestController
@Api(tags = "工单评价")
public class TbWorkOrderEvaluationController {

    @Resource
    private TbWorkOrderEvaluationService tbWorkOrderEvaluationService;

    /**
     * 查询工单评价
     *
     * @author maksim
     * @date 2022-01-11 17:17:47
     */
    @Permission
    @GetMapping("/tbWorkOrderEvaluation/page")
    @BusinessLog(title = "工单评价_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        return new SuccessResponseData(tbWorkOrderEvaluationService.page(tbWorkOrderEvaluationParam));
    }

    /**
     * 添加工单评价
     *
     * @author maksim
     * @date 2022-01-11 17:17:47
     */
    @Permission
    @PostMapping("/tbWorkOrderEvaluation/add")
    @BusinessLog(title = "工单评价_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(TbWorkOrderEvaluationParam.add.class) TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
            tbWorkOrderEvaluationService.add(tbWorkOrderEvaluationParam);
        return new SuccessResponseData();
    }

    /**
     * 删除工单评价，可批量删除
     *
     * @author maksim
     * @date 2022-01-11 17:17:47
     */
    @Permission
    @PostMapping("/tbWorkOrderEvaluation/delete")
    @BusinessLog(title = "工单评价_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(TbWorkOrderEvaluationParam.delete.class) List<TbWorkOrderEvaluationParam> tbWorkOrderEvaluationParamList) {
            tbWorkOrderEvaluationService.delete(tbWorkOrderEvaluationParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑工单评价
     *
     * @author maksim
     * @date 2022-01-11 17:17:47
     */
    @Permission
    @PostMapping("/tbWorkOrderEvaluation/edit")
    @BusinessLog(title = "工单评价_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(TbWorkOrderEvaluationParam.edit.class) TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
            tbWorkOrderEvaluationService.edit(tbWorkOrderEvaluationParam);
        return new SuccessResponseData();
    }

    /**
     * 查看工单评价
     *
     * @author maksim
     * @date 2022-01-11 17:17:47
     */
    @Permission
    @GetMapping("/tbWorkOrderEvaluation/detail")
    @BusinessLog(title = "工单评价_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(TbWorkOrderEvaluationParam.detail.class) TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        return new SuccessResponseData(tbWorkOrderEvaluationService.detail(tbWorkOrderEvaluationParam));
    }

    /**
     * 工单评价列表
     *
     * @author maksim
     * @date 2022-01-11 17:17:47
     */
    @Permission
    @GetMapping("/tbWorkOrderEvaluation/list")
    @BusinessLog(title = "工单评价_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        return new SuccessResponseData(tbWorkOrderEvaluationService.list(tbWorkOrderEvaluationParam));
    }

    /**
     * 导出工单评价
     *
     * @author maksim
     * @date 2022-01-11 17:17:47
     */
    @Permission
    @GetMapping("/tbWorkOrderEvaluation/export")
    @BusinessLog(title = "工单评价_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        tbWorkOrderEvaluationService.export(tbWorkOrderEvaluationParam);
    }

}
