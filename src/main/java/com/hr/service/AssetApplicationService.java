package com.hr.service;

import com.hr.dao.ApprovalRecordRepository;
import com.hr.dao.AssetApplicationRepository;
import com.hr.dao.AssetCatalogRepository;
import com.hr.dao.AssetDistributionRepository;
import com.hr.entity.ApprovalRecord;
import com.hr.entity.AssetApplication;
import com.hr.entity.AssetCatalog;
import com.hr.entity.AssetDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资产申请服务类
 */
@Service
public class AssetApplicationService {

    @Autowired
    private AssetApplicationRepository assetApplicationRepository;

    @Autowired
    private AssetCatalogRepository assetCatalogRepository;

    @Autowired
    private ApprovalRecordRepository approvalRecordRepository;

    @Autowired
    private AssetDistributionRepository assetDistributionRepository;

    /**
     * 获取所有资产申请（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 资产申请分页列表
     */
    public Page<AssetApplication> getAllAssetApplications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return assetApplicationRepository.findAll(pageable);
    }

    /**
     * 根据员工ID获取资产申请列表
     *
     * @param employeeId 员工ID
     * @return 资产申请列表
     */
    public List<AssetApplication> getAssetApplicationsByEmployeeId(Long employeeId) {
        return assetApplicationRepository.findByEmployeeId(employeeId);
    }

    /**
     * 根据状态获取资产申请列表
     *
     * @param status 状态
     * @return 资产申请列表
     */
    public List<AssetApplication> getAssetApplicationsByStatus(String status) {
        return assetApplicationRepository.findByStatus(status);
    }

    /**
     * 根据ID获取资产申请
     *
     * @param id 资产申请ID
     * @return 资产申请信息
     */
    public AssetApplication getAssetApplicationById(Long id) {
        return assetApplicationRepository.findById(id).orElse(null);
    }

    /**
     * 创建资产申请
     *
     * @param assetApplication 资产申请信息
     * @return 创建后的资产申请
     */
    public AssetApplication createAssetApplication(AssetApplication assetApplication) {
        // 设置初始状态为待主管审批
        assetApplication.setStatus("待主管审批");
        return assetApplicationRepository.save(assetApplication);
    }

    /**
     * 主管审批资产申请
     *
     * @param id         资产申请ID
     * @param result     审批结果（通过/驳回）
     * @param comment    审批意见
     * @param approverId 审批人ID
     * @return 更新后的资产申请
     */
    public AssetApplication managerApproveAssetApplication(Long id, String result, String comment, Long approverId) {
        AssetApplication assetApplication = assetApplicationRepository.findById(id).orElse(null);
        if (assetApplication == null) {
            return null;
        }

        // 更新资产申请状态
        if ("通过".equals(result)) {
            assetApplication.setStatus("待管理员审批");
        } else {
            assetApplication.setStatus("已驳回");
        }
        assetApplication.setManagerComment(comment);
        AssetApplication updatedApplication = assetApplicationRepository.save(assetApplication);

        // 记录审批记录
        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setApplicationId(id);
        approvalRecord.setApplicationType("资产申请");
        approvalRecord.setApproverId(approverId);
        approvalRecord.setApproverRole("主管");
        approvalRecord.setResult(result);
        approvalRecord.setComment(comment);
        approvalRecordRepository.save(approvalRecord);

        // TODO: 发送通知给相关人员

        return updatedApplication;
    }

    /**
     * 管理员审批资产申请
     *
     * @param id         资产申请ID
     * @param result     审批结果（通过/驳回）
     * @param comment    审批意见
     * @param approverId 审批人ID
     * @return 更新后的资产申请
     */
    public AssetApplication adminApproveAssetApplication(Long id, String result, String comment, Long approverId) {
        AssetApplication assetApplication = assetApplicationRepository.findById(id).orElse(null);
        if (assetApplication == null) {
            return null;
        }

        if ("通过".equals(result)) {
            // 检查库存是否充足
            AssetCatalog assetCatalog = assetCatalogRepository.findById(assetApplication.getAssetId()).orElse(null);
            if (assetCatalog == null) {
                return null;
            }

            if (assetCatalog.getStockQuantity() < assetApplication.getRequestQuantity()) {
                // 库存不足，驳回申请
                assetApplication.setStatus("已驳回");
                assetApplication.setAdminComment("库存不足，无法批准申请");
                AssetApplication updatedApplication = assetApplicationRepository.save(assetApplication);

                // 记录审批记录
                ApprovalRecord approvalRecord = new ApprovalRecord();
                approvalRecord.setApplicationId(id);
                approvalRecord.setApplicationType("资产申请");
                approvalRecord.setApproverId(approverId);
                approvalRecord.setApproverRole("管理员");
                approvalRecord.setResult("驳回");
                approvalRecord.setComment("库存不足，无法批准申请");
                approvalRecordRepository.save(approvalRecord);

                // TODO: 发送通知给相关人员

                return updatedApplication;
            }

            // 检查预算是否允许
            Double totalCost = assetCatalog.getPrice() * assetApplication.getRequestQuantity();
            if (assetCatalog.getBudgetLimit() != null && totalCost > assetCatalog.getBudgetLimit()) {
                // 预算不足，驳回申请
                assetApplication.setStatus("已驳回");
                assetApplication.setAdminComment("预算不足，无法批准申请");
                AssetApplication updatedApplication = assetApplicationRepository.save(assetApplication);

                // 记录审批记录
                ApprovalRecord approvalRecord = new ApprovalRecord();
                approvalRecord.setApplicationId(id);
                approvalRecord.setApplicationType("资产申请");
                approvalRecord.setApproverId(approverId);
                approvalRecord.setApproverRole("管理员");
                approvalRecord.setResult("驳回");
                approvalRecord.setComment("预算不足，无法批准申请");
                approvalRecordRepository.save(approvalRecord);

                // TODO: 发送通知给相关人员

                return updatedApplication;
            }

            // 库存充足且预算允许，批准申请
            assetApplication.setStatus("已分配");
            assetApplication.setAdminComment(comment);
            AssetApplication updatedApplication = assetApplicationRepository.save(assetApplication);

            // 更新库存数量
            assetCatalog.setStockQuantity(assetCatalog.getStockQuantity() - assetApplication.getRequestQuantity());
            assetCatalogRepository.save(assetCatalog);

            // 记录资产分配
            AssetDistribution assetDistribution = new AssetDistribution();
            assetDistribution.setApplicationId(id);
            assetDistribution.setAssetId(assetApplication.getAssetId());
            assetDistribution.setDistributionQuantity(assetApplication.getRequestQuantity());
            assetDistribution.setEmployeeId(assetApplication.getEmployeeId());
            assetDistribution.setReceiveStatus("待接收");
            assetDistributionRepository.save(assetDistribution);

            // 记录审批记录
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApplicationId(id);
            approvalRecord.setApplicationType("资产申请");
            approvalRecord.setApproverId(approverId);
            approvalRecord.setApproverRole("管理员");
            approvalRecord.setResult("通过");
            approvalRecord.setComment(comment);
            approvalRecordRepository.save(approvalRecord);

            // TODO: 发送通知给相关人员

            return updatedApplication;
        } else {
            // 管理员驳回申请
            assetApplication.setStatus("已驳回");
            assetApplication.setAdminComment(comment);
            AssetApplication updatedApplication = assetApplicationRepository.save(assetApplication);

            // 记录审批记录
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApplicationId(id);
            approvalRecord.setApplicationType("资产申请");
            approvalRecord.setApproverId(approverId);
            approvalRecord.setApproverRole("管理员");
            approvalRecord.setResult("驳回");
            approvalRecord.setComment(comment);
            approvalRecordRepository.save(approvalRecord);

            // TODO: 发送通知给相关人员

            return updatedApplication;
        }
    }

    /**
     * 员工确认接收资产
     *
     * @param distributionId 资产分配ID
     * @return 更新后的资产分配记录
     */
    public AssetDistribution confirmReceiveAsset(Long distributionId) {
        AssetDistribution assetDistribution = assetDistributionRepository.findById(distributionId).orElse(null);
        if (assetDistribution == null) {
            return null;
        }

        // 更新资产分配状态为已接收
        assetDistribution.setReceiveStatus("已接收");
        AssetDistribution updatedDistribution = assetDistributionRepository.save(assetDistribution);

        // 更新资产申请状态为已完成
        AssetApplication assetApplication = assetApplicationRepository.findById(assetDistribution.getApplicationId()).orElse(null);
        if (assetApplication != null) {
            assetApplication.setStatus("已完成");
            assetApplicationRepository.save(assetApplication);
        }

        // TODO: 发送通知给相关人员

        return updatedDistribution;
    }

    /**
     * 员工提出资产异议
     *
     * @param distributionId 资产分配ID
     * @param feedback       反馈内容
     * @return 更新后的资产分配记录
     */
    public AssetDistribution raiseAssetObjection(Long distributionId, String feedback) {
        AssetDistribution assetDistribution = assetDistributionRepository.findById(distributionId).orElse(null);
        if (assetDistribution == null) {
            return null;
        }

        // 更新资产分配状态为有异议
        assetDistribution.setReceiveStatus("有异议");
        assetDistribution.setEmployeeFeedback(feedback);
        return assetDistributionRepository.save(assetDistribution);
    }

    /**
     * 管理员处理资产异议
     *
     * @param distributionId 资产分配ID
     * @param processResult  处理结果
     * @return 更新后的资产分配记录
     */
    public AssetDistribution processAssetObjection(Long distributionId, String processResult) {
        AssetDistribution assetDistribution = assetDistributionRepository.findById(distributionId).orElse(null);
        if (assetDistribution == null) {
            return null;
        }

        // 更新资产分配状态为已接收，并记录处理结果
        assetDistribution.setReceiveStatus("已接收");
        assetDistribution.setAdminProcessResult(processResult);
        AssetDistribution updatedDistribution = assetDistributionRepository.save(assetDistribution);

        // 更新资产申请状态为已完成
        AssetApplication assetApplication = assetApplicationRepository.findById(assetDistribution.getApplicationId()).orElse(null);
        if (assetApplication != null) {
            assetApplication.setStatus("已完成");
            assetApplicationRepository.save(assetApplication);
        }

        // TODO: 发送通知给相关人员

        return updatedDistribution;
    }

    /**
     * 获取资产申请的审批记录
     *
     * @param applicationId 资产申请ID
     * @return 审批记录列表
     */
    public List<ApprovalRecord> getApprovalRecords(Long applicationId) {
        return approvalRecordRepository.findByApplicationIdAndApplicationType(applicationId, "资产申请");
    }

    /**
     * 获取资产分配记录
     *
     * @param applicationId 资产申请ID
     * @return 资产分配记录列表
     */
    public List<AssetDistribution> getAssetDistributionsByApplicationId(Long applicationId) {
        return assetDistributionRepository.findByApplicationId(applicationId);
    }
}