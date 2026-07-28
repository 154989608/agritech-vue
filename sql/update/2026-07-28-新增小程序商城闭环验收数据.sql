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
