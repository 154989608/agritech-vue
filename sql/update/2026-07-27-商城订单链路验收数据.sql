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
