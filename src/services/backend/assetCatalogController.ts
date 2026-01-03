// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /asset-catalogs */
export async function getAllAssetCatalogs(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAllAssetCatalogsParams,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoPageAssetCatalogDto>('/asset-catalogs', {
    method: 'GET',
    params: {
      // size has a default value: 10
      size: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /asset-catalogs */
export async function createAssetCatalog(
  body: API.AssetCatalogDto,
  options?: { [key: string]: any },
) {
  return request<API.ResponseDtoAssetCatalogDto>('/asset-catalogs', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /asset-catalogs/${param0} */
export async function getAssetCatalogById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAssetCatalogByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoAssetCatalogDto>(`/asset-catalogs/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /asset-catalogs/${param0} */
export async function updateAssetCatalog(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateAssetCatalogParams,
  body: API.AssetCatalogDto,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoAssetCatalogDto>(`/asset-catalogs/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /asset-catalogs/${param0} */
export async function deleteAssetCatalog(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteAssetCatalogParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResponseDtoObject>(`/asset-catalogs/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /asset-catalogs/all */
export async function getAllAssetCatalogs1(options?: { [key: string]: any }) {
  return request<API.ResponseDtoListAssetCatalogDto>('/asset-catalogs/all', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /asset-catalogs/type/${param0} */
export async function getAssetCatalogsByType(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAssetCatalogsByTypeParams,
  options?: { [key: string]: any },
) {
  const { type: param0, ...queryParams } = params;
  return request<API.ResponseDtoListAssetCatalogDto>(`/asset-catalogs/type/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}
