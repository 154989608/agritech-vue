const app = getApp()
const { getMemberProfile } = require('../../api/shop')

const orderItems = [
  { title: '待支付', short: '付' },
  { title: '待发货', short: '发' },
  { title: '待收货', short: '收' },
  { title: '已完成', short: '完' }
]

const serviceItems = [
  { title: '收货地址', short: '址', action: 'address', color: '#dcfce7' },
  { title: '我的优惠券', short: '券', action: 'coupon', color: '#ffe4e6' }
]

function buildUser() {
  const userInfo = wx.getStorageSync('userInfo')
  if (userInfo) {
    return {
      nickname: userInfo.nickname || '农产品用户',
      role: userInfo.role || '普通会员',
      mobile: userInfo.mobile || '138****0000',
      loggedIn: true
    }
  }

  return {
    nickname: '游客',
    role: '未登录',
    mobile: '登录后可同步订单和地址',
    loggedIn: false
  }
}

Page({
  data: { userInfo: buildUser(), orderItems, serviceItems, availableCouponCount: 0 },
  onShow() {
    const userInfo = buildUser()
    this.setData({ userInfo })
    if (!userInfo.loggedIn) return
    getMemberProfile().then((profile) => this.setData({ userInfo: { ...userInfo, nickname: profile.nickname || userInfo.nickname }, orderItems: orderItems.map((item, index) => ({ ...item, count: [profile.pendingPaymentCount, profile.pendingShipmentCount, profile.shippedCount, profile.completedCount][index] || 0 })), availableCouponCount: profile.availableCouponCount || 0 })).catch((error) => { if (error.code === 401) this.setData({ userInfo: buildUser() }) })
  },
  goLogin() {
    wx.navigateTo({ url: '/pages/login/index' })
  },
  goOrders() {
    wx.navigateTo({ url: '/pages/order/list/index' })
  },
  handleServiceTap(e) {
    const { action } = e.currentTarget.dataset
    if (action === 'address') {
      wx.navigateTo({ url: '/pages/address/list/index' })
      return
    }
    if (action === 'coupon') { wx.navigateTo({ url: '/pages/coupon/list/index' }); return }
    if (action === 'logout') {
      wx.removeStorageSync('token')
      wx.removeStorageSync('userInfo')
      wx.removeStorageSync('cartCount')
      app.globalData.token = ''
      app.globalData.userInfo = null
      app.globalData.cartCount = 0
      this.setData({ userInfo: buildUser() })
      wx.showToast({ title: '已退出登录', icon: 'none' })
      return
    }

    wx.showToast({ title: '当前操作不可用', icon: 'none' })
  }
})
