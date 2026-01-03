package com.hr.controller;

import com.hr.converter.AssetApplicationConverter;
import com.hr.dto.AssetApplicationDto;
import com.hr.dto.ResponseDto;
import com.hr.entity.ApprovalRecord;
import com.hr.entity.AssetApplication;
import com.hr.entity.AssetDistribution;
import com.hr.service.AssetApplicationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资产申请控制器
 */
@RestController
@RequestMapping("/asset-applications")
@Slf4j
public class AssetApplicationController {

    @Autowired
    private AssetApplicationService assetApplicationService;

    @Autowired
    private AssetApplicationConverter assetApplicationConverter;

    /**
     * 获取所有资产申请（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 资产申请分页列表
     */
    @GetMapping
    public ResponseDto<Page<AssetApplicationDto>> getAllAssetApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<AssetApplication> assetApplications = assetApplicationService.getAllAssetApplications(page, size);

            // 转换为DTO列表
            List<AssetApplicationDto> dtoList = assetApplications.getContent().stream()
                    .map(assetApplicationConverter::toDto)
                    .collect(Collectors.toList());

            // 创建DTO分页对象
            Page<AssetApplicationDto> dtoPage = new PageImpl<>(dtoList, assetApplications.getPageable(), assetApplications.getTotalElements());

            return ResponseDto.success(dtoPage);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get asset applications: " + e.getMessage());
        }
    }

    /**
     * 根据员工ID获取资产申请列表
     *
     * @param employeeId 员工ID
     * @return 资产申请列表
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseDto<List<AssetApplicationDto>> getAssetApplicationsByEmployeeId(@PathVariable Long employeeId) {
        try {
            List<AssetApplication> assetApplications = assetApplicationService.getAssetApplicationsByEmployeeId(employeeId);

            // 转换为DTO列表
            List<AssetApplicationDto> dtoList = assetApplications.stream()
                    .map(assetApplicationConverter::toDto)
                    .collect(Collectors.toList());

            return ResponseDto.success(dtoList);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get asset applications: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取资产申请
     *
     * @param id 资产申请ID
     * @return 资产申请信息
     */
    @GetMapping("/{id}")
    public ResponseDto<AssetApplicationDto> getAssetApplicationById(@PathVariable Long id) {
        try {
            AssetApplication assetApplication = assetApplicationService.getAssetApplicationById(id);
            if (assetApplication == null) {
                return ResponseDto.fail(404, "Asset application not found");
            }

            // 转换为DTO
            AssetApplicationDto dto = assetApplicationConverter.toDto(assetApplication);

            return ResponseDto.success(dto);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get asset application: " + e.getMessage());
        }
    }

    /**
     * 创建资产申请
     *
     * @param assetApplicationDto 资产申请信息
     * @param result              绑定结果
     * @return 创建后的资产申请
     */
    @PostMapping
    public ResponseDto<AssetApplicationDto> createAssetApplication(@Valid @RequestBody AssetApplicationDto assetApplicationDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseDto.fail(400, result.getFieldError().getDefaultMessage());
        }
        try {
            // 转换为实体类
            AssetApplication assetApplication = assetApplicationConverter.toEntity(assetApplicationDto);

            AssetApplication createdAssetApplication = assetApplicationService.createAssetApplication(assetApplication);

            // 转换为DTO返回
            AssetApplicationDto dto = assetApplicationConverter.toDto(createdAssetApplication);

            return ResponseDto.success(dto);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create asset application: " + e.getMessage());
        }
    }

    /**
     * 主管审批资产申请
     *
     * @param id         资产申请ID
     * @param result     审批结果
     * @param comment    审批意见
     * @param approverId 审批人ID
     * @return 更新后的资产申请
     */
    @PostMapping("/{id}/manager-approve")
    public ResponseDto<AssetApplicationDto> managerApproveAssetApplication(
            @PathVariable Long id,
            @RequestParam String result,
            @RequestParam String comment,
            @RequestParam Long approverId) {
        try {
            AssetApplication updatedAssetApplication = assetApplicationService.managerApproveAssetApplication(
                    id, result, comment, approverId);
            if (updatedAssetApplication == null) {
                return ResponseDto.fail(404, "Asset application not found");
            }

            // 转换为DTO返回
            AssetApplicationDto dto = assetApplicationConverter.toDto(updatedAssetApplication);

            return ResponseDto.success(dto);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to approve asset application: " + e.getMessage());
        }
    }

    /**
     * 管理员审批资产申请
     *
     * @param id         资产申请ID
     * @param result     审批结果
     * @param comment    审批意见
     * @param approverId 审批人ID
     * @return 更新后的资产申请
     */
    @PostMapping("/{id}/admin-approve")
    public ResponseDto<AssetApplicationDto> adminApproveAssetApplication(
            @PathVariable Long id,
            @RequestParam String result,
            @RequestParam String comment,
            @RequestParam Long approverId) {
        try {
            AssetApplication updatedAssetApplication = assetApplicationService.adminApproveAssetApplication(
                    id, result, comment, approverId);
            if (updatedAssetApplication == null) {
                return ResponseDto.fail(404, "Asset application not found");
            }

            // 转换为DTO返回
            AssetApplicationDto dto = assetApplicationConverter.toDto(updatedAssetApplication);

            return ResponseDto.success(dto);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to approve asset application: " + e.getMessage());
        }
    }

    /**
     * 员工确认接收资产
     *
     * @param distributionId 资产分配ID
     * @return 更新后的资产分配记录
     */
    @PostMapping("/asset-distributions/{distributionId}/confirm-receive")
    public ResponseDto<AssetDistribution> confirmReceiveAsset(@PathVariable Long distributionId) {
        try {
            AssetDistribution updatedDistribution = assetApplicationService.confirmReceiveAsset(distributionId);
            if (updatedDistribution == null) {
                return ResponseDto.fail(404, "Asset distribution not found");
            }
            return ResponseDto.success(updatedDistribution);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to confirm receive asset: " + e.getMessage());
        }
    }

    /**
     * 员工提出资产异议
     *
     * @param distributionId 资产分配ID
     * @param feedback       反馈内容
     * @return 更新后的资产分配记录
     */
    @PostMapping("/asset-distributions/{distributionId}/raise-objection")
    public ResponseDto<AssetDistribution> raiseAssetObjection(
            @PathVariable Long distributionId,
            @RequestParam String feedback) {
        try {
            AssetDistribution updatedDistribution = assetApplicationService.raiseAssetObjection(distributionId, feedback);
            if (updatedDistribution == null) {
                return ResponseDto.fail(404, "Asset distribution not found");
            }
            return ResponseDto.success(updatedDistribution);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to raise asset objection: " + e.getMessage());
        }
    }

    /**
     * 管理员处理资产异议
     *
     * @param distributionId 资产分配ID
     * @param processResult  处理结果
     * @return 更新后的资产分配记录
     */
    @PostMapping("/asset-distributions/{distributionId}/process-objection")
    public ResponseDto<AssetDistribution> processAssetObjection(
            @PathVariable Long distributionId,
            @RequestParam String processResult) {
        try {
            AssetDistribution updatedDistribution = assetApplicationService.processAssetObjection(distributionId, processResult);
            if (updatedDistribution == null) {
                return ResponseDto.fail(404, "Asset distribution not found");
            }
            return ResponseDto.success(updatedDistribution);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to process asset objection: " + e.getMessage());
        }
    }

    /**
     * 获取资产申请的审批记录
     *
     * @param id 资产申请ID
     * @return 审批记录列表
     */
    @GetMapping("/{id}/approval-records")
    public ResponseDto<List<ApprovalRecord>> getApprovalRecords(@PathVariable Long id) {
        try {
            List<ApprovalRecord> approvalRecords = assetApplicationService.getApprovalRecords(id);
            return ResponseDto.success(approvalRecords);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get approval records: " + e.getMessage());
        }
    }

    /**
     * 获取资产分配记录
     *
     * @param id 资产申请ID
     * @return 资产分配记录列表
     */
    @GetMapping("/{id}/asset-distributions")
    public ResponseDto<List<AssetDistribution>> getAssetDistributions(@PathVariable Long id) {
        try {
            List<AssetDistribution> assetDistributions = assetApplicationService.getAssetDistributionsByApplicationId(id);
            return ResponseDto.success(assetDistributions);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get asset distributions: " + e.getMessage());
        }
    }
}