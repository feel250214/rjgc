package com.hr.service;

import com.hr.dao.SalaryDisputeRepository;
import com.hr.entity.SalaryDispute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 薪资质疑服务类
 */
@Service
public class SalaryDisputeService {

    @Autowired
    private SalaryDisputeRepository salaryDisputeRepository;

    /**
     * 获取所有薪资质疑（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 薪资质疑分页列表
     */
    public Page<SalaryDispute> getAllSalaryDisputes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return salaryDisputeRepository.findAll(pageable);
    }

    /**
     * 根据员工ID获取薪资质疑
     *
     * @param employeeId 员工ID
     * @return 薪资质疑列表
     */
    public List<SalaryDispute> getSalaryDisputesByEmployeeId(Long employeeId) {
        return salaryDisputeRepository.findByEmployeeId(employeeId);
    }

    /**
     * 根据薪资明细ID获取薪资质疑
     *
     * @param salaryDetailId 薪资明细ID
     * @return 薪资质疑列表
     */
    public List<SalaryDispute> getSalaryDisputesBySalaryDetailId(Long salaryDetailId) {
        return salaryDisputeRepository.findBySalaryDetailId(salaryDetailId);
    }

    /**
     * 根据状态获取薪资质疑
     *
     * @param status 状态
     * @return 薪资质疑列表
     */
    public List<SalaryDispute> getSalaryDisputesByStatus(String status) {
        return salaryDisputeRepository.findByStatus(status);
    }

    /**
     * 根据ID获取薪资质疑
     *
     * @param id 薪资质疑ID
     * @return 薪资质疑
     */
    public SalaryDispute getSalaryDisputeById(Long id) {
        return salaryDisputeRepository.findById(id).orElse(null);
    }

    /**
     * 员工提交薪资质疑
     *
     * @param salaryDispute 薪资质疑信息
     * @return 创建后的薪资质疑
     */
    public SalaryDispute createSalaryDispute(SalaryDispute salaryDispute) {
        // 设置初始状态为待处理
        salaryDispute.setStatus("待处理");
        return salaryDisputeRepository.save(salaryDispute);
    }

    /**
     * HR解答薪资质疑
     *
     * @param id         薪资质疑ID
     * @param hrResponse HR解答内容
     * @return 更新后的薪资质疑
     */
    public SalaryDispute respondToSalaryDispute(Long id, String hrResponse) {
        SalaryDispute salaryDispute = salaryDisputeRepository.findById(id).orElse(null);
        if (salaryDispute == null) {
            return null;
        }

        // 更新薪资质疑状态为已处理，并记录解答内容和时间
        salaryDispute.setStatus("已处理");
        salaryDispute.setHrResponse(hrResponse);
        salaryDispute.setHrResponseTime(new Date());
        return salaryDisputeRepository.save(salaryDispute);
    }

    /**
     * 删除薪资质疑
     *
     * @param id 薪资质疑ID
     */
    public void deleteSalaryDispute(Long id) {
        salaryDisputeRepository.deleteById(id);
    }
}