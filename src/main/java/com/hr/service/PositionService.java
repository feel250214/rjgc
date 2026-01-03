package com.hr.service;

import com.hr.dao.EmployeeRepository;
import com.hr.dao.PositionRepository;
import com.hr.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职位服务类
 */
@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 获取所有职位（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 职位分页列表
     */
    public Page<Position> getAllPositions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return positionRepository.findAll(pageable);
    }

    /**
     * 获取所有职位
     *
     * @return 职位列表
     */
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    /**
     * 根据部门ID获取职位列表
     *
     * @param departmentId 部门ID
     * @return 职位列表
     */
    public List<Position> getPositionsByDepartmentId(Long departmentId) {
        return positionRepository.findByDepartmentId(departmentId);
    }

    /**
     * 根据ID获取职位
     *
     * @param id 职位ID
     * @return 职位信息
     */
    public Position getPositionById(Long id) {
        return positionRepository.findById(id).orElse(null);
    }

    /**
     * 创建职位
     *
     * @param position 职位信息
     * @return 创建后的职位信息
     */
    public Position createPosition(Position position) {
        return positionRepository.save(position);
    }

    /**
     * 更新职位
     *
     * @param id       职位ID
     * @param position 职位信息
     * @return 更新后的职位信息
     */
    public Position updatePosition(Long id, Position position) {
        position.setId(id);
        return positionRepository.save(position);
    }

    /**
     * 删除职位
     *
     * @param id 职位ID
     * @return 是否删除成功
     */
    public boolean deletePosition(Long id) {
        // 检查该职位下是否有员工
        if (!employeeRepository.findByPositionId(id).isEmpty()) {
            return false;
        }
        positionRepository.deleteById(id);
        return true;
    }
}