package com.zazhi.shiro_demo.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zazhi
 * @date 2025/2/7
 * @description: TODO
 */
@RestController
@Tag(name = "admin", description = "管理员接口")
public class AdminTestController {
    @GetMapping("/admin/hello")
    @RequiresAuthentication
    @RequiresRoles("admin")
    public String hello() {
        return "hello admin";
    }
}
