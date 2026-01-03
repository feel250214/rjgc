package com.hr.dao;

import com.hr.entity.AssetCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资产目录数据访问接口
 */
@Repository
public interface AssetCatalogRepository extends JpaRepository<AssetCatalog, Long> {
    /**
     * 根据资产类型查询资产列表
     *
     * @param type 资产类型
     * @return 资产列表
     */
    List<AssetCatalog> findByType(String type);

    /**
     * 根据状态查询资产列表
     *
     * @param status 状态
     * @return 资产列表
     */
    List<AssetCatalog> findByStatus(String status);
}