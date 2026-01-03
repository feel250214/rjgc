package com.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * 资产申请实体类
 */
@Data
@Entity
@Table(name = "asset_application")
public class AssetApplication {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 申请人ID
     */
    @NotNull(message = "申请人ID不能为空")
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    /**
     * 资产ID
     */
    @NotNull(message = "资产ID不能为空")
    @Column(name = "asset_id", nullable = false)
    private Long assetId;

    /**
     * 申请数量
     */
    @NotNull(message = "申请数量不能为空")
    @Min(value = 1, message = "申请数量不能小于1")
    @Column(name = "request_quantity", nullable = false)
    private Integer requestQuantity;

    /**
     * 申请用途
     */
    @NotBlank(message = "申请用途不能为空")
    @Size(max = 1000, message = "申请用途长度不能超过1000个字符")
    @Column(nullable = false)
    private String purpose;

    /**
     * 状态：待主管审批、待管理员审批、已驳回、已分配、已完成
     */
    @NotBlank(message = "状态不能为空")
    @Size(max = 50, message = "状态长度不能超过50个字符")
    @Column(nullable = false)
    private String status;

    /**
     * 主管审批意见
     */
    @Size(max = 1000, message = "主管审批意见长度不能超过1000个字符")
    @Column(name = "manager_comment")
    private String managerComment;

    /**
     * 管理员审批意见
     */
    @Size(max = 1000, message = "管理员审批意见长度不能超过1000个字符")
    @Column(name = "admin_comment")
    private String adminComment;

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