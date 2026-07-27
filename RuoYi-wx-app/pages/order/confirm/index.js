const { previewOrder, createOrder, listAddresses, listMemberCoupons } = require('../../../api/shop')
const cart = require('../../../utils/cart')
const money = (cent) => (Number(cent || 0) / 100).toFixed(2)

Page({
  data: {
    address: {}, items: [], preview: null, coupons: [], memberCouponId: null, loading: false, clientRequestNo: ''
  },
  onLoad() {
    const items = wx.getStorageSync('mall-checkout-items') || cart.getSelected()
    if (!items.length) return wx.navigateBack()
    this.setData({ items, clientRequestNo: wx.getStorageSync('mall-order-request-no') || `${Date.now()}${Math.random().toString(16).slice(2)}` })
    wx.setStorageSync('mall-order-request-no', this.data.clientRequestNo)
    this.loadAddress()
    this.loadCoupons()
  },
  onShow() {
    const address = wx.getStorageSync('mall-checkout-address')
    if (address && address.addressId && address.addressId !== this.data.address.addressId) {
      wx.removeStorageSync('mall-checkout-address')
      this.setData({ address })
      this.loadPreview()
    }
  },
  loadAddress() {
    this.setData({ loading: true })
    return listAddresses().then((addresses) => {
      const address = addresses.find((item) => item.isDefault === '1') || addresses[0]
      this.setData({ address: address || {} })
      return address ? this.loadPreview() : null
    }).catch((error) => {
      if (error.code === 401) wx.navigateTo({ url: '/pages/login/index' })
      else wx.showToast({ title: error.msg || '地址加载失败', icon: 'none' })
    }).finally(() => this.setData({ loading: false }))
  },
  chooseAddress() {
    wx.navigateTo({ url: '/pages/address/list/index?select=1' })
  },
  loadCoupons() {
    return listMemberCoupons('AVAILABLE').then((coupons) => this.setData({ coupons: (coupons || []).map((coupon) => ({ ...coupon, thresholdText: money(coupon.thresholdAmountSnapshot), discountText: money(coupon.discountAmountSnapshot) })) })).catch((error) => { if (error.code !== 401) wx.showToast({ title: error.msg || '优惠券加载失败', icon: 'none' }) })
  },
  selectCoupon(e) {
    const memberCouponId = Number(e.currentTarget.dataset.id) || null
    this.setData({ memberCouponId: this.data.memberCouponId === memberCouponId ? null : memberCouponId })
    this.loadPreview()
  },
  loadPreview() {
    if (!this.data.address.addressId) return Promise.resolve()
    this.setData({ loading: true })
    return previewOrder({ addressId: this.data.address.addressId, memberCouponId: this.data.memberCouponId || undefined, items: this.data.items }).then((preview) => this.setData({ preview: { ...preview, productAmountText: money(preview.productAmountCent), freightAmountText: money(preview.freightAmountCent), discountAmountText: money(preview.discountAmountCent), payableAmountText: money(preview.payableAmountCent), items: (preview.items || []).map((item) => ({ ...item, lineAmountText: money(item.lineAmountCent) })) } })).catch((error) => {
      if (error.code === 401) wx.navigateTo({ url: '/pages/login/index' })
      else wx.showToast({ title: error.msg || '预结算失败', icon: 'none' })
    }).finally(() => this.setData({ loading: false }))
  },
  submitOrder() {
    if (!this.data.address.addressId) return this.chooseAddress()
    if (!this.data.preview || this.data.preview.unavailableReason) return wx.showToast({ title: this.data.preview && this.data.preview.unavailableReason || '商品暂不可结算', icon: 'none' })
    if (this.data.loading) return
    this.setData({ loading: true })
    createOrder({ clientRequestNo: this.data.clientRequestNo, addressId: this.data.address.addressId, memberCouponId: this.data.memberCouponId || undefined, items: this.data.items }).then(() => { this.data.items.forEach((item) => cart.remove(item.skuId)); wx.removeStorageSync('mall-order-request-no'); wx.showToast({ title: '订单已创建，请完成支付', icon: 'success' }); wx.reLaunch({ url: '/pages/order/list/index' }) }).catch((error) => wx.showToast({ title: error.msg || '订单创建失败', icon: 'none' })).finally(() => this.setData({ loading: false }))
  }
})
