package com.hr.dao;

import com.hr.entity.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 请假申请数据访问接口
 */
@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    /**
     * 根据员工ID查询请假申请列表
     *
     * @param employeeId 员工ID
     * @return 请假申请列表
     */
    List<LeaveApplication> findByEmployeeId(Long employeeId);

    /**
     * 根据状态查询请假申请列表
     *
     * @param status 状态
     * @return 请假申请列表
     */
    List<LeaveApplication> findByStatus(String status);
}