package com.hr.controller;

import com.hr.converter.EmployeeConverter;
import com.hr.dto.LoginDto;
import com.hr.dto.ResponseDto;
import com.hr.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeConverter employeeConverter;

    /**
     * 登录接口
     * @param loginDto 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody LoginDto loginDto) {
        try {
            // 调用登录服务
            var employee = authService.login(loginDto);
            if (employee != null) {
                // 转换为DTO返回，不包含密码
                var employeeDto = employeeConverter.toDto(employee);
                return ResponseDto.success(employeeDto);
            } else {
                return ResponseDto.fail(401, "Invalid username or password");
            }
        } catch (Exception e) {
            return ResponseDto.fail(500, "Login failed: " + e.getMessage());
        }
    }
}
