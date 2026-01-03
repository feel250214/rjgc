import { getLeaveApplicationsByEmployeeId } from '@/services/backend/leaveApplicationController';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import { message } from 'antd';
import React, { useRef } from 'react';

const IndividualLeavePage: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const { initialState } = useModel('@@initialState');
  const currentUser = initialState?.currentUser as any;
  const employeeId = currentUser?.id ?? currentUser?.user?.id;

  const columns: ProColumns<API.LeaveApplication>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      valueType: 'text',
      width: 80,
      search: false,
    },
    {
      title: '请假类型',
      dataIndex: 'type',
      valueType: 'text',
    },
    {
      title: '开始时间',
      dataIndex: 'startTime',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '结束时间',
      dataIndex: 'endTime',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '请假原因',
      dataIndex: 'reason',
      valueType: 'text',
      ellipsis: true,
      search: false,
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
  ];

  return (
    <PageContainer>
      <ProTable<API.LeaveApplication>
        headerTitle="我的请假记录"
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
          const res = await getLeaveApplicationsByEmployeeId({
            employeeId,
          } as API.getLeaveApplicationsByEmployeeIdParams);
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

export default IndividualLeavePage;
