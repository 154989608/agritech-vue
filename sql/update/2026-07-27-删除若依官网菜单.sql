-- 删除若依官网外链菜单及其角色授权。
-- 精确匹配默认初始化记录，避免影响其他外链菜单。
START TRANSACTION;

DELETE rm
FROM sys_role_menu rm
JOIN sys_menu m ON m.menu_id = rm.menu_id
WHERE m.menu_id = 4
  AND m.menu_name = '若依官网'
  AND m.path = 'http://ruoyi.vip';

DELETE FROM sys_menu
WHERE menu_id = 4
  AND menu_name = '若依官网'
  AND path = 'http://ruoyi.vip';

COMMIT;
