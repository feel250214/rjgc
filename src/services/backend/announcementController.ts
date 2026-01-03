// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /announcements */
export async function getAllAnnouncements(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllAnnouncementsParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPageAnnouncement>('/announcements', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /announcements */
export async function createAnnouncement(body: API.Announcement, options?: { [key: string]: any }) {
  return request<API.ResponseDtoAnnouncement>('/announcements', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /announcements/${param0} */
export async function getAnnouncementById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAnnouncementByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoAnnouncement>(`/announcements/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /announcements/${param0} */
export async function updateAnnouncement(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateAnnouncementParams,
  body: API.Announcement,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoAnnouncement>(`/announcements/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /announcements/${param0} */
export async function deleteAnnouncement(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteAnnouncementParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoObject>(`/announcements/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /announcements/${param0}/confirm-read */
export async function confirmAnnouncementRead(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.confirmAnnouncementReadParams,
  options?: { [key: string]: any },
) {
  const { announcementId: param0, ...queryParams } = params;
  return request<API.ResponseDtoAnnouncementReadRecord>(`/announcements/${param0}/confirm-read`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /announcements/${param0}/publish */
export async function publishAnnouncement(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.publishAnnouncementParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoAnnouncement>(`/announcements/${param0}/publish`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /announcements/${param0}/read */
export async function recordAnnouncementRead(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.recordAnnouncementReadParams,
  options?: { [key: string]: any },
) {
  const { announcementId: param0, ...queryParams } = params;
  return request<API.ResponseDtoAnnouncementReadRecord>(`/announcements/${param0}/read`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /announcements/${param0}/read-records */
export async function getAnnouncementReadRecords(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAnnouncementReadRecordsParams,
  options?: { [key: string]: any },
) {
  const { announcementId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListAnnouncementReadRecord>(
    `/announcements/${param0}/read-records`,
    {
      method: 'GET',
      params: { ...queryParams },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 GET /announcements/employee/${param0}/read-records */
export async function getEmployeeAnnouncementReadRecords(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getEmployeeAnnouncementReadRecordsParams,
  options?: { [key: string]: any },
) {
  const { employeeId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListAnnouncementReadRecord>(
    `/announcements/employee/${param0}/read-records`,
    {
      method: 'GET',
      params: { ...queryParams },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 GET /announcements/valid */
export async function getValidAnnouncements(options?: { [key: string]: any }) {
  return request<API.ResponseDtoListAnnouncement>('/announcements/valid', {
    method: 'GET',
    ...(options || {}),
  });
}
