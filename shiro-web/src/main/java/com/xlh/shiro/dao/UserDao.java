package com.xlh.shiro.dao;

import com.xlh.shiro.vo.User;

import java.util.List;

/**
 * @author xiaolei hu
 * @date 2019/1/26 12:49
 **/
public interface UserDao {
    User getUserByUserName(String userName);

    List<String> queryRolesByUserName(String userName);

    List<String> getPermissionsByUserName(String userName);
}
