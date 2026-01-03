package com.hr.dto;

import lombok.Data;

import java.util.Date;

/**
 * 部门数据传输对象
 */
@Data
public class DepartmentDto {
    /**
     * 部门ID
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;
}