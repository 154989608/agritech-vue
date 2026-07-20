const { addresses, cartItems } = require('../../../utils/mock')

function calcSummary(items) {
  const selectedItems = items.filter((item) => item.selected !== false)
  const count = selectedItems.reduce((sum, item) => sum + item.count, 0)
  const total = selectedItems.reduce((sum, item) => sum + item.price * item.count, 0)

  return { count, total, selectedItems }
}

Page({
  data: {
    address: {},
    items: [],
    summary: {
      count: 0,
      total: 0
    }
  },
  onLoad() {
    const address = addresses.find((item) => item.isDefault) || addresses[0]
    const items = JSON.parse(JSON.stringify(cartItems))
    const summary = calcSummary(items)

    this.setData({
      address,
      items: summary.selectedItems,
      summary
    })
  },
  submitOrder() {
    wx.showToast({ title: '下单成功', icon: 'success' })
    wx.reLaunch({ url: '/pages/order/list/index' })
  }
})
