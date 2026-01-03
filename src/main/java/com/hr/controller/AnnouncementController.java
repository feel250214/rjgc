package com.hr.controller;

import com.hr.dto.ResponseDto;
import com.hr.entity.Announcement;
import com.hr.entity.AnnouncementReadRecord;
import com.hr.service.AnnouncementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 公告控制器
 */
@RestController
@RequestMapping("/announcements")
@Slf4j
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    /**
     * 获取所有公告（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 公告分页列表
     */
    @GetMapping
    public ResponseDto<Page<Announcement>> getAllAnnouncements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Announcement> announcements = announcementService.getAllAnnouncements(page, size);
            return ResponseDto.success(announcements);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get announcements: " + e.getMessage());
        }
    }

    /**
     * 获取当前有效的公告
     *
     * @return 公告列表
     */
    @GetMapping("/valid")
    public ResponseDto<List<Announcement>> getValidAnnouncements() {
        try {
            List<Announcement> announcements = announcementService.getValidAnnouncements();
            return ResponseDto.success(announcements);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get valid announcements: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取公告
     *
     * @param id 公告ID
     * @return 公告信息
     */
    @GetMapping("/{id}")
    public ResponseDto<Announcement> getAnnouncementById(@PathVariable Long id) {
        try {
            Announcement announcement = announcementService.getAnnouncementById(id);
            if (announcement == null) {
                return ResponseDto.fail(404, "Announcement not found");
            }
            return ResponseDto.success(announcement);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get announcement: " + e.getMessage());
        }
    }

    /**
     * 创建公告
     *
     * @param announcement 公告信息
     * @return 创建后的公告
     */
    @PostMapping
    public ResponseDto<Announcement> createAnnouncement(@RequestBody Announcement announcement) {
        try {
            Announcement createdAnnouncement = announcementService.createAnnouncement(announcement);
            return ResponseDto.success(createdAnnouncement);
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to create announcement: " + e.getMessage());
        }
    }

    /**
     * 更新公告
     *
     * @param id           公告ID
     * @param announcement 公告信息
     * @return 更新后的公告
     */
    @PutMapping("/{id}")
    public ResponseDto<Announcement> updateAnnouncement(@PathVariable Long id, @RequestBody Announcement announcement) {
        try {
            Announcement updatedAnnouncement = announcementService.updateAnnouncement(id, announcement);
            return ResponseDto.success(updatedAnnouncement);
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to update announcement: " + e.getMessage());
        }
    }

    /**
     * 发布公告
     *
     * @param id          公告ID
     * @param publisherId 发布人ID
     * @return 更新后的公告
     */
    @PostMapping("/{id}/publish")
    public ResponseDto<Announcement> publishAnnouncement(
            @PathVariable Long id,
            @RequestParam Long publisherId) {
        try {
            Announcement publishedAnnouncement = announcementService.publishAnnouncement(id, publisherId);
            return ResponseDto.success(publishedAnnouncement);
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to publish announcement: " + e.getMessage());
        }
    }

    /**
     * 删除公告
     *
     * @param id 公告ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteAnnouncement(@PathVariable Long id) {
        try {
            announcementService.deleteAnnouncement(id);
            return ResponseDto.success("Announcement deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail(400, e.getMessage());
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to delete announcement: " + e.getMessage());
        }
    }

    /**
     * 记录公告阅读
     *
     * @param announcementId 公告ID
     * @param employeeId     员工ID
     * @return 阅读记录
     */
    @PostMapping("/{announcementId}/read")
    public ResponseDto<AnnouncementReadRecord> recordAnnouncementRead(
            @PathVariable Long announcementId,
            @RequestParam Long employeeId) {
        try {
            AnnouncementReadRecord readRecord = announcementService.recordAnnouncementRead(announcementId, employeeId);
            return ResponseDto.success(readRecord);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to record announcement read: " + e.getMessage());
        }
    }

    /**
     * 确认阅读公告
     *
     * @param announcementId 公告ID
     * @param employeeId     员工ID
     * @return 阅读记录
     */
    @PostMapping("/{announcementId}/confirm-read")
    public ResponseDto<AnnouncementReadRecord> confirmAnnouncementRead(
            @PathVariable Long announcementId,
            @RequestParam Long employeeId) {
        try {
            AnnouncementReadRecord readRecord = announcementService.confirmAnnouncementRead(announcementId, employeeId);
            return ResponseDto.success(readRecord);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to confirm announcement read: " + e.getMessage());
        }
    }

    /**
     * 获取公告的阅读记录
     *
     * @param announcementId 公告ID
     * @return 阅读记录列表
     */
    @GetMapping("/{announcementId}/read-records")
    public ResponseDto<List<AnnouncementReadRecord>> getAnnouncementReadRecords(@PathVariable Long announcementId) {
        try {
            List<AnnouncementReadRecord> readRecords = announcementService.getAnnouncementReadRecords(announcementId);
            return ResponseDto.success(readRecords);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get announcement read records: " + e.getMessage());
        }
    }

    /**
     * 获取员工的公告阅读记录
     *
     * @param employeeId 员工ID
     * @return 阅读记录列表
     */
    @GetMapping("/employee/{employeeId}/read-records")
    public ResponseDto<List<AnnouncementReadRecord>> getEmployeeAnnouncementReadRecords(@PathVariable Long employeeId) {
        try {
            List<AnnouncementReadRecord> readRecords = announcementService.getEmployeeAnnouncementReadRecords(employeeId);
            return ResponseDto.success(readRecords);
        } catch (Exception e) {
            return ResponseDto.fail(500, "Failed to get employee announcement read records: " + e.getMessage());
        }
    }
}