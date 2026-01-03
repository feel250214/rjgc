package com.hr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * 绩效记录实体类
 */
@Data
@Entity
@Table(name = "performance_record")
public class PerformanceRecord {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 员工ID
     */
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    /**
     * 绩效周期（如2025-01）
     */
    @Column(name = "performance_period", nullable = false)
    private String performancePeriod;

    /**
     * 绩效评分
     */
    @Column(nullable = false)
    private Double score;

    /**
     * 绩效等级：优秀、良好、合格、不合格
     */
    @Column(nullable = false)
    private String grade;

    /**
     * 绩效评价
     */
    private String comment;

    /**
     * 考评人ID
     */
    @Column(name = "appraiser_id", nullable = false)
    private Long appraiserId;

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