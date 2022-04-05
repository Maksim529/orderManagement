
package vip.xiaonuo.main.modular.tbworkorder.controller;

import cn.hutool.core.lang.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.annotion.Permission;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ErrorResponseData;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tbworkorder.dto.WorkOrderReportedDTO;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkerorderReportedInfo;
import vip.xiaonuo.main.modular.tbworkorder.param.TbWorkOrderParam;
import vip.xiaonuo.main.modular.tbworkorder.param.TbWorkerorderReportedInfoParam;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkOrderService;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkerorderReportedInfoService;
import vip.xiaonuo.main.modular.tbworkorder.vo.TbWorkOrderInfoVO;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工厂工单控制器
 *
 * @author wjc
 * @date 2022-01-13 14:29:32
 */
@Api(tags = "工厂工单")
@RestController
public class TbWorkOrderController {

    @Resource
    private TbWorkOrderService tbWorkOrderService;
    @Autowired
    private TbWorkerorderReportedInfoService tbWorkerorderReportedInfoService;


    /**
     * 工单完成
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    @GetMapping("/tbWorkOrder/finish")
    @BusinessLog(title = "工单完成", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("工单完成")
    public ResponseData finish(Long workOrderId) {
        tbWorkOrderService.finish(workOrderId);
        return new SuccessResponseData();
    }

    @ApiOperation("生产工单_上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "工单号", required = true, dataType = "string"),
            @ApiImplicitParam(name = "type", value = "类型 1=款式图 2=详情图 3=合同 4=工单合同", required = true, dataType = "int")
    })
    @PostMapping(value= "/tbWorkOrder/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseData uploadFile(@RequestPart( "files") MultipartFile[] files, Long id, Integer type){
        tbWorkOrderService.uploadFile(files, id, type);
        return SuccessResponseData.success();
    }

    @ApiOperation("生产工单获取所有合同信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "工单号", required = true, dataType = "string"),
    })
    @GetMapping(value= "/tbWorkOrder/contract")
    public ResponseData uploadFile(Long id){
        return SuccessResponseData.success(tbWorkOrderService.getContractPath(id));
    }

    /**
     * 工单提交转入生产
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    @GetMapping("/tbWorkOrder/commit/contract")
    @BusinessLog(title = "工单提交转入生产", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("工单提交转入生产")
    public ResponseData commit(Long workOrderId) {
        tbWorkOrderService.commit(workOrderId);
        return new SuccessResponseData();
    }

    /**
     * 查询工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    @Permission
    @GetMapping("/tbWorkOrder/page")
    @BusinessLog(title = "工厂工单_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("工厂工单_分页查询")
    public ResponseData page(TbWorkOrderParam tbWorkOrderParam) {
        return new SuccessResponseData(tbWorkOrderService.page(tbWorkOrderParam));
    }

    /**
     * 添加工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    @Permission
    @PostMapping("/tbWorkOrder/add")
    @BusinessLog(title = "工厂工单_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("工厂工单_增加")
    public ResponseData add(@RequestBody @Validated(TbWorkOrderParam.add.class) TbWorkOrderParam tbWorkOrderParam) {
            tbWorkOrderService.add(tbWorkOrderParam);
        return new SuccessResponseData();
    }

    /**
     * 删除工厂工单，可批量删除
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    @Permission
    @PostMapping("/tbWorkOrder/delete")
    @BusinessLog(title = "工厂工单_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("工厂工单_删除")
    public ResponseData delete(@RequestBody @Validated(TbWorkOrderParam.delete.class) List<TbWorkOrderParam> tbWorkOrderParamList) {
            tbWorkOrderService.delete(tbWorkOrderParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    @Permission
    @PostMapping("/tbWorkOrder/edit")
    @BusinessLog(title = "工厂工单_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("工厂工单_编辑")
    public ResponseData edit(@RequestBody @Validated(TbWorkOrderParam.edit.class) TbWorkOrderParam tbWorkOrderParam) {
            tbWorkOrderService.edit(tbWorkOrderParam);
        return new SuccessResponseData();
    }

    /**
     * 查看工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    @Permission
    @GetMapping("/tbWorkOrder/detail")
    @BusinessLog(title = "工厂工单_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("工厂工单_查看")
    public ResponseData detail(@Validated(TbWorkOrderParam.detail.class) TbWorkOrderParam tbWorkOrderParam) {
        return new SuccessResponseData(tbWorkOrderService.detail(tbWorkOrderParam));
    }

    /**
     * 工厂工单列表
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    @Permission
    @GetMapping("/tbWorkOrder/list")
    @BusinessLog(title = "工厂工单_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("工厂工单_列表")
    public ResponseData list(TbWorkOrderParam tbWorkOrderParam) {
        return new SuccessResponseData(tbWorkOrderService.list(tbWorkOrderParam));
    }

    /**
     * 导出工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    @Permission
    @GetMapping("/tbWorkOrder/export")
    @BusinessLog(title = "工厂工单_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("工厂工单_导出")
    public void export(TbWorkOrderParam tbWorkOrderParam) {
        tbWorkOrderService.export(tbWorkOrderParam);
    }

    @GetMapping("/tbWorkOrder/searchOrder")
    @BusinessLog(title = "查询工单信息", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("查询工单信息")
    public ResponseData searchOrder(TbWorkOrderInfoVO tbWorkOrderInfoVO){
        return new SuccessResponseData(tbWorkOrderService.searchOrder(tbWorkOrderInfoVO));
    }

    @PostMapping("/tbWorkOrder/sendSMS")
    @ApiOperation("发送SMS短信通知")
    public ResponseData sendSMS(@RequestBody Dict dict){
        tbWorkOrderService.sendSMS(dict);
        return new SuccessResponseData();
    }

    @PostMapping("/tbWorkOrder/saveReported")
    @ApiOperation("保存报工")
    public ResponseData saveReported(@RequestBody WorkOrderReportedDTO dto){
        Long resultId = tbWorkOrderService.saveReported(dto);
        return resultId != null ? new SuccessResponseData(resultId) : new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE, "操作失败！");
    }

    @PostMapping("/mobile/tbWorkOrder/saveReportedInfo")
    @ApiOperation("保存报工记录")
    public ResponseData saveReportedInfo(@RequestBody TbWorkerorderReportedInfo info){
        tbWorkerorderReportedInfoService.save(info);
        Long id = info.getId();
        return new SuccessResponseData(id);
    }

    @GetMapping("/mobile/tbWorkOrder/getReportedInfoList")
    @ApiOperation("查看报工记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workOrderId", value = "工单id", required = true),
            @ApiImplicitParam(name = "type", value = "类型 1=裁剪")
    })
    public ResponseData getReportedInfoList(@RequestParam("workOrderId") Long workOrderId, @RequestParam("type") Integer type){
        return new SuccessResponseData(tbWorkerorderReportedInfoService.getReportedInfoList(workOrderId, type));
    }

}
