import { getAllSalaryDetails } from '@/services/backend/salaryDetailController';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import React, { useRef } from 'react';

const PersonnelSalaryPage: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const columns: ProColumns<API.SalaryDetail>[] = [
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
      title: '薪资周期',
      dataIndex: 'salaryPeriod',
      valueType: 'text',
    },
    {
      title: '基本工资',
      dataIndex: 'basicSalary',
      valueType: 'money',
    },
    {
      title: '绩效奖金',
      dataIndex: 'performanceBonus',
      valueType: 'money',
      search: false,
    },
    {
      title: '补贴',
      dataIndex: 'allowance',
      valueType: 'money',
      search: false,
    },
    {
      title: '加班费',
      dataIndex: 'overtimePay',
      valueType: 'money',
      search: false,
    },
    {
      title: '社保扣除',
      dataIndex: 'socialSecurityDeduction',
      valueType: 'money',
      search: false,
    },
    {
      title: '公积金扣除',
      dataIndex: 'housingFundDeduction',
      valueType: 'money',
      search: false,
    },
    {
      title: '个税扣除',
      dataIndex: 'taxDeduction',
      valueType: 'money',
      search: false,
    },
    {
      title: '其他扣减',
      dataIndex: 'otherDeductions',
      valueType: 'money',
      search: false,
    },
    {
      title: '实发工资',
      dataIndex: 'netSalary',
      valueType: 'money',
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'text',
    },
    {
      title: '审核人ID',
      dataIndex: 'auditorId',
      valueType: 'text',
      search: false,
    },
    {
      title: '审核时间',
      dataIndex: 'auditTime',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '发放时间',
      dataIndex: 'paymentTime',
      valueType: 'dateTime',
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
      <ProTable<API.SalaryDetail>
        headerTitle="薪资信息"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 80,
        }}
        request={async (params) => {
          const page = params.current ? params.current - 1 : 0;
          const size = params.pageSize || 10;
          const res = await getAllSalaryDetails({
            page,
            size,
          } as API.getAllSalaryDetailsParams);
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

export default PersonnelSalaryPage;
