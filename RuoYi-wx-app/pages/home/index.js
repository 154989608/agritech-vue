const { homePromos, homeCategories, featuredStore, featuredBanners } = require('../../utils/mock')

Page({
  data: {
    greeting: '早上好',
    homePromos: [],
    homeCategories: [],
    featuredStore: { name: '', sold: '', minOrder: '', badge: '', tip: '', products: [] },
    featuredBanners: []
  },
  onLoad() {
    const hour = new Date().getHours()
    const greeting = hour < 12 ? '早上好' : hour < 18 ? '下午好' : '晚上好'
    this.setData({ greeting, homePromos, homeCategories, featuredStore, featuredBanners })
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
    setTimeout(() => wx.stopPullDownRefresh(), 600)
  }
})
