package com.hr.service;

import com.hr.dao.AnnouncementReadRecordRepository;
import com.hr.dao.AnnouncementRepository;
import com.hr.entity.Announcement;
import com.hr.entity.AnnouncementReadRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 公告服务类
 */
@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementReadRecordRepository announcementReadRecordRepository;

    /**
     * 获取所有公告（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 公告分页列表
     */
    public Page<Announcement> getAllAnnouncements(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return announcementRepository.findAll(pageable);
    }

    /**
     * 根据状态获取公告
     *
     * @param status 状态
     * @return 公告列表
     */
    public List<Announcement> getAnnouncementsByStatus(String status) {
        return announcementRepository.findByStatus(status);
    }

    /**
     * 获取当前有效的公告
     *
     * @return 公告列表
     */
    public List<Announcement> getValidAnnouncements() {
        Date now = new Date();
        return announcementRepository.findByStatusAndValidFromLessThanEqualAndValidToGreaterThanEqual("已发布", now, now);
    }

    /**
     * 根据ID获取公告
     *
     * @param id 公告ID
     * @return 公告信息
     */
    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id).orElse(null);
    }

    /**
     * 创建公告
     *
     * @param announcement 公告信息
     * @return 创建后的公告
     */
    public Announcement createAnnouncement(Announcement announcement) {
        // 设置初始状态为草稿
        announcement.setStatus("草稿");
        announcement.setVersion(1);
        return announcementRepository.save(announcement);
    }

    /**
     * 发布公告
     *
     * @param id          公告ID
     * @param publisherId 发布人ID
     * @return 更新后的公告
     */
    public Announcement publishAnnouncement(Long id, Long publisherId) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);
        if (announcement == null) {
            throw new IllegalArgumentException("Announcement not found");
        }

        // 更新公告状态为已发布，并设置发布时间和发布人
        announcement.setStatus("已发布");
        announcement.setPublisherId(publisherId);
        announcement.setPublishTime(new Date());
        return announcementRepository.save(announcement);
    }

    /**
     * 更新公告
     *
     * @param id           公告ID
     * @param announcement 公告信息
     * @return 更新后的公告
     */
    public Announcement updateAnnouncement(Long id, Announcement announcement) {
        Announcement existingAnnouncement = announcementRepository.findById(id).orElse(null);
        if (existingAnnouncement == null) {
            throw new IllegalArgumentException("Announcement not found");
        }

        // 更新公告信息，版本号自动递增
        announcement.setId(id);
        announcement.setVersion(existingAnnouncement.getVersion() + 1);
        return announcementRepository.save(announcement);
    }

    /**
     * 删除公告
     *
     * @param id 公告ID
     */
    public void deleteAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);
        if (announcement == null) {
            throw new IllegalArgumentException("Announcement not found");
        }

        // 标记为已删除，而不是物理删除
        announcement.setStatus("已删除");
        announcement.setVersion(announcement.getVersion() + 1);
        announcementRepository.save(announcement);
    }

    /**
     * 记录公告阅读
     *
     * @param announcementId 公告ID
     * @param employeeId     员工ID
     * @return 阅读记录
     */
    public AnnouncementReadRecord recordAnnouncementRead(Long announcementId, Long employeeId) {
        // 检查阅读记录是否已存在
        AnnouncementReadRecord existingRecord = announcementReadRecordRepository.findByAnnouncementIdAndEmployeeId(announcementId, employeeId);
        if (existingRecord != null) {
            return existingRecord;
        }

        // 创建新的阅读记录
        AnnouncementReadRecord readRecord = new AnnouncementReadRecord();
        readRecord.setAnnouncementId(announcementId);
        readRecord.setEmployeeId(employeeId);
        readRecord.setReadTime(new Date());
        readRecord.setIsConfirmed(0);
        return announcementReadRecordRepository.save(readRecord);
    }

    /**
     * 确认阅读公告
     *
     * @param announcementId 公告ID
     * @param employeeId     员工ID
     * @return 更新后的阅读记录
     */
    public AnnouncementReadRecord confirmAnnouncementRead(Long announcementId, Long employeeId) {
        AnnouncementReadRecord readRecord = announcementReadRecordRepository.findByAnnouncementIdAndEmployeeId(announcementId, employeeId);
        if (readRecord == null) {
            // 如果阅读记录不存在，先创建
            readRecord = new AnnouncementReadRecord();
            readRecord.setAnnouncementId(announcementId);
            readRecord.setEmployeeId(employeeId);
            readRecord.setReadTime(new Date());
        }

        // 更新为已确认阅读
        readRecord.setIsConfirmed(1);
        readRecord.setConfirmTime(new Date());
        return announcementReadRecordRepository.save(readRecord);
    }

    /**
     * 获取公告的阅读记录
     *
     * @param announcementId 公告ID
     * @return 阅读记录列表
     */
    public List<AnnouncementReadRecord> getAnnouncementReadRecords(Long announcementId) {
        return announcementReadRecordRepository.findByAnnouncementId(announcementId);
    }

    /**
     * 获取员工的公告阅读记录
     *
     * @param employeeId 员工ID
     * @return 阅读记录列表
     */
    public List<AnnouncementReadRecord> getEmployeeAnnouncementReadRecords(Long employeeId) {
        return announcementReadRecordRepository.findByEmployeeId(employeeId);
    }
}