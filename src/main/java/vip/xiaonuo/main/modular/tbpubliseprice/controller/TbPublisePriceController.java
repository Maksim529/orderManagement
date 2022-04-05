
package vip.xiaonuo.main.modular.tbpubliseprice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.login.SysLoginUser;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.core.jwt.JwtTokenUtil;
import vip.xiaonuo.main.modular.tbpubliseprice.param.TbPublisePriceParam;
import vip.xiaonuo.main.modular.tbpubliseprice.service.TbPublisePriceService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 报价记录控制器
 *
 * @author 邾茂星
 * @date 2022-01-11 10:11:12
 */
@Api(tags = "报价记录")
@RestController
public class TbPublisePriceController {

    @Resource
    private TbPublisePriceService tbPublisePriceService;

    /**
     * 查询报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    @Permission
    @GetMapping("/tbPublisePrice/page")
    @BusinessLog(title = "报价记录_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("报价记录_分页查询")
    public ResponseData page(TbPublisePriceParam tbPublisePriceParam) {
        return new SuccessResponseData(tbPublisePriceService.page(tbPublisePriceParam));
    }

    /**
     * 添加报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    @Permission
    @PostMapping("/tbPublisePrice/add")
    @BusinessLog(title = "报价记录_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("报价记录_增加")
    public ResponseData add(@RequestBody @Validated(TbPublisePriceParam.add.class) TbPublisePriceParam tbPublisePriceParam) {
        SysLoginUser loginUser = JwtTokenUtil.getLoginUser();
        tbPublisePriceParam.setPublicPriceUser(loginUser.getId());
        tbPublisePriceService.add(tbPublisePriceParam);
        return new SuccessResponseData();
    }

    /**
     * 删除报价记录，可批量删除
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    @Permission
    @PostMapping("/tbPublisePrice/delete")
    @BusinessLog(title = "报价记录_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("报价记录_删除")
    public ResponseData delete(@RequestBody @Validated(TbPublisePriceParam.delete.class) List<TbPublisePriceParam> tbPublisePriceParamList) {
            tbPublisePriceService.delete(tbPublisePriceParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    @Permission
    @PostMapping("/tbPublisePrice/edit")
    @BusinessLog(title = "报价记录_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("报价记录_编辑")
    public ResponseData edit(@RequestBody @Validated(TbPublisePriceParam.edit.class) TbPublisePriceParam tbPublisePriceParam) {
            tbPublisePriceService.edit(tbPublisePriceParam);
        return new SuccessResponseData();
    }

    /**
     * 查看报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    @Permission
    @GetMapping("/tbPublisePrice/detail")
    @BusinessLog(title = "报价记录_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("报价记录_查看")
    public ResponseData detail(@Validated(TbPublisePriceParam.detail.class) TbPublisePriceParam tbPublisePriceParam) {
        return new SuccessResponseData(tbPublisePriceService.detail(tbPublisePriceParam));
    }

    /**
     * 报价记录列表
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    @Permission
    @GetMapping("/tbPublisePrice/list")
    @BusinessLog(title = "报价记录_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("报价记录_列表")
    public ResponseData list(TbPublisePriceParam tbPublisePriceParam) {
        return new SuccessResponseData(tbPublisePriceService.list(tbPublisePriceParam));
    }

    /**
     * 导出报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    @Permission
    @GetMapping("/tbPublisePrice/export")
    @BusinessLog(title = "报价记录_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("报价记录_导出")
    public void export(TbPublisePriceParam tbPublisePriceParam) {
        tbPublisePriceService.export(tbPublisePriceParam);
    }

}
