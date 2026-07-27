-- 商城订单超时关闭任务。执行前确认 2026-07-24-01 建表脚本已成功执行。
-- 使用防重复插入，不覆盖既有同名任务配置。
INSERT INTO sys_job (job_name, job_group, invoke_target, cron_expression, misfire_policy, concurrent, status, create_by, create_time, remark)
SELECT '商城超时订单关闭', 'DEFAULT', 'shopTask.closeExpiredOrders()', '0 * * * * ?', '3', '1', '0', 'admin', NOW(), '扫描并关闭超时待支付订单'
WHERE NOT EXISTS (
  SELECT 1 FROM sys_job WHERE job_name = '商城超时订单关闭' AND job_group = 'DEFAULT'
);
