package com.xlh.shiro.dao.impl;

import com.xlh.shiro.dao.UserDao;
import com.xlh.shiro.vo.User;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei hu
 * @date 2019/1/26 12:50
 **/
@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByUserName(String userName) {
        String sql = "select username, password from users where username = ?";
        List<User> userList = jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });

        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }

        return userList.get(0);
    }

    @Override
    public List<String> queryRolesByUserName(String userName) {
        String sql = "select role_name from user_roles where username = ?";

        return jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {

                return resultSet.getString("role_name");
            }
        });
    }

    @Override
    public List<String> getPermissionsByUserName(String userName) {

        List<String> roleNameList = queryRolesByUserName(userName);

        List<String> permissionList = new ArrayList<>(16);

        if (CollectionUtils.isEmpty(roleNameList)) {
            return null;
        }

        roleNameList.forEach(roleName -> {
            String sql = "select permission from roles_permissions where role_name = ?";
            List<String> rolePermissionList = jdbcTemplate.query(sql, new String[]{roleName}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("permission");
                }
            });
            permissionList.addAll(rolePermissionList);
        });

        return permissionList;
    }
}
