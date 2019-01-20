package com.xlh.test;

import com.xlh.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author xiaolei hu
 * @date 2019/1/20 19:21
 **/
public class CustomRealmTest {

    @Test
    public void testAuthentication() {
        // 自定义 realm
        CustomRealm customRealm = new CustomRealm();

        // 1. 构建 SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        // 设置加密次数，可以设置 1次 或 多次
        matcher.setHashIterations(1);

        customRealm.setCredentialsMatcher(matcher);

        // 2. 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());
        subject.checkRole("admin");
////        subject.checkPermission("user:update");
//        subject.checkPermissions("user:update", "user:delete");
        subject.checkPermissions("user:add", "user:delete");
    }
}
