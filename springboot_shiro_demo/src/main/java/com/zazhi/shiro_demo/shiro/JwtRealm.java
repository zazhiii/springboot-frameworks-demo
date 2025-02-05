package com.zazhi.shiro_demo.shiro;

import com.zazhi.shiro_demo.JwtUtil;
import com.zazhi.shiro_demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 这个方法用于判断 JwtRealm 是否支持该类型的 Token。
     * 如果是 JwtToken，则返回 true，表示当前 Realm 支持该 Token 类型
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        // 这里查询数据库获取用户的角色和权限
        Set<String> roles = userService.findRolesByUsername(username); // 示例：{admin}
        Set<String> permissions = userService.findPermissionsByUsername(username); // 示例：{"user:delete", "user:update"}

        // 创建 SimpleAuthorizationInfo 对象
        // 并使用 addRoles 和 addStringPermissions 方法将角色和权限添加到授权信息中。
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 用于验证用户身份
     * @param authenticationToken
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken){

        JwtToken token = (JwtToken)authenticationToken;
        String jwtToken = (String) token.getPrincipal();
        Map<String, Object> map = JwtUtil.parseToken(jwtToken);
        String username = userService.getUsernameById("1");
        return new SimpleAuthenticationInfo(username, jwtToken, getName());
    }
}
