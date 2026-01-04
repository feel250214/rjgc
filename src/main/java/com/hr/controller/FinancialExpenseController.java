package com.hr.controller;

import com.hr.dto.ResponseDto;
import com.hr.entity.FinancialExpense;
import com.hr.service.FinancialExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 财务支出记录控制器
 */
@RestController
@RequestMapping("/financial-expenses")
@Slf4j
public class FinancialExpenseController {

    @Autowired
    private FinancialExpenseService financialExpenseService;

    /**
     * 获取所有财务支出记录（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 财务支出记录分页列表
     */
    @GetMapping
    public ResponseDto<Page<FinancialExpense>> getAllFinancialExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<FinancialExpense> financialExpenses = financialExpenseService.getAllFinancialExpenses(page, size);
            return ResponseDto.success(financialExpenses);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get financial expenses: " + e.getMessage());
        }
    }

    /**
     * 根据支出类型获取财务支出记录
     *
     * @param type 支出类型
     * @return 财务支出记录列表
     */
    @GetMapping("/type/{type}")
    public ResponseDto<List<FinancialExpense>> getFinancialExpensesByType(@PathVariable String type) {
        try {
            List<FinancialExpense> financialExpenses = financialExpenseService.getFinancialExpensesByType(type);
            return ResponseDto.success(financialExpenses);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get financial expenses: " + e.getMessage());
        }
    }

    /**
     * 根据创建人ID获取财务支出记录
     *
     * @param creatorId 创建人ID
     * @return 财务支出记录列表
     */
    @GetMapping("/creator/{creatorId}")
    public ResponseDto<List<FinancialExpense>> getFinancialExpensesByCreatorId(@PathVariable Long creatorId) {
        try {
            List<FinancialExpense> financialExpenses = financialExpenseService.getFinancialExpensesByCreatorId(creatorId);
            return ResponseDto.success(financialExpenses);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get financial expenses: " + e.getMessage());
        }
    }

    /**
     * 根据日期范围获取财务支出记录
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 财务支出记录列表
     */
    @GetMapping("/date-range")
    public ResponseDto<List<FinancialExpense>> getFinancialExpensesByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<FinancialExpense> financialExpenses = financialExpenseService.getFinancialExpensesByDateRange(startDate, endDate);
            return ResponseDto.success(financialExpenses);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get financial expenses: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取财务支出记录
     *
     * @param id 财务支出记录ID
     * @return 财务支出记录信息
     */
    @GetMapping("/{id}")
    public ResponseDto<FinancialExpense> getFinancialExpenseById(@PathVariable Long id) {
        try {
            FinancialExpense financialExpense = financialExpenseService.getFinancialExpenseById(id);
            if (financialExpense == null) {
                return ResponseDto.fail(404, "Financial expense not found");
            }
            return ResponseDto.success(financialExpense);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get financial expense: " + e.getMessage());
        }
    }

    /**
     * 创建财务支出记录
     *
     * @param financialExpense 财务支出记录信息
     * @return 创建后的财务支出记录
     */
    @PostMapping
    public ResponseDto<FinancialExpense> createFinancialExpense(@RequestBody FinancialExpense financialExpense) {
        try {
            FinancialExpense createdFinancialExpense = financialExpenseService.createFinancialExpense(financialExpense);
            return ResponseDto.success(createdFinancialExpense);
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create financial expense: " + e.getMessage());
        }
    }

    /**
     * 更新财务支出记录
     *
     * @param id               财务支出记录ID
     * @param financialExpense 财务支出记录信息
     * @return 更新后的财务支出记录
     */
    @PutMapping("/{id}")
    public ResponseDto<FinancialExpense> updateFinancialExpense(@PathVariable Long id, @RequestBody FinancialExpense financialExpense) {
        try {
            FinancialExpense updatedFinancialExpense = financialExpenseService.updateFinancialExpense(id, financialExpense);
            return ResponseDto.success(updatedFinancialExpense);
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update financial expense: " + e.getMessage());
        }
    }

    /**
     * 删除财务支出记录
     *
     * @param id 财务支出记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteFinancialExpense(@PathVariable Long id) {
        try {
            financialExpenseService.deleteFinancialExpense(id);
            return ResponseDto.success("Financial expense deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete financial expense: " + e.getMessage());
        }
    }
}
