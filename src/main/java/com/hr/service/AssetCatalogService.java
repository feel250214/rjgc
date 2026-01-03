package com.hr.service;

import com.hr.dao.AssetCatalogRepository;
import com.hr.entity.AssetCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资产目录服务类
 */
@Service
public class AssetCatalogService {

    @Autowired
    private AssetCatalogRepository assetCatalogRepository;

    /**
     * 获取所有资产目录（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 资产目录分页列表
     */
    public Page<AssetCatalog> getAllAssetCatalogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return assetCatalogRepository.findAll(pageable);
    }

    /**
     * 获取所有资产目录（不分页）
     *
     * @return 资产目录列表
     */
    public List<AssetCatalog> getAllAssetCatalogs() {
        return assetCatalogRepository.findAll();
    }

    /**
     * 根据资产类型获取资产目录
     *
     * @param type 资产类型
     * @return 资产目录列表
     */
    public List<AssetCatalog> getAssetCatalogsByType(String type) {
        return assetCatalogRepository.findByType(type);
    }

    /**
     * 根据ID获取资产目录
     *
     * @param id 资产ID
     * @return 资产目录信息
     */
    public AssetCatalog getAssetCatalogById(Long id) {
        return assetCatalogRepository.findById(id).orElse(null);
    }

    /**
     * 创建资产目录
     *
     * @param assetCatalog 资产目录信息
     * @return 创建后的资产目录
     */
    public AssetCatalog createAssetCatalog(AssetCatalog assetCatalog) {
        return assetCatalogRepository.save(assetCatalog);
    }

    /**
     * 更新资产目录
     *
     * @param id           资产ID
     * @param assetCatalog 资产目录信息
     * @return 更新后的资产目录
     */
    public AssetCatalog updateAssetCatalog(Long id, AssetCatalog assetCatalog) {
        assetCatalog.setId(id);
        return assetCatalogRepository.save(assetCatalog);
    }

    /**
     * 删除资产目录
     *
     * @param id 资产ID
     */
    public void deleteAssetCatalog(Long id) {
        assetCatalogRepository.deleteById(id);
    }

    /**
     * 更新资产库存数量
     *
     * @param id       资产ID
     * @param quantity 变更数量（正数增加，负数减少）
     * @return 更新后的资产目录
     */
    public AssetCatalog updateStockQuantity(Long id, Integer quantity) {
        AssetCatalog assetCatalog = assetCatalogRepository.findById(id).orElse(null);
        if (assetCatalog != null) {
            Integer currentStock = assetCatalog.getStockQuantity();
            assetCatalog.setStockQuantity(currentStock + quantity);
            return assetCatalogRepository.save(assetCatalog);
        }
        return null;
    }
}