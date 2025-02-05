package com.zazhi.shiro_demo.controller;

import com.zazhi.shiro_demo.JwtUtil;
import com.zazhi.shiro_demo.pojo.User;
import com.zazhi.shiro_demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zazhi
 * @date 2024/12/9
 * @description: TODO
 */
@Slf4j
@RestController
@RequestMapping("myController")
public class myController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(String username, String password) {
        User user = userService.login(username, password);
        if (user == null) {
            return "登录失败";
        }
        return JwtUtil.genToken(Map.of("id", "1"));
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    // 需要认证和角色为"admin"才能访问
//    @RequiresAuthentication
    @RequiresRoles("admin")
    @GetMapping("/dashboard")
    public String dashboard() {
        log.info("调用 dashboard");
        return "管理员控制面板";
    }

    // 需要认证才能访问
    @RequiresAuthentication
    @GetMapping("/profile")
    public String profile() {
        return "个人资料页面";
    }

    // 需要特定权限才能访问
    @RequiresPermissions("view:dashboard")
    @GetMapping("/viewDashboard")
    public String viewDashboard() {
        return "查看控制面板";
    }
}
