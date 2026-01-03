package com.hr.controller;

import com.hr.converter.AssetCatalogConverter;
import com.hr.dto.AssetCatalogDto;
import com.hr.dto.ResponseDto;
import com.hr.entity.AssetCatalog;
import com.hr.service.AssetCatalogService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资产目录控制器
 */
@RestController
@RequestMapping("/asset-catalogs")
@Slf4j
public class AssetCatalogController {

    @Autowired
    private AssetCatalogService assetCatalogService;

    @Autowired
    private AssetCatalogConverter assetCatalogConverter;

    /**
     * 获取所有资产目录（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 资产目录分页列表
     */
    @GetMapping
    public ResponseDto<Page<AssetCatalogDto>> getAllAssetCatalogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<AssetCatalog> assetCatalogs = assetCatalogService.getAllAssetCatalogs(page, size);

            // 转换为DTO列表
            List<AssetCatalogDto> dtoList = assetCatalogs.getContent().stream()
                    .map(assetCatalogConverter::toDto)
                    .collect(Collectors.toList());

            // 创建DTO分页对象
            Page<AssetCatalogDto> dtoPage = new PageImpl<>(dtoList, assetCatalogs.getPageable(), assetCatalogs.getTotalElements());

            return ResponseDto.success(dtoPage);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get asset catalogs: " + e.getMessage());
        }
    }

    /**
     * 获取所有资产目录（不分页）
     *
     * @return 资产目录列表
     */
    @GetMapping("/all")
    public ResponseDto<List<AssetCatalogDto>> getAllAssetCatalogs() {
        try {
            List<AssetCatalog> assetCatalogs = assetCatalogService.getAllAssetCatalogs();

            // 转换为DTO列表
            List<AssetCatalogDto> dtoList = assetCatalogs.stream()
                    .map(assetCatalogConverter::toDto)
                    .collect(Collectors.toList());

            return ResponseDto.success(dtoList);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get asset catalogs: " + e.getMessage());
        }
    }

    /**
     * 根据资产类型获取资产目录
     *
     * @param type 资产类型
     * @return 资产目录列表
     */
    @GetMapping("/type/{type}")
    public ResponseDto<List<AssetCatalogDto>> getAssetCatalogsByType(@PathVariable String type) {
        try {
            List<AssetCatalog> assetCatalogs = assetCatalogService.getAssetCatalogsByType(type);

            // 转换为DTO列表
            List<AssetCatalogDto> dtoList = assetCatalogs.stream()
                    .map(assetCatalogConverter::toDto)
                    .collect(Collectors.toList());

            return ResponseDto.success(dtoList);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get asset catalogs: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取资产目录
     *
     * @param id 资产ID
     * @return 资产目录信息
     */
    @GetMapping("/{id}")
    public ResponseDto<AssetCatalogDto> getAssetCatalogById(@PathVariable Long id) {
        try {
            AssetCatalog assetCatalog = assetCatalogService.getAssetCatalogById(id);
            if (assetCatalog == null) {
                return ResponseDto.fail(404, "Asset catalog not found");
            }

            // 转换为DTO
            AssetCatalogDto dto = assetCatalogConverter.toDto(assetCatalog);

            return ResponseDto.success(dto);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get asset catalog: " + e.getMessage());
        }
    }

    /**
     * 创建资产目录
     *
     * @param assetCatalogDto 资产目录信息
     * @param result          绑定结果
     * @return 创建后的资产目录
     */
    @PostMapping
    public ResponseDto<AssetCatalogDto> createAssetCatalog(@Valid @RequestBody AssetCatalogDto assetCatalogDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseDto.fail(400, result.getFieldError().getDefaultMessage());
        }
        try {
            // 转换为实体类
            AssetCatalog assetCatalog = assetCatalogConverter.toEntity(assetCatalogDto);

            AssetCatalog createdAssetCatalog = assetCatalogService.createAssetCatalog(assetCatalog);

            // 转换为DTO返回
            AssetCatalogDto dto = assetCatalogConverter.toDto(createdAssetCatalog);

            return ResponseDto.success(dto);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create asset catalog: " + e.getMessage());
        }
    }

    /**
     * 更新资产目录
     *
     * @param id              资产ID
     * @param assetCatalogDto 资产目录信息
     * @param result          绑定结果
     * @return 更新后的资产目录
     */
    @PutMapping("/{id}")
    public ResponseDto<AssetCatalogDto> updateAssetCatalog(@PathVariable Long id, @Valid @RequestBody AssetCatalogDto assetCatalogDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseDto.fail(400, result.getFieldError().getDefaultMessage());
        }
        try {
            // 检查资产目录是否存在
            if (assetCatalogService.getAssetCatalogById(id) == null) {
                return ResponseDto.fail(404, "Asset catalog not found");
            }

            // 转换为实体类并设置ID
            AssetCatalog assetCatalog = assetCatalogConverter.toEntity(assetCatalogDto);
            assetCatalog.setId(id);

            AssetCatalog updatedAssetCatalog = assetCatalogService.updateAssetCatalog(id, assetCatalog);

            // 转换为DTO返回
            AssetCatalogDto dto = assetCatalogConverter.toDto(updatedAssetCatalog);

            return ResponseDto.success(dto);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update asset catalog: " + e.getMessage());
        }
    }

    /**
     * 删除资产目录
     *
     * @param id 资产ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteAssetCatalog(@PathVariable Long id) {
        try {
            // 检查资产目录是否存在
            if (assetCatalogService.getAssetCatalogById(id) == null) {
                return ResponseDto.fail(404, "Asset catalog not found");
            }
            assetCatalogService.deleteAssetCatalog(id);
            return ResponseDto.success("Asset catalog deleted successfully");
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete asset catalog: " + e.getMessage());
        }
    }
}