package com.hr.converter;

import com.hr.dto.PerformanceRecordDto;
import com.hr.entity.PerformanceRecord;
import org.springframework.stereotype.Component;

@Component
public class PerformanceRecordConverter {

    /**
     * 将PerformanceRecord实体转换为PerformanceRecordDto
     *
     * @param performanceRecord 绩效记录实体
     * @return 绩效记录DTO
     */
    public PerformanceRecordDto toDto(PerformanceRecord performanceRecord) {
        if (performanceRecord == null) {
            return null;
        }
        PerformanceRecordDto dto = new PerformanceRecordDto();
        dto.setId(performanceRecord.getId());
        dto.setEmployeeId(performanceRecord.getEmployeeId());
        dto.setPerformancePeriod(performanceRecord.getPerformancePeriod());
        dto.setScore(performanceRecord.getScore());
        dto.setGrade(performanceRecord.getGrade());
        dto.setComment(performanceRecord.getComment());
        dto.setAppraiserId(performanceRecord.getAppraiserId());
        dto.setCreateTime(performanceRecord.getCreateTime());
        dto.setUpdateTime(performanceRecord.getUpdateTime());
        return dto;
    }

    /**
     * 将PerformanceRecordDto转换为PerformanceRecord实体
     *
     * @param dto 绩效记录DTO
     * @return 绩效记录实体
     */
    public PerformanceRecord toEntity(PerformanceRecordDto dto) {
        if (dto == null) {
            return null;
        }
        PerformanceRecord performanceRecord = new PerformanceRecord();
        performanceRecord.setId(dto.getId());
        performanceRecord.setEmployeeId(dto.getEmployeeId());
        performanceRecord.setPerformancePeriod(dto.getPerformancePeriod());
        performanceRecord.setScore(dto.getScore());
        performanceRecord.setGrade(dto.getGrade());
        performanceRecord.setComment(dto.getComment());
        performanceRecord.setAppraiserId(dto.getAppraiserId());
        return performanceRecord;
    }
}