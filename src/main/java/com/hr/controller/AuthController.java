package com.hr.controller;

import com.hr.converter.EmployeeConverter;
import com.hr.dto.LoginDto;
import com.hr.dto.ResponseDto;
import com.hr.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeConverter employeeConverter;

    /**
     * 登录接口
     *
     * @param loginDto 登录信息
     * @param result   绑定结果
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResponseDto<?> login(@Valid @RequestBody LoginDto loginDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseDto.fail(400, result.getFieldError().getDefaultMessage());
        }
        try {
            // 调用登录服务
            var employee = authService.login(loginDto);
            if (employee != null) {
                // 转换为DTO返回，不包含密码
                var employeeDto = employeeConverter.toDto(employee);
                return ResponseDto.success(employeeDto);
            } else {
                return ResponseDto.fail(401, "用户名或密码错误");
            }
        } catch (Exception e) {
            return ResponseDto.fail(500, "登录失败: " + e.getMessage());
        }
    }
}
