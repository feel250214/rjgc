package com.hr.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDto {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String position;
    private Date createTime;
}
