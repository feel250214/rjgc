# 人事管理系统后端

## 项目概述

这是一个基于 Spring Boot 的人事管理系统后端，提供了简单的登录认证和员工管理功能。系统采用分层架构设计，包括控制层、服务层、数据访问层，以及 DTO 和 Converter 用于数据传输和转换。

## 技术栈

- Spring Boot 3.1.0
- Spring Web
- Spring Data JPA
- MySQL
- Lombok

## 数据库设计

### 数据库脚本

数据库脚本位于`hr_management.sql`文件中，包含数据库创建、表结构和示例数据。

### 表结构

**employee 表** | 字段名 | 数据类型 | 描述 | | --- | --- | --- | | id | BIGINT | 主键 ID，自增 | | username | VARCHAR(50) | 用户名，唯一 | | password | VARCHAR(100) | 密码 | | name | VARCHAR(50) | 姓名 | | email | VARCHAR(100) | 邮箱 | | phone | VARCHAR(20) | 电话 | | department | VARCHAR(50) | 部门 | | position | VARCHAR(50) | 职位 | | create_time | DATETIME | 创建时间，默认当前时间 |

## 接口文档

### 基础 URL

所有接口的基础 URL 为：`http://localhost:8080/api`

### 认证接口

#### 登录接口

**接口路径**：`POST /auth/login`

**请求参数**：

```json
{
  "username": "admin",
  "password": "123456"
}
```

**返回结果**：

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

#### 获取所有员工（分页）

**接口路径**：`GET /employees`

**请求参数**：

- page：页码，默认 0
- size：每页大小，默认 10

**返回结果**：

```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "content": [
      {
        "id": 1,
        "username": "admin",
        "name": "管理员",
        "email": "admin@example.com",
        "phone": "13800138000",
        "department": "人力资源部",
        "position": "经理",
        "createTime": "2025-12-21T17:30:00"
      },
      {
        "id": 2,
        "username": "user1",
        "name": "张三",
        "email": "zhangsan@example.com",
        "phone": "13800138001",
        "department": "技术部",
        "position": "开发工程师",
        "createTime": "2025-12-21T17:30:00"
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 10,
      "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
      },
      "offset": 0,
      "paged": true,
      "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 10,
    "number": 0,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
  }
}
```

#### 根据 ID 获取员工

**接口路径**：`GET /employees/{id}`

**请求参数**：

- id：员工 ID，路径参数

**返回结果**：

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

#### 创建员工

**接口路径**：`POST /employees`

**请求参数**：

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

**返回结果**：

```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 3,
    "username": "user3",
    "name": "王五",
    "email": "wangwu@example.com",
    "phone": "13800138003",
    "department": "财务部",
    "position": "财务会计",
    "createTime": "2025-12-21T17:30:00"
  }
}
```

#### 更新员工

**接口路径**：`PUT /employees/{id}`

**请求参数**：

- id：员工 ID，路径参数

```json
{
  "name": "张三三",
  "email": "zhangsansan@example.com",
  "phone": "13800138001",
  "department": "技术部",
  "position": "高级开发工程师"
}
```

**返回结果**：

```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 2,
    "username": "user1",
    "name": "张三三",
    "email": "zhangsansan@example.com",
    "phone": "13800138001",
    "department": "技术部",
    "position": "高级开发工程师",
    "createTime": "2025-12-21T17:30:00"
  }
}
```

#### 删除员工

**接口路径**：`DELETE /employees/{id}`

**请求参数**：

- id：员工 ID，路径参数

**返回结果**：

```json
{
  "code": 200,
  "message": "Success",
  "data": "Employee deleted successfully"
}
```

## 示例数据

数据库脚本中包含以下示例数据：

| 用户名 | 密码   | 姓名   | 邮箱                 | 电话        | 部门       | 职位       |
| ------ | ------ | ------ | -------------------- | ----------- | ---------- | ---------- |
| admin  | 123456 | 管理员 | admin@example.com    | 13800138000 | 人力资源部 | 经理       |
| user1  | 123456 | 张三   | zhangsan@example.com | 13800138001 | 技术部     | 开发工程师 |
| user2  | 123456 | 李四   | lisi@example.com     | 13800138002 | 市场部     | 市场专员   |
| user3  | 123456 | 王五   | wangwu@example.com   | 13800138003 | 财务部     | 财务会计   |
| user4  | 123456 | 赵六   | zhaoliu@example.com  | 13800138004 | 技术部     | 测试工程师 |

## 如何运行项目

1. **创建数据库**

   - 执行`hr_management.sql`脚本创建数据库和表结构，并插入示例数据

2. **修改配置**

   - 根据实际情况修改`application.properties`文件中的数据库连接配置

3. **启动项目**

   ```bash
   mvn spring-boot:run
   ```

4. **测试接口**
   - 使用 Postman 或其他 API 测试工具测试接口
   - 登录接口：`POST http://localhost:8080/api/auth/login`
   - 员工管理接口：`GET http://localhost:8080/api/employees`

## 项目结构

```
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
│   ├── LoginDto.java            # 登录请求DTO
│   ├── EmployeeDto.java         # 员工信息DTO
│   └── ResponseDto.java         # 统一响应格式
├── entity/                      # 实体类
│   └── Employee.java            # 员工实体
└── converter/                   # 转换器
    └── EmployeeConverter.java   # 员工实体与DTO转换器
```
