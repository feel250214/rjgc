package com.hr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr.entity.Employee;

import java.util.List;

/**
 * 员工Mapper接口
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    /**
     * 根据用户名查询员工
     *
     * @param username 用户名
     * @return 员工信息
     */
    Employee findByUsername(String username);

    /**
     * 根据部门ID查询员工列表
     *
     * @param departmentId 部门ID
     * @return 员工列表
     */
    List<Employee> findByDepartmentId(Long departmentId);

    /**
     * 根据职位ID查询员工列表
     *
     * @param positionId 职位ID
     * @return 员工列表
     */
    List<Employee> findByPositionId(Long positionId);
}
