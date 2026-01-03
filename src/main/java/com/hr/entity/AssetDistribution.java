package com.hr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * 资产分配记录实体类
 */
@Data
@Entity
@Table(name = "asset_distribution")
public class AssetDistribution {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资产申请ID
     */
    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    /**
     * 资产ID
     */
    @Column(name = "asset_id", nullable = false)
    private Long assetId;

    /**
     * 分配数量
     */
    @Column(name = "distribution_quantity", nullable = false)
    private Integer distributionQuantity;

    /**
     * 接收员工ID
     */
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    /**
     * 分配时间
     */
    @Column(name = "distribution_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date distributionTime;

    /**
     * 接收状态：待接收、已接收、有异议
     */
    @Column(name = "receive_status", nullable = false)
    private String receiveStatus;

    /**
     * 员工反馈意见
     */
    @Column(name = "employee_feedback")
    private String employeeFeedback;

    /**
     * 管理员处理结果
     */
    @Column(name = "admin_process_result")
    private String adminProcessResult;

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
        distributionTime = new Date();
    }

    /**
     * 更新前自动设置时间
     */
    @PreUpdate
    protected void onUpdate() {
        updateTime = new Date();
    }
}