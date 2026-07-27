package com.ruoyi.system.service;
import java.util.List; import com.ruoyi.system.domain.shop.MallMemberAddress; import com.ruoyi.system.domain.shop.app.AppMemberAddressRequest;
public interface IAppMemberAddressService { List<MallMemberAddress> list(Long memberId); MallMemberAddress add(Long memberId,AppMemberAddressRequest request); MallMemberAddress update(Long memberId,Long addressId,AppMemberAddressRequest request); int delete(Long memberId,Long addressId); int setDefault(Long memberId,Long addressId); }
