
package vip.xiaonuo.main.modular.tbworkerorderexception.controller;

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
import vip.xiaonuo.main.modular.tbworkerorderexception.param.TbWorkerOrderExceptionParam;
import vip.xiaonuo.main.modular.tbworkerorderexception.service.TbWorkerOrderExceptionService;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 生产异常反馈控制器
 *
 * @author 邾茂星
 * @date 2022-01-20 09:23:33
 */
@Api(tags = "生产异常反馈")
@RestController
public class TbWorkerOrderExceptionController {

    @Resource
    private TbWorkerOrderExceptionService tbWorkerOrderExceptionService;

    /**
     * 查询生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    @GetMapping("/tbWorkerOrderException/count")
    @BusinessLog(title = "生产异常反馈_生产异常类型数量查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("生产异常反馈_生产异常类型数量查询")
    public ResponseData page(@RequestParam Long userId) {
        return new SuccessResponseData(tbWorkerOrderExceptionService.getCount(userId));
    }

    /**
     * 查询生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    @GetMapping("/tbWorkerOrderException/page")
    @BusinessLog(title = "生产异常反馈_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("生产异常反馈_分页查询")
    public ResponseData page(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        return new SuccessResponseData(tbWorkerOrderExceptionService.page(tbWorkerOrderExceptionParam));
    }

    /**
     * 添加生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    @PostMapping("/tbWorkerOrderException/add")
    @BusinessLog(title = "生产异常反馈_增加", opType = LogAnnotionOpTypeEnum.ADD)
    @ApiOperation("生产异常反馈_增加")
    public ResponseData add(@RequestBody @Validated(TbWorkerOrderExceptionParam.add.class) TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        Long id = tbWorkerOrderExceptionService.add(tbWorkerOrderExceptionParam);
        return new SuccessResponseData(id);
    }

    /**
     * 删除生产异常反馈，可批量删除
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    @Permission
    @PostMapping("/tbWorkerOrderException/delete")
    @BusinessLog(title = "生产异常反馈_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    @ApiOperation("生产异常反馈_删除")
    public ResponseData delete(@RequestBody @Validated(TbWorkerOrderExceptionParam.delete.class) List<TbWorkerOrderExceptionParam> tbWorkerOrderExceptionParamList) {
        tbWorkerOrderExceptionService.delete(tbWorkerOrderExceptionParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    @Permission
    @PostMapping("/tbWorkerOrderException/edit")
    @BusinessLog(title = "生产异常反馈_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("生产异常反馈_编辑")
    public ResponseData edit(@RequestBody @Validated(TbWorkerOrderExceptionParam.edit.class) TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        tbWorkerOrderExceptionService.edit(tbWorkerOrderExceptionParam);
        return new SuccessResponseData();
    }

    /**
     * 查看生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    @GetMapping("/tbWorkerOrderException/detail")
    @BusinessLog(title = "生产异常反馈_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @ApiOperation("生产异常反馈_查看")
    public ResponseData detail(@Validated(TbWorkerOrderExceptionParam.detail.class) TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        return new SuccessResponseData(tbWorkerOrderExceptionService.detail(tbWorkerOrderExceptionParam));
    }

    /**
     * 生产异常反馈列表
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    @Permission
    @GetMapping("/tbWorkerOrderException/list")
    @BusinessLog(title = "生产异常反馈_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("生产异常反馈_列表")
    public ResponseData list(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        return new SuccessResponseData(tbWorkerOrderExceptionService.list(tbWorkerOrderExceptionParam));
    }

    /**
     * 导出生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    @Permission
    @GetMapping("/tbWorkerOrderException/export")
    @BusinessLog(title = "生产异常反馈_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    @ApiOperation("生产异常反馈_导出")
    public void export(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        tbWorkerOrderExceptionService.export(tbWorkerOrderExceptionParam);
    }

    @PostMapping("/tbWorkerOrderException/handlingException")
    @BusinessLog(title = "处理生产异常", opType = LogAnnotionOpTypeEnum.EDIT)
    @ApiOperation("处理生产异常")
    public ResponseData handlingException(@RequestBody TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam){
        int resNum = tbWorkerOrderExceptionService.handlingException(tbWorkerOrderExceptionParam);
        return resNum == 0 ? new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE, "操作失败！") : new SuccessResponseData();
    }

    @GetMapping("/tbWorkerOrderException/getExceptionNumber")
    @ApiOperation("查询异常数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workOrderId", value = "工单id", dataType = "long"),
            @ApiImplicitParam(name = "type", value = "类型：0=未处理 1=已处理 2=全部", dataType = "int")
    })
    public int getExceptionNumber(@RequestParam("workOrderId") Long workOrderId, @RequestParam("type") Integer type){
        return tbWorkerOrderExceptionService.getExceptionNumber(workOrderId, type);
    }

}
