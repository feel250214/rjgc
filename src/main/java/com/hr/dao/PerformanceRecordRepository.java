package com.hr.dao;

import com.hr.entity.PerformanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 绩效记录数据访问接口
 */
@Repository
public interface PerformanceRecordRepository extends JpaRepository<PerformanceRecord, Long> {
    /**
     * 根据员工ID查询绩效记录
     *
     * @param employeeId 员工ID
     * @return 绩效记录列表
     */
    List<PerformanceRecord> findByEmployeeId(Long employeeId);

    /**
     * 根据绩效周期查询绩效记录
     *
     * @param performancePeriod 绩效周期
     * @return 绩效记录列表
     */
    List<PerformanceRecord> findByPerformancePeriod(String performancePeriod);

    /**
     * 根据员工ID和绩效周期查询绩效记录
     *
     * @param employeeId        员工ID
     * @param performancePeriod 绩效周期
     * @return 绩效记录
     */
    PerformanceRecord findByEmployeeIdAndPerformancePeriod(Long employeeId, String performancePeriod);
}