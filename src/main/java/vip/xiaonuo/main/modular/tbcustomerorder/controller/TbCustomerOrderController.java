
package vip.xiaonuo.main.modular.tbcustomerorder.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import vip.xiaonuo.main.modular.tbcustomerorder.dto.TbCustomerOrderDTO;
import vip.xiaonuo.main.modular.tbcustomerorder.dto.TbOrderDistributeDTO;
import vip.xiaonuo.main.modular.tbcustomerorder.param.TbCustomerOrderParam;
import vip.xiaonuo.main.modular.tbcustomerorder.service.TbCustomerOrderService;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.TbCustomerOrderContractVO;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.vo.WorkOrderDetailVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 需求订单控制器
 *
 * @author 邾茂星
 * @date 2022-01-12 08:59:51
 */
@Api(tags = "需求订单")
@RestController
public class TbCustomerOrderController {

    @Resource
    private TbCustomerOrderService tbCustomerOrderService;

    /**
     * 订单分配,生成工单时,先获取订单的详细
     *
     * @param orderId 参数
     * @return
     */
    @GetMapping("/tbCustomerOrder/distribute/detail")
    @BusinessLog(title = "获取订单的详细", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("获取订单的详细")
    public ResponseData getDistributeDetail(Long orderId) {

        return new SuccessResponseData(tbCustomerOrderService.getDistributeDetail(orderId));
    }

    /**
     * 订单分配,生成工单
     *
     * @param dto 参数
     * @return
     */
    @PostMapping("/tbCustomerOrder/distribute")
    @BusinessLog(title = "订单分配", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("订单分配")
    public ResponseData distribute(@RequestBody @Validated TbOrderDistributeDTO dto) {
        tbCustomerOrderService.distribute(dto);
        return new SuccessResponseData();
    }

    /**
     * 查询订单和工单一起
     *
     * @author wjc
     * @date 2022-1-15 13:39:25
     */
    @Permission
    @GetMapping("/tbCustomerOrder/work/page")
    @BusinessLog(title = "需求订单_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("需求订单工单_分页查询")
    public ResponseData orderPage(TbCustomerOrderParam tbCustomerOrderParam) {
        return new SuccessResponseData(tbCustomerOrderService.findOrderPage(tbCustomerOrderParam));
    }

    /**
     * 查询需求订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
//    @Permission
    @GetMapping("/tbCustomerOrder/page")
    @BusinessLog(title = "需求订单_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("需求订单_分页查询")
    public ResponseData page(TbCustomerOrderParam tbCustomerOrderParam) {
        return new SuccessResponseData(tbCustomerOrderService.findPage(tbCustomerOrderParam));
    }

    /**
     * 添加需求订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    @PostMapping("/tbCustomerOrder/add")
    @BusinessLog(title = "需求订单_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("需求订单_增加")
    public ResponseData add(@RequestBody @Validated(TbCustomerOrderDTO.add.class) TbCustomerOrderDTO tbCustomerOrderDTO) {
        tbCustomerOrderService.add(tbCustomerOrderDTO);
        return new SuccessResponseData();
    }

    @PostMapping("/tbCustomerOrder/publish")
    @BusinessLog(title = "需求订单_手机端发布", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("需求订单_手机端发布")
    public Long publish(@RequestParam("param") String params, @RequestPart("stylePhoto") MultipartFile file) {
        TbCustomerOrderDTO tbCustomerOrderDTO = JSONObject.parseObject(params, TbCustomerOrderDTO.class);
        return tbCustomerOrderService.publish(tbCustomerOrderDTO, file);
    }

    /**
     * @Author: 邾茂星
     * @Des:上传附件
     */
    @PostMapping(value = "/tbCustomerOrder/publish/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int uploadPublishPhoto(@RequestParam("id") Long id, @RequestPart("uploadPhoto") MultipartFile uploadPhoto) {
        int i = tbCustomerOrderService.uploadPublishPhoto(id, uploadPhoto);
        return i;
    }

    /**
     * 删除需求订单，可批量删除
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    @Permission
    @PostMapping("/tbCustomerOrder/delete")
    @BusinessLog(title = "需求订单_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("需求订单_删除")
    public ResponseData delete(@RequestBody @Validated(TbCustomerOrderParam.delete.class) List<TbCustomerOrderParam> tbCustomerOrderParamList) {
        tbCustomerOrderService.delete(tbCustomerOrderParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑需求订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    @Permission
    @PostMapping("/tbCustomerOrder/edit")
    @BusinessLog(title = "需求订单_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("需求订单_编辑")
    public ResponseData edit(@RequestBody @Validated(TbCustomerOrderDTO.edit.class) TbCustomerOrderDTO tbCustomerOrderDTO) {
        tbCustomerOrderService.edit(tbCustomerOrderDTO);
        return new SuccessResponseData();
    }

    /**
     * 查看需求订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    @Permission
    @GetMapping("/tbCustomerOrder/detail")
    @BusinessLog(title = "需求订单_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("需求订单_查看")
    public ResponseData detail(@Validated(TbCustomerOrderParam.detail.class) TbCustomerOrderParam tbCustomerOrderParam) {
        return new SuccessResponseData(tbCustomerOrderService.detail(tbCustomerOrderParam));
    }

    /**
     * 需求订单列表
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    @Permission
    @GetMapping("/tbCustomerOrder/list")
    @BusinessLog(title = "需求订单_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("需求订单_列表")
    public ResponseData list(TbCustomerOrderParam tbCustomerOrderParam) {
        return new SuccessResponseData(tbCustomerOrderService.list(tbCustomerOrderParam));
    }

    /**
     * 导出需求订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    @Permission
    @GetMapping("/tbCustomerOrder/export")
    @BusinessLog(title = "需求订单_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("需求订单_导出")
    public void export(TbCustomerOrderParam tbCustomerOrderParam) {
        tbCustomerOrderService.export(tbCustomerOrderParam);
    }

    @GetMapping("/tbCustomerOrder/createOrderNo")
    @BusinessLog(title = "需求订单_生成订单号", opType = LogAnnotionOpTypeEnum.OTHER)
    @ApiOperation("需求订单_生成订单号")
    @ApiImplicitParam(name = "customeerId", value = "客户id", required = true)
    public ResponseData createOrderNo(Long customeerId) {
        String orderNo = tbCustomerOrderService.createOrderNo(customeerId);
        if (StrUtil.isNotBlank(orderNo)) {
            return new SuccessResponseData(orderNo);
        }
        return new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE, "生成订单号失败！");
    }

    @ApiOperation("需求订单_上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "string"),
            @ApiImplicitParam(name = "type", value = "类型 1=款式图 2=详情图 3=合同", required = true, dataType = "int")
    })
    @PostMapping(value = "/tbCustomerOrder/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseData uploadFile(@RequestPart("files") MultipartFile[] files, String orderNo, Integer type) {
        int successNum = tbCustomerOrderService.uploadFile(files, orderNo, type);
        return successNum > 0 ? new SuccessResponseData() : new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE, "上传失败！");
    }

    @BusinessLog(title = "需求订单_统计数量", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("需求订单_统计数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customeerId", value = "客户id"),
            @ApiImplicitParam(name = "status", value = "订单状态", dataType = "int")
    })
    @GetMapping(value = "/tbCustomerOrder/countNumber")
    public ResponseData countNumber(Long customeerId, Integer status) {
        return new SuccessResponseData(tbCustomerOrderService.countNumber(customeerId, status));
    }

    @GetMapping("/tbCustomerOrder/totalCounts")
    @ApiOperation("工作台_统计数量")
    public Map<String, Integer> totalCounts(@RequestParam("customerId") Long customerId, @RequestParam("accountId") Long accountId) {
        return tbCustomerOrderService.totalCounts(customerId, accountId);
    }

    @BusinessLog(title = "需求订单_id查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("需求订单_id查询")
    @ApiImplicitParam(name = "id", value = "订单id", required = true)
    @GetMapping("/tbCustomerOrder/getById")
    public ResponseData getById(Long id) {
        return new SuccessResponseData(tbCustomerOrderService.getDetailById(id));
    }

    @BusinessLog(title = "需求订单_修改状态", opType = LogAnnotionOpTypeEnum.UPDATE)
    @ApiOperation("需求订单_修改状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "status", value = "订单状态", required = true, dataType = "int")
    })
    @PostMapping("/tbCustomerOrder/saveStatus")
    public ResponseData saveStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        int resultNum = tbCustomerOrderService.saveStatus(id, status);
        return resultNum > 0 ? new SuccessResponseData() : new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE, "操作失败！");
    }

    @BusinessLog(title = "订单_滚动信息", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("订单_滚动信息")
    @ApiImplicitParam(name = "type", value = "类型 1=询价单 2=需求订单", required = true, dataType = "int")
    @GetMapping("/tbCustomerOrder/findRollInfo")
    public ResponseData findRollInfo(@RequestParam("type") Integer type) {
        return new SuccessResponseData(tbCustomerOrderService.findRollInfo(type));
    }

    @GetMapping("/tbCustomerOrder/salesContractPage")
    @BusinessLog(title = "销售合同_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("销售合同_分页查询")
    public ResponseData salesContractPage(TbCustomerOrderContractVO tbCustomerOrderContractVO) {
        return new SuccessResponseData(tbCustomerOrderService.salesContractPage(tbCustomerOrderContractVO));
    }

    @GetMapping("/tbCustomerOrder/checkFilePath")
    @BusinessLog(title = "查看附件路径", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("查看附件路径")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "关联值id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "type", value = "类型 2=详情图 3=销售合同", required = true, dataType = "int")
    })
    public ResponseData checkFilePath(@RequestParam("id") Long id, @RequestParam("type") Integer type) {
        return new SuccessResponseData(tbCustomerOrderService.checkContractFilePath(id, type));
    }

    @GetMapping("/tbCustomerOrder/mobile/checkTailorInfo")
    @BusinessLog(title = "查看裁剪信息", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("查看裁剪信息")
    @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "long")
    public ResponseData checkTailorInfo(@RequestParam("orderId") Long orderId) {
        List<WorkOrderDetailVO> list = tbCustomerOrderService.checkTailorInfo(orderId);
        return new SuccessResponseData(list);
    }

    @GetMapping("/tbCustomerOrder/mobile/checkStatusStep")
    @BusinessLog(title = "查看订单状态步骤图", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("查看订单状态步骤图")
    @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "long")
    public ResponseData checkStatusStep(@RequestParam("orderId") Long orderId) {
        Map<String, Object> map = tbCustomerOrderService.checkStatusStep(orderId);
        return new SuccessResponseData(map);
    }

    @GetMapping("/tbCustomerOrder/mobile/orderFileUrlList")
    @BusinessLog(title = "获取订单附件URL", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("获取订单附件URL")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "businessType", value = "附件类型"),
            @ApiImplicitParam(name = "fileType", value = "1=图片 2=视频")
    })
    public ResponseData orderFileUrlList(@RequestParam("orderId") Long orderId,
                                         @RequestParam("businessType") Integer businessType,
                                         @RequestParam("fileType") Integer fileType){
        List<String> list = tbCustomerOrderService.orderFileUrlList(orderId, businessType, fileType);
        return new SuccessResponseData(list);
    }

}
