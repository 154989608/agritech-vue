import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

const root = new URL('../../', import.meta.url)
const source = (path) => readFile(new URL(path, root), 'utf8')

test('public app shop endpoints expose dedicated browse DTOs only', async () => {
  const [controller, service, mapper, xml] = await Promise.all([
    source('ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppShopController.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/service/impl/AppShopServiceImpl.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/mapper/shop/AppShopMapper.java'),
    source('ruoyi-system/src/main/resources/mapper/shop/AppShopMapper.xml')
  ])

  assert.match(controller, /@RequestMapping\("\/app\/shop"\)/)
  assert.match(controller, /@Anonymous[\s\S]*@GetMapping\("\/home"\)/)
  assert.match(controller, /@Anonymous[\s\S]*@GetMapping\("\/categories"\)/)
  assert.match(controller, /@Anonymous[\s\S]*@GetMapping\("\/products"\)/)
  assert.match(controller, /@Anonymous[\s\S]*@GetMapping\("\/products\/\{productId\}"\)/)
  assert.match(controller, /@Anonymous[\s\S]*@PostMapping\("\/cart\/preview"\)/)
  assert.match(service, /AppProductDto/)
  assert.doesNotMatch(controller, /MallProduct/)
  assert.match(mapper, /selectPublishedProducts/)
  assert.match(xml, /product\.status='1'/)
  assert.match(xml, /sku\.status='0'/)
  assert.match(xml, /MAX\(sku\.available_stock\)/)
})

test('mini program public browse path has no runtime mock dependency', async () => {
  const [shop, cart, request, home, category, detail, cartPage, confirm, search] = await Promise.all([
    source('RuoYi-wx-app/api/shop.js'),
    source('RuoYi-wx-app/utils/cart.js'),
    source('RuoYi-wx-app/utils/request.js'),
    source('RuoYi-wx-app/pages/home/index.js'),
    source('RuoYi-wx-app/pages/category/index.js'),
    source('RuoYi-wx-app/pages/product/detail/index.js'),
    source('RuoYi-wx-app/pages/cart/index.js'),
    source('RuoYi-wx-app/pages/order/confirm/index.js'),
    source('RuoYi-wx-app/pages/search/index.js')
  ])

  assert.match(shop, /getHome/)
  assert.match(shop, /previewCart/)
  assert.match(cart, /addOrMerge/)
  assert.match(cart, /getSelected/)
  assert.match(request, /responseData\.code !== 200/)
  assert.match(request, /loadingCount/)
  assert.match(home, /getHome/)
  assert.match(category, /getCategories/)
  assert.match(detail, /getProduct/)
  assert.match(cartPage, /previewCart/)
  assert.match(confirm, /previewOrder/)
  assert.match(search, /getProducts/)
  assert.match(search, /wx\.getStorageSync/)
  for (const page of [home, category, detail, cartPage, confirm, search]) assert.doesNotMatch(page, /utils\/mock/)
})

test('product detail HTML is sanitized before management writes it', async () => {
  const service = await source('ruoyi-system/src/main/java/com/ruoyi/system/service/impl/MallProductServiceImpl.java')
  assert.match(service, /EscapeUtil\.clean\(product\.getDetailHtml\(\)\)/)
})
