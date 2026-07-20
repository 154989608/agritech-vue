const { categoryTree, findCategoryById, getProductsByCategory } = require('../../utils/mock')

Page({
  data: {
    categories: [],
    activeCategoryId: '',
    currentCategory: { bg: 'linear-gradient(135deg, #dbeafe 0%, #eff6ff 100%)', name: '黄精专区' },
    categoryProducts: []
  },
  onLoad(options) {
    const currentCategory = findCategoryById(options.category || categoryTree[0].id)
    this.setData({
      categories: categoryTree,
      activeCategoryId: currentCategory.id,
      currentCategory,
      categoryProducts: getProductsByCategory(currentCategory.id)
    })
  },
  selectCategory(e) {
    const { id } = e.currentTarget.dataset
    const currentCategory = findCategoryById(id)
    this.setData({
      activeCategoryId: id,
      currentCategory,
      categoryProducts: getProductsByCategory(id)
    })
  },
  goSearch() {
    wx.navigateTo({ url: '/pages/search/index' })
  },
  goDetail(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({ url: `/pages/product/detail/index?id=${id}` })
  }
})
