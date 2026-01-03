package com.hr.dao;

import com.hr.entity.AnnouncementReadRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 公告阅读记录数据访问接口
 */
@Repository
public interface AnnouncementReadRecordRepository extends JpaRepository<AnnouncementReadRecord, Long> {
    /**
     * 根据公告ID查询阅读记录
     *
     * @param announcementId 公告ID
     * @return 阅读记录列表
     */
    List<AnnouncementReadRecord> findByAnnouncementId(Long announcementId);

    /**
     * 根据员工ID查询阅读记录
     *
     * @param employeeId 员工ID
     * @return 阅读记录列表
     */
    List<AnnouncementReadRecord> findByEmployeeId(Long employeeId);

    /**
     * 根据公告ID和员工ID查询阅读记录
     *
     * @param announcementId 公告ID
     * @param employeeId     员工ID
     * @return 阅读记录
     */
    AnnouncementReadRecord findByAnnouncementIdAndEmployeeId(Long announcementId, Long employeeId);

    /**
     * 根据公告ID和是否已确认阅读查询阅读记录
     *
     * @param announcementId 公告ID
     * @param isConfirmed    是否已确认阅读
     * @return 阅读记录列表
     */
    List<AnnouncementReadRecord> findByAnnouncementIdAndIsConfirmed(Long announcementId, Integer isConfirmed);
}