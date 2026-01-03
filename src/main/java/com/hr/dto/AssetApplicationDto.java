package com.hr.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * 资产申请DTO
 */
@Data
public class AssetApplicationDto {
    private Long id;

    @NotNull(message = "申请人ID不能为空")
    private Long employeeId;

    @NotNull(message = "资产ID不能为空")
    private Long assetId;

    @NotNull(message = "申请数量不能为空")
    @Min(value = 1, message = "申请数量不能小于1")
    private Integer requestQuantity;

    @NotBlank(message = "申请用途不能为空")
    @Size(max = 1000, message = "申请用途长度不能超过1000个字符")
    private String purpose;

    private String status;

    @Size(max = 1000, message = "主管审批意见长度不能超过1000个字符")
    private String managerComment;

    @Size(max = 1000, message = "管理员审批意见长度不能超过1000个字符")
    private String adminComment;

    private Date createTime;
    private Date updateTime;
}