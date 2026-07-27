import request from '@/utils/request'
export const listMember = params => request({ url: '/shop/member/list', method: 'get', params })
export const getMember = id => request({ url: `/shop/member/${id}`, method: 'get' })
export const listMemberAddresses = id => request({ url: `/shop/member/${id}/addresses`, method: 'get' })
export const changeMemberStatus = (id, status) => request({ url: `/shop/member/${id}/status`, method: 'put', data: { status } })
