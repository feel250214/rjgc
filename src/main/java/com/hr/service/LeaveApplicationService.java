package com.hr.service;

import com.hr.dao.ApprovalRecordRepository;
import com.hr.dao.LeaveApplicationRepository;
import com.hr.entity.ApprovalRecord;
import com.hr.entity.LeaveApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 请假申请服务类
 */
@Service
public class LeaveApplicationService {

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private ApprovalRecordRepository approvalRecordRepository;

    /**
     * 获取所有请假申请（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 请假申请分页列表
     */
    public Page<LeaveApplication> getAllLeaveApplications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return leaveApplicationRepository.findAll(pageable);
    }

    /**
     * 根据员工ID获取请假申请列表
     *
     * @param employeeId 员工ID
     * @return 请假申请列表
     */
    public List<LeaveApplication> getLeaveApplicationsByEmployeeId(Long employeeId) {
        return leaveApplicationRepository.findByEmployeeId(employeeId);
    }

    /**
     * 根据状态获取请假申请列表
     *
     * @param status 状态
     * @return 请假申请列表
     */
    public List<LeaveApplication> getLeaveApplicationsByStatus(String status) {
        return leaveApplicationRepository.findByStatus(status);
    }

    /**
     * 根据ID获取请假申请
     *
     * @param id 请假申请ID
     * @return 请假申请信息
     */
    public LeaveApplication getLeaveApplicationById(Long id) {
        return leaveApplicationRepository.findById(id).orElse(null);
    }

    /**
     * 创建请假申请
     *
     * @param leaveApplication 请假申请信息
     * @return 创建后的请假申请
     */
    public LeaveApplication createLeaveApplication(LeaveApplication leaveApplication) {
        // 设置初始状态为待主管审批
        leaveApplication.setStatus("待主管审批");
        return leaveApplicationRepository.save(leaveApplication);
    }

    /**
     * 主管审批请假申请
     *
     * @param id         请假申请ID
     * @param result     审批结果（通过/驳回）
     * @param comment    审批意见
     * @param approverId 审批人ID
     * @return 更新后的请假申请
     */
    public LeaveApplication managerApproveLeaveApplication(Long id, String result, String comment, Long approverId) {
        LeaveApplication leaveApplication = leaveApplicationRepository.findById(id).orElse(null);
        if (leaveApplication == null) {
            return null;
        }

        // 更新请假申请状态
        if ("通过".equals(result)) {
            leaveApplication.setStatus("待管理员审批");
        } else {
            leaveApplication.setStatus("已驳回");
        }
        leaveApplication.setManagerComment(comment);
        LeaveApplication updatedApplication = leaveApplicationRepository.save(leaveApplication);

        // 记录审批记录
        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setApplicationId(id);
        approvalRecord.setApplicationType("请假申请");
        approvalRecord.setApproverId(approverId);
        approvalRecord.setApproverRole("主管");
        approvalRecord.setResult(result);
        approvalRecord.setComment(comment);
        approvalRecordRepository.save(approvalRecord);

        // TODO: 发送通知给相关人员

        return updatedApplication;
    }

    /**
     * 管理员审批请假申请
     *
     * @param id         请假申请ID
     * @param result     审批结果（通过/驳回）
     * @param comment    审批意见
     * @param approverId 审批人ID
     * @return 更新后的请假申请
     */
    public LeaveApplication adminApproveLeaveApplication(Long id, String result, String comment, Long approverId) {
        LeaveApplication leaveApplication = leaveApplicationRepository.findById(id).orElse(null);
        if (leaveApplication == null) {
            return null;
        }

        // 更新请假申请状态
        if ("通过".equals(result)) {
            leaveApplication.setStatus("已批准");
            // TODO: 更新请假记录、更新考勤统计
        } else {
            leaveApplication.setStatus("已驳回");
        }
        leaveApplication.setAdminComment(comment);
        LeaveApplication updatedApplication = leaveApplicationRepository.save(leaveApplication);

        // 记录审批记录
        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setApplicationId(id);
        approvalRecord.setApplicationType("请假申请");
        approvalRecord.setApproverId(approverId);
        approvalRecord.setApproverRole("管理员");
        approvalRecord.setResult(result);
        approvalRecord.setComment(comment);
        approvalRecordRepository.save(approvalRecord);

        // TODO: 发送通知给相关人员

        return updatedApplication;
    }

    /**
     * 获取请假申请的审批记录
     *
     * @param applicationId 请假申请ID
     * @return 审批记录列表
     */
    public List<ApprovalRecord> getApprovalRecords(Long applicationId) {
        return approvalRecordRepository.findByApplicationIdAndApplicationType(applicationId, "请假申请");
    }
}