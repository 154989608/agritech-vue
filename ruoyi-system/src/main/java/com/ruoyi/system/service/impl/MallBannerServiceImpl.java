package com.ruoyi.system.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.shop.MallBanner;
import com.ruoyi.system.mapper.shop.MallBannerMapper;
import com.ruoyi.system.service.IMallBannerService;
@Service public class MallBannerServiceImpl implements IMallBannerService {
 @Autowired private MallBannerMapper mapper;
 public List<MallBanner> selectBannerList(MallBanner b){return mapper.selectBannerList(b);} public MallBanner selectBannerById(Long id){return mapper.selectBannerById(id);}
 public int insertBanner(MallBanner b){validate(b);return mapper.insertBanner(b);} public int updateBanner(MallBanner b){validate(b);return mapper.updateBanner(b);}
 public int updateStatus(Long id,String status,String user){if(!"0".equals(status)&&!"1".equals(status))throw new ServiceException("Banner状态非法");MallBanner b=selectBannerById(id);if(b==null)throw new ServiceException("Banner不存在");b.setStatus(status);b.setUpdateBy(user);return mapper.updateBannerStatus(b);} public int deleteBanner(Long id){return mapper.deleteBannerById(id);}
 private void validate(MallBanner b){if(b.getTitle()==null||b.getTitle().trim().isEmpty()||b.getImageUrl()==null||b.getImageUrl().trim().isEmpty())throw new ServiceException("标题和图片不能为空");if(b.getBeginTime()==null||b.getEndTime()==null||!b.getBeginTime().before(b.getEndTime()))throw new ServiceException("展示结束时间必须晚于开始时间");if(!"NONE".equals(b.getJumpType())&&!"PRODUCT".equals(b.getJumpType())&&!"CATEGORY".equals(b.getJumpType())&&!"PATH".equals(b.getJumpType()))throw new ServiceException("跳转类型非法");if(!"NONE".equals(b.getJumpType())&&(b.getTargetValue()==null||b.getTargetValue().trim().isEmpty()))throw new ServiceException("跳转目标不能为空");}
}
