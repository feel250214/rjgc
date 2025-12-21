package com.hr.controller;

import com.hr.converter.EmployeeConverter;
import com.hr.dto.EmployeeDto;
import com.hr.dto.ResponseDto;
import com.hr.entity.Employee;
import com.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeConverter employeeConverter;

    /**
     * 获取所有员工（分页）
     * @param page 页码
     * @param size 每页大小
     * @return 员工分页列表
     */
    @GetMapping
    public ResponseDto<Page<EmployeeDto>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Employee> employees = employeeService.getAllEmployees(pageable);
            // 转换为DTO页面
            Page<EmployeeDto> employeeDtos = employees.map(employeeConverter::toDto);
            return ResponseDto.success(employeeDtos);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get employees: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取员工
     * @param id 员工ID
     * @return 员工信息
     */
    @GetMapping("/{id}")
    public ResponseDto<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
            return ResponseDto.success(employeeConverter.toDto(employee));
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get employee: " + e.getMessage());
        }
    }

    /**
     * 创建员工
     * @param employeeDto 员工信息
     * @return 创建后的员工信息
     */
    @PostMapping
    public ResponseDto<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            // 检查用户名是否已存在
            if (employeeService.getEmployeeByUsername(employeeDto.getUsername()) != null) {
                return ResponseDto.fail(400, "Username already exists");
            }
            // 转换为实体并保存
            Employee employee = employeeConverter.toEntity(employeeDto);
            // 设置默认密码（测试用）
            if (employee.getPassword() == null || employee.getPassword().isEmpty()) {
                employee.setPassword("123456");
            }
            Employee savedEmployee = employeeService.createEmployee(employee);
            return ResponseDto.success(employeeConverter.toDto(savedEmployee));
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create employee: " + e.getMessage());
        }
    }

    /**
     * 更新员工
     * @param id 员工ID
     * @param employeeDto 更新后的员工信息
     * @return 更新后的员工信息
     */
    @PutMapping("/{id}")
    public ResponseDto<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        try {
            // 转换为实体并更新
            Employee employee = employeeConverter.toEntity(employeeDto);
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseDto.success(employeeConverter.toDto(updatedEmployee));
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update employee: " + e.getMessage());
        }
    }

    /**
     * 删除员工
     * @param id 员工ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseDto.success("Employee deleted successfully");
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete employee: " + e.getMessage());
        }
    }
}
