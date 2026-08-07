SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS `ry_vue`;
CREATE DATABASE `ry_vue` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ry_vue`;

SOURCE D:/AIruoyi_project/agritech-vue/sql/ry_20250522.sql;
SOURCE D:/AIruoyi_project/agritech-vue/sql/quartz.sql;
SOURCE D:/AIruoyi_project/agritech-vue/sql/system_menu_routes_rebuild.sql;
SOURCE D:/AIruoyi_project/agritech-vue/sql/update/2026-07-24-01-新增商城业务表.sql;
SOURCE D:/AIruoyi_project/agritech-vue/sql/update/2026-07-24-02-新增商城菜单字典.sql;
SOURCE D:/AIruoyi_project/agritech-vue/sql/update/2026-07-24-03-新增商城超时订单任务.sql;
SOURCE D:/AIruoyi_project/agritech-vue/sql/update/2026-07-27-删除若依官网菜单.sql;
SOURCE D:/AIruoyi_project/agritech-vue/sql/update/2026-07-27-商城订单补充更新审计字段.sql;
SOURCE D:/AIruoyi_project/agritech-vue/sql/update/2026-07-27-新增小程序商城配置.sql;
SOURCE D:/AIruoyi_project/agritech-vue/sql/update/2026-07-27-商城订单链路验收数据.sql;
SOURCE D:/AIruoyi_project/agritech-vue/sql/update/2026-07-28-新增小程序商城闭环验收数据.sql;

SET FOREIGN_KEY_CHECKS = 1;
