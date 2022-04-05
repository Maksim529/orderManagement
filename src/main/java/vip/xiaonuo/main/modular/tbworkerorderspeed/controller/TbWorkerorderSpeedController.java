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
package vip.xiaonuo.main.modular.tbworkerorderspeed.controller;

import io.swagger.annotations.Api;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tbworkerorderspeed.param.TbWorkerorderSpeedParam;
import vip.xiaonuo.main.modular.tbworkerorderspeed.service.TbWorkerorderSpeedService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 生产进度控制器
 *
 * @author maksim
 * @date 2022-01-13 10:35:23
 */
@RestController
@Api(tags = "生产工单进度")
public class TbWorkerorderSpeedController {

    @Resource
    private TbWorkerorderSpeedService tbWorkerorderSpeedService;

    /**
     * 查询生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    @Permission
    @GetMapping("/tbWorkerorderSpeed/page")
    @BusinessLog(title = "生产进度_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        return new SuccessResponseData(tbWorkerorderSpeedService.page(tbWorkerorderSpeedParam));
    }

    /**
     * 添加生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    @Permission
    @PostMapping("/tbWorkerorderSpeed/add")
    @BusinessLog(title = "生产进度_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(TbWorkerorderSpeedParam.add.class) TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
            tbWorkerorderSpeedService.add(tbWorkerorderSpeedParam);
        return new SuccessResponseData();
    }

    /**
     * 删除生产进度，可批量删除
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    @Permission
    @PostMapping("/tbWorkerorderSpeed/delete")
    @BusinessLog(title = "生产进度_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(TbWorkerorderSpeedParam.delete.class) List<TbWorkerorderSpeedParam> tbWorkerorderSpeedParamList) {
            tbWorkerorderSpeedService.delete(tbWorkerorderSpeedParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    @Permission
    @PostMapping("/tbWorkerorderSpeed/edit")
    @BusinessLog(title = "生产进度_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(TbWorkerorderSpeedParam.edit.class) TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
            tbWorkerorderSpeedService.edit(tbWorkerorderSpeedParam);
        return new SuccessResponseData();
    }

    /**
     * 查看生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    @Permission
    @GetMapping("/tbWorkerorderSpeed/detail")
    @BusinessLog(title = "生产进度_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(TbWorkerorderSpeedParam.detail.class) TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        return new SuccessResponseData(tbWorkerorderSpeedService.detail(tbWorkerorderSpeedParam));
    }

    /**
     * 生产进度列表
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    @Permission
    @GetMapping("/tbWorkerorderSpeed/list")
    @BusinessLog(title = "生产进度_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        return new SuccessResponseData(tbWorkerorderSpeedService.list(tbWorkerorderSpeedParam));
    }

    /**
     * 导出生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    @Permission
    @GetMapping("/tbWorkerorderSpeed/export")
    @BusinessLog(title = "生产进度_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        tbWorkerorderSpeedService.export(tbWorkerorderSpeedParam);
    }

}
