// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /positions */
export async function getAllPositions(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllPositionsParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPagePosition>('/positions', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /positions */
export async function createPosition(body: API.Position, options?: { [key: string]: any }) {
  return request<API.ResponseDtoPosition>('/positions', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /positions/${param0} */
export async function getPositionById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getPositionByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoPosition>(`/positions/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /positions/${param0} */
export async function updatePosition(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updatePositionParams,
  body: API.Position,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoPosition>(`/positions/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /positions/${param0} */
export async function deletePosition(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deletePositionParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoObject>(`/positions/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /positions/all */
export async function getAllPositions1(options?: { [key: string]: any }) {
  return request<API.ResponseDtoListPosition>('/positions/all', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /positions/department/${param0} */
export async function getPositionsByDepartmentId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getPositionsByDepartmentIdParams,
  options?: { [key: string]: any },
) {
  const { departmentId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListPosition>(`/positions/department/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}
