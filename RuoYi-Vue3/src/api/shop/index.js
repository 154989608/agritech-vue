import request from '@/utils/request'

export const shopApi = {
  dashboard: () => request({ url: '/shop/dashboard', method: 'get' })
}
