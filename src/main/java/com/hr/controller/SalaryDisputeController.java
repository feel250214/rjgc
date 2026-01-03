package com.hr.controller;

import com.hr.dto.ResponseDto;
import com.hr.entity.SalaryDispute;
import com.hr.service.SalaryDisputeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 薪资质疑控制器
 */
@RestController
@RequestMapping("/salary-disputes")
@Slf4j
public class SalaryDisputeController {

    @Autowired
    private SalaryDisputeService salaryDisputeService;

    /**
     * 获取所有薪资质疑（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 薪资质疑分页列表
     */
    @GetMapping
    public ResponseDto<Page<SalaryDispute>> getAllSalaryDisputes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<SalaryDispute> salaryDisputes = salaryDisputeService.getAllSalaryDisputes(page, size);
            return ResponseDto.success(salaryDisputes);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get salary disputes: " + e.getMessage());
        }
    }

    /**
     * 根据员工ID获取薪资质疑
     *
     * @param employeeId 员工ID
     * @return 薪资质疑列表
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseDto<List<SalaryDispute>> getSalaryDisputesByEmployeeId(@PathVariable Long employeeId) {
        try {
            List<SalaryDispute> salaryDisputes = salaryDisputeService.getSalaryDisputesByEmployeeId(employeeId);
            return ResponseDto.success(salaryDisputes);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get salary disputes: " + e.getMessage());
        }
    }

    /**
     * 根据薪资明细ID获取薪资质疑
     *
     * @param salaryDetailId 薪资明细ID
     * @return 薪资质疑列表
     */
    @GetMapping("/salary-detail/{salaryDetailId}")
    public ResponseDto<List<SalaryDispute>> getSalaryDisputesBySalaryDetailId(@PathVariable Long salaryDetailId) {
        try {
            List<SalaryDispute> salaryDisputes = salaryDisputeService.getSalaryDisputesBySalaryDetailId(salaryDetailId);
            return ResponseDto.success(salaryDisputes);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get salary disputes: " + e.getMessage());
        }
    }

    /**
     * 根据状态获取薪资质疑
     *
     * @param status 状态
     * @return 薪资质疑列表
     */
    @GetMapping("/status/{status}")
    public ResponseDto<List<SalaryDispute>> getSalaryDisputesByStatus(@PathVariable String status) {
        try {
            List<SalaryDispute> salaryDisputes = salaryDisputeService.getSalaryDisputesByStatus(status);
            return ResponseDto.success(salaryDisputes);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get salary disputes: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取薪资质疑
     *
     * @param id 薪资质疑ID
     * @return 薪资质疑
     */
    @GetMapping("/{id}")
    public ResponseDto<SalaryDispute> getSalaryDisputeById(@PathVariable Long id) {
        try {
            SalaryDispute salaryDispute = salaryDisputeService.getSalaryDisputeById(id);
            if (salaryDispute == null) {
                return ResponseDto.fail(404, "Salary dispute not found");
            }
            return ResponseDto.success(salaryDispute);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get salary dispute: " + e.getMessage());
        }
    }

    /**
     * 员工提交薪资质疑
     *
     * @param salaryDispute 薪资质疑信息
     * @return 创建后的薪资质疑
     */
    @PostMapping
    public ResponseDto<SalaryDispute> createSalaryDispute(@RequestBody SalaryDispute salaryDispute) {
        try {
            SalaryDispute createdSalaryDispute = salaryDisputeService.createSalaryDispute(salaryDispute);
            return ResponseDto.success(createdSalaryDispute);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create salary dispute: " + e.getMessage());
        }
    }

    /**
     * HR解答薪资质疑
     *
     * @param id         薪资质疑ID
     * @param hrResponse HR解答内容
     * @return 更新后的薪资质疑
     */
    @PostMapping("/{id}/respond")
    public ResponseDto<SalaryDispute> respondToSalaryDispute(
            @PathVariable Long id,
            @RequestParam String hrResponse) {
        try {
            SalaryDispute updatedSalaryDispute = salaryDisputeService.respondToSalaryDispute(id, hrResponse);
            if (updatedSalaryDispute == null) {
                return ResponseDto.fail(404, "Salary dispute not found");
            }
            return ResponseDto.success(updatedSalaryDispute);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to respond to salary dispute: " + e.getMessage());
        }
    }

    /**
     * 删除薪资质疑
     *
     * @param id 薪资质疑ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteSalaryDispute(@PathVariable Long id) {
        try {
            // 检查薪资质疑是否存在
            if (salaryDisputeService.getSalaryDisputeById(id) == null) {
                return ResponseDto.fail(404, "Salary dispute not found");
            }
            salaryDisputeService.deleteSalaryDispute(id);
            return ResponseDto.success("Salary dispute deleted successfully");
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete salary dispute: " + e.getMessage());
        }
    }
}