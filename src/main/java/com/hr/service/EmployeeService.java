package com.hr.service;

import com.hr.dao.DepartmentRepository;
import com.hr.dao.EmployeeRepository;
import com.hr.dao.PositionRepository;
import com.hr.dto.EmployeeDto;
import com.hr.entity.Department;
import com.hr.entity.Employee;
import com.hr.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工服务类
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    /**
     * 获取所有员工（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 员工分页列表
     */
    public Page<Employee> getAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

    /**
     * 获取所有员工
     *
     * @return 员工列表
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * 根据ID获取员工
     *
     * @param id 员工ID
     * @return 员工信息
     */
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    /**
     * 根据用户名获取员工
     *
     * @param username 用户名
     * @return 员工信息
     */
    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    /**
     * 根据部门ID获取员工列表
     *
     * @param departmentId 部门ID
     * @return 员工列表
     */
    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    /**
     * 创建员工
     *
     * @param employee 员工信息
     * @return 创建后的员工信息
     */
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * 更新员工
     *
     * @param id       员工ID
     * @param employee 员工信息
     * @return 更新后的员工信息
     */
    public Employee updateEmployee(Long id, Employee employee) {
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    /**
     * 删除员工
     *
     * @param id 员工ID
     */
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    /**
     * 获取员工详情（包含部门和职位名称）
     *
     * @param employee 员工实体
     * @return 员工DTO
     */
    public EmployeeDto getEmployeeDetail(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setUsername(employee.getUsername());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setCreateTime(employee.getCreateTime());

        // 获取部门名称
        if (employee.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employee.getDepartmentId()).orElse(null);
            if (department != null) {
                dto.setDepartment(department.getName());
            }
        }

        // 获取职位名称
        if (employee.getPositionId() != null) {
            Position position = positionRepository.findById(employee.getPositionId()).orElse(null);
            if (position != null) {
                dto.setPosition(position.getName());
            }
        }

        return dto;
    }

    /**
     * 批量获取员工详情
     *
     * @param employees 员工列表
     * @return 员工DTO列表
     */
    public List<EmployeeDto> getEmployeeDetails(List<Employee> employees) {
        List<EmployeeDto> dtos = new ArrayList<>();
        for (Employee employee : employees) {
            dtos.add(getEmployeeDetail(employee));
        }
        return dtos;
    }
}