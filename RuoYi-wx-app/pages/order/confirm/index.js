const { previewOrder } = require('../../../api/shop')
const cart = require('../../../utils/cart')
const money = (cent) => (Number(cent || 0) / 100).toFixed(2)

Page({
  data: {
    address: {}, items: [], preview: null, loading: false
  },
  onLoad() {
    const items = wx.getStorageSync('mall-checkout-items') || cart.getSelected()
    if (!items.length) return wx.navigateBack()
    this.setData({ items })
    this.loadPreview()
  },
  loadPreview() {
    this.setData({ loading: true })
    return previewOrder({ items: this.data.items }).then((preview) => this.setData({ preview: { ...preview, payableAmountText: money(preview.payableAmountCent), items: (preview.items || []).map((item) => ({ ...item, lineAmountText: money(item.lineAmountCent) })) } })).catch((error) => {
      if (error.code === 401) wx.navigateTo({ url: '/pages/login/index' })
      else wx.showToast({ title: error.msg || '预结算失败', icon: 'none' })
    }).finally(() => this.setData({ loading: false }))
  },
  submitOrder() {
    wx.showToast({ title: '订单创建将在会员订单链路接入后开放', icon: 'none' })
  }
})
