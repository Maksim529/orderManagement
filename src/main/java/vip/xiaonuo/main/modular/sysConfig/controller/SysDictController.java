package vip.xiaonuo.main.modular.sysConfig.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.sys.modular.dict.service.SysDictDataService;

@Api(tags = "系统字典")
@RestController
@RequestMapping("mobile/sysDict")
public class SysDictController {
    @Autowired
    private SysDictDataService sysDictDataService;

    @GetMapping("/findListByDictTypeCode")
    @BusinessLog(title = "查询字典值", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    @ApiOperation("查询字典值")
    @ApiImplicitParam(name = "dictTypeCode", value = "字典类型编码", required = true, dataType = "string")
    public ResponseData findListByDictTypeCode(String dictTypeCode){
        return new SuccessResponseData(sysDictDataService.findListByDictTypeCode(dictTypeCode));
    }
}
