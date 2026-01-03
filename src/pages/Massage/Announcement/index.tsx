import { getAllAnnouncements } from '@/services/backend/announcementController';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import { Tag } from 'antd';
import React, { useRef } from 'react';

const AnnouncementPage: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const columns: ProColumns<API.Announcement>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      valueType: 'text',
      width: 80,
      search: false,
    },
    {
      title: '标题',
      dataIndex: 'title',
      valueType: 'text',
    },
    {
      title: '类型',
      dataIndex: 'type',
      valueType: 'text',
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'text',
    },
    {
      title: '生效开始时间',
      dataIndex: 'validFrom',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '生效结束时间',
      dataIndex: 'validTo',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '发布范围',
      dataIndex: 'publishScope',
      valueType: 'text',
      search: false,
    },
    {
      title: '发布人ID',
      dataIndex: 'publisherId',
      valueType: 'text',
      search: false,
    },
    {
      title: '发布时间',
      dataIndex: 'publishTime',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '内容',
      dataIndex: 'content',
      valueType: 'text',
      ellipsis: true,
      render: (_, record) => <span title={record.content}>{record.content}</span>,
      search: false,
    },
    {
      title: '当前有效',
      dataIndex: 'current',
      valueType: 'text',
      render: (_, record) => {
        const now = new Date().getTime();
        const start = record.validFrom ? new Date(record.validFrom).getTime() : 0;
        const end = record.validTo ? new Date(record.validTo).getTime() : 0;
        const isValid = record.status === '已发布' && now >= start && now <= end;
        return isValid ? <Tag color="green">有效</Tag> : <Tag>无效</Tag>;
      },
      search: false,
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.Announcement>
        headerTitle="公告信息"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 80,
        }}
        request={async (params) => {
          const page = params.current ? params.current - 1 : 0;
          const size = params.pageSize || 10;
          const res = await getAllAnnouncements({
            page,
            size,
          } as API.getAllAnnouncementsParams);
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

export default AnnouncementPage;
