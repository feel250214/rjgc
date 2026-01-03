package com.hr.controller;

import com.hr.dto.ResponseDto;
import com.hr.entity.Position;
import com.hr.service.PositionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职位控制器
 */
@RestController
@RequestMapping("/positions")
@Slf4j
public class PositionController {

    @Autowired
    private PositionService positionService;

    /**
     * 获取所有职位（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 职位分页列表
     */
    @GetMapping
    public ResponseDto<Page<Position>> getAllPositions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Position> positions = positionService.getAllPositions(page, size);
            return ResponseDto.success(positions);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get positions: " + e.getMessage());
        }
    }

    /**
     * 获取所有职位（不分页）
     *
     * @return 职位列表
     */
    @GetMapping("/all")
    public ResponseDto<List<Position>> getAllPositions() {
        try {
            List<Position> positions = positionService.getAllPositions();
            return ResponseDto.success(positions);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get positions: " + e.getMessage());
        }
    }

    /**
     * 根据部门ID获取职位列表
     *
     * @param departmentId 部门ID
     * @return 职位列表
     */
    @GetMapping("/department/{departmentId}")
    public ResponseDto<List<Position>> getPositionsByDepartmentId(@PathVariable Long departmentId) {
        try {
            List<Position> positions = positionService.getPositionsByDepartmentId(departmentId);
            return ResponseDto.success(positions);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get positions: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取职位
     *
     * @param id 职位ID
     * @return 职位信息
     */
    @GetMapping("/{id}")
    public ResponseDto<Position> getPositionById(@PathVariable Long id) {
        try {
            Position position = positionService.getPositionById(id);
            if (position == null) {
                return ResponseDto.fail(404, "Position not found");
            }
            return ResponseDto.success(position);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get position: " + e.getMessage());
        }
    }

    /**
     * 创建职位
     *
     * @param position 职位信息
     * @param result   绑定结果
     * @return 创建后的职位信息
     */
    @PostMapping
    public ResponseDto<Position> createPosition(@Valid @RequestBody Position position, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseDto.fail(400, result.getFieldError().getDefaultMessage());
        }
        try {
            Position createdPosition = positionService.createPosition(position);
            return ResponseDto.success(createdPosition);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create position: " + e.getMessage());
        }
    }

    /**
     * 更新职位
     *
     * @param id       职位ID
     * @param position 职位信息
     * @param result   绑定结果
     * @return 更新后的职位信息
     */
    @PutMapping("/{id}")
    public ResponseDto<Position> updatePosition(@PathVariable Long id, @Valid @RequestBody Position position, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseDto.fail(400, result.getFieldError().getDefaultMessage());
        }
        try {
            // 检查职位是否存在
            if (positionService.getPositionById(id) == null) {
                return ResponseDto.fail(404, "Position not found");
            }
            Position updatedPosition = positionService.updatePosition(id, position);
            return ResponseDto.success(updatedPosition);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update position: " + e.getMessage());
        }
    }

    /**
     * 删除职位
     *
     * @param id 职位ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deletePosition(@PathVariable Long id) {
        try {
            // 检查职位是否存在
            if (positionService.getPositionById(id) == null) {
                return ResponseDto.fail(404, "Position not found");
            }
            // 检查职位下是否存在员工，存在则不允许删除
            boolean deleted = positionService.deletePosition(id);
            if (!deleted) {
                return ResponseDto.fail(400, "Cannot delete position: There are employees assigned to this position");
            }
            return ResponseDto.success("Position deleted successfully");
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete position: " + e.getMessage());
        }
    }
}