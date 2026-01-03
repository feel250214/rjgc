package com.hr.dao;

import com.hr.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 职位数据访问接口
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    /**
     * 根据部门ID查询职位列表
     *
     * @param departmentId 部门ID
     * @return 职位列表
     */
    java.util.List<Position> findByDepartmentId(Long departmentId);
}