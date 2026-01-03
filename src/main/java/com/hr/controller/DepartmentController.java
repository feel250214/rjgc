package com.hr.controller;

import com.hr.dto.ResponseDto;
import com.hr.entity.Department;
import com.hr.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制器
 */
@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取所有部门（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 部门分页列表
     */
    @GetMapping
    public ResponseDto<Page<Department>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Department> departments = departmentService.getAllDepartments(page, size);
            return ResponseDto.success(departments);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get departments: " + e.getMessage());
        }
    }

    /**
     * 获取所有部门（不分页）
     *
     * @return 部门列表
     */
    @GetMapping("/all")
    public ResponseDto<List<Department>> getAllDepartments() {
        try {
            List<Department> departments = departmentService.getAllDepartments();
            return ResponseDto.success(departments);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get departments: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取部门
     *
     * @param id 部门ID
     * @return 部门信息
     */
    @GetMapping("/{id}")
    public ResponseDto<Department> getDepartmentById(@PathVariable Long id) {
        try {
            Department department = departmentService.getDepartmentById(id);
            if (department == null) {
                return ResponseDto.fail(404, "Department not found");
            }
            return ResponseDto.success(department);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get department: " + e.getMessage());
        }
    }

    /**
     * 根据上级部门ID获取子部门列表
     *
     * @param parentId 上级部门ID
     * @return 子部门列表
     */
    @GetMapping("/child/{parentId}")
    public ResponseDto<List<Department>> getChildDepartments(@PathVariable Long parentId) {
        try {
            List<Department> childDepartments = departmentService.getChildDepartments(parentId);
            return ResponseDto.success(childDepartments);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get child departments: " + e.getMessage());
        }
    }

    /**
     * 创建部门
     *
     * @param department 部门信息
     * @param result     绑定结果
     * @return 创建后的部门信息
     */
    @PostMapping
    public ResponseDto<Department> createDepartment(@Valid @RequestBody Department department, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseDto.fail(400, result.getFieldError().getDefaultMessage());
        }
        try {
            Department createdDepartment = departmentService.createDepartment(department);
            return ResponseDto.success(createdDepartment);
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create department: " + e.getMessage());
        }
    }

    /**
     * 更新部门
     *
     * @param id         部门ID
     * @param department 部门信息
     * @param result     绑定结果
     * @return 更新后的部门信息
     */
    @PutMapping("/{id}")
    public ResponseDto<Department> updateDepartment(@PathVariable Long id, @Valid @RequestBody Department department, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseDto.fail(400, result.getFieldError().getDefaultMessage());
        }
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, department);
            return ResponseDto.success(updatedDepartment);
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update department: " + e.getMessage());
        }
    }

    /**
     * 删除部门
     *
     * @param id 部门ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseDto.success("Department deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete department: " + e.getMessage());
        }
    }

    /**
     * 转移部门员工
     *
     * @param fromDepartmentId 源部门ID
     * @param toDepartmentId   目标部门ID
     * @return 转移结果
     */
    @PostMapping("/transfer-employees")
    public ResponseDto<?> transferEmployees(
            @RequestParam Long fromDepartmentId,
            @RequestParam Long toDepartmentId) {
        try {
            int count = departmentService.transferEmployees(fromDepartmentId, toDepartmentId);
            return ResponseDto.success("Successfully transferred " + count + " employees");
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to transfer employees: " + e.getMessage());
        }
    }
}