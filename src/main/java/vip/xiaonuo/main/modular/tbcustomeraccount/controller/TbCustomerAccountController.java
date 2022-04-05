
package vip.xiaonuo.main.modular.tbcustomeraccount.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tbcustomeraccount.entity.TbCustomerAccount;
import vip.xiaonuo.main.modular.tbcustomeraccount.param.TbCustomerAccountParam;
import vip.xiaonuo.main.modular.tbcustomeraccount.service.TbCustomerAccountService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户账号控制器
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:03
 */
@Api(tags = "客户账号")
@RestController
public class TbCustomerAccountController {

    @Resource
    private TbCustomerAccountService tbCustomerAccountService;

    /**
     * 查询客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    @Permission
    @GetMapping("/tbCustomerAccount/page")
    @BusinessLog(title = "客户账号_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("客户账号_分页查询")
    public ResponseData page(TbCustomerAccountParam tbCustomerAccountParam) {
        return new SuccessResponseData(tbCustomerAccountService.page(tbCustomerAccountParam));
    }

    /**
     * 查询客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    //@Permission
    @GetMapping("/tbCustomerAccount/search")
    @BusinessLog(title = "客户账号_模糊查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("客户账号_模糊查询")
    public ResponseData search(TbCustomerAccountParam tbCustomerAccountParam) {
        return new SuccessResponseData(tbCustomerAccountService.search(tbCustomerAccountParam));
    }
    /**
     * 添加客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    @Permission
    @PostMapping("/tbCustomerAccount/add")
    @BusinessLog(title = "客户账号_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("客户账号_增加")
    public ResponseData add(@RequestBody @Validated(TbCustomerAccountParam.add.class) TbCustomerAccountParam tbCustomerAccountParam) {
            tbCustomerAccountService.add(tbCustomerAccountParam);
        return new SuccessResponseData();
    }

    /**
     * 删除客户账号，可批量删除
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    @Permission
    @PostMapping("/tbCustomerAccount/delete")
    @BusinessLog(title = "客户账号_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("客户账号_删除")
    public ResponseData delete(@RequestBody @Validated(TbCustomerAccountParam.delete.class) List<TbCustomerAccountParam> tbCustomerAccountParamList) {
            tbCustomerAccountService.delete(tbCustomerAccountParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    @Permission
    @PostMapping("/tbCustomerAccount/edit")
    @BusinessLog(title = "客户账号_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("客户账号_编辑")
    public ResponseData edit(@RequestBody @Validated(TbCustomerAccountParam.edit.class) TbCustomerAccountParam tbCustomerAccountParam) {
            tbCustomerAccountService.edit(tbCustomerAccountParam);
        return new SuccessResponseData();
    }

    /**
     * 查看客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    @Permission
    @GetMapping("/tbCustomerAccount/detail")
    @BusinessLog(title = "客户账号_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("客户账号_查看")
    public ResponseData detail(@Validated(TbCustomerAccountParam.detail.class) TbCustomerAccountParam tbCustomerAccountParam) {
        return new SuccessResponseData(tbCustomerAccountService.detail(tbCustomerAccountParam));
    }

    /**
     * 客户账号列表
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    @Permission
    @GetMapping("/tbCustomerAccount/list")
    @BusinessLog(title = "客户账号_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("客户账号_列表")
    public ResponseData list(TbCustomerAccountParam tbCustomerAccountParam) {
        return new SuccessResponseData(tbCustomerAccountService.list(tbCustomerAccountParam));
    }

    /**
     * 导出客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    @Permission
    @GetMapping("/tbCustomerAccount/export")
    @BusinessLog(title = "客户账号_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("客户账号_导出")
    public void export(TbCustomerAccountParam tbCustomerAccountParam) {
        tbCustomerAccountService.export(tbCustomerAccountParam);
    }

    @GetMapping("/mobile/tbCustomerAccount/getById")
    @BusinessLog(title = "客户账号_id查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("客户账号_id查询")
    public TbCustomerAccount getById(@RequestParam("id") Long id) {
        return tbCustomerAccountService.getById(id);
    }

    @GetMapping("/mobile/tbCustomerAccount/delRedisKey")
    @BusinessLog(title = "清除RedisKey", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("清除RedisKey")
    public ResponseData delRedisKey(@RequestParam("id") Long id){
        return new SuccessResponseData(tbCustomerAccountService.delRedisKey(id));
    }

    @GetMapping("/mobile/tbCustomerAccount/getByCustomerId")
    @BusinessLog(title = "同所属公司客户", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("同所属公司客户")
    @ApiImplicitParam(name = "customerId", value = "公司id", required = true, dataType = "long")
    public ResponseData getByCustomerId(@RequestParam("customerId") Long customerId) {
        return new SuccessResponseData(tbCustomerAccountService.getByCustomerId(customerId));
    }

}
