import { getAllFinancialExpenses } from '@/services/backend/financialExpenseController';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import React, { useRef } from 'react';

const FinancePage: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const columns: ProColumns<API.FinancialExpense>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      valueType: 'text',
      width: 80,
      search: false,
    },
    {
      title: '支出类型',
      dataIndex: 'type',
      valueType: 'text',
    },
    {
      title: '金额',
      dataIndex: 'amount',
      valueType: 'money',
    },
    {
      title: '支出日期',
      dataIndex: 'expenseDate',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '用途',
      dataIndex: 'purpose',
      valueType: 'text',
      ellipsis: true,
    },
    {
      title: '关联ID',
      dataIndex: 'relatedId',
      valueType: 'text',
      search: false,
    },
    {
      title: '关联类型',
      dataIndex: 'relatedType',
      valueType: 'text',
      search: false,
    },
    {
      title: '支付方式',
      dataIndex: 'paymentMethod',
      valueType: 'text',
      search: false,
    },
    {
      title: '备注',
      dataIndex: 'remark',
      valueType: 'text',
      ellipsis: true,
      search: false,
    },
    {
      title: '创建人ID',
      dataIndex: 'creatorId',
      valueType: 'text',
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
      <ProTable<API.FinancialExpense>
        headerTitle="财务支出"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 80,
        }}
        request={async (params) => {
          const page = params.current ? params.current - 1 : 0;
          const size = params.pageSize || 10;
          const res = await getAllFinancialExpenses({
            page,
            size,
          } as API.getAllFinancialExpensesParams);
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

export default FinancePage;
