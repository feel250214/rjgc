# HR管理系统API文档

## 1. 简介

本文档提供了HR管理系统中所有API的详细信息，包括：
- API端点和HTTP方法
- 请求参数和响应格式
- 数据库表关系
- 必填字段和验证规则
- 示例请求和响应

## 2. 数据库结构

### 2.1 表关系图

```
department ──┬──► employee
            │
            └──► position ──► employee
                      
employee ───► leave_application
employee ───► asset_application ───► asset_distribution
asset_catalog ───► asset_application
employee ───► salary_detail ───► salary_dispute
employee ───► attendance_record
employee ───► performance_record
employee ───► announcement ───► announcement_read_record
employee ───► financial_expense
```

### 2.2 表详细信息

#### 2.2.1 Department (部门表)
- **主键**: `id` (自增，不需要手动填写)
- **外键关系**: 
  - `parent_id` → department.id (上级部门ID)
  - `manager_id` → employee.id (部门负责人ID)
- **必填字段**: `code` (部门编号), `name` (部门名称), `create_time` (创建时间，自动生成), `update_time` (更新时间，自动生成)

#### 2.2.2 Position (职位表)
- **主键**: `id` (自增，不需要手动填写)
- **外键关系**: 
  - `department_id` → department.id (所属部门ID)
- **必填字段**: `name` (职位名称), `department_id` (部门ID), `create_time` (创建时间，自动生成)

#### 2.2.3 Employee (员工表)
- **主键**: `id` (自增，不需要手动填写)
- **外键关系**: 
  - `department_id` → department.id (所属部门ID)
  - `position_id` → position.id (职位ID)
- **必填字段**: `username` (用户名), `password` (密码), `name` (姓名), `create_time` (创建时间，自动生成)

#### 2.2.4 LeaveApplication (请假申请表)
- **主键**: `id` (自增，不需要手动填写)
- **外键关系**: 
  - `employee_id` → employee.id (申请人ID)
- **必填字段**: `employee_id` (申请人ID), `type` (请假类型), `start_time` (开始时间), `end_time` (结束时间), `reason` (请假原因), `status` (状态，自动生成), `create_time` (创建时间，自动生成), `update_time` (更新时间，自动生成)

#### 2.2.5 AssetCatalog (资产目录表)
- **主键**: `id` (自增，不需要手动填写)
- **必填字段**: `type` (资产类型), `name` (资产名称), `stock_quantity` (库存数量), `unit` (单位), `price` (单价), `status` (状态), `create_time` (创建时间，自动生成), `update_time` (更新时间，自动生成)

#### 2.2.6 AssetApplication (资产申请表)
- **主键**: `id` (自增，不需要手动填写)
- **外键关系**: 
  - `employee_id` → employee.id (申请人ID)
  - `asset_id` → asset_catalog.id (资产ID)
- **必填字段**: `employee_id` (申请人ID), `asset_id` (资产ID), `request_quantity` (申请数量), `purpose` (申请用途), `status` (状态，自动生成), `create_time` (创建时间，自动生成), `update_time` (更新时间，自动生成)

#### 2.2.7 SalaryDetail (薪资明细表)
- **主键**: `id` (自增，不需要手动填写)
- **外键关系**: 
  - `employee_id` → employee.id (员工ID)
  - `auditor_id` → employee.id (审核人ID)
- **必填字段**: `employee_id` (员工ID), `salary_period` (薪资周期), `basic_salary` (基本工资), `net_salary` (实发工资), `status` (状态), `create_time` (创建时间，自动生成), `update_time` (更新时间，自动生成)

## 3. API端点

### 3.1 认证API

#### 3.1.1 登录
- **URL**: `POST /api/auth/login`
- **描述**: 用户登录
- **请求体**:
  ```json
  {
    "username": "admin",  // 必填，用户名，必须存在于employee表中
    "password": "123456"   // 必填，密码
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {              // 响应数据
      "id": 1,             // 员工ID（自动生成）
      "username": "admin", // 用户名
      "name": "管理员",     // 员工姓名
      "email": "admin@example.com", // 邮箱
      "phone": "13800138000", // 电话
      "department": "技术部", // 部门名称（自动关联department表）
      "position": "管理员", // 职位名称（自动关联position表）
      "departmentId": 1,    // 部门ID（自动关联department表）
      "positionId": 1,      // 职位ID（自动关联position表）
      "createTime": "2025-12-25T10:00:00"  // 创建时间（自动生成）
    }
  }
  ```

### 3.1 认证API

#### 3.1.1 用户登录
- **URL**: `POST /api/auth/login`
- **描述**: 用户登录获取访问令牌
- **表关联说明**:
  - 涉及表：`employee`（员工表）
  - 关联字段：`username`（用户名）、`password`（密码）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求体**:
  ```json
  {
    "username": "admin",
    "password": "123456"
    // username: 用户名，必填，对应employee表的username字段
    // password: 密码，必填，对应employee表的password字段
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "登录成功",
    "data": {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "expireTime": "2025-12-26T10:00:00",
      "user": {
        "id": 1,                    // 员工ID，对应employee表的id字段
        "username": "admin",        // 用户名，对应employee表的username字段
        "name": "管理员",           // 员工姓名，对应employee表的name字段
        "email": "admin@example.com", // 邮箱，对应employee表的email字段
        "phone": "13800138000",     // 电话，对应employee表的phone字段
        "department": "技术部",     // 部门名称，关联department表的name字段
        "position": "开发工程师"     // 职位名称，关联position表的name字段
      }
    }
  }
  ```

### 3.2 部门API

#### 3.2.1 获取所有部门
- **URL**: `GET /api/departments`
- **描述**: 分页获取所有部门
- **表关联说明**:
  - 涉及表：`department`（部门表）、`employee`（员工表）
  - 关联字段：`managerId`（部门负责人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 部门ID，对应department表的id字段（自动生成）
          "code": "DEP001",           // 部门编号，对应department表的code字段
          "name": "技术部",           // 部门名称，对应department表的name字段
          "parentId": null,           // 上级部门ID，对应department表的parent_id字段（可选）
          "managerId": 1,             // 部门负责人ID，对应department表的manager_id字段，关联employee表的id字段（可选）
          "description": "负责公司技术研发", // 部门描述，对应department表的description字段（可选）
          "createTime": "2025-12-25T10:00:00", // 创建时间，对应department表的create_time字段（自动生成）
          "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应department表的update_time字段（自动生成）
        }
      ],
      "totalElements": 1,   // 总记录数
      "totalPages": 1,      // 总页数
      "size": 10,           // 每页大小
      "number": 0           // 当前页码
    }
  }
  ```

#### 3.2.2 创建部门
- **URL**: `POST /api/departments`
- **描述**: 创建新部门
- **表关联说明**:
  - 涉及表：`department`（部门表）
  - 关联字段：`parentId`（上级部门ID）关联`department.id`（部门ID），`managerId`（部门负责人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 修改表：`department`（新增一条部门记录）
- **请求体**:
  ```json
  {
    "code": "DEP002",       // 必填，部门编号，对应department表的code字段，必须唯一，不能重复
    "name": "市场部",       // 必填，部门名称，对应department表的name字段，必须唯一，不能重复
    "parentId": null,       // 可选，上级部门ID，对应department表的parent_id字段，若填写必须存在于department表中
    "managerId": 2,         // 可选，部门负责人ID，对应department表的manager_id字段，若填写必须存在于employee表中
    "description": "负责公司市场推广"  // 可选，部门描述，对应department表的description字段
    // id: 部门ID，对应department表的id字段，自动生成，不需要填写
    // createTime: 创建时间，对应department表的create_time字段，自动生成，不需要填写
    // updateTime: 更新时间，对应department表的update_time字段，自动生成，不需要填写
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,                    // 部门ID，对应department表的id字段（自动生成）
      "code": "DEP002",          // 部门编号，对应department表的code字段
      "name": "市场部",          // 部门名称，对应department表的name字段
      "parentId": null,          // 上级部门ID，对应department表的parent_id字段
      "managerId": 2,            // 部门负责人ID，对应department表的manager_id字段，关联employee表的id字段
      "description": "负责公司市场推广", // 部门描述，对应department表的description字段
      "createTime": "2025-12-25T10:00:00", // 创建时间，对应department表的create_time字段（自动生成）
      "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应department表的update_time字段（自动生成）
    }
  }
  ```

### 3.3 职位API

#### 3.3.1 获取所有职位
- **URL**: `GET /api/positions`
- **描述**: 分页获取所有职位
- **表关联说明**:
  - 涉及表：`position`（职位表）、`department`（部门表）
  - 关联字段：`departmentId`（所属部门ID）关联`department.id`（部门ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 职位ID，对应position表的id字段（自动生成）
          "name": "开发工程师",           // 职位名称，对应position表的name字段
          "departmentId": 1,           // 所属部门ID，对应position表的department_id字段，关联department表的id字段
          "description": "负责软件开发", // 职位描述，对应position表的description字段（可选）
          "createTime": "2025-12-25T10:00:00"  // 创建时间，对应position表的create_time字段（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.3.2 创建职位
- **URL**: `POST /api/positions`
- **描述**: 创建新职位
- **表关联说明**:
  - 涉及表：`position`（职位表）、`department`（部门表）
  - 关联字段：`departmentId`（所属部门ID）关联`department.id`（部门ID）
- **操作影响的表**:
  - 修改表：`position`（新增一条职位记录）
- **请求体**:
  ```json
  {
    "name": "产品经理",     // 必填，职位名称，对应position表的name字段
    "departmentId": 1,     // 必填，所属部门ID，对应position表的department_id字段，必须存在于department表中
    "description": "负责产品规划"  // 可选，职位描述，对应position表的description字段
    // id: 职位ID，对应position表的id字段，自动生成，不需要填写
    // createTime: 创建时间，对应position表的create_time字段，自动生成，不需要填写
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 2,                    // 职位ID，对应position表的id字段（自动生成）
      "name": "产品经理",          // 职位名称，对应position表的name字段
      "departmentId": 1,          // 所属部门ID，对应position表的department_id字段，关联department表的id字段
      "description": "负责产品规划", // 职位描述，对应position表的description字段
      "createTime": "2025-12-25T10:00:00"  // 创建时间，对应position表的create_time字段（自动生成）
    }
  }
  ```

### 3.4 员工API

#### 3.4.1 获取所有员工
- **URL**: `GET /api/employees`
- **描述**: 分页获取所有员工
- **表关联说明**:
  - 涉及表：`employee`（员工表）、`department`（部门表）、`position`（职位表）
  - 关联字段：`departmentId`（部门ID）关联`department.id`（部门ID），`positionId`（职位ID）关联`position.id`（职位ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 员工ID，对应employee表的id字段（自动生成）
          "username": "testuser",     // 用户名，对应employee表的username字段
          "name": "测试用户",        // 姓名，对应employee表的name字段
          "email": "test@example.com", // 邮箱，对应employee表的email字段
          "phone": "13800138000",     // 电话，对应employee表的phone字段
          "department": "技术部",     // 部门名称，自动关联department表的name字段
          "position": "开发工程师",   // 职位名称，自动关联position表的name字段
          "departmentId": 1,           // 部门ID，对应employee表的department_id字段，关联department表的id字段
          "positionId": 1,             // 职位ID，对应employee表的position_id字段，关联position表的id字段
          "createTime": "2025-12-25T10:00:00"  // 创建时间，对应employee表的create_time字段（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.4.2 创建员工
- **URL**: `POST /api/employees`
- **描述**: 创建新员工
- **表关联说明**:
  - 涉及表：`employee`（员工表）、`department`（部门表）、`position`（职位表）
  - 关联字段：`departmentId`（部门ID）关联`department.id`（部门ID），`positionId`（职位ID）关联`position.id`（职位ID）
- **操作影响的表**:
  - 修改表：`employee`（新增一条员工记录）
- **请求体**:
  ```json
  {
    "username": "newuser",     // 必填，用户名，对应employee表的username字段，必须唯一，长度4-50个字符
    "password": "password123", // 必填，密码，对应employee表的password字段，长度6-100个字符
    "name": "新员工",        // 必填，姓名，对应employee表的name字段，长度不超过100个字符
    "email": "new@example.com", // 可选，邮箱，对应employee表的email字段，必须是有效的邮箱格式
    "phone": "13900139000",     // 可选，电话，对应employee表的phone字段，长度不超过20个字符
    "departmentId": 1,         // 必填，所属部门ID，对应employee表的department_id字段，必须存在于department表中
    "positionId": 1             // 必填，职位ID，对应employee表的position_id字段，必须存在于position表中
    // id: 员工ID，对应employee表的id字段，自动生成，不需要填写
    // department: 部门名称，自动关联department表的name字段，不需要填写
    // position: 职位名称，自动关联position表的name字段，不需要填写
    // createTime: 创建时间，对应employee表的create_time字段，自动生成，不需要填写
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 2,                    // 员工ID，对应employee表的id字段（自动生成）
      "username": "newuser",     // 用户名，对应employee表的username字段
      "name": "新员工",        // 员工姓名，对应employee表的name字段
      "email": "new@example.com", // 邮箱，对应employee表的email字段
      "phone": "13900139000",     // 电话，对应employee表的phone字段
      "department": "技术部",     // 部门名称，自动关联department表的name字段
      "position": "开发工程师",   // 职位名称，自动关联position表的name字段
      "departmentId": 1,         // 部门ID，对应employee表的department_id字段，关联department表的id字段
      "positionId": 1,           // 职位ID，对应employee表的position_id字段，关联position表的id字段
      "createTime": "2025-12-25T10:00:00"  // 创建时间，对应employee表的create_time字段（自动生成）
    }
  }
  ```

### 3.5 资产目录API

#### 3.5.1 获取所有资产目录
- **URL**: `GET /api/asset-catalogs`
- **描述**: 分页获取所有资产目录
- **表关联说明**:
  - 涉及表：`asset_catalog`（资产目录表）
  - 关联字段：无外键关联
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 资产ID，对应asset_catalog表的id字段（自动生成）
          "type": "电子设备",           // 资产类型，对应asset_catalog表的type字段
          "name": "笔记本电脑",         // 资产名称，对应asset_catalog表的name字段
          "description": "高性能笔记本电脑", // 资产描述，对应asset_catalog表的description字段（可选）
          "stockQuantity": 10,         // 库存数量，对应asset_catalog表的stock_quantity字段
          "unit": "台",               // 单位，对应asset_catalog表的unit字段
          "price": 8000.0,              // 单价，对应asset_catalog表的price字段
          "budgetLimit": 50000.0,      // 预算限制，对应asset_catalog表的budget_limit字段（可选）
          "status": "可用",             // 状态，对应asset_catalog表的status字段
          "createTime": "2025-12-25T10:00:00", // 创建时间，对应asset_catalog表的create_time字段（自动生成）
          "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应asset_catalog表的update_time字段（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.5.2 创建资产目录
- **URL**: `POST /api/asset-catalogs`
- **描述**: 创建新资产目录
- **表关联说明**:
  - 涉及表：`asset_catalog`（资产目录表）
  - 关联字段：无外键关联
- **操作影响的表**:
  - 修改表：`asset_catalog`（新增一条资产目录记录）
- **请求体**:
  ```json
  {
    "type": "办公家具",         // 必填，资产类型，对应asset_catalog表的type字段，长度不超过50个字符
    "name": "办公椅",           // 必填，资产名称，对应asset_catalog表的name字段，长度不超过100个字符
    "description": "人体工学办公椅",  // 可选，资产描述，对应asset_catalog表的description字段，长度不超过500个字符
    "stockQuantity": 20,         // 必填，库存数量，对应asset_catalog表的stock_quantity字段，不能小于0
    "unit": "把",               // 必填，单位，对应asset_catalog表的unit字段，长度不超过20个字符
    "price": 500.0,              // 必填，单价，对应asset_catalog表的price字段，不能小于0
    "budgetLimit": 10000.0,      // 可选，预算限制，对应asset_catalog表的budget_limit字段，不能小于0
    "status": "可用"             // 必填，状态，对应asset_catalog表的status字段，长度不超过50个字符
    // id: 资产ID，对应asset_catalog表的id字段，自动生成，不需要填写
    // createTime: 创建时间，对应asset_catalog表的create_time字段，自动生成，不需要填写
    // updateTime: 更新时间，对应asset_catalog表的update_time字段，自动生成，不需要填写
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 2,                    // 资产ID，对应asset_catalog表的id字段（自动生成）
      "type": "办公家具",          // 资产类型，对应asset_catalog表的type字段
      "name": "办公椅",            // 资产名称，对应asset_catalog表的name字段
      "description": "人体工学办公椅", // 资产描述，对应asset_catalog表的description字段
      "stockQuantity": 20,         // 库存数量，对应asset_catalog表的stock_quantity字段
      "unit": "把",              // 单位，对应asset_catalog表的unit字段
      "price": 500.0,             // 单价，对应asset_catalog表的price字段
      "budgetLimit": 10000.0,     // 预算限制，对应asset_catalog表的budget_limit字段
      "status": "可用",            // 状态，对应asset_catalog表的status字段
      "createTime": "2025-12-25T10:00:00", // 创建时间，对应asset_catalog表的create_time字段（自动生成）
      "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应asset_catalog表的update_time字段（自动生成）
    }
  }
  ```

### 3.6 请假申请API

#### 3.6.1 获取所有请假申请
- **URL**: `GET /api/leave-applications`
- **描述**: 分页获取所有请假申请
- **表关联说明**:
  - 涉及表：`leave_application`（请假申请表）、`employee`（员工表）
  - 关联字段：`employeeId`（申请人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 请假申请ID，对应leave_application表的id字段（自动生成）
          "employeeId": 1,             // 申请人ID，对应leave_application表的employee_id字段，关联employee表的id字段
          "type": "年假",               // 请假类型，对应leave_application表的type字段
          "startTime": "2025-12-26T09:00:00", // 开始时间，对应leave_application表的start_time字段
          "endTime": "2025-12-27T18:00:00",   // 结束时间，对应leave_application表的end_time字段
          "reason": "回家过年",             // 请假原因，对应leave_application表的reason字段
          "attachment": null,           // 附件，对应leave_application表的attachment字段（可选）
          "status": "待主管审批",           // 状态，对应leave_application表的status字段（自动生成）
          "managerComment": null,       // 主管审批意见，对应leave_application表的manager_comment字段（可选）
          "adminComment": null,         // 管理员审批意见，对应leave_application表的admin_comment字段（可选）
          "createTime": "2025-12-25T10:00:00", // 创建时间，对应leave_application表的create_time字段（自动生成）
          "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应leave_application表的update_time字段（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.6.2 创建请假申请
- **URL**: `POST /api/leave-applications`
- **描述**: 创建新请假申请
- **表关联说明**:
  - 涉及表：`leave_application`（请假申请表）、`employee`（员工表）
  - 关联字段：`employeeId`（申请人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 修改表：`leave_application`（新增一条请假申请记录）
- **请求体**:
  ```json
  {
    "employeeId": 1,                 // 必填，申请人ID，对应leave_application表的employee_id字段，外键，必须存在于employee表中
    "type": "病假",                   // 必填，请假类型，对应leave_application表的type字段，长度不超过50个字符
    "startTime": "2025-12-28T09:00:00", // 必填，开始时间，对应leave_application表的start_time字段，格式：yyyy-MM-ddTHH:mm:ss
    "endTime": "2025-12-28T18:00:00",   // 必填，结束时间，对应leave_application表的end_time字段，格式：yyyy-MM-ddTHH:mm:ss，必须大于开始时间
    "reason": "身体不适",               // 必填，请假原因，对应leave_application表的reason字段，长度不超过1000个字符
    "attachment": "http://example.com/medical.pdf" // 可选，附件URL，对应leave_application表的attachment字段
    // id: 请假申请ID，对应leave_application表的id字段，自动生成，不需要填写
    // status: 状态，对应leave_application表的status字段，自动生成，初始为"待主管审批"
    // managerComment: 主管审批意见，对应leave_application表的manager_comment字段，初始为null，不需要填写
    // adminComment: 管理员审批意见，对应leave_application表的admin_comment字段，初始为null，不需要填写
    // createTime: 创建时间，对应leave_application表的create_time字段，自动生成，不需要填写
    // updateTime: 更新时间，对应leave_application表的update_time字段，自动生成，不需要填写
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 2,                    // 请假申请ID，对应leave_application表的id字段（自动生成）
      "employeeId": 1,             // 申请人ID，对应leave_application表的employee_id字段，关联employee表的id字段
      "type": "病假",               // 请假类型，对应leave_application表的type字段（必填）
      "startTime": "2025-12-28T09:00:00", // 开始时间，对应leave_application表的start_time字段（必填，格式：yyyy-MM-ddTHH:mm:ss）
      "endTime": "2025-12-28T18:00:00",   // 结束时间，对应leave_application表的end_time字段（必填，格式：yyyy-MM-ddTHH:mm:ss）
      "reason": "身体不适",             // 请假原因，对应leave_application表的reason字段（必填）
      "attachment": "http://example.com/medical.pdf", // 附件，对应leave_application表的attachment字段（可选）
      "status": "待主管审批",           // 状态，对应leave_application表的status字段（自动生成，初始为"待主管审批"）
      "managerComment": null,       // 主管审批意见（初始为null）
      "adminComment": null,         // 管理员审批意见（初始为null）
      "createTime": "2025-12-25T10:00:00", // 创建时间（自动生成）
      "updateTime": "2025-12-25T10:00:00"  // 更新时间（自动生成）
    }
  }
  ```

#### 3.6.3 主管审批请假申请
- **URL**: `POST /api/leave-applications/{id}/manager-approve`
- **描述**: 主管审批请假申请
- **请求参数**:
  - `id`: 请假申请ID（路径参数，必须存在于leave_application表中）
  - `result`: 审批结果（approve/reject，必填）
  - `comment`: 审批意见（必填）
  - `approverId`: 审批人ID（必填，必须存在于employee表中，且为该部门的主管）
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 1,                    // 请假申请ID（自动生成）
      "employeeId": 1,             // 申请人ID（外键，关联employee表）
      "type": "年假",               // 请假类型（必填）
      "startTime": "2025-12-26T09:00:00", // 开始时间（必填，格式：yyyy-MM-ddTHH:mm:ss）
      "endTime": "2025-12-27T18:00:00",   // 结束时间（必填，格式：yyyy-MM-ddTHH:mm:ss）
      "reason": "回家过年",             // 请假原因（必填）
      "attachment": null,           // 附件（可选）
      "status": "待管理员审批",           // 状态（自动更新为待管理员审批）
      "managerComment": "同意请假",     // 主管审批意见（必填）
      "adminComment": null,         // 管理员审批意见（初始为null，管理员审批后更新）
      "createTime": "2025-12-25T10:00:00", // 创建时间（自动生成）
      "updateTime": "2025-12-25T10:30:00"  // 更新时间（自动更新）
    }
  }
  ```

#### 3.6.4 管理员审批请假申请
- **URL**: `POST /api/leave-applications/{id}/admin-approve`
- **描述**: 管理员审批请假申请
- **请求参数**:
  - `id`: 请假申请ID（路径参数，必须存在于leave_application表中）
  - `result`: 审批结果（approve/reject，必填）
  - `comment`: 审批意见（必填）
  - `approverId`: 审批人ID（必填，必须存在于employee表中，且为管理员）
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 1,                    // 请假申请ID（自动生成）
      "employeeId": 1,             // 申请人ID（外键，关联employee表）
      "type": "年假",               // 请假类型（必填）
      "startTime": "2025-12-26T09:00:00", // 开始时间（必填，格式：yyyy-MM-ddTHH:mm:ss）
      "endTime": "2025-12-27T18:00:00",   // 结束时间（必填，格式：yyyy-MM-ddTHH:mm:ss）
      "reason": "回家过年",             // 请假原因（必填）
      "attachment": null,           // 附件（可选）
      "status": "已批准",             // 状态（自动更新为已批准）
      "managerComment": "同意请假",     // 主管审批意见（必填）
      "adminComment": "同意请假",       // 管理员审批意见（必填）
      "createTime": "2025-12-25T10:00:00", // 创建时间（自动生成）
      "updateTime": "2025-12-25T10:45:00"  // 更新时间（自动更新）
    }
  }
  ```

#### 3.6.5 根据员工ID获取请假申请列表
- **URL**: `GET /api/leave-applications/employee/{employeeId}`
- **描述**: 根据员工ID获取请假申请列表
- **请求参数**:
  - `employeeId`: 员工ID（路径参数，必须存在于employee表中）
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": [
      {
        "id": 1,                    // 请假申请ID（自动生成）
        "employeeId": 1,             // 申请人ID（外键，关联employee表）
        "type": "年假",               // 请假类型（必填）
        "startTime": "2025-12-26T09:00:00", // 开始时间（必填，格式：yyyy-MM-ddTHH:mm:ss）
        "endTime": "2025-12-27T18:00:00",   // 结束时间（必填，格式：yyyy-MM-ddTHH:mm:ss）
        "reason": "回家过年",             // 请假原因（必填）
        "attachment": null,           // 附件（可选）
        "status": "已批准",             // 状态（自动生成）
        "managerComment": "同意请假",     // 主管审批意见（必填）
        "adminComment": "同意请假",       // 管理员审批意见（必填）
        "createTime": "2025-12-25T10:00:00", // 创建时间（自动生成）
        "updateTime": "2025-12-25T10:45:00"  // 更新时间（自动更新）
      }
    ]
  }
  ```

#### 3.6.6 根据ID获取请假申请
- **URL**: `GET /api/leave-applications/{id}`
- **描述**: 根据ID获取请假申请
- **请求参数**:
  - `id`: 请假申请ID（路径参数，必须存在于leave_application表中）
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 1,                    // 请假申请ID（自动生成）
      "employeeId": 1,             // 申请人ID（外键，关联employee表）
      "type": "年假",               // 请假类型（必填）
      "startTime": "2025-12-26T09:00:00", // 开始时间（必填，格式：yyyy-MM-ddTHH:mm:ss）
      "endTime": "2025-12-27T18:00:00",   // 结束时间（必填，格式：yyyy-MM-ddTHH:mm:ss）
      "reason": "回家过年",             // 请假原因（必填）
      "attachment": null,           // 附件（可选）
      "status": "已批准",             // 状态（自动生成）
      "managerComment": "同意请假",     // 主管审批意见（必填）
      "adminComment": "同意请假",       // 管理员审批意见（必填）
      "createTime": "2025-12-25T10:00:00", // 创建时间（自动生成）
      "updateTime": "2025-12-25T10:45:00"  // 更新时间（自动更新）
    }
  }
  ```

#### 3.6.7 获取请假申请的审批记录
- **URL**: `GET /api/leave-applications/{id}/approval-records`
- **描述**: 获取请假申请的审批记录
- **请求参数**:
  - `id`: 请假申请ID（路径参数，必须存在于leave_application表中）
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": [
      {
        "id": 1,                    // 审批记录ID（自动生成）
        "applicationId": 1,         // 申请ID（外键，关联leave_application表）
        "applicationType": "leave", // 申请类型
        "approverId": 2,            // 审批人ID（外键，关联employee表）
        "approverName": "主管",       // 审批人姓名（自动关联employee表）
        "result": "approve",        // 审批结果（approve/reject）
        "comment": "同意请假",         // 审批意见
        "approveTime": "2025-12-25T10:30:00" // 审批时间（自动生成）
      },
      {
        "id": 2,                    // 审批记录ID（自动生成）
        "applicationId": 1,         // 申请ID（外键，关联leave_application表）
        "applicationType": "leave", // 申请类型
        "approverId": 3,            // 审批人ID（外键，关联employee表）
        "approverName": "管理员",     // 审批人姓名（自动关联employee表）
        "result": "approve",        // 审批结果（approve/reject）
        "comment": "同意请假",         // 审批意见
        "approveTime": "2025-12-25T10:45:00" // 审批时间（自动生成）
      }
    ]
  }
  ```

### 3.7 资产申请API

#### 3.7.1 获取所有资产申请（分页）
- **URL**: `GET /api/asset-applications`
- **描述**: 分页获取所有资产申请
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 资产申请ID（自动生成）
          "employeeId": 1,             // 申请人ID
          "assetId": 1,                // 资产ID
          "requestQuantity": 2,        // 申请数量
          "purpose": "项目开发需要",       // 申请用途
          "status": "待主管审批",           // 状态（自动生成）
          "managerComment": null,       // 主管审批意见（可选）
          "adminComment": null,         // 管理员审批意见（可选）
          "createTime": "2025-12-25T10:00:00", // 创建时间（自动生成）
          "updateTime": "2025-12-25T10:00:00"  // 更新时间（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.7.2 根据员工ID获取资产申请列表
- **URL**: `GET /api/asset-applications/employee/{employeeId}`
- **描述**: 根据员工ID获取资产申请列表
- **请求参数**:
  - `employeeId`: 员工ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,
        "employeeId": 1,
        "assetId": 1,
        "requestQuantity": 2,
        "purpose": "项目开发需要",
        "status": "待主管审批",
        "managerComment": null,
        "adminComment": null,
        "createTime": "2025-12-25T10:00:00",
        "updateTime": "2025-12-25T10:00:00"
      }
    ]
  }
  ```

#### 3.7.3 根据ID获取资产申请
- **URL**: `GET /api/asset-applications/{id}`
- **描述**: 根据ID获取资产申请
- **请求参数**:
  - `id`: 资产申请ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,
      "employeeId": 1,
      "assetId": 1,
      "requestQuantity": 2,
      "purpose": "项目开发需要",
      "status": "待主管审批",
      "managerComment": null,
      "adminComment": null,
      "createTime": "2025-12-25T10:00:00",
      "updateTime": "2025-12-25T10:00:00"
    }
  }
  ```

#### 3.7.4 创建资产申请
- **URL**: `POST /api/asset-applications`
- **描述**: 创建新资产申请
- **请求体**:
  ```json
  {
    "employeeId": 1,                 // 必填，申请人ID，外键，必须存在于employee表中
    "assetId": 1,                    // 必填，资产ID，外键，必须存在于asset_catalog表中
    "requestQuantity": 2,            // 必填，申请数量，不能小于1，且不能超过asset_catalog表中的stock_quantity
    "purpose": "项目开发需要"           // 必填，申请用途，长度不能超过1000个字符
    // id: 资产申请ID，自动生成，不需要填写
    // status: 状态，自动生成，初始为"待主管审批"
    // managerComment: 主管审批意见，初始为null，不需要填写
    // adminComment: 管理员审批意见，初始为null，不需要填写
    // createTime: 创建时间，自动生成，不需要填写
    // updateTime: 更新时间，自动生成，不需要填写
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 2,                    // 资产申请ID（自动生成）
      "employeeId": 1,             // 申请人ID（外键，关联employee表）
      "assetId": 1,                // 资产ID（外键，关联asset_catalog表）
      "requestQuantity": 2,        // 申请数量
      "purpose": "项目开发需要",       // 申请用途
      "status": "待主管审批",           // 状态（自动生成，初始为"待主管审批"）
      "managerComment": null,       // 主管审批意见（初始为null）
      "adminComment": null,         // 管理员审批意见（初始为null）
      "createTime": "2025-12-25T10:00:00", // 创建时间（自动生成）
      "updateTime": "2025-12-25T10:00:00"  // 更新时间（自动生成）
    }
  }
  ```

#### 3.7.5 主管审批资产申请
- **URL**: `POST /api/asset-applications/{id}/manager-approve`
- **描述**: 主管审批资产申请
- **请求参数**:
  - `id`: 资产申请ID（路径参数，必须存在于asset_application表中）
  - `result`: 审批结果（approve/reject，必填）
  - `comment`: 审批意见（必填）
  - `approverId`: 审批人ID（必填，必须存在于employee表中，且为该部门的主管）
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 1,                    // 资产申请ID（自动生成）
      "employeeId": 1,             // 申请人ID（外键，关联employee表）
      "assetId": 1,                // 资产ID（外键，关联asset_catalog表）
      "requestQuantity": 2,        // 申请数量
      "purpose": "项目开发需要",       // 申请用途
      "status": "待管理员审批",           // 状态（自动更新为待管理员审批）
      "managerComment": "同意申请",     // 主管审批意见（必填）
      "adminComment": null,         // 管理员审批意见（初始为null，管理员审批后更新）
      "createTime": "2025-12-25T10:00:00", // 创建时间（自动生成）
      "updateTime": "2025-12-25T10:30:00"  // 更新时间（自动更新）
    }
  }
  ```

#### 3.7.6 管理员审批资产申请
- **URL**: `POST /api/asset-applications/{id}/admin-approve`
- **描述**: 管理员审批资产申请
- **请求参数**:
  - `id`: 资产申请ID（路径参数，必须存在于asset_application表中，且状态为"待管理员审批"）
  - `result`: 审批结果（approve/reject，必填）
  - `comment`: 审批意见（必填）
  - `approverId`: 审批人ID（必填，必须存在于employee表中，且为管理员）
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 1,                    // 资产申请ID（自动生成）
      "employeeId": 1,             // 申请人ID（外键，关联employee表）
      "assetId": 1,                // 资产ID（外键，关联asset_catalog表）
      "requestQuantity": 2,        // 申请数量
      "purpose": "项目开发需要",       // 申请用途
      "status": "已分配",             // 状态（自动更新为已分配）
      "managerComment": "同意申请",     // 主管审批意见（必填）
      "adminComment": "同意分配",       // 管理员审批意见（必填）
      "createTime": "2025-12-25T10:00:00", // 创建时间（自动生成）
      "updateTime": "2025-12-25T10:45:00"  // 更新时间（自动更新）
    }
  }
  ```

#### 3.7.7 员工确认接收资产
- **URL**: `POST /api/asset-applications/asset-distributions/{distributionId}/confirm-receive`
- **描述**: 员工确认接收资产
- **请求参数**:
  - `distributionId`: 资产分配ID（路径参数，必须存在于asset_distribution表中，且状态为"已分配"）
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 1,                    // 资产分配ID（自动生成）
      "assetApplicationId": 1,     // 资产申请ID（外键，关联asset_application表）
      "distributionQuantity": 2,   // 分配数量
      "distributionTime": "2025-12-25T11:00:00", // 分配时间（自动生成）
      "status": "已确认接收",           // 状态（自动更新为已确认接收）
      "feedback": null             // 反馈内容（初始为null）
    }
  }
  ```

#### 3.7.8 员工提出资产异议
- **URL**: `POST /api/asset-applications/asset-distributions/{distributionId}/raise-objection`
- **描述**: 员工提出资产异议
- **请求参数**:
  - `distributionId`: 资产分配ID（路径参数，必须存在于asset_distribution表中，且状态为"已分配"或"已确认接收"）
  - `feedback`: 反馈内容（必填，描述资产问题）
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 1,                    // 资产分配ID（自动生成）
      "assetApplicationId": 1,     // 资产申请ID（外键，关联asset_application表）
      "distributionQuantity": 2,   // 分配数量
      "distributionTime": "2025-12-25T11:00:00", // 分配时间（自动生成）
      "status": "异议处理中",           // 状态（自动更新为异议处理中）
      "feedback": "资产有损坏"             // 反馈内容（必填）
    }
  }
  ```

#### 3.7.9 管理员处理资产异议
- **URL**: `POST /api/asset-applications/asset-distributions/{distributionId}/process-objection`
- **描述**: 管理员处理资产异议
- **请求参数**:
  - `distributionId`: 资产分配ID（路径参数，必须存在于asset_distribution表中，且状态为"异议处理中"）
  - `processResult`: 处理结果（必填，描述处理方式）
- **响应**:
  ```json
  {
    "code": 200,           // 状态码：200=成功，其他=错误
    "message": "success",  // 结果描述
    "data": {
      "id": 1,                    // 资产分配ID（自动生成）
      "assetApplicationId": 1,     // 资产申请ID（外键，关联asset_application表）
      "distributionQuantity": 2,   // 分配数量
      "distributionTime": "2025-12-25T11:00:00", // 分配时间（自动生成）
      "status": "已处理",             // 状态（自动更新为已处理）
      "feedback": "资产有损坏",             // 反馈内容
      "processResult": "已重新分配"         // 处理结果（必填）
    }
  }
  ```

#### 3.7.10 获取资产申请的审批记录
- **URL**: `GET /api/asset-applications/{id}/approval-records`
- **描述**: 获取资产申请的审批记录
- **请求参数**:
  - `id`: 资产申请ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,
        "applicationId": 1,
        "applicationType": "asset",
        "approverId": 2,
        "approverName": "主管",
        "result": "approve",
        "comment": "同意申请",
        "approveTime": "2025-12-25T10:30:00"
      },
      {
        "id": 2,
        "applicationId": 1,
        "applicationType": "asset",
        "approverId": 3,
        "approverName": "管理员",
        "result": "approve",
        "comment": "同意分配",
        "approveTime": "2025-12-25T10:45:00"
      }
    ]
  }
  ```

#### 3.7.11 获取资产分配记录
- **URL**: `GET /api/asset-applications/{id}/asset-distributions`
- **描述**: 获取资产分配记录
- **请求参数**:
  - `id`: 资产申请ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,
        "assetApplicationId": 1,
        "distributionQuantity": 2,
        "distributionTime": "2025-12-25T11:00:00",
        "status": "已确认接收",
        "feedback": null
      }
    ]
  }
  ```

### 3.8 薪资明细API

#### 3.8.1 获取所有薪资明细（分页）
- **URL**: `GET /api/salary-details`
- **描述**: 分页获取所有薪资明细
- **表关联说明**:
  - 涉及表：`salary_detail`（薪资明细表）、`employee`（员工表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`auditorId`（审核人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 薪资明细ID，对应salary_detail表的id字段（自动生成）
          "employeeId": 1,             // 员工ID，对应salary_detail表的employee_id字段，关联employee表的id字段
          "salaryPeriod": "2025-12",   // 薪资周期，对应salary_detail表的salary_period字段
          "basicSalary": 8000.0,        // 基本工资，对应salary_detail表的basic_salary字段
          "performanceBonus": 2000.0,   // 绩效奖金，对应salary_detail表的performance_bonus字段
          "allowance": 500.0,           // 津贴，对应salary_detail表的allowance字段
          "overtimePay": 1000.0,         // 加班费，对应salary_detail表的overtime_pay字段
          "socialSecurityDeduction": 1200.0, // 社保扣款，对应salary_detail表的social_security_deduction字段
          "housingFundDeduction": 800.0, // 公积金扣款，对应salary_detail表的housing_fund_deduction字段
          "taxDeduction": 500.0,         // 个税扣款，对应salary_detail表的tax_deduction字段
          "otherDeductions": 0.0,        // 其他扣款，对应salary_detail表的other_deductions字段
          "netSalary": 9000.0,           // 实发工资，对应salary_detail表的net_salary字段
          "status": "已发放",             // 状态，对应salary_detail表的status字段
          "auditorId": 2,               // 审核人ID，对应salary_detail表的auditor_id字段，关联employee表的id字段
          "auditTime": "2025-12-25T09:00:00", // 审核时间，对应salary_detail表的audit_time字段
          "paymentTime": "2025-12-25T10:00:00", // 发放时间，对应salary_detail表的payment_time字段
          "createTime": "2025-12-25T08:00:00", // 创建时间，对应salary_detail表的create_time字段（自动生成）
          "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应salary_detail表的update_time字段（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.8.2 根据员工ID获取薪资明细
- **URL**: `GET /api/salary-details/employee/{employeeId}`
- **描述**: 根据员工ID获取薪资明细列表
- **表关联说明**:
  - 涉及表：`salary_detail`（薪资明细表）、`employee`（员工表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`auditorId`（审核人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `employeeId`: 员工ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,                    // 薪资明细ID，对应salary_detail表的id字段（自动生成）
        "employeeId": 1,             // 员工ID，对应salary_detail表的employee_id字段，关联employee表的id字段
        "salaryPeriod": "2025-12",   // 薪资周期，对应salary_detail表的salary_period字段
        "basicSalary": 8000.0,        // 基本工资，对应salary_detail表的basic_salary字段
        "performanceBonus": 2000.0,   // 绩效奖金，对应salary_detail表的performance_bonus字段
        "allowance": 500.0,           // 津贴，对应salary_detail表的allowance字段
        "overtimePay": 1000.0,         // 加班费，对应salary_detail表的overtime_pay字段
        "socialSecurityDeduction": 1200.0, // 社保扣款，对应salary_detail表的social_security_deduction字段
        "housingFundDeduction": 800.0, // 公积金扣款，对应salary_detail表的housing_fund_deduction字段
        "taxDeduction": 500.0,         // 个税扣款，对应salary_detail表的tax_deduction字段
        "otherDeductions": 0.0,        // 其他扣款，对应salary_detail表的other_deductions字段
        "netSalary": 9000.0,           // 实发工资，对应salary_detail表的net_salary字段
        "status": "已发放",             // 状态，对应salary_detail表的status字段
        "auditorId": 2,               // 审核人ID，对应salary_detail表的auditor_id字段，关联employee表的id字段
        "auditTime": "2025-12-25T09:00:00", // 审核时间，对应salary_detail表的audit_time字段
        "paymentTime": "2025-12-25T10:00:00", // 发放时间，对应salary_detail表的payment_time字段
        "createTime": "2025-12-25T08:00:00", // 创建时间，对应salary_detail表的create_time字段（自动生成）
        "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应salary_detail表的update_time字段（自动生成）
      }
    ]
  }
  ```

### 3.9 绩效记录API

#### 3.9.1 获取所有绩效记录（分页）
- **URL**: `GET /api/performance-records`
- **描述**: 分页获取所有绩效记录
- **表关联说明**:
  - 涉及表：`performance_record`（绩效记录表）、`employee`（员工表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`assessorId`（评估人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 绩效记录ID，对应performance_record表的id字段（自动生成）
          "employeeId": 1,             // 员工ID，对应performance_record表的employee_id字段，关联employee表的id字段
          "employeeName": "测试用户",   // 员工姓名，自动关联employee表的name字段
          "performancePeriod": "2025-12", // 绩效周期，对应performance_record表的performance_period字段
          "performanceScore": 95,      // 绩效分数，对应performance_record表的performance_score字段
          "performanceLevel": "优秀",  // 绩效等级，对应performance_record表的performance_level字段
          "performanceComment": "工作表现出色", // 绩效评语，对应performance_record表的performance_comment字段
          "assessorId": 2,             // 评估人ID，对应performance_record表的assessor_id字段，关联employee表的id字段
          "assessorName": "主管",       // 评估人姓名，自动关联employee表的name字段
          "createTime": "2025-12-25T10:00:00", // 创建时间，对应performance_record表的create_time字段（自动生成）
          "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应performance_record表的update_time字段（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.9.2 根据员工ID获取绩效记录
- **URL**: `GET /api/performance-records/employee/{employeeId}`
- **描述**: 根据员工ID获取绩效记录列表
- **表关联说明**:
  - 涉及表：`performance_record`（绩效记录表）、`employee`（员工表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`assessorId`（评估人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `employeeId`: 员工ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,                    // 绩效记录ID，对应performance_record表的id字段（自动生成）
        "employeeId": 1,             // 员工ID，对应performance_record表的employee_id字段，关联employee表的id字段
        "employeeName": "测试用户",   // 员工姓名，自动关联employee表的name字段
        "performancePeriod": "2025-12", // 绩效周期，对应performance_record表的performance_period字段
        "performanceScore": 95,      // 绩效分数，对应performance_record表的performance_score字段
        "performanceLevel": "优秀",  // 绩效等级，对应performance_record表的performance_level字段
        "performanceComment": "工作表现出色", // 绩效评语，对应performance_record表的performance_comment字段
        "assessorId": 2,             // 评估人ID，对应performance_record表的assessor_id字段，关联employee表的id字段
        "assessorName": "主管",       // 评估人姓名，自动关联employee表的name字段
        "createTime": "2025-12-25T10:00:00", // 创建时间，对应performance_record表的create_time字段（自动生成）
        "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应performance_record表的update_time字段（自动生成）
      }
    ]
  }
  ```

#### 3.9.3 根据绩效周期获取绩效记录
- **URL**: `GET /api/performance-records/period/{performancePeriod}`
- **描述**: 根据绩效周期获取绩效记录列表
- **请求参数**:
  - `performancePeriod`: 绩效周期（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,
        "employeeId": 1,
        "employeeName": "测试用户",
        "performancePeriod": "2025-12",
        "performanceScore": 95,
        "performanceLevel": "优秀",
        "performanceComment": "工作表现出色",
        "assessorId": 2,
        "assessorName": "主管",
        "createTime": "2025-12-25T10:00:00",
        "updateTime": "2025-12-25T10:00:00"
      }
    ]
  }
  ```

#### 3.9.4 根据ID获取绩效记录
- **URL**: `GET /api/performance-records/{id}`
- **描述**: 根据ID获取绩效记录
- **请求参数**:
  - `id`: 绩效记录ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,
      "employeeId": 1,
      "employeeName": "测试用户",
      "performancePeriod": "2025-12",
      "performanceScore": 95,
      "performanceLevel": "优秀",
      "performanceComment": "工作表现出色",
      "assessorId": 2,
      "assessorName": "主管",
      "createTime": "2025-12-25T10:00:00",
      "updateTime": "2025-12-25T10:00:00"
    }
  }
  ```

#### 3.9.5 创建绩效记录
- **URL**: `POST /api/performance-records`
- **描述**: 创建新绩效记录
- **请求体**:
  ```json
  {
    "employeeId": 1,
    "performancePeriod": "2025-12",
    "performanceScore": 95,
    "performanceLevel": "优秀",
    "performanceComment": "工作表现出色",
    "assessorId": 2
    // id, employeeName, assessorName, createTime, updateTime 不需要填写，自动生成
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,
      "employeeId": 1,
      "employeeName": "测试用户",
      "performancePeriod": "2025-12",
      "performanceScore": 95,
      "performanceLevel": "优秀",
      "performanceComment": "工作表现出色",
      "assessorId": 2,
      "assessorName": "主管",
      "createTime": "2025-12-25T10:00:00",
      "updateTime": "2025-12-25T10:00:00"
    }
  }
  ```

#### 3.9.6 更新绩效记录
- **URL**: `PUT /api/performance-records/{id}`
- **描述**: 更新绩效记录
- **请求参数**:
  - `id`: 绩效记录ID（路径参数）
- **请求体**:
  ```json
  {
    "employeeId": 1,
    "performancePeriod": "2025-12",
    "performanceScore": 98,
    "performanceLevel": "优秀",
    "performanceComment": "工作表现非常出色",
    "assessorId": 2
    // id, employeeName, assessorName, createTime, updateTime 不需要填写或自动更新
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,
      "employeeId": 1,
      "employeeName": "测试用户",
      "performancePeriod": "2025-12",
      "performanceScore": 98,
      "performanceLevel": "优秀",
      "performanceComment": "工作表现非常出色",
      "assessorId": 2,
      "assessorName": "主管",
      "createTime": "2025-12-25T10:00:00",
      "updateTime": "2025-12-25T11:00:00"
    }
  }
  ```

#### 3.9.7 删除绩效记录
- **URL**: `DELETE /api/performance-records/{id}`
- **描述**: 删除绩效记录
- **请求参数**:
  - `id`: 绩效记录ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": "Performance record deleted successfully"
  }
  ```

### 3.10 公告API

#### 3.10.1 获取所有公告（分页）
- **URL**: `GET /api/announcements`
- **描述**: 分页获取所有公告
- **表关联说明**:
  - 涉及表：`announcement`（公告表）、`employee`（员工表）
  - 关联字段：`publisherId`（发布人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 公告ID，对应announcement表的id字段（自动生成）
          "title": "公司年会通知",       // 公告标题，对应announcement表的title字段
          "content": "公司将于2025年12月30日举行年会，请全体员工准时参加。", // 公告内容，对应announcement表的content字段
          "startTime": "2025-12-25T00:00:00", // 开始时间，对应announcement表的start_time字段
          "endTime": "2025-12-30T23:59:59",   // 结束时间，对应announcement表的end_time字段
          "publisherId": 1,             // 发布人ID，对应announcement表的publisher_id字段，关联employee表的id字段
          "publisherName": "管理员",       // 发布人姓名，自动关联employee表的name字段
          "status": "已发布",             // 状态，对应announcement表的status字段
          "createTime": "2025-12-25T10:00:00", // 创建时间，对应announcement表的create_time字段（自动生成）
          "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应announcement表的update_time字段（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.10.2 获取当前有效的公告
- **URL**: `GET /api/announcements/valid`
- **描述**: 获取当前有效的公告
- **表关联说明**:
  - 涉及表：`announcement`（公告表）、`employee`（员工表）
  - 关联字段：`publisherId`（发布人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,                    // 公告ID，对应announcement表的id字段（自动生成）
        "title": "公司年会通知",       // 公告标题，对应announcement表的title字段
        "content": "公司将于2025年12月30日举行年会，请全体员工准时参加。", // 公告内容，对应announcement表的content字段
        "startTime": "2025-12-25T00:00:00", // 开始时间，对应announcement表的start_time字段
        "endTime": "2025-12-30T23:59:59",   // 结束时间，对应announcement表的end_time字段
        "publisherId": 1,             // 发布人ID，对应announcement表的publisher_id字段，关联employee表的id字段
        "publisherName": "管理员",       // 发布人姓名，自动关联employee表的name字段
        "status": "已发布",             // 状态，对应announcement表的status字段
        "createTime": "2025-12-25T10:00:00", // 创建时间，对应announcement表的create_time字段（自动生成）
        "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应announcement表的update_time字段（自动生成）
      }
    ]
  }
  ```

#### 3.10.3 根据ID获取公告
- **URL**: `GET /api/announcements/{id}`
- **描述**: 根据ID获取公告
- **请求参数**:
  - `id`: 公告ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,
      "title": "公司年会通知",
      "content": "公司将于2025年12月30日举行年会，请全体员工准时参加。",
      "startTime": "2025-12-25T00:00:00",
      "endTime": "2025-12-30T23:59:59",
      "publisherId": 1,
      "publisherName": "管理员",
      "status": "已发布",
      "createTime": "2025-12-25T10:00:00",
      "updateTime": "2025-12-25T10:00:00"
    }
  }
  ```

#### 3.10.4 创建公告
- **URL**: `POST /api/announcements`
- **描述**: 创建新公告
- **请求体**:
  ```json
  {
    "title": "公司年会通知",
    "content": "公司将于2025年12月30日举行年会，请全体员工准时参加。",
    "startTime": "2025-12-25T00:00:00",
    "endTime": "2025-12-30T23:59:59",
    "publisherId": 1
    // id, publisherName, status, createTime, updateTime 不需要填写，自动生成
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,
      "title": "公司年会通知",
      "content": "公司将于2025年12月30日举行年会，请全体员工准时参加。",
      "startTime": "2025-12-25T00:00:00",
      "endTime": "2025-12-30T23:59:59",
      "publisherId": 1,
      "publisherName": "管理员",
      "status": "草稿",
      "createTime": "2025-12-25T10:00:00",
      "updateTime": "2025-12-25T10:00:00"
    }
  }
  ```

#### 3.10.5 更新公告
- **URL**: `PUT /api/announcements/{id}`
- **描述**: 更新公告
- **请求参数**:
  - `id`: 公告ID（路径参数）
- **请求体**:
  ```json
  {
    "title": "公司年会通知（更新）",
    "content": "公司将于2025年12月30日举行年会，请全体员工准时参加。时间：下午2点。",
    "startTime": "2025-12-25T00:00:00",
    "endTime": "2025-12-30T23:59:59"
    // id, publisherId, publisherName, status, createTime, updateTime 不需要填写或自动更新
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,
      "title": "公司年会通知（更新）",
      "content": "公司将于2025年12月30日举行年会，请全体员工准时参加。时间：下午2点。",
      "startTime": "2025-12-25T00:00:00",
      "endTime": "2025-12-30T23:59:59",
      "publisherId": 1,
      "publisherName": "管理员",
      "status": "草稿",
      "createTime": "2025-12-25T10:00:00",
      "updateTime": "2025-12-25T11:00:00"
    }
  }
  ```

#### 3.10.6 发布公告
- **URL**: `POST /api/announcements/{id}/publish`
- **描述**: 发布公告
- **请求参数**:
  - `id`: 公告ID（路径参数）
  - `publisherId`: 发布人ID
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,
      "title": "公司年会通知（更新）",
      "content": "公司将于2025年12月30日举行年会，请全体员工准时参加。时间：下午2点。",
      "startTime": "2025-12-25T00:00:00",
      "endTime": "2025-12-30T23:59:59",
      "publisherId": 1,
      "publisherName": "管理员",
      "status": "已发布",
      "createTime": "2025-12-25T10:00:00",
      "updateTime": "2025-12-25T11:30:00"
    }
  }
  ```

#### 3.10.7 删除公告
- **URL**: `DELETE /api/announcements/{id}`
- **描述**: 删除公告
- **请求参数**:
  - `id`: 公告ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": "Announcement deleted successfully"
  }
  ```

#### 3.10.8 记录公告阅读
- **URL**: `POST /api/announcements/{announcementId}/read`
- **描述**: 记录公告阅读
- **请求参数**:
  - `announcementId`: 公告ID（路径参数）
  - `employeeId`: 员工ID
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,
      "announcementId": 2,
      "employeeId": 1,
      "readTime": "2025-12-25T12:00:00",
      "isConfirmed": false
    }
  }
  ```

#### 3.10.9 确认阅读公告
- **URL**: `POST /api/announcements/{announcementId}/confirm-read`
- **描述**: 确认阅读公告
- **请求参数**:
  - `announcementId`: 公告ID（路径参数）
  - `employeeId`: 员工ID
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,
      "announcementId": 2,
      "employeeId": 1,
      "readTime": "2025-12-25T12:00:00",
      "isConfirmed": true
    }
  }
  ```

#### 3.10.10 获取公告的阅读记录
- **URL**: `GET /api/announcements/{announcementId}/read-records`
- **描述**: 获取公告的阅读记录
- **请求参数**:
  - `announcementId`: 公告ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,
        "announcementId": 2,
        "employeeId": 1,
        "employeeName": "测试用户",
        "readTime": "2025-12-25T12:00:00",
        "isConfirmed": true
      }
    ]
  }
  ```

#### 3.10.11 获取员工的公告阅读记录
- **URL**: `GET /api/announcements/employee/{employeeId}/read-records`
- **描述**: 获取员工的公告阅读记录
- **请求参数**:
  - `employeeId`: 员工ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,
        "announcementId": 2,
        "announcementTitle": "公司年会通知（更新）",
        "employeeId": 1,
        "readTime": "2025-12-25T12:00:00",
        "isConfirmed": true
      }
    ]
  }
  ```

### 3.11 考勤记录API

#### 3.11.1 获取所有考勤记录（分页）
- **URL**: `GET /api/attendance-records`
- **描述**: 分页获取所有考勤记录
- **表关联说明**:
  - 涉及表：`attendance_record`（考勤记录表）、`employee`（员工表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 考勤记录ID，对应attendance_record表的id字段（自动生成）
          "employeeId": 1,             // 员工ID，对应attendance_record表的employee_id字段，关联employee表的id字段
          "employeeName": "测试用户",   // 员工姓名，自动关联employee表的name字段
          "attendanceDate": "2025-12-25", // 考勤日期，对应attendance_record表的attendance_date字段
          "checkInTime": "2025-12-25T09:00:00", // 上班时间，对应attendance_record表的check_in_time字段
          "checkOutTime": "2025-12-25T18:00:00", // 下班时间，对应attendance_record表的check_out_time字段
          "workHours": 9.0,            // 工作时长，对应attendance_record表的work_hours字段
          "status": "正常",             // 状态，对应attendance_record表的status字段
          "overtimeHours": 1.0,         // 加班时长，对应attendance_record表的overtime_hours字段
          "createTime": "2025-12-25T18:30:00", // 创建时间，对应attendance_record表的create_time字段（自动生成）
          "updateTime": "2025-12-25T18:30:00"  // 更新时间，对应attendance_record表的update_time字段（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.11.2 根据员工ID获取考勤记录
- **URL**: `GET /api/attendance-records/employee/{employeeId}`
- **描述**: 根据员工ID获取考勤记录列表
- **表关联说明**:
  - 涉及表：`attendance_record`（考勤记录表）、`employee`（员工表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `employeeId`: 员工ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,                    // 考勤记录ID，对应attendance_record表的id字段（自动生成）
        "employeeId": 1,             // 员工ID，对应attendance_record表的employee_id字段，关联employee表的id字段
        "employeeName": "测试用户",   // 员工姓名，自动关联employee表的name字段
        "attendanceDate": "2025-12-25", // 考勤日期，对应attendance_record表的attendance_date字段
        "checkInTime": "2025-12-25T09:00:00", // 上班时间，对应attendance_record表的check_in_time字段
        "checkOutTime": "2025-12-25T18:00:00", // 下班时间，对应attendance_record表的check_out_time字段
        "workHours": 9.0,            // 工作时长，对应attendance_record表的work_hours字段
        "status": "正常",             // 状态，对应attendance_record表的status字段
        "overtimeHours": 1.0,         // 加班时长，对应attendance_record表的overtime_hours字段
        "createTime": "2025-12-25T18:30:00", // 创建时间，对应attendance_record表的create_time字段（自动生成）
        "updateTime": "2025-12-25T18:30:00"  // 更新时间，对应attendance_record表的update_time字段（自动生成）
      }
    ]
  }
  ```

#### 3.11.3 根据ID获取考勤记录
- **URL**: `GET /api/attendance-records/{id}`
- **描述**: 根据ID获取考勤记录
- **请求参数**:
  - `id`: 考勤记录ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,
      "employeeId": 1,
      "employeeName": "测试用户",
      "attendanceDate": "2025-12-25",
      "checkInTime": "2025-12-25T09:00:00",
      "checkOutTime": "2025-12-25T18:00:00",
      "workHours": 9.0,
      "status": "正常",
      "overtimeHours": 1.0,
      "createTime": "2025-12-25T18:30:00",
      "updateTime": "2025-12-25T18:30:00"
    }
  }
  ```

#### 3.11.4 创建考勤记录
- **URL**: `POST /api/attendance-records`
- **描述**: 创建新考勤记录
- **请求体**:
  ```json
  {
    "employeeId": 1,
    "attendanceDate": "2025-12-26",
    "checkInTime": "2025-12-26T09:00:00",
    "checkOutTime": "2025-12-26T18:00:00",
    "workHours": 9.0,
    "status": "正常",
    "overtimeHours": 1.0
    // id, employeeName, createTime, updateTime 不需要填写，自动生成
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,
      "employeeId": 1,
      "employeeName": "测试用户",
      "attendanceDate": "2025-12-26",
      "checkInTime": "2025-12-26T09:00:00",
      "checkOutTime": "2025-12-26T18:00:00",
      "workHours": 9.0,
      "status": "正常",
      "overtimeHours": 1.0,
      "createTime": "2025-12-26T18:30:00",
      "updateTime": "2025-12-26T18:30:00"
    }
  }
  ```

#### 3.11.5 更新考勤记录
- **URL**: `PUT /api/attendance-records/{id}`
- **描述**: 更新考勤记录
- **请求参数**:
  - `id`: 考勤记录ID（路径参数）
- **请求体**:
  ```json
  {
    "checkInTime": "2025-12-26T09:30:00",
    "checkOutTime": "2025-12-26T18:30:00",
    "workHours": 9.0,
    "status": "迟到",
    "overtimeHours": 1.5
    // id, employeeId, employeeName, attendanceDate, createTime, updateTime 不需要填写或自动更新
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,
      "employeeId": 1,
      "employeeName": "测试用户",
      "attendanceDate": "2025-12-26",
      "checkInTime": "2025-12-26T09:30:00",
      "checkOutTime": "2025-12-26T18:30:00",
      "workHours": 9.0,
      "status": "迟到",
      "overtimeHours": 1.5,
      "createTime": "2025-12-26T18:30:00",
      "updateTime": "2025-12-26T19:00:00"
    }
  }
  ```

#### 3.11.6 删除考勤记录
- **URL**: `DELETE /api/attendance-records/{id}`
- **描述**: 删除考勤记录
- **请求参数**:
  - `id`: 考勤记录ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": "Attendance record deleted successfully"
  }
  ```

### 3.12 财务支出记录API

#### 3.12.1 获取所有财务支出记录（分页）
- **URL**: `GET /api/financial-expenses`
- **描述**: 分页获取所有财务支出记录
- **表关联说明**:
  - 涉及表：`financial_expense`（财务支出记录表）、`employee`（员工表）
  - 关联字段：`creatorId`（创建人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 财务支出记录ID，对应financial_expense表的id字段（自动生成）
          "expenseType": "办公用品",       // 支出类型，对应financial_expense表的expense_type字段
          "amount": 1000.0,            // 支出金额，对应financial_expense表的amount字段
          "description": "购买办公桌椅",     // 支出描述，对应financial_expense表的description字段
          "creatorId": 1,              // 创建人ID，对应financial_expense表的creator_id字段（外键，关联employee表）
          "creatorName": "管理员",         // 创建人姓名，自动关联employee表的name字段
          "approvalStatus": "已批准",       // 审批状态，对应financial_expense表的approval_status字段
          "approvalDate": "2025-12-25T10:00:00", // 审批日期，对应financial_expense表的approval_date字段
          "expenseDate": "2025-12-25",   // 支出日期，对应financial_expense表的expense_date字段
          "createTime": "2025-12-25T09:00:00", // 创建时间，对应financial_expense表的create_time字段（自动生成）
          "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应financial_expense表的update_time字段（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.12.2 根据支出类型获取财务支出记录
- **URL**: `GET /api/financial-expenses/type/{type}`
- **描述**: 根据支出类型获取财务支出记录列表
- **表关联说明**:
  - 涉及表：`financial_expense`（财务支出记录表）、`employee`（员工表）
  - 关联字段：`creatorId`（创建人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `type`: 支出类型（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,                    // 财务支出记录ID，对应financial_expense表的id字段（自动生成）
        "expenseType": "办公用品",       // 支出类型，对应financial_expense表的expense_type字段
        "amount": 1000.0,            // 支出金额，对应financial_expense表的amount字段
        "description": "购买办公桌椅",     // 支出描述，对应financial_expense表的description字段
        "creatorId": 1,              // 创建人ID，对应financial_expense表的creator_id字段（外键，关联employee表）
        "creatorName": "管理员",         // 创建人姓名，自动关联employee表的name字段
        "approvalStatus": "已批准",       // 审批状态，对应financial_expense表的approval_status字段
        "approvalDate": "2025-12-25T10:00:00", // 审批日期，对应financial_expense表的approval_date字段
        "expenseDate": "2025-12-25",   // 支出日期，对应financial_expense表的expense_date字段
        "createTime": "2025-12-25T09:00:00", // 创建时间，对应financial_expense表的create_time字段（自动生成）
        "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应financial_expense表的update_time字段（自动生成）
      }
    ]
  }
  ```

#### 3.12.3 根据创建人ID获取财务支出记录
- **URL**: `GET /api/financial-expenses/creator/{creatorId}`
- **描述**: 根据创建人ID获取财务支出记录列表
- **表关联说明**:
  - 涉及表：`financial_expense`（财务支出记录表）、`employee`（员工表）
  - 关联字段：`creatorId`（创建人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `creatorId`: 创建人ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,                    // 财务支出记录ID，对应financial_expense表的id字段（自动生成）
        "expenseType": "办公用品",       // 支出类型，对应financial_expense表的expense_type字段
        "amount": 1000.0,            // 支出金额，对应financial_expense表的amount字段
        "description": "购买办公桌椅",     // 支出描述，对应financial_expense表的description字段
        "creatorId": 1,              // 创建人ID，对应financial_expense表的creator_id字段（外键，关联employee表）
        "creatorName": "管理员",         // 创建人姓名，自动关联employee表的name字段
        "approvalStatus": "已批准",       // 审批状态，对应financial_expense表的approval_status字段
        "approvalDate": "2025-12-25T10:00:00", // 审批日期，对应financial_expense表的approval_date字段
        "expenseDate": "2025-12-25",   // 支出日期，对应financial_expense表的expense_date字段
        "createTime": "2025-12-25T09:00:00", // 创建时间，对应financial_expense表的create_time字段（自动生成）
        "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应financial_expense表的update_time字段（自动生成）
      }
    ]
  }
  ```

#### 3.12.4 根据日期范围获取财务支出记录
- **URL**: `GET /api/financial-expenses/date-range`
- **描述**: 根据日期范围获取财务支出记录列表
- **表关联说明**:
  - 涉及表：`financial_expense`（财务支出记录表）、`employee`（员工表）
  - 关联字段：`creatorId`（创建人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `startDate`: 开始日期（格式：yyyy-MM-dd）
  - `endDate`: 结束日期（格式：yyyy-MM-dd）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,                    // 财务支出记录ID，对应financial_expense表的id字段（自动生成）
        "expenseType": "办公用品",       // 支出类型，对应financial_expense表的expense_type字段
        "amount": 1000.0,            // 支出金额，对应financial_expense表的amount字段
        "description": "购买办公桌椅",     // 支出描述，对应financial_expense表的description字段
        "creatorId": 1,              // 创建人ID，对应financial_expense表的creator_id字段（外键，关联employee表）
        "creatorName": "管理员",         // 创建人姓名，自动关联employee表的name字段
        "approvalStatus": "已批准",       // 审批状态，对应financial_expense表的approval_status字段
        "approvalDate": "2025-12-25T10:00:00", // 审批日期，对应financial_expense表的approval_date字段
        "expenseDate": "2025-12-25",   // 支出日期，对应financial_expense表的expense_date字段
        "createTime": "2025-12-25T09:00:00", // 创建时间，对应financial_expense表的create_time字段（自动生成）
        "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应financial_expense表的update_time字段（自动生成）
      }
    ]
  }
  ```

#### 3.12.5 根据ID获取财务支出记录
- **URL**: `GET /api/financial-expenses/{id}`
- **描述**: 根据ID获取财务支出记录
- **表关联说明**:
  - 涉及表：`financial_expense`（财务支出记录表）、`employee`（员工表）
  - 关联字段：`creatorId`（创建人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `id`: 财务支出记录ID（路径参数）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,                    // 财务支出记录ID，对应financial_expense表的id字段（自动生成）
      "expenseType": "办公用品",       // 支出类型，对应financial_expense表的expense_type字段
      "amount": 1000.0,            // 支出金额，对应financial_expense表的amount字段
      "description": "购买办公桌椅",     // 支出描述，对应financial_expense表的description字段
      "creatorId": 1,              // 创建人ID，对应financial_expense表的creator_id字段（外键，关联employee表）
      "creatorName": "管理员",         // 创建人姓名，自动关联employee表的name字段
      "approvalStatus": "已批准",       // 审批状态，对应financial_expense表的approval_status字段
      "approvalDate": "2025-12-25T10:00:00", // 审批日期，对应financial_expense表的approval_date字段
      "expenseDate": "2025-12-25",   // 支出日期，对应financial_expense表的expense_date字段
      "createTime": "2025-12-25T09:00:00", // 创建时间，对应financial_expense表的create_time字段（自动生成）
      "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应financial_expense表的update_time字段（自动生成）
    }
  }
  ```

#### 3.12.6 创建财务支出记录
- **URL**: `POST /api/financial-expenses`
- **描述**: 创建新财务支出记录
- **表关联说明**:
  - 涉及表：`financial_expense`（财务支出记录表）、`employee`（员工表）
  - 关联字段：`creatorId`（创建人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 修改表：`financial_expense`（新增一条财务支出记录）
- **请求体**:
  ```json
  {
    "expenseType": "办公用品", // 必填，支出类型，对应financial_expense表的expense_type字段
    "amount": 1000.0,            // 必填，支出金额，对应financial_expense表的amount字段（必须大于0）
    "description": "购买办公桌椅",     // 必填，支出描述，对应financial_expense表的description字段
    "creatorId": 1,              // 必填，创建人ID，对应financial_expense表的creator_id字段（外键，必须存在于employee表中）
    "expenseDate": "2025-12-25"  // 必填，支出日期，对应financial_expense表的expense_date字段
    // id: 财务支出记录ID，对应financial_expense表的id字段，自动生成，不需要填写
    // creatorName: 创建人姓名，自动关联employee表的name字段，不需要填写
    // approvalStatus: 审批状态，对应financial_expense表的approval_status字段，自动生成初始为"待批准"，不需要填写
    // approvalDate: 审批日期，对应financial_expense表的approval_date字段，初始为null，不需要填写
    // createTime: 创建时间，对应financial_expense表的create_time字段，自动生成，不需要填写
    // updateTime: 更新时间，对应financial_expense表的update_time字段，自动生成，不需要填写
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,                    // 财务支出记录ID，对应financial_expense表的id字段（自动生成）
      "expenseType": "办公用品",       // 支出类型，对应financial_expense表的expense_type字段
      "amount": 1000.0,            // 支出金额，对应financial_expense表的amount字段
      "description": "购买办公桌椅",     // 支出描述，对应financial_expense表的description字段
      "creatorId": 1,              // 创建人ID，对应financial_expense表的creator_id字段（外键，关联employee表）
      "creatorName": "管理员",         // 创建人姓名，自动关联employee表的name字段
      "approvalStatus": "待批准",       // 审批状态，对应financial_expense表的approval_status字段（自动生成）
      "approvalDate": null,        // 审批日期，对应financial_expense表的approval_date字段（初始为null）
      "expenseDate": "2025-12-25",   // 支出日期，对应financial_expense表的expense_date字段
      "createTime": "2025-12-25T09:00:00", // 创建时间，对应financial_expense表的create_time字段（自动生成）
      "updateTime": "2025-12-25T09:00:00"  // 更新时间，对应financial_expense表的update_time字段（自动生成）
    }
  }
  ```

#### 3.12.7 更新财务支出记录
- **URL**: `PUT /api/financial-expenses/{id}`
- **描述**: 更新财务支出记录
- **表关联说明**:
  - 涉及表：`financial_expense`（财务支出记录表）、`employee`（员工表）
  - 关联字段：`creatorId`（创建人ID）关联`employee.id`（员工ID）
- **操作影响的表**:
  - 修改表：`financial_expense`（更新一条财务支出记录）
- **请求参数**:
  - `id`: 财务支出记录ID（路径参数，必须存在于financial_expense表中）
- **请求体**:
  ```json
  {
    "expenseType": "办公用品", // 必填，支出类型，对应financial_expense表的expense_type字段
    "amount": 1500.0,            // 必填，支出金额，对应financial_expense表的amount字段（必须大于0）
    "description": "购买办公桌椅和文件柜", // 必填，支出描述，对应financial_expense表的description字段
    "expenseDate": "2025-12-25"  // 必填，支出日期，对应financial_expense表的expense_date字段
    // id: 财务支出记录ID，自动从路径参数获取，不需要在请求体中填写
    // creatorId: 创建人ID，不允许修改，不需要填写
    // creatorName: 创建人姓名，自动关联employee表的name字段，不需要填写
    // approvalStatus: 审批状态，不允许通过此接口修改，不需要填写
    // approvalDate: 审批日期，不允许通过此接口修改，不需要填写
    // createTime: 创建时间，自动生成，不允许修改，不需要填写
    // updateTime: 更新时间，自动更新，不需要填写
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,                    // 财务支出记录ID，对应financial_expense表的id字段
      "expenseType": "办公用品",       // 支出类型，对应financial_expense表的expense_type字段
      "amount": 1500.0,            // 支出金额，对应financial_expense表的amount字段
      "description": "购买办公桌椅和文件柜", // 支出描述，对应financial_expense表的description字段
      "creatorId": 1,              // 创建人ID，对应financial_expense表的creator_id字段（外键，关联employee表）
      "creatorName": "管理员",         // 创建人姓名，自动关联employee表的name字段
      "approvalStatus": "待批准",       // 审批状态，对应financial_expense表的approval_status字段
      "approvalDate": null,        // 审批日期，对应financial_expense表的approval_date字段
      "expenseDate": "2025-12-25",   // 支出日期，对应financial_expense表的expense_date字段
      "createTime": "2025-12-25T09:00:00", // 创建时间，对应financial_expense表的create_time字段（自动生成，不可修改）
      "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应financial_expense表的update_time字段（自动更新）
    }
  }
  ```

#### 3.12.8 删除财务支出记录
- **URL**: `DELETE /api/financial-expenses/{id}`
- **描述**: 删除财务支出记录
- **表关联说明**:
  - 涉及表：`financial_expense`（财务支出记录表）
- **操作影响的表**:
  - 修改表：`financial_expense`（删除一条财务支出记录）
- **请求参数**:
  - `id`: 财务支出记录ID（路径参数，必须存在于financial_expense表中）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": "Financial expense deleted successfully"
  }
  ```

### 3.13 薪资质疑API

#### 3.13.1 获取所有薪资质疑（分页）
- **URL**: `GET /api/salary-disputes`
- **描述**: 分页获取所有薪资质疑
- **表关联说明**:
  - 涉及表：`salary_dispute`（薪资质疑表）、`employee`（员工表）、`salary_detail`（薪资明细表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`salaryDetailId`（薪资明细ID）关联`salary_detail.id`（薪资明细ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `page`: 页码，默认0
  - `size`: 每页大小，默认10
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "content": [
        {
          "id": 1,                    // 薪资质疑ID，对应salary_dispute表的id字段（自动生成）
          "employeeId": 1,             // 员工ID，对应salary_dispute表的employee_id字段（外键，关联employee表）
          "employeeName": "测试用户",     // 员工姓名，自动关联employee表的name字段
          "salaryDetailId": 1,         // 薪资明细ID，对应salary_dispute表的salary_detail_id字段（外键，关联salary_detail表）
          "disputeReason": "工资计算错误",   // 质疑原因，对应salary_dispute表的dispute_reason字段
          "disputeDescription": "我的加班工资计算不正确", // 质疑描述，对应salary_dispute表的dispute_description字段
          "submissionTime": "2025-12-25T10:00:00", // 提交时间，对应salary_dispute表的submission_time字段
          "status": "待处理",           // 状态，对应salary_dispute表的status字段
          "hrResponse": null,          // HR回复，对应salary_dispute表的hr_response字段
          "hrResponseTime": null,      // HR回复时间，对应salary_dispute表的hr_response_time字段
          "createTime": "2025-12-25T10:00:00", // 创建时间，对应salary_dispute表的create_time字段（自动生成）
          "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应salary_dispute表的update_time字段（自动生成）
        }
      ],
      "totalElements": 1,
      "totalPages": 1,
      "size": 10,
      "number": 0
    }
  }
  ```

#### 3.13.2 根据员工ID获取薪资质疑
- **URL**: `GET /api/salary-disputes/employee/{employeeId}`
- **描述**: 根据员工ID获取薪资质疑列表
- **表关联说明**:
  - 涉及表：`salary_dispute`（薪资质疑表）、`employee`（员工表）、`salary_detail`（薪资明细表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`salaryDetailId`（薪资明细ID）关联`salary_detail.id`（薪资明细ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `employeeId`: 员工ID（路径参数，必须存在于employee表中）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,                    // 薪资质疑ID，对应salary_dispute表的id字段（自动生成）
        "employeeId": 1,             // 员工ID，对应salary_dispute表的employee_id字段（外键，关联employee表）
        "employeeName": "测试用户",     // 员工姓名，自动关联employee表的name字段
        "salaryDetailId": 1,         // 薪资明细ID，对应salary_dispute表的salary_detail_id字段（外键，关联salary_detail表）
        "disputeReason": "工资计算错误",   // 质疑原因，对应salary_dispute表的dispute_reason字段
        "disputeDescription": "我的加班工资计算不正确", // 质疑描述，对应salary_dispute表的dispute_description字段
        "submissionTime": "2025-12-25T10:00:00", // 提交时间，对应salary_dispute表的submission_time字段
        "status": "待处理",           // 状态，对应salary_dispute表的status字段
        "hrResponse": null,          // HR回复，对应salary_dispute表的hr_response字段
        "hrResponseTime": null,      // HR回复时间，对应salary_dispute表的hr_response_time字段
        "createTime": "2025-12-25T10:00:00", // 创建时间，对应salary_dispute表的create_time字段（自动生成）
        "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应salary_dispute表的update_time字段（自动生成）
      }
    ]
  }
  ```

#### 3.13.3 根据薪资明细ID获取薪资质疑
- **URL**: `GET /api/salary-disputes/salary-detail/{salaryDetailId}`
- **描述**: 根据薪资明细ID获取薪资质疑列表
- **表关联说明**:
  - 涉及表：`salary_dispute`（薪资质疑表）、`employee`（员工表）、`salary_detail`（薪资明细表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`salaryDetailId`（薪资明细ID）关联`salary_detail.id`（薪资明细ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `salaryDetailId`: 薪资明细ID（路径参数，必须存在于salary_detail表中）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,                    // 薪资质疑ID，对应salary_dispute表的id字段（自动生成）
        "employeeId": 1,             // 员工ID，对应salary_dispute表的employee_id字段（外键，关联employee表）
        "employeeName": "测试用户",     // 员工姓名，自动关联employee表的name字段
        "salaryDetailId": 1,         // 薪资明细ID，对应salary_dispute表的salary_detail_id字段（外键，关联salary_detail表）
        "disputeReason": "工资计算错误",   // 质疑原因，对应salary_dispute表的dispute_reason字段
        "disputeDescription": "我的加班工资计算不正确", // 质疑描述，对应salary_dispute表的dispute_description字段
        "submissionTime": "2025-12-25T10:00:00", // 提交时间，对应salary_dispute表的submission_time字段
        "status": "待处理",           // 状态，对应salary_dispute表的status字段
        "hrResponse": null,          // HR回复，对应salary_dispute表的hr_response字段
        "hrResponseTime": null,      // HR回复时间，对应salary_dispute表的hr_response_time字段
        "createTime": "2025-12-25T10:00:00", // 创建时间，对应salary_dispute表的create_time字段（自动生成）
        "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应salary_dispute表的update_time字段（自动生成）
      }
    ]
  }
  ```

#### 3.13.4 根据状态获取薪资质疑
- **URL**: `GET /api/salary-disputes/status/{status}`
- **描述**: 根据状态获取薪资质疑列表
- **表关联说明**:
  - 涉及表：`salary_dispute`（薪资质疑表）、`employee`（员工表）、`salary_detail`（薪资明细表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`salaryDetailId`（薪资明细ID）关联`salary_detail.id`（薪资明细ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `status`: 状态（路径参数，可选值：待处理、已处理、已解决）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "id": 1,                    // 薪资质疑ID，对应salary_dispute表的id字段（自动生成）
        "employeeId": 1,             // 员工ID，对应salary_dispute表的employee_id字段（外键，关联employee表）
        "employeeName": "测试用户",     // 员工姓名，自动关联employee表的name字段
        "salaryDetailId": 1,         // 薪资明细ID，对应salary_dispute表的salary_detail_id字段（外键，关联salary_detail表）
        "disputeReason": "工资计算错误",   // 质疑原因，对应salary_dispute表的dispute_reason字段
        "disputeDescription": "我的加班工资计算不正确", // 质疑描述，对应salary_dispute表的dispute_description字段
        "submissionTime": "2025-12-25T10:00:00", // 提交时间，对应salary_dispute表的submission_time字段
        "status": "待处理",           // 状态，对应salary_dispute表的status字段
        "hrResponse": null,          // HR回复，对应salary_dispute表的hr_response字段
        "hrResponseTime": null,      // HR回复时间，对应salary_dispute表的hr_response_time字段
        "createTime": "2025-12-25T10:00:00", // 创建时间，对应salary_dispute表的create_time字段（自动生成）
        "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应salary_dispute表的update_time字段（自动生成）
      }
    ]
  }
  ```

#### 3.13.5 根据ID获取薪资质疑
- **URL**: `GET /api/salary-disputes/{id}`
- **描述**: 根据ID获取薪资质疑
- **表关联说明**:
  - 涉及表：`salary_dispute`（薪资质疑表）、`employee`（员工表）、`salary_detail`（薪资明细表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`salaryDetailId`（薪资明细ID）关联`salary_detail.id`（薪资明细ID）
- **操作影响的表**:
  - 仅查询，不修改任何数据库表
- **请求参数**:
  - `id`: 薪资质疑ID（路径参数，必须存在于salary_dispute表中）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,                    // 薪资质疑ID，对应salary_dispute表的id字段（自动生成）
      "employeeId": 1,             // 员工ID，对应salary_dispute表的employee_id字段（外键，关联employee表）
      "employeeName": "测试用户",     // 员工姓名，自动关联employee表的name字段
      "salaryDetailId": 1,         // 薪资明细ID，对应salary_dispute表的salary_detail_id字段（外键，关联salary_detail表）
      "disputeReason": "工资计算错误",   // 质疑原因，对应salary_dispute表的dispute_reason字段
      "disputeDescription": "我的加班工资计算不正确", // 质疑描述，对应salary_dispute表的dispute_description字段
      "submissionTime": "2025-12-25T10:00:00", // 提交时间，对应salary_dispute表的submission_time字段
      "status": "待处理",           // 状态，对应salary_dispute表的status字段
      "hrResponse": null,          // HR回复，对应salary_dispute表的hr_response字段
      "hrResponseTime": null,      // HR回复时间，对应salary_dispute表的hr_response_time字段
      "createTime": "2025-12-25T10:00:00", // 创建时间，对应salary_dispute表的create_time字段（自动生成）
      "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应salary_dispute表的update_time字段（自动生成）
    }
  }
  ```

#### 3.13.6 员工提交薪资质疑
- **URL**: `POST /api/salary-disputes`
- **描述**: 员工提交薪资质疑
- **表关联说明**:
  - 涉及表：`salary_dispute`（薪资质疑表）、`employee`（员工表）、`salary_detail`（薪资明细表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`salaryDetailId`（薪资明细ID）关联`salary_detail.id`（薪资明细ID）
- **操作影响的表**:
  - 修改表：`salary_dispute`（新增一条薪资质疑记录）
- **请求体**:
  ```json
  {
    "employeeId": 1,              // 必填，员工ID，对应salary_dispute表的employee_id字段（外键，必须存在于employee表中）
    "salaryDetailId": 1,         // 必填，薪资明细ID，对应salary_dispute表的salary_detail_id字段（外键，必须存在于salary_detail表中）
    "disputeReason": "工资计算错误",   // 必填，质疑原因，对应salary_dispute表的dispute_reason字段
    "disputeDescription": "我的加班工资计算不正确" // 必填，质疑描述，对应salary_dispute表的dispute_description字段
    // id: 薪资质疑ID，对应salary_dispute表的id字段，自动生成，不需要填写
    // employeeName: 员工姓名，自动关联employee表的name字段，不需要填写
    // submissionTime: 提交时间，对应salary_dispute表的submission_time字段，自动生成，不需要填写
    // status: 状态，对应salary_dispute表的status字段，自动生成初始为"待处理"，不需要填写
    // hrResponse: HR回复，对应salary_dispute表的hr_response字段，初始为null，不需要填写
    // hrResponseTime: HR回复时间，对应salary_dispute表的hr_response_time字段，初始为null，不需要填写
    // createTime: 创建时间，对应salary_dispute表的create_time字段，自动生成，不需要填写
    // updateTime: 更新时间，对应salary_dispute表的update_time字段，自动生成，不需要填写
  }
  ```
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,                    // 薪资质疑ID，对应salary_dispute表的id字段（自动生成）
      "employeeId": 1,             // 员工ID，对应salary_dispute表的employee_id字段（外键，关联employee表）
      "employeeName": "测试用户",     // 员工姓名，自动关联employee表的name字段
      "salaryDetailId": 1,         // 薪资明细ID，对应salary_dispute表的salary_detail_id字段（外键，关联salary_detail表）
      "disputeReason": "工资计算错误",   // 质疑原因，对应salary_dispute表的dispute_reason字段
      "disputeDescription": "我的加班工资计算不正确", // 质疑描述，对应salary_dispute表的dispute_description字段
      "submissionTime": "2025-12-25T10:00:00", // 提交时间，对应salary_dispute表的submission_time字段（自动生成）
      "status": "待处理",           // 状态，对应salary_dispute表的status字段（自动生成）
      "hrResponse": null,          // HR回复，对应salary_dispute表的hr_response字段（初始为null）
      "hrResponseTime": null,      // HR回复时间，对应salary_dispute表的hr_response_time字段（初始为null）
      "createTime": "2025-12-25T10:00:00", // 创建时间，对应salary_dispute表的create_time字段（自动生成）
      "updateTime": "2025-12-25T10:00:00"  // 更新时间，对应salary_dispute表的update_time字段（自动生成）
    }
  }
  ```

#### 3.13.7 HR解答薪资质疑
- **URL**: `POST /api/salary-disputes/{id}/respond`
- **描述**: HR解答薪资质疑
- **表关联说明**:
  - 涉及表：`salary_dispute`（薪资质疑表）、`employee`（员工表）、`salary_detail`（薪资明细表）
  - 关联字段：`employeeId`（员工ID）关联`employee.id`（员工ID），`salaryDetailId`（薪资明细ID）关联`salary_detail.id`（薪资明细ID）
- **操作影响的表**:
  - 修改表：`salary_dispute`（更新一条薪资质疑记录）
- **请求参数**:
  - `id`: 薪资质疑ID（路径参数，必须存在于salary_dispute表中）
  - `hrResponse`: HR解答内容（必填）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 2,                    // 薪资质疑ID，对应salary_dispute表的id字段
      "employeeId": 1,             // 员工ID，对应salary_dispute表的employee_id字段（外键，关联employee表）
      "employeeName": "测试用户",     // 员工姓名，自动关联employee表的name字段
      "salaryDetailId": 1,         // 薪资明细ID，对应salary_dispute表的salary_detail_id字段（外键，关联salary_detail表）
      "disputeReason": "工资计算错误",   // 质疑原因，对应salary_dispute表的dispute_reason字段
      "disputeDescription": "我的加班工资计算不正确", // 质疑描述，对应salary_dispute表的dispute_description字段
      "submissionTime": "2025-12-25T10:00:00", // 提交时间，对应salary_dispute表的submission_time字段
      "status": "已解答",           // 状态，对应salary_dispute表的status字段（自动更新为"已解答"）
      "hrResponse": "您的加班工资计算正确，详细情况请查看薪资明细。", // HR回复，对应salary_dispute表的hr_response字段
      "hrResponseTime": "2025-12-25T11:00:00", // HR回复时间，对应salary_dispute表的hr_response_time字段（自动生成）
      "createTime": "2025-12-25T10:00:00", // 创建时间，对应salary_dispute表的create_time字段（自动生成）
      "updateTime": "2025-12-25T11:00:00"  // 更新时间，对应salary_dispute表的update_time字段（自动更新）
    }
  }
  ```

#### 3.13.8 删除薪资质疑
- **URL**: `DELETE /api/salary-disputes/{id}`
- **描述**: 删除薪资质疑
- **表关联说明**:
  - 涉及表：`salary_dispute`（薪资质疑表）
- **操作影响的表**:
  - 修改表：`salary_dispute`（删除一条薪资质疑记录）
- **请求参数**:
  - `id`: 薪资质疑ID（路径参数，必须存在于salary_dispute表中）
- **响应**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": "Salary dispute deleted successfully"
  }
  ```

## 4. 通用响应格式

所有API响应遵循以下格式：

```json
{
  "code": 200,           // 状态码：200=成功，其他=错误
  "message": "success",  // 结果描述
  "data": {}             // 响应数据（错误响应时可选）
}
```

## 5. 错误码说明

| 状态码 | 描述 |
|--------|------|
| 200 | 成功 |
| 400 | 请求错误（验证失败、缺少参数等） |
| 401 | 未授权（无效的凭证） |
| 404 | 资源未找到 |
| 500 | 服务器内部错误 |

## 6. 验证规则

### 6.1 通用验证规则

| 字段类型 | 验证规则 |
|----------|----------|
| 用户名 | 必填，4-50个字符，唯一 |
| 密码 | 必填，6-100个字符 |
| 邮箱 | 有效的邮箱格式 |
| 外键 | 必须存在于被引用的表中 |
| 字符串 | 有最大长度限制 |
| 数字 | 有最小值限制 |

### 6.2 字段特定规则

| 字段 | 验证规则 |
|------|----------|
| Department.code | 唯一，必填 |
| Department.name | 唯一，必填 |
| Employee.username | 唯一，4-50个字符 |
| Employee.email | 有效的邮箱格式 |
| AssetCatalog.stockQuantity | 大于等于0 |
| AssetCatalog.price | 大于等于0 |

## 7. API测试指南

### 7.1 数据创建顺序

创建数据时，请按照以下顺序进行，以确保外键约束得到满足：

1. 创建部门（departments）
2. 创建职位（positions）
3. 创建员工（employees）
4. 创建资产目录（asset-catalogs）
5. 创建资产申请（asset-applications）
6. 创建请假申请（leave-applications）
7. 创建薪资明细（salary-details）

### 7.2 必填字段注意事项

创建或更新记录时，请务必提供所有必填字段。必填字段请参考数据库结构部分。

### 7.3 外键引用要求

确保外键值在被引用的表中存在，否则会导致500错误。

## 8. 示例工作流

### 8.1 创建员工

1. **创建部门**:
   ```json
   POST /api/departments
   {
     "code": "DEP001",  // 必填
     "name": "技术部"   // 必填
     // 其他可选字段可根据需要添加
   }
   ```

2. **创建职位**:
   ```json
   POST /api/positions
   {
     "name": "开发工程师",  // 必填
     "departmentId": 1      // 必填，引用上一步创建的部门ID
   }
   ```

3. **创建员工**:
   ```json
   POST /api/employees
   {
     "username": "testuser",    // 必填
     "password": "123456",      // 必填
     "name": "测试用户",       // 必填
     "departmentId": 1,          // 必填，引用部门ID
     "positionId": 1             // 必填，引用职位ID
   }
   ```

### 8.2 创建请假申请

1. **确保员工存在** (id: 1)

2. **创建请假申请**:
   ```json
   POST /api/leave-applications
   {
     "employeeId": 1,                 // 必填，引用员工ID
     "type": "年假",                   // 必填
     "startTime": "2025-12-26T09:00:00", // 必填
     "endTime": "2025-12-27T18:00:00",   // 必填
     "reason": "回家过年"               // 必填
   }
   ```

## 9. 结论

本文档提供了HR管理系统所有API的详细信息，包括：
- API端点和请求/响应格式
- 数据库表关系
- 必填字段和验证规则
- 示例请求和响应

通过遵循本文档，前端开发人员可以有效地与后端API集成，并确保数据处理的正确性。