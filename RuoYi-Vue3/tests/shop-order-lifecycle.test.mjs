import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

test('shop order lifecycle supports completion and expires unused member coupons', async () => {
  const [controller, service, mapper, xml, task, page, schema] = await Promise.all([
    readFile(new URL('../../ruoyi-admin/src/main/java/com/ruoyi/web/controller/shop/MallOrderController.java', import.meta.url), 'utf8'),
    readFile(new URL('../../ruoyi-system/src/main/java/com/ruoyi/system/service/impl/MallOrderServiceImpl.java', import.meta.url), 'utf8'),
    readFile(new URL('../../ruoyi-system/src/main/java/com/ruoyi/system/mapper/shop/MallOrderMapper.java', import.meta.url), 'utf8'),
    readFile(new URL('../../ruoyi-system/src/main/resources/mapper/shop/MallOrderMapper.xml', import.meta.url), 'utf8'),
    readFile(new URL('../../ruoyi-quartz/src/main/java/com/ruoyi/quartz/task/ShopTask.java', import.meta.url), 'utf8'),
    readFile(new URL('../src/views/shop/order/index.vue', import.meta.url), 'utf8'),
    readFile(new URL('../../sql/update/2026-07-24-01-新增商城业务表.sql', import.meta.url), 'utf8')
  ])

  assert.match(controller, /\/\{orderId\}\/complete/)
  assert.match(service, /completeOrder/)
  assert.match(mapper, /completeShippedOrder/)
  assert.match(xml, /id="completeShippedOrder"/)
  const orderTable = schema.match(/CREATE TABLE IF NOT EXISTS mall_order \([\s\S]*?\n\) ENGINE/)?.[0] || ''
  assert.match(xml, /shipPendingOrder.*update_by/s)
  assert.match(orderTable, /update_by VARCHAR\(64\)/)
  assert.match(orderTable, /update_time DATETIME\(3\)/)
  assert.match(page, /确认收货/)
  assert.match(task, /expireCoupons/)
})
