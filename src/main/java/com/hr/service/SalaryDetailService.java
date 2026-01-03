package com.hr.service;

import com.hr.dao.SalaryDetailRepository;
import com.hr.entity.SalaryDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 薪资明细服务类
 */
@Service
public class SalaryDetailService {

    @Autowired
    private SalaryDetailRepository salaryDetailRepository;

    /**
     * 获取所有薪资明细（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 薪资明细分页列表
     */
    public Page<SalaryDetail> getAllSalaryDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return salaryDetailRepository.findAll(pageable);
    }

    /**
     * 根据员工ID获取薪资明细
     *
     * @param employeeId 员工ID
     * @return 薪资明细列表
     */
    public List<SalaryDetail> getSalaryDetailsByEmployeeId(Long employeeId) {
        return salaryDetailRepository.findByEmployeeId(employeeId);
    }

    /**
     * 根据薪资周期获取薪资明细
     *
     * @param salaryPeriod 薪资周期
     * @return 薪资明细列表
     */
    public List<SalaryDetail> getSalaryDetailsByPeriod(String salaryPeriod) {
        return salaryDetailRepository.findBySalaryPeriod(salaryPeriod);
    }

    /**
     * 根据员工ID和薪资周期获取薪资明细
     *
     * @param employeeId   员工ID
     * @param salaryPeriod 薪资周期
     * @return 薪资明细
     */
    public SalaryDetail getSalaryDetailByEmployeeIdAndPeriod(Long employeeId, String salaryPeriod) {
        return salaryDetailRepository.findByEmployeeIdAndSalaryPeriod(employeeId, salaryPeriod);
    }

    /**
     * 根据ID获取薪资明细
     *
     * @param id 薪资明细ID
     * @return 薪资明细
     */
    public SalaryDetail getSalaryDetailById(Long id) {
        return salaryDetailRepository.findById(id).orElse(null);
    }

    /**
     * 创建薪资明细
     *
     * @param salaryDetail 薪资明细信息
     * @return 创建后的薪资明细
     */
    public SalaryDetail createSalaryDetail(SalaryDetail salaryDetail) {
        // 设置初始状态为待审核
        salaryDetail.setStatus("待审核");
        return salaryDetailRepository.save(salaryDetail);
    }

    /**
     * 更新薪资明细
     *
     * @param id           薪资明细ID
     * @param salaryDetail 薪资明细信息
     * @return 更新后的薪资明细
     */
    public SalaryDetail updateSalaryDetail(Long id, SalaryDetail salaryDetail) {
        salaryDetail.setId(id);
        return salaryDetailRepository.save(salaryDetail);
    }

    /**
     * 审核薪资明细
     *
     * @param id        薪资明细ID
     * @param result    审核结果（通过/驳回）
     * @param auditorId 审核人ID
     * @return 更新后的薪资明细
     */
    public SalaryDetail auditSalaryDetail(Long id, String result, Long auditorId) {
        SalaryDetail salaryDetail = salaryDetailRepository.findById(id).orElse(null);
        if (salaryDetail == null) {
            return null;
        }

        if ("通过".equals(result)) {
            // TODO: 这里可以根据需要实现多级审批逻辑
            // 目前简化处理，直接设置为已审核
            salaryDetail.setStatus("已审核");
        } else {
            salaryDetail.setStatus("已驳回");
        }
        salaryDetail.setAuditorId(auditorId);
        salaryDetail.setAuditTime(new Date());
        return salaryDetailRepository.save(salaryDetail);
    }

    /**
     * 发放薪资
     *
     * @param id 薪资明细ID
     * @return 更新后的薪资明细
     */
    public SalaryDetail issueSalary(Long id) {
        SalaryDetail salaryDetail = salaryDetailRepository.findById(id).orElse(null);
        if (salaryDetail == null) {
            return null;
        }

        // 更新薪资状态为已发放
        salaryDetail.setStatus("已发放");
        salaryDetail.setPaymentTime(new Date());
        return salaryDetailRepository.save(salaryDetail);
    }

    /**
     * 删除薪资明细
     *
     * @param id 薪资明细ID
     */
    public void deleteSalaryDetail(Long id) {
        salaryDetailRepository.deleteById(id);
    }
}