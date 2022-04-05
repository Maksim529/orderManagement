package vip.xiaonuo.main.modular.dingding.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkOrderService;

import javax.annotation.Resource;

/**
 * 用于钉钉接口
 * @author 邾茂星
 * @date 2022/1/18 9:19
 */
@Api(tags = "钉钉接口")
@RestController
@RequestMapping("/ding")
public class DingController {

    @Resource
    private TbWorkOrderService tbWorkOrderService;

    /**
     * 查询跟单员的生产工单各状态数量
     *
     * @author wjc
     * @date 2022-1-18 09:44:42
     */
    @GetMapping("/order/count")
    @BusinessLog(title = "查询跟单员的生产工单各状态数量", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("查询跟单员的生产工单各状态数量")
    public ResponseData count() {
        return new SuccessResponseData(tbWorkOrderService.countByUser());
    }

    /**
     * 根据跟单员以及跟单数量查询工单信息分页
     * @author wjc
     * @date 2022-1-18 09:44:42
     */
    @GetMapping("/order/page")
    @BusinessLog(title = "根据跟单员以及跟单数量查询工单信息", opType = LogAnnotionOpTypeEnum.QUERY)
    @ApiOperation("根据跟单员以及跟单数量查询工单信息")
    public ResponseData count(Integer status) {
        return new SuccessResponseData(tbWorkOrderService.pageByStatus(status));
    }
}
