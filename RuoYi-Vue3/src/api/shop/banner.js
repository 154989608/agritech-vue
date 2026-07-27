import request from '@/utils/request'
export const listBanner = params => request({ url: '/shop/banner/list', method: 'get', params })
export const getBanner = id => request({ url: `/shop/banner/${id}`, method: 'get' })
export const addBanner = data => request({ url: '/shop/banner', method: 'post', data })
export const updateBanner = data => request({ url: '/shop/banner', method: 'put', data })
export const deleteBanner = id => request({ url: `/shop/banner/${id}`, method: 'delete' })
export const changeBannerStatus = (id, status) => request({ url: `/shop/banner/${id}/status`, method: 'put', data: { status } })
