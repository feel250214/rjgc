package com.hr.service;

import com.hr.dao.EmployeeMapper;
import com.hr.dto.LoginDto;
import com.hr.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 认证服务类
 */
@Service
public class AuthService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 登录认证
     *
     * @param loginDto 登录信息
     * @return 认证成功的员工对象，失败返回null
     */
    public Employee login(LoginDto loginDto) {
        // 根据用户名查询员工
        Employee employee = employeeMapper.findByUsername(loginDto.getUsername());
        if (employee == null) {
            return null;
        }
        // 直接比较密码（测试用，实际项目需加密）
        if (employee.getPassword().equals(loginDto.getPassword())) {
            return employee;
        }
        return null;
    }
}
