const app = getApp()

const orderItems = [
  { title: '待支付', short: '付' },
  { title: '待发货', short: '发' },
  { title: '待收货', short: '收' },
  { title: '已完成', short: '完' }
]

const serviceItems = [
  { title: '收货地址', short: '址', action: 'address', color: '#dcfce7' },
  { title: '我的优惠券', short: '券', action: 'coupon', color: '#ffe4e6' },
  { title: '分销中心', short: '分', action: 'center', color: '#fef3c7' },
  { title: '我的银行卡', short: '卡', action: 'bank', color: '#fef3c7' },
  { title: '售后记录', short: '售', action: 'after', color: '#dbeafe' },
  { title: '平台介绍', short: '介', action: 'about', color: '#e9d5ff' },
  { title: '消息通知', short: '讯', action: 'notice', color: '#fee2e2' },
  { title: '商家聊天', short: '聊', action: 'chat', color: '#dcfce7' },
  { title: '联系客服', short: '服', action: 'service', color: '#d1fae5' }
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
  data: { userInfo: buildUser(), orderItems, serviceItems },
  onShow() {
    this.setData({ userInfo: buildUser() })
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

    const tips = {
      coupon: '优惠券功能待接入',
      center: '分销中心待接入',
      bank: '银行卡功能待接入',
      after: '售后记录待接入',
      about: '平台介绍待接入',
      notice: '消息通知待接入',
      chat: '商家聊天待接入',
      service: '联系客服待接入'
    }
    wx.showToast({ title: tips[action] || '功能待接入', icon: 'none' })
  }
})
