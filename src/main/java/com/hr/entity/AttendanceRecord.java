package com.hr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * 考勤记录实体类
 */
@Data
@Entity
@Table(name = "attendance_record")
public class AttendanceRecord {
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
     * 考勤日期
     */
    @Column(name = "record_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date recordDate;

    /**
     * 考勤状态：正常、迟到、早退、旷工、请假
     */
    @Column(nullable = false)
    private String status;

    /**
     * 上班打卡时间
     */
    @Column(name = "check_in_time")
    @Temporal(TemporalType.TIME)
    private Date checkInTime;

    /**
     * 下班打卡时间
     */
    @Column(name = "check_out_time")
    @Temporal(TemporalType.TIME)
    private Date checkOutTime;

    /**
     * 请假小时数
     */
    @Column(name = "leave_hours")
    private Double leaveHours;

    /**
     * 加班小时数
     */
    @Column(name = "overtime_hours")
    private Double overtimeHours;

    /**
     * 备注
     */
    @Column(name = "description")
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