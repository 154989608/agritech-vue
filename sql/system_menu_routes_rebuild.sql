-- Rebuild dynamic menu routes for the pages under src/views/system/*
-- This script only rebuilds sys_menu records.
-- Hidden detail routes such as /system/user-auth, /system/role-auth, and /system/dict-data
-- are still handled by the front-end dynamic routes and do not need sys_menu rows.
--
-- If a non-admin role still cannot see these menus after running this script,
-- grant the menu_ids below in sys_role_menu as needed.

-- 1) Root directory: /system
INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon,
  create_by, create_time, update_by, update_time, remark
) VALUES (
  1, '系统管理', 0, 1, 'system', NULL, '', '',
  1, 0, 'M', '0', '0', '', 'system',
  'admin', SYSDATE(), 'admin', SYSDATE(), 'System management directory'
)
ON DUPLICATE KEY UPDATE
  menu_name = VALUES(menu_name),
  parent_id = VALUES(parent_id),
  order_num = VALUES(order_num),
  path = VALUES(path),
  component = VALUES(component),
  query = VALUES(query),
  route_name = VALUES(route_name),
  is_frame = VALUES(is_frame),
  is_cache = VALUES(is_cache),
  menu_type = VALUES(menu_type),
  visible = VALUES(visible),
  status = VALUES(status),
  perms = VALUES(perms),
  icon = VALUES(icon),
  update_by = VALUES(update_by),
  update_time = VALUES(update_time),
  remark = VALUES(remark);

-- 4) Visualization routes driven by sys_menu -> /getRouters
-- Route paths:
-- /visualization/capacityDiagnosis
-- /visualization/spaceTimeEvolution
-- /visualization/stationHeatEfficiency
INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon,
  create_by, create_time, update_by, update_time, remark
) VALUES (
  2000, '可视化大屏', 0, 5, 'visualization', NULL, '', '',
  1, 0, 'M', '0', '0', '', 'dashboard',
  'admin', SYSDATE(), 'admin', SYSDATE(), '可视化大屏目录'
)
ON DUPLICATE KEY UPDATE
  menu_name = VALUES(menu_name),
  parent_id = VALUES(parent_id),
  order_num = VALUES(order_num),
  path = VALUES(path),
  component = VALUES(component),
  query = VALUES(query),
  route_name = VALUES(route_name),
  is_frame = VALUES(is_frame),
  is_cache = VALUES(is_cache),
  menu_type = VALUES(menu_type),
  visible = VALUES(visible),
  status = VALUES(status),
  perms = VALUES(perms),
  icon = VALUES(icon),
  update_by = VALUES(update_by),
  update_time = VALUES(update_time),
  remark = VALUES(remark);

INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon,
  create_by, create_time, update_by, update_time, remark
) VALUES
  (2001, '运力诊断决策', 2000, 1, 'capacityDiagnosis',      'visualization/capacityDiagnosis/index',      '', 'CapacityDiagnosis',      1, 0, 'C', '0', '0', '', 'dashboard', 'admin', SYSDATE(), 'admin', SYSDATE(), '运力诊断决策菜单'),
  (2002, '时空演化分析', 2000, 2, 'spaceTimeEvolution',     'visualization/spaceTimeEvolution/index',     '', 'SpaceTimeEvolution',     1, 0, 'C', '0', '0', '', 'chart',     'admin', SYSDATE(), 'admin', SYSDATE(), '时空演化分析菜单'),
  (2003, '站点热力效能', 2000, 3, 'stationHeatEfficiency',  'visualization/stationHeatEfficiency/index',  '', 'StationHeatEfficiency',  1, 0, 'C', '0', '0', '', 'chart',     'admin', SYSDATE(), 'admin', SYSDATE(), '站点热力效能菜单')
ON DUPLICATE KEY UPDATE
  menu_name = VALUES(menu_name),
  parent_id = VALUES(parent_id),
  order_num = VALUES(order_num),
  path = VALUES(path),
  component = VALUES(component),
  query = VALUES(query),
  route_name = VALUES(route_name),
  is_frame = VALUES(is_frame),
  is_cache = VALUES(is_cache),
  menu_type = VALUES(menu_type),
  visible = VALUES(visible),
  status = VALUES(status),
  perms = VALUES(perms),
  icon = VALUES(icon),
  update_by = VALUES(update_by),
  update_time = VALUES(update_time),
  remark = VALUES(remark);

-- 2) System pages driven by sys_menu -> /getRouters
INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon,
  create_by, create_time, update_by, update_time, remark
) VALUES
  (100, '用户管理', 1, 1, 'user',   'system/user/index',   '', '', 1, 0, 'C', '0', '0', 'system:user:list',   'user',       'admin', SYSDATE(), 'admin', SYSDATE(), 'System user page'),
  (101, '角色管理', 1, 2, 'role',   'system/role/index',   '', '', 1, 0, 'C', '0', '0', 'system:role:list',   'peoples',    'admin', SYSDATE(), 'admin', SYSDATE(), 'System role page'),
  (102, '菜单管理', 1, 3, 'menu',   'system/menu/index',   '', '', 1, 0, 'C', '0', '0', 'system:menu:list',   'tree-table', 'admin', SYSDATE(), 'admin', SYSDATE(), 'System menu page'),
  (103, '部门管理', 1, 4, 'dept',   'system/dept/index',   '', '', 1, 0, 'C', '0', '0', 'system:dept:list',   'tree',       'admin', SYSDATE(), 'admin', SYSDATE(), 'System dept page'),
  (104, '岗位管理', 1, 5, 'post',   'system/post/index',   '', '', 1, 0, 'C', '0', '0', 'system:post:list',   'post',       'admin', SYSDATE(), 'admin', SYSDATE(), 'System post page'),
  (105, '字典管理', 1, 6, 'dict',   'system/dict/index',   '', '', 1, 0, 'C', '0', '0', 'system:dict:list',   'dict',       'admin', SYSDATE(), 'admin', SYSDATE(), 'System dict page'),
  (106, '参数设置', 1, 7, 'config', 'system/config/index', '', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit',       'admin', SYSDATE(), 'admin', SYSDATE(), 'System config page'),
  (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message',    'admin', SYSDATE(), 'admin', SYSDATE(), 'System notice page')
ON DUPLICATE KEY UPDATE
  menu_name = VALUES(menu_name),
  parent_id = VALUES(parent_id),
  order_num = VALUES(order_num),
  path = VALUES(path),
  component = VALUES(component),
  query = VALUES(query),
  route_name = VALUES(route_name),
  is_frame = VALUES(is_frame),
  is_cache = VALUES(is_cache),
  menu_type = VALUES(menu_type),
  visible = VALUES(visible),
  status = VALUES(status),
  perms = VALUES(perms),
  icon = VALUES(icon),
  update_by = VALUES(update_by),
  update_time = VALUES(update_time),
  remark = VALUES(remark);

-- 3) Button permissions used by the system pages
INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon,
  create_by, create_time, update_by, update_time, remark
) VALUES
  (1000, '用户查询',   100, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',    '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'User query button'),
  (1001, '用户新增',   100, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',      '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'User add button'),
  (1002, '用户修改',   100, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',     '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'User edit button'),
  (1003, '用户删除',   100, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'User remove button'),
  (1004, '用户导出',   100, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'User export button'),
  (1005, '用户导入',   100, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'User import button'),
  (1006, '重置密码',   100, 7, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'User reset password button'),

  (1007, '角色查询',   101, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query',    '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Role query button'),
  (1008, '角色新增',   101, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add',      '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Role add button'),
  (1009, '角色修改',   101, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit',     '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Role edit button'),
  (1010, '角色删除',   101, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Role remove button'),
  (1011, '角色导出',   101, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Role export button'),

  (1012, '菜单查询',   102, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query',    '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Menu query button'),
  (1013, '菜单新增',   102, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add',      '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Menu add button'),
  (1014, '菜单修改',   102, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',     '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Menu edit button'),
  (1015, '菜单删除',   102, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Menu remove button'),

  (1016, '部门查询',   103, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query',    '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Dept query button'),
  (1017, '部门新增',   103, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add',      '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Dept add button'),
  (1018, '部门修改',   103, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',     '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Dept edit button'),
  (1019, '部门删除',   103, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Dept remove button'),

  (1020, '岗位查询',   104, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query',    '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Post query button'),
  (1021, '岗位新增',   104, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add',      '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Post add button'),
  (1022, '岗位修改',   104, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit',     '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Post edit button'),
  (1023, '岗位删除',   104, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Post remove button'),
  (1024, '岗位导出',   104, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Post export button'),

  (1025, '字典查询',   105, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query',    '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Dict query button'),
  (1026, '字典新增',   105, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add',      '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Dict add button'),
  (1027, '字典修改',   105, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit',     '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Dict edit button'),
  (1028, '字典删除',   105, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Dict remove button'),
  (1029, '字典导出',   105, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Dict export button'),

  (1030, '参数查询',   106, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query',  '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Config query button'),
  (1031, '参数新增',   106, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add',    '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Config add button'),
  (1032, '参数修改',   106, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Config edit button'),
  (1033, '参数删除',   106, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Config remove button'),
  (1034, '参数导出',   106, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Config export button'),

  (1035, '公告查询',   107, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query',  '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Notice query button'),
  (1036, '公告新增',   107, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add',    '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Notice add button'),
  (1037, '公告修改',   107, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit',   '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Notice edit button'),
  (1038, '公告删除',   107, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', SYSDATE(), 'admin', SYSDATE(), 'Notice remove button')
ON DUPLICATE KEY UPDATE
  menu_name = VALUES(menu_name),
  parent_id = VALUES(parent_id),
  order_num = VALUES(order_num),
  path = VALUES(path),
  component = VALUES(component),
  query = VALUES(query),
  route_name = VALUES(route_name),
  is_frame = VALUES(is_frame),
  is_cache = VALUES(is_cache),
  menu_type = VALUES(menu_type),
  visible = VALUES(visible),
  status = VALUES(status),
  perms = VALUES(perms),
  icon = VALUES(icon),
  update_by = VALUES(update_by),
  update_time = VALUES(update_time),
  remark = VALUES(remark);
