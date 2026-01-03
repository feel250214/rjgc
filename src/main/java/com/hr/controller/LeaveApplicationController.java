package com.hr.controller;

import com.hr.dto.ResponseDto;
import com.hr.entity.ApprovalRecord;
import com.hr.entity.LeaveApplication;
import com.hr.service.LeaveApplicationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 请假申请控制器
 */
@RestController
@RequestMapping("/leave-applications")
@Slf4j
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    /**
     * 获取所有请假申请（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 请假申请分页列表
     */
    @GetMapping
    public ResponseDto<Page<LeaveApplication>> getAllLeaveApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<LeaveApplication> leaveApplications = leaveApplicationService.getAllLeaveApplications(page, size);
            return ResponseDto.success(leaveApplications);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get leave applications: " + e.getMessage());
        }
    }

    /**
     * 根据员工ID获取请假申请列表
     *
     * @param employeeId 员工ID
     * @return 请假申请列表
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseDto<List<LeaveApplication>> getLeaveApplicationsByEmployeeId(@PathVariable Long employeeId) {
        try {
            List<LeaveApplication> leaveApplications = leaveApplicationService.getLeaveApplicationsByEmployeeId(employeeId);
            return ResponseDto.success(leaveApplications);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get leave applications: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取请假申请
     *
     * @param id 请假申请ID
     * @return 请假申请信息
     */
    @GetMapping("/{id}")
    public ResponseDto<LeaveApplication> getLeaveApplicationById(@PathVariable Long id) {
        try {
            LeaveApplication leaveApplication = leaveApplicationService.getLeaveApplicationById(id);
            if (leaveApplication == null) {
                return ResponseDto.fail(404, "Leave application not found");
            }
            return ResponseDto.success(leaveApplication);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get leave application: " + e.getMessage());
        }
    }

    /**
     * 创建请假申请
     *
     * @param leaveApplication 请假申请信息
     * @param result           绑定结果
     * @return 创建后的请假申请
     */
    @PostMapping
    public ResponseDto<LeaveApplication> createLeaveApplication(@Valid @RequestBody LeaveApplication leaveApplication, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseDto.fail(400, result.getFieldError().getDefaultMessage());
        }
        try {
            LeaveApplication createdLeaveApplication = leaveApplicationService.createLeaveApplication(leaveApplication);
            return ResponseDto.success(createdLeaveApplication);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create leave application: " + e.getMessage());
        }
    }

    /**
     * 主管审批请假申请
     *
     * @param id         请假申请ID
     * @param result     审批结果
     * @param comment    审批意见
     * @param approverId 审批人ID
     * @return 更新后的请假申请
     */
    @PostMapping("/{id}/manager-approve")
    public ResponseDto<LeaveApplication> managerApproveLeaveApplication(
            @PathVariable Long id,
            @RequestParam String result,
            @RequestParam String comment,
            @RequestParam Long approverId) {
        try {
            LeaveApplication updatedLeaveApplication = leaveApplicationService.managerApproveLeaveApplication(
                    id, result, comment, approverId);
            if (updatedLeaveApplication == null) {
                return ResponseDto.fail(404, "Leave application not found");
            }
            return ResponseDto.success(updatedLeaveApplication);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to approve leave application: " + e.getMessage());
        }
    }

    /**
     * 管理员审批请假申请
     *
     * @param id         请假申请ID
     * @param result     审批结果
     * @param comment    审批意见
     * @param approverId 审批人ID
     * @return 更新后的请假申请
     */
    @PostMapping("/{id}/admin-approve")
    public ResponseDto<LeaveApplication> adminApproveLeaveApplication(
            @PathVariable Long id,
            @RequestParam String result,
            @RequestParam String comment,
            @RequestParam Long approverId) {
        try {
            LeaveApplication updatedLeaveApplication = leaveApplicationService.adminApproveLeaveApplication(
                    id, result, comment, approverId);
            if (updatedLeaveApplication == null) {
                return ResponseDto.fail(404, "Leave application not found");
            }
            return ResponseDto.success(updatedLeaveApplication);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to approve leave application: " + e.getMessage());
        }
    }

    /**
     * 获取请假申请的审批记录
     *
     * @param id 请假申请ID
     * @return 审批记录列表
     */
    @GetMapping("/{id}/approval-records")
    public ResponseDto<List<ApprovalRecord>> getApprovalRecords(@PathVariable Long id) {
        try {
            List<ApprovalRecord> approvalRecords = leaveApplicationService.getApprovalRecords(id);
            return ResponseDto.success(approvalRecords);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get approval records: " + e.getMessage());
        }
    }
}