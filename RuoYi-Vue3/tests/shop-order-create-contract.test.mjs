import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

test('order creation input excludes client controlled money fields', async () => {
  const request = await readFile(new URL('../../ruoyi-system/src/main/java/com/ruoyi/system/domain/shop/MallOrderCreateRequest.java', import.meta.url), 'utf8')
  assert.match(request, /clientRequestNo/)
  assert.match(request, /addressId/)
  assert.match(request, /memberCouponId/)
  assert.match(request, /skuId/)
  assert.match(request, /quantity/)
  assert.doesNotMatch(request, /salePrice|paidAmount|payableAmount/)
})

test('order creation and payment lifecycle are service-owned transactions', async () => {
  const [controller, service, mapper, xml] = await Promise.all([
    readFile(new URL('../../ruoyi-admin/src/main/java/com/ruoyi/web/controller/shop/MallOrderController.java', import.meta.url), 'utf8'),
    readFile(new URL('../../ruoyi-system/src/main/java/com/ruoyi/system/service/impl/MallOrderServiceImpl.java', import.meta.url), 'utf8'),
    readFile(new URL('../../ruoyi-system/src/main/java/com/ruoyi/system/mapper/shop/MallOrderMapper.java', import.meta.url), 'utf8'),
    readFile(new URL('../../ruoyi-system/src/main/resources/mapper/shop/MallOrderMapper.xml', import.meta.url), 'utf8')
  ])

  assert.match(controller, /MallOrderCreateRequest/)
  assert.match(controller, /shop:order:add/)
  assert.match(controller, /@PreAuthorize\("@ss\.hasPermi\('shop:order:add'\)"\).*@PostMapping/s)
  assert.match(controller, /\/pay-success/)
  assert.match(controller, /@PreAuthorize\("@ss\.hasPermi\('shop:order:pay'\)"\).*\/pay-success/s)
  assert.match(service, /@Transactional\(rollbackFor=Exception\.class\).*createOrder/s)
  assert.match(service, /@Transactional\(rollbackFor=Exception\.class\).*paySuccess/s)
  assert.match(service, /getPayableAmount\(\)==0L.*paySuccess/s)
  assert.match(mapper, /lockSkuStock/)
  assert.match(mapper, /deductLockedStock/)
  assert.match(xml, /id="insertOrder"/)
  assert.match(xml, /id="insertOrderItem"/)
  assert.match(xml, /biz_type,biz_no/)
  assert.match(xml, /order_status='PENDING_PAYMENT'/)
})

test('order sensitive data stays behind explicit backend and frontend permission gates', async () => {
  const [controller, api, page, menuSql] = await Promise.all([
    readFile(new URL('../../ruoyi-admin/src/main/java/com/ruoyi/web/controller/shop/MallOrderController.java', import.meta.url), 'utf8'),
    readFile(new URL('../src/api/shop/order.js', import.meta.url), 'utf8'),
    readFile(new URL('../src/views/shop/order/index.vue', import.meta.url), 'utf8'),
    readFile(new URL('../../sql/update/2026-07-24-02-新增商城菜单字典.sql', import.meta.url), 'utf8')
  ])

  assert.match(controller, /shop:order:sensitive/)
  assert.match(api, /getOrderSensitive/)
  assert.match(page, /getOrderSensitive/)
  assert.match(page, /shop:order:sensitive/)
  assert.match(page, /查看明文/)
  assert.match(menuSql, /shop:order:add/)
  assert.match(menuSql, /shop:order:pay/)
  assert.match(menuSql, /shop:order:sensitive/)
})
