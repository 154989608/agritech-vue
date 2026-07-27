import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

const root = new URL('../../', import.meta.url)
const source = (path) => readFile(new URL(path, root), 'utf8')

test('app shop uses an isolated member session instead of the administrator JWT', async () => {
  const [controller, authService, memberFilter, jwtFilter, mapper, schema] = await Promise.all([
    source('ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppShopAuthController.java'),
    source('ruoyi-framework/src/main/java/com/ruoyi/framework/web/service/AppMemberAuthService.java'),
    source('ruoyi-framework/src/main/java/com/ruoyi/framework/security/filter/AppMemberAuthenticationFilter.java'),
    source('ruoyi-framework/src/main/java/com/ruoyi/framework/security/filter/JwtAuthenticationTokenFilter.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/mapper/shop/MallMemberMapper.java'),
    source('sql/update/2026-07-24-01-新增商城业务表.sql')
  ])

  assert.match(controller, /@Anonymous[\s\S]*@PostMapping\("\/auth\/login"\)/)
  assert.match(controller, /@RateLimiter/)
  assert.match(authService, /code2Session/)
  assert.match(authService, /RedisCache/)
  assert.match(authService, /IdUtils\.fastUUID/)
  assert.match(memberFilter, /\/app\/shop/)
  assert.match(memberFilter, /MEMBER_TOKEN_KEY/)
  assert.match(jwtFilter, /shouldNotFilter/)
  assert.match(jwtFilter, /\/app\/shop/)
  assert.match(mapper, /selectMemberByAppOpen/)
  assert.match(mapper, /insertAppMember/)
  assert.match(schema, /uk_mall_member_app_open/)
  assert.doesNotMatch(authService, /TokenService/)
})

test('mini program obtains its member token only from wx.login', async () => {
  const [api, page] = await Promise.all([
    source('RuoYi-wx-app/api/shop.js'),
    source('RuoYi-wx-app/pages/login/index.js')
  ])

  assert.match(api, /loginByWx/)
  assert.match(page, /wx\.login/)
  assert.match(page, /loginByWx/)
  assert.doesNotMatch(page, /demo-token/)
  assert.doesNotMatch(page, /password/)
})

test('profile keeps only implemented member services and uses native customer contact', async () => {
  const [controller, api, page, template] = await Promise.all([
    source('ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppMemberProfileController.java'),
    source('RuoYi-wx-app/api/shop.js'),
    source('RuoYi-wx-app/pages/profile/index.js'),
    source('RuoYi-wx-app/pages/profile/index.wxml')
  ])

  assert.match(controller, /@GetMapping\("\/me"\)/)
  assert.match(controller, /AppMemberContext\.getMemberId\(\)/)
  assert.match(api, /getMemberProfile/)
  assert.match(page, /getMemberProfile/)
  assert.doesNotMatch(template, /我的钱包|钱包余额/)
  assert.match(template, /open-type="contact"/)
  assert.doesNotMatch(page, /openCustomerServiceChat/)
  assert.doesNotMatch(page, /银行卡|分销|售后/)
})
