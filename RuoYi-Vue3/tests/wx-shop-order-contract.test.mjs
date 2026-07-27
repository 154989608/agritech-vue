import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

const root = new URL('../../', import.meta.url)
const source = (path) => readFile(new URL(path, root), 'utf8')

test('member order API previews and creates orders without accepting a client member id or returning persistence entities', async () => {
  const [controller, service, appService, request, mapper, xml, dto, configSql] = await Promise.all([
    source('ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppMemberOrderController.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/service/impl/MallOrderServiceImpl.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/service/impl/AppMemberOrderServiceImpl.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/domain/shop/app/AppMemberOrderRequest.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/mapper/shop/MallOrderMapper.java'),
    source('ruoyi-system/src/main/resources/mapper/shop/MallOrderMapper.xml'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/domain/shop/app/AppMemberOrderDto.java'),
    source('sql/update/2026-07-27-新增小程序商城配置.sql')
  ])

  assert.match(controller, /\/orders\/preview/)
  assert.match(controller, /AppMemberContext\.getMemberId\(\)/)
  assert.match(controller, /@PostMapping\("\/orders"\)/)
  assert.match(appService, /preview\(/)
  assert.match(service, /createOrder\(/)
  assert.match(service, /MallOrderCreateRequest/)
  assert.match(appService, /AppMemberOrderDto/)
  assert.match(service, /calculateFreightAmount/)
  assert.match(appService, /calculateFreightAmount/)
  assert.match(appService, /selectAvailableById/)
  assert.match(configSql, /mall\.freight\.amount/)
  assert.match(configSql, /mall\.freight\.freeThreshold/)
  assert.doesNotMatch(request, /memberId/)
  assert.match(controller, /result\.setRows\(orderService\.toDtos\(orders\)\)/)
  assert.match(dto, /availableActions/)
  assert.doesNotMatch(dto, /memberId/)
  assert.doesNotMatch(dto, /clientRequestNo/)
  assert.doesNotMatch(dto, /adminRemark/)
  assert.match(mapper, /selectOrderByMemberNo/)
  assert.match(xml, /member_id=#\{memberId\}/)
})

test('mini program order pages use real member order APIs and preserve the selected address', async () => {
  const [api, confirm, list, listTemplate, detail, app] = await Promise.all([
    source('RuoYi-wx-app/api/shop.js'),
    source('RuoYi-wx-app/pages/order/confirm/index.js'),
    source('RuoYi-wx-app/pages/order/list/index.js'),
    source('RuoYi-wx-app/pages/order/list/index.wxml'),
    source('RuoYi-wx-app/pages/order/detail/index.js'),
    source('RuoYi-wx-app/app.json')
  ])
  assert.match(api, /createOrder/)
  assert.match(api, /listOrders/)
  assert.match(api, /getOrder/)
  assert.match(api, /cancelOrder/)
  assert.match(api, /confirmReceipt/)
  assert.match(confirm, /createOrder/)
  assert.match(confirm, /listAddresses/)
  assert.match(confirm, /listMemberCoupons/)
  assert.match(confirm, /memberCouponId/)
  assert.match(confirm, /addressId/)
  assert.doesNotMatch(confirm, /订单创建将在会员订单链路接入后开放/)
  assert.match(list, /listOrders/)
  assert.match(list, /getOrder/)
  assert.match(listTemplate, /orderNo/)
  assert.doesNotMatch(listTemplate, /item\.sn/)
  assert.match(detail, /getOrder/)
  assert.match(detail, /cancelOrder/)
  assert.match(detail, /availableActions/)
  assert.match(app, /pages\/order\/detail\/index/)
})
