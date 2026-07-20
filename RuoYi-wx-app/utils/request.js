function request(options) {
  const app = getApp()
  const { url, method = 'GET', data = {}, header = {}, showLoading = true } = options || {}

  if (!url) {
    return Promise.reject(new Error('request url is required'))
  }

  if (showLoading) {
    wx.showLoading({ title: '加载中...' })
  }

  return new Promise((resolve, reject) => {
    wx.request({
      url: /^https?:\/\//.test(url) ? url : `${app.globalData.apiBaseUrl}${url}`,
      method,
      data,
      header: {
        'content-type': 'application/json',
        ...(app.globalData.token ? { Authorization: `Bearer ${app.globalData.token}` } : {}),
        ...header
      },
      success(res) {
        const { statusCode, data: responseData } = res
        if (statusCode >= 200 && statusCode < 300) {
          resolve(responseData)
          return
        }
        reject(new Error((responseData && responseData.msg) || `HTTP ${statusCode}`))
      },
      fail(err) {
        reject(err)
      },
      complete() {
        if (showLoading) wx.hideLoading()
      }
    })
  })
}

module.exports = { request }
