import request from '@/utils/request'
export const listCoupon = params => request({ url: '/shop/coupon/list', method: 'get', params })
export const getCoupon = id => request({ url: `/shop/coupon/${id}`, method: 'get' })
export const addCoupon = data => request({ url: '/shop/coupon', method: 'post', data })
export const updateCoupon = data => request({ url: '/shop/coupon', method: 'put', data })
export const deleteCoupon = id => request({ url: `/shop/coupon/${id}`, method: 'delete' })
export const changeCouponStatus = (id, status) => request({ url: `/shop/coupon/${id}/status`, method: 'put', data: { status } })
