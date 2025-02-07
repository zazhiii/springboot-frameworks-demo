package com.zazhi.shiro_demo.controller.admin;

import com.zazhi.shiro_demo.pojo.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zazhi
 * @date 2025/2/7
 * @description: TODO
 */
@RestController("AdminMyController")
@RequestMapping("/api/admin")
@Tag(name = "my-controller", description = "管理员接口")

// @Validated
// 这里有个bug, 若@Validated注解加到类上并且方法中有@RequiresAuthentication或者@RequiresRoles注解
// 会导致knife4j无法扫描到这个controller, 加到方法上就不会有这个问题

public class MyController {

    @Operation(summary = "hello admin")
    @GetMapping("/admin/hello")
    @RequiresAuthentication
//    @RequiresRoles("admin")
    public String hello(@Validated User user) {
        return "hello admin";
    }
}
