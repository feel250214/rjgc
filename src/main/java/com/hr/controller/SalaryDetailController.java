package com.hr.controller;

import com.hr.dto.ResponseDto;
import com.hr.entity.SalaryDetail;
import com.hr.service.SalaryDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 薪资明细控制器
 */
@RestController
@RequestMapping("/salary-details")
@Slf4j
public class SalaryDetailController {

    @Autowired
    private SalaryDetailService salaryDetailService;

    /**
     * 获取所有薪资明细（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 薪资明细分页列表
     */
    @GetMapping
    public ResponseDto<Page<SalaryDetail>> getAllSalaryDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<SalaryDetail> salaryDetails = salaryDetailService.getAllSalaryDetails(page, size);
            return ResponseDto.success(salaryDetails);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get salary details: " + e.getMessage());
        }
    }

    /**
     * 根据员工ID获取薪资明细
     *
     * @param employeeId 员工ID
     * @return 薪资明细列表
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseDto<List<SalaryDetail>> getSalaryDetailsByEmployeeId(@PathVariable Long employeeId) {
        try {
            List<SalaryDetail> salaryDetails = salaryDetailService.getSalaryDetailsByEmployeeId(employeeId);
            return ResponseDto.success(salaryDetails);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get salary details: " + e.getMessage());
        }
    }

    /**
     * 根据薪资周期获取薪资明细
     *
     * @param salaryPeriod 薪资周期
     * @return 薪资明细列表
     */
    @GetMapping("/period/{salaryPeriod}")
    public ResponseDto<List<SalaryDetail>> getSalaryDetailsByPeriod(@PathVariable String salaryPeriod) {
        try {
            List<SalaryDetail> salaryDetails = salaryDetailService.getSalaryDetailsByPeriod(salaryPeriod);
            return ResponseDto.success(salaryDetails);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get salary details: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取薪资明细
     *
     * @param id 薪资明细ID
     * @return 薪资明细
     */
    @GetMapping("/{id}")
    public ResponseDto<SalaryDetail> getSalaryDetailById(@PathVariable Long id) {
        try {
            SalaryDetail salaryDetail = salaryDetailService.getSalaryDetailById(id);
            if (salaryDetail == null) {
                return ResponseDto.fail(404, "Salary detail not found");
            }
            return ResponseDto.success(salaryDetail);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get salary detail: " + e.getMessage());
        }
    }

    /**
     * 创建薪资明细
     *
     * @param salaryDetail 薪资明细信息
     * @return 创建后的薪资明细
     */
    @PostMapping
    public ResponseDto<SalaryDetail> createSalaryDetail(@RequestBody SalaryDetail salaryDetail) {
        try {
            SalaryDetail createdSalaryDetail = salaryDetailService.createSalaryDetail(salaryDetail);
            return ResponseDto.success(createdSalaryDetail);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create salary detail: " + e.getMessage());
        }
    }

    /**
     * 更新薪资明细
     *
     * @param id           薪资明细ID
     * @param salaryDetail 薪资明细信息
     * @return 更新后的薪资明细
     */
    @PutMapping("/{id}")
    public ResponseDto<SalaryDetail> updateSalaryDetail(@PathVariable Long id, @RequestBody SalaryDetail salaryDetail) {
        try {
            // 检查薪资明细是否存在
            if (salaryDetailService.getSalaryDetailById(id) == null) {
                return ResponseDto.fail(404, "Salary detail not found");
            }
            SalaryDetail updatedSalaryDetail = salaryDetailService.updateSalaryDetail(id, salaryDetail);
            return ResponseDto.success(updatedSalaryDetail);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update salary detail: " + e.getMessage());
        }
    }

    /**
     * 审核薪资明细
     *
     * @param id        薪资明细ID
     * @param result    审核结果（通过/驳回）
     * @param auditorId 审核人ID
     * @return 更新后的薪资明细
     */
    @PostMapping("/{id}/audit")
    public ResponseDto<SalaryDetail> auditSalaryDetail(
            @PathVariable Long id,
            @RequestParam String result,
            @RequestParam Long auditorId) {
        try {
            SalaryDetail updatedSalaryDetail = salaryDetailService.auditSalaryDetail(id, result, auditorId);
            if (updatedSalaryDetail == null) {
                return ResponseDto.fail(404, "Salary detail not found");
            }
            return ResponseDto.success(updatedSalaryDetail);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to audit salary detail: " + e.getMessage());
        }
    }

    /**
     * 发放薪资
     *
     * @param id 薪资明细ID
     * @return 更新后的薪资明细
     */
    @PostMapping("/{id}/issue")
    public ResponseDto<SalaryDetail> issueSalary(@PathVariable Long id) {
        try {
            SalaryDetail updatedSalaryDetail = salaryDetailService.issueSalary(id);
            if (updatedSalaryDetail == null) {
                return ResponseDto.fail(404, "Salary detail not found");
            }
            return ResponseDto.success(updatedSalaryDetail);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to issue salary: " + e.getMessage());
        }
    }

    /**
     * 删除薪资明细
     *
     * @param id 薪资明细ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteSalaryDetail(@PathVariable Long id) {
        try {
            // 检查薪资明细是否存在
            if (salaryDetailService.getSalaryDetailById(id) == null) {
                return ResponseDto.fail(404, "Salary detail not found");
            }
            salaryDetailService.deleteSalaryDetail(id);
            return ResponseDto.success("Salary detail deleted successfully");
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete salary detail: " + e.getMessage());
        }
    }
}