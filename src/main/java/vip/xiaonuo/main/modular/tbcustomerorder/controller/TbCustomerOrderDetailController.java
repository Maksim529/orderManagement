
package vip.xiaonuo.main.modular.tbcustomerorder.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tbcustomerorder.param.TbCustomerOrderDetailParam;
import vip.xiaonuo.main.modular.tbcustomerorder.service.TbCustomerOrderDetailService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 订单明细控制器
 *
 * @author 邾茂星
 * @date 2022-01-12 10:09:05
 */
@Api(tags = "订单明细")
@RestController
public class TbCustomerOrderDetailController {

    @Resource
    private TbCustomerOrderDetailService tbCustomerOrderDetailService;

    /**
     * 查询订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    @Permission
    @GetMapping("/tbCustomerOrderDetail/page")
    @BusinessLog(title = "订单明细_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("订单明细_分页查询")
    public ResponseData page(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        return new SuccessResponseData(tbCustomerOrderDetailService.page(tbCustomerOrderDetailParam));
    }

    /**
     * 添加订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    @Permission
    @PostMapping("/tbCustomerOrderDetail/add")
    @BusinessLog(title = "订单明细_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("订单明细_增加")
    public ResponseData add(@RequestBody @Validated(TbCustomerOrderDetailParam.add.class) TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
            tbCustomerOrderDetailService.add(tbCustomerOrderDetailParam);
        return new SuccessResponseData();
    }

    /**
     * 删除订单明细，可批量删除
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    @Permission
    @PostMapping("/tbCustomerOrderDetail/delete")
    @BusinessLog(title = "订单明细_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("订单明细_删除")
    public ResponseData delete(@RequestBody @Validated(TbCustomerOrderDetailParam.delete.class) List<TbCustomerOrderDetailParam> tbCustomerOrderDetailParamList) {
            tbCustomerOrderDetailService.delete(tbCustomerOrderDetailParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    @Permission
    @PostMapping("/tbCustomerOrderDetail/edit")
    @BusinessLog(title = "订单明细_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("订单明细_编辑")
    public ResponseData edit(@RequestBody @Validated(TbCustomerOrderDetailParam.edit.class) TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
            tbCustomerOrderDetailService.edit(tbCustomerOrderDetailParam);
        return new SuccessResponseData();
    }

    /**
     * 查看订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    @Permission
    @GetMapping("/tbCustomerOrderDetail/detail")
    @BusinessLog(title = "订单明细_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("订单明细_查看")
    public ResponseData detail(@Validated(TbCustomerOrderDetailParam.detail.class) TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        return new SuccessResponseData(tbCustomerOrderDetailService.detail(tbCustomerOrderDetailParam));
    }

    /**
     * 订单明细列表
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    @Permission
    @GetMapping("/tbCustomerOrderDetail/list")
    @BusinessLog(title = "订单明细_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("订单明细_列表")
    public ResponseData list(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        return new SuccessResponseData(tbCustomerOrderDetailService.list(tbCustomerOrderDetailParam));
    }

    /**
     * 导出订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    @Permission
    @GetMapping("/tbCustomerOrderDetail/export")
    @BusinessLog(title = "订单明细_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("订单明细_导出")
    public void export(TbCustomerOrderDetailParam tbCustomerOrderDetailParam) {
        tbCustomerOrderDetailService.export(tbCustomerOrderDetailParam);
    }

}
