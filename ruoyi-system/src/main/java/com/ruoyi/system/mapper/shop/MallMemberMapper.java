package com.ruoyi.system.mapper.shop;
import java.util.List; import com.ruoyi.system.domain.shop.MallMember; import com.ruoyi.system.domain.shop.MallMemberAddress;
public interface MallMemberMapper { List<MallMember> selectMemberList(MallMember member); MallMember selectMemberById(Long memberId); List<MallMemberAddress> selectAddressesByMemberId(Long memberId); int updateMemberStatus(MallMember member); }
