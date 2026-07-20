const { searchHistory, searchProducts } = require('../../utils/mock')

Page({
  data: {
    keyword: '',
    history: [],
    results: []
  },
  onLoad() {
    this.setData({
      history: searchHistory,
      results: searchProducts('')
    })
  },
  onInput(e) {
    const keyword = e.detail.value
    this.setData({
      keyword,
      results: searchProducts(keyword)
    })
  },
  onConfirm() {
    this.setData({
      results: searchProducts(this.data.keyword)
    })
  },
  chooseKeyword(e) {
    const { keyword } = e.currentTarget.dataset
    this.setData({
      keyword,
      results: searchProducts(keyword)
    })
  },
  goDetail(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({ url: `/pages/product/detail/index?id=${id}` })
  }
})
