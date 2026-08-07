const { getProducts } = require('../../api/shop')

const HISTORY_KEY = 'mall-search-history'
const money = (cent) => (Number(cent || 0) / 100).toFixed(2)

Page({
  data: { keyword: '', history: [], results: [], loading: false },
  onLoad() { this.setData({ history: wx.getStorageSync(HISTORY_KEY) || [] }); this.search('') },
  onUnload() { clearTimeout(this.searchTimer) },
  onInput(e) {
    const keyword = e.detail.value
    this.setData({ keyword })
    clearTimeout(this.searchTimer)
    this.searchTimer = setTimeout(() => this.search(keyword), 300)
  },
  onConfirm() { clearTimeout(this.searchTimer); this.search(this.data.keyword, true) },
  chooseKeyword(e) { const keyword = e.currentTarget.dataset.keyword; this.setData({ keyword }); this.search(keyword, true) },
  search(keyword, saveHistory) {
    const value = String(keyword || '').trim()
    this.setData({ loading: true })
    return getProducts({ keyword: value || undefined, pageNum: 1, pageSize: 20 }).then((response) => {
      this.setData({ results: (response.rows || []).map((product) => ({ ...product, priceText: money(product.priceCent) })) })
      if (saveHistory && value) this.saveHistory(value)
    }).catch((error) => wx.showToast({ title: error.msg || '搜索失败', icon: 'none' })).finally(() => this.setData({ loading: false }))
  },
  saveHistory(keyword) {
    const history = [keyword].concat(this.data.history.filter((item) => item !== keyword)).slice(0, 10)
    wx.setStorageSync(HISTORY_KEY, history)
    this.setData({ history })
  },
  clearHistory() {
    wx.removeStorageSync(HISTORY_KEY)
    this.setData({ history: [] })
  },
  goDetail(e) { wx.navigateTo({ url: `/pages/product/detail/index?id=${e.currentTarget.dataset.id}` }) }
})
