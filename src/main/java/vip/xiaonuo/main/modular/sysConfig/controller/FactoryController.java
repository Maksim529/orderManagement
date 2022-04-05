
package vip.xiaonuo.main.modular.sysConfig.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import vip.xiaonuo.main.modular.sysConfig.param.FactoryParam;
import vip.xiaonuo.main.modular.sysConfig.service.FactoryService;
import vip.xiaonuo.main.modular.sysConfig.service.TbFactoryMerchandiserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工厂信息控制器
 *
 * @author 邾茂星
 * @date 2022-01-10 16:37:50
 */
@Api(tags = "工厂信息")
@RestController
public class FactoryController {

    @Resource
    private FactoryService factoryService;
    @Autowired
    private TbFactoryMerchandiserService tbFactoryMerchandiserService;

    /**
     * 查询工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    @Permission
    @GetMapping("/factory/page")
    @BusinessLog(title = "工厂信息_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("工厂信息_分页查询")
    public ResponseData page(FactoryParam factoryParam) {
        return new SuccessResponseData(factoryService.page(factoryParam));
    }

    /**
     * 添加工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    @Permission
    @PostMapping("/factory/add")
    @BusinessLog(title = "工厂信息_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("工厂信息_增加")
    public ResponseData add(@RequestBody @Validated(FactoryParam.add.class) FactoryParam factoryParam) {
            factoryService.add(factoryParam);
        return new SuccessResponseData();
    }

    /**
     * 删除工厂信息，可批量删除
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    @Permission
    @PostMapping("/factory/delete")
    @BusinessLog(title = "工厂信息_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("工厂信息_删除")
    public ResponseData delete(@RequestBody @Validated(FactoryParam.delete.class) List<FactoryParam> factoryParamList) {
            factoryService.delete(factoryParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    @Permission
    @PostMapping("/factory/edit")
    @BusinessLog(title = "工厂信息_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("工厂信息_编辑")
    public ResponseData edit(@RequestBody @Validated(FactoryParam.edit.class) FactoryParam factoryParam) {
            factoryService.edit(factoryParam);
        return new SuccessResponseData();
    }

    /**
     * 查看工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    @Permission
    @GetMapping("/factory/detail")
    @BusinessLog(title = "工厂信息_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("工厂信息_查看")
    public ResponseData detail(@Validated(FactoryParam.detail.class) FactoryParam factoryParam) {
        return new SuccessResponseData(factoryService.detail(factoryParam));
    }

    /**
     * 工厂信息列表
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    @Permission
    @GetMapping("/factory/list")
    @BusinessLog(title = "工厂信息_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("工厂信息_列表")
    public ResponseData list(FactoryParam factoryParam) {
        return new SuccessResponseData(factoryService.list(factoryParam));
    }

    /**
     * 导出工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    @Permission
    @GetMapping("/factory/export")
    @BusinessLog(title = "工厂信息_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("工厂信息_导出")
    public void export(FactoryParam factoryParam) {
        factoryService.export(factoryParam);
    }

    @GetMapping("/mobile/factory/getFactoryIdListByUserId")
    @ApiOperation("根据userID查询所跟单的工厂id")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public List<Long> getFactoryIdListByUserId(@RequestParam("userId") Long userId){
        return tbFactoryMerchandiserService.getFactoryIdListByUserId(userId);
    }

}
