package com.hr.dao;

import com.hr.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * 根据用户名查询员工
     *
     * @param username 用户名
     * @return 员工对象
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

    /**
     * 根据部门ID查询员工列表（分页）
     *
     * @param departmentId 部门ID
     * @param pageable     分页参数
     * @return 员工分页列表
     */
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);
}
