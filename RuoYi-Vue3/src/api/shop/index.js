import request from '@/utils/request'

// Replace USE_MOCK when the shop service is available. Page contracts stay unchanged.
export const USE_MOCK = true
export const shopApi = {
  dashboard: () => request({ url: '/shop/dashboard', method: 'get' }),
  products: (params) => request({ url: '/shop/products', method: 'get', params }),
  orders: (params) => request({ url: '/shop/orders', method: 'get', params }),
  users: (params) => request({ url: '/shop/users', method: 'get', params }),
  campaigns: () => request({ url: '/shop/campaigns', method: 'get' })
}
