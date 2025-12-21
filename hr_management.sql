-- 创建数据库
CREATE DATABASE IF NOT EXISTS hr_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE hr_management;

-- 创建员工表
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    name VARCHAR(100) NOT NULL COMMENT '姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '电话',
    department VARCHAR(50) COMMENT '部门',
    position VARCHAR(50) COMMENT '职位',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工表';

-- 插入示例数据
INSERT INTO employee (username, password, name, email, phone, department, position) VALUES
('admin', '123456', '管理员', 'admin@example.com', '13800138000', '人力资源部', '经理'),
('user1', '123456', '张三', 'zhangsan@example.com', '13800138001', '技术部', '开发工程师'),
('user2', '123456', '李四', 'lisi@example.com', '13800138002', '市场部', '市场专员'),
('user3', '123456', '王五', 'wangwu@example.com', '13800138003', '财务部', '财务会计'),
('user4', '123456', '赵六', 'zhaoliu@example.com', '13800138004', '技术部', '测试工程师');
