-- 创建数据库（开发环境全量重建，避免重复外键等错误）
DROP DATABASE IF EXISTS hr_management;
CREATE DATABASE hr_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE hr_management;

-- 1. 部门表
CREATE TABLE IF NOT EXISTS department
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    code        VARCHAR(50)                                                     NOT NULL UNIQUE COMMENT '部门编号',
    name        VARCHAR(100)                                                    NOT NULL UNIQUE COMMENT '部门名称',
    parent_id   BIGINT COMMENT '上级部门ID',
    manager_id  BIGINT COMMENT '部门负责人ID',
    description TEXT COMMENT '部门描述',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='部门表';

-- 2. 职位表
CREATE TABLE IF NOT EXISTS position
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100)                        NOT NULL COMMENT '职位名称',
    department_id BIGINT                              NOT NULL COMMENT '部门ID',
    description   TEXT COMMENT '职位描述',
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='职位表';

-- 3. 员工表
CREATE TABLE IF NOT EXISTS employee
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50)                         NOT NULL UNIQUE COMMENT '用户名',
    password      VARCHAR(100)                        NOT NULL COMMENT '密码',
    name          VARCHAR(100)                        NOT NULL COMMENT '姓名',
    email         VARCHAR(100) COMMENT '邮箱',
    phone         VARCHAR(20) COMMENT '电话',
    department_id BIGINT COMMENT '部门ID',
    position_id   BIGINT COMMENT '职位ID',
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='员工表';

-- 4. 请假申请表
CREATE TABLE IF NOT EXISTS leave_application
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id     BIGINT                                                          NOT NULL COMMENT '申请人ID',
    type            VARCHAR(50)                                                     NOT NULL COMMENT '请假类型',
    start_time      TIMESTAMP                                                       NOT NULL COMMENT '开始时间',
    end_time        TIMESTAMP                                                       NOT NULL COMMENT '结束时间',
    reason          TEXT                                                            NOT NULL COMMENT '请假原因',
    attachment      VARCHAR(255) COMMENT '相关证明文件路径',
    status          VARCHAR(50)                                                     NOT NULL COMMENT '状态：待主管审批、待管理员审批、已驳回、已批准',
    manager_comment TEXT COMMENT '主管审批意见',
    admin_comment   TEXT COMMENT '管理员审批意见',
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='请假申请表';

-- 5. 资产目录表
CREATE TABLE IF NOT EXISTS asset_catalog
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    type           VARCHAR(50)                                                     NOT NULL COMMENT '资产类型',
    name           VARCHAR(100)                                                    NOT NULL COMMENT '资产名称',
    description    TEXT COMMENT '资产描述',
    stock_quantity INT                                                             NOT NULL DEFAULT 0 COMMENT '库存数量',
    unit           VARCHAR(20)                                                     NOT NULL COMMENT '单位',
    price          DOUBLE                                                          NOT NULL COMMENT '单价',
    budget_limit   DOUBLE COMMENT '预算限制',
    status         VARCHAR(50)                                                     NOT NULL COMMENT '状态：可用、不可用',
    create_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='资产目录表';

-- 6. 资产申请表
CREATE TABLE IF NOT EXISTS asset_application
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id      BIGINT                                                          NOT NULL COMMENT '申请人ID',
    asset_id         BIGINT                                                          NOT NULL COMMENT '资产ID',
    request_quantity INT                                                             NOT NULL COMMENT '申请数量',
    purpose          TEXT                                                            NOT NULL COMMENT '申请用途',
    status           VARCHAR(50)                                                     NOT NULL COMMENT '状态：待主管审批、待管理员审批、已驳回、已分配、已完成',
    manager_comment  TEXT COMMENT '主管审批意见',
    admin_comment    TEXT COMMENT '管理员审批意见',
    create_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='资产申请表';

-- 7. 资产分配记录表
CREATE TABLE IF NOT EXISTS asset_distribution
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id        BIGINT                                                          NOT NULL COMMENT '资产申请ID',
    asset_id              BIGINT                                                          NOT NULL COMMENT '资产ID',
    distribution_quantity INT                                                             NOT NULL COMMENT '分配数量',
    employee_id           BIGINT                                                          NOT NULL COMMENT '接收员工ID',
    distribution_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '分配时间',
    receive_status        VARCHAR(50)                                                     NOT NULL COMMENT '接收状态：待接收、已接收、有异议',
    employee_feedback     TEXT COMMENT '员工反馈意见',
    admin_process_result  TEXT COMMENT '管理员处理结果',
    create_time           TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='资产分配记录表';

-- 8. 薪资明细表
CREATE TABLE IF NOT EXISTS salary_detail
(
    id                        BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id               BIGINT                                                          NOT NULL COMMENT '员工ID',
    salary_period             VARCHAR(20)                                                     NOT NULL COMMENT '薪资周期（如2025-01）',
    basic_salary              DOUBLE                                                          NOT NULL COMMENT '基本工资',
    performance_bonus         DOUBLE COMMENT '绩效奖金',
    allowance                 DOUBLE COMMENT '津贴补贴',
    overtime_pay              DOUBLE COMMENT '加班工资',
    social_security_deduction DOUBLE COMMENT '社保扣款',
    housing_fund_deduction    DOUBLE COMMENT '公积金扣款',
    tax_deduction             DOUBLE COMMENT '个税扣款',
    other_deductions          DOUBLE COMMENT '其他扣款',
    net_salary                DOUBLE                                                          NOT NULL COMMENT '实发工资',
    status                    VARCHAR(50)                                                     NOT NULL COMMENT '薪资状态：待审核、已审核、已发放、已驳回',
    auditor_id                BIGINT COMMENT '审核人ID',
    audit_time                TIMESTAMP COMMENT '审核时间',
    payment_time              TIMESTAMP COMMENT '发放时间',
    create_time               TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time               TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='薪资明细表';

-- 9. 考勤记录表
CREATE TABLE IF NOT EXISTS attendance_record
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id    BIGINT                                                          NOT NULL COMMENT '员工ID',
    record_date    DATE                                                            NOT NULL COMMENT '记录日期',
    check_in_time  TIME COMMENT '上班打卡时间',
    check_out_time TIME COMMENT '下班打卡时间',
    status         VARCHAR(50)                                                     NOT NULL COMMENT '考勤状态：正常、迟到、早退、旷工、请假',
    leave_hours    DOUBLE    DEFAULT 0 COMMENT '请假小时数',
    overtime_hours DOUBLE    DEFAULT 0 COMMENT '加班小时数',
    description    TEXT COMMENT '备注',
    create_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='考勤记录表';

-- 10. 绩效记录表
CREATE TABLE IF NOT EXISTS performance_record
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id        BIGINT                                                          NOT NULL COMMENT '员工ID',
    performance_period VARCHAR(20)                                                     NOT NULL COMMENT '绩效周期（如2025-01）',
    score              DOUBLE                                                          NOT NULL COMMENT '绩效得分',
    grade              VARCHAR(20)                                                     NOT NULL COMMENT '绩效等级',
    comment            TEXT COMMENT '绩效评价',
    appraiser_id       BIGINT                                                          NOT NULL COMMENT '评价人ID',
    create_time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='绩效记录表';

-- 11. 公告表
CREATE TABLE IF NOT EXISTS announcement
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(200)                                                    NOT NULL COMMENT '标题',
    content        TEXT                                                            NOT NULL COMMENT '正文内容',
    type           VARCHAR(50)                                                     NOT NULL COMMENT '公告类型',
    valid_from     TIMESTAMP                                                       NOT NULL COMMENT '有效期开始时间',
    valid_to       TIMESTAMP                                                       NOT NULL COMMENT '有效期结束时间',
    publish_scope  VARCHAR(50)                                                     NOT NULL COMMENT '发布范围：全部、部门、特定人员',
    department_ids VARCHAR(255) COMMENT '关联的部门ID（多个部门用逗号分隔）',
    employee_ids   VARCHAR(255) COMMENT '关联的人员ID（多个人员用逗号分隔）',
    attachments    VARCHAR(255) COMMENT '附件路径（多个附件用逗号分隔）',
    status         VARCHAR(50)                                                     NOT NULL COMMENT '状态：草稿、已发布、已删除',
    publisher_id   BIGINT                                                          NOT NULL COMMENT '发布人ID',
    publish_time   TIMESTAMP COMMENT '发布时间',
    version        INT                                                             NOT NULL DEFAULT 1 COMMENT '版本号',
    create_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='公告表';

-- 12. 公告阅读记录表
CREATE TABLE IF NOT EXISTS announcement_read_record
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    announcement_id BIGINT                              NOT NULL COMMENT '公告ID',
    employee_id     BIGINT                              NOT NULL COMMENT '员工ID',
    read_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '阅读时间',
    is_confirmed    INT                                 NOT NULL DEFAULT 0 COMMENT '是否已确认阅读：0-未确认，1-已确认',
    confirm_time    TIMESTAMP COMMENT '确认阅读时间',
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='公告阅读记录表';

-- 13. 财务支出记录表
CREATE TABLE IF NOT EXISTS financial_expense
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    type           VARCHAR(50)                                                     NOT NULL COMMENT '支出类型',
    amount         DOUBLE                                                          NOT NULL COMMENT '支出金额',
    expense_date   DATE                                                            NOT NULL COMMENT '支出日期',
    purpose        TEXT                                                            NOT NULL COMMENT '支出用途',
    related_id     BIGINT COMMENT '关联的项目或申请ID（如资产申请ID、报销申请ID等）',
    related_type   VARCHAR(50) COMMENT '关联类型（如资产申请、报销申请等）',
    payment_method VARCHAR(50)                                                     NOT NULL COMMENT '支付方式：现金、银行转账、支付宝、微信等',
    remark         TEXT COMMENT '备注',
    creator_id     BIGINT                                                          NOT NULL COMMENT '创建人ID',
    create_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='财务支出记录表';

-- 14. 薪资异议表
CREATE TABLE IF NOT EXISTS salary_dispute
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id      BIGINT                                                          NOT NULL COMMENT '员工ID',
    salary_detail_id BIGINT                                                          NOT NULL COMMENT '薪资明细ID',
    dispute_content  TEXT                                                            NOT NULL COMMENT '异议内容',
    status           VARCHAR(50)                                                     NOT NULL COMMENT '处理状态：待处理、已处理、已驳回',
    hr_reply         TEXT COMMENT 'HR回复',
    handler_id       BIGINT COMMENT '处理人ID',
    handle_time      TIMESTAMP COMMENT '处理时间',
    create_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    update_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='薪资异议表';

-- 15. 审批记录表
CREATE TABLE IF NOT EXISTS approval_record
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_type VARCHAR(50)                         NOT NULL COMMENT '申请类型：请假申请、资产申请等',
    application_id   BIGINT                              NOT NULL COMMENT '申请ID',
    approver_id      BIGINT                              NOT NULL COMMENT '审批人ID',
    approval_result  VARCHAR(50)                         NOT NULL COMMENT '审批结果：通过、驳回',
    approval_comment TEXT COMMENT '审批意见',
    approval_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '审批时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='审批记录表';

-- 添加外键约束
ALTER TABLE department
    ADD CONSTRAINT fk_department_parent FOREIGN KEY (parent_id) REFERENCES department (id) ON DELETE SET NULL;
ALTER TABLE department
    ADD CONSTRAINT fk_department_manager FOREIGN KEY (manager_id) REFERENCES employee (id) ON DELETE SET NULL;

ALTER TABLE position
    ADD CONSTRAINT fk_position_department FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE CASCADE;

ALTER TABLE employee
    ADD CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE SET NULL;
ALTER TABLE employee
    ADD CONSTRAINT fk_employee_position FOREIGN KEY (position_id) REFERENCES position (id) ON DELETE SET NULL;

ALTER TABLE leave_application
    ADD CONSTRAINT fk_leave_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE;

ALTER TABLE asset_application
    ADD CONSTRAINT fk_asset_application_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE;
ALTER TABLE asset_application
    ADD CONSTRAINT fk_asset_application_asset FOREIGN KEY (asset_id) REFERENCES asset_catalog (id) ON DELETE CASCADE;

ALTER TABLE asset_distribution
    ADD CONSTRAINT fk_distribution_application FOREIGN KEY (application_id) REFERENCES asset_application (id) ON DELETE CASCADE;
ALTER TABLE asset_distribution
    ADD CONSTRAINT fk_distribution_asset FOREIGN KEY (asset_id) REFERENCES asset_catalog (id) ON DELETE CASCADE;
ALTER TABLE asset_distribution
    ADD CONSTRAINT fk_distribution_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE;

ALTER TABLE salary_detail
    ADD CONSTRAINT fk_salary_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE;
ALTER TABLE salary_detail
    ADD CONSTRAINT fk_salary_auditor FOREIGN KEY (auditor_id) REFERENCES employee (id) ON DELETE SET NULL;

ALTER TABLE attendance_record
    ADD CONSTRAINT fk_attendance_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE;

ALTER TABLE performance_record
    ADD CONSTRAINT fk_performance_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE;
ALTER TABLE performance_record
    ADD CONSTRAINT fk_performance_appraiser FOREIGN KEY (appraiser_id) REFERENCES employee (id) ON DELETE CASCADE;

ALTER TABLE announcement
    ADD CONSTRAINT fk_announcement_publisher FOREIGN KEY (publisher_id) REFERENCES employee (id) ON DELETE CASCADE;

ALTER TABLE announcement_read_record
    ADD CONSTRAINT fk_read_announcement FOREIGN KEY (announcement_id) REFERENCES announcement (id) ON DELETE CASCADE;
ALTER TABLE announcement_read_record
    ADD CONSTRAINT fk_read_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE;

ALTER TABLE financial_expense
    ADD CONSTRAINT fk_expense_creator FOREIGN KEY (creator_id) REFERENCES employee (id) ON DELETE CASCADE;

ALTER TABLE salary_dispute
    ADD CONSTRAINT fk_dispute_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE;
ALTER TABLE salary_dispute
    ADD CONSTRAINT fk_dispute_salary FOREIGN KEY (salary_detail_id) REFERENCES salary_detail (id) ON DELETE CASCADE;
ALTER TABLE salary_dispute
    ADD CONSTRAINT fk_dispute_handler FOREIGN KEY (handler_id) REFERENCES employee (id) ON DELETE SET NULL;

ALTER TABLE approval_record
    ADD CONSTRAINT fk_approval_approver FOREIGN KEY (approver_id) REFERENCES employee (id) ON DELETE CASCADE;

-- 插入示例数据

-- 部门示例数据
INSERT INTO department (code, name, parent_id, description)
VALUES ('DEPT001', '人力资源部', NULL, '负责公司人力资源管理'),
       ('DEPT002', '财务部', NULL, '负责公司财务管理'),
       ('DEPT003', '技术部', NULL, '负责公司技术研发'),
       ('DEPT004', '市场部', NULL, '负责公司市场推广'),
       ('DEPT005', '销售部', NULL, '负责公司产品销售');

-- 职位示例数据
INSERT INTO position (name, department_id, description)
VALUES ('人力资源总监', 1, '负责人力资源部全面工作'),
       ('招聘专员', 1, '负责公司招聘工作'),
       ('薪酬专员', 1, '负责公司薪酬管理'),
       ('财务总监', 2, '负责财务部全面工作'),
       ('会计', 2, '负责公司会计核算'),
       ('出纳', 2, '负责公司现金管理'),
       ('技术总监', 3, '负责技术部全面工作'),
       ('高级开发工程师', 3, '负责公司核心技术开发'),
       ('开发工程师', 3, '负责公司产品开发'),
       ('市场总监', 4, '负责市场部全面工作'),
       ('市场专员', 4, '负责公司市场推广'),
       ('销售总监', 5, '负责销售部全面工作'),
       ('销售经理', 5, '负责销售团队管理'),
       ('销售代表', 5, '负责公司产品销售');

-- 员工示例数据
INSERT INTO employee (username, password, name, email, phone, department_id, position_id)
VALUES ('admin', '123456', '管理员', 'admin@hr.com', '13800138000', 1, 1),
       ('zhangsan', '123456', '张三', 'zhangsan@hr.com', '13800138001', 1, 2),
       ('lisi', '123456', '李四', 'lisi@hr.com', '13800138002', 2, 5),
       ('wangwu', '123456', '王五', 'wangwu@hr.com', '13800138003', 3, 8),
       ('zhaoliu', '123456', '赵六', 'zhaoliu@hr.com', '13800138004', 3, 9),
       ('qianqi', '123456', '钱七', 'qianqi@hr.com', '13800138005', 4, 11),
       ('sunba', '123456', '孙八', 'sunba@hr.com', '13800138006', 5, 14),
       ('zhoujiu', '123456', '周九', 'zhoujiu@hr.com', '13800138007', 5, 14),
       ('wushi', '123456', '吴十', 'wushi@hr.com', '13800138008', 2, 6),
       ('chenshi', '123456', '陈十', 'chenshi@hr.com', '13800138009', 1, 3);

-- 更新部门负责人
UPDATE department
SET manager_id = 1
WHERE id = 1;
UPDATE department
SET manager_id = 4
WHERE id = 2;
UPDATE department
SET manager_id = 5
WHERE id = 3;
UPDATE department
SET manager_id = 7
WHERE id = 4;
UPDATE department
SET manager_id = 9
WHERE id = 5;

-- 资产目录示例数据
INSERT INTO asset_catalog (type, name, description, stock_quantity, unit, price, budget_limit, status)
VALUES ('办公设备', '笔记本电脑', '联想ThinkPad X1 Carbon', 50, '台', 12000.00, 1000000.00, '可用'),
       ('办公设备', '台式电脑', '戴尔OptiPlex 7090', 30, '台', 8000.00, 500000.00, '可用'),
       ('办公设备', '打印机', '惠普LaserJet Pro M404n', 20, '台', 3000.00, 100000.00, '可用'),
       ('办公用品', 'A4纸', '80g A4打印纸', 1000, '包', 25.00, 50000.00, '可用'),
       ('办公用品', '笔记本', '得力笔记本', 500, '本', 15.00, 10000.00, '可用'),
       ('电子设备', '手机', 'iPhone 15 Pro', 10, '部', 9999.00, 200000.00, '可用'),
       ('电子设备', '平板', 'iPad Pro 12.9', 5, '台', 8999.00, 100000.00, '可用');

-- 请假申请示例数据
INSERT INTO leave_application (employee_id, type, start_time, end_time, reason, status, manager_comment, admin_comment)
VALUES (2, '病假', '2025-12-15 09:00:00', '2025-12-17 18:00:00', '感冒发烧，需要休息', '已批准', '同意请假', '同意请假'),
       (5, '年假', '2025-12-20 09:00:00', '2025-12-25 18:00:00', '回家探亲', '待主管审批', NULL, NULL),
       (7, '事假', '2025-12-18 09:00:00', '2025-12-18 18:00:00', '处理个人事务', '已驳回', '工作繁忙，不同意请假', NULL);

-- 资产申请示例数据
INSERT INTO asset_application (employee_id, asset_id, request_quantity, purpose, status, manager_comment, admin_comment)
VALUES (5, 1, 1, '工作需要', '已完成', '同意申请', '同意申请'),
       (6, 3, 1, '部门打印需求', '待管理员审批', '同意申请', NULL),
       (8, 4, 10, '部门办公用品', '已驳回', '申请数量过多', NULL);

-- 资产分配示例数据
INSERT INTO asset_distribution (application_id, asset_id, distribution_quantity, employee_id, receive_status)
VALUES (1, 1, 1, 5, '已接收'),
       (2, 3, 1, 6, '待接收');

-- 薪资明细示例数据
INSERT INTO salary_detail (employee_id, salary_period, basic_salary, performance_bonus, allowance, overtime_pay,
                           social_security_deduction, housing_fund_deduction, tax_deduction, other_deductions,
                           net_salary, status, auditor_id, audit_time, payment_time)
VALUES (1, '2025-12', 15000.00, 5000.00, 2000.00, 0.00, 1500.00, 1200.00, 800.00, 0.00, 18500.00, '已发放', 4,
        '2025-12-10 10:00:00', '2025-12-15 15:00:00'),
       (2, '2025-12', 8000.00, 2000.00, 1000.00, 0.00, 800.00, 640.00, 300.00, 0.00, 9260.00, '已发放', 4,
        '2025-12-10 10:00:00', '2025-12-15 15:00:00'),
       (5, '2025-12', 12000.00, 4000.00, 1500.00, 500.00, 1200.00, 960.00, 600.00, 0.00, 14240.00, '已发放', 4,
        '2025-12-10 10:00:00', '2025-12-15 15:00:00'),
       (6, '2025-12', 7000.00, 1500.00, 800.00, 0.00, 700.00, 560.00, 250.00, 0.00, 7790.00, '待审核', NULL, NULL,
        NULL);

-- 考勤记录示例数据
INSERT INTO attendance_record (employee_id, record_date, check_in_time, check_out_time, status, leave_hours,
                               overtime_hours, description)
VALUES (2, '2025-12-10', '09:00:00', '18:00:00', '正常', 0.0, 0.0, NULL),
       (2, '2025-12-11', '09:15:00', '18:00:00', '迟到', 0.0, 0.0, NULL),
       (5, '2025-12-10', '09:00:00', '19:00:00', '正常', 0.0, 1.0, '加班一小时'),
       (6, '2025-12-10', '09:00:00', '17:30:00', '早退', 0.0, 0.0, NULL),
       (2, '2025-12-15', NULL, NULL, '请假', 8.0, 0.0, '病假');

-- 绩效记录示例数据
INSERT INTO performance_record (employee_id, performance_period, score, grade, comment, appraiser_id)
VALUES (2, '2025-12', 95.0, '优秀', '工作表现出色', 1),
       (5, '2025-12', 88.0, '良好', '工作认真负责', 7),
       (6, '2025-12', 82.0, '合格', '工作表现稳定', 7),
       (8, '2025-12', 75.0, '合格', '需要进一步提高', 9);

-- 公告示例数据
INSERT INTO announcement (title, content, type, valid_from, valid_to, publish_scope, status, publisher_id, publish_time)
VALUES ('2025年元旦放假通知', '根据国家规定，2025年元旦放假时间为1月1日至1月3日，共3天。', '节假日通知',
        '2025-12-01 00:00:00', '2025-12-31 23:59:59', '全部', '已发布', 1, '2025-12-01 10:00:00'),
       ('关于2025年年度绩效考核的通知', '2025年年度绩效考核将于12月20日开始，请各部门做好准备。', '公司通知',
        '2025-12-01 00:00:00', '2025-12-31 23:59:59', '全部', '已发布', 1, '2025-12-01 14:00:00'),
       ('技术部项目进度会议通知', '技术部将于12月15日下午2点召开项目进度会议，请全体技术人员参加。', '部门通知',
        '2025-12-01 00:00:00', '2025-12-15 23:59:59', '部门', '已发布', 7, '2025-12-01 16:00:00');

-- 更新公告的部门ID
UPDATE announcement
SET department_ids = '3'
WHERE id = 3;

-- 公告阅读记录示例数据
INSERT INTO announcement_read_record (announcement_id, employee_id, is_confirmed, confirm_time)
VALUES (1, 2, 1, '2025-12-01 10:30:00'),
       (1, 5, 1, '2025-12-01 11:00:00'),
       (2, 2, 1, '2025-12-01 14:30:00'),
       (3, 5, 1, '2025-12-01 16:30:00'),
       (3, 6, 1, '2025-12-01 17:00:00');

-- 财务支出示例数据
INSERT INTO financial_expense (type, amount, expense_date, purpose, related_id, related_type, payment_method,
                               creator_id)
VALUES ('办公用品采购', 2500.00, '2025-12-01', '采购A4纸和笔记本', 3, 'asset_application', '银行转账', 1),
       ('工资发放', 150000.00, '2025-12-15', '12月份工资发放', NULL, NULL, '银行转账', 4),
       ('设备采购', 60000.00, '2025-12-05', '采购笔记本电脑', 1, 'asset_application', '银行转账', 4);

-- 薪资异议示例数据
INSERT INTO salary_dispute (employee_id, salary_detail_id, dispute_content, status, hr_reply, handler_id, handle_time)
VALUES (6, 4, '12月份绩效奖金计算有误', '已处理', '经核实，加班工资计算正确，详情请查看绩效评分表', 4,
        '2025-12-20 10:00:00');

-- 审批记录示例数据
INSERT INTO approval_record (application_type, application_id, approver_id, approval_result, approval_comment)
VALUES ('leave_application', 1, 1, '通过', '同意请假'),
       ('leave_application', 1, 4, '通过', '同意请假'),
       ('asset_application', 1, 7, '通过', '同意申请'),
       ('asset_application', 1, 4, '通过', '同意申请'),
       ('asset_application', 3, 9, '驳回', '申请数量过多');

-- 查看所有表
SHOW TABLES;

-- 查看表数据
SELECT 'department' AS table_name, COUNT(*) AS record_count
FROM department
UNION ALL
SELECT 'position' AS table_name, COUNT(*) AS record_count
FROM position
UNION ALL
SELECT 'employee' AS table_name, COUNT(*) AS record_count
FROM employee
UNION ALL
SELECT 'leave_application' AS table_name, COUNT(*) AS record_count
FROM leave_application
UNION ALL
SELECT 'asset_catalog' AS table_name, COUNT(*) AS record_count
FROM asset_catalog
UNION ALL
SELECT 'asset_application' AS table_name, COUNT(*) AS record_count
FROM asset_application
UNION ALL
SELECT 'asset_distribution' AS table_name, COUNT(*) AS record_count
FROM asset_distribution
UNION ALL
SELECT 'salary_detail' AS table_name, COUNT(*) AS record_count
FROM salary_detail
UNION ALL
SELECT 'attendance_record' AS table_name, COUNT(*) AS record_count
FROM attendance_record
UNION ALL
SELECT 'performance_record' AS table_name, COUNT(*) AS record_count
FROM performance_record
UNION ALL
SELECT 'announcement' AS table_name, COUNT(*) AS record_count
FROM announcement
UNION ALL
SELECT 'announcement_read_record' AS table_name, COUNT(*) AS record_count
FROM announcement_read_record
UNION ALL
SELECT 'financial_expense' AS table_name, COUNT(*) AS record_count
FROM financial_expense
UNION ALL
SELECT 'salary_dispute' AS table_name, COUNT(*) AS record_count
FROM salary_dispute
UNION ALL
SELECT 'approval_record' AS table_name, COUNT(*) AS record_count
FROM approval_record;
