package com.hr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr.entity.User;

/**
 * 用户Mapper接口
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);
}