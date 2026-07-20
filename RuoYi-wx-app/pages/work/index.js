const { workItems } = require('../../utils/mock')

Page({
  data: {
    filters: ['全部', '待处理', '处理中', '待审核'],
    activeFilter: '全部',
    workItems: []
  },
  onLoad() {
    this.setData({ workItems })
  },
  switchFilter(e) {
    const { filter } = e.currentTarget.dataset

    this.setData({ activeFilter: filter })
  },
  onPullDownRefresh() {
    setTimeout(() => {
      wx.stopPullDownRefresh()
    }, 600)
  }
})
