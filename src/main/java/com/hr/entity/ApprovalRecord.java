package com.hr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * 审批记录实体类
 */
@Data
@Entity
@Table(name = "approval_record")
public class ApprovalRecord {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关联的申请ID（如请假申请ID、资产申请ID等）
     */
    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    /**
     * 申请类型：请假申请、资产申请等
     */
    @Column(name = "application_type", nullable = false)
    private String applicationType;

    /**
     * 审批人ID
     */
    @Column(name = "approver_id", nullable = false)
    private Long approverId;

    /**
     * 审批人角色：主管、管理员等
     */
    @Column(name = "approver_role", nullable = false)
    private String approverRole;

    /**
     * 审批结果：通过、驳回
     */
    @Column(nullable = false)
    private String result;

    /**
     * 审批意见
     */
    private String comment;

    /**
     * 审批时间
     */
    @Column(name = "approval_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalTime;

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
        approvalTime = new Date();
    }
}