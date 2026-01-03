// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /leave-applications */
export async function getAllLeaveApplications(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllLeaveApplicationsParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPageLeaveApplication>('/leave-applications', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /leave-applications */
export async function createLeaveApplication(
  body: API.LeaveApplication,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoLeaveApplication>('/leave-applications', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /leave-applications/${param0} */
export async function getLeaveApplicationById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getLeaveApplicationByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoLeaveApplication>(`/leave-applications/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /leave-applications/${param0}/admin-approve */
export async function adminApproveLeaveApplication(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.adminApproveLeaveApplicationParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoLeaveApplication>(`/leave-applications/${param0}/admin-approve`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /leave-applications/${param0}/approval-records */
export async function getApprovalRecords(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApprovalRecordsParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoListApprovalRecord>(
    `/leave-applications/${param0}/approval-records`,
    {
      method: 'GET',
      params: { ...queryParams },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 POST /leave-applications/${param0}/manager-approve */
export async function managerApproveLeaveApplication(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.managerApproveLeaveApplicationParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoLeaveApplication>(`/leave-applications/${param0}/manager-approve`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /leave-applications/employee/${param0} */
export async function getLeaveApplicationsByEmployeeId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getLeaveApplicationsByEmployeeIdParams,
  options?: { [key: string]: any },
) {
  const { employeeId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListLeaveApplication>(`/leave-applications/employee/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}
