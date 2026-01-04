package com.hr.controller;

import com.hr.converter.EmployeeConverter;
import com.hr.dto.EmployeeDto;
import com.hr.dto.ResponseDto;
import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Position;
import com.hr.service.DepartmentService;
import com.hr.service.EmployeeService;
import com.hr.service.PositionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工控制器
 */
@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private EmployeeConverter employeeConverter;

    /**
     * 获取所有员工（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 员工分页列表
     */
    @GetMapping
    public ResponseDto<org.springframework.data.domain.Page<EmployeeDto>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            org.springframework.data.domain.Page<Employee> employees = employeeService.getAllEmployees(page, size);
            // 转换为DTO页面，包含部门和职位名称
            List<EmployeeDto> employeeDtos = employeeService.getEmployeeDetails(employees.getContent());
            org.springframework.data.domain.Page<EmployeeDto> employeeDtoPage = new org.springframework.data.domain.PageImpl<>(employeeDtos, employees.getPageable(), employees.getTotalElements());
            return ResponseDto.success(employeeDtoPage);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get employees: " + e.getMessage());
        }
    }

    /**
     * 获取所有员工（不分页）
     *
     * @return 员工列表
     */
    @GetMapping("/all")
    public ResponseDto<List<EmployeeDto>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            List<EmployeeDto> employeeDtos = employeeService.getEmployeeDetails(employees);
            return ResponseDto.success(employeeDtos);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get employees: " + e.getMessage());
        }
    }

    /**
     * 根据部门ID获取员工
     *
     * @param departmentId 部门ID
     * @return 员工列表
     */
    @GetMapping("/department/{departmentId}")
    public ResponseDto<List<EmployeeDto>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        try {
            List<Employee> employees = employeeService.getEmployeesByDepartmentId(departmentId);
            List<EmployeeDto> employeeDtos = employeeService.getEmployeeDetails(employees);
            return ResponseDto.success(employeeDtos);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get employees by department: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取员工
     *
     * @param id 员工ID
     * @return 员工信息
     */
    @GetMapping("/{id}")
    public ResponseDto<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee == null) {
                return ResponseDto.fail(404, "Employee not found");
            }
            return ResponseDto.success(employeeService.getEmployeeDetail(employee));
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get employee: " + e.getMessage());
        }
    }

    /**
     * 创建员工
     *
     * @param employee 员工信息
     * @param result   绑定结果
     * @return 创建后的员工信息
     */
    @PostMapping
    public ResponseDto<EmployeeDto> createEmployee(@Valid @RequestBody Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseDto.fail(400, result.getFieldError().getDefaultMessage());
        }
        try {
            // 检查用户名是否已存在
            if (employeeService.getEmployeeByUsername(employee.getUsername()) != null) {
                return ResponseDto.fail(400, "Username already exists");
            }

            // 检查部门ID是否为空
            if (employee.getDepartmentId() == null) {
                return ResponseDto.fail(400, "Department ID cannot be null");
            }

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
     *
     * @param id          员工ID
     * @param employeeDto 员工信息
     * @return 更新后的员工信息
     */
    @PutMapping("/{id}")
    public ResponseDto<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        try {
            // 检查员工是否存在
            Employee existingEmployee = employeeService.getEmployeeById(id);
            if (existingEmployee == null) {
                return ResponseDto.fail(404, "Employee not found");
            }

            // 转换为实体并更新
            Employee employee = employeeConverter.toEntity(employeeDto);

            // 设置ID，确保更新的是指定员工
            employee.setId(id);

            // 如果DTO中没有提供密码，则使用原有密码
            if (employeeDto.getPassword() == null || employeeDto.getPassword().isEmpty()) {
                employee.setPassword(existingEmployee.getPassword());
            } else {
                employee.setPassword(employeeDto.getPassword());
            }

            // 保留原有部门和职位信息
            employee.setDepartmentId(existingEmployee.getDepartmentId());
            employee.setPositionId(existingEmployee.getPositionId());

            // 更新员工
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseDto.success(employeeService.getEmployeeDetail(updatedEmployee));
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update employee: " + e.getMessage());
        }
    }

    /**
     * 更新员工个人信息（非敏感信息）
     *
     * @param id          员工ID
     * @param employeeDto 员工信息（仅包含可修改字段）
     * @return 更新后的员工信息
     */
    @PutMapping("/profile/{id}")
    public ResponseDto<EmployeeDto> updateEmployeeProfile(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        try {
            // 检查员工是否存在
            Employee existingEmployee = employeeService.getEmployeeById(id);
            if (existingEmployee == null) {
                return ResponseDto.fail(404, "Employee not found");
            }

            // 仅更新非敏感信息
            if (employeeDto.getName() != null) {
                existingEmployee.setName(employeeDto.getName());
            }
            if (employeeDto.getEmail() != null) {
                existingEmployee.setEmail(employeeDto.getEmail());
            }
            if (employeeDto.getPhone() != null) {
                existingEmployee.setPhone(employeeDto.getPhone());
            }

            // 保存更新后的员工信息
            Employee updatedEmployee = employeeService.updateEmployee(id, existingEmployee);
            return ResponseDto.success(employeeService.getEmployeeDetail(updatedEmployee));
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update employee profile: " + e.getMessage());
        }
    }

    /**
     * 删除员工
     *
     * @param id 员工ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteEmployee(@PathVariable Long id) {
        try {
            // 检查员工是否存在
            if (employeeService.getEmployeeById(id) == null) {
                return ResponseDto.fail(404, "Employee not found");
            }

            // 删除员工
            employeeService.deleteEmployee(id);
            return ResponseDto.success("Employee deleted successfully");
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete employee: " + e.getMessage());
        }
    }
}
