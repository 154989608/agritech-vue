const app = getApp()
const { findProductById, products } = require('../../../utils/mock')

Page({
  data: {
    product: {
      bg: 'linear-gradient(135deg, #0f172a 0%, #334155 100%)',
      tag: '',
      title: '',
      desc: '',
      originPrice: 0,
      price: 0,
      sales: 0,
      highlights: []
    },
    relatedProducts: [],
    specs: ['官方正品', '7 天无理由', '48 小时发货']
  },
  onLoad(options) {
    const product = findProductById(options.id)
    const relatedProducts = products.filter((item) => item.categoryId === product.categoryId && item.id !== product.id).slice(0, 4)

    this.setData({
      product,
      relatedProducts
    })
  },
  addCart() {
    const nextCount = (wx.getStorageSync('cartCount') || 0) + 1
    wx.setStorageSync('cartCount', nextCount)
    app.globalData.cartCount = nextCount
    wx.showToast({ title: '已加入购物车', icon: 'success' })
  },
  buyNow() {
    wx.navigateTo({ url: '/pages/order/confirm/index' })
  },
  goCart() {
    wx.reLaunch({ url: '/pages/cart/index' })
  },
  goDetail(e) {
    const { id } = e.currentTarget.dataset
    wx.redirectTo({ url: `/pages/product/detail/index?id=${id}` })
  }
})
