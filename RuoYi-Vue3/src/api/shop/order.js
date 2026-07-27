import request from '@/utils/request'
export function listOrder(query) { return request({ url: '/shop/order/list', method: 'get', params: query }) }
export function getOrder(orderId) { return request({ url: `/shop/order/${orderId}`, method: 'get' }) }
export function getOrderSensitive(orderId) { return request({ url: `/shop/order/${orderId}/sensitive`, method: 'get' }) }
export function createOrder(data) { return request({ url: '/shop/order', method: 'post', data }) }
export function paySuccess(orderId, data) { return request({ url: `/shop/order/${orderId}/pay-success`, method: 'put', data }) }
export function cancelOrder(orderId, cancelReason) { return request({ url: `/shop/order/${orderId}/cancel`, method: 'put', data: { cancelReason } }) }
export function shipOrder(orderId, data) { return request({ url: `/shop/order/${orderId}/ship`, method: 'put', data }) }
export function completeOrder(orderId) { return request({ url: `/shop/order/${orderId}/complete`, method: 'put' }) }
