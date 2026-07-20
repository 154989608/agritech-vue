const app = getApp()

Page({
  data: { username: '', password: '' },
  onUsernameInput(e) {
    this.setData({ username: e.detail.value })
  },
  onPasswordInput(e) {
    this.setData({ password: e.detail.value })
  },
  handleLogin() {
    const { username, password } = this.data
    if (!username || !password) {
      wx.showToast({ title: '请填写账号和密码', icon: 'none' })
      return
    }

    wx.showLoading({ title: '登录中...' })
    setTimeout(() => {
      const userInfo = { nickname: username, role: '会员用户', mobile: '138****0000' }
      const token = `demo-token-${Date.now()}`
      wx.setStorageSync('token', token)
      wx.setStorageSync('userInfo', userInfo)
      app.globalData.token = token
      app.globalData.userInfo = userInfo
      wx.hideLoading()
      wx.showToast({ title: '登录成功', icon: 'success' })
      setTimeout(() => wx.reLaunch({ url: '/pages/profile/index' }), 300)
    }, 700)
  }
})
