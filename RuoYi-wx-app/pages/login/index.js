const app = getApp()
const { loginByWx } = require('../../api/shop')

Page({
  data: { loading: false },
  handleLogin() {
    if (this.data.loading) return
    this.setData({ loading: true })
    new Promise((resolve, reject) => wx.login({ success: ({ code }) => code ? resolve(code) : reject({ msg: '微信登录凭证获取失败' }), fail: () => reject({ msg: '微信登录失败，请重试' }) })).then(loginByWx).then((member) => {
      wx.setStorageSync('token', member.token); wx.setStorageSync('userInfo', member)
      app.globalData.token = member.token; app.globalData.userInfo = member
      wx.showToast({ title: '登录成功', icon: 'success' }); setTimeout(() => wx.navigateBack(), 300)
    }).catch((error) => wx.showToast({ title: error.msg || '登录失败，请重试', icon: 'none' })).finally(() => this.setData({ loading: false }))
  }
})
