const { request } = require('../utils/request')

const data = (options) => request(options).then((response) => response.data)

function getHome() { return data({ url: '/app/shop/home', showLoading: false }) }
function getCategories() { return data({ url: '/app/shop/categories', showLoading: false }) }
function getProducts(params) { return request({ url: '/app/shop/products', data: params, showLoading: false }) }
function getProduct(productId) { return data({ url: `/app/shop/products/${productId}`, showLoading: false }) }
function previewCart(items) { return data({ url: '/app/shop/cart/preview', method: 'POST', data: { items }, showLoading: false }) }
function previewOrder(payload) { return data({ url: '/app/shop/orders/preview', method: 'POST', data: payload, showLoading: false }) }
function createOrder(payload) { return data({ url: '/app/shop/orders', method: 'POST', data: payload }) }
function listOrders(params) { return request({ url: '/app/shop/orders', data: params, showLoading: false }) }
function getOrder(orderNo) { return data({ url: `/app/shop/orders/${orderNo}`, showLoading: false }) }
function cancelOrder(orderNo, cancelReason) { return data({ url: `/app/shop/orders/${orderNo}/cancel`, method: 'POST', data: { cancelReason } }) }
function confirmReceipt(orderNo) { return data({ url: `/app/shop/orders/${orderNo}/confirm-receipt`, method: 'POST' }) }
function loginByWx(code) { return data({ url: '/app/shop/auth/login', method: 'POST', data: { code } }) }
function getMemberProfile() { return data({ url: '/app/shop/me', showLoading: false }) }
function listAddresses() { return data({ url: '/app/shop/member/addresses', showLoading: false }) }
function createAddress(payload) { return data({ url: '/app/shop/member/addresses', method: 'POST', data: payload }) }
function updateAddress(addressId, payload) { return data({ url: `/app/shop/member/addresses/${addressId}`, method: 'PUT', data: payload }) }
function deleteAddress(addressId) { return data({ url: `/app/shop/member/addresses/${addressId}`, method: 'DELETE' }) }
function setDefaultAddress(addressId) { return data({ url: `/app/shop/member/addresses/${addressId}/default`, method: 'PUT' }) }
function listCoupons() { return data({ url: '/app/shop/coupons', showLoading: false }) }
function listMemberCoupons(status) { return data({ url: '/app/shop/member/coupons', data: status ? { status } : {}, showLoading: false }) }
function claimCoupon(couponId) { return data({ url: `/app/shop/coupons/${couponId}/claim`, method: 'POST' }) }

module.exports = { getHome, getCategories, getProducts, getProduct, previewCart, previewOrder, createOrder, listOrders, getOrder, cancelOrder, confirmReceipt, loginByWx, getMemberProfile, listAddresses, createAddress, updateAddress, deleteAddress, setDefaultAddress, listCoupons, listMemberCoupons, claimCoupon }
