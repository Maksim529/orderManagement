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
package vip.xiaonuo.main.modular.tbcontract.controller;

import io.swagger.annotations.Api;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tbcontract.param.TbContractParam;
import vip.xiaonuo.main.modular.tbcontract.service.TbContractService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 合同信息控制器
 *
 * @author maksim
 * @date 2022-01-11 17:03:53
 */
@RestController
@Api(tags = "合同信息")
public class TbContractController {

    @Resource
    private TbContractService tbContractService;

    /**
     * 查询合同信息
     *
     * @author maksim
     * @date 2022-01-11 17:03:53
     */
    @Permission
    @GetMapping("/tbContract/page")
    @BusinessLog(title = "合同信息_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(TbContractParam tbContractParam) {
        return new SuccessResponseData(tbContractService.page(tbContractParam));
    }

    /**
     * 添加合同信息
     *
     * @author maksim
     * @date 2022-01-11 17:03:53
     */
    @Permission
    @PostMapping("/tbContract/add")
    @BusinessLog(title = "合同信息_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(TbContractParam.add.class) TbContractParam tbContractParam) {
            tbContractService.add(tbContractParam);
        return new SuccessResponseData();
    }

    /**
     * 删除合同信息，可批量删除
     *
     * @author maksim
     * @date 2022-01-11 17:03:53
     */
    @Permission
    @PostMapping("/tbContract/delete")
    @BusinessLog(title = "合同信息_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(TbContractParam.delete.class) List<TbContractParam> tbContractParamList) {
            tbContractService.delete(tbContractParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑合同信息
     *
     * @author maksim
     * @date 2022-01-11 17:03:53
     */
    @Permission
    @PostMapping("/tbContract/edit")
    @BusinessLog(title = "合同信息_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(TbContractParam.edit.class) TbContractParam tbContractParam) {
            tbContractService.edit(tbContractParam);
        return new SuccessResponseData();
    }

    /**
     * 查看合同信息
     *
     * @author maksim
     * @date 2022-01-11 17:03:53
     */
    @Permission
    @GetMapping("/tbContract/detail")
    @BusinessLog(title = "合同信息_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(TbContractParam.detail.class) TbContractParam tbContractParam) {
        return new SuccessResponseData(tbContractService.detail(tbContractParam));
    }

    /**
     * 合同信息列表
     *
     * @author maksim
     * @date 2022-01-11 17:03:53
     */
    @Permission
    @GetMapping("/tbContract/list")
    @BusinessLog(title = "合同信息_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(TbContractParam tbContractParam) {
        return new SuccessResponseData(tbContractService.list(tbContractParam));
    }

    /**
     * 导出合同信息
     *
     * @author maksim
     * @date 2022-01-11 17:03:53
     */
    @Permission
    @GetMapping("/tbContract/export")
    @BusinessLog(title = "合同信息_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(TbContractParam tbContractParam) {
        tbContractService.export(tbContractParam);
    }

}
