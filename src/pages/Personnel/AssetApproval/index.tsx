import { getAllAssetApplications } from '@/services/backend/assetApplicationController';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import React, { useRef } from 'react';

const AssetApprovalPage: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const columns: ProColumns<API.AssetApplicationDto>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      valueType: 'text',
      width: 80,
      search: false,
    },
    {
      title: '员工ID',
      dataIndex: 'employeeId',
      valueType: 'text',
    },
    {
      title: '资产ID',
      dataIndex: 'assetId',
      valueType: 'text',
    },
    {
      title: '申请数量',
      dataIndex: 'requestQuantity',
      valueType: 'digit',
    },
    {
      title: '用途',
      dataIndex: 'purpose',
      valueType: 'text',
      ellipsis: true,
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'text',
    },
    {
      title: '负责人意见',
      dataIndex: 'managerComment',
      valueType: 'text',
      ellipsis: true,
      search: false,
    },
    {
      title: '管理员意见',
      dataIndex: 'adminComment',
      valueType: 'text',
      ellipsis: true,
      search: false,
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
      <ProTable<API.AssetApplicationDto>
        headerTitle="资产审批"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 80,
        }}
        request={async (params) => {
          const page = params.current ? params.current - 1 : 0;
          const size = params.pageSize || 10;
          const res = await getAllAssetApplications({
            page,
            size,
          } as API.getAllAssetApplicationsParams);
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

export default AssetApprovalPage;
