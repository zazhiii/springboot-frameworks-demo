package com.zazhi.shiro_demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;

/**
 * @author zazhi
 * @date 2024/12/9
 * @description: TODO
 */
public class shiroTest {

    @Test
    void test(){
        // 1. 初始化获取SecurityManager
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        // 2. 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 3. 创建token对象, web应用用户名密码从页面获取
        AuthenticationToken token = new UsernamePasswordToken("zhangsan", "123456");
        // 4. 完成登录
        try {
            subject.login(token);
            System.out.println("登录成功");
            // 5. 判断是否有某个角色
            System.out.println("是否拥有角色role1：" + subject.hasRole("role1"));
            // 6. 判断是否有某个权限
            System.out.println("是否拥有权限：" + subject.isPermitted("user:delete"));
        } catch (UnknownAccountException e) {
            System.out.println("用户不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
        } catch (Exception e) {
            System.out.println("其他错误");
        }
    }

    @Test
    void test_2(){
        Md5Hash md5Hash = new Md5Hash("123456");
        System.out.println(md5Hash);
        Md5Hash md5Hash2 = new Md5Hash("123456", "salt");
        System.out.println(md5Hash2);
        Md5Hash md5Hash3 = new Md5Hash("123456", "salt");
        System.out.println(md5Hash3);
    }
}
