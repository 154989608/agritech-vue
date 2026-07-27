import request from '@/utils/request'

export function listProduct(query) { return request({ url: '/shop/product/list', method: 'get', params: query }) }
export function getProduct(productId) { return request({ url: `/shop/product/${productId}`, method: 'get' }) }
export function addProduct(data) { return request({ url: '/shop/product', method: 'post', data }) }
export function updateProduct(data) { return request({ url: '/shop/product', method: 'put', data }) }
export function delProduct(productId) { return request({ url: `/shop/product/${productId}`, method: 'delete' }) }
export function changeProductStatus(productId, status) { return request({ url: `/shop/product/${productId}/status`, method: 'put', data: { status } }) }
