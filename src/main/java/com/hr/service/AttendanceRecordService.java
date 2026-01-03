package com.hr.service;

import com.hr.dao.AttendanceRecordRepository;
import com.hr.entity.AttendanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 考勤记录服务类
 */
@Service
public class AttendanceRecordService {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    /**
     * 获取所有考勤记录（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 考勤记录分页列表
     */
    public Page<AttendanceRecord> getAllAttendanceRecords(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return attendanceRecordRepository.findAll(pageable);
    }

    /**
     * 根据员工ID获取考勤记录
     *
     * @param employeeId 员工ID
     * @return 考勤记录列表
     */
    public List<AttendanceRecord> getAttendanceRecordsByEmployeeId(Long employeeId) {
        return attendanceRecordRepository.findByEmployeeId(employeeId);
    }

    /**
     * 根据员工ID和日期范围获取考勤记录
     *
     * @param employeeId 员工ID
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @return 考勤记录列表
     */
    public List<AttendanceRecord> getAttendanceRecordsByEmployeeIdAndDateRange(
            Long employeeId, Date startDate, Date endDate) {
        return attendanceRecordRepository.findByEmployeeIdAndRecordDateBetween(employeeId, startDate, endDate);
    }

    /**
     * 根据ID获取考勤记录
     *
     * @param id 考勤记录ID
     * @return 考勤记录
     */
    public AttendanceRecord getAttendanceRecordById(Long id) {
        return attendanceRecordRepository.findById(id).orElse(null);
    }

    /**
     * 创建考勤记录
     *
     * @param attendanceRecord 考勤记录信息
     * @return 创建后的考勤记录
     */
    public AttendanceRecord createAttendanceRecord(AttendanceRecord attendanceRecord) {
        return attendanceRecordRepository.save(attendanceRecord);
    }

    /**
     * 更新考勤记录
     *
     * @param id               考勤记录ID
     * @param attendanceRecord 考勤记录信息
     * @return 更新后的考勤记录
     */
    public AttendanceRecord updateAttendanceRecord(Long id, AttendanceRecord attendanceRecord) {
        attendanceRecord.setId(id);
        return attendanceRecordRepository.save(attendanceRecord);
    }

    /**
     * 删除考勤记录
     *
     * @param id 考勤记录ID
     */
    public void deleteAttendanceRecord(Long id) {
        attendanceRecordRepository.deleteById(id);
    }
}