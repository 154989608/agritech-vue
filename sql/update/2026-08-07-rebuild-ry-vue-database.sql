SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP DATABASE IF EXISTS `ry_vue`;
CREATE DATABASE `ry_vue` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ry_vue`;

-- source: D:\AIruoyi_project\agritech-vue\sql\ry_20250522.sql
-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null auto_increment    comment '部门id',
  parent_id         bigint(20)      default 0                  comment '父部门id',
  ancestors         varchar(50)     default ''                 comment '祖级列表',
  dept_name         varchar(30)     default ''                 comment '部门名称',
  order_num         int(4)          default 0                  comment '显示顺序',
  leader            varchar(20)     default null               comment '负责人',
  phone             varchar(11)     default null               comment '联系电话',
  email             varchar(50)     default null               comment '邮箱',
  status            char(1)         default '0'                comment '部门状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = '部门表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept values(100,  0,   '0',          '若依科技',   0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(101,  100, '0,100',      '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(102,  100, '0,100',      '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(103,  101, '0,100,101',  '研发部门',   1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(104,  101, '0,100,101',  '市场部门',   2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(105,  101, '0,100,101',  '测试部门',   3, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(106,  101, '0,100,101',  '财务部门',   4, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(107,  101, '0,100,101',  '运维部门',   5, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(108,  102, '0,100,102',  '市场部门',   1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(109,  102, '0,100,102',  '财务部门',   2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment '用户ID',
  dept_id           bigint(20)      default null               comment '部门ID',
  user_name         varchar(30)     not null                   comment '用户账号',
  nick_name         varchar(30)     not null                   comment '用户昵称',
  user_type         varchar(2)      default '00'               comment '用户类型（00系统用户）',
  email             varchar(50)     default ''                 comment '用户邮箱',
  phonenumber       varchar(11)     default ''                 comment '手机号码',
  sex               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
  avatar            varchar(100)    default ''                 comment '头像地址',
  password          varchar(100)    default ''                 comment '密码',
  status            char(1)         default '0'                comment '账号状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  login_ip          varchar(128)    default ''                 comment '最后登录IP',
  login_date        datetime                                   comment '最后登录时间',
  pwd_update_date   datetime                                   comment '密码最后更新时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(1,  103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '管理员');
insert into sys_user values(2,  105, 'ry',    '若依', '00', 'ry@qq.com',  '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '测试员');


-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null auto_increment    comment '岗位ID',
  post_code     varchar(64)     not null                   comment '岗位编码',
  post_name     varchar(50)     not null                   comment '岗位名称',
  post_sort     int(4)          not null                   comment '显示顺序',
  status        char(1)         not null                   comment '状态（0正常 1停用）',
  create_by     varchar(64)     default ''                 comment '创建者',
  create_time   datetime                                   comment '创建时间',
  update_by     varchar(64)     default ''			       comment '更新者',
  update_time   datetime                                   comment '更新时间',
  remark        varchar(500)    default null               comment '备注',
  primary key (post_id)
) engine=innodb comment = '岗位信息表';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post values(1, 'ceo',  '董事长',    1, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(2, 'se',   '项目经理',  2, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(3, 'hr',   '人力资源',  3, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(4, 'user', '普通员工',  4, '0', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment '角色ID',
  role_name            varchar(30)     not null                   comment '角色名称',
  role_key             varchar(100)    not null                   comment '角色权限字符串',
  role_sort            int(4)          not null                   comment '显示顺序',
  data_scope           char(1)         default '1'                comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  menu_check_strictly  tinyint(1)      default 1                  comment '菜单树选择项是否关联显示',
  dept_check_strictly  tinyint(1)      default 1                  comment '部门树选择项是否关联显示',
  status               char(1)         not null                   comment '角色状态（0正常 1停用）',
  del_flag             char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by            varchar(64)     default ''                 comment '创建者',
  create_time          datetime                                   comment '创建时间',
  update_by            varchar(64)     default ''                 comment '更新者',
  update_time          datetime                                   comment '更新时间',
  remark               varchar(500)    default null               comment '备注',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values('1', '超级管理员',  'admin',  1, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, '超级管理员');
insert into sys_role values('2', '普通角色',    'common', 2, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '普通角色');


-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment '菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  parent_id         bigint(20)      default 0                  comment '父菜单ID',
  order_num         int(4)          default 0                  comment '显示顺序',
  path              varchar(200)    default ''                 comment '路由地址',
  component         varchar(255)    default null               comment '组件路径',
  query             varchar(255)    default null               comment '路由参数',
  route_name        varchar(50)     default ''                 comment '路由名称',
  is_frame          int(1)          default 1                  comment '是否为外链（0是 1否）',
  is_cache          int(1)          default 0                  comment '是否缓存（0缓存 1不缓存）',
  menu_type         char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
  visible           char(1)         default 0                  comment '菜单状态（0显示 1隐藏）',
  status            char(1)         default 0                  comment '菜单状态（0正常 1停用）',
  perms             varchar(100)    default null               comment '权限标识',
  icon              varchar(100)    default '#'                comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values('1', '系统管理', '0', '1', 'system',           null, '', '', 1, 0, 'M', '0', '0', '', 'system',   'admin', sysdate(), '', null, '系统管理目录');
insert into sys_menu values('2', '系统监控', '0', '2', 'monitor',          null, '', '', 1, 0, 'M', '0', '0', '', 'monitor',  'admin', sysdate(), '', null, '系统监控目录');
insert into sys_menu values('3', '系统工具', '0', '3', 'tool',             null, '', '', 1, 0, 'M', '0', '0', '', 'tool',     'admin', sysdate(), '', null, '系统工具目录');
-- 二级菜单
insert into sys_menu values('100',  '用户管理', '1',   '1', 'user',       'system/user/index',        '', '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', sysdate(), '', null, '用户管理菜单');
insert into sys_menu values('101',  '角色管理', '1',   '2', 'role',       'system/role/index',        '', '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', sysdate(), '', null, '角色管理菜单');
insert into sys_menu values('102',  '菜单管理', '1',   '3', 'menu',       'system/menu/index',        '', '', 1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'admin', sysdate(), '', null, '菜单管理菜单');
insert into sys_menu values('103',  '部门管理', '1',   '4', 'dept',       'system/dept/index',        '', '', 1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'admin', sysdate(), '', null, '部门管理菜单');
insert into sys_menu values('104',  '岗位管理', '1',   '5', 'post',       'system/post/index',        '', '', 1, 0, 'C', '0', '0', 'system:post:list',        'post',          'admin', sysdate(), '', null, '岗位管理菜单');
insert into sys_menu values('105',  '字典管理', '1',   '6', 'dict',       'system/dict/index',        '', '', 1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'admin', sysdate(), '', null, '字典管理菜单');
insert into sys_menu values('106',  '参数设置', '1',   '7', 'config',     'system/config/index',      '', '', 1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'admin', sysdate(), '', null, '参数设置菜单');
insert into sys_menu values('107',  '通知公告', '1',   '8', 'notice',     'system/notice/index',      '', '', 1, 0, 'C', '0', '0', 'system:notice:list',      'message',       'admin', sysdate(), '', null, '通知公告菜单');
insert into sys_menu values('108',  '日志管理', '1',   '9', 'log',        '',                         '', '', 1, 0, 'M', '0', '0', '',                        'log',           'admin', sysdate(), '', null, '日志管理菜单');
insert into sys_menu values('109',  '在线用户', '2',   '1', 'online',     'monitor/online/index',     '', '', 1, 0, 'C', '0', '0', 'monitor:online:list',     'online',        'admin', sysdate(), '', null, '在线用户菜单');
insert into sys_menu values('110',  '定时任务', '2',   '2', 'job',        'monitor/job/index',        '', '', 1, 0, 'C', '0', '0', 'monitor:job:list',        'job',           'admin', sysdate(), '', null, '定时任务菜单');
insert into sys_menu values('111',  '数据监控', '2',   '3', 'druid',      'monitor/druid/index',      '', '', 1, 0, 'C', '0', '0', 'monitor:druid:list',      'druid',         'admin', sysdate(), '', null, '数据监控菜单');
insert into sys_menu values('112',  '服务监控', '2',   '4', 'server',     'monitor/server/index',     '', '', 1, 0, 'C', '0', '0', 'monitor:server:list',     'server',        'admin', sysdate(), '', null, '服务监控菜单');
insert into sys_menu values('113',  '缓存监控', '2',   '5', 'cache',      'monitor/cache/index',      '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis',         'admin', sysdate(), '', null, '缓存监控菜单');
insert into sys_menu values('114',  '缓存列表', '2',   '6', 'cacheList',  'monitor/cache/list',       '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis-list',    'admin', sysdate(), '', null, '缓存列表菜单');
insert into sys_menu values('115',  '表单构建', '3',   '1', 'build',      'tool/build/index',         '', '', 1, 0, 'C', '0', '0', 'tool:build:list',         'build',         'admin', sysdate(), '', null, '表单构建菜单');
insert into sys_menu values('116',  '代码生成', '3',   '2', 'gen',        'tool/gen/index',           '', '', 1, 0, 'C', '0', '0', 'tool:gen:list',           'code',          'admin', sysdate(), '', null, '代码生成菜单');
insert into sys_menu values('117',  '系统接口', '3',   '3', 'swagger',    'tool/swagger/index',       '', '', 1, 0, 'C', '0', '0', 'tool:swagger:list',       'swagger',       'admin', sysdate(), '', null, '系统接口菜单');
-- 三级菜单
insert into sys_menu values('500',  '操作日志', '108', '1', 'operlog',    'monitor/operlog/index',    '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list',    'form',          'admin', sysdate(), '', null, '操作日志菜单');
insert into sys_menu values('501',  '登录日志', '108', '2', 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'admin', sysdate(), '', null, '登录日志菜单');
-- 用户管理按钮
insert into sys_menu values('1000', '用户查询', '100', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1001', '用户新增', '100', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1002', '用户修改', '100', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1003', '用户删除', '100', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1004', '用户导出', '100', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1005', '用户导入', '100', '6',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1006', '重置密码', '100', '7',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',       '#', 'admin', sysdate(), '', null, '');
-- 角色管理按钮
insert into sys_menu values('1007', '角色查询', '101', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1008', '角色新增', '101', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1009', '角色修改', '101', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1010', '角色删除', '101', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1011', '角色导出', '101', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export',         '#', 'admin', sysdate(), '', null, '');
-- 菜单管理按钮
insert into sys_menu values('1012', '菜单查询', '102', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1013', '菜单新增', '102', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1014', '菜单修改', '102', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1015', '菜单删除', '102', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',         '#', 'admin', sysdate(), '', null, '');
-- 部门管理按钮
insert into sys_menu values('1016', '部门查询', '103', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1017', '部门新增', '103', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1018', '部门修改', '103', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1019', '部门删除', '103', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',         '#', 'admin', sysdate(), '', null, '');
-- 岗位管理按钮
insert into sys_menu values('1020', '岗位查询', '104', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1021', '岗位新增', '104', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1022', '岗位修改', '104', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1023', '岗位删除', '104', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1024', '岗位导出', '104', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export',         '#', 'admin', sysdate(), '', null, '');
-- 字典管理按钮
insert into sys_menu values('1025', '字典查询', '105', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1026', '字典新增', '105', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1027', '字典修改', '105', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1028', '字典删除', '105', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1029', '字典导出', '105', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'admin', sysdate(), '', null, '');
-- 参数设置按钮
insert into sys_menu values('1030', '参数查询', '106', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1031', '参数新增', '106', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1032', '参数修改', '106', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1033', '参数删除', '106', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1034', '参数导出', '106', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export',       '#', 'admin', sysdate(), '', null, '');
-- 通知公告按钮
insert into sys_menu values('1035', '公告查询', '107', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1036', '公告新增', '107', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1037', '公告修改', '107', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1038', '公告删除', '107', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove',       '#', 'admin', sysdate(), '', null, '');
-- 操作日志按钮
insert into sys_menu values('1039', '操作查询', '500', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1040', '操作删除', '500', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1041', '日志导出', '500', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export',     '#', 'admin', sysdate(), '', null, '');
-- 登录日志按钮
insert into sys_menu values('1042', '登录查询', '501', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1043', '登录删除', '501', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1044', '日志导出', '501', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1045', '账户解锁', '501', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock',  '#', 'admin', sysdate(), '', null, '');
-- 在线用户按钮
insert into sys_menu values('1046', '在线查询', '109', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1047', '批量强退', '109', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1048', '单条强退', '109', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', sysdate(), '', null, '');
-- 定时任务按钮
insert into sys_menu values('1049', '任务查询', '110', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1050', '任务新增', '110', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1051', '任务修改', '110', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1052', '任务删除', '110', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1053', '状态修改', '110', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1054', '任务导出', '110', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export',         '#', 'admin', sysdate(), '', null, '');
-- 代码生成按钮
insert into sys_menu values('1055', '生成查询', '116', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query',             '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1056', '生成修改', '116', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit',              '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1057', '生成删除', '116', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1058', '导入代码', '116', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1059', '预览代码', '116', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1060', '生成代码', '116', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code',              '#', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '用户ID',
  role_id   bigint(20) not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '角色ID',
  menu_id   bigint(20) not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '4');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '110');
insert into sys_role_menu values ('2', '111');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '115');
insert into sys_role_menu values ('2', '116');
insert into sys_role_menu values ('2', '117');
insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1049');
insert into sys_role_menu values ('2', '1050');
insert into sys_role_menu values ('2', '1051');
insert into sys_role_menu values ('2', '1052');
insert into sys_role_menu values ('2', '1053');
insert into sys_role_menu values ('2', '1054');
insert into sys_role_menu values ('2', '1055');
insert into sys_role_menu values ('2', '1056');
insert into sys_role_menu values ('2', '1057');
insert into sys_role_menu values ('2', '1058');
insert into sys_role_menu values ('2', '1059');
insert into sys_role_menu values ('2', '1060');

-- ----------------------------
-- Visualization menu routes
-- ----------------------------
insert into sys_menu values('5',   '可视化大屏',  '0', '0', 'visualization',            null,                                       '', '', 1, 0, 'M', '0', '0', '', 'dashboard', 'admin', sysdate(), '', null, '可视化大屏目录');
insert into sys_menu values('118', '运力诊断决策', '5', '1', 'capacityDiagnosis',        'visualization/capacityDiagnosis/index',    '', '', 1, 0, 'C', '0', '0', '', 'dashboard', 'admin', sysdate(), '', null, '运力诊断决策菜单');
insert into sys_menu values('119', '时空演化分析', '5', '2', 'spaceTimeEvolution',       'visualization/spaceTimeEvolution/index',   '', '', 1, 0, 'C', '0', '0', '', 'chart',     'admin', sysdate(), '', null, '时空演化分析菜单');
insert into sys_menu values('120', '站点热力效能', '5', '3', 'stationHeatEfficiency',    'visualization/stationHeatEfficiency/index','', '', 1, 0, 'C', '0', '0', '', 'chart',     'admin', sysdate(), '', null, '站点热力效能菜单');

insert into sys_role_menu values ('2', '5');
insert into sys_role_menu values ('2', '118');
insert into sys_role_menu values ('2', '119');
insert into sys_role_menu values ('2', '120');

-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment '角色ID',
  dept_id   bigint(20) not null comment '部门ID',
  primary key(role_id, dept_id)
) engine=innodb comment = '角色和部门关联表';

-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');


-- ----------------------------
-- 9、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment '用户ID',
  post_id   bigint(20) not null comment '岗位ID',
  primary key (user_id, post_id)
) engine=innodb comment = '用户与岗位关联表';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null auto_increment    comment '日志主键',
  title             varchar(50)     default ''                 comment '模块标题',
  business_type     int(2)          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
  method            varchar(200)    default ''                 comment '方法名称',
  request_method    varchar(10)     default ''                 comment '请求方式',
  operator_type     int(1)          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
  oper_name         varchar(50)     default ''                 comment '操作人员',
  dept_name         varchar(50)     default ''                 comment '部门名称',
  oper_url          varchar(255)    default ''                 comment '请求URL',
  oper_ip           varchar(128)    default ''                 comment '主机地址',
  oper_location     varchar(255)    default ''                 comment '操作地点',
  oper_param        varchar(2000)   default ''                 comment '请求参数',
  json_result       varchar(2000)   default ''                 comment '返回参数',
  status            int(1)          default 0                  comment '操作状态（0正常 1异常）',
  error_msg         varchar(2000)   default ''                 comment '错误消息',
  oper_time         datetime                                   comment '操作时间',
  cost_time         bigint(20)      default 0                  comment '消耗时间',
  primary key (oper_id),
  key idx_sys_oper_log_bt (business_type),
  key idx_sys_oper_log_s  (status),
  key idx_sys_oper_log_ot (oper_time)
) engine=innodb auto_increment=100 comment = '操作日志记录';


-- ----------------------------
-- 11、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment '字典主键',
  dict_name        varchar(100)    default ''                 comment '字典名称',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = '字典类型表';

insert into sys_dict_type values(1,  '用户性别', 'sys_user_sex',        '0', 'admin', sysdate(), '', null, '用户性别列表');
insert into sys_dict_type values(2,  '菜单状态', 'sys_show_hide',       '0', 'admin', sysdate(), '', null, '菜单状态列表');
insert into sys_dict_type values(3,  '系统开关', 'sys_normal_disable',  '0', 'admin', sysdate(), '', null, '系统开关列表');
insert into sys_dict_type values(4,  '任务状态', 'sys_job_status',      '0', 'admin', sysdate(), '', null, '任务状态列表');
insert into sys_dict_type values(5,  '任务分组', 'sys_job_group',       '0', 'admin', sysdate(), '', null, '任务分组列表');
insert into sys_dict_type values(6,  '系统是否', 'sys_yes_no',          '0', 'admin', sysdate(), '', null, '系统是否列表');
insert into sys_dict_type values(7,  '通知类型', 'sys_notice_type',     '0', 'admin', sysdate(), '', null, '通知类型列表');
insert into sys_dict_type values(8,  '通知状态', 'sys_notice_status',   '0', 'admin', sysdate(), '', null, '通知状态列表');
insert into sys_dict_type values(9,  '操作类型', 'sys_oper_type',       '0', 'admin', sysdate(), '', null, '操作类型列表');
insert into sys_dict_type values(10, '系统状态', 'sys_common_status',   '0', 'admin', sysdate(), '', null, '登录状态列表');


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment '字典编码',
  dict_sort        int(4)          default 0                  comment '字典排序',
  dict_label       varchar(100)    default ''                 comment '字典标签',
  dict_value       varchar(100)    default ''                 comment '字典键值',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  css_class        varchar(100)    default null               comment '样式属性（其他样式扩展）',
  list_class       varchar(100)    default null               comment '表格回显样式',
  is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = '字典数据表';

insert into sys_dict_data values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', sysdate(), '', null, '性别男');
insert into sys_dict_data values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性别女');
insert into sys_dict_data values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性别未知');
insert into sys_dict_data values(4,  1,  '显示',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '显示菜单');
insert into sys_dict_data values(5,  2,  '隐藏',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '隐藏菜单');
insert into sys_dict_data values(6,  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(7,  2,  '停用',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(8,  1,  '正常',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(9,  2,  '暂停',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(10, 1,  '默认',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'admin', sysdate(), '', null, '默认分组');
insert into sys_dict_data values(11, 2,  '系统',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'admin', sysdate(), '', null, '系统分组');
insert into sys_dict_data values(12, 1,  '是',       'Y',       'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '系统默认是');
insert into sys_dict_data values(13, 2,  '否',       'N',       'sys_yes_no',          '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '系统默认否');
insert into sys_dict_data values(14, 1,  '通知',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', sysdate(), '', null, '通知');
insert into sys_dict_data values(15, 2,  '公告',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', sysdate(), '', null, '公告');
insert into sys_dict_data values(16, 1,  '正常',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(17, 2,  '关闭',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '关闭状态');
insert into sys_dict_data values(18, 99, '其他',     '0',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '其他操作');
insert into sys_dict_data values(19, 1,  '新增',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '新增操作');
insert into sys_dict_data values(20, 2,  '修改',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '修改操作');
insert into sys_dict_data values(21, 3,  '删除',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '删除操作');
insert into sys_dict_data values(22, 4,  '授权',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '授权操作');
insert into sys_dict_data values(23, 5,  '导出',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '导出操作');
insert into sys_dict_data values(24, 6,  '导入',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '导入操作');
insert into sys_dict_data values(25, 7,  '强退',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '强退操作');
insert into sys_dict_data values(26, 8,  '生成代码', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '生成操作');
insert into sys_dict_data values(27, 9,  '清空数据', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '清空操作');
insert into sys_dict_data values(28, 1,  '成功',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(29, 2,  '失败',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用状态');


-- ----------------------------
-- 13、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment '参数主键',
  config_name       varchar(100)    default ''                 comment '参数名称',
  config_key        varchar(100)    default ''                 comment '参数键名',
  config_value      varchar(500)    default ''                 comment '参数键值',
  config_type       char(1)         default 'N'                comment '系统内置（Y是 N否）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = '参数配置表';

insert into sys_config values(1, '主框架页-默认皮肤样式名称',     'sys.index.skinName',               'skin-blue',     'Y', 'admin', sysdate(), '', null, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow' );
insert into sys_config values(2, '用户管理-账号初始密码',         'sys.user.initPassword',            '123456',        'Y', 'admin', sysdate(), '', null, '初始化密码 123456' );
insert into sys_config values(3, '主框架页-侧边栏主题',           'sys.index.sideTheme',              'theme-dark',    'Y', 'admin', sysdate(), '', null, '深色主题theme-dark，浅色主题theme-light' );
insert into sys_config values(4, '账号自助-验证码开关',           'sys.account.captchaEnabled',       'true',          'Y', 'admin', sysdate(), '', null, '是否开启验证码功能（true开启，false关闭）');
insert into sys_config values(5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser',         'false',         'Y', 'admin', sysdate(), '', null, '是否开启注册用户功能（true开启，false关闭）');
insert into sys_config values(6, '用户登录-黑名单列表',           'sys.login.blackIPList',            '',              'Y', 'admin', sysdate(), '', null, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');
insert into sys_config values(7, '用户管理-初始密码修改策略',     'sys.account.initPasswordModify',   '1',             'Y', 'admin', sysdate(), '', null, '0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框');
insert into sys_config values(8, '用户管理-账号密码更新周期',     'sys.account.passwordValidateDays', '0',             'Y', 'admin', sysdate(), '', null, '密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框');


-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null auto_increment   comment '访问ID',
  user_name      varchar(50)    default ''                comment '用户账号',
  ipaddr         varchar(128)   default ''                comment '登录IP地址',
  login_location varchar(255)   default ''                comment '登录地点',
  browser        varchar(50)    default ''                comment '浏览器类型',
  os             varchar(50)    default ''                comment '操作系统',
  status         char(1)        default '0'               comment '登录状态（0成功 1失败）',
  msg            varchar(255)   default ''                comment '提示消息',
  login_time     datetime                                 comment '访问时间',
  primary key (info_id),
  key idx_sys_logininfor_s  (status),
  key idx_sys_logininfor_lt (login_time)
) engine=innodb auto_increment=100 comment = '系统访问记录';


-- ----------------------------
-- 15、定时任务调度表
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint(20)    not null auto_increment    comment '任务ID',
  job_name            varchar(64)   default ''                 comment '任务名称',
  job_group           varchar(64)   default 'DEFAULT'          comment '任务组名',
  invoke_target       varchar(500)  not null                   comment '调用目标字符串',
  cron_expression     varchar(255)  default ''                 comment 'cron执行表达式',
  misfire_policy      varchar(20)   default '3'                comment '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  concurrent          char(1)       default '1'                comment '是否并发执行（0允许 1禁止）',
  status              char(1)       default '0'                comment '状态（0正常 1暂停）',
  create_by           varchar(64)   default ''                 comment '创建者',
  create_time         datetime                                 comment '创建时间',
  update_by           varchar(64)   default ''                 comment '更新者',
  update_time         datetime                                 comment '更新时间',
  remark              varchar(500)  default ''                 comment '备注信息',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 comment = '定时任务调度表';

insert into sys_job values(1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams',        '0/10 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')',  '0/15 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 16、定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint(20)     not null auto_increment    comment '任务日志ID',
  job_name            varchar(64)    not null                   comment '任务名称',
  job_group           varchar(64)    not null                   comment '任务组名',
  invoke_target       varchar(500)   not null                   comment '调用目标字符串',
  job_message         varchar(500)                              comment '日志信息',
  status              char(1)        default '0'                comment '执行状态（0正常 1失败）',
  exception_info      varchar(2000)  default ''                 comment '异常信息',
  create_time         datetime                                  comment '创建时间',
  primary key (job_log_id)
) engine=innodb comment = '定时任务调度日志表';


-- ----------------------------
-- 17、通知公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment '公告ID',
  notice_title      varchar(50)     not null                   comment '公告标题',
  notice_type       char(1)         not null                   comment '公告类型（1通知 2公告）',
  notice_content    longblob        default null               comment '公告内容',
  status            char(1)         default '0'                comment '公告状态（0正常 1关闭）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(255)    default null               comment '备注',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = '通知公告表';

-- ----------------------------
-- 初始化-公告信息表数据
-- ----------------------------
insert into sys_notice values('1', '温馨提醒：2018-07-01 若依新版本发布啦', '2', '新版本内容', '0', 'admin', sysdate(), '', null, '管理员');
insert into sys_notice values('2', '维护通知：2018-07-01 若依系统凌晨维护', '1', '维护内容',   '0', 'admin', sysdate(), '', null, '管理员');


-- ----------------------------
-- 18、代码生成业务表
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id          bigint(20)      not null auto_increment    comment '编号',
  table_name        varchar(200)    default ''                 comment '表名称',
  table_comment     varchar(500)    default ''                 comment '表描述',
  sub_table_name    varchar(64)     default null               comment '关联子表的表名',
  sub_table_fk_name varchar(64)     default null               comment '子表关联的外键名',
  class_name        varchar(100)    default ''                 comment '实体类名称',
  tpl_category      varchar(200)    default 'crud'             comment '使用的模板（crud单表操作 tree树表操作）',
  tpl_web_type      varchar(30)     default ''                 comment '前端模板类型（element-ui模版 element-plus模版）',
  package_name      varchar(100)                               comment '生成包路径',
  module_name       varchar(30)                                comment '生成模块名',
  business_name     varchar(30)                                comment '生成业务名',
  function_name     varchar(50)                                comment '生成功能名',
  function_author   varchar(50)                                comment '生成功能作者',
  gen_type          char(1)         default '0'                comment '生成代码方式（0zip压缩包 1自定义路径）',
  gen_path          varchar(200)    default '/'                comment '生成路径（不填默认项目路径）',
  options           varchar(1000)                              comment '其它生成选项',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (table_id)
) engine=innodb auto_increment=1 comment = '代码生成业务表';


-- ----------------------------
-- 19、代码生成业务表字段
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint(20)      not null auto_increment    comment '编号',
  table_id          bigint(20)                                 comment '归属表编号',
  column_name       varchar(200)                               comment '列名称',
  column_comment    varchar(500)                               comment '列描述',
  column_type       varchar(100)                               comment '列类型',
  java_type         varchar(500)                               comment 'JAVA类型',
  java_field        varchar(200)                               comment 'JAVA字段名',
  is_pk             char(1)                                    comment '是否主键（1是）',
  is_increment      char(1)                                    comment '是否自增（1是）',
  is_required       char(1)                                    comment '是否必填（1是）',
  is_insert         char(1)                                    comment '是否为插入字段（1是）',
  is_edit           char(1)                                    comment '是否编辑字段（1是）',
  is_list           char(1)                                    comment '是否列表字段（1是）',
  is_query          char(1)                                    comment '是否查询字段（1是）',
  query_type        varchar(200)    default 'EQ'               comment '查询方式（等于、不等于、大于、小于、范围）',
  html_type         varchar(200)                               comment '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  dict_type         varchar(200)    default ''                 comment '字典类型',
  sort              int                                        comment '排序',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (column_id)
) engine=innodb auto_increment=1 comment = '代码生成业务表字段';


-- source: D:\AIruoyi_project\agritech-vue\sql\quartz.sql
DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

-- ----------------------------
-- 1、存储每一个已配置的 jobDetail 的详细信息
-- ----------------------------
create table QRTZ_JOB_DETAILS (
    sched_name           varchar(120)    not null            comment '调度名称',
    job_name             varchar(200)    not null            comment '任务名称',
    job_group            varchar(200)    not null            comment '任务组名',
    description          varchar(250)    null                comment '相关介绍',
    job_class_name       varchar(250)    not null            comment '执行任务类名称',
    is_durable           varchar(1)      not null            comment '是否持久化',
    is_nonconcurrent     varchar(1)      not null            comment '是否并发',
    is_update_data       varchar(1)      not null            comment '是否更新数据',
    requests_recovery    varchar(1)      not null            comment '是否接受恢复执行',
    job_data             blob            null                comment '存放持久化job对象',
    primary key (sched_name, job_name, job_group)
) engine=innodb comment = '任务详细信息表';

-- ----------------------------
-- 2、 存储已配置的 Trigger 的信息
-- ----------------------------
create table QRTZ_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_name         varchar(200)    not null            comment '触发器的名字',
    trigger_group        varchar(200)    not null            comment '触发器所属组的名字',
    job_name             varchar(200)    not null            comment 'qrtz_job_details表job_name的外键',
    job_group            varchar(200)    not null            comment 'qrtz_job_details表job_group的外键',
    description          varchar(250)    null                comment '相关介绍',
    next_fire_time       bigint(13)      null                comment '上一次触发时间（毫秒）',
    prev_fire_time       bigint(13)      null                comment '下一次触发时间（默认为-1表示不触发）',
    priority             integer         null                comment '优先级',
    trigger_state        varchar(16)     not null            comment '触发器状态',
    trigger_type         varchar(8)      not null            comment '触发器的类型',
    start_time           bigint(13)      not null            comment '开始时间',
    end_time             bigint(13)      null                comment '结束时间',
    calendar_name        varchar(200)    null                comment '日程表名称',
    misfire_instr        smallint(2)     null                comment '补偿执行的策略',
    job_data             blob            null                comment '存放持久化job对象',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, job_name, job_group) references QRTZ_JOB_DETAILS(sched_name, job_name, job_group)
) engine=innodb comment = '触发器详细信息表';

-- ----------------------------
-- 3、 存储简单的 Trigger，包括重复次数，间隔，以及已触发的次数
-- ----------------------------
create table QRTZ_SIMPLE_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外键',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    repeat_count         bigint(7)       not null            comment '重复的次数统计',
    repeat_interval      bigint(12)      not null            comment '重复的间隔时间',
    times_triggered      bigint(10)      not null            comment '已经触发的次数',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = '简单触发器的信息表';

-- ----------------------------
-- 4、 存储 Cron Trigger，包括 Cron 表达式和时区信息
-- ---------------------------- 
create table QRTZ_CRON_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外键',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    cron_expression      varchar(200)    not null            comment 'cron表达式',
    time_zone_id         varchar(80)                         comment '时区',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = 'Cron类型的触发器表';

-- ----------------------------
-- 5、 Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，JobStore 并不知道如何存储实例的时候)
-- ---------------------------- 
create table QRTZ_BLOB_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外键',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    blob_data            blob            null                comment '存放持久化Trigger对象',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = 'Blob类型的触发器表';

-- ----------------------------
-- 6、 以 Blob 类型存储存放日历信息， quartz可配置一个日历来指定一个时间范围
-- ---------------------------- 
create table QRTZ_CALENDARS (
    sched_name           varchar(120)    not null            comment '调度名称',
    calendar_name        varchar(200)    not null            comment '日历名称',
    calendar             blob            not null            comment '存放持久化calendar对象',
    primary key (sched_name, calendar_name)
) engine=innodb comment = '日历信息表';

-- ----------------------------
-- 7、 存储已暂停的 Trigger 组的信息
-- ---------------------------- 
create table QRTZ_PAUSED_TRIGGER_GRPS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    primary key (sched_name, trigger_group)
) engine=innodb comment = '暂停的触发器表';

-- ----------------------------
-- 8、 存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息
-- ---------------------------- 
create table QRTZ_FIRED_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    entry_id             varchar(95)     not null            comment '调度器实例id',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外键',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    instance_name        varchar(200)    not null            comment '调度器实例名',
    fired_time           bigint(13)      not null            comment '触发的时间',
    sched_time           bigint(13)      not null            comment '定时器制定的时间',
    priority             integer         not null            comment '优先级',
    state                varchar(16)     not null            comment '状态',
    job_name             varchar(200)    null                comment '任务名称',
    job_group            varchar(200)    null                comment '任务组名',
    is_nonconcurrent     varchar(1)      null                comment '是否并发',
    requests_recovery    varchar(1)      null                comment '是否接受恢复执行',
    primary key (sched_name, entry_id)
) engine=innodb comment = '已触发的触发器表';

-- ----------------------------
-- 9、 存储少量的有关 Scheduler 的状态信息，假如是用于集群中，可以看到其他的 Scheduler 实例
-- ---------------------------- 
create table QRTZ_SCHEDULER_STATE (
    sched_name           varchar(120)    not null            comment '调度名称',
    instance_name        varchar(200)    not null            comment '实例名称',
    last_checkin_time    bigint(13)      not null            comment '上次检查时间',
    checkin_interval     bigint(13)      not null            comment '检查间隔时间',
    primary key (sched_name, instance_name)
) engine=innodb comment = '调度器状态表';

-- ----------------------------
-- 10、 存储程序的悲观锁的信息(假如使用了悲观锁)
-- ---------------------------- 
create table QRTZ_LOCKS (
    sched_name           varchar(120)    not null            comment '调度名称',
    lock_name            varchar(40)     not null            comment '悲观锁名称',
    primary key (sched_name, lock_name)
) engine=innodb comment = '存储的悲观锁信息表';

-- ----------------------------
-- 11、 Quartz集群实现同步机制的行锁表
-- ---------------------------- 
create table QRTZ_SIMPROP_TRIGGERS (
    sched_name           varchar(120)    not null            comment '调度名称',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外键',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外键',
    str_prop_1           varchar(512)    null                comment 'String类型的trigger的第一个参数',
    str_prop_2           varchar(512)    null                comment 'String类型的trigger的第二个参数',
    str_prop_3           varchar(512)    null                comment 'String类型的trigger的第三个参数',
    int_prop_1           int             null                comment 'int类型的trigger的第一个参数',
    int_prop_2           int             null                comment 'int类型的trigger的第二个参数',
    long_prop_1          bigint          null                comment 'long类型的trigger的第一个参数',
    long_prop_2          bigint          null                comment 'long类型的trigger的第二个参数',
    dec_prop_1           numeric(13,4)   null                comment 'decimal类型的trigger的第一个参数',
    dec_prop_2           numeric(13,4)   null                comment 'decimal类型的trigger的第二个参数',
    bool_prop_1          varchar(1)      null                comment 'Boolean类型的trigger的第一个参数',
    bool_prop_2          varchar(1)      null                comment 'Boolean类型的trigger的第二个参数',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = '同步机制的行锁表';

commit;

-- source: D:\AIruoyi_project\agritech-vue\sql\system_menu_routes_rebuild.sql
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


-- source: D:\AIruoyi_project\agritech-vue\sql\update\2026-07-24-01-新增商城业务表.sql
-- 商城首期核心表（MySQL 8.0）。
-- 执行前确认目标库已备份；本脚本不包含 DROP、TRUNCATE 或演示数据。
-- 若 DDL 被中断，先核对 information_schema 中已创建的表、索引和外键，再重新执行。

CREATE TABLE IF NOT EXISTS mall_category (
  category_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  parent_id BIGINT UNSIGNED NULL COMMENT '父分类ID，一级分类为空',
  category_name VARCHAR(64) NOT NULL COMMENT '分类名称',
  level TINYINT UNSIGNED NOT NULL COMMENT '层级，1或2',
  sort_num INT NOT NULL DEFAULT 0 COMMENT '排序值，越小越靠前',
  status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常 1停用',
  create_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '创建者',
  create_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '更新者',
  update_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
  del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志，0存在 2删除',
  PRIMARY KEY (category_id),
  KEY idx_mall_category_parent_status_sort (parent_id, status, sort_num),
  CONSTRAINT chk_mall_category_level CHECK (level IN (1, 2)),
  CONSTRAINT chk_mall_category_sort CHECK (sort_num >= 0),
  CONSTRAINT fk_mall_category_parent FOREIGN KEY (parent_id) REFERENCES mall_category (category_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城商品分类';

CREATE TABLE IF NOT EXISTS mall_product (
  product_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  category_id BIGINT UNSIGNED NOT NULL COMMENT '分类ID',
  product_name VARCHAR(128) NOT NULL COMMENT '商品名称',
  subtitle VARCHAR(255) NOT NULL DEFAULT '' COMMENT '副标题',
  main_image VARCHAR(512) NOT NULL DEFAULT '' COMMENT '主图地址',
  images_json JSON NULL COMMENT '图片地址数组',
  detail_html LONGTEXT NULL COMMENT '商品详情HTML',
  product_params_json JSON NULL COMMENT '农产品参数JSON',
  spec_schema_json JSON NULL COMMENT '规格定义JSON',
  status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态，0草稿 1上架 2下架',
  publish_time DATETIME(3) NULL COMMENT '上架时间',
  version INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  create_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '创建者',
  create_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '更新者',
  update_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
  del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志，0存在 2删除',
  PRIMARY KEY (product_id),
  KEY idx_mall_product_category_status_del (category_id, status, del_flag),
  CONSTRAINT chk_mall_product_version CHECK (version >= 0),
  CONSTRAINT fk_mall_product_category FOREIGN KEY (category_id) REFERENCES mall_category (category_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城商品SPU';

CREATE TABLE IF NOT EXISTS mall_product_sku (
  sku_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
  product_id BIGINT UNSIGNED NOT NULL COMMENT '商品ID',
  sku_code VARCHAR(64) NOT NULL COMMENT 'SKU业务编码',
  sku_name VARCHAR(128) NOT NULL COMMENT 'SKU名称',
  spec_values_json JSON NULL COMMENT '规格值JSON',
  image_url VARCHAR(512) NOT NULL DEFAULT '' COMMENT 'SKU图片地址',
  sale_price BIGINT NOT NULL COMMENT '销售价，分',
  market_price BIGINT NOT NULL DEFAULT 0 COMMENT '市场价，分',
  available_stock BIGINT NOT NULL DEFAULT 0 COMMENT '可用库存',
  locked_stock BIGINT NOT NULL DEFAULT 0 COMMENT '锁定库存',
  warning_stock BIGINT NOT NULL DEFAULT 0 COMMENT '库存预警值',
  status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态，0启用 1停用',
  version INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  create_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '创建者',
  create_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '更新者',
  update_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
  del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志，0存在 2删除',
  PRIMARY KEY (sku_id),
  UNIQUE KEY uk_mall_product_sku_code (sku_code),
  KEY idx_mall_product_sku_product_status_del (product_id, status, del_flag),
  CONSTRAINT chk_mall_product_sku_amount CHECK (sale_price >= 0 AND market_price >= 0),
  CONSTRAINT chk_mall_product_sku_stock CHECK (available_stock >= 0 AND locked_stock >= 0 AND warning_stock >= 0),
  CONSTRAINT chk_mall_product_sku_version CHECK (version >= 0),
  CONSTRAINT fk_mall_product_sku_product FOREIGN KEY (product_id) REFERENCES mall_product (product_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城商品SKU与库存';

CREATE TABLE IF NOT EXISTS mall_member (
  member_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  app_id VARCHAR(64) NOT NULL COMMENT '小程序AppID',
  open_id VARCHAR(128) NOT NULL COMMENT '小程序OpenID',
  union_id VARCHAR(128) DEFAULT NULL COMMENT '微信UnionID',
  nickname VARCHAR(128) NOT NULL DEFAULT '' COMMENT '昵称',
  avatar_url VARCHAR(512) NOT NULL DEFAULT '' COMMENT '头像地址',
  phone VARCHAR(32) DEFAULT NULL COMMENT '手机号，默认脱敏展示',
  status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常 1禁用',
  last_login_time DATETIME(3) NULL COMMENT '最近登录时间',
  create_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (member_id),
  UNIQUE KEY uk_mall_member_app_open (app_id, open_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城会员';

CREATE TABLE IF NOT EXISTS mall_member_address (
  address_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  member_id BIGINT UNSIGNED NOT NULL COMMENT '会员ID',
  receiver_name VARCHAR(64) NOT NULL COMMENT '收货人',
  receiver_phone VARCHAR(32) NOT NULL COMMENT '收货电话，默认脱敏展示',
  province_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '省编码',
  province_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '省名称',
  city_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '市编码',
  city_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '市名称',
  district_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '区县编码',
  district_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '区县名称',
  detail_address VARCHAR(255) NOT NULL COMMENT '详细地址，默认脱敏展示',
  is_default CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否默认，0否 1是',
  del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志，0存在 2删除',
  create_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (address_id),
  KEY idx_mall_member_address_member_default_del (member_id, is_default, del_flag),
  CONSTRAINT fk_mall_member_address_member FOREIGN KEY (member_id) REFERENCES mall_member (member_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城会员收货地址';

CREATE TABLE IF NOT EXISTS mall_coupon (
  coupon_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
  coupon_name VARCHAR(128) NOT NULL COMMENT '优惠券名称',
  threshold_amount BIGINT NOT NULL DEFAULT 0 COMMENT '使用门槛，分',
  discount_amount BIGINT NOT NULL COMMENT '优惠金额，分',
  total_quantity BIGINT NOT NULL COMMENT '发放总量',
  received_quantity BIGINT NOT NULL DEFAULT 0 COMMENT '已领取数量',
  used_quantity BIGINT NOT NULL DEFAULT 0 COMMENT '已使用数量',
  limit_per_member INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '每人限领数',
  receive_begin_time DATETIME(3) NOT NULL COMMENT '领取开始时间',
  receive_end_time DATETIME(3) NOT NULL COMMENT '领取结束时间',
  valid_days INT UNSIGNED DEFAULT NULL COMMENT '领取后有效天数，固定有效期时为空',
  valid_begin_time DATETIME(3) DEFAULT NULL COMMENT '固定有效期开始时间',
  valid_end_time DATETIME(3) DEFAULT NULL COMMENT '固定有效期结束时间',
  status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态，0启用 1停用',
  version INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  create_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '创建者',
  create_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '更新者',
  update_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
  del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志，0存在 2删除',
  PRIMARY KEY (coupon_id),
  KEY idx_mall_coupon_status_receive_window (status, receive_begin_time, receive_end_time),
  CONSTRAINT chk_mall_coupon_amount CHECK (threshold_amount >= 0 AND discount_amount >= 0),
  CONSTRAINT chk_mall_coupon_quantity CHECK (total_quantity >= 0 AND received_quantity >= 0 AND used_quantity >= 0 AND received_quantity <= total_quantity AND used_quantity <= received_quantity),
  CONSTRAINT chk_mall_coupon_version CHECK (version >= 0),
  CONSTRAINT chk_mall_coupon_receive_window CHECK (receive_begin_time < receive_end_time),
  CONSTRAINT chk_mall_coupon_validity CHECK ((valid_days IS NOT NULL AND valid_days > 0 AND valid_begin_time IS NULL AND valid_end_time IS NULL) OR (valid_days IS NULL AND valid_begin_time IS NOT NULL AND valid_end_time IS NOT NULL AND valid_begin_time < valid_end_time))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城满减优惠券';

CREATE TABLE IF NOT EXISTS mall_order (
  order_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  order_no VARCHAR(32) NOT NULL COMMENT '订单号',
  member_id BIGINT UNSIGNED NOT NULL COMMENT '会员ID',
  client_request_no VARCHAR(64) NOT NULL COMMENT '客户端幂等请求号',
  order_status VARCHAR(32) NOT NULL COMMENT '订单状态',
  product_amount BIGINT NOT NULL COMMENT '商品总额，分',
  freight_amount BIGINT NOT NULL DEFAULT 0 COMMENT '运费，分',
  discount_amount BIGINT NOT NULL DEFAULT 0 COMMENT '优惠总额，分',
  payable_amount BIGINT NOT NULL COMMENT '应付金额，分',
  paid_amount BIGINT NOT NULL DEFAULT 0 COMMENT '实付金额，分',
  coupon_name_snapshot VARCHAR(128) DEFAULT NULL COMMENT '优惠券名称快照',
  coupon_threshold_snapshot BIGINT DEFAULT NULL COMMENT '优惠券门槛快照，分',
  coupon_discount_snapshot BIGINT DEFAULT NULL COMMENT '优惠券金额快照，分',
  receiver_name VARCHAR(64) NOT NULL COMMENT '收货人快照',
  receiver_phone VARCHAR(32) NOT NULL COMMENT '收货电话快照',
  province_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '省编码快照',
  province_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '省名称快照',
  city_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '市编码快照',
  city_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '市名称快照',
  district_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '区县编码快照',
  district_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '区县名称快照',
  detail_address VARCHAR(255) NOT NULL COMMENT '详细地址快照',
  pay_channel VARCHAR(32) DEFAULT NULL COMMENT '支付渠道',
  channel_trade_no VARCHAR(64) DEFAULT NULL COMMENT '支付渠道交易号',
  pay_prepay_id VARCHAR(128) DEFAULT NULL COMMENT '支付预支付标识',
  logistics_company VARCHAR(128) DEFAULT NULL COMMENT '物流公司',
  logistics_no VARCHAR(64) DEFAULT NULL COMMENT '物流单号',
  buyer_remark VARCHAR(500) DEFAULT NULL COMMENT '买家留言',
  admin_remark VARCHAR(500) DEFAULT NULL COMMENT '后台备注',
  cancel_reason VARCHAR(255) DEFAULT NULL COMMENT '取消原因',
  expire_time DATETIME(3) NOT NULL COMMENT '支付过期时间',
  pay_time DATETIME(3) DEFAULT NULL COMMENT '支付时间',
  ship_time DATETIME(3) DEFAULT NULL COMMENT '发货时间',
  complete_time DATETIME(3) DEFAULT NULL COMMENT '完成时间',
  cancel_time DATETIME(3) DEFAULT NULL COMMENT '取消时间',
  version INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  create_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '更新者',
  update_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (order_id),
  UNIQUE KEY uk_mall_order_no (order_no),
  UNIQUE KEY uk_mall_order_member_request (member_id, client_request_no),
  UNIQUE KEY uk_mall_order_pay_trade (pay_channel, channel_trade_no),
  KEY idx_mall_order_member_status_time (member_id, order_status, create_time),
  KEY idx_mall_order_status_time (order_status, create_time),
  CONSTRAINT chk_mall_order_amount CHECK (product_amount >= 0 AND freight_amount >= 0 AND discount_amount >= 0 AND payable_amount >= 0 AND paid_amount >= 0 AND discount_amount <= product_amount + freight_amount AND payable_amount = product_amount + freight_amount - discount_amount),
  CONSTRAINT chk_mall_order_version CHECK (version >= 0),
  CONSTRAINT fk_mall_order_member FOREIGN KEY (member_id) REFERENCES mall_member (member_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城订单';

CREATE TABLE IF NOT EXISTS mall_order_item (
  order_item_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  order_id BIGINT UNSIGNED NOT NULL COMMENT '订单ID',
  product_id BIGINT UNSIGNED NOT NULL COMMENT '商品ID',
  sku_id BIGINT UNSIGNED NOT NULL COMMENT 'SKU ID',
  product_name_snapshot VARCHAR(128) NOT NULL COMMENT '商品名称快照',
  sku_name_snapshot VARCHAR(128) NOT NULL COMMENT 'SKU名称快照',
  spec_values_snapshot JSON NULL COMMENT '规格快照JSON',
  image_url_snapshot VARCHAR(512) NOT NULL DEFAULT '' COMMENT '图片快照',
  sale_price_snapshot BIGINT NOT NULL COMMENT '销售单价快照，分',
  quantity INT UNSIGNED NOT NULL COMMENT '购买数量',
  line_amount BIGINT NOT NULL COMMENT '商品行金额，分',
  discount_amount BIGINT NOT NULL DEFAULT 0 COMMENT '优惠分摊金额，分',
  payable_amount BIGINT NOT NULL COMMENT '明细应付金额，分',
  PRIMARY KEY (order_item_id),
  KEY idx_mall_order_item_order (order_id),
  CONSTRAINT chk_mall_order_item_amount CHECK (sale_price_snapshot >= 0 AND line_amount >= 0 AND discount_amount >= 0 AND payable_amount >= 0 AND discount_amount <= line_amount AND payable_amount = line_amount - discount_amount),
  CONSTRAINT chk_mall_order_item_quantity CHECK (quantity > 0),
  CONSTRAINT fk_mall_order_item_order FOREIGN KEY (order_id) REFERENCES mall_order (order_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_mall_order_item_product FOREIGN KEY (product_id) REFERENCES mall_product (product_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_mall_order_item_sku FOREIGN KEY (sku_id) REFERENCES mall_product_sku (sku_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城订单明细';

CREATE TABLE IF NOT EXISTS mall_order_log (
  order_log_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单日志ID',
  order_id BIGINT UNSIGNED NOT NULL COMMENT '订单ID',
  action VARCHAR(64) NOT NULL COMMENT '操作动作',
  before_status VARCHAR(32) DEFAULT NULL COMMENT '操作前订单状态',
  after_status VARCHAR(32) NOT NULL COMMENT '操作后订单状态',
  operator_type VARCHAR(32) NOT NULL COMMENT '操作者类型，ADMIN MEMBER SYSTEM',
  operator_id VARCHAR(64) DEFAULT NULL COMMENT '操作者ID',
  description VARCHAR(500) NOT NULL DEFAULT '' COMMENT '脱敏操作说明',
  create_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY (order_log_id),
  KEY idx_mall_order_log_order_time (order_id, create_time),
  CONSTRAINT fk_mall_order_log_order FOREIGN KEY (order_id) REFERENCES mall_order (order_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城订单状态日志';

CREATE TABLE IF NOT EXISTS mall_member_coupon (
  member_coupon_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '会员券ID',
  coupon_no VARCHAR(32) NOT NULL COMMENT '会员券凭证号',
  coupon_id BIGINT UNSIGNED NOT NULL COMMENT '优惠券ID',
  member_id BIGINT UNSIGNED NOT NULL COMMENT '会员ID',
  coupon_name_snapshot VARCHAR(128) NOT NULL COMMENT '优惠券名称快照',
  threshold_amount_snapshot BIGINT NOT NULL COMMENT '门槛快照，分',
  discount_amount_snapshot BIGINT NOT NULL COMMENT '优惠金额快照，分',
  status VARCHAR(32) NOT NULL COMMENT '状态，AVAILABLE LOCKED USED EXPIRED VOID',
  valid_begin_time DATETIME(3) NOT NULL COMMENT '生效时间',
  valid_end_time DATETIME(3) NOT NULL COMMENT '失效时间',
  locked_order_id BIGINT UNSIGNED DEFAULT NULL COMMENT '锁定订单ID',
  used_order_id BIGINT UNSIGNED DEFAULT NULL COMMENT '使用订单ID',
  receive_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '领取时间',
  use_time DATETIME(3) DEFAULT NULL COMMENT '使用时间',
  version INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (member_coupon_id),
  UNIQUE KEY uk_mall_member_coupon_no (coupon_no),
  UNIQUE KEY uk_mall_member_coupon_locked_order (locked_order_id),
  UNIQUE KEY uk_mall_member_coupon_used_order (used_order_id),
  KEY idx_mall_member_coupon_member_status_end (member_id, status, valid_end_time),
  KEY idx_mall_member_coupon_coupon_member (coupon_id, member_id),
  CONSTRAINT chk_mall_member_coupon_amount CHECK (threshold_amount_snapshot >= 0 AND discount_amount_snapshot >= 0),
  CONSTRAINT chk_mall_member_coupon_version CHECK (version >= 0),
  CONSTRAINT chk_mall_member_coupon_window CHECK (valid_begin_time < valid_end_time),
  CONSTRAINT fk_mall_member_coupon_coupon FOREIGN KEY (coupon_id) REFERENCES mall_coupon (coupon_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_mall_member_coupon_member FOREIGN KEY (member_id) REFERENCES mall_member (member_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_mall_member_coupon_locked_order FOREIGN KEY (locked_order_id) REFERENCES mall_order (order_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_mall_member_coupon_used_order FOREIGN KEY (used_order_id) REFERENCES mall_order (order_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城会员优惠券';

CREATE TABLE IF NOT EXISTS mall_inventory_log (
  inventory_log_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '库存流水ID',
  sku_id BIGINT UNSIGNED NOT NULL COMMENT 'SKU ID',
  biz_type VARCHAR(32) NOT NULL COMMENT '业务类型，INBOUND ADJUST LOCK RELEASE DEDUCT',
  biz_no VARCHAR(64) NOT NULL COMMENT '业务编号',
  available_change BIGINT NOT NULL COMMENT '可用库存变更量',
  locked_change BIGINT NOT NULL COMMENT '锁定库存变更量',
  available_before BIGINT NOT NULL COMMENT '变更前可用库存',
  available_after BIGINT NOT NULL COMMENT '变更后可用库存',
  locked_before BIGINT NOT NULL COMMENT '变更前锁定库存',
  locked_after BIGINT NOT NULL COMMENT '变更后锁定库存',
  operator_type VARCHAR(32) NOT NULL COMMENT '操作者类型，ADMIN MEMBER SYSTEM',
  operator_id VARCHAR(64) DEFAULT NULL COMMENT '操作者ID',
  reason VARCHAR(500) NOT NULL DEFAULT '' COMMENT '变更原因',
  create_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  PRIMARY KEY (inventory_log_id),
  UNIQUE KEY uk_mall_inventory_log_sku_biz (sku_id, biz_type, biz_no),
  KEY idx_mall_inventory_log_sku_time (sku_id, create_time),
  CONSTRAINT chk_mall_inventory_log_stock CHECK (available_before >= 0 AND available_after >= 0 AND locked_before >= 0 AND locked_after >= 0 AND available_after = available_before + available_change AND locked_after = locked_before + locked_change),
  CONSTRAINT fk_mall_inventory_log_sku FOREIGN KEY (sku_id) REFERENCES mall_product_sku (sku_id) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城库存流水';

CREATE TABLE IF NOT EXISTS mall_banner (
  banner_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Banner ID',
  title VARCHAR(128) NOT NULL COMMENT '标题',
  image_url VARCHAR(512) NOT NULL COMMENT '图片地址',
  jump_type VARCHAR(32) NOT NULL COMMENT '跳转类型，NONE PRODUCT CATEGORY PATH',
  target_value VARCHAR(512) DEFAULT NULL COMMENT '跳转目标，由跳转类型解释',
  sort_num INT NOT NULL DEFAULT 0 COMMENT '排序值，越小越靠前',
  status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态，0启用 1停用',
  begin_time DATETIME(3) NOT NULL COMMENT '展示开始时间',
  end_time DATETIME(3) NOT NULL COMMENT '展示结束时间',
  create_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '创建者',
  create_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_by VARCHAR(64) NOT NULL DEFAULT '' COMMENT '更新者',
  update_time DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
  del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志，0存在 2删除',
  PRIMARY KEY (banner_id),
  KEY idx_mall_banner_status_sort_window (status, sort_num, begin_time, end_time),
  CONSTRAINT chk_mall_banner_sort CHECK (sort_num >= 0),
  CONSTRAINT chk_mall_banner_window CHECK (begin_time < end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城首页Banner';

-- 回滚说明：本期不提供自动回滚 SQL，避免误删已产生的订单、库存和券凭证数据。


-- source: D:\AIruoyi_project\agritech-vue\sql\update\2026-07-24-02-新增商城菜单字典.sql
-- 商城菜单、按钮权限和状态字典。
-- 本脚本仅使用防重复插入；不会覆盖已有菜单、字典或角色授权。
-- 执行后在“角色管理”中按岗位授予对应菜单和按钮权限。

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3000, '商城管理', 0, 4, 'shop', NULL, '', '', 1, 0, 'M', '0', '0', '', 'shopping', 'admin', NOW(), '', NULL, '商城运营目录'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3000)
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE parent_id = 0 AND path = 'shop' AND menu_type = 'M');

SET @shop_menu_id := COALESCE(
  (SELECT menu_id FROM sys_menu WHERE menu_id = 3000),
  (SELECT menu_id FROM sys_menu WHERE parent_id = 0 AND path = 'shop' AND menu_type = 'M' ORDER BY menu_id LIMIT 1)
);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT m.menu_id, m.menu_name, @shop_menu_id, m.order_num, m.path, m.component, '', '', 1, 0, 'C', '0', '0', m.perms, m.icon, 'admin', NOW(), '', NULL, m.remark
FROM (
  SELECT 3001 menu_id, '商品管理' menu_name, 1 order_num, 'product' path, 'shop/product/index' component, 'shop:product:list' perms, 'shopping-bag' icon, '商城商品管理' remark
  UNION ALL SELECT 3002, '分类管理', 2, 'category', 'shop/category/index', 'shop:category:list', 'tree-table', '商城分类管理'
  UNION ALL SELECT 3003, '订单管理', 3, 'order', 'shop/order/index', 'shop:order:list', 'list', '商城订单管理'
  UNION ALL SELECT 3004, '会员管理', 4, 'member', 'shop/member/index', 'shop:member:list', 'peoples', '商城会员管理'
  UNION ALL SELECT 3005, 'Banner管理', 5, 'banner', 'shop/banner/index', 'shop:banner:list', 'picture', '商城Banner管理'
  UNION ALL SELECT 3006, '优惠券管理', 6, 'coupon', 'shop/coupon/index', 'shop:coupon:list', 'discount', '商城优惠券管理'
) m
WHERE NOT EXISTS (SELECT 1 FROM sys_menu s WHERE s.menu_id = m.menu_id OR s.perms = m.perms);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT b.menu_id, b.menu_name, b.parent_id, b.order_num, '', '', '', '', 1, 0, 'F', '0', '0', b.perms, '#', 'admin', NOW(), '', NULL, b.remark
FROM (
  SELECT 3101 menu_id, '商品查询' menu_name, 3001 parent_id, 1 order_num, 'shop:product:query' perms, '查询商品' remark
  UNION ALL SELECT 3102, '商品新增', 3001, 2, 'shop:product:add', '新增商品'
  UNION ALL SELECT 3103, '商品修改', 3001, 3, 'shop:product:edit', '修改商品'
  UNION ALL SELECT 3104, '商品删除', 3001, 4, 'shop:product:remove', '删除商品'
  UNION ALL SELECT 3105, '商品导出', 3001, 5, 'shop:product:export', '导出商品'
  UNION ALL SELECT 3106, '商品上下架', 3001, 6, 'shop:product:status', '商品上下架'
  UNION ALL SELECT 3107, '库存调整', 3001, 7, 'shop:product:stock', '调整商品库存'
  UNION ALL SELECT 3111, '分类查询', 3002, 1, 'shop:category:query', '查询分类'
  UNION ALL SELECT 3112, '分类新增', 3002, 2, 'shop:category:add', '新增分类'
  UNION ALL SELECT 3113, '分类修改', 3002, 3, 'shop:category:edit', '修改分类'
  UNION ALL SELECT 3114, '分类删除', 3002, 4, 'shop:category:remove', '删除分类'
  UNION ALL SELECT 3115, '分类导出', 3002, 5, 'shop:category:export', '导出分类'
  UNION ALL SELECT 3121, '订单查询', 3003, 1, 'shop:order:query', '查询订单'
  UNION ALL SELECT 3122, '订单导出', 3003, 2, 'shop:order:export', '导出订单'
  UNION ALL SELECT 3123, '订单取消', 3003, 3, 'shop:order:cancel', '取消待支付订单'
  UNION ALL SELECT 3124, '订单发货', 3003, 4, 'shop:order:ship', '订单发货'
  UNION ALL SELECT 3125, '敏感信息查看', 3003, 5, 'shop:order:sensitive', '查看订单明文地址和电话'
  UNION ALL SELECT 3126, '订单创建', 3003, 6, 'shop:order:add', '创建订单'
  UNION ALL SELECT 3127, '支付确认', 3003, 7, 'shop:order:pay', '订单支付确认'
  UNION ALL SELECT 3131, '会员查询', 3004, 1, 'shop:member:query', '查询会员'
  UNION ALL SELECT 3132, '会员导出', 3004, 2, 'shop:member:export', '导出会员'
  UNION ALL SELECT 3133, '会员状态修改', 3004, 3, 'shop:member:status', '启用或禁用会员'
  UNION ALL SELECT 3134, '会员敏感信息查看', 3004, 4, 'shop:member:sensitive', '查看会员明文地址和电话'
  UNION ALL SELECT 3141, 'Banner查询', 3005, 1, 'shop:banner:query', '查询Banner'
  UNION ALL SELECT 3142, 'Banner新增', 3005, 2, 'shop:banner:add', '新增Banner'
  UNION ALL SELECT 3143, 'Banner修改', 3005, 3, 'shop:banner:edit', '修改Banner'
  UNION ALL SELECT 3144, 'Banner删除', 3005, 4, 'shop:banner:remove', '删除Banner'
  UNION ALL SELECT 3145, 'Banner状态修改', 3005, 5, 'shop:banner:status', '启用或停用Banner'
  UNION ALL SELECT 3146, 'Banner导出', 3005, 6, 'shop:banner:export', '导出Banner'
  UNION ALL SELECT 3151, '优惠券查询', 3006, 1, 'shop:coupon:query', '查询优惠券'
  UNION ALL SELECT 3152, '优惠券新增', 3006, 2, 'shop:coupon:add', '新增优惠券'
  UNION ALL SELECT 3153, '优惠券修改', 3006, 3, 'shop:coupon:edit', '修改优惠券'
  UNION ALL SELECT 3154, '优惠券删除', 3006, 4, 'shop:coupon:remove', '删除优惠券'
  UNION ALL SELECT 3155, '优惠券状态修改', 3006, 5, 'shop:coupon:status', '启用或停用优惠券'
  UNION ALL SELECT 3156, '优惠券导出', 3006, 6, 'shop:coupon:export', '导出优惠券'
  UNION ALL SELECT 3199, '经营总览查询', 3000, 99, 'shop:dashboard:query', '查看首页经营总览'
) b
WHERE NOT EXISTS (SELECT 1 FROM sys_menu s WHERE s.menu_id = b.menu_id OR s.perms = b.perms);

INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark)
SELECT d.dict_name, d.dict_type, '0', 'admin', NOW(), '', NULL, d.remark
FROM (
  SELECT '商城商品状态' dict_name, 'mall_product_status' dict_type, '商品草稿、上架、下架' remark
  UNION ALL SELECT '商城SKU状态', 'mall_sku_status', 'SKU启用、停用'
  UNION ALL SELECT '商城库存业务类型', 'mall_inventory_biz_type', '库存入库、调整、锁定、释放、实扣'
  UNION ALL SELECT '商城订单状态', 'mall_order_status', '订单状态机'
  UNION ALL SELECT '商城会员状态', 'mall_member_status', '会员正常、禁用'
  UNION ALL SELECT '商城Banner跳转类型', 'mall_banner_jump_type', 'Banner跳转目标类型'
  UNION ALL SELECT '商城优惠券状态', 'mall_coupon_status', '优惠券启用、停用'
  UNION ALL SELECT '商城会员券状态', 'mall_member_coupon_status', '会员券可用、锁定、已使用、已过期、作废'
) d
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type t WHERE t.dict_type = d.dict_type);

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark)
SELECT d.dict_sort, d.dict_label, d.dict_value, d.dict_type, '', d.list_class, d.is_default, '0', 'admin', NOW(), '', NULL, d.remark
FROM (
  SELECT 1 dict_sort, '草稿' dict_label, '0' dict_value, 'mall_product_status' dict_type, 'info' list_class, 'Y' is_default, '商品草稿' remark
  UNION ALL SELECT 2, '上架', '1', 'mall_product_status', 'success', 'N', '商品上架'
  UNION ALL SELECT 3, '下架', '2', 'mall_product_status', 'danger', 'N', '商品下架'
  UNION ALL SELECT 1, '启用', '0', 'mall_sku_status', 'success', 'Y', 'SKU启用'
  UNION ALL SELECT 2, '停用', '1', 'mall_sku_status', 'danger', 'N', 'SKU停用'
  UNION ALL SELECT 1, '入库', 'INBOUND', 'mall_inventory_biz_type', 'success', 'N', '库存入库'
  UNION ALL SELECT 2, '调整', 'ADJUST', 'mall_inventory_biz_type', 'warning', 'N', '库存调整'
  UNION ALL SELECT 3, '锁定', 'LOCK', 'mall_inventory_biz_type', 'primary', 'N', '下单锁定库存'
  UNION ALL SELECT 4, '释放', 'RELEASE', 'mall_inventory_biz_type', 'info', 'N', '取消释放库存'
  UNION ALL SELECT 5, '实扣', 'DEDUCT', 'mall_inventory_biz_type', 'danger', 'N', '支付实扣库存'
  UNION ALL SELECT 1, '待支付', 'PENDING_PAYMENT', 'mall_order_status', 'warning', 'N', '待支付订单'
  UNION ALL SELECT 2, '待发货', 'PENDING_SHIPMENT', 'mall_order_status', 'primary', 'N', '待发货订单'
  UNION ALL SELECT 3, '已发货', 'SHIPPED', 'mall_order_status', 'primary', 'N', '已发货订单'
  UNION ALL SELECT 4, '已完成', 'COMPLETED', 'mall_order_status', 'success', 'N', '已完成订单'
  UNION ALL SELECT 5, '已取消', 'CANCELED', 'mall_order_status', 'info', 'N', '已取消订单'
  UNION ALL SELECT 1, '正常', '0', 'mall_member_status', 'success', 'Y', '会员正常'
  UNION ALL SELECT 2, '禁用', '1', 'mall_member_status', 'danger', 'N', '会员禁用'
  UNION ALL SELECT 1, '不跳转', 'NONE', 'mall_banner_jump_type', 'info', 'N', 'Banner不跳转'
  UNION ALL SELECT 2, '商品', 'PRODUCT', 'mall_banner_jump_type', 'primary', 'N', '跳转商品'
  UNION ALL SELECT 3, '分类', 'CATEGORY', 'mall_banner_jump_type', 'primary', 'N', '跳转分类'
  UNION ALL SELECT 4, '小程序路径', 'PATH', 'mall_banner_jump_type', 'primary', 'N', '跳转小程序路径'
  UNION ALL SELECT 1, '启用', '0', 'mall_coupon_status', 'success', 'Y', '优惠券启用'
  UNION ALL SELECT 2, '停用', '1', 'mall_coupon_status', 'danger', 'N', '优惠券停用'
  UNION ALL SELECT 1, '可用', 'AVAILABLE', 'mall_member_coupon_status', 'success', 'N', '会员券可用'
  UNION ALL SELECT 2, '锁定', 'LOCKED', 'mall_member_coupon_status', 'warning', 'N', '会员券锁定'
  UNION ALL SELECT 3, '已使用', 'USED', 'mall_member_coupon_status', 'info', 'N', '会员券已使用'
  UNION ALL SELECT 4, '已过期', 'EXPIRED', 'mall_member_coupon_status', 'danger', 'N', '会员券已过期'
  UNION ALL SELECT 5, '已作废', 'VOID', 'mall_member_coupon_status', 'info', 'N', '会员券已作废'
) d
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data x WHERE x.dict_type = d.dict_type AND x.dict_value = d.dict_value);


-- source: D:\AIruoyi_project\agritech-vue\sql\update\2026-07-24-03-新增商城超时订单任务.sql
-- 商城订单超时关闭任务。执行前确认 2026-07-24-01 建表脚本已成功执行。
-- 使用防重复插入，不覆盖既有同名任务配置。
INSERT INTO sys_job (job_name, job_group, invoke_target, cron_expression, misfire_policy, concurrent, status, create_by, create_time, remark)
SELECT '商城超时订单关闭', 'DEFAULT', 'shopTask.closeExpiredOrders()', '0 * * * * ?', '3', '1', '0', 'admin', NOW(), '扫描并关闭超时待支付订单'
WHERE NOT EXISTS (
  SELECT 1 FROM sys_job WHERE job_name = '商城超时订单关闭' AND job_group = 'DEFAULT'
);


-- source: D:\AIruoyi_project\agritech-vue\sql\update\2026-07-27-删除若依官网菜单.sql
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


-- source: D:\AIruoyi_project\agritech-vue\sql\update\2026-07-27-商城订单补充更新审计字段.sql
-- mall_order 发货、取消、完成会写 update_by；补齐已建库缺失的更新者字段。

SET @has_update_by := (
  SELECT COUNT(1)
  FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'mall_order'
    AND COLUMN_NAME = 'update_by'
);

SET @ddl := IF(
  @has_update_by = 0,
  'ALTER TABLE mall_order ADD COLUMN update_by VARCHAR(64) NOT NULL DEFAULT '''' COMMENT ''更新者'' AFTER create_time',
  'SELECT 1'
);

PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


-- source: D:\AIruoyi_project\agritech-vue\sql\update\2026-07-27-新增小程序商城配置.sql
-- 小程序商城首期配送与订单配置。执行前请按实际经营规则复核金额和不可配送地区。
-- 金额单位均为分；不支持地区使用逗号分隔的行政区编码。

INSERT INTO sys_config (config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark)
SELECT value_name, value_key, value_value, 'Y', 'admin', NOW(), '', NULL, value_remark
FROM (
  SELECT '商城统一运费' value_name, 'mall.freight.amount' value_key, '1000' value_value, '金额单位分' value_remark
  UNION ALL SELECT '商城包邮门槛', 'mall.freight.freeThreshold', '9900', '金额单位分，0表示不启用包邮'
  UNION ALL SELECT '商城订单支付超时分钟数', 'mall.order.expireMinutes', '30', '待支付订单关闭时限'
  UNION ALL SELECT '商城发货说明', 'mall.delivery.notice', '订单支付完成后按商品实际情况安排发货', '小程序展示文案'
  UNION ALL SELECT '商城不可配送地区', 'mall.delivery.unsupportedRegionCodes', '', '逗号分隔行政区编码，空表示不限制'
) config_values
WHERE NOT EXISTS (SELECT 1 FROM sys_config c WHERE c.config_key = config_values.value_key);


-- source: D:\AIruoyi_project\agritech-vue\sql\update\2026-07-27-商城订单链路验收数据.sql
-- 商城订单链路验收数据。
-- 仅插入固定 tag 的会员、地址和会员券；重复执行不重复造数据。

SET @tag := 'ACC20260727A' COLLATE utf8mb4_unicode_ci;

INSERT INTO mall_member (app_id, open_id, union_id, nickname, avatar_url, phone, status, last_login_time)
SELECT @tag, CONCAT(@tag, '-OPENID'), NULL, CONCAT(@tag, '-验收会员'), '', '13800138000', '0', CURRENT_TIMESTAMP(3)
WHERE NOT EXISTS (
  SELECT 1 FROM mall_member WHERE app_id = @tag AND open_id = CONCAT(@tag, '-OPENID')
);

SET @member_id := (
  SELECT member_id FROM mall_member WHERE app_id = @tag AND open_id = CONCAT(@tag, '-OPENID') ORDER BY member_id LIMIT 1
);

INSERT INTO mall_member_address (member_id, receiver_name, receiver_phone, province_code, province_name, city_code, city_name, district_code, district_name, detail_address, is_default)
SELECT @member_id, '验收收件人', '13800138000', '520000', '贵州省', '520100', '贵阳市', '520102', '南明区', '验收路 1 号', '1'
WHERE NOT EXISTS (
  SELECT 1 FROM mall_member_address
  WHERE member_id = @member_id AND receiver_phone = '13800138000' AND detail_address = '验收路 1 号' AND del_flag = '0'
);

SET @coupon_id := (
  SELECT coupon_id FROM mall_coupon WHERE coupon_name = CONCAT(@tag, '-满减券') AND del_flag = '0' ORDER BY coupon_id LIMIT 1
);

INSERT INTO mall_member_coupon (coupon_no, coupon_id, member_id, coupon_name_snapshot, threshold_amount_snapshot, discount_amount_snapshot, status, valid_begin_time, valid_end_time)
SELECT CONCAT(@tag, '-MC-001'), @coupon_id, @member_id, coupon_name, threshold_amount, discount_amount, 'AVAILABLE', CURRENT_TIMESTAMP(3), DATE_ADD(CURRENT_TIMESTAMP(3), INTERVAL 30 DAY)
FROM mall_coupon
WHERE coupon_id = @coupon_id
  AND NOT EXISTS (
    SELECT 1 FROM mall_member_coupon WHERE coupon_no = CONCAT(@tag, '-MC-001')
  );

UPDATE mall_coupon c
SET c.received_quantity = GREATEST(c.received_quantity, (
  SELECT COUNT(1) FROM mall_member_coupon mc WHERE mc.coupon_id = c.coupon_id
))
WHERE c.coupon_id = @coupon_id;


-- source: D:\AIruoyi_project\agritech-vue\sql\update\2026-07-28-新增小程序商城闭环验收数据.sql
-- 小程序商城闭环验收种子数据。
-- 先执行 2026-07-27-新增小程序商城配置.sql；本脚本仅新增 E2E20260728A 标签数据，可重复执行。
-- 订单、库存锁定、优惠券状态和物流状态必须通过接口流程产生，避免伪造交易事实。

SET @tag := 'E2E20260728A' COLLATE utf8mb4_unicode_ci;

INSERT INTO mall_category (parent_id, category_name, level, sort_num, status, create_by, remark)
SELECT NULL, CONCAT(@tag, '-闭环分类'), 1, 999, '0', 'e2e', '小程序闭环验收数据'
WHERE NOT EXISTS (
  SELECT 1 FROM mall_category WHERE category_name = CONCAT(@tag, '-闭环分类') AND del_flag = '0'
);

SET @category_id := (
  SELECT category_id FROM mall_category
  WHERE category_name = CONCAT(@tag, '-闭环分类') AND del_flag = '0'
  ORDER BY category_id LIMIT 1
);

INSERT INTO mall_product (category_id, product_name, subtitle, main_image, images_json, detail_html, product_params_json, spec_schema_json, status, publish_time, create_by, remark)
SELECT @category_id, CONCAT(@tag, '-黄精切片闭环验收装'), '用于小程序订单全流程验收', 'https://example.com/e2e-huangjing.jpg',
  JSON_ARRAY('https://example.com/e2e-huangjing.jpg'), '<p>闭环验收商品，仅用于测试。</p>',
  JSON_OBJECT('产地', '贵州省', '净含量', '100g', '加工方式', '切片', '配料', '黄精', '储存条件', '阴凉干燥处', '保质期', '18个月'),
  JSON_ARRAY(JSON_OBJECT('name', '规格', 'values', JSON_ARRAY('闭环验收装'))), '1', CURRENT_TIMESTAMP(3), 'e2e', '小程序闭环验收数据'
WHERE NOT EXISTS (
  SELECT 1 FROM mall_product WHERE product_name = CONCAT(@tag, '-黄精切片闭环验收装') AND del_flag = '0'
);

SET @product_id := (
  SELECT product_id FROM mall_product
  WHERE product_name = CONCAT(@tag, '-黄精切片闭环验收装') AND del_flag = '0'
  ORDER BY product_id LIMIT 1
);

INSERT INTO mall_product_sku (product_id, sku_code, sku_name, spec_values_json, image_url, sale_price, market_price, available_stock, locked_stock, warning_stock, status, create_by, remark)
SELECT @product_id, CONCAT(@tag, '-SKU-001'), '闭环验收装', JSON_OBJECT('规格', '闭环验收装'), 'https://example.com/e2e-huangjing.jpg', 9900, 9900, 20, 0, 2, '0', 'e2e', '小程序闭环验收数据'
WHERE NOT EXISTS (
  SELECT 1 FROM mall_product_sku WHERE sku_code = CONCAT(@tag, '-SKU-001')
);

INSERT INTO mall_banner (title, image_url, jump_type, target_value, sort_num, status, begin_time, end_time, create_by, remark)
SELECT CONCAT(@tag, '-首页验收 Banner'), 'https://example.com/e2e-banner.jpg', 'PRODUCT', CAST(@product_id AS CHAR), 999, '0',
  DATE_SUB(CURRENT_TIMESTAMP(3), INTERVAL 1 DAY), DATE_ADD(CURRENT_TIMESTAMP(3), INTERVAL 30 DAY), 'e2e', '小程序闭环验收数据'
WHERE NOT EXISTS (
  SELECT 1 FROM mall_banner WHERE title = CONCAT(@tag, '-首页验收 Banner') AND del_flag = '0'
);

INSERT INTO mall_coupon (coupon_name, threshold_amount, discount_amount, total_quantity, received_quantity, used_quantity, limit_per_member, receive_begin_time, receive_end_time, valid_days, valid_begin_time, valid_end_time, status, create_by, remark)
SELECT CONCAT(@tag, '-满99减99'), 9900, 9900, 10, 0, 0, 1,
  DATE_SUB(CURRENT_TIMESTAMP(3), INTERVAL 1 DAY), DATE_ADD(CURRENT_TIMESTAMP(3), INTERVAL 30 DAY), 30, NULL, NULL, '0', 'e2e', '小程序闭环验收数据'
WHERE NOT EXISTS (
  SELECT 1 FROM mall_coupon WHERE coupon_name = CONCAT(@tag, '-满99减99') AND del_flag = '0'
);

INSERT INTO mall_member (app_id, open_id, nickname, avatar_url, phone, status, last_login_time)
SELECT @tag, CONCAT(@tag, '-OPENID'), CONCAT(@tag, '-验收会员'), '', '13900000028', '0', CURRENT_TIMESTAMP(3)
WHERE NOT EXISTS (
  SELECT 1 FROM mall_member WHERE app_id = @tag AND open_id = CONCAT(@tag, '-OPENID')
);


SET FOREIGN_KEY_CHECKS = 1;
