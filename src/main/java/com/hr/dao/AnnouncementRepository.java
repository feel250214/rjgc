package com.hr.dao;

import com.hr.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 公告数据访问接口
 */
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    /**
     * 根据状态查询公告
     *
     * @param status 状态
     * @return 公告列表
     */
    List<Announcement> findByStatus(String status);

    /**
     * 根据类型查询公告
     *
     * @param type 类型
     * @return 公告列表
     */
    List<Announcement> findByType(String type);

    /**
     * 根据发布范围查询公告
     *
     * @param publishScope 发布范围
     * @return 公告列表
     */
    List<Announcement> findByPublishScope(String publishScope);

    /**
     * 根据有效期查询当前有效的公告
     *
     * @param now 当前时间
     * @return 公告列表
     */
    List<Announcement> findByStatusAndValidFromLessThanEqualAndValidToGreaterThanEqual(String status, Date now, Date now2);
}