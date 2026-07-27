import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

test('banner and coupon pages provide complete editable management workflows', async () => {
  const [banner, coupon] = await Promise.all([
    readFile(new URL('../src/views/shop/banner/index.vue', import.meta.url), 'utf8'),
    readFile(new URL('../src/views/shop/coupon/index.vue', import.meta.url), 'utf8')
  ])

  for (const page of [banner, coupon]) {
    assert.match(page, /新增/)
    assert.match(page, /修改/)
    assert.match(page, /<pagination/)
    assert.match(page, /submitting/)
  }

  assert.match(banner, /addBanner/)
  assert.match(banner, /updateBanner/)
  assert.match(banner, /getBanner/)
  assert.match(banner, /展示结束时间必须晚于开始时间/)
  assert.match(coupon, /addCoupon/)
  assert.match(coupon, /updateCoupon/)
  assert.match(coupon, /getCoupon/)
  assert.match(coupon, /优惠金额不能大于使用门槛/)

  const product = await readFile(new URL('../src/views/shop/product/index.vue', import.meta.url), 'utf8')
  assert.match(product, /库存调整/)
  assert.match(product, /adjustInventory/)
  assert.match(product, /调整原因不能为空/)
})
