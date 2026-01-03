package com.hr.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * 资产目录DTO
 */
@Data
public class AssetCatalogDto {
    private Long id;

    @NotBlank(message = "资产类型不能为空")
    @Size(max = 50, message = "资产类型长度不能超过50个字符")
    private String type;

    @NotBlank(message = "资产名称不能为空")
    @Size(max = 100, message = "资产名称长度不能超过100个字符")
    private String name;

    @Size(max = 500, message = "资产描述长度不能超过500个字符")
    private String description;

    @NotNull(message = "库存数量不能为空")
    @Min(value = 0, message = "库存数量不能为负数")
    private Integer stockQuantity;

    @NotBlank(message = "单位不能为空")
    @Size(max = 20, message = "单位长度不能超过20个字符")
    private String unit;

    @NotNull(message = "单价不能为空")
    @Min(value = 0, message = "单价不能为负数")
    private Double price;

    @Min(value = 0, message = "预算限制不能为负数")
    private Double budgetLimit;

    @NotBlank(message = "状态不能为空")
    @Size(max = 50, message = "状态长度不能超过50个字符")
    private String status;

    private Date createTime;
    private Date updateTime;
}