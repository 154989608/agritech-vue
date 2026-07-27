const API_BASE_URLS = {
  develop: 'http://127.0.0.1:8080',
  trial: 'https://trial-api.example.com',
  release: 'https://api.example.com'
}

function getApiBaseUrl() {
  const accountInfo = wx.getAccountInfoSync && wx.getAccountInfoSync()
  const envVersion = accountInfo && accountInfo.miniProgram && accountInfo.miniProgram.envVersion
  return API_BASE_URLS[envVersion] || API_BASE_URLS.develop
}

module.exports = { getApiBaseUrl }
