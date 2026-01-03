// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /departments */
export async function getAllDepartments(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllDepartmentsParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPageDepartment>('/departments', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /departments */
export async function createDepartment(body: API.Department, options?: { [key: string]: any }) {
  return request<API.ResponseDtoDepartment>('/departments', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /departments/${param0} */
export async function getDepartmentById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getDepartmentByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoDepartment>(`/departments/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /departments/${param0} */
export async function updateDepartment(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateDepartmentParams,
  body: API.Department,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoDepartment>(`/departments/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /departments/${param0} */
export async function deleteDepartment(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteDepartmentParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoObject>(`/departments/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /departments/all */
export async function getAllDepartments1(options?: { [key: string]: any }) {
  return request<API.ResponseDtoListDepartment>('/departments/all', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /departments/child/${param0} */
export async function getChildDepartments(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getChildDepartmentsParams,
  options?: { [key: string]: any },
) {
  const { parentId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListDepartment>(`/departments/child/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /departments/transfer-employees */
export async function transferEmployees(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.transferEmployeesParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoObject>('/departments/transfer-employees', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}
