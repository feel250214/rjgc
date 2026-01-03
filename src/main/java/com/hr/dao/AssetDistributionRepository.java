package com.hr.dao;

import com.hr.entity.AssetDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资产分配记录数据访问接口
 */
@Repository
public interface AssetDistributionRepository extends JpaRepository<AssetDistribution, Long> {
    /**
     * 根据资产申请ID查询分配记录
     *
     * @param applicationId 资产申请ID
     * @return 分配记录列表
     */
    List<AssetDistribution> findByApplicationId(Long applicationId);

    /**
     * 根据员工ID查询分配记录
     *
     * @param employeeId 员工ID
     * @return 分配记录列表
     */
    List<AssetDistribution> findByEmployeeId(Long employeeId);

    /**
     * 根据接收状态查询分配记录
     *
     * @param receiveStatus 接收状态
     * @return 分配记录列表
     */
    List<AssetDistribution> findByReceiveStatus(String receiveStatus);
}