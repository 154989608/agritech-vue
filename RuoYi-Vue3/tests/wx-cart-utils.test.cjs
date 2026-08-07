const assert = require('node:assert/strict')
const test = require('node:test')

const storage = new Map()
global.wx = {
  getStorageSync: (key) => storage.get(key),
  setStorageSync: (key, value) => storage.set(key, value)
}
global.getApp = () => ({ globalData: {} })

const cart = require('../../RuoYi-wx-app/utils/cart.js')

test('cart quantity is capped at 99 through every storage path', () => {
  storage.clear()
  cart.addOrMerge(1, 99)
  cart.addOrMerge(1, 1)
  assert.equal(cart.getAll()[0].quantity, 99)

  wx.setStorageSync('mall-cart', [{ skuId: 2, quantity: 999 }])
  assert.equal(cart.getAll()[0].quantity, 99)
})
