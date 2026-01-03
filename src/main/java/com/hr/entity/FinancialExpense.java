package com.hr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * 财务支出记录实体类
 */
@Data
@Entity
@Table(name = "financial_expense")
public class FinancialExpense {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 支出类型
     */
    @Column(nullable = false)
    private String type;

    /**
     * 支出金额
     */
    @Column(nullable = false)
    private Double amount;

    /**
     * 支出日期
     */
    @Column(name = "expense_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expenseDate;

    /**
     * 支出用途
     */
    @Column(nullable = false)
    private String purpose;

    /**
     * 关联的项目或申请ID（如资产申请ID、报销申请ID等）
     */
    @Column(name = "related_id")
    private Long relatedId;

    /**
     * 关联类型（如资产申请、报销申请等）
     */
    @Column(name = "related_type")
    private String relatedType;

    /**
     * 支付方式：现金、银行转账、支付宝、微信等
     */
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

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