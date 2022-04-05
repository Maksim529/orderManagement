
package vip.xiaonuo.main.modular.company.controller;

import io.swagger.annotations.Api;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.company.param.CompanyParam;
import vip.xiaonuo.main.modular.company.service.CompanyService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 客户管理控制器
 *
 * @author 邾茂星
 * @date 2021-12-30 20:34:18
 */
@RestController
@Api(tags = "客户管理")
public class CompanyController {

    @Resource
    private CompanyService companyService;

    /**
     * 查询客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    @Permission
    @GetMapping("/company/page")
    @BusinessLog(title = "客户管理_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(CompanyParam companyParam) {
        return new SuccessResponseData(companyService.page(companyParam));
    }

    /**
     * 添加客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    @Permission
    @PostMapping("/company/add")
    @BusinessLog(title = "客户管理_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(CompanyParam.add.class) CompanyParam companyParam) {
            companyService.add(companyParam);
        return new SuccessResponseData();
    }

    /**
     * 删除客户管理，可批量删除
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    @Permission
    @PostMapping("/company/delete")
    @BusinessLog(title = "客户管理_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(CompanyParam.delete.class) List<CompanyParam> companyParamList) {
            companyService.delete(companyParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    @Permission
    @PostMapping("/company/edit")
    @BusinessLog(title = "客户管理_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(CompanyParam.edit.class) CompanyParam companyParam) {
            companyService.edit(companyParam);
        return new SuccessResponseData();
    }

    /**
     * 查看客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    @Permission
    @GetMapping("/company/detail")
    @BusinessLog(title = "客户管理_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(CompanyParam.detail.class) CompanyParam companyParam) {
        return new SuccessResponseData(companyService.detail(companyParam));
    }

    /**
     * 客户管理列表
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    @Permission
    @GetMapping("/company/list")
    @BusinessLog(title = "客户管理_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(CompanyParam companyParam) {
        return new SuccessResponseData(companyService.list(companyParam));
    }

    /**
     * 导出客户管理
     *
     * @author 邾茂星
     * @date 2021-12-30 20:34:18
     */
    @Permission
    @GetMapping("/company/export")
    @BusinessLog(title = "客户管理_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(CompanyParam companyParam) {
        companyService.export(companyParam);
    }

}
