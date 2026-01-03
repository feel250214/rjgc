// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /asset-applications */
export async function getAllAssetApplications(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllAssetApplicationsParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPageAssetApplicationDto>('/asset-applications', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /asset-applications */
export async function createAssetApplication(
  body: API.AssetApplicationDto,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoAssetApplicationDto>('/asset-applications', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /asset-applications/${param0} */
export async function getAssetApplicationById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAssetApplicationByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoAssetApplicationDto>(`/asset-applications/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /asset-applications/${param0}/admin-approve */
export async function adminApproveAssetApplication(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.adminApproveAssetApplicationParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoAssetApplicationDto>(
    `/asset-applications/${param0}/admin-approve`,
    {
      method: 'POST',
      params: {
        ...queryParams,
      },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 GET /asset-applications/${param0}/approval-records */
export async function getApprovalRecords1(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApprovalRecords1Params,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoListApprovalRecord>(
    `/asset-applications/${param0}/approval-records`,
    {
      method: 'GET',
      params: { ...queryParams },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 GET /asset-applications/${param0}/asset-distributions */
export async function getAssetDistributions(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAssetDistributionsParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoListAssetDistribution>(
    `/asset-applications/${param0}/asset-distributions`,
    {
      method: 'GET',
      params: { ...queryParams },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 POST /asset-applications/${param0}/manager-approve */
export async function managerApproveAssetApplication(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.managerApproveAssetApplicationParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoAssetApplicationDto>(
    `/asset-applications/${param0}/manager-approve`,
    {
      method: 'POST',
      params: {
        ...queryParams,
      },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 POST /asset-applications/asset-distributions/${param0}/confirm-receive */
export async function confirmReceiveAsset(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.confirmReceiveAssetParams,
  options?: { [key: string]: any },
) {
  const { distributionId: param0, ...queryParams } = params;
  return request<API.ResponseDtoAssetDistribution>(
    `/asset-applications/asset-distributions/${param0}/confirm-receive`,
    {
      method: 'POST',
      params: { ...queryParams },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 POST /asset-applications/asset-distributions/${param0}/process-objection */
export async function processAssetObjection(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.processAssetObjectionParams,
  options?: { [key: string]: any },
) {
  const { distributionId: param0, ...queryParams } = params;
  return request<API.ResponseDtoAssetDistribution>(
    `/asset-applications/asset-distributions/${param0}/process-objection`,
    {
      method: 'POST',
      params: {
        ...queryParams,
      },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 POST /asset-applications/asset-distributions/${param0}/raise-objection */
export async function raiseAssetObjection(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.raiseAssetObjectionParams,
  options?: { [key: string]: any },
) {
  const { distributionId: param0, ...queryParams } = params;
  return request<API.ResponseDtoAssetDistribution>(
    `/asset-applications/asset-distributions/${param0}/raise-objection`,
    {
      method: 'POST',
      params: {
        ...queryParams,
      },
      ...(options || {}),
    },
  );
}

/** 此处后端没有提供注释 GET /asset-applications/employee/${param0} */
export async function getAssetApplicationsByEmployeeId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAssetApplicationsByEmployeeIdParams,
  options?: { [key: string]: any },
) {
  const { employeeId: param0, ...queryParams } = params;
  return request<API.ResponseDtoListAssetApplicationDto>(`/asset-applications/employee/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}
