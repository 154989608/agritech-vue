import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

const root = new URL('../../', import.meta.url)
const source = (path) => readFile(new URL(path, root), 'utf8')

test('member coupon APIs use the member principal and existing transactional claim service', async () => {
  const [controller, couponService, mapper, xml, dto] = await Promise.all([
    source('ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppMemberCouponController.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/service/impl/MallMemberCouponServiceImpl.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/mapper/shop/MallMemberCouponMapper.java'),
    source('ruoyi-system/src/main/resources/mapper/shop/MallMemberCouponMapper.xml'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/domain/shop/app/AppMemberCouponDto.java')
  ])

  assert.match(controller, /\/coupons\/\{couponId\}\/claim/)
  assert.match(controller, /AppMemberContext\.getMemberId\(\)/)
  assert.match(controller, /getMemberIdOrNull/)
  assert.match(couponService, /@Transactional/)
  assert.match(couponService, /increaseReceived/)
  assert.match(couponService, /setCouponNo\(IdUtils\.fastSimpleUUID\(\)\)/)
  assert.doesNotMatch(couponService, /setCouponNo\(IdUtils\.fastUUID\(\)\)/)
  assert.match(mapper, /selectAvailableCoupons/)
  assert.match(mapper, /countByCouponAndMember/)
  assert.match(xml, /received_quantity &lt; total_quantity/)
  assert.match(controller, /AppMemberCouponDto/)
  assert.doesNotMatch(dto, /memberId/)
  assert.doesNotMatch(dto, /lockedOrderId/)
})

test('mini program exposes coupon requests and a registered coupon list page', async () => {
  const [api, page, app] = await Promise.all([
    source('RuoYi-wx-app/api/shop.js'),
    source('RuoYi-wx-app/pages/coupon/list/index.js'),
    source('RuoYi-wx-app/app.json')
  ])
  assert.match(api, /listCoupons/)
  assert.match(api, /listMemberCoupons/)
  assert.match(api, /claimCoupon/)
  assert.match(page, /listCoupons/)
  assert.match(page, /claimCoupon/)
  assert.match(page, /listMemberCoupons/)
  assert.match(app, /pages\/coupon\/list\/index/)
})

test('public coupon received state is loaded with one batch query', async () => {
  const [controller, mapper, xml] = await Promise.all([
    source('ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppMemberCouponController.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/mapper/shop/MallMemberCouponMapper.java'),
    source('ruoyi-system/src/main/resources/mapper/shop/MallMemberCouponMapper.xml')
  ])

  assert.match(controller, /selectReceivedCouponIds/)
  assert.doesNotMatch(controller, /countByCouponAndMember/)
  assert.match(mapper, /selectReceivedCouponIds/)
  assert.match(xml, /id="selectReceivedCouponIds"[\s\S]*<foreach/)
})
