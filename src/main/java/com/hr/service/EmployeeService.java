package com.hr.service;

import com.hr.dao.EmployeeRepository;
import com.hr.dto.EmployeeDto;
import com.hr.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 获取所有员工（分页）
     * @param pageable 分页参数
     * @return 员工分页列表
     */
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    /**
     * 根据ID获取员工
     * @param id 员工ID
     * @return 员工对象
     */
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * 根据用户名获取员工
     * @param username 用户名
     * @return 员工对象
     */
    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    /**
     * 创建员工
     * @param employee 员工对象
     * @return 创建后的员工对象
     */
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * 更新员工
     * @param id 员工ID
     * @param employeeDetails 更新后的员工信息
     * @return 更新后的员工对象
     */
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setPosition(employeeDetails.getPosition());
        // 可以选择性更新密码
        if (employeeDetails.getPassword() != null && !employeeDetails.getPassword().isEmpty()) {
            employee.setPassword(employeeDetails.getPassword());
        }

        return employeeRepository.save(employee);
    }

    /**
     * 删除员工
     * @param id 员工ID
     */
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }
}
