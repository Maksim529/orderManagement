package vip.xiaonuo.main.modular.user.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.user.service.SysUserService;

import javax.annotation.Resource;

/**
 * @author 邾茂星
 * @date 2022/1/12 13:58
 */
@RestController("mainSysUserController")
@Api(tags = "系统用户")
public class SysUserController {

    @Resource(name = "mainSysUserService")
    private SysUserService sysUserService;

    /**
     * 查询系统用户
     *
     * @author xuyuxiang
     * @date 2020/3/20 21:00
     */
    @GetMapping("/sysUser/page/role")
    @BusinessLog(title = "系统用户_查询角色下用户", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(String roleCode, String param) {
        return new SuccessResponseData(sysUserService.pageByRoleCode(roleCode,param));
    }
}
