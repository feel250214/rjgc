package com.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * 薪资明细实体类
 */
@Data
@Entity
@Table(name = "salary_detail")
public class SalaryDetail {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 员工ID
     */
    @NotNull(message = "员工ID不能为空")
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    /**
     * 薪资周期（如2025-01）
     */
    @NotBlank(message = "薪资周期不能为空")
    @Size(max = 20, message = "薪资周期长度不能超过20个字符")
    @Column(name = "salary_period", nullable = false)
    private String salaryPeriod;

    /**
     * 基本工资
     */
    @NotNull(message = "基本工资不能为空")
    @Min(value = 0, message = "基本工资不能为负数")
    @Column(name = "basic_salary", nullable = false)
    private Double basicSalary;

    /**
     * 绩效奖金
     */
    @Min(value = 0, message = "绩效奖金不能为负数")
    @Column(name = "performance_bonus")
    private Double performanceBonus;

    /**
     * 津贴补贴
     */
    @Min(value = 0, message = "津贴补贴不能为负数")
    @Column(name = "allowance")
    private Double allowance;

    /**
     * 加班工资
     */
    @Min(value = 0, message = "加班工资不能为负数")
    @Column(name = "overtime_pay")
    private Double overtimePay;

    /**
     * 社保扣款
     */
    @Min(value = 0, message = "社保扣款不能为负数")
    @Column(name = "social_security_deduction")
    private Double socialSecurityDeduction;

    /**
     * 公积金扣款
     */
    @Min(value = 0, message = "公积金扣款不能为负数")
    @Column(name = "housing_fund_deduction")
    private Double housingFundDeduction;

    /**
     * 个税扣款
     */
    @Min(value = 0, message = "个税扣款不能为负数")
    @Column(name = "tax_deduction")
    private Double taxDeduction;

    /**
     * 其他扣款
     */
    @Min(value = 0, message = "其他扣款不能为负数")
    @Column(name = "other_deductions")
    private Double otherDeductions;

    /**
     * 实发工资
     */
    @NotNull(message = "实发工资不能为空")
    @Column(name = "net_salary", nullable = false)
    private Double netSalary;

    /**
     * 薪资状态：待审核、已审核、已发放、已驳回
     */
    @NotBlank(message = "薪资状态不能为空")
    @Size(max = 50, message = "薪资状态长度不能超过50个字符")
    @Column(nullable = false)
    private String status;

    /**
     * 审核人ID
     */
    @Column(name = "auditor_id")
    private Long auditorId;

    /**
     * 审核时间
     */
    @Column(name = "audit_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auditTime;

    /**
     * 发放时间
     */
    @Column(name = "payment_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentTime;

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