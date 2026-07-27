const { listCoupons, listMemberCoupons, claimCoupon } = require('../../../api/shop')
const money = (cent) => (Number(cent || 0) / 100).toFixed(2)

Page({
  data: { coupons: [], loading: false, claimingId: null, tab: 'available' },
  onShow() { this.loadCoupons() },
  switchTab(e) { this.setData({ tab: e.currentTarget.dataset.tab }); this.loadCoupons() },
  loadCoupons() { this.setData({ loading: true }); const request = this.data.tab === 'available' ? listCoupons() : listMemberCoupons(); return request.then((coupons) => this.setData({ coupons: (coupons || []).map((coupon) => ({ ...coupon, thresholdText: money(coupon.thresholdAmount || coupon.thresholdAmountSnapshot), discountText: money(coupon.discountAmount || coupon.discountAmountSnapshot) })) })).catch((error) => { if (error.code === 401) wx.navigateTo({ url: '/pages/login/index' }); else wx.showToast({ title: error.msg || '优惠券加载失败', icon: 'none' }) }).finally(() => this.setData({ loading: false })) },
  claim(e) { const couponId = Number(e.currentTarget.dataset.id); const coupon = this.data.coupons.find((item) => item.couponId === couponId); if (coupon && coupon.received) return; if (this.data.claimingId) return; this.setData({ claimingId: couponId }); claimCoupon(couponId).then(() => { wx.showToast({ title: '领取成功', icon: 'success' }); this.loadCoupons() }).catch((error) => { if (error.code === 401) wx.navigateTo({ url: '/pages/login/index' }); else wx.showToast({ title: error.msg || '领取失败', icon: 'none' }) }).finally(() => this.setData({ claimingId: null })) }
})
