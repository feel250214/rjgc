import {getSalaryDetailsByEmployeeId} from '@/services/backend/salaryDetailController';
import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {PageContainer, ProTable} from '@ant-design/pro-components';
import {useModel} from '@umijs/max';
import {message} from 'antd';
import React, {useRef} from 'react';

const IndividualSalaryPage: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const {initialState} = useModel('@@initialState');
  const currentUser = initialState?.currentUser as any;
  const employeeId = currentUser?.id ?? currentUser?.user?.id;

  const columns: ProColumns<API.SalaryDetail>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      valueType: 'text',
      width: 80,
      search: false,
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
      title: '发放时间',
      dataIndex: 'paymentTime',
      valueType: 'dateTime',
      search: false,
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.SalaryDetail>
        headerTitle="我的薪资"
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
          const res = await getSalaryDetailsByEmployeeId({
            employeeId,
          } as API.getSalaryDetailsByEmployeeIdParams);
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

export default IndividualSalaryPage;

