package com.xlh.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author xiaolei hu
 * @date 2019/1/20 18:17
 **/
public class AuthenticationTest {
    private static final SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        // 添加 Mark 用户，密码是 123456，角色是 admin 和 user
        simpleAccountRealm.addAccount("Mark", "123456", "admin", "user");
    }

    @Test
    public void testAuthentication() {
        // 1. 构建 SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2. 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        // 检查用户是否同时具备 admin 和 user 角色，如果不同时具备那么会认证失败
        subject.checkRoles("admin", "user");
//        subject.checkRole("admin");

        // 退出登录
        subject.logout();

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

    }
}
