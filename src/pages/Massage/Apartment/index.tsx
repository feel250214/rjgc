import { getAllDepartments } from '@/services/backend/departmentController';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import React, { useRef } from 'react';

const DepartmentPage: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const columns: ProColumns<API.Department>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      valueType: 'text',
      width: 80,
      search: false,
    },
    {
      title: '部门编号',
      dataIndex: 'code',
      valueType: 'text',
    },
    {
      title: '部门名称',
      dataIndex: 'name',
      valueType: 'text',
    },
    {
      title: '上级部门ID',
      dataIndex: 'parentId',
      valueType: 'text',
      search: false,
    },
    {
      title: '负责人ID',
      dataIndex: 'managerId',
      valueType: 'text',
      search: false,
    },
    {
      title: '描述',
      dataIndex: 'description',
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
      <ProTable<API.Department>
        headerTitle="部门信息"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 80,
        }}
        request={async (params) => {
          const page = params.current ? params.current - 1 : 0;
          const size = params.pageSize || 10;
          const res = await getAllDepartments({
            page,
            size,
          } as API.getAllDepartmentsParams);
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

export default DepartmentPage;
