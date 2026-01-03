# 企业人力资源管理系统（HRMS）完善计划

## 一、当前项目分析

### 1. 现有技术栈

- Spring Boot 3.1.0
- Spring Data JPA（数据持久层）
- MySQL（数据库）
- Lombok（简化代码）

### 2. 现有功能

- 员工基本信息管理（CRUD）
- 简单登录认证
- CORS配置

### 3. 现有项目结构

```
com.hr
├── config/          # 配置类
├── controller/      # 控制器
├── converter/       # DTO与实体转换
├── dao/             # 数据访问层（JPA Repository）
├── dto/             # 数据传输对象
├── entity/          # 实体类
├── service/         # 服务层
└── HrApplication.java  # 启动类
```

## 二、需求对比与差距分析

### 1. 技术栈差距

- 需要替换Spring Data JPA为MyBatis-Plus
- 需要添加数据校验、日志记录、全局异常处理

### 2. 功能模块差距

- 缺少角色权限管理
- 缺少部门管理
- 缺少资产信息管理
- 缺少薪资管理
- 缺少财务支出管理
- 缺少审批流程（请假、资产申请）
- 缺少信息发布（公告管理）

## 三、完善计划

### 1. 技术栈调整（Step 1）

**修改pom.xml**

- 移除spring-boot-starter-data-jpa依赖
- 添加mybatis-plus-boot-starter依赖
- 添加spring-boot-starter-validation（数据校验）
- 添加spring-boot-starter-logging（日志记录）

**修改application.properties**

- 配置MyBatis-Plus相关属性
- 配置日志级别

**修改项目结构**

- 将dao目录下的JPA Repository替换为MyBatis-Plus Mapper
- 添加mapper.xml目录，存放SQL映射文件

### 2. 数据库设计（Step 2）

**创建核心数据表**

1. sys_user（用户表）
2. sys_role（角色表）
3. sys_user_role（用户角色关联表）
4. hr_employee（员工表）
5. hr_department（部门表）
6. hr_asset（资产表）
7. hr_asset_apply（资产申请表）
8. hr_leave_apply（请假申请表）
9. hr_salary（薪资表）
10. hr_finance（财务支出表）
11. hr_notice（公告表）
12. sys_oper_log（操作日志表）
13. sys_read_log（阅读日志表）

**生成数据库脚本**

- 编写完整的建表SQL，包含字段注释、主键外键、索引
- 插入初始数据（角色、管理员用户、示例部门、员工）

### 3. 公共组件实现（Step 3）

**全局异常处理**

- 创建GlobalExceptionHandler类，统一处理系统异常、业务异常

**数据校验**

- 添加JSR-380注解进行请求参数校验
- 实现自定义校验器

**日志记录**

- 使用AOP实现操作日志记录
- 记录关键操作：登录、修改、删除、审批等

**统一响应格式**

- 完善ResponseDto，统一所有接口返回格式

### 4. 核心模块实现（Step 4-7）

#### （1）登录与权限管理模块（Step 4）

**实体类**：

- SysUser：用户信息
- SysRole：角色信息
- SysUserRole：用户角色关联

**DTO类**：

- LoginDto：登录请求
- PasswordChangeDto：密码修改请求

**Controller**：

- AuthController：登录、密码修改

**Service**：

- AuthService：登录验证、密码修改
- PermissionService：权限验证（基于角色）

#### （2）核心数据管理模块（Step 5）

**部门管理**：

- Department实体：部门信息（含父子部门关系）
- DepartmentController：部门CRUD
- DepartmentService：部门业务逻辑（含删除约束）

**员工信息管理**：

- 扩展现有Employee实体，添加部门、角色关联
- 修改EmployeeController，添加多条件查询
- 添加员工信息校验

**资产信息管理**：

- Asset实体：资产基本信息
- AssetController：资产CRUD、申请处理
- AssetService：资产业务逻辑

**薪资信息管理**：

- Salary实体：薪资明细
- SalaryController：薪资录入、审批、查询
- SalaryService：薪资计算、审批流程

**财务支出管理**：

- Finance实体：财务支出记录
- FinanceController：支出CRUD、查询
- FinanceService：支出业务逻辑

#### （3）审批流程模块（Step 6）

**请假审批**：

- LeaveApply实体：请假申请信息
- LeaveApplyController：请假申请、审批处理
- LeaveApplyService：请假流程管理

**资产申请审批**：

- AssetApply实体：资产申请信息
- AssetApplyController：资产申请、审批处理
- AssetApplyService：资产申请流程管理

#### （4）信息发布模块（Step 7）

**公告管理**：

- Notice实体：公告信息
- NoticeController：公告发布、编辑、删除、查询
- NoticeService：公告业务逻辑

## 四、实现优先级

1. 技术栈调整与基础架构搭建
2. 登录与权限管理模块
3. 部门信息管理（基础数据）
4. 员工信息管理（核心数据）
5. 资产信息管理
6. 审批流程模块
7. 薪资与财务支出管理
8. 信息发布模块
9. 公共组件完善

## 五、代码规范与质量

1. 严格遵循分层架构设计
2. 所有接口返回统一格式
3. 关键操作添加日志记录
4. 数据变更添加事务管理
5. 实现全局异常处理
6. 添加必要的注释
7. 确保代码可运行、无语法错误

## 六、预期成果

1. 完整的企业人力资源管理系统后端
2. 符合需求的所有功能模块
3. 完整的数据库脚本和初始数据
4. 清晰的API文档
5. 可直接运行的代码

## 七、注意事项

1. 登录密码无需加密，直接存储
2. 权限控制基于角色，不实现细粒度权限
3. 审批流程按指定角色层级处理
4. 所有日志记录基础信息即可
5. 尽可能使用现有依赖，避免引入过多新依赖
6. 严格按照需求文档实现功能，不添加额外功能

通过以上计划，我将按照需求逐步完善企业人力资源管理系统，确保系统功能完整、架构清晰、代码质量可靠。