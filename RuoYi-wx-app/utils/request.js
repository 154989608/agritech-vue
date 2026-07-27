let loadingCount = 0
let handlingUnauthorized = false

function request(options) {
  const app = getApp()
  const { url, method = 'GET', data = {}, header = {}, showLoading = true } = options || {}

  if (!url) {
    return Promise.reject(new Error('request url is required'))
  }

  if (showLoading && loadingCount++ === 0) wx.showLoading({ title: '加载中...' })

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
        if (statusCode === 401) {
          if (!handlingUnauthorized) {
            handlingUnauthorized = true
            wx.removeStorageSync('token')
            wx.removeStorageSync('userInfo')
            app.globalData.token = ''
            app.globalData.userInfo = null
            wx.showToast({ title: '登录已失效，请重新登录', icon: 'none' })
            setTimeout(() => { handlingUnauthorized = false }, 500)
          }
          reject({ code: 401, msg: '登录已失效' })
          return
        }
        if (statusCode >= 200 && statusCode < 300 && responseData && responseData.code !== 200) {
          reject({ code: responseData.code, msg: responseData.msg || '请求失败' })
          return
        }
        if (statusCode >= 200 && statusCode < 300) {
          resolve(responseData)
          return
        }
        reject({ code: statusCode, msg: (responseData && responseData.msg) || `HTTP ${statusCode}` })
      },
      fail(err) {
        reject({ code: 0, msg: err.errMsg || '网络连接失败' })
      },
      complete() {
        if (showLoading && --loadingCount === 0) wx.hideLoading()
      }
    })
  })
}

module.exports = { request }
