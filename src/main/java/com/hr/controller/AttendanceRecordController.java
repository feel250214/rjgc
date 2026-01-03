package com.hr.controller;

import com.hr.dto.ResponseDto;
import com.hr.entity.AttendanceRecord;
import com.hr.service.AttendanceRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤记录控制器
 */
@RestController
@RequestMapping("/attendance-records")
@Slf4j
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordService attendanceRecordService;

    /**
     * 获取所有考勤记录（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 考勤记录分页列表
     */
    @GetMapping
    public ResponseDto<Page<AttendanceRecord>> getAllAttendanceRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<AttendanceRecord> attendanceRecords = attendanceRecordService.getAllAttendanceRecords(page, size);
            return ResponseDto.success(attendanceRecords);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get attendance records: " + e.getMessage());
        }
    }

    /**
     * 根据员工ID获取考勤记录
     *
     * @param employeeId 员工ID
     * @return 考勤记录列表
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseDto<List<AttendanceRecord>> getAttendanceRecordsByEmployeeId(@PathVariable Long employeeId) {
        try {
            List<AttendanceRecord> attendanceRecords = attendanceRecordService.getAttendanceRecordsByEmployeeId(employeeId);
            return ResponseDto.success(attendanceRecords);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get attendance records: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取考勤记录
     *
     * @param id 考勤记录ID
     * @return 考勤记录
     */
    @GetMapping("/{id}")
    public ResponseDto<AttendanceRecord> getAttendanceRecordById(@PathVariable Long id) {
        try {
            AttendanceRecord attendanceRecord = attendanceRecordService.getAttendanceRecordById(id);
            if (attendanceRecord == null) {
                return ResponseDto.fail(404, "Attendance record not found");
            }
            return ResponseDto.success(attendanceRecord);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get attendance record: " + e.getMessage());
        }
    }

    /**
     * 创建考勤记录
     *
     * @param attendanceRecord 考勤记录信息
     * @return 创建后的考勤记录
     */
    @PostMapping
    public ResponseDto<AttendanceRecord> createAttendanceRecord(@RequestBody AttendanceRecord attendanceRecord) {
        try {
            AttendanceRecord createdAttendanceRecord = attendanceRecordService.createAttendanceRecord(attendanceRecord);
            return ResponseDto.success(createdAttendanceRecord);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create attendance record: " + e.getMessage());
        }
    }

    /**
     * 更新考勤记录
     *
     * @param id               考勤记录ID
     * @param attendanceRecord 考勤记录信息
     * @return 更新后的考勤记录
     */
    @PutMapping("/{id}")
    public ResponseDto<AttendanceRecord> updateAttendanceRecord(@PathVariable Long id, @RequestBody AttendanceRecord attendanceRecord) {
        try {
            // 检查考勤记录是否存在
            if (attendanceRecordService.getAttendanceRecordById(id) == null) {
                return ResponseDto.fail(404, "Attendance record not found");
            }
            AttendanceRecord updatedAttendanceRecord = attendanceRecordService.updateAttendanceRecord(id, attendanceRecord);
            return ResponseDto.success(updatedAttendanceRecord);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update attendance record: " + e.getMessage());
        }
    }

    /**
     * 删除考勤记录
     *
     * @param id 考勤记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteAttendanceRecord(@PathVariable Long id) {
        try {
            // 检查考勤记录是否存在
            if (attendanceRecordService.getAttendanceRecordById(id) == null) {
                return ResponseDto.fail(404, "Attendance record not found");
            }
            attendanceRecordService.deleteAttendanceRecord(id);
            return ResponseDto.success("Attendance record deleted successfully");
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete attendance record: " + e.getMessage());
        }
    }
}