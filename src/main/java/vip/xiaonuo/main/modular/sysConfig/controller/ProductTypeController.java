
package vip.xiaonuo.main.modular.sysConfig.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ErrorResponseData;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import vip.xiaonuo.main.modular.sysConfig.entity.ProductType;
import vip.xiaonuo.main.modular.sysConfig.param.ProductTypeParam;
import vip.xiaonuo.main.modular.sysConfig.service.ProductTypeService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品类控制器
 *
 * @author 邾茂星
 * @date 2022-01-10 16:03:10
 */
@Api(tags = "商品分类")
@RestController
public class ProductTypeController {

    @Resource
    private ProductTypeService productTypeService;

    /**
     * 查询品类
     *
     * @author 邾茂星
     * @date 2022-01-10 16:03:10
     */
    @Permission
    @GetMapping("/productType/page")
    @BusinessLog(title = "品类_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("品类_分页查询")
    public ResponseData page(ProductTypeParam productTypeParam) {
        return new SuccessResponseData(productTypeService.page(productTypeParam));
    }

    /**
     * 添加品类
     *
     * @author 邾茂星
     * @date 2022-01-10 16:03:10
     */
    @Permission
    @PostMapping("/productType/add")
    @BusinessLog(title = "品类_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("品类_增加")
    public ResponseData add(@RequestBody @Validated(ProductTypeParam.add.class) ProductTypeParam productTypeParam) {
        productTypeService.add(productTypeParam);
        return new SuccessResponseData();
    }

    /**
     * 删除品类，可批量删除
     *
     * @author 邾茂星
     * @date 2022-01-10 16:03:10
     */
    @Permission
    @PostMapping("/productType/delete")
    @BusinessLog(title = "品类_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("品类_删除")
    public ResponseData delete(@RequestBody @Validated(ProductTypeParam.delete.class) List<ProductTypeParam> productTypeParamList) {
        productTypeService.delete(productTypeParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑品类
     *
     * @author 邾茂星
     * @date 2022-01-10 16:03:10
     */
    @Permission
    @PostMapping("/productType/edit")
    @BusinessLog(title = "品类_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("品类_编辑")
    public ResponseData edit(@RequestBody @Validated(ProductTypeParam.edit.class) ProductTypeParam productTypeParam) {
        productTypeService.edit(productTypeParam);
        return new SuccessResponseData();
    }

    /**
     * 查看品类
     *
     * @author 邾茂星
     * @date 2022-01-10 16:03:10
     */
    @GetMapping("/productType/detail")
    @BusinessLog(title = "品类_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("品类_查看")
    public ResponseData detail(@Validated(ProductTypeParam.detail.class) ProductTypeParam productTypeParam) {
        return new SuccessResponseData(productTypeService.detail(productTypeParam));
    }

    /**
     * 品类列表
     *
     * @author 邾茂星
     * @date 2022-01-10 16:03:10
     */
    @Permission
    @GetMapping("/productType/list")
    @BusinessLog(title = "品类_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("品类树形列表")
    public ResponseData list(ProductTypeParam productTypeParam) {
        return new SuccessResponseData(productTypeService.list(productTypeParam));
    }

    /**
     * 导出品类
     *
     * @author 邾茂星
     * @date 2022-01-10 16:03:10
     */
    @Permission
    @GetMapping("/productType/export")
    @BusinessLog(title = "品类_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("品类_导出")
    public void export(ProductTypeParam productTypeParam) {
        productTypeService.export(productTypeParam);
    }

    @GetMapping("/productType/findListByPid")
    @BusinessLog(title = "品类_pid查询", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("品类_pid查询")
    @ApiImplicitParam(name = "pid", value = "pid 0=查询一级", required = true)
    public ResponseData findListByPid(@RequestParam("pid") Long pid){
        return new SuccessResponseData(productTypeService.findListByPid(pid));
    }

    @GetMapping("/productType/checkTypeName")
    @BusinessLog(title = "校验品类名称", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("校验品类名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "父id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "typeName", value = "品类名称", required = true, dataType = "string")
    })
    public ResponseData checkTypeName(@RequestParam("pid") Long pid, @RequestParam("typeName") String typeName){
        int resNumber = productTypeService.checkTypeName(pid, typeName);
        return resNumber == 0 ? new SuccessResponseData() : new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE, "品类名称重复！");
    }

    @GetMapping("/productType/findSortList")
    @BusinessLog(title = "查询品类", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("查询品类")
    public ResponseData findSortList(Integer depth){
        return new SuccessResponseData(productTypeService.findSortList(depth));
    }
}
