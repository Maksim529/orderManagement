
package vip.xiaonuo.main.modular.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.api.auth.entity.SysUser;
import vip.xiaonuo.api.auth.result.SysUserResult;
import vip.xiaonuo.common.pojo.page.PageResult;

/**
 * 系统用户service接口
 *
 * @author xuyuxiang
 * @date 2020/3/11 17:49
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据参数和角色类型查询用户
     * @param roleCode 角色编码
     * @param param 用于查询的参数,可查询用户名,密码,手机号码
     * @return
     */
    public PageResult<SysUserResult> pageByRoleCode(String roleCode, String param);
}
