
package vip.xiaonuo.main.modular.sizeconfig.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ErrorResponseData;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.sizeconfig.param.SizeConfigParam;
import vip.xiaonuo.main.modular.sizeconfig.service.SizeConfigService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 尺码配置控制器
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:59
 */
@Api(tags = "尺码配置")
@RestController
public class SizeConfigController {

    @Resource
    private SizeConfigService sizeConfigService;

    /**
     * 查询尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    @Permission
    @GetMapping("/sizeConfig/page")
    @BusinessLog(title = "尺码配置_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("尺码配置_分页查询")
    public ResponseData page(SizeConfigParam sizeConfigParam) {
        return new SuccessResponseData(sizeConfigService.page(sizeConfigParam));
    }

    /**
     * 添加尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    @Permission
    @PostMapping("/sizeConfig/add")
    @BusinessLog(title = "尺码配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("尺码配置_增加")
    public ResponseData add(@RequestBody @Validated(SizeConfigParam.add.class) SizeConfigParam sizeConfigParam) {
        sizeConfigService.add(sizeConfigParam);
        return new SuccessResponseData();
    }

    /**
     * 删除尺码配置，可批量删除
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    @Permission
    @PostMapping("/sizeConfig/delete")
    @BusinessLog(title = "尺码配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("尺码配置_删除")
    public ResponseData delete(@RequestBody @Validated(SizeConfigParam.delete.class) List<SizeConfigParam> sizeConfigParamList) {
        sizeConfigService.delete(sizeConfigParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    @Permission
    @PostMapping("/sizeConfig/edit")
    @BusinessLog(title = "尺码配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("尺码配置_编辑")
    public ResponseData edit(@RequestBody @Validated(SizeConfigParam.edit.class) SizeConfigParam sizeConfigParam) {
        sizeConfigService.edit(sizeConfigParam);
        return new SuccessResponseData();
    }

    /**
     * 查看尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    @Permission
    @GetMapping("/sizeConfig/detail")
    @BusinessLog(title = "尺码配置_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("尺码配置_查看")
    public ResponseData detail(@Validated(SizeConfigParam.detail.class) SizeConfigParam sizeConfigParam) {
        return new SuccessResponseData(sizeConfigService.detail(sizeConfigParam));
    }

    /**
     * 尺码配置列表
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    @Permission
    @GetMapping("/sizeConfig/list")
    @BusinessLog(title = "尺码配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("尺码配置_列表")
    public ResponseData list(SizeConfigParam sizeConfigParam) {
        return new SuccessResponseData(sizeConfigService.list(sizeConfigParam));
    }

    /**
     * 导出尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    @Permission
    @GetMapping("/sizeConfig/export")
    @BusinessLog(title = "尺码配置_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("尺码配置_导出")
    public void export(SizeConfigParam sizeConfigParam) {
        sizeConfigService.export(sizeConfigParam);
    }

    @GetMapping("/sizeConfig/checkCateName")
    @BusinessLog(title = "校验尺码组名称", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("校验尺码组名称")
    @ApiImplicitParam(name = "cateName", value = "尺码组名称", required = true, dataType = "string")
    public ResponseData checkCateName(String cateName) {
        int resultNum = sizeConfigService.checkCateName(cateName);
        return resultNum > 0 ? new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE, "尺码组名称已存在！") : new SuccessResponseData();
    }

    @Permission
    @PostMapping("/sizeConfig/saveDefault")
    @BusinessLog(title = "尺码配置_设为默认", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("尺码配置_设为默认")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public ResponseData saveDefault(Long id) {
        int resultNum = sizeConfigService.saveDefault(id);
        return resultNum == 0 ? new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE, "保存失败！") : new SuccessResponseData();
    }

    @GetMapping("/sizeConfig/getSizeInforList")
    @BusinessLog(title = "查询尺码信息List", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("查询尺码信息List")
    @ApiImplicitParam(name = "id", value = "尺码组id 0=查询默认尺码组信息", required = true)
    public ResponseData getSizeInforList(Long id) {
        return new SuccessResponseData(sizeConfigService.getSizeInforList(id));
    }

}
