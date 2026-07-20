Component({
  properties: {
    current: {
      type: String,
      value: '/pages/home/index'
    }
  },
  data: {
    tabs: [
      { text: '首页', short: '首', pagePath: '/pages/home/index' },
      { text: '分类', short: '分', pagePath: '/pages/category/index' },
      { text: '购物车', short: '购', pagePath: '/pages/cart/index' },
      { text: '我的', short: '我', pagePath: '/pages/profile/index' }
    ]
  },
  methods: {
    switchPage(e) {
      const { path } = e.currentTarget.dataset

      if (path && path !== this.properties.current) {
        wx.reLaunch({ url: path })
      }
    }
  }
})
