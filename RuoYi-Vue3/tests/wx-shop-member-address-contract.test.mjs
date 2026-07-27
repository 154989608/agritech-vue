import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

const root = new URL('../../', import.meta.url)
const source = (path) => readFile(new URL(path, root), 'utf8')

test('member address endpoints derive ownership from the member principal', async () => {
  const [controller, context, service, mapper, xml, dto] = await Promise.all([
    source('ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppMemberAddressController.java'),
    source('ruoyi-framework/src/main/java/com/ruoyi/framework/security/context/AppMemberContext.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/service/impl/AppMemberAddressServiceImpl.java'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/mapper/shop/MallMemberMapper.java'),
    source('ruoyi-system/src/main/resources/mapper/shop/MallMemberMapper.xml'),
    source('ruoyi-system/src/main/java/com/ruoyi/system/domain/shop/app/AppMemberAddressDto.java')
  ])

  assert.match(controller, /\/member\/addresses/)
  assert.match(controller, /AppMemberContext\.getMemberId\(\)/)
  assert.doesNotMatch(controller, /@RequestParam[^\n]*memberId/)
  assert.match(context, /AppMemberSession/)
  assert.match(service, /@Transactional/)
  assert.match(service, /setDefault/)
  assert.match(mapper, /updateAddressByMember/)
  assert.match(mapper, /clearDefaultAddress/)
  assert.match(xml, /member_id=#\{memberId\}/)
  assert.match(xml, /del_flag='2'/)
  assert.match(controller, /AppMemberAddressDto/)
  assert.doesNotMatch(dto, /memberId/)
})

test('mini program supports address CRUD and checkout selection without local mock data', async () => {
  const [api, page, edit, app] = await Promise.all([
    source('RuoYi-wx-app/api/shop.js'),
    source('RuoYi-wx-app/pages/address/list/index.js'),
    source('RuoYi-wx-app/pages/address/edit/index.js'),
    source('RuoYi-wx-app/app.json')
  ])

  assert.match(api, /listAddresses/)
  assert.match(api, /setDefaultAddress/)
  assert.match(api, /createAddress/)
  assert.match(api, /updateAddress/)
  assert.match(api, /deleteAddress/)
  assert.match(page, /listAddresses/)
  assert.match(page, /pages\/address\/edit\/index/)
  assert.doesNotMatch(page, /地址编辑页正在接入/)
  assert.match(edit, /wx\.chooseAddress/)
  assert.match(edit, /createAddress/)
  assert.match(app, /pages\/address\/edit\/index/)
  assert.doesNotMatch(page, /utils\/mock/)
})
