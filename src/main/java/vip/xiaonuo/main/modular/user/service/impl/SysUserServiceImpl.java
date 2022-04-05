
package vip.xiaonuo.main.modular.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vip.xiaonuo.api.auth.entity.SysUser;
import vip.xiaonuo.api.auth.result.SysUserResult;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.main.modular.user.service.SysUserService;
import vip.xiaonuo.sys.modular.role.entity.SysRole;
import vip.xiaonuo.sys.modular.role.mapper.SysRoleMapper;
import vip.xiaonuo.sys.modular.user.entity.SysUserRole;
import vip.xiaonuo.sys.modular.user.mapper.SysUserMapper;
import vip.xiaonuo.sys.modular.user.mapper.SysUserRoleMapper;

import java.util.List;


/**
 * 系统用户service接口实现类
 *
 * @author xuyuxiang
 * @date 2020/3/11 17:49
 */
@Service("mainSysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    /**
     * 根据参数和角色类型查询用户
     *
     * @param roleCode 角色编码
     * @param param    用于查询的参数,可查询用户名,密码,手机号码
     * @return
     */
    @Override
    public PageResult<SysUserResult> pageByRoleCode(String roleCode, String param) {
        PageResult pageResult = new PageResult<SysUserResult>();
        String[] codes = roleCode.split(",");
        LambdaQueryWrapper<SysRole> queryWrapperRole = new LambdaQueryWrapper<>();
        queryWrapperRole.select(SysRole::getId).in(SysRole::getCode,codes);
        List<Object> roleIds = sysRoleMapper.selectObjs(queryWrapperRole);
        if (CollectionUtils.isEmpty(roleIds)){
            return pageResult;
        }
        LambdaQueryWrapper<SysUserRole> queryWrapperUserRole = new LambdaQueryWrapper<>();
        queryWrapperUserRole.select(SysUserRole::getUserId).in(SysUserRole::getRoleId,roleIds);
        List<Object> userIds = sysUserRoleMapper.selectObjs(queryWrapperUserRole);
        if (CollectionUtils.isEmpty(userIds)){
            return pageResult;
        }
        QueryWrapper<SysUserResult> queryWrapperUser = new QueryWrapper<>();
        queryWrapperUser.in("sys_user.id",userIds);
        queryWrapperUser.eq("sys_user.status",0);
        if (StringUtils.isNotEmpty(param)){
            queryWrapperUser.and(q -> q.like("sys_user.account", param)
                    .or().like("sys_user.name", param)
                    .or().like("sys_user.phone", param));
        }
        return new PageResult<>(this.baseMapper.page(PageFactory.defaultPage(), queryWrapperUser));
    }
}
