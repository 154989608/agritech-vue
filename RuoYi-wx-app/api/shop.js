const { request } = require('../utils/request')

const data = (options) => request(options).then((response) => response.data)

function getHome() { return data({ url: '/app/shop/home', showLoading: false }) }
function getCategories() { return data({ url: '/app/shop/categories', showLoading: false }) }
function getProducts(params) { return request({ url: '/app/shop/products', data: params, showLoading: false }) }
function getProduct(productId) { return data({ url: `/app/shop/products/${productId}`, showLoading: false }) }
function previewCart(items) { return data({ url: '/app/shop/cart/preview', method: 'POST', data: { items }, showLoading: false }) }
function previewOrder(payload) { return data({ url: '/app/shop/orders/preview', method: 'POST', data: payload, showLoading: false }) }
function loginByWx(code) { return data({ url: '/app/shop/auth/login', method: 'POST', data: { code } }) }

module.exports = { getHome, getCategories, getProducts, getProduct, previewCart, previewOrder, loginByWx }
