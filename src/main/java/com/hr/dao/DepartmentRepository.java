package com.hr.dao;

import com.hr.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门数据访问接口
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    /**
     * 根据部门名称查询部门
     *
     * @param name 部门名称
     * @return 部门实体
     */
    Department findByName(String name);

    /**
     * 根据部门编号查询部门
     *
     * @param code 部门编号
     * @return 部门实体
     */
    Department findByCode(String code);

    /**
     * 根据上级部门ID查询子部门列表
     *
     * @param parentId 上级部门ID
     * @return 子部门列表
     */
    List<Department> findByParentId(Long parentId);
}