import { getAllAnnouncements } from '@/services/backend/announcementController';
import { getAllDepartments1 } from '@/services/backend/departmentController';
import { getAllEmployees1 } from '@/services/backend/employeeController';
import { getFinancialExpensesByDateRange } from '@/services/backend/financialExpenseController';
import { getSalaryDetailsByPeriod } from '@/services/backend/salaryDetailController';
import { Column, Line, Pie } from '@ant-design/charts';
import { PageContainer } from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import {
  Button,
  Card,
  Col,
  DatePicker,
  Empty,
  List,
  message,
  Row,
  Select,
  Space,
  Spin,
  Tag,
  theme,
  Typography,
} from 'antd';
import moment, { Moment } from 'moment';
import React, { useEffect, useMemo, useRef, useState } from 'react';

const { Text } = Typography;

export type ExpenseStat = {
  type: string;
  amount: number;
};

export type SalaryStat = {
  department: string;
  total: number;
};

type ExpenseTrendPoint = {
  date: string;
  value: number;
  type: '实际支出' | '预算支出';
};

type DepartmentExpenseBarPoint = {
  department: string;
  category: string;
  amount: number;
};

export function buildExpenseStatsFromFinancialExpenses(
  list: API.FinancialExpense[],
): ExpenseStat[] {
  const map = new Map<string, number>();
  list.forEach((item) => {
    const key = item.type || '其他';
    const amount = item.amount || 0;
    map.set(key, (map.get(key) || 0) + amount);
  });
  return Array.from(map.entries()).map(([type, amount]) => ({
    type,
    amount,
  }));
}

export function buildSalaryStatsFromDetails(
  salaryList: API.SalaryDetail[],
  employeeList: API.EmployeeDto[],
): SalaryStat[] {
  const departmentByEmployeeId = new Map<number, string>();
  employeeList.forEach((emp) => {
    if (typeof emp.id === 'number') {
      departmentByEmployeeId.set(emp.id, emp.department || '未分配');
    }
  });

  const map = new Map<string, number>();
  salaryList.forEach((detail) => {
    const employeeId = detail.employeeId;
    if (!employeeId) {
      return;
    }
    const department = departmentByEmployeeId.get(employeeId) || '未分配';
    const amount = detail.netSalary || 0;
    map.set(department, (map.get(department) || 0) + amount);
  });

  return Array.from(map.entries()).map(([department, total]) => ({
    department,
    total,
  }));
}

const Welcome: React.FC = () => {
  const { token } = theme.useToken();
  const { initialState } = useModel('@@initialState');
  const currentUser = initialState?.currentUser as any;
  const userName =
    currentUser?.name ||
    currentUser?.user?.name ||
    currentUser?.username ||
    currentUser?.user?.username ||
    '管理员';

  const [announcements, setAnnouncements] = useState<API.Announcement[]>([]);
  const [announcementLoading, setAnnouncementLoading] = useState(false);

  const [selectedMonth, setSelectedMonth] = useState<Moment>(moment());
  const [expenseLoading, setExpenseLoading] = useState(false);
  const [expenseStats, setExpenseStats] = useState<ExpenseStat[]>([]);

  const [salaryLoading, setSalaryLoading] = useState(false);
  const [salaryStats, setSalaryStats] = useState<SalaryStat[]>([]);

  const [expenseTrendLoading, setExpenseTrendLoading] = useState(false);
  const [expenseTrendData, setExpenseTrendData] = useState<ExpenseTrendPoint[]>([]);
  const [expenseBudgetLimit, setExpenseBudgetLimit] = useState<number | undefined>(undefined);

  const [deptExpenseLoading, setDeptExpenseLoading] = useState(false);
  const [deptExpenseData, setDeptExpenseData] = useState<DepartmentExpenseBarPoint[]>([]);
  const [availableExpenseCategories, setAvailableExpenseCategories] = useState<string[]>([]);
  const [selectedExpenseCategories, setSelectedExpenseCategories] = useState<string[]>([]);

  const expenseStatsCache = useRef<Record<string, ExpenseStat[]>>({});
  const salaryStatsCache = useRef<Record<string, SalaryStat[]>>({});

  const totalExpenseAmount = useMemo(
    () => expenseStats.reduce((sum, item) => sum + item.amount, 0),
    [expenseStats],
  );

  const filteredDeptExpenseData = useMemo(() => {
    if (!selectedExpenseCategories.length) {
      return deptExpenseData;
    }
    return deptExpenseData.filter((item) => selectedExpenseCategories.includes(item.category));
  }, [deptExpenseData, selectedExpenseCategories]);

  const expensePieConfig = useMemo(
    () => ({
      data: expenseStats,
      angleField: 'amount',
      colorField: 'type',
      radius: 0.9,
      innerRadius: 0.5,
      legend: {
        position: 'right' as const,
      },
      label: {
        type: 'outer' as const,
        formatter: (datum: any) => {
          if (!datum) {
            return '';
          }
          const item = datum as Partial<ExpenseStat>;
          const type = item.type || '';
          const amount = typeof item.amount === 'number' ? item.amount : 0;
          if (!totalExpenseAmount) {
            return type ? `${type}: 0%` : '';
          }
          const percent = (amount / totalExpenseAmount) * 100;
          return `${type}: ${percent.toFixed(1)}%`;
        },
      },
      tooltip: {
        formatter: (datum: any) => {
          if (!datum) {
            return {
              name: '',
              value: '',
            };
          }
          const item = datum as Partial<ExpenseStat>;
          const type = item.type || '';
          const amount = typeof item.amount === 'number' ? item.amount : 0;
          return {
            name: type,
            value: `${amount.toFixed(2)} 元`,
          };
        },
      },
      interactions: [{ type: 'element-active' }],
      height: 260,
      autoFit: true,
      animation: {
        appear: {
          animation: 'scale-in-x',
          duration: 800,
        },
      },
    }),
    [expenseStats, totalExpenseAmount],
  );

  const salaryColumnConfig = useMemo(
    () => ({
      data: salaryStats,
      xField: 'department',
      yField: 'total',
      xAxis: {
        title: {
          text: '部门',
        },
      },
      yAxis: {
        title: {
          text: '薪资总额（元）',
        },
      },
      columnStyle: {
        radius: [4, 4, 0, 0] as [number, number, number, number],
      },
      label: {
        position: 'middle' as const,
        style: {
          fill: '#fff',
          opacity: 0.9,
        },
        formatter: (datum: any) => {
          return `${Math.round(datum.total)}`;
        },
      },
      tooltip: {
        formatter: (datum: any) => ({
          name: datum.department,
          value: `${datum.total.toFixed(2)} 元`,
        }),
      },
      height: 260,
      autoFit: true,
      animation: {
        appear: {
          animation: 'scale-in-y',
          duration: 800,
        },
      },
    }),
    [salaryStats],
  );

  const expenseTrendConfig = useMemo(
    () => ({
      data: expenseTrendData,
      xField: 'date',
      yField: 'value',
      seriesField: 'type',
      smooth: true,
      xAxis: {
        title: {
          text: '月份',
        },
      },
      yAxis: {
        title: {
          text: '支出金额（元）',
        },
      },
      tooltip: {
        formatter: (datum: any) => ({
          name: datum.type,
          value: `${datum.value.toFixed(2)} 元`,
        }),
      },
      slider: {
        start: 0,
        end: 1,
      },
      annotations:
        typeof expenseBudgetLimit === 'number'
          ? [
              {
                type: 'line',
                start: ['min', expenseBudgetLimit],
                end: ['max', expenseBudgetLimit],
                text: {
                  content: '预算参考线',
                  style: {
                    fill: token.colorError,
                  },
                },
                style: {
                  stroke: token.colorError,
                  lineDash: [4, 4],
                },
              },
            ]
          : [],
      height: 260,
      autoFit: true,
      animation: {
        appear: {
          animation: 'path-in',
          duration: 800,
        },
      },
    }),
    [expenseTrendData, expenseBudgetLimit, token.colorError],
  );

  const deptExpenseBarConfig = useMemo(
    () => ({
      data: filteredDeptExpenseData,
      xField: 'department',
      yField: 'amount',
      seriesField: 'category',
      isGroup: true as const,
      xAxis: {
        label: {
          autoRotate: true,
        },
        title: {
          text: '部门',
        },
      },
      yAxis: {
        title: {
          text: '支出金额（元）',
        },
      },
      legend: {
        position: 'top' as const,
      },
      columnStyle: {
        radius: [4, 4, 0, 0] as [number, number, number, number],
      },
      tooltip: {
        formatter: (datum: any) => ({
          name: `${datum.department} - ${datum.category}`,
          value: `${datum.amount.toFixed(2)} 元`,
        }),
      },
      height: 260,
      autoFit: true,
      animation: {
        appear: {
          animation: 'scale-in-y',
          duration: 800,
        },
      },
    }),
    [filteredDeptExpenseData],
  );

  useEffect(() => {
    const fetchAnnouncements = async () => {
      setAnnouncementLoading(true);
      try {
        const res = await getAllAnnouncements({
          page: 0,
          size: 5,
        } as API.getAllAnnouncementsParams);
        const pageData = res.data as API.PageAnnouncement | undefined;
        setAnnouncements(pageData?.content || []);
      } catch (error) {
        const err = error as Error;
        message.error(err.message || '获取公告失败');
      } finally {
        setAnnouncementLoading(false);
      }
    };

    fetchAnnouncements();
  }, []);

  useEffect(() => {
    const fetchExpenseStats = async () => {
      const monthKey = selectedMonth.format('YYYY-MM');
      const cached = expenseStatsCache.current[monthKey];
      if (cached) {
        setExpenseStats(cached);
        return;
      }
      setExpenseLoading(true);
      try {
        const startDate = selectedMonth.startOf('month').format('YYYY-MM-DD');
        const endDate = selectedMonth.endOf('month').format('YYYY-MM-DD');
        const res = await getFinancialExpensesByDateRange({
          startDate,
          endDate,
        } as API.getFinancialExpensesByDateRangeParams);
        const list = (res.data || []) as API.FinancialExpense[];
        const result = buildExpenseStatsFromFinancialExpenses(list);
        expenseStatsCache.current[monthKey] = result;
        setExpenseStats(result);
      } catch (error) {
        const err = error as Error;
        message.error(err.message || '获取财务支出数据失败');
      } finally {
        setExpenseLoading(false);
      }
    };

    const fetchSalaryStats = async () => {
      const monthKey = selectedMonth.format('YYYY-MM');
      const cached = salaryStatsCache.current[monthKey];
      if (cached) {
        setSalaryStats(cached);
        return;
      }
      setSalaryLoading(true);
      try {
        const salaryPeriod = selectedMonth.format('YYYY-MM');
        const [salaryRes, employeeRes] = await Promise.all([
          getSalaryDetailsByPeriod({
            salaryPeriod,
          } as API.getSalaryDetailsByPeriodParams),
          getAllEmployees1(),
        ]);
        const salaryList = (salaryRes.data || []) as API.SalaryDetail[];
        const employeeList = (employeeRes.data || []) as API.EmployeeDto[];
        const result = buildSalaryStatsFromDetails(salaryList, employeeList);
        salaryStatsCache.current[monthKey] = result;
        setSalaryStats(result);
      } catch (error) {
        const err = error as Error;
        message.error(err.message || '获取薪资统计数据失败');
      } finally {
        setSalaryLoading(false);
      }
    };

    const fetchExpenseTrend = async () => {
      setExpenseTrendLoading(true);
      try {
        const endDate = selectedMonth.endOf('month').format('YYYY-MM-DD');
        const startDate = selectedMonth
          .clone()
          .subtract(5, 'month')
          .startOf('month')
          .format('YYYY-MM-DD');
        const res = await getFinancialExpensesByDateRange({
          startDate,
          endDate,
        } as API.getFinancialExpensesByDateRangeParams);
        const list = (res.data || []) as API.FinancialExpense[];
        const monthMap = new Map<string, number>();
        list.forEach((item) => {
          if (!item.expenseDate || !item.amount) {
            return;
          }
          const monthKey = moment(item.expenseDate).format('YYYY-MM');
          monthMap.set(monthKey, (monthMap.get(monthKey) || 0) + item.amount);
        });
        const months = Array.from(monthMap.keys()).sort();
        const data: ExpenseTrendPoint[] = [];
        const actualValues: number[] = [];
        months.forEach((month) => {
          const actual = monthMap.get(month) || 0;
          actualValues.push(actual);
          data.push({
            date: month,
            value: actual,
            type: '实际支出',
          });
          data.push({
            date: month,
            value: Math.round(actual * 1.1 * 100) / 100,
            type: '预算支出',
          });
        });
        if (actualValues.length) {
          const totalActual = actualValues.reduce((sum, value) => sum + value, 0);
          const avgActual = totalActual / actualValues.length;
          setExpenseBudgetLimit(Math.round(avgActual * 1.2 * 100) / 100);
        } else {
          setExpenseBudgetLimit(undefined);
        }
        setExpenseTrendData(data);
      } catch (error) {
        const err = error as Error;
        message.error(err.message || '获取财务支出趋势数据失败');
        setExpenseTrendData([]);
        setExpenseBudgetLimit(undefined);
      } finally {
        setExpenseTrendLoading(false);
      }
    };

    const fetchDeptExpenseStats = async () => {
      setDeptExpenseLoading(true);
      try {
        const startDate = selectedMonth.startOf('month').format('YYYY-MM-DD');
        const endDate = selectedMonth.endOf('month').format('YYYY-MM-DD');
        const [expenseRes, departmentRes] = await Promise.all([
          getFinancialExpensesByDateRange({
            startDate,
            endDate,
          } as API.getFinancialExpensesByDateRangeParams),
          getAllDepartments1(),
        ]);
        const expenseList = (expenseRes.data || []) as API.FinancialExpense[];
        const departmentList = (departmentRes.data || []) as API.Department[];
        const departmentNameById = new Map<number, string>();
        departmentList.forEach((dept) => {
          if (typeof dept.id === 'number') {
            departmentNameById.set(dept.id, dept.name);
          }
        });
        const deptMap = new Map<string, Map<string, number>>();
        expenseList.forEach((item) => {
          const amount = item.amount || 0;
          let departmentName = '未分配';
          if (item.relatedType === 'DEPARTMENT' && typeof item.relatedId === 'number') {
            departmentName = departmentNameById.get(item.relatedId) || '未分配';
          }
          const category = item.type || '其他';
          const categoryMap = deptMap.get(departmentName) || new Map<string, number>();
          categoryMap.set(category, (categoryMap.get(category) || 0) + amount);
          deptMap.set(departmentName, categoryMap);
        });
        const result: DepartmentExpenseBarPoint[] = [];
        deptMap.forEach((categoryMap, department) => {
          categoryMap.forEach((amount, category) => {
            result.push({
              department,
              category,
              amount,
            });
          });
        });
        setDeptExpenseData(result);
        const categories = Array.from(new Set(result.map((item) => item.category)));
        setAvailableExpenseCategories(categories);
        setSelectedExpenseCategories(categories);
      } catch (error) {
        const err = error as Error;
        message.error(err.message || '获取部门财务支出数据失败');
        setDeptExpenseData([]);
        setAvailableExpenseCategories([]);
        setSelectedExpenseCategories([]);
      } finally {
        setDeptExpenseLoading(false);
      }
    };

    fetchExpenseStats();
    fetchSalaryStats();
    fetchExpenseTrend();
    fetchDeptExpenseStats();
  }, [selectedMonth]);

  return (
    <PageContainer>
      <Space direction="vertical" size="middle" style={{ width: '100%' }}>
        <Card>
          <div
            style={{
              fontSize: 20,
              marginBottom: 8,
            }}
          >
            您好，{userName}！欢迎使用本系统。
          </div>
          <Text type="secondary">
            今天是 {moment().format('YYYY年MM月DD日')}，祝您工作顺利，心情愉快。
          </Text>
        </Card>

        <Row gutter={16}>
          <Col xs={24} lg={24}>
            <Card title="公告列表">
              <Spin spinning={announcementLoading}>
                {announcements.length === 0 ? (
                  <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} description="暂无公告" />
                ) : (
                  <List
                    dataSource={announcements}
                    renderItem={(item) => (
                      <List.Item key={item.id}>
                        <List.Item.Meta
                          title={
                            <Space>
                              <span>{item.title}</span>
                              {item.type && <Tag color="blue">{item.type}</Tag>}
                            </Space>
                          }
                          description={
                            <Space direction="vertical" size={2}>
                              <Text ellipsis={{ tooltip: item.content }}>{item.content}</Text>
                              <Text type="secondary" style={{ fontSize: 12 }}>
                                生效时间：{item.validFrom?.slice(0, 10)} 至{' '}
                                {item.validTo?.slice(0, 10)}
                              </Text>
                            </Space>
                          }
                        />
                      </List.Item>
                    )}
                  />
                )}
              </Spin>
            </Card>
          </Col>
          <Col xs={24} lg={24}>
            <Card
              title="统计概览"
              extra={
                <Space>
                  <span>统计月份</span>
                  <DatePicker
                    picker="month"
                    allowClear={false}
                    value={selectedMonth}
                    onChange={(value) => {
                      setSelectedMonth(value || moment());
                    }}
                  />
                </Space>
              }
            >
              <Row gutter={16}>
                <Col xs={24} md={12}>
                  <Card
                    title="财务支出分布"
                    bordered={false}
                    bodyStyle={{ padding: 0, paddingTop: 8 }}
                  >
                    <Text type="secondary" style={{ fontSize: 12 }}>
                      统计维度：支出类型
                    </Text>
                    <Spin spinning={expenseLoading}>
                      {expenseStats.length === 0 ? (
                        <Empty
                          image={Empty.PRESENTED_IMAGE_SIMPLE}
                          description="暂无支出数据"
                          style={{ marginTop: 24 }}
                        />
                      ) : (
                        <div style={{ marginTop: 16 }}>
                          <Pie {...expensePieConfig} />
                        </div>
                      )}
                    </Spin>
                  </Card>
                </Col>
                <Col xs={24} md={12}>
                  <Card
                    title="部门薪资统计"
                    bordered={false}
                    bodyStyle={{ padding: 0, paddingTop: 8 }}
                  >
                    <Text type="secondary" style={{ fontSize: 12 }}>
                      统计维度：部门
                    </Text>
                    <Spin spinning={salaryLoading}>
                      {salaryStats.length === 0 ? (
                        <Empty
                          image={Empty.PRESENTED_IMAGE_SIMPLE}
                          description="暂无薪资数据"
                          style={{ marginTop: 24 }}
                        />
                      ) : (
                        <div style={{ marginTop: 16 }}>
                          <Column {...salaryColumnConfig} />
                        </div>
                      )}
                    </Spin>
                  </Card>
                </Col>
              </Row>
              <Row gutter={16} style={{ marginTop: 16 }}>
                <Col xs={24} md={12}>
                  <Card
                    title="部门财务支出趋势"
                    bordered={false}
                    bodyStyle={{ padding: 0, paddingTop: 8 }}
                  >
                    <Text type="secondary" style={{ fontSize: 12 }}>
                      统计维度：月份
                    </Text>
                    <Spin spinning={expenseTrendLoading}>
                      {expenseTrendData.length === 0 ? (
                        <Empty
                          image={Empty.PRESENTED_IMAGE_SIMPLE}
                          description="暂无趋势数据"
                          style={{ marginTop: 24 }}
                        />
                      ) : (
                        <div style={{ marginTop: 16 }}>
                          <Line {...expenseTrendConfig} />
                        </div>
                      )}
                    </Spin>
                  </Card>
                </Col>
                <Col xs={24} md={12}>
                  <Card
                    title="部门财务支出对比"
                    bordered={false}
                    bodyStyle={{ padding: 0, paddingTop: 8 }}
                  >
                    <Space
                      style={{
                        width: '100%',
                        justifyContent: 'space-between',
                        marginBottom: 8,
                      }}
                    >
                      <Text type="secondary" style={{ fontSize: 12 }}>
                        统计维度：部门与支出类型
                      </Text>
                      <Space size={8}>
                        <Select
                          mode="multiple"
                          allowClear
                          placeholder="筛选支出类型"
                          style={{ minWidth: 180 }}
                          value={selectedExpenseCategories}
                          onChange={(values) => setSelectedExpenseCategories(values)}
                          maxTagCount="responsive"
                          options={availableExpenseCategories.map((category) => ({
                            label: category,
                            value: category,
                          }))}
                        />
                        <Button
                          size="small"
                          onClick={() => setSelectedExpenseCategories(availableExpenseCategories)}
                        >
                          重置
                        </Button>
                        <Button
                          size="small"
                          type="primary"
                          onClick={() => {
                            if (!filteredDeptExpenseData.length) {
                              message.warning('暂无可导出的数据');
                              return;
                            }
                            const rows: string[][] = [
                              ['部门', '支出类型', '金额'],
                              ...filteredDeptExpenseData.map((item) => [
                                item.department,
                                item.category,
                                item.amount.toFixed(2),
                              ]),
                            ];
                            const csvContent = rows
                              .map((row) =>
                                row
                                  .map((value) => `"${String(value).replace(/"/g, '""')}"`)
                                  .join(','),
                              )
                              .join('\n');
                            const blob = new Blob([csvContent], {
                              type: 'text/csv;charset=utf-8;',
                            });
                            const url = window.URL.createObjectURL(blob);
                            const link = document.createElement('a');
                            link.href = url;
                            link.setAttribute(
                              'download',
                              `部门财务支出对比_${selectedMonth.format('YYYYMM')}.csv`,
                            );
                            document.body.appendChild(link);
                            link.click();
                            document.body.removeChild(link);
                            window.URL.revokeObjectURL(url);
                          }}
                        >
                          导出数据
                        </Button>
                      </Space>
                    </Space>
                    <Spin spinning={deptExpenseLoading}>
                      {filteredDeptExpenseData.length === 0 ? (
                        <Empty
                          image={Empty.PRESENTED_IMAGE_SIMPLE}
                          description="暂无部门支出数据"
                          style={{ marginTop: 24 }}
                        />
                      ) : (
                        <div style={{ marginTop: 16 }}>
                          <Column {...deptExpenseBarConfig} />
                        </div>
                      )}
                    </Spin>
                  </Card>
                </Col>
              </Row>
            </Card>
          </Col>
        </Row>
      </Space>
    </PageContainer>
  );
};

export default Welcome;
