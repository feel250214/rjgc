import { getAllAssetCatalogs } from '@/services/backend/assetCatalogController';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import React, { useRef } from 'react';

const AssetInformationPage: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const columns: ProColumns<API.AssetCatalogDto>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      valueType: 'text',
      width: 80,
      search: false,
    },
    {
      title: '资产类型',
      dataIndex: 'type',
      valueType: 'text',
    },
    {
      title: '资产名称',
      dataIndex: 'name',
      valueType: 'text',
    },
    {
      title: '描述',
      dataIndex: 'description',
      valueType: 'text',
      ellipsis: true,
      search: false,
    },
    {
      title: '库存数量',
      dataIndex: 'stockQuantity',
      valueType: 'digit',
    },
    {
      title: '单位',
      dataIndex: 'unit',
      valueType: 'text',
      search: false,
    },
    {
      title: '单价',
      dataIndex: 'price',
      valueType: 'money',
      search: false,
    },
    {
      title: '预算上限',
      dataIndex: 'budgetLimit',
      valueType: 'money',
      search: false,
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'text',
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      search: false,
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.AssetCatalogDto>
        headerTitle="资产信息"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 80,
        }}
        request={async (params) => {
          const page = params.current ? params.current - 1 : 0;
          const size = params.pageSize || 10;
          const res = await getAllAssetCatalogs({
            page,
            size,
          } as API.getAllAssetCatalogsParams);
          const pageData = res.data;
          return {
            success: true,
            data: pageData?.content || [],
            total: pageData?.totalElements || 0,
          };
        }}
        columns={columns}
      />
    </PageContainer>
  );
};

export default AssetInformationPage;
