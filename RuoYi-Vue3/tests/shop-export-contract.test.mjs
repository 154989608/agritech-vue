import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

test('shop controllers expose RuoYi export endpoints for list pages', async () => {
  const controllers = [
    ['MallProductController.java', 'shop:product:export', 'MallProduct'],
    ['MallCategoryController.java', 'shop:category:export', 'MallCategory'],
    ['MallOrderController.java', 'shop:order:export', 'MallOrder'],
    ['MallMemberController.java', 'shop:member:export', 'MallMember'],
    ['MallBannerController.java', 'shop:banner:export', 'MallBanner'],
    ['MallCouponController.java', 'shop:coupon:export', 'MallCoupon']
  ]

  for (const [file, permission, domain] of controllers) {
    const source = await readFile(new URL(`../../ruoyi-admin/src/main/java/com/ruoyi/web/controller/shop/${file}`, import.meta.url), 'utf8')
    assert.match(source, new RegExp(permission.replaceAll(':', ':')))
    assert.match(source, /@PostMapping\("\/export"\)/)
    assert.match(source, new RegExp(`ExcelUtil<${domain}>`))
    assert.match(source, /BusinessType\.EXPORT/)
  }

  const pages = [
    ['product/index.vue', 'shop/product/export', 'shop:product:export'],
    ['category/index.vue', 'shop/category/export', 'shop:category:export'],
    ['order/index.vue', 'shop/order/export', 'shop:order:export'],
    ['member/index.vue', 'shop/member/export', 'shop:member:export'],
    ['banner/index.vue', 'shop/banner/export', 'shop:banner:export'],
    ['coupon/index.vue', 'shop/coupon/export', 'shop:coupon:export']
  ]

  for (const [file, url, permission] of pages) {
    const source = await readFile(new URL(`../src/views/shop/${file}`, import.meta.url), 'utf8')
    assert.match(source, /handleExport/)
    assert.match(source, new RegExp(url))
    assert.match(source, new RegExp(permission))
  }
})
