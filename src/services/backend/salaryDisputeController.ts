// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /salary-disputes */
export async function getAllSalaryDisputes(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllSalaryDisputesParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPageSalaryDispute>('/salary-disputes', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /salary-disputes */
export async function createSalaryDispute(
  body: API.SalaryDispute,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoSalaryDispute>('/salary-disputes', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /salary-disputes/${param0} */
export async function getSalaryDisputeById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSalaryDisputeByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoSalaryDispute>(`/salary-disputes/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /salary-disputes/${param0} */
export async function deleteSalaryDispute(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteSalaryDisputeParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoObject>(`/salary-disputes/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /salary-disputes/${param0}/respond */
export async function respondToSalaryDispute(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.respondToSalaryDisputeParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoSalaryDispute>(`/salary-disputes/${param0}/respond`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /salary-disputes/employee/${param0} */
export async function getSalaryDisputesByEmployeeId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSalaryDisputesByEmployeeIdParams,
  options?: { [key: string]: any },
) {
  const { employeeId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListSalaryDispute>(`/salary-disputes/employee/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /salary-disputes/salary-detail/${param0} */
export async function getSalaryDisputesBySalaryDetailId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSalaryDisputesBySalaryDetailIdParams,
  options?: { [key: string]: any },
) {
  const { salaryDetailId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListSalaryDispute>(`/salary-disputes/salary-detail/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /salary-disputes/status/${param0} */
export async function getSalaryDisputesByStatus(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSalaryDisputesByStatusParams,
  options?: { [key: string]: any },
) {
  const { status: param0, ...queryParams } = params;
  return request<API.ResponseDtoListSalaryDispute>(`/salary-disputes/status/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}
