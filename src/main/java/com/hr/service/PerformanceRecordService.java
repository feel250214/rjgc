package com.hr.service;

import com.hr.dao.PerformanceRecordRepository;
import com.hr.entity.PerformanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 绩效记录服务类
 */
@Service
public class PerformanceRecordService {

    @Autowired
    private PerformanceRecordRepository performanceRecordRepository;

    /**
     * 获取所有绩效记录（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 绩效记录分页列表
     */
    public Page<PerformanceRecord> getAllPerformanceRecords(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return performanceRecordRepository.findAll(pageable);
    }

    /**
     * 根据员工ID获取绩效记录
     *
     * @param employeeId 员工ID
     * @return 绩效记录列表
     */
    public List<PerformanceRecord> getPerformanceRecordsByEmployeeId(Long employeeId) {
        return performanceRecordRepository.findByEmployeeId(employeeId);
    }

    /**
     * 根据绩效周期获取绩效记录
     *
     * @param performancePeriod 绩效周期
     * @return 绩效记录列表
     */
    public List<PerformanceRecord> getPerformanceRecordsByPeriod(String performancePeriod) {
        return performanceRecordRepository.findByPerformancePeriod(performancePeriod);
    }

    /**
     * 根据员工ID和绩效周期获取绩效记录
     *
     * @param employeeId        员工ID
     * @param performancePeriod 绩效周期
     * @return 绩效记录
     */
    public PerformanceRecord getPerformanceRecordByEmployeeIdAndPeriod(Long employeeId, String performancePeriod) {
        return performanceRecordRepository.findByEmployeeIdAndPerformancePeriod(employeeId, performancePeriod);
    }

    /**
     * 根据ID获取绩效记录
     *
     * @param id 绩效记录ID
     * @return 绩效记录
     */
    public PerformanceRecord getPerformanceRecordById(Long id) {
        return performanceRecordRepository.findById(id).orElse(null);
    }

    /**
     * 创建绩效记录
     *
     * @param performanceRecord 绩效记录信息
     * @return 创建后的绩效记录
     */
    public PerformanceRecord createPerformanceRecord(PerformanceRecord performanceRecord) {
        return performanceRecordRepository.save(performanceRecord);
    }

    /**
     * 更新绩效记录
     *
     * @param id                绩效记录ID
     * @param performanceRecord 绩效记录信息
     * @return 更新后的绩效记录
     */
    public PerformanceRecord updatePerformanceRecord(Long id, PerformanceRecord performanceRecord) {
        performanceRecord.setId(id);
        return performanceRecordRepository.save(performanceRecord);
    }

    /**
     * 删除绩效记录
     *
     * @param id 绩效记录ID
     */
    public void deletePerformanceRecord(Long id) {
        performanceRecordRepository.deleteById(id);
    }
}