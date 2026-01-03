package com.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * 请假申请表实体类
 */
@Data
@Entity
@Table(name = "leave_application")
public class LeaveApplication {
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
     * 请假类型
     */
    @NotBlank(message = "请假类型不能为空")
    @Size(max = 50, message = "请假类型长度不能超过50个字符")
    @Column(nullable = false)
    private String type;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    @Column(name = "end_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    /**
     * 请假原因
     */
    @NotBlank(message = "请假原因不能为空")
    @Size(max = 1000, message = "请假原因长度不能超过1000个字符")
    @Column(nullable = false)
    private String reason;

    /**
     * 相关证明文件路径
     */
    @Size(max = 255, message = "证明文件路径长度不能超过255个字符")
    private String attachment;

    /**
     * 状态：待主管审批、待管理员审批、已驳回、已批准
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