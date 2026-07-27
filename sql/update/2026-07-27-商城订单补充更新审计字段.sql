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
