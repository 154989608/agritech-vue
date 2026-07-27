const { previewCart } = require('../../api/shop')
const cart = require('../../utils/cart')
const money = (cent) => (Number(cent || 0) / 100).toFixed(2)

Page({
  data: {
    cartItems: [],
    summary: {
      count: 0,
      total: 0,
      allSelected: false
    },
    empty: false, loading: false
  },
  onLoad() {
    this.refreshCart()
  },
  onShow() { this.refreshCart() },
  refreshCart() {
    const items = cart.getAll()
    if (!items.length) return this.syncCart([])
    this.setData({ loading: true })
    return previewCart(items).then((preview) => this.syncCart((preview.items || []).map((item) => ({ ...item, selected: items.find((saved) => saved.skuId === item.skuId)?.selected !== false, priceText: money(item.priceCent), available: item.available !== false })))).catch((error) => wx.showToast({ title: error.msg || '购物车刷新失败', icon: 'none' })).finally(() => this.setData({ loading: false }))
  },
  syncCart(items) {
    const selectedItems = items.filter((item) => item.selected && item.available)
    const summary = { count: selectedItems.reduce((total, item) => total + item.quantity, 0), totalText: money(selectedItems.reduce((total, item) => total + Number(item.lineAmountCent || 0), 0)), allSelected: items.length > 0 && items.every((item) => item.selected) }

    this.setData({
      cartItems: items,
      summary,
      empty: items.length === 0
    })

    cart.save(items.map((item) => ({ skuId: item.skuId, quantity: item.quantity, selected: item.selected })))
  },
  toggleItem(e) {
    const skuId = Number(e.currentTarget.dataset.id)
    const cartItems = this.data.cartItems.map((item) => {
      if (item.skuId === skuId) {
        return { ...item, selected: !item.selected }
      }
      return item
    })

    this.syncCart(cartItems)
  },
  toggleAll() {
    const { allSelected } = this.data.summary
    const cartItems = this.data.cartItems.map((item) => ({
      ...item,
      selected: !allSelected
    }))

    this.syncCart(cartItems)
  },
  changeCount(e) {
    const skuId = Number(e.currentTarget.dataset.id), delta = Number(e.currentTarget.dataset.delta)
    const cartItems = this.data.cartItems.map((item) => {
      if (item.skuId !== skuId) {
        return item
      }

      return { ...item, quantity: Math.max(1, Math.min(999, item.quantity + delta)) }
    })

    this.syncCart(cartItems)
  },
  goCheckout() {
    const selected = cart.getSelected()
    if (!selected.length) return wx.showToast({ title: '请选择商品', icon: 'none' })
    wx.setStorageSync('mall-checkout-items', selected)
    wx.navigateTo({ url: '/pages/order/confirm/index' })
  },
  goHome() {
    wx.reLaunch({ url: '/pages/home/index' })
  }
})
