package com.hr.dao;

import com.hr.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 考勤记录数据访问接口
 */
@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    /**
     * 根据员工ID查询考勤记录
     *
     * @param employeeId 员工ID
     * @return 考勤记录列表
     */
    List<AttendanceRecord> findByEmployeeId(Long employeeId);

    /**
     * 根据员工ID和考勤日期查询考勤记录
     *
     * @param employeeId 员工ID
     * @param recordDate 考勤日期
     * @return 考勤记录
     */
    AttendanceRecord findByEmployeeIdAndRecordDate(Long employeeId, Date recordDate);

    /**
     * 根据考勤日期范围查询考勤记录
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 考勤记录列表
     */
    List<AttendanceRecord> findByRecordDateBetween(Date startDate, Date endDate);

    /**
     * 根据员工ID和考勤日期范围查询考勤记录
     *
     * @param employeeId 员工ID
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @return 考勤记录列表
     */
    List<AttendanceRecord> findByEmployeeIdAndRecordDateBetween(Long employeeId, Date startDate, Date endDate);
}