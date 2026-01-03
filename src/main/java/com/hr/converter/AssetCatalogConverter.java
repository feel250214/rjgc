package com.hr.converter;

import com.hr.dto.AssetCatalogDto;
import com.hr.entity.AssetCatalog;
import org.springframework.stereotype.Component;

@Component
public class AssetCatalogConverter {

    /**
     * 将AssetCatalog实体转换为AssetCatalogDto
     *
     * @param assetCatalog 资产目录实体
     * @return 资产目录DTO
     */
    public AssetCatalogDto toDto(AssetCatalog assetCatalog) {
        if (assetCatalog == null) {
            return null;
        }
        AssetCatalogDto dto = new AssetCatalogDto();
        dto.setId(assetCatalog.getId());
        dto.setType(assetCatalog.getType());
        dto.setName(assetCatalog.getName());
        dto.setDescription(assetCatalog.getDescription());
        dto.setStockQuantity(assetCatalog.getStockQuantity());
        dto.setUnit(assetCatalog.getUnit());
        dto.setPrice(assetCatalog.getPrice());
        dto.setBudgetLimit(assetCatalog.getBudgetLimit());
        dto.setStatus(assetCatalog.getStatus());
        dto.setCreateTime(assetCatalog.getCreateTime());
        dto.setUpdateTime(assetCatalog.getUpdateTime());
        return dto;
    }

    /**
     * 将AssetCatalogDto转换为AssetCatalog实体
     *
     * @param dto 资产目录DTO
     * @return 资产目录实体
     */
    public AssetCatalog toEntity(AssetCatalogDto dto) {
        if (dto == null) {
            return null;
        }
        AssetCatalog assetCatalog = new AssetCatalog();
        assetCatalog.setId(dto.getId());
        assetCatalog.setType(dto.getType());
        assetCatalog.setName(dto.getName());
        assetCatalog.setDescription(dto.getDescription());
        assetCatalog.setStockQuantity(dto.getStockQuantity());
        assetCatalog.setUnit(dto.getUnit());
        assetCatalog.setPrice(dto.getPrice());
        assetCatalog.setBudgetLimit(dto.getBudgetLimit());
        assetCatalog.setStatus(dto.getStatus());
        return assetCatalog;
    }
}