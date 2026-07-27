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
