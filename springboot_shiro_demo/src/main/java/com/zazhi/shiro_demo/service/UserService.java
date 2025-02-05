package com.zazhi.shiro_demo.service;

import com.zazhi.shiro_demo.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author zazhi
 * @date 2024/12/9
 * @description: TODO
 */
@Service
public class UserService {
    public User getByUsername(String username) {
        if(username.equals("zhangsan")){
            return new User("zhangsan", "e10adc3949ba59abbe56e057f20f883e");
        }
        return null;
    }

    public User login(String username, String password) {
        return new User("zhangsan", "123456");
    }

    public Set<String> findPermissionsByUsername(String username) {
        return Set.of("user:delete", "user:update");
    }

    public Set<String> findRolesByUsername(String username) {
        return Set.of("admin");
    }

    public String getUsernameById(String userId) {
        return "zhangsan";
    }
}
