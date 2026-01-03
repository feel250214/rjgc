package com.hr.dto;

import lombok.Data;

import java.util.Date;

/**
 * 绩效记录DTO
 */
@Data
public class PerformanceRecordDto {
    private Long id;
    private Long employeeId;
    private String performancePeriod;
    private Double score;
    private String grade;
    private String comment;
    private Long appraiserId;
    private Date createTime;
    private Date updateTime;
}