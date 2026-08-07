import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

const root = new URL('../../', import.meta.url)
const source = (path) => readFile(new URL(path, root), 'utf8')

test('storefront shows loading and retry states instead of a blank home page', async () => {
  const [page, script] = await Promise.all([
    source('RuoYi-wx-app/pages/home/index.wxml'),
    source('RuoYi-wx-app/pages/home/index.js')
  ])

  assert.match(page, /wx:if="\{\{loading\}\}" class="home-skeleton"/)
  assert.match(page, /bindtap="loadHome">重新加载/)
  assert.match(script, /loadFailed: false/)
  assert.match(script, /loadFailed: true/)
})

test('checkout accurately describes the pending-payment state', async () => {
  const page = await source('RuoYi-wx-app/pages/order/confirm/index.wxml')

  assert.match(page, /支付暂未开放/)
  assert.match(page, /创建待支付订单/)
})

test('storefront base styles use restrained surfaces instead of decorative gradients', async () => {
  const style = await source('RuoYi-wx-app/app.wxss')

  assert.doesNotMatch(style, /radial-gradient/)
  assert.match(style, /border-radius: 16rpx/)
})

test('product quantity stays within the selected SKU stock', async () => {
  const [page, script] = await Promise.all([
    source('RuoYi-wx-app/pages/product/detail/index.wxml'),
    source('RuoYi-wx-app/pages/product/detail/index.js')
  ])

  assert.match(page, /库存 {{selectedSku\.availableStock}} 件/)
  assert.match(page, /stepper__btn--disabled/)
  assert.match(script, /selectedSku: null/)
  assert.match(script, /Math\.min\(this\.data\.quantity \+ Number\(e\.currentTarget\.dataset\.delta\), sku\.availableStock\)/)
})

test('cart keeps checkout visible and quantity within previewed stock', async () => {
  const [page, script, style] = await Promise.all([
    source('RuoYi-wx-app/pages/cart/index.wxml'),
    source('RuoYi-wx-app/pages/cart/index.js'),
    source('RuoYi-wx-app/pages/cart/index.wxss')
  ])

  assert.match(page, /class="page page--tab cart-page"/)
  assert.match(page, /class="cart-settlement"/)
  assert.match(page, /disabled="\{\{summary\.count === 0\}\}"/)
  assert.match(script, /Math\.min\(Number\(item\.availableStock \|\| 1\), 99, item\.quantity \+ delta\)/)
  assert.match(style, /\.cart-settlement\s*\{[\s\S]*position: fixed/)
  assert.match(style, /\.cart-page\s*\{[\s\S]*padding-bottom: 320rpx/)
})

test('order detail turns existing status into a clear fulfillment progress', async () => {
  const [page, script, style] = await Promise.all([
    source('RuoYi-wx-app/pages/order/detail/index.wxml'),
    source('RuoYi-wx-app/pages/order/detail/index.js'),
    source('RuoYi-wx-app/pages/order/detail/index.wxss')
  ])

  assert.match(page, /class="order-progress"/)
  assert.match(page, /wx:for="\{\{order\.stages\}\}"/)
  assert.match(page, /订单已取消，库存已释放/)
  assert.match(script, /statusStages\(order\.orderStatus\)/)
  assert.match(style, /\.order-progress\s*\{/)
})

test('search history can be cleared from the existing local history section', async () => {
  const [page, script] = await Promise.all([
    source('RuoYi-wx-app/pages/search/index.wxml'),
    source('RuoYi-wx-app/pages/search/index.js')
  ])

  assert.match(page, /bindtap="clearHistory">清空/)
  assert.match(script, /clearHistory\(\)\s*\{[\s\S]*wx\.removeStorageSync\(HISTORY_KEY\)[\s\S]*history: \[\]/)
})

test('profile only exposes logout to a signed-in member', async () => {
  const page = await source('RuoYi-wx-app/pages/profile/index.wxml')

  assert.match(page, /wx:if="\{\{userInfo\.loggedIn\}\}"[\s\S]*data-action="logout"/)
})

test('category page provides an in-page retry when categories cannot load', async () => {
  const [page, script] = await Promise.all([
    source('RuoYi-wx-app/pages/category/index.wxml'),
    source('RuoYi-wx-app/pages/category/index.js')
  ])

  assert.match(page, /bindtap="loadCategories">重新加载/)
  assert.match(script, /categoryLoadFailed: false/)
  assert.match(script, /loadCategories\(\)\s*\{[\s\S]*getCategories\(\)[\s\S]*categoryLoadFailed: true/)
})

test('empty address management remains actionable', async () => {
  const page = await source('RuoYi-wx-app/pages/address/list/index.wxml')

  assert.match(page, /wx:if="\{\{!loading && !addressList\.length\}\}" class="empty"/)
  assert.match(page, /暂无收货地址[\s\S]*bindtap="addAddress">新增收货地址/)
})
