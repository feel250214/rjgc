package com.hr.dao;

import com.hr.entity.SalaryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 薪资明细数据访问接口
 */
@Repository
public interface SalaryDetailRepository extends JpaRepository<SalaryDetail, Long> {
    /**
     * 根据员工ID查询薪资明细
     *
     * @param employeeId 员工ID
     * @return 薪资明细列表
     */
    List<SalaryDetail> findByEmployeeId(Long employeeId);

    /**
     * 根据薪资周期查询薪资明细
     *
     * @param salaryPeriod 薪资周期
     * @return 薪资明细列表
     */
    List<SalaryDetail> findBySalaryPeriod(String salaryPeriod);

    /**
     * 根据员工ID和薪资周期查询薪资明细
     *
     * @param employeeId   员工ID
     * @param salaryPeriod 薪资周期
     * @return 薪资明细
     */
    SalaryDetail findByEmployeeIdAndSalaryPeriod(Long employeeId, String salaryPeriod);

    /**
     * 根据状态查询薪资明细
     *
     * @param status 状态
     * @return 薪资明细列表
     */
    List<SalaryDetail> findByStatus(String status);
}