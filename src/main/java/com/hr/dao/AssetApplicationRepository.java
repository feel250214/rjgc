package com.hr.dao;

import com.hr.entity.AssetApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资产申请数据访问接口
 */
@Repository
public interface AssetApplicationRepository extends JpaRepository<AssetApplication, Long> {
    /**
     * 根据员工ID查询资产申请列表
     *
     * @param employeeId 员工ID
     * @return 资产申请列表
     */
    List<AssetApplication> findByEmployeeId(Long employeeId);

    /**
     * 根据状态查询资产申请列表
     *
     * @param status 状态
     * @return 资产申请列表
     */
    List<AssetApplication> findByStatus(String status);

    /**
     * 根据资产ID查询资产申请列表
     *
     * @param assetId 资产ID
     * @return 资产申请列表
     */
    List<AssetApplication> findByAssetId(Long assetId);
}