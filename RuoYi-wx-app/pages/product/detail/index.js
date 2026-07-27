const { getProduct } = require('../../../api/shop')
const cart = require('../../../utils/cart')

const money = (cent) => (Number(cent || 0) / 100).toFixed(2)

Page({
  data: {
    product: {}, images: [], params: [], selectedSkuId: null, quantity: 1, loading: true
  },
  onLoad(options) {
    this.loadProduct(options.id)
  },
  loadProduct(productId) {
    return getProduct(productId).then((product) => {
      const images = parseArray(product.imagesJson, product.mainImage)
      const params = parseArray(product.productParamsJson, [])
      this.setData({ product: { ...product, priceText: money(product.priceCent), marketPriceText: money(product.marketPriceCent) }, images, params, selectedSkuId: product.skus && product.skus.find((sku) => !sku.soldOut)?.skuId || null })
    }).catch((error) => wx.showToast({ title: error.msg || '商品加载失败', icon: 'none' })).finally(() => this.setData({ loading: false }))
  },
  selectSku(e) { this.setData({ selectedSkuId: Number(e.currentTarget.dataset.id) }) },
  changeQuantity(e) { this.setData({ quantity: Math.max(1, this.data.quantity + Number(e.currentTarget.dataset.delta)) }) },
  selectedSku() { return (this.data.product.skus || []).find((sku) => sku.skuId === this.data.selectedSkuId) },
  addCart() {
    const sku = this.selectedSku()
    if (!sku || sku.soldOut) return wx.showToast({ title: '请选择可售规格', icon: 'none' })
    cart.addOrMerge(sku.skuId, this.data.quantity)
    wx.showToast({ title: '已加入购物车', icon: 'success' })
  },
  buyNow() {
    const sku = this.selectedSku()
    if (!sku || sku.soldOut) return wx.showToast({ title: '请选择可售规格', icon: 'none' })
    wx.setStorageSync('mall-checkout-items', [{ skuId: sku.skuId, quantity: this.data.quantity, selected: true }])
    wx.navigateTo({ url: '/pages/order/confirm/index' })
  },
  goCart() {
    wx.reLaunch({ url: '/pages/cart/index' })
  },
  goDetail(e) { wx.redirectTo({ url: `/pages/product/detail/index?id=${e.currentTarget.dataset.id}` }) }
})

function parseArray(value, fallback) { try { const parsed = JSON.parse(value || '[]'); return Array.isArray(parsed) && parsed.length ? parsed : (Array.isArray(fallback) ? fallback : [fallback]); } catch (error) { return Array.isArray(fallback) ? fallback : [fallback] } }
