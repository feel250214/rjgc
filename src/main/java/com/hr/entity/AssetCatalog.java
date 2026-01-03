package com.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * 资产目录实体类
 */
@Data
@Entity
@Table(name = "asset_catalog")
public class AssetCatalog {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资产类型
     */
    @NotBlank(message = "资产类型不能为空")
    @Size(max = 50, message = "资产类型长度不能超过50个字符")
    @Column(nullable = false)
    private String type;

    /**
     * 资产名称
     */
    @NotBlank(message = "资产名称不能为空")
    @Size(max = 100, message = "资产名称长度不能超过100个字符")
    @Column(nullable = false)
    private String name;

    /**
     * 资产描述
     */
    @Size(max = 500, message = "资产描述长度不能超过500个字符")
    private String description;

    /**
     * 库存数量
     */
    @NotNull(message = "库存数量不能为空")
    @Min(value = 0, message = "库存数量不能为负数")
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空")
    @Size(max = 20, message = "单位长度不能超过20个字符")
    @Column(nullable = false)
    private String unit;

    /**
     * 单价
     */
    @NotNull(message = "单价不能为空")
    @Min(value = 0, message = "单价不能为负数")
    @Column(nullable = false)
    private Double price;

    /**
     * 预算限制
     */
    @Min(value = 0, message = "预算限制不能为负数")
    @Column(name = "budget_limit")
    private Double budgetLimit;

    /**
     * 状态：可用、不可用
     */
    @NotBlank(message = "状态不能为空")
    @Size(max = 50, message = "状态长度不能超过50个字符")
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
    }

    /**
     * 更新前自动设置时间
     */
    @PreUpdate
    protected void onUpdate() {
        updateTime = new Date();
    }
}