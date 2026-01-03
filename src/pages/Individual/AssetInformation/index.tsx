import {getAssetApplicationsByEmployeeId} from '@/services/backend/assetApplicationController';
import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {PageContainer, ProTable} from '@ant-design/pro-components';
import {useModel} from '@umijs/max';
import {message} from 'antd';
import React, {useRef} from 'react';

const IndividualAssetInformationPage: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const {initialState} = useModel('@@initialState');
  const currentUser = initialState?.currentUser as any;
  const employeeId = currentUser?.id ?? currentUser?.user?.id;

  const columns: ProColumns<API.AssetApplicationDto>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      valueType: 'text',
      width: 80,
      search: false,
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
      title: '申请时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
      search: false,
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.AssetApplicationDto>
        headerTitle="我的资产申请"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 80,
        }}
        request={async (params) => {
          if (!employeeId) {
            message.error('未获取到当前用户信息');
            return {
              success: false,
              data: [],
              total: 0,
            };
          }
          const res = await getAssetApplicationsByEmployeeId({
            employeeId,
          } as API.getAssetApplicationsByEmployeeIdParams);
          const list = res.data || [];
          const page = params.current || 1;
          const pageSize = params.pageSize || 10;
          const start = (page - 1) * pageSize;
          const end = start + pageSize;
          return {
            success: true,
            data: list.slice(start, end),
            total: list.length,
          };
        }}
        columns={columns}
      />
    </PageContainer>
  );
};

export default IndividualAssetInformationPage;

