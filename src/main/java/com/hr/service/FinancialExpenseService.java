package com.hr.service;

import com.hr.dao.FinancialExpenseRepository;
import com.hr.entity.FinancialExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 财务支出记录服务类
 */
@Service
public class FinancialExpenseService {

    @Autowired
    private FinancialExpenseRepository financialExpenseRepository;

    /**
     * 获取所有财务支出记录（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 财务支出记录分页列表
     */
    public Page<FinancialExpense> getAllFinancialExpenses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return financialExpenseRepository.findAll(pageable);
    }

    /**
     * 根据支出类型获取财务支出记录
     *
     * @param type 支出类型
     * @return 财务支出记录列表
     */
    public List<FinancialExpense> getFinancialExpensesByType(String type) {
        return financialExpenseRepository.findByType(type);
    }

    /**
     * 根据创建人ID获取财务支出记录
     *
     * @param creatorId 创建人ID
     * @return 财务支出记录列表
     */
    public List<FinancialExpense> getFinancialExpensesByCreatorId(Long creatorId) {
        return financialExpenseRepository.findByCreatorId(creatorId);
    }

    /**
     * 根据日期范围获取财务支出记录
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 财务支出记录列表
     */
    public List<FinancialExpense> getFinancialExpensesByDateRange(Date startDate, Date endDate) {
        return financialExpenseRepository.findByExpenseDateBetween(startDate, endDate);
    }

    /**
     * 根据关联ID和关联类型获取财务支出记录
     *
     * @param relatedId   关联ID
     * @param relatedType 关联类型
     * @return 财务支出记录列表
     */
    public List<FinancialExpense> getFinancialExpensesByRelatedIdAndType(Long relatedId, String relatedType) {
        return financialExpenseRepository.findByRelatedIdAndRelatedType(relatedId, relatedType);
    }

    /**
     * 根据ID获取财务支出记录
     *
     * @param id 财务支出记录ID
     * @return 财务支出记录信息
     */
    public FinancialExpense getFinancialExpenseById(Long id) {
        return financialExpenseRepository.findById(id).orElse(null);
    }

    /**
     * 创建财务支出记录
     *
     * @param financialExpense 财务支出记录信息
     * @return 创建后的财务支出记录
     */
    public FinancialExpense createFinancialExpense(FinancialExpense financialExpense) {
        return financialExpenseRepository.save(financialExpense);
    }

    /**
     * 更新财务支出记录
     *
     * @param id               财务支出记录ID
     * @param financialExpense 财务支出记录信息
     * @return 更新后的财务支出记录
     */
    public FinancialExpense updateFinancialExpense(Long id, FinancialExpense financialExpense) {
        // 检查财务支出记录是否存在
        FinancialExpense existingExpense = financialExpenseRepository.findById(id).orElse(null);
        if (existingExpense == null) {
            throw new IllegalArgumentException("Financial expense not found");
        }

        financialExpense.setId(id);
        return financialExpenseRepository.save(financialExpense);
    }

    /**
     * 删除财务支出记录
     *
     * @param id 财务支出记录ID
     */
    public void deleteFinancialExpense(Long id) {
        // 检查财务支出记录是否存在
        FinancialExpense existingExpense = financialExpenseRepository.findById(id).orElse(null);
        if (existingExpense == null) {
            throw new IllegalArgumentException("Financial expense not found");
        }

        financialExpenseRepository.deleteById(id);
    }
}