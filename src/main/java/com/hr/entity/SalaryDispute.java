package com.hr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * 薪资质疑实体类
 */
@Data
@Entity
@Table(name = "salary_dispute")
public class SalaryDispute {
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
     * 薪资明细ID
     */
    @Column(name = "salary_detail_id", nullable = false)
    private Long salaryDetailId;

    /**
     * 质疑内容
     */
    @Column(nullable = false)
    private String disputeContent;

    /**
     * 质疑时间
     */
    @Column(name = "dispute_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date disputeTime;

    /**
     * HR解答内容
     */
    @Column(name = "hr_response")
    private String hrResponse;

    /**
     * HR解答时间
     */
    @Column(name = "hr_response_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hrResponseTime;

    /**
     * 状态：待处理、已处理
     */
    @Column(nullable = false)
    private String status;

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
        disputeTime = new Date();
    }

    /**
     * 更新前自动设置时间
     */
    @PreUpdate
    protected void onUpdate() {
        updateTime = new Date();
    }
}