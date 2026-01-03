package com.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * 职位实体类
 */
@Data
@Entity
@Table(name = "position")
public class Position {
    /**
     * 职位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 职位名称
     */
    @NotBlank(message = "职位名称不能为空")
    @Size(max = 100, message = "职位名称长度不能超过100个字符")
    @Column(nullable = false)
    private String name;

    /**
     * 部门ID
     */
    @NotNull(message = "部门ID不能为空")
    @Column(nullable = false)
    private Long departmentId;

    /**
     * 职位描述
     */
    @Size(max = 500, message = "职位描述长度不能超过500个字符")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "create_time", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 创建前自动设置时间
     */
    @PrePersist
    protected void onCreate() {
        createTime = new Date();
    }
}