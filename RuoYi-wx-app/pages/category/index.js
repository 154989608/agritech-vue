const { getCategories, getProducts } = require('../../api/shop')
const money = (cent) => (Number(cent || 0) / 100).toFixed(2)

Page({
  data: {
    categories: [], activeCategoryId: '', currentCategory: {}, categoryProducts: [], pageNum: 1, hasMore: true, loading: false, categoryLoadFailed: false
  },
  onLoad(options) {
    this.initialCategoryId = Number(options.category) || null
    this.loadCategories()
  },
  loadCategories() {
    this.setData({ categoryLoadFailed: false })
    getCategories().then((categories) => {
      const currentCategory = categories.find((item) => item.categoryId === this.initialCategoryId) || categories[0] || {}
      this.setData({ categories, activeCategoryId: currentCategory.categoryId, currentCategory })
      this.loadProducts(true)
    }).catch((error) => { this.setData({ categoryLoadFailed: true }); wx.showToast({ title: error.msg || '分类加载失败', icon: 'none' }) })
  },
  selectCategory(e) {
    const categoryId = Number(e.currentTarget.dataset.id)
    const currentCategory = this.data.categories.find((item) => item.categoryId === categoryId) || {}
    this.setData({ activeCategoryId: categoryId, currentCategory })
    this.loadProducts(true)
  },
  goSearch() {
    wx.navigateTo({ url: '/pages/search/index' })
  },
  goDetail(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({ url: `/pages/product/detail/index?id=${id}` })
  },
  loadProducts(reset) {
    if (this.data.loading || (!reset && !this.data.hasMore)) return Promise.resolve()
    const pageNum = reset ? 1 : this.data.pageNum + 1
    this.setData({ loading: true })
    return getProducts({ categoryId: this.data.activeCategoryId, pageNum, pageSize: 20 }).then((response) => {
      const rows = response.rows || []
      const products = rows.map((product) => ({ ...product, priceText: money(product.priceCent) }))
      this.setData({ categoryProducts: reset ? products : this.data.categoryProducts.concat(products), pageNum, hasMore: rows.length === 20 })
    }).catch((error) => wx.showToast({ title: error.msg || '商品加载失败', icon: 'none' })).finally(() => this.setData({ loading: false }))
  },
  onReachBottom() { this.loadProducts(false) }
})
