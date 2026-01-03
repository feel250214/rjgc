// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /financial-expenses */
export async function getAllFinancialExpenses(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllFinancialExpensesParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPageFinancialExpense>('/financial-expenses', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /financial-expenses */
export async function createFinancialExpense(
  body: API.FinancialExpense,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoFinancialExpense>('/financial-expenses', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /financial-expenses/${param0} */
export async function getFinancialExpenseById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getFinancialExpenseByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoFinancialExpense>(`/financial-expenses/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /financial-expenses/${param0} */
export async function updateFinancialExpense(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateFinancialExpenseParams,
  body: API.FinancialExpense,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoFinancialExpense>(`/financial-expenses/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /financial-expenses/${param0} */
export async function deleteFinancialExpense(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteFinancialExpenseParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoObject>(`/financial-expenses/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /financial-expenses/creator/${param0} */
export async function getFinancialExpensesByCreatorId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getFinancialExpensesByCreatorIdParams,
  options?: { [key: string]: any },
) {
  const { creatorId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListFinancialExpense>(`/financial-expenses/creator/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /financial-expenses/date-range */
export async function getFinancialExpensesByDateRange(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getFinancialExpensesByDateRangeParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoListFinancialExpense>('/financial-expenses/date-range', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /financial-expenses/type/${param0} */
export async function getFinancialExpensesByType(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getFinancialExpensesByTypeParams,
  options?: { [key: string]: any },
) {
  const { type: param0, ...queryParams } = params;
  return request<API.ResponseDtoListFinancialExpense>(`/financial-expenses/type/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}
