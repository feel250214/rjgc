package com.hr.service;

import com.hr.dao.DepartmentRepository;
import com.hr.dao.EmployeeRepository;
import com.hr.entity.Department;
import com.hr.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门服务实现类
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 获取所有部门（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 部门分页列表
     */
    public Page<Department> getAllDepartments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return departmentRepository.findAll(pageable);
    }

    /**
     * 获取所有部门（不分页）
     *
     * @return 部门列表
     */
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * 根据ID获取部门
     *
     * @param id 部门ID
     * @return 部门信息
     */
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    /**
     * 根据上级部门ID获取子部门列表
     *
     * @param parentId 上级部门ID
     * @return 子部门列表
     */
    public List<Department> getChildDepartments(Long parentId) {
        return departmentRepository.findByParentId(parentId);
    }

    /**
     * 创建部门
     *
     * @param department 部门信息
     * @return 创建后的部门信息
     */
    public Department createDepartment(Department department) {
        // 检查部门名称是否已存在
        if (departmentRepository.findByName(department.getName()) != null) {
            throw new IllegalArgumentException("Department name already exists");
        }
        // 检查部门编号是否已存在
        if (departmentRepository.findByCode(department.getCode()) != null) {
            throw new IllegalArgumentException("Department code already exists");
        }
        return departmentRepository.save(department);
    }

    /**
     * 更新部门
     *
     * @param id         部门ID
     * @param department 部门信息
     * @return 更新后的部门信息
     */
    public Department updateDepartment(Long id, Department department) {
        // 检查部门是否存在
        Department existingDepartment = departmentRepository.findById(id).orElse(null);
        if (existingDepartment == null) {
            throw new IllegalArgumentException("Department not found");
        }
        // 检查部门名称是否已存在（排除自身）
        Department departmentWithSameName = departmentRepository.findByName(department.getName());
        if (departmentWithSameName != null && !departmentWithSameName.getId().equals(id)) {
            throw new IllegalArgumentException("Department name already exists");
        }
        // 检查部门编号是否已存在（排除自身）
        Department departmentWithSameCode = departmentRepository.findByCode(department.getCode());
        if (departmentWithSameCode != null && !departmentWithSameCode.getId().equals(id)) {
            throw new IllegalArgumentException("Department code already exists");
        }
        // 更新部门信息
        existingDepartment.setName(department.getName());
        existingDepartment.setCode(department.getCode());
        existingDepartment.setParentId(department.getParentId());
        existingDepartment.setManagerId(department.getManagerId());
        existingDepartment.setDescription(department.getDescription());
        return departmentRepository.save(existingDepartment);
    }

    /**
     * 删除部门
     *
     * @param id 部门ID
     */
    public void deleteDepartment(Long id) {
        // 检查部门是否存在
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            throw new IllegalArgumentException("Department not found");
        }
        // 检查部门下是否有子部门
        List<Department> childDepartments = departmentRepository.findByParentId(id);
        if (!childDepartments.isEmpty()) {
            throw new IllegalArgumentException("Cannot delete department with child departments");
        }
        // 检查部门下是否有员工
        List<Employee> employees = employeeRepository.findByDepartmentId(id);
        if (!employees.isEmpty()) {
            throw new IllegalArgumentException("Cannot delete department with employees");
        }
        // 删除部门
        departmentRepository.deleteById(id);
    }

    /**
     * 转移部门员工
     *
     * @param fromDepartmentId 源部门ID
     * @param toDepartmentId   目标部门ID
     * @return 转移的员工数量
     */
    public int transferEmployees(Long fromDepartmentId, Long toDepartmentId) {
        // 检查源部门和目标部门是否存在
        Department fromDepartment = departmentRepository.findById(fromDepartmentId).orElse(null);
        Department toDepartment = departmentRepository.findById(toDepartmentId).orElse(null);
        if (fromDepartment == null || toDepartment == null) {
            throw new IllegalArgumentException("Department not found");
        }
        // 获取源部门下的所有员工
        List<Employee> employees = employeeRepository.findByDepartmentId(fromDepartmentId);
        // 转移员工
        int count = 0;
        for (Employee employee : employees) {
            employee.setDepartmentId(toDepartmentId);
            employeeRepository.save(employee);
            count++;
        }
        return count;
    }
}