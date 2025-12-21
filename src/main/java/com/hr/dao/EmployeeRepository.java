package com.hr.dao;

import com.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * 根据用户名查询员工
     * @param username 用户名
     * @return 员工对象
     */
    Employee findByUsername(String username);
}
