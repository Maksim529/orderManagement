
package vip.xiaonuo.main.modular.tbfactoryorderdetail.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.param.TbFactoryOrderDetailParam;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.service.TbFactoryOrderDetailService;
import org.springframework.validation.annotation.Validated;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.vo.WorkOrderAmountInfoVO;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工单明细控制器
 *
 * @author wjc
 * @date 2022-01-13 13:35:25
 */
@Api(tags = "工单明细")
@RestController
public class TbFactoryOrderDetailController {

    @Resource
    private TbFactoryOrderDetailService tbFactoryOrderDetailService;

    /**
     * 查询工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    @Permission
    @GetMapping("/tbFactoryOrderDetail/page")
    @BusinessLog(title = "工单明细_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("工单明细_分页查询")
    public ResponseData page(TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        return new SuccessResponseData(tbFactoryOrderDetailService.page(tbFactoryOrderDetailParam));
    }

    /**
     * 添加工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    @Permission
    @PostMapping("/tbFactoryOrderDetail/add")
    @BusinessLog(title = "工单明细_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("工单明细_增加")
    public ResponseData add(@RequestBody @Validated(TbFactoryOrderDetailParam.add.class) TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
            tbFactoryOrderDetailService.add(tbFactoryOrderDetailParam);
        return new SuccessResponseData();
    }

    /**
     * 删除工单明细，可批量删除
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    @Permission
    @PostMapping("/tbFactoryOrderDetail/delete")
    @BusinessLog(title = "工单明细_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("工单明细_删除")
    public ResponseData delete(@RequestBody @Validated(TbFactoryOrderDetailParam.delete.class) List<TbFactoryOrderDetailParam> tbFactoryOrderDetailParamList) {
            tbFactoryOrderDetailService.delete(tbFactoryOrderDetailParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    @Permission
    @PostMapping("/tbFactoryOrderDetail/edit")
    @BusinessLog(title = "工单明细_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("工单明细_编辑")
    public ResponseData edit(@RequestBody @Validated(TbFactoryOrderDetailParam.edit.class) TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
            tbFactoryOrderDetailService.edit(tbFactoryOrderDetailParam);
        return new SuccessResponseData();
    }

    /**
     * 查看工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    @Permission
    @GetMapping("/tbFactoryOrderDetail/detail")
    @BusinessLog(title = "工单明细_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("工单明细_查看")
    public ResponseData detail(@Validated(TbFactoryOrderDetailParam.detail.class) TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        return new SuccessResponseData(tbFactoryOrderDetailService.detail(tbFactoryOrderDetailParam));
    }

    /**
     * 工单明细列表
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    @Permission
    @GetMapping("/tbFactoryOrderDetail/list")
    @BusinessLog(title = "工单明细_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("工单明细_列表")
    public ResponseData list(TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        return new SuccessResponseData(tbFactoryOrderDetailService.list(tbFactoryOrderDetailParam));
    }

    /**
     * 导出工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    @Permission
    @GetMapping("/tbFactoryOrderDetail/export")
    @BusinessLog(title = "工单明细_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("工单明细_导出")
    public void export(TbFactoryOrderDetailParam tbFactoryOrderDetailParam) {
        tbFactoryOrderDetailService.export(tbFactoryOrderDetailParam);
    }

    @GetMapping("/tbFactoryOrderDetail/getAmountInfo")
    @BusinessLog(title = "查询工单数量明细", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("查询工单数量明细")
    @ApiImplicitParam(name = "workOrderId", value = "工单id", required = true)
    public ResponseData getAmountInfo(@RequestParam("workOrderId") Long workOrderId){
        WorkOrderAmountInfoVO amountInfo = tbFactoryOrderDetailService.getAmountInfo(workOrderId);
        return new SuccessResponseData(amountInfo);
    }

}
