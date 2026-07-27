package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.shop.app.AppCartPreviewDto;
import com.ruoyi.system.domain.shop.app.AppCartPreviewItemDto;
import com.ruoyi.system.domain.shop.app.AppCartPreviewRequest;
import com.ruoyi.system.domain.shop.app.AppCategoryDto;
import com.ruoyi.system.domain.shop.app.AppHomeDto;
import com.ruoyi.system.domain.shop.app.AppProductDto;
import com.ruoyi.system.domain.shop.app.AppProductSkuDto;
import com.ruoyi.system.mapper.shop.AppShopMapper;
import com.ruoyi.system.service.IAppShopService;

@Service public class AppShopServiceImpl implements IAppShopService {
    private static final int HOME_PRODUCT_LIMIT = 8;
    @Autowired private AppShopMapper appShopMapper;
    @Override public AppHomeDto getHome(){ List<AppProductDto> products=getProducts(null,null); AppHomeDto home=new AppHomeDto(); home.setBanners(appShopMapper.selectActiveBanners()); home.setCategories(getCategories()); home.setLatestProducts(limit(products)); home.setHotProducts(limit(products)); return home; }
    @Override public List<AppCategoryDto> getCategories(){ List<AppCategoryDto> all=appShopMapper.selectEnabledCategories(); Map<Long,AppCategoryDto> roots=new LinkedHashMap<Long,AppCategoryDto>(); for(AppCategoryDto item:all){if(item.getParentId()==null||item.getParentId()==0)roots.put(item.getCategoryId(),item);} for(AppCategoryDto item:all){if(item.getParentId()!=null&&item.getParentId()!=0&&roots.containsKey(item.getParentId()))roots.get(item.getParentId()).getChildren().add(item);} return new ArrayList<AppCategoryDto>(roots.values()); }
    @Override public List<AppProductDto> getProducts(Long categoryId,String keyword){if(StringUtils.isNotEmpty(keyword)&&keyword.trim().length()>64)throw new ServiceException("关键词不能超过64个字符"); return appShopMapper.selectPublishedProducts(categoryId,StringUtils.isEmpty(keyword)?null:keyword.trim());}
    @Override public AppProductDto getProduct(Long productId){if(productId==null||productId<1)throw new ServiceException("商品不存在或已下架"); AppProductDto product=appShopMapper.selectPublishedProductById(productId); if(product==null)throw new ServiceException("商品不存在或已下架"); product.setSkus(appShopMapper.selectPublishedSkusByProductId(productId)); return product;}
    @Override public AppCartPreviewDto previewCart(AppCartPreviewRequest request){Map<Long,Integer> quantities=new LinkedHashMap<Long,Integer>(); for(AppCartPreviewRequest.Item item:request.getItems()){quantities.put(item.getSkuId(),quantities.getOrDefault(item.getSkuId(),0)+item.getQuantity());} List<AppCartPreviewItemDto> rows=appShopMapper.selectCartPreviewItems(new ArrayList<Long>(quantities.keySet())); Map<Long,AppCartPreviewItemDto> products=new LinkedHashMap<Long,AppCartPreviewItemDto>(); for(AppCartPreviewItemDto row:rows)products.put(row.getSkuId(),row); List<AppCartPreviewItemDto> items=new ArrayList<AppCartPreviewItemDto>(); long amount=0; for(Map.Entry<Long,Integer> entry:quantities.entrySet()){AppCartPreviewItemDto item=products.get(entry.getKey()); if(item==null){item=new AppCartPreviewItemDto();item.setSkuId(entry.getKey());item.setAvailable(false);item.setUnavailableReason("商品已失效");} item.setQuantity(entry.getValue()); boolean available=Boolean.TRUE.equals(item.getAvailableStock()!=null&&item.getAvailableStock()>=entry.getValue())&&Boolean.TRUE.equals(item.getAvailable()); if(!available){item.setAvailable(false);if(StringUtils.isEmpty(item.getUnavailableReason()))item.setUnavailableReason("库存不足");item.setLineAmountCent(0L);}else{long line=item.getPriceCent()*entry.getValue();item.setLineAmountCent(line);amount+=line;} items.add(item);} AppCartPreviewDto result=new AppCartPreviewDto(); result.setItems(items); result.setProductAmountCent(amount); return result;}
    private List<AppProductDto> limit(List<AppProductDto> products){return products.size()<=HOME_PRODUCT_LIMIT?products:new ArrayList<AppProductDto>(products.subList(0,HOME_PRODUCT_LIMIT));}
}
