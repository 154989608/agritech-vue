const { getOrder, cancelOrder, confirmReceipt } = require('../../../api/shop')

const money = (cent) => (Number(cent || 0) / 100).toFixed(2)
const statusText = { PENDING_PAYMENT: '待支付', PENDING_SHIPMENT: '待发货', SHIPPED: '待收货', COMPLETED: '已完成', CANCELED: '已取消' }

Page({
  data: { orderNo: '', order: null, loading: false },
  onLoad(options) { this.setData({ orderNo: options.orderNo || '' }); this.loadOrder() },
  onShow() { if (this.data.orderNo) this.loadOrder() },
  loadOrder() {
    if (!this.data.orderNo) return wx.navigateBack()
    this.setData({ loading: true })
    return getOrder(this.data.orderNo).then((order) => { const availableActions = order.availableActions || []; this.setData({ order: { ...order, canCancel: availableActions.includes('CANCEL'), canConfirmReceipt: availableActions.includes('CONFIRM_RECEIPT'), statusText: statusText[order.orderStatus] || order.orderStatus, productAmountText: money(order.productAmount), freightAmountText: money(order.freightAmount), discountAmountText: money(order.discountAmount), payableAmountText: money(order.payableAmount), items: (order.items || []).map((item) => ({ ...item, payableAmountText: money(item.payableAmount) })) } }) }).catch((error) => wx.showToast({ title: error.msg || '订单读取失败', icon: 'none' })).finally(() => this.setData({ loading: false }))
  },
  cancel() { wx.showModal({ title: '取消订单', content: '取消后库存将释放，是否继续？', success: ({ confirm }) => confirm && cancelOrder(this.data.orderNo, '会员取消订单').then(() => this.loadOrder()).catch((error) => wx.showToast({ title: error.msg || '订单取消失败', icon: 'none' })) }) },
  confirmReceipt() { wx.showModal({ title: '确认收货', content: '确认已收到商品？', success: ({ confirm }) => confirm && confirmReceipt(this.data.orderNo).then(() => this.loadOrder()).catch((error) => wx.showToast({ title: error.msg || '确认收货失败', icon: 'none' })) }) }
})
