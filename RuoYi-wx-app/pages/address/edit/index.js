const { listAddresses, createAddress, updateAddress } = require('../../../api/shop')

const emptyAddress = { receiverName: '', receiverPhone: '', provinceCode: '', provinceName: '', cityCode: '', cityName: '', districtCode: '', districtName: '', detailAddress: '', isDefault: false }

Page({
  data: { addressId: null, form: emptyAddress, regionText: '', loading: false },
  onLoad(options) {
    const addressId = Number(options.id)
    if (!addressId) return
    this.setData({ addressId, loading: true })
    listAddresses().then((addresses) => {
      const address = addresses.find((item) => item.addressId === addressId)
      if (!address) return wx.showToast({ title: '地址不存在', icon: 'none' })
      this.setData({ form: { ...address, isDefault: address.isDefault === '1' }, regionText: `${address.provinceName}${address.cityName}${address.districtName}` })
    }).catch((error) => wx.showToast({ title: error.msg || '地址读取失败', icon: 'none' })).finally(() => this.setData({ loading: false }))
  },
  input(e) { this.setData({ [`form.${e.currentTarget.dataset.field}`]: e.detail.value }) },
  chooseAddress() {
    wx.chooseAddress({ success: (address) => {
      // ponytail: names are stored as compatibility codes until a managed region dictionary is introduced.
      this.setData({ form: { ...this.data.form, receiverName: address.userName || this.data.form.receiverName, receiverPhone: address.telNumber || this.data.form.receiverPhone, provinceCode: address.provinceName, provinceName: address.provinceName, cityCode: address.cityName, cityName: address.cityName, districtCode: address.countyName, districtName: address.countyName, detailAddress: address.detailInfo }, regionText: `${address.provinceName}${address.cityName}${address.countyName}` })
    }, fail: (error) => { if (!/cancel/.test(error.errMsg || '')) wx.showToast({ title: '微信地址选择失败', icon: 'none' }) } })
  },
  toggleDefault() { this.setData({ 'form.isDefault': !this.data.form.isDefault }) },
  save() {
    const { form, addressId } = this.data
    if (!form.receiverName || !/^1[3-9]\d{9}$/.test(form.receiverPhone) || !form.provinceName || !form.cityName || !form.districtName || !form.detailAddress) return wx.showToast({ title: '请完善收货地址', icon: 'none' })
    if (this.data.loading) return
    this.setData({ loading: true })
    const action = addressId ? updateAddress(addressId, form) : createAddress(form)
    action.then(() => wx.navigateBack()).catch((error) => wx.showToast({ title: error.msg || '地址保存失败', icon: 'none' })).finally(() => this.setData({ loading: false }))
  }
})
