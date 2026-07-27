import request from '@/utils/request'

export function listCategory(query) { return request({ url: '/shop/category/list', method: 'get', params: query }) }
export function getCategory(categoryId) { return request({ url: `/shop/category/${categoryId}`, method: 'get' }) }
export function addCategory(data) { return request({ url: '/shop/category', method: 'post', data }) }
export function updateCategory(data) { return request({ url: '/shop/category', method: 'put', data }) }
export function delCategory(categoryId) { return request({ url: `/shop/category/${categoryId}`, method: 'delete' }) }
