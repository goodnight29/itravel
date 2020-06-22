package com.qhit.itravel.config;

import com.qhit.itravel.entity.Role;
import com.qhit.itravel.entity.SysPermission;
import com.qhit.itravel.entity.SysUser;
import com.qhit.itravel.service.SysPermissionService;
import com.qhit.itravel.service.SysUserService;
import com.qhit.itravel.utils.UserUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 授权，Shiro管理权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser user = UserUtil.getCurrentUser();
        //获得当前用户的角色信息
        List<Role> roles = sysUserService.findUserRolesByUserId(user.getId());
        Set<String> rolesSet = new HashSet<>();
        for (Role role : roles) {
            rolesSet.add(role.getName());
        }
        authorizationInfo.setRoles(rolesSet);
        //获取当前用户的权限信息
        List<SysPermission> permissions = sysPermissionService.findPermissionsByUserId(user.getId());
        Set<String> permissionsSet = new HashSet<>();
        for (SysPermission permission : permissions) {
            if (permission.getPermission()!=null && permission.getPermission()!=""){
                permissionsSet.add(permission.getPermission());
            }
        }
        authorizationInfo.setStringPermissions(permissionsSet);

        //把当前用户的权限放到session
        UserUtil.setPermissionSession(permissions);

        return authorizationInfo;
    }

    /**
     * 认证，登录功能
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;

        String frontUserName = usernamePasswordToken.getUsername();

        //判断用户名是否正确
        SysUser user = sysUserService.findUserByName(frontUserName);

        if (user==null){
            throw new UnknownAccountException("用户名不存在");
        }

        //判断密码是否正确
        //将前台传过来的密码进行加密，之后与数据库查出来的进行比较
        String password = user.getPassword();
        String tempPassword = new String(usernamePasswordToken.getPassword());
        String frontPassword = sysUserService.passwordEncoder(tempPassword, user.getSalt());

        if (!password.equals(frontPassword)){
            throw new IncorrectCredentialsException("密码不正确");
        }


        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,
                password,
                ByteSource.Util.bytes(user.getSalt()), getName());
        //代码走到这个地方，表示用户名密码都对，把当前登录用户存储起来

        UserUtil.setUserSession(user);
        return authenticationInfo;
    }
}
