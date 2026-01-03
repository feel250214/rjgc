import {
  getAssetApplicationsByEmployeeId,
  getAssetDistributions,
} from '@/services/backend/assetApplicationController';
import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {PageContainer, ProTable} from '@ant-design/pro-components';
import {useModel} from '@umijs/max';
import {message} from 'antd';
import React, {useRef} from 'react';

const StaffAssetPage: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const {initialState} = useModel('@@initialState');
  const currentUser = initialState?.currentUser as any;
  const employeeId = currentUser?.id ?? currentUser?.user?.id;

  const columns: ProColumns<API.AssetDistribution>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      valueType: 'text',
      width: 80,
      search: false,
    },
    {
      title: '申请ID',
      dataIndex: 'applicationId',
      valueType: 'text',
    },
    {
      title: '资产ID',
      dataIndex: 'assetId',
      valueType: 'text',
    },
    {
      title: '发放数量',
      dataIndex: 'distributionQuantity',
      valueType: 'digit',
    },
    {
      title: '发放时间',
      dataIndex: 'distributionTime',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '领取状态',
      dataIndex: 'receiveStatus',
      valueType: 'text',
    },
    {
      title: '员工反馈',
      dataIndex: 'employeeFeedback',
      valueType: 'text',
      ellipsis: true,
      search: false,
    },
    {
      title: '管理员处理结果',
      dataIndex: 'adminProcessResult',
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
  ];

  return (
    <PageContainer>
      <ProTable<API.AssetDistribution>
        headerTitle="员工资产"
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
          const applicationsRes = await getAssetApplicationsByEmployeeId({
            employeeId,
          } as API.getAssetApplicationsByEmployeeIdParams);
          const applications = applicationsRes.data || [];
          if (!applications.length) {
            return {
              success: true,
              data: [],
              total: 0,
            };
          }
          const distributionsList = await Promise.all(
            applications
              .filter((item) => item.id !== undefined)
              .map((item) =>
                getAssetDistributions({
                  id: item.id as number,
                } as API.getAssetDistributionsParams),
              ),
          );
          const allDistributions =
            distributionsList.flatMap((res) => res.data || []) || [];
          const page = params.current || 1;
          const pageSize = params.pageSize || 10;
          const start = (page - 1) * pageSize;
          const end = start + pageSize;
          return {
            success: true,
            data: allDistributions.slice(start, end),
            total: allDistributions.length,
          };
        }}
        columns={columns}
      />
    </PageContainer>
  );
};

export default StaffAssetPage;

