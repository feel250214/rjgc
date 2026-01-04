-- 示例数据插入脚本，不直接插入主键ID值
-- 删除现有数据，避免重复插入
DELETE
FROM announcement_read_record;
DELETE
FROM approval_record;
DELETE
FROM asset_distribution;
DELETE
FROM asset_application;
DELETE
FROM attendance_record;
DELETE
FROM financial_expense;
DELETE
FROM leave_application;
DELETE
FROM performance_record;
DELETE
FROM salary_dispute;
DELETE
FROM salary_detail;
DELETE
FROM announcement;
DELETE
FROM employee;
DELETE
FROM position;
DELETE
FROM asset_catalog;
DELETE
FROM department;

-- 1. 插入部门数据
-- 先插入所有顶级部门
INSERT INTO department (code, name, parent_id, description)
VALUES ('DEPT001', '人力资源部', NULL, '负责公司人力资源管理'),
       ('DEPT002', '技术部', NULL, '负责公司技术开发'),
       ('DEPT003', '财务部', NULL, '负责公司财务管理'),
       ('DEPT004', '市场部', NULL, '负责公司市场推广'),
       ('DEPT005', '销售部', NULL, '负责公司产品销售');

-- 再插入子部门
INSERT INTO department (code, name, parent_id, description)
VALUES ('DEPT00201', '前端组', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT002') AS t),
        '负责前端开发'),
       ('DEPT00202', '后端组', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT002') AS t),
        '负责后端开发');

-- 2. 插入职位数据
INSERT INTO position (name, department_id, description)
VALUES ('人力资源经理', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT001') AS t),
        '负责人力资源部门管理'),
       ('招聘专员', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT001') AS t), '负责员工招聘工作'),
       ('技术总监', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT002') AS t), '负责技术部门管理'),
       ('前端开发工程师', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT00201') AS t),
        '负责前端开发工作'),
       ('后端开发工程师', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT00202') AS t),
        '负责后端开发工作'),
       ('财务经理', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT003') AS t), '负责财务部门管理'),
       ('会计', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT003') AS t), '负责财务核算工作'),
       ('市场经理', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT004') AS t), '负责市场部门管理'),
       ('销售经理', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT005') AS t), '负责销售部门管理'),
       ('销售人员', (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT005') AS t), '负责产品销售工作');

-- 3. 插入资产目录数据
INSERT INTO asset_catalog (type, name, description, stock_quantity, unit, price, budget_limit, status)
VALUES ('电子设备', '笔记本电脑', '公司办公用笔记本电脑', 50, '台', 8000.00, 400000.00, '可用'),
       ('电子设备', '显示器', '27英寸显示器', 100, '台', 1500.00, 150000.00, '可用'),
       ('电子设备', '键盘', '机械键盘', 200, '个', 300.00, 60000.00, '可用'),
       ('电子设备', '鼠标', '无线鼠标', 200, '个', 100.00, 20000.00, '可用'),
       ('办公用品', '办公桌', '职员办公桌', 100, '张', 1200.00, 120000.00, '可用'),
       ('办公用品', '办公椅', '人体工学办公椅', 100, '把', 800.00, 80000.00, '可用'),
       ('办公用品', '文件柜', '金属文件柜', 50, '个', 600.00, 30000.00, '可用');

-- 4. 插入员工数据（与 hr_management.sql 的 employee 表结构一致）
INSERT INTO employee (username, password, name, email, phone, department_id, position_id, create_time)
VALUES ('admin', '123456', '管理员', 'admin@example.com', '13800138000',
        (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT001') AS t),
        (SELECT id FROM (SELECT id FROM position WHERE name = '人力资源经理') AS t), NOW()),
       ('zhangsan', '123456', '张三', 'zhangsan@example.com', '13800138001',
        (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT002') AS t),
        (SELECT id FROM (SELECT id FROM position WHERE name = '技术总监') AS t), NOW()),
       ('lisi', '123456', '李四', 'lisi@example.com', '13800138002',
        (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT00201') AS t),
        (SELECT id FROM (SELECT id FROM position WHERE name = '前端开发工程师') AS t), NOW()),
       ('wangwu', '123456', '王五', 'wangwu@example.com', '13800138003',
        (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT00202') AS t),
        (SELECT id FROM (SELECT id FROM position WHERE name = '后端开发工程师') AS t), NOW()),
       ('zhaoliu', '123456', '赵六', 'zhaoliu@example.com', '13800138004',
        (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT003') AS t),
        (SELECT id FROM (SELECT id FROM position WHERE name = '财务经理') AS t), NOW()),
       ('qianqi', '123456', '钱七', 'qianqi@example.com', '13800138005',
        (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT003') AS t),
        (SELECT id FROM (SELECT id FROM position WHERE name = '会计') AS t), NOW()),
       ('sunba', '123456', '孙八', 'sunba@example.com', '13800138006',
        (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT004') AS t),
        (SELECT id FROM (SELECT id FROM position WHERE name = '市场经理') AS t), NOW()),
       ('zhoujiu', '123456', '周九', 'zhoujiu@example.com', '13800138007',
        (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT005') AS t),
        (SELECT id FROM (SELECT id FROM position WHERE name = '销售经理') AS t), NOW()),
       ('wushi', '123456', '吴十', 'wushi@example.com', '13800138008',
        (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT005') AS t),
        (SELECT id FROM (SELECT id FROM position WHERE name = '销售人员') AS t), NOW());


-- 5. 插入公告数据
INSERT INTO announcement (title, content, type, valid_from, valid_to, publish_scope, department_ids, employee_ids,
                          attachments, status, publisher_id, publish_time)
VALUES ('公司年度体检通知', '公司将于2025年6月1日组织年度体检，请各位员工准时参加。', '通知', '2025-05-01 00:00:00',
        '2025-06-01 23:59:59', '全体', NULL, NULL, NULL, '已发布',
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'admin') AS t), NOW()),
       ('技术部项目启动会议', '技术部将于2025年5月15日召开新项目启动会议，请技术部全体员工参加。', '会议',
        '2025-05-10 00:00:00', '2025-05-15 23:59:59', '部门',
        (SELECT id FROM (SELECT id FROM department WHERE code = 'DEPT002') AS t), NULL, NULL, '已发布',
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t), NOW());

-- 6. 插入公告阅读记录数据
INSERT INTO announcement_read_record (announcement_id, employee_id, read_time, is_confirmed, confirm_time)
VALUES ((SELECT id FROM (SELECT id FROM announcement WHERE title = '公司年度体检通知') AS t),
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t), NOW(), 1, NOW()),
       ((SELECT id FROM (SELECT id FROM announcement WHERE title = '公司年度体检通知') AS t),
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t), NOW(), 1, NOW()),
       ((SELECT id FROM (SELECT id FROM announcement WHERE title = '公司年度体检通知') AS t),
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'wangwu') AS t), NOW(), 0, NULL),
       ((SELECT id FROM (SELECT id FROM announcement WHERE title = '技术部项目启动会议') AS t),
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t), NOW(), 1, NOW()),
       ((SELECT id FROM (SELECT id FROM announcement WHERE title = '技术部项目启动会议') AS t),
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t), NOW(), 1, NOW());

-- 7. 插入请假申请数据（需要先插入，因为审批记录会引用它）
INSERT INTO leave_application (employee_id, type, start_time, end_time, reason, attachment, status, manager_comment,
                               admin_comment)
VALUES ((SELECT id FROM (SELECT id FROM employee WHERE username = 'wushi') AS t), '年假', '2025-06-01 00:00:00',
        '2025-06-05 23:59:59', '回家探亲', NULL, '已批准', '同意请假', '已批准'),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t), '病假', '2025-05-10 00:00:00',
        '2025-05-12 23:59:59', '感冒发烧', NULL, '已批准', '同意请假，注意休息', '已批准'),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'wangwu') AS t), '事假', '2025-05-20 00:00:00',
        '2025-05-20 23:59:59', '处理个人事务', NULL, '已批准', '同意请假', '已批准');

-- 8. 插入资产申请数据
INSERT INTO asset_application (employee_id, asset_id, request_quantity, purpose, status, manager_comment, admin_comment)
VALUES ((SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t),
        (SELECT id FROM (SELECT id FROM asset_catalog WHERE name = '笔记本电脑') AS t), 1, '开发工作需要', '已完成',
        '同意申请', '已分配'),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'wangwu') AS t),
        (SELECT id FROM (SELECT id FROM asset_catalog WHERE name = '显示器') AS t), 2, '开发工作需要双显示器', '已完成',
        '同意申请', '已分配');

-- 9. 插入审批记录数据
INSERT INTO approval_record (application_type, application_id, approver_id, approval_result, approval_comment,
                             approval_time)
VALUES ('请假申请', (SELECT id
                     FROM (SELECT id
                           FROM leave_application
                           WHERE employee_id =
                                 (SELECT id FROM (SELECT id FROM employee WHERE username = 'wushi') AS t)) AS t),
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t), '通过', '同意请假', NOW()),
       ('资产申请', (SELECT id
                     FROM (SELECT id
                           FROM asset_application
                           WHERE employee_id =
                                 (SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t)) AS t),
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'admin') AS t), '通过', '同意申请', NOW());

-- 10. 插入资产分配记录数据
INSERT INTO asset_distribution (application_id, asset_id, distribution_quantity, employee_id, distribution_time,
                                receive_status, employee_feedback, admin_process_result)
VALUES ((SELECT id
         FROM (SELECT id
               FROM asset_application
               WHERE employee_id = (SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t)) AS t),
        (SELECT id FROM (SELECT id FROM asset_catalog WHERE name = '笔记本电脑') AS t), 1,
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t), NOW(), '已接收', '设备正常',
        '分配完成'),
       ((SELECT id
         FROM (SELECT id
               FROM asset_application
               WHERE employee_id = (SELECT id FROM (SELECT id FROM employee WHERE username = 'wangwu') AS t)) AS t),
        (SELECT id FROM (SELECT id FROM asset_catalog WHERE name = '显示器') AS t), 2,
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'wangwu') AS t), NOW(), '已接收', '设备正常',
        '分配完成');

-- 11. 插入考勤记录数据
INSERT INTO attendance_record (employee_id, record_date, check_in_time, check_out_time, status, leave_hours,
                               overtime_hours, description)
VALUES ((SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t), '2025-05-01', '09:00:00',
        '18:00:00', '正常', 0, 0, NULL),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t), '2025-05-01', '09:15:00', '18:30:00',
        '迟到', 0, 1, '早上交通堵塞'),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'wangwu') AS t), '2025-05-01', '08:50:00', '19:00:00',
        '正常', 0, 2, '项目赶进度'),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'zhaoliu') AS t), '2025-05-01', '09:00:00',
        '18:00:00', '正常', 0, 0, NULL),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'qianqi') AS t), '2025-05-01', '09:00:00', '17:30:00',
        '正常', 0, 0, NULL);

-- 12. 插入财务支出记录数据
INSERT INTO financial_expense (type, amount, expense_date, purpose, related_id, related_type, payment_method, remark,
                               creator_id)
VALUES ('办公设备采购', 8000.00, '2025-04-15', '购买新员工办公电脑', (SELECT id
                                                                      FROM (SELECT id
                                                                            FROM asset_application
                                                                            WHERE employee_id =
                                                                                  (SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t)) AS t),
        '资产申请', '银行转账', '笔记本电脑采购',
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhaoliu') AS t)),
       ('办公用品采购', 2000.00, '2025-04-20', '购买会议室办公用品', NULL, NULL, '支付宝', '打印纸、笔等',
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'admin') AS t)),
       ('员工培训', 5000.00, '2025-04-25', '技术人员技能培训', NULL, NULL, '银行转账', '前端开发培训',
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t));

-- 13. 插入绩效记录数据
INSERT INTO performance_record (employee_id, performance_period, score, grade, comment, appraiser_id)
VALUES ((SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t), '2025-04', 95, '优秀',
        '工作表现出色，领导能力强', (SELECT id FROM (SELECT id FROM employee WHERE username = 'admin') AS t)),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t), '2025-04', 90, '优秀',
        '前端开发技能突出，工作效率高', (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t)),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'wangwu') AS t), '2025-04', 85, '良好',
        '后端开发能力强，团队合作好', (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t)),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'zhaoliu') AS t), '2025-04', 88, '良好',
        '财务管理能力强，工作认真负责', (SELECT id FROM (SELECT id FROM employee WHERE username = 'admin') AS t)),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'qianqi') AS t), '2025-04', 82, '合格',
        '会计工作认真，需要加强财务分析能力',
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhaoliu') AS t));

-- 14. 插入工资明细数据
INSERT INTO salary_detail (employee_id, salary_period, basic_salary, performance_bonus, allowance, overtime_pay,
                           social_security_deduction, housing_fund_deduction, tax_deduction, other_deductions,
                           net_salary, status, auditor_id, audit_time, payment_time)
VALUES ((SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t), '2025-04', 15000, 5000, 2000, 1000,
        1500, 1200, 800, 0, 19500, '已发放', (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhaoliu') AS t),
        NOW(), NOW()),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'lisi') AS t), '2025-04', 10000, 3000, 1500, 800,
        1000, 800, 500, 0, 12000, '已发放', (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhaoliu') AS t),
        NOW(), NOW()),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'wangwu') AS t), '2025-04', 10000, 3000, 1500, 1200,
        1000, 800, 500, 0, 12400, '已发放', (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhaoliu') AS t),
        NOW(), NOW()),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'zhaoliu') AS t), '2025-04', 12000, 4000, 1800, 500,
        1200, 960, 600, 0, 14540, '已发放', (SELECT id FROM (SELECT id FROM employee WHERE username = 'admin') AS t),
        NOW(), NOW()),
       ((SELECT id FROM (SELECT id FROM employee WHERE username = 'qianqi') AS t), '2025-04', 8000, 2000, 1200, 300,
        800, 640, 400, 0, 9660, '已发放', (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhaoliu') AS t),
        NOW(), NOW());

-- 15. 插入工资争议数据
INSERT INTO salary_dispute (employee_id, salary_detail_id, dispute_content, status, hr_reply, handler_id, handle_time)
VALUES ((SELECT id FROM (SELECT id FROM employee WHERE username = 'qianqi') AS t), (SELECT id
                                                                                    FROM (SELECT id
                                                                                          FROM salary_detail
                                                                                          WHERE employee_id =
                                                                                                (SELECT id FROM (SELECT id FROM employee WHERE username = 'qianqi') AS t)
                                                                                            AND salary_period = '2025-04') AS t),
        '4月份加班工资计算有误', '已处理', '经核实，加班工资计算正确',
        (SELECT id FROM (SELECT id FROM employee WHERE username = 'admin') AS t), NOW());

-- 更新部门经理ID
UPDATE department
SET manager_id = (SELECT id FROM (SELECT id FROM employee WHERE username = 'admin') AS t)
WHERE code = 'DEPT001';
UPDATE department
SET manager_id = (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t)
WHERE code = 'DEPT002';
UPDATE department
SET manager_id = (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhaoliu') AS t)
WHERE code = 'DEPT003';
UPDATE department
SET manager_id = (SELECT id FROM (SELECT id FROM employee WHERE username = 'sunba') AS t)
WHERE code = 'DEPT004';
UPDATE department
SET manager_id = (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhoujiu') AS t)
WHERE code = 'DEPT005';
UPDATE department
SET manager_id = (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t)
WHERE code = 'DEPT00201';
UPDATE department
SET manager_id = (SELECT id FROM (SELECT id FROM employee WHERE username = 'zhangsan') AS t)
WHERE code = 'DEPT00202';
