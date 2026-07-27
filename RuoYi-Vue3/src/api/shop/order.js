import request from '@/utils/request'
export function listOrder(query) { return request({ url: '/shop/order/list', method: 'get', params: query }) }
export function getOrder(orderId) { return request({ url: `/shop/order/${orderId}`, method: 'get' }) }
export function cancelOrder(orderId, cancelReason) { return request({ url: `/shop/order/${orderId}/cancel`, method: 'put', data: { cancelReason } }) }
export function shipOrder(orderId, data) { return request({ url: `/shop/order/${orderId}/ship`, method: 'put', data }) }
