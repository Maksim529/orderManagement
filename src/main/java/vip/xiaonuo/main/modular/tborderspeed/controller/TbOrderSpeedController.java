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
package vip.xiaonuo.main.modular.tborderspeed.controller;

import io.swagger.annotations.Api;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tborderspeed.param.TbOrderSpeedParam;
import vip.xiaonuo.main.modular.tborderspeed.service.TbOrderSpeedService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 订单进度控制器
 *
 * @author maksim
 * @date 2022-01-11 16:53:00
 */
@RestController
@Api(tags = "订单进度")
public class TbOrderSpeedController {

    @Resource
    private TbOrderSpeedService tbOrderSpeedService;

    /**
     * 查询订单进度
     *
     * @author maksim
     * @date 2022-01-11 16:53:00
     */
    @Permission
    @GetMapping("/tbOrderSpeed/page")
    @BusinessLog(title = "订单进度_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(TbOrderSpeedParam tbOrderSpeedParam) {
        return new SuccessResponseData(tbOrderSpeedService.page(tbOrderSpeedParam));
    }

    /**
     * 添加订单进度
     *
     * @author maksim
     * @date 2022-01-11 16:53:00
     */
    @Permission
    @PostMapping("/tbOrderSpeed/add")
    @BusinessLog(title = "订单进度_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(TbOrderSpeedParam.add.class) TbOrderSpeedParam tbOrderSpeedParam) {
            tbOrderSpeedService.add(tbOrderSpeedParam);
        return new SuccessResponseData();
    }

    /**
     * 删除订单进度，可批量删除
     *
     * @author maksim
     * @date 2022-01-11 16:53:00
     */
    @Permission
    @PostMapping("/tbOrderSpeed/delete")
    @BusinessLog(title = "订单进度_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(TbOrderSpeedParam.delete.class) List<TbOrderSpeedParam> tbOrderSpeedParamList) {
            tbOrderSpeedService.delete(tbOrderSpeedParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑订单进度
     *
     * @author maksim
     * @date 2022-01-11 16:53:00
     */
    @Permission
    @PostMapping("/tbOrderSpeed/edit")
    @BusinessLog(title = "订单进度_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(TbOrderSpeedParam.edit.class) TbOrderSpeedParam tbOrderSpeedParam) {
            tbOrderSpeedService.edit(tbOrderSpeedParam);
        return new SuccessResponseData();
    }

    /**
     * 查看订单进度
     *
     * @author maksim
     * @date 2022-01-11 16:53:00
     */
    @Permission
    @GetMapping("/tbOrderSpeed/detail")
    @BusinessLog(title = "订单进度_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(TbOrderSpeedParam.detail.class) TbOrderSpeedParam tbOrderSpeedParam) {
        return new SuccessResponseData(tbOrderSpeedService.detail(tbOrderSpeedParam));
    }

    /**
     * 订单进度列表
     *
     * @author maksim
     * @date 2022-01-11 16:53:00
     */
    @Permission
    @GetMapping("/tbOrderSpeed/list")
    @BusinessLog(title = "订单进度_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(TbOrderSpeedParam tbOrderSpeedParam) {
        return new SuccessResponseData(tbOrderSpeedService.list(tbOrderSpeedParam));
    }

    /**
     * 导出订单进度
     *
     * @author maksim
     * @date 2022-01-11 16:53:00
     */
    @Permission
    @GetMapping("/tbOrderSpeed/export")
    @BusinessLog(title = "订单进度_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(TbOrderSpeedParam tbOrderSpeedParam) {
        tbOrderSpeedService.export(tbOrderSpeedParam);
    }

}
