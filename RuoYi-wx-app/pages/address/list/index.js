const { listAddresses, setDefaultAddress, deleteAddress } = require('../../../api/shop')

Page({
  data: { addressList: [], loading: false, selectMode: false },
  onLoad(options) { this.setData({ selectMode: options.select === '1' }) },
  onShow() { this.loadAddresses() },
  loadAddresses() { this.setData({ loading: true }); return listAddresses().then((addressList) => this.setData({ addressList })).catch((error) => { if (error.code === 401) wx.navigateTo({ url: '/pages/login/index' }); else wx.showToast({ title: error.msg || '地址加载失败', icon: 'none' }) }).finally(() => this.setData({ loading: false })) },
  setDefault(e) { const addressId = e.currentTarget.dataset.id; setDefaultAddress(addressId).then(() => this.loadAddresses()).catch((error) => wx.showToast({ title: error.msg || '设置默认地址失败', icon: 'none' })) },
  selectAddress(e) { if (!this.data.selectMode) return; const address = this.data.addressList.find((item) => item.addressId === Number(e.currentTarget.dataset.id)); if (address) wx.setStorageSync('mall-checkout-address', address); wx.navigateBack() },
  addAddress() { wx.navigateTo({ url: '/pages/address/edit/index' }) },
  editAddress(e) { wx.navigateTo({ url: `/pages/address/edit/index?id=${e.currentTarget.dataset.id}` }) },
  deleteAddress(e) { const addressId = e.currentTarget.dataset.id; wx.showModal({ title: '删除地址', content: '确定删除该收货地址？', success: ({ confirm }) => confirm && deleteAddress(addressId).then(() => this.loadAddresses()).catch((error) => wx.showToast({ title: error.msg || '删除失败', icon: 'none' })) }) }
})
