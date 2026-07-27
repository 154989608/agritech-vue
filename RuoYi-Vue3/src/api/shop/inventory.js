import request from '@/utils/request'

export const adjustInventory = data => request({ url: '/shop/inventory/adjust', method: 'post', data })
