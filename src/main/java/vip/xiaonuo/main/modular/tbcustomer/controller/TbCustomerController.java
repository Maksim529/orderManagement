
package vip.xiaonuo.main.modular.tbcustomer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tbcustomer.param.TbCustomerParam;
import vip.xiaonuo.main.modular.tbcustomer.service.TbCustomerService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 客户控制器
 *
 * @author 邾茂星
 * @date 2022-01-14 08:42:18
 */
@Api(tags = "客户")
@RestController
public class TbCustomerController {

    @Resource
    private TbCustomerService tbCustomerService;

    /**
     * 查询客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    @Permission
    @GetMapping("/tbCustomer/page")
    @BusinessLog(title = "客户_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("客户_分页查询")
    public ResponseData page(TbCustomerParam tbCustomerParam) {
        return new SuccessResponseData(tbCustomerService.page(tbCustomerParam));
    }

    /**
     * 添加客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    @Permission
    @PostMapping("/tbCustomer/add")
    @BusinessLog(title = "客户_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("客户_增加")
    public ResponseData add(@RequestBody @Validated(TbCustomerParam.add.class) TbCustomerParam tbCustomerParam) {
            tbCustomerService.add(tbCustomerParam);
        return new SuccessResponseData();
    }

    /**
     * 删除客户，可批量删除
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    @Permission
    @PostMapping("/tbCustomer/delete")
    @BusinessLog(title = "客户_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("客户_删除")
    public ResponseData delete(@RequestBody @Validated(TbCustomerParam.delete.class) List<TbCustomerParam> tbCustomerParamList) {
            tbCustomerService.delete(tbCustomerParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    @Permission
    @PostMapping("/tbCustomer/edit")
    @BusinessLog(title = "客户_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("客户_编辑")
    public ResponseData edit(@RequestBody @Validated(TbCustomerParam.edit.class) TbCustomerParam tbCustomerParam) {
            tbCustomerService.edit(tbCustomerParam);
        return new SuccessResponseData();
    }

    /**
     * 查看客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    @Permission
    @GetMapping("/tbCustomer/detail")
    @BusinessLog(title = "客户_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("客户_查看")
    public ResponseData detail(@Validated(TbCustomerParam.detail.class) TbCustomerParam tbCustomerParam) {
        return new SuccessResponseData(tbCustomerService.detail(tbCustomerParam));
    }

    /**
     * 客户列表
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    @Permission
    @GetMapping("/tbCustomer/list")
    @BusinessLog(title = "客户_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("客户_列表")
    public ResponseData list(TbCustomerParam tbCustomerParam) {
        return new SuccessResponseData(tbCustomerService.list(tbCustomerParam));
    }

    /**
     * 导出客户
     *
     * @author 邾茂星
     * @date 2022-01-14 08:42:18
     */
    @Permission
    @GetMapping("/tbCustomer/export")
    @BusinessLog(title = "客户_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("客户_导出")
    public void export(TbCustomerParam tbCustomerParam) {
        tbCustomerService.export(tbCustomerParam);
    }

}
