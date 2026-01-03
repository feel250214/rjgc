package com.hr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * 公告阅读记录实体类
 */
@Data
@Entity
@Table(name = "announcement_read_record")
public class AnnouncementReadRecord {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 公告ID
     */
    @Column(name = "announcement_id", nullable = false)
    private Long announcementId;

    /**
     * 员工ID
     */
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    /**
     * 阅读时间
     */
    @Column(name = "read_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date readTime;

    /**
     * 是否已确认阅读：0-未确认，1-已确认
     */
    @Column(name = "is_confirmed", nullable = false)
    private Integer isConfirmed;

    /**
     * 确认阅读时间
     */
    @Column(name = "confirm_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmTime;

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
        readTime = new Date();
        isConfirmed = 0;
    }
}