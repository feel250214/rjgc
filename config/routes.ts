export default [
  {
    path: '/user',
    layout: false,
    routes: [
      { path: '/user/login', component: './User/Login' },
      { path: '/user/register', component: './User/Register' },
    ],
  },
  { path: '/welcome', icon: 'smile', component: './Welcome', name: '系统首页' },
  {
    path: '/massage',
    icon: 'ContainerOutlined',
    name: '信息管理',
    routes: [
      { path: '/massage', redirect: '/massage/announcement' },
      {
        icon: 'table',
        path: '/massage/announcement',
        component: './Massage/Announcement',
        name: '公告信息',
      },
      {
        icon: 'table',
        path: '/massage/apartment',
        component: './Massage/Apartment',
        name: '部门信息',
      },
    ],
  },
  {
    path: '/personnel',
    icon: 'UsergroupAddOutlined',
    name: '人事管理',
    access: 'canAdmin',
    routes: [
      { path: '/personnel', redirect: '/personnel/salary' },
      {
        icon: 'table',
        path: '/personnel/salary',
        component: './Personnel/Salary',
        name: '薪资信息',
      },
      {
        icon: 'table',
        path: '/personnel/finance',
        component: './Personnel/Finance',
        name: '财务支出',
      },
      { icon: 'table', path: '/personnel/leave', component: './Personnel/Leave', name: '请假审批' },
      {
        icon: 'table',
        path: '/personnel/assetInformation',
        component: './Personnel/AssetInformation',
        name: '资产信息',
      },
      {
        icon: 'table',
        path: '/personnel/assetApproval',
        component: './Personnel/AssetApproval',
        name: '资产审批',
      },
    ],
  },
  {
    path: '/individual',
    icon: 'UserOutlined',
    name: '个人管理',
    routes: [
      { path: '/individual', redirect: '/individual/salary' },
      {
        icon: 'table',
        path: '/individual/salary',
        component: './Individual/Salary',
        name: '薪资信息',
      },
      {
        icon: 'table',
        path: '/individual/leave',
        component: './Individual/Leave',
        name: '请假记录',
      },
      {
        icon: 'table',
        path: '/individual/assetInformation',
        component: './Individual/AssetInformation',
        name: '资产信息',
      },
      {
        icon: 'table',
        path: '/individual/staffAsset',
        component: './Individual/StaffAsset',
        name: '员工资产',
      },
    ],
  },
  {
    path: '/admin',
    icon: 'crown',
    name: '管理页',
    access: 'canAdmin',
    routes: [
      { path: '/admin', redirect: '/admin/user' },
      { icon: 'table', path: '/admin/user', component: './Admin/User', name: '用户信息' },
      { icon: 'table', path: '/admin/admin', component: './Admin/Admin', name: '管理员信息' },
    ],
  },
  { path: '/', redirect: '/welcome' },
  { path: '*', layout: false, component: './404' },
];
