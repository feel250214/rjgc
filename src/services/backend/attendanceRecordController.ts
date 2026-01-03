// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /attendance-records */
export async function getAllAttendanceRecords(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllAttendanceRecordsParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPageAttendanceRecord>('/attendance-records', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /attendance-records */
export async function createAttendanceRecord(
  body: API.AttendanceRecord,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoAttendanceRecord>('/attendance-records', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /attendance-records/${param0} */
export async function getAttendanceRecordById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAttendanceRecordByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoAttendanceRecord>(`/attendance-records/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /attendance-records/${param0} */
export async function updateAttendanceRecord(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateAttendanceRecordParams,
  body: API.AttendanceRecord,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoAttendanceRecord>(`/attendance-records/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /attendance-records/${param0} */
export async function deleteAttendanceRecord(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteAttendanceRecordParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoObject>(`/attendance-records/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /attendance-records/employee/${param0} */
export async function getAttendanceRecordsByEmployeeId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAttendanceRecordsByEmployeeIdParams,
  options?: { [key: string]: any },
) {
  const { employeeId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListAttendanceRecord>(`/attendance-records/employee/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}
