package com.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * 部门实体类
 */
@Data
@Entity
@Table(name = "department")
public class Department {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 部门编号
     */
    @NotBlank(message = "部门编号不能为空")
    @Size(max = 50, message = "部门编号长度不能超过50个字符")
    @Column(unique = true, nullable = false)
    private String code;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 100, message = "部门名称长度不能超过100个字符")
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * 上级部门ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 部门负责人ID
     */
    @Column(name = "manager_id")
    private Long managerId;

    /**
     * 部门描述
     */
    @Size(max = 500, message = "部门描述长度不能超过500个字符")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "create_time", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 创建前自动设置时间
     */
    @PrePersist
    protected void onCreate() {
        createTime = new Date();
        updateTime = new Date();
    }

    /**
     * 更新前自动设置时间
     */
    @PreUpdate
    protected void onUpdate() {
        updateTime = new Date();
    }
}