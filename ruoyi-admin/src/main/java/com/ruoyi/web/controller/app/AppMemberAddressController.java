package com.ruoyi.web.controller.app;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.security.context.AppMemberContext;
import com.ruoyi.system.domain.shop.MallMemberAddress;
import com.ruoyi.system.domain.shop.app.AppMemberAddressDto;
import com.ruoyi.system.domain.shop.app.AppMemberAddressRequest;
import com.ruoyi.system.service.IAppMemberAddressService;

@RestController @RequestMapping("/app/shop/member/addresses") public class AppMemberAddressController { @Autowired private IAppMemberAddressService addressService; @GetMapping public AjaxResult list(){List<AppMemberAddressDto> result=new ArrayList<AppMemberAddressDto>();for(MallMemberAddress address:addressService.list(AppMemberContext.getMemberId()))result.add(toDto(address));return AjaxResult.success(result);}@PostMapping public AjaxResult add(@Valid @RequestBody AppMemberAddressRequest request){return AjaxResult.success(toDto(addressService.add(AppMemberContext.getMemberId(),request)));}@PutMapping("/{addressId}") public AjaxResult update(@PathVariable Long addressId,@Valid @RequestBody AppMemberAddressRequest request){return AjaxResult.success(toDto(addressService.update(AppMemberContext.getMemberId(),addressId,request)));}@DeleteMapping("/{addressId}") public AjaxResult delete(@PathVariable Long addressId){return AjaxResult.success(addressService.delete(AppMemberContext.getMemberId(),addressId));}@PutMapping("/{addressId}/default") public AjaxResult setDefault(@PathVariable Long addressId){return AjaxResult.success(addressService.setDefault(AppMemberContext.getMemberId(),addressId));}private AppMemberAddressDto toDto(MallMemberAddress source){AppMemberAddressDto result=new AppMemberAddressDto();result.setAddressId(source.getAddressId());result.setReceiverName(source.getReceiverName());result.setReceiverPhone(source.getReceiverPhone());result.setProvinceCode(source.getProvinceCode());result.setProvinceName(source.getProvinceName());result.setCityCode(source.getCityCode());result.setCityName(source.getCityName());result.setDistrictCode(source.getDistrictCode());result.setDistrictName(source.getDistrictName());result.setDetailAddress(source.getDetailAddress());result.setIsDefault(source.getIsDefault());return result;} }
