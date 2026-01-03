// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /salary-details */
export async function getAllSalaryDetails(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllSalaryDetailsParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPageSalaryDetail>('/salary-details', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /salary-details */
export async function createSalaryDetail(body: API.SalaryDetail, options?: { [key: string]: any }) {
  return request<API.ResponseDtoSalaryDetail>('/salary-details', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /salary-details/${param0} */
export async function getSalaryDetailById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSalaryDetailByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoSalaryDetail>(`/salary-details/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /salary-details/${param0} */
export async function updateSalaryDetail(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateSalaryDetailParams,
  body: API.SalaryDetail,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoSalaryDetail>(`/salary-details/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /salary-details/${param0} */
export async function deleteSalaryDetail(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteSalaryDetailParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoObject>(`/salary-details/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /salary-details/${param0}/audit */
export async function auditSalaryDetail(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.auditSalaryDetailParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoSalaryDetail>(`/salary-details/${param0}/audit`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /salary-details/${param0}/issue */
export async function issueSalary(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.issueSalaryParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoSalaryDetail>(`/salary-details/${param0}/issue`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /salary-details/employee/${param0} */
export async function getSalaryDetailsByEmployeeId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSalaryDetailsByEmployeeIdParams,
  options?: { [key: string]: any },
) {
  const { employeeId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListSalaryDetail>(`/salary-details/employee/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /salary-details/period/${param0} */
export async function getSalaryDetailsByPeriod(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSalaryDetailsByPeriodParams,
  options?: { [key: string]: any },
) {
  const { salaryPeriod: param0, ...queryParams } = params;
  return request<API.ResponseDtoListSalaryDetail>(`/salary-details/period/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}
