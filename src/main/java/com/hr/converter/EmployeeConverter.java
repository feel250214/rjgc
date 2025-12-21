package com.hr.converter;

import com.hr.dto.EmployeeDto;
import com.hr.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {

    /**
     * 将Employee实体转换为EmployeeDto
     * @param employee 员工实体
     * @return 员工DTO
     */
    public EmployeeDto toDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setUsername(employee.getUsername());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setDepartment(employee.getDepartment());
        dto.setPosition(employee.getPosition());
        dto.setCreateTime(employee.getCreateTime());
        return dto;
    }

    /**
     * 将EmployeeDto转换为Employee实体
     * @param dto 员工DTO
     * @return 员工实体
     */
    public Employee toEntity(EmployeeDto dto) {
        if (dto == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setUsername(dto.getUsername());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setDepartment(dto.getDepartment());
        employee.setPosition(dto.getPosition());
        return employee;
    }
}
