package com.ruoyi.system.service;
import java.util.List; import com.ruoyi.system.domain.shop.MallMember; import com.ruoyi.system.domain.shop.MallMemberAddress;
public interface IMallMemberService { List<MallMember> selectMemberList(MallMember member); MallMember selectMemberById(Long memberId); List<MallMemberAddress> selectAddresses(Long memberId); int updateStatus(Long memberId,String status); }
