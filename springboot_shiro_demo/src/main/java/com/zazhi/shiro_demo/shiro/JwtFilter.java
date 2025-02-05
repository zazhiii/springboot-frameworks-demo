package com.zazhi.shiro_demo.shiro;

//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;

import com.zazhi.shiro_demo.JwtUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends AuthenticatingFilter {

    /**
     * 拦截请求之后，用于把令牌字符串封装成令牌对象
     * 该方法用于从请求中获取 JWT 并将其封装为 JwtToken（自定义的 AuthenticationToken 类）。
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String jwtToken = httpRequest.getHeader("Authorization");
        if (!StringUtils.hasLength(jwtToken)) {
            return null;
        }
        return new JwtToken(jwtToken);
    }

    /**
     * 该方法的作用是判断当前请求是否被允许访问。它主要用于检查某些条件，决定是否允许访问或是否跳过认证过程
     * 他作用于 onAccessDenied 之前
     * 如果请求满足某些条件（例如，isAccessAllowed 返回 true），Shiro 会跳过后续的认证步骤，允许请求继续。
     * 如果返回 false，Shiro 会继续执行 onAccessDenied，进行认证或其他授权操作。
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("调用 JwtFilter 中 isAccessAllowed ");

        // 如果用户已经认证，直接放行
        // 这个认证状态会存储在回话中
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return true;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 对预检请求直接返回true，避免多次触发认证逻辑
        return httpRequest.getMethod().equals(RequestMethod.OPTIONS.name());
    }

    /**
     * 当 isAccessAllowed 返回 false 或没有足够的权限访问时
     * onAccessDenied 会被调用，用来执行认证操作（例如，验证 Token 或 Session）
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("调用 JwtFilter 中 onAccessDenied ");

        // 从请求头中获取 Token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String jwtToken = httpRequest.getHeader("Authorization");

        // 有token且token合法才执行认证流程
        if (StringUtils.hasLength(jwtToken) && JwtUtil.verifyToken(jwtToken)) {
            executeLogin(request, response);
            log.info("Token 认证成功");
        }
        return true;
    }

}
