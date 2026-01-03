// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /employees */
export async function getAllEmployees(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllEmployeesParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPageEmployeeDto>('/employees', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /employees */
export async function createEmployee(body: API.Employee, options?: { [key: string]: any }) {
  return request<API.ResponseDtoEmployeeDto>('/employees', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /employees/${param0} */
export async function getEmployeeById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getEmployeeByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoEmployeeDto>(`/employees/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /employees/${param0} */
export async function updateEmployee(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateEmployeeParams,
  body: API.EmployeeDto,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoEmployeeDto>(`/employees/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /employees/${param0} */
export async function deleteEmployee(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteEmployeeParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoObject>(`/employees/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /employees/all */
export async function getAllEmployees1(options?: { [key: string]: any }) {
  return request<API.ResponseDtoListEmployeeDto>('/employees/all', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /employees/department/${param0} */
export async function getEmployeesByDepartmentId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getEmployeesByDepartmentIdParams,
  options?: { [key: string]: any },
) {
  const { departmentId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListEmployeeDto>(`/employees/department/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /employees/profile/${param0} */
export async function updateEmployeeProfile(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateEmployeeProfileParams,
  body: API.EmployeeDto,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoEmployeeDto>(`/employees/profile/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}
