import { getAllLeaveApplications } from '@/services/backend/leaveApplicationController';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import React, { useRef } from 'react';

const LeaveApprovalPage: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const columns: ProColumns<API.LeaveApplication>[] = [
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
    {
      title: '更新时间',
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      search: false,
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.LeaveApplication>
        headerTitle="请假审批"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 80,
        }}
        request={async (params) => {
          const page = params.current ? params.current - 1 : 0;
          const size = params.pageSize || 10;
          const res = await getAllLeaveApplications({
            page,
            size,
          } as API.getAllLeaveApplicationsParams);
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

export default LeaveApprovalPage;
