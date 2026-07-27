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
