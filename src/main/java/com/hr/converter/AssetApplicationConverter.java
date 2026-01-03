package com.hr.converter;

import com.hr.dto.AssetApplicationDto;
import com.hr.entity.AssetApplication;
import org.springframework.stereotype.Component;

@Component
public class AssetApplicationConverter {

    /**
     * 将AssetApplication实体转换为AssetApplicationDto
     *
     * @param assetApplication 资产申请实体
     * @return 资产申请DTO
     */
    public AssetApplicationDto toDto(AssetApplication assetApplication) {
        if (assetApplication == null) {
            return null;
        }
        AssetApplicationDto dto = new AssetApplicationDto();
        dto.setId(assetApplication.getId());
        dto.setEmployeeId(assetApplication.getEmployeeId());
        dto.setAssetId(assetApplication.getAssetId());
        dto.setRequestQuantity(assetApplication.getRequestQuantity());
        dto.setPurpose(assetApplication.getPurpose());
        dto.setStatus(assetApplication.getStatus());
        dto.setManagerComment(assetApplication.getManagerComment());
        dto.setAdminComment(assetApplication.getAdminComment());
        dto.setCreateTime(assetApplication.getCreateTime());
        dto.setUpdateTime(assetApplication.getUpdateTime());
        return dto;
    }

    /**
     * 将AssetApplicationDto转换为AssetApplication实体
     *
     * @param dto 资产申请DTO
     * @return 资产申请实体
     */
    public AssetApplication toEntity(AssetApplicationDto dto) {
        if (dto == null) {
            return null;
        }
        AssetApplication assetApplication = new AssetApplication();
        assetApplication.setId(dto.getId());
        assetApplication.setEmployeeId(dto.getEmployeeId());
        assetApplication.setAssetId(dto.getAssetId());
        assetApplication.setRequestQuantity(dto.getRequestQuantity());
        assetApplication.setPurpose(dto.getPurpose());
        assetApplication.setStatus(dto.getStatus());
        assetApplication.setManagerComment(dto.getManagerComment());
        assetApplication.setAdminComment(dto.getAdminComment());
        return assetApplication;
    }
}