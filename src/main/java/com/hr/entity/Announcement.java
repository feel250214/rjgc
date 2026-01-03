package com.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * 公告实体类
 */
@Data
@Entity
@Table(name = "announcement")
public class Announcement {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    @NotBlank(message = "公告标题不能为空")
    @Size(max = 200, message = "公告标题长度不能超过200个字符")
    @Column(nullable = false)
    private String title;

    /**
     * 正文内容
     */
    @NotBlank(message = "公告内容不能为空")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 公告类型
     */
    @NotBlank(message = "公告类型不能为空")
    @Size(max = 50, message = "公告类型长度不能超过50个字符")
    @Column(nullable = false)
    private String type;

    /**
     * 有效期开始时间
     */
    @NotNull(message = "有效期开始时间不能为空")
    @Column(name = "valid_from", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;

    /**
     * 有效期结束时间
     */
    @NotNull(message = "有效期结束时间不能为空")
    @Column(name = "valid_to", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;

    /**
     * 发布范围：全部、部门、特定人员
     */
    @NotBlank(message = "发布范围不能为空")
    @Size(max = 50, message = "发布范围长度不能超过50个字符")
    @Column(name = "publish_scope", nullable = false)
    private String publishScope;

    /**
     * 关联的部门ID（多个部门用逗号分隔）
     */
    @Size(max = 255, message = "部门ID列表长度不能超过255个字符")
    @Column(name = "department_ids")
    private String departmentIds;

    /**
     * 关联的人员ID（多个人员用逗号分隔）
     */
    @Size(max = 255, message = "人员ID列表长度不能超过255个字符")
    @Column(name = "employee_ids")
    private String employeeIds;

    /**
     * 附件路径（多个附件用逗号分隔）
     */
    @Size(max = 255, message = "附件路径长度不能超过255个字符")
    private String attachments;

    /**
     * 状态：草稿、已发布、已删除
     */
    @NotBlank(message = "公告状态不能为空")
    @Size(max = 50, message = "公告状态长度不能超过50个字符")
    @Column(nullable = false)
    private String status;

    /**
     * 发布人ID
     */
    @NotNull(message = "发布人ID不能为空")
    @Column(name = "publisher_id", nullable = false)
    private Long publisherId;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishTime;

    /**
     * 版本号
     */
    @NotNull(message = "版本号不能为空")
    @Min(value = 1, message = "版本号不能小于1")
    @Column(nullable = false)
    private Integer version;

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
     * 创建前自动设置时间和版本号
     */
    @PrePersist
    protected void onCreate() {
        createTime = new Date();
        updateTime = new Date();
        version = 1;
    }

    /**
     * 更新前自动设置时间
     */
    @PreUpdate
    protected void onUpdate() {
        updateTime = new Date();
        version += 1;
    }
}