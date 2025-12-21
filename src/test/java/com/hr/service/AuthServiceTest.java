package com.hr.service;

import com.hr.dao.EmployeeRepository;
import com.hr.dto.LoginDto;
import com.hr.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AuthService authService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 初始化测试数据
        employee = new Employee();
        employee.setId(1L);
        employee.setUsername("admin");
        employee.setPassword("123456");
        employee.setName("Admin");
    }

    @Test
    void testLoginSuccess() {
        // 模拟数据库查询
        when(employeeRepository.findByUsername("admin")).thenReturn(employee);

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("admin");
        loginDto.setPassword("123456");

        // 执行登录
        Employee result = authService.login(loginDto);

        // 验证结果
        assertEquals(employee, result);
    }

    @Test
    void testLoginFailedWithWrongPassword() {
        // 模拟数据库查询
        when(employeeRepository.findByUsername("admin")).thenReturn(employee);

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("admin");
        loginDto.setPassword("wrongpassword");

        // 执行登录
        Employee result = authService.login(loginDto);

        // 验证结果
        assertNull(result);
    }

    @Test
    void testLoginFailedWithNonExistentUser() {
        // 模拟数据库查询，返回null
        when(employeeRepository.findByUsername("nonexistent")).thenReturn(null);

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("nonexistent");
        loginDto.setPassword("password");

        // 执行登录
        Employee result = authService.login(loginDto);

        // 验证结果
        assertNull(result);
    }
}
