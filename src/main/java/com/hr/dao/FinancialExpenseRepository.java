package com.hr.dao;

import com.hr.entity.FinancialExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 财务支出记录数据访问接口
 */
@Repository
public interface FinancialExpenseRepository extends JpaRepository<FinancialExpense, Long> {
    /**
     * 根据支出类型查询财务支出记录
     *
     * @param type 支出类型
     * @return 财务支出记录列表
     */
    List<FinancialExpense> findByType(String type);

    /**
     * 根据创建人ID查询财务支出记录
     *
     * @param creatorId 创建人ID
     * @return 财务支出记录列表
     */
    List<FinancialExpense> findByCreatorId(Long creatorId);

    /**
     * 根据关联ID和关联类型查询财务支出记录
     *
     * @param relatedId   关联ID
     * @param relatedType 关联类型
     * @return 财务支出记录列表
     */
    List<FinancialExpense> findByRelatedIdAndRelatedType(Long relatedId, String relatedType);

    /**
     * 根据支出日期范围查询财务支出记录
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 财务支出记录列表
     */
    List<FinancialExpense> findByExpenseDateBetween(Date startDate, Date endDate);
}