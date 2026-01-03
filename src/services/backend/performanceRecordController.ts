// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /performance-records */
export async function getAllPerformanceRecords(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllPerformanceRecordsParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPagePerformanceRecordDto>('/performance-records', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /performance-records */
export async function createPerformanceRecord(
  body: API.PerformanceRecordDto,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPerformanceRecordDto>('/performance-records', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /performance-records/${param0} */
export async function getPerformanceRecordById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getPerformanceRecordByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoPerformanceRecordDto>(`/performance-records/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /performance-records/${param0} */
export async function updatePerformanceRecord(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updatePerformanceRecordParams,
  body: API.PerformanceRecordDto,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoPerformanceRecordDto>(`/performance-records/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /performance-records/${param0} */
export async function deletePerformanceRecord(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deletePerformanceRecordParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoObject>(`/performance-records/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /performance-records/employee/${param0} */
export async function getPerformanceRecordsByEmployeeId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getPerformanceRecordsByEmployeeIdParams,
  options?: { [key: string]: any },
) {
  const { employeeId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListPerformanceRecordDto>(
    `/performance-records/employee/${param0}`,
    {
      method: 'GET',
      params: { ...queryParams },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 GET /performance-records/period/${param0} */
export async function getPerformanceRecordsByPeriod(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getPerformanceRecordsByPeriodParams,
  options?: { [key: string]: any },
) {
  const { performancePeriod: param0, ...queryParams } = params;
  return request<API.ResponseDtoListPerformanceRecordDto>(`/performance-records/period/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}
