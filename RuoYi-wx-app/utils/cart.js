const CART_KEY = 'mall-cart'

function normalize(item) {
  const skuId = Number(item && item.skuId)
  const quantity = Number(item && item.quantity)
  if (!Number.isSafeInteger(skuId) || skuId < 1 || !Number.isSafeInteger(quantity) || quantity < 1) return null
  return { skuId, quantity: Math.min(quantity, 99), selected: item.selected !== false }
}

function getAll() {
  const stored = wx.getStorageSync(CART_KEY)
  return Array.isArray(stored) ? stored.map(normalize).filter(Boolean) : []
}

function save(items) {
  const normalized = items.map(normalize).filter(Boolean)
  wx.setStorageSync(CART_KEY, normalized)
  const app = getApp()
  app.globalData.cartCount = normalized.reduce((total, item) => total + item.quantity, 0)
  return normalized
}

function addOrMerge(skuId, quantity) {
  const items = getAll()
  const index = items.findIndex((item) => item.skuId === Number(skuId))
  if (index >= 0) items[index].quantity = Math.min(99, items[index].quantity + Number(quantity))
  else items.push({ skuId: Number(skuId), quantity: Number(quantity), selected: true })
  return save(items)
}

function update(skuId, changes) { return save(getAll().map((item) => item.skuId === Number(skuId) ? { ...item, ...changes } : item)) }
function remove(skuId) { return save(getAll().filter((item) => item.skuId !== Number(skuId))) }
function getSelected() { return getAll().filter((item) => item.selected) }

module.exports = { getAll, save, addOrMerge, update, remove, getSelected }
