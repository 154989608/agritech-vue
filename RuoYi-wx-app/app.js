App({
  globalData: {
    appName: 'RuoYi Shop',
    apiBaseUrl: 'https://example.com/api',
    userInfo: null,
    token: '',
    cartCount: 0
  },
  onLaunch() {
    const token = wx.getStorageSync('token')
    const userInfo = wx.getStorageSync('userInfo')
    const cartCount = wx.getStorageSync('cartCount') || 0

    if (token) {
      this.globalData.token = token
    }

    if (userInfo) {
      this.globalData.userInfo = userInfo
    }

    this.globalData.cartCount = cartCount
  }
})
