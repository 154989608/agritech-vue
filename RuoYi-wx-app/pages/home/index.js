const { getHome } = require('../../api/shop')
const money = (cent) => (Number(cent || 0) / 100).toFixed(2)
const withPrice = (products) => (products || []).map((product) => ({ ...product, priceText: money(product.priceCent) }))

Page({
  data: {
    greeting: '早上好',
    banners: [], categories: [], latestProducts: [], hotProducts: [], loading: true, loadFailed: false
  },
  onLoad() {
    const hour = new Date().getHours()
    const greeting = hour < 12 ? '早上好' : hour < 18 ? '下午好' : '晚上好'
    this.setData({ greeting })
    this.loadHome()
  },
  loadHome() {
    this.setData({ loading: true, loadFailed: false })
    return getHome().then((home) => this.setData({
      banners: home.banners || [], categories: home.categories || [],
      latestProducts: withPrice(home.latestProducts), hotProducts: withPrice(home.hotProducts)
    })).catch((error) => { this.setData({ loadFailed: true }); wx.showToast({ title: error.msg || '加载失败', icon: 'none' }) }).finally(() => this.setData({ loading: false }))
  },
  goSearch() {
    wx.navigateTo({ url: '/pages/search/index' })
  },
  goCategory(e) {
    const { id } = e.currentTarget.dataset
    wx.reLaunch({ url: `/pages/category/index?category=${id}` })
  },
  goDetail(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({ url: `/pages/product/detail/index?id=${id}` })
  },
  goPage(e) {
    const { path } = e.currentTarget.dataset
    if (path) wx.navigateTo({ url: path })
  },
  onPullDownRefresh() {
    this.loadHome().finally(() => wx.stopPullDownRefresh())
  }
})
