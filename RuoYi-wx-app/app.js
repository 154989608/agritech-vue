App({
  globalData: {
    appName: '黔山优选商城',
    apiBaseUrl: '',
    userInfo: null,
    token: '',
    cartCount: 0
  },
  onLaunch() {
    const { getApiBaseUrl } = require('./utils/config')
    const token = wx.getStorageSync('token')
    const userInfo = wx.getStorageSync('userInfo')
    const cart = wx.getStorageSync('mall-cart') || []

    this.globalData.apiBaseUrl = getApiBaseUrl()

    if (token) {
      this.globalData.token = token
    }

    if (userInfo) {
      this.globalData.userInfo = userInfo
    }

    this.globalData.cartCount = Array.isArray(cart) ? cart.reduce((total, item) => total + Number(item.quantity || 0), 0) : 0
  }
})
