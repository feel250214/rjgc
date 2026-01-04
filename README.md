# 人事管理系统前端

## 项目概述

本系统是一个基于 Ant Design Pro 和 Umi Max 构建的 React 人事管理系统前端项目，配套后端为基于 Spring Boot 的人事管理系统。  
前端主要提供登录认证、员工信息管理等人力资源基础功能的 Web 管理界面，通过统一的接口服务与后端进行数据交互。

## 前端技术栈

- React 18.2.0
- Ant Design Pro 6（基于 @umijs/max）
- Ant Design 5.2.2
- TypeScript
- @umijs/max（路由、权限、请求、布局等）
- Node.js 至少 16 版本及以上

## 前端主要功能

- 用户登录与身份认证
- 员工信息列表展示与分页查询
- 员工信息增删改查（配合后端接口）
- 基于 Ant Design Pro 的通用后台管理布局与导航

## 目录结构（前端）

项目主要目录结构如下：

- config/：Umi/Ant Design Pro 配置（路由、主题、代理、OpenAPI 等）
  - config.ts：项目主配置（路由、布局、openAPI 等）
  - proxy.ts：接口代理配置（本地开发时转发到后端）
- src/
  - pages/：业务页面
    - Welcome.tsx：欢迎页 / 首页
    - Admin.tsx：管理类页面示例
    - 404.tsx：404 页面
  - app.tsx、global.tsx：应用入口与全局配置
  - global.less：全局样式
  - requestConfig.ts：网络请求相关配置
  - access.ts：权限控制相关配置
- public/：静态资源

## 前后端交互说明

- 后端基础 URL：`http://localhost:8080/api`
- 前端通过 `@umijs/max` 提供的 `request` 能力（参见 `src/requestConfig.ts`）统一调用后端接口。
- 在 [`config/config.ts`](file:///c:/Users/Feeling/Desktop/hr_management/rjgc-frontend/config/config.ts#L129-L135) 中配置了 OpenAPI：
  - `schemaPath: http://localhost:8080/api/v3/api-docs`
  - 可基于后端 OpenAPI 文档自动生成前端服务代码和类型定义，便于前后端联调与维护。

> 注意：`config/proxy.ts` 中的开发代理配置默认是注释掉的，如需通过前端开发服务器代理到后端，可按实际后端地址自行开启并修改。

## 后端项目概述（摘要）

> 以下内容为配套 Spring Boot 人事管理系统后端的核心说明，便于前端联调和理解整体业务。

### 后端技术栈

- Spring Boot 3.1.0
- Spring Web
- Spring Data JPA
- MySQL
- Lombok

### 数据库设计

- 数据库脚本：`hr_management.sql`  
  包含数据库创建、表结构和示例数据。

#### 关键表：employee

| 字段名      | 数据类型      | 描述                     |
|-----------|-------------|------------------------|
| id        | BIGINT      | 主键 ID，自增             |
| username  | VARCHAR(50) | 用户名，唯一               |
| password  | VARCHAR(100)| 密码                     |
| name      | VARCHAR(50) | 姓名                     |
| email     | VARCHAR(100)| 邮箱                     |
| phone     | VARCHAR(20) | 电话                     |
| department| VARCHAR(50) | 部门                     |
| position  | VARCHAR(50) | 职位                     |
| create_time | DATETIME  | 创建时间，默认当前时间         |

### 接口基础信息

- 所有接口基础 URL：`http://localhost:8080/api`
- 统一响应格式：

```json
{
  "code": 200,
  "message": "Success",
  "data": {}
}
```

### 认证接口

- 登录接口  
  - 方法与路径：`POST /auth/login`
  - 请求示例：

```json
{
  "username": "admin",
  "password": "123456"
}
```

- 响应示例（成功）：

```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "username": "admin",
    "name": "管理员",
    "email": "admin@example.com",
    "phone": "13800138000",
    "department": "人力资源部",
    "position": "经理",
    "createTime": "2025-12-21T17:30:00"
  }
}
```

### 员工管理接口

- 分页获取所有员工  
  - 方法与路径：`GET /employees`
  - 请求参数：
    - `page`：页码，默认 0
    - `size`：每页大小，默认 10

- 根据 ID 获取员工  
  - 方法与路径：`GET /employees/{id}`

- 创建员工  
  - 方法与路径：`POST /employees`
  - 请求示例：

```json
{
  "username": "user3",
  "name": "王五",
  "email": "wangwu@example.com",
  "phone": "13800138003",
  "department": "财务部",
  "position": "财务会计"
}
```

- 更新员工  
  - 方法与路径：`PUT /employees/{id}`

- 删除员工  
  - 方法与路径：`DELETE /employees/{id}`

### 示例数据（employee）

数据库脚本中包含以下示例用户数据，便于本地快速体验：

| 用户名  | 密码   | 姓名   | 邮箱                  | 电话         | 部门       | 职位         |
|-------|------|------|---------------------|------------|----------|------------|
| admin | 123456 | 管理员 | admin@example.com   | 13800138000 | 人力资源部   | 经理         |
| user1 | 123456 | 张三   | zhangsan@example.com| 13800138001 | 技术部      | 开发工程师      |
| user2 | 123456 | 李四   | lisi@example.com    | 13800138002 | 市场部      | 市场专员       |
| user3 | 123456 | 王五   | wangwu@example.com  | 13800138003 | 财务部      | 财务会计       |
| user4 | 123456 | 赵六   | zhaoliu@example.com | 13800138004 | 技术部      | 测试工程师      |

## 本地开发与运行

### 环境准备

1. 安装 Node.js（建议 16 及以上版本）。
2. 安装包管理工具（npm / pnpm / yarn 任一即可）。
3. 在后端项目中：
   - 执行 `hr_management.sql` 创建数据库、表结构并插入示例数据。
   - 根据实际情况修改 `application.properties` 中的数据库连接配置。
   - 启动后端项目（默认端口 `8080`，基础接口路径 `/api`）。

### 安装前端依赖

在当前前端项目根目录执行：

```bash
# 使用 npm
npm install

# 或使用 pnpm
pnpm install
```

### 启动前端开发环境

```bash
npm run start:dev
```

默认情况下，开发环境会运行在 `http://localhost:8000`，前端通过 `/api` 与后端服务进行联调。

### 构建与预览

```bash
# 打包构建
npm run build

# 本地预览打包结果
npm run preview
```

## 前端常用脚本

- `npm run start:dev`：启动开发环境（本地调试）。
- `npm run build`：打包构建生产环境静态资源。
- `npm run preview`：本地预览打包结果。
- `npm run lint`：运行 ESLint、Prettier 与 TypeScript 检查，保证代码质量。

## 项目结构参考（后端）

后端 Spring Boot 项目采用经典分层架构，大致结构如下，便于理解接口与业务划分：

```text
src/main/java/com.hr/
├── HrApplication.java           # 主启动类
├── config/                      # 配置类
│   └── WebConfig.java           # 跨域配置
├── controller/                  # 控制层
│   ├── AuthController.java      # 登录认证接口
│   └── EmployeeController.java  # 员工管理接口
├── service/                     # 服务层
│   ├── AuthService.java         # 登录认证服务
│   └── EmployeeService.java     # 员工管理服务
├── dao/                         # 数据访问层
│   └── EmployeeRepository.java  # 员工数据访问接口
├── dto/                         # 数据传输对象
│   ├── LoginDto.java            # 登录请求 DTO
│   ├── EmployeeDto.java         # 员工信息 DTO
│   └── ResponseDto.java         # 统一响应格式
├── entity/                      # 实体类
│   └── Employee.java            # 员工实体
└── converter/                   # 转换器
    └── EmployeeConverter.java   # 员工实体与 DTO 转换器
```

配合本前端项目即可快速搭建一套完整的人事管理系统，实现从数据库到接口，再到 Web 界面的全链路体验。
