const app = getApp()
const { cartItems } = require('../../utils/mock')

function cloneItems(items) {
  return JSON.parse(JSON.stringify(items))
}

function calcSummary(items) {
  const selectedItems = items.filter((item) => item.selected)
  const count = selectedItems.reduce((sum, item) => sum + item.count, 0)
  const total = selectedItems.reduce((sum, item) => sum + item.price * item.count, 0)

  return {
    count,
    total,
    allSelected: items.length > 0 && selectedItems.length === items.length
  }
}

Page({
  data: {
    cartItems: [],
    summary: {
      count: 0,
      total: 0,
      allSelected: false
    },
    empty: false
  },
  onLoad() {
    this.syncCart(cloneItems(cartItems))
  },
  syncCart(items) {
    const summary = calcSummary(items)

    this.setData({
      cartItems: items,
      summary,
      empty: items.length === 0
    })

    app.globalData.cartCount = summary.count
    wx.setStorageSync('cartCount', summary.count)
  },
  toggleItem(e) {
    const { id } = e.currentTarget.dataset
    const cartItems = this.data.cartItems.map((item) => {
      if (item.id === id) {
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
    const { id, type } = e.currentTarget.dataset
    const cartItems = this.data.cartItems.map((item) => {
      if (item.id !== id) {
        return item
      }

      const nextCount = type === 'minus' ? Math.max(1, item.count - 1) : item.count + 1
      return { ...item, count: nextCount }
    })

    this.syncCart(cartItems)
  },
  goCheckout() {
    wx.navigateTo({ url: '/pages/order/confirm/index' })
  },
  goHome() {
    wx.reLaunch({ url: '/pages/home/index' })
  }
})
