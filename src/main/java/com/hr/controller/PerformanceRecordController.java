package com.hr.controller;

import com.hr.converter.PerformanceRecordConverter;
import com.hr.dto.PerformanceRecordDto;
import com.hr.dto.ResponseDto;
import com.hr.entity.PerformanceRecord;
import com.hr.service.PerformanceRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 绩效记录控制器
 */
@RestController
@RequestMapping("/performance-records")
@Slf4j
public class PerformanceRecordController {

    @Autowired
    private PerformanceRecordService performanceRecordService;

    @Autowired
    private PerformanceRecordConverter performanceRecordConverter;

    /**
     * 获取所有绩效记录（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 绩效记录分页列表
     */
    @GetMapping
    public ResponseDto<Page<PerformanceRecordDto>> getAllPerformanceRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<PerformanceRecord> performanceRecords = performanceRecordService.getAllPerformanceRecords(page, size);

            // 转换为DTO列表
            List<PerformanceRecordDto> dtoList = performanceRecords.getContent().stream()
                    .map(performanceRecordConverter::toDto)
                    .collect(Collectors.toList());

            // 创建DTO分页对象
            Page<PerformanceRecordDto> dtoPage = new PageImpl<>(dtoList, performanceRecords.getPageable(), performanceRecords.getTotalElements());

            return ResponseDto.success(dtoPage);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get performance records: " + e.getMessage());
        }
    }

    /**
     * 根据员工ID获取绩效记录
     *
     * @param employeeId 员工ID
     * @return 绩效记录列表
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseDto<List<PerformanceRecordDto>> getPerformanceRecordsByEmployeeId(@PathVariable Long employeeId) {
        try {
            List<PerformanceRecord> performanceRecords = performanceRecordService.getPerformanceRecordsByEmployeeId(employeeId);

            // 转换为DTO列表
            List<PerformanceRecordDto> dtoList = performanceRecords.stream()
                    .map(performanceRecordConverter::toDto)
                    .collect(Collectors.toList());

            return ResponseDto.success(dtoList);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get performance records: " + e.getMessage());
        }
    }

    /**
     * 根据绩效周期获取绩效记录
     *
     * @param performancePeriod 绩效周期
     * @return 绩效记录列表
     */
    @GetMapping("/period/{performancePeriod}")
    public ResponseDto<List<PerformanceRecordDto>> getPerformanceRecordsByPeriod(@PathVariable String performancePeriod) {
        try {
            List<PerformanceRecord> performanceRecords = performanceRecordService.getPerformanceRecordsByPeriod(performancePeriod);

            // 转换为DTO列表
            List<PerformanceRecordDto> dtoList = performanceRecords.stream()
                    .map(performanceRecordConverter::toDto)
                    .collect(Collectors.toList());

            return ResponseDto.success(dtoList);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get performance records: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取绩效记录
     *
     * @param id 绩效记录ID
     * @return 绩效记录
     */
    @GetMapping("/{id}")
    public ResponseDto<PerformanceRecordDto> getPerformanceRecordById(@PathVariable Long id) {
        try {
            PerformanceRecord performanceRecord = performanceRecordService.getPerformanceRecordById(id);
            if (performanceRecord == null) {
                return ResponseDto.fail(404, "Performance record not found");
            }

            // 转换为DTO
            PerformanceRecordDto dto = performanceRecordConverter.toDto(performanceRecord);

            return ResponseDto.success(dto);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get performance record: " + e.getMessage());
        }
    }

    /**
     * 创建绩效记录
     *
     * @param performanceRecordDto 绩效记录信息
     * @return 创建后的绩效记录
     */
    @PostMapping
    public ResponseDto<PerformanceRecordDto> createPerformanceRecord(@RequestBody PerformanceRecordDto performanceRecordDto) {
        try {
            // 转换为实体类
            PerformanceRecord performanceRecord = performanceRecordConverter.toEntity(performanceRecordDto);

            PerformanceRecord createdPerformanceRecord = performanceRecordService.createPerformanceRecord(performanceRecord);

            // 转换为DTO返回
            PerformanceRecordDto dto = performanceRecordConverter.toDto(createdPerformanceRecord);

            return ResponseDto.success(dto);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create performance record: " + e.getMessage());
        }
    }

    /**
     * 更新绩效记录
     *
     * @param id                   绩效记录ID
     * @param performanceRecordDto 绩效记录信息
     * @return 更新后的绩效记录
     */
    @PutMapping("/{id}")
    public ResponseDto<PerformanceRecordDto> updatePerformanceRecord(@PathVariable Long id, @RequestBody PerformanceRecordDto performanceRecordDto) {
        try {
            // 检查绩效记录是否存在
            if (performanceRecordService.getPerformanceRecordById(id) == null) {
                return ResponseDto.fail(404, "Performance record not found");
            }

            // 转换为实体类并设置ID
            PerformanceRecord performanceRecord = performanceRecordConverter.toEntity(performanceRecordDto);
            performanceRecord.setId(id);

            PerformanceRecord updatedPerformanceRecord = performanceRecordService.updatePerformanceRecord(id, performanceRecord);

            // 转换为DTO返回
            PerformanceRecordDto dto = performanceRecordConverter.toDto(updatedPerformanceRecord);

            return ResponseDto.success(dto);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update performance record: " + e.getMessage());
        }
    }

    /**
     * 删除绩效记录
     *
     * @param id 绩效记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deletePerformanceRecord(@PathVariable Long id) {
        try {
            // 检查绩效记录是否存在
            if (performanceRecordService.getPerformanceRecordById(id) == null) {
                return ResponseDto.fail(404, "Performance record not found");
            }
            performanceRecordService.deletePerformanceRecord(id);
            return ResponseDto.success("Performance record deleted successfully");
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete performance record: " + e.getMessage());
        }
    }
}