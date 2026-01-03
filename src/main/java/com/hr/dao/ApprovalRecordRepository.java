package com.hr.dao;

import com.hr.entity.ApprovalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 审批记录数据访问接口
 */
@Repository
public interface ApprovalRecordRepository extends JpaRepository<ApprovalRecord, Long> {
    /**
     * 根据申请ID和申请类型查询审批记录
     *
     * @param applicationId   申请ID
     * @param applicationType 申请类型
     * @return 审批记录列表
     */
    List<ApprovalRecord> findByApplicationIdAndApplicationType(Long applicationId, String applicationType);

    /**
     * 根据审批人ID查询审批记录
     *
     * @param approverId 审批人ID
     * @return 审批记录列表
     */
    List<ApprovalRecord> findByApproverId(Long approverId);
}