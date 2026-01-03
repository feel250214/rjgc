package com.hr.dao;

import com.hr.entity.SalaryDispute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 薪资质疑数据访问接口
 */
@Repository
public interface SalaryDisputeRepository extends JpaRepository<SalaryDispute, Long> {
    /**
     * 根据员工ID查询薪资质疑
     *
     * @param employeeId 员工ID
     * @return 薪资质疑列表
     */
    List<SalaryDispute> findByEmployeeId(Long employeeId);

    /**
     * 根据薪资明细ID查询薪资质疑
     *
     * @param salaryDetailId 薪资明细ID
     * @return 薪资质疑列表
     */
    List<SalaryDispute> findBySalaryDetailId(Long salaryDetailId);

    /**
     * 根据状态查询薪资质疑
     *
     * @param status 状态
     * @return 薪资质疑列表
     */
    List<SalaryDispute> findByStatus(String status);
}