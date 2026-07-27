package com.ruoyi.system.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.shop.MallCategory;
import com.ruoyi.system.domain.shop.MallProduct;
import com.ruoyi.system.domain.shop.MallProductSku;
import com.ruoyi.system.mapper.shop.MallCategoryMapper;
import com.ruoyi.system.mapper.shop.MallProductMapper;
import com.ruoyi.system.service.IMallProductService;

@Service
public class MallProductServiceImpl implements IMallProductService
{
    @Autowired private MallProductMapper productMapper;
    @Autowired private MallCategoryMapper categoryMapper;
    @Override public List<MallProduct> selectProductList(MallProduct product) { return productMapper.selectProductList(product); }
    @Override public MallProduct selectProductById(Long productId) { MallProduct product = productMapper.selectProductById(productId); if (product != null) product.setSkus(productMapper.selectSkusByProductId(productId)); return product; }
    @Override @Transactional(rollbackFor = Exception.class)
    public int insertProduct(MallProduct product) { product.setStatus("0"); validateProduct(product); productMapper.insertProduct(product); replaceSkus(product); return 1; }
    @Override @Transactional(rollbackFor = Exception.class)
    public int updateProduct(MallProduct product) { if (selectProductById(product.getProductId()) == null) throw new ServiceException("商品不存在或已删除"); validateProduct(product); productMapper.updateProduct(product); saveSkus(product); return 1; }
    @Override public int deleteProductById(Long productId) { return productMapper.deleteProductById(productId); }
    @Override @Transactional(rollbackFor = Exception.class)
    public int updateProductStatus(Long productId, String status, String updateBy) { if (!"0".equals(status) && !"1".equals(status) && !"2".equals(status)) throw new ServiceException("商品状态非法"); MallProduct product = selectProductById(productId); if (product == null) throw new ServiceException("商品不存在或已删除"); if ("1".equals(status)) { validateCategory(product.getCategoryId()); if (productMapper.countPublishableSkus(productId) == 0) throw new ServiceException("上架商品至少需要一个启用且有可售库存的SKU"); } product.setStatus(status); product.setUpdateBy(updateBy); return productMapper.updateProductStatus(product); }
    private void validateProduct(MallProduct product) { validateCategory(product.getCategoryId()); List<MallProductSku> skus = product.getSkus() == null ? Collections.<MallProductSku>emptyList() : product.getSkus(); Set<String> codes = new HashSet<String>(); for (MallProductSku sku : skus) { if (sku.getSalePrice() == null || sku.getSalePrice() < 0 || (sku.getMarketPrice() != null && sku.getMarketPrice() < 0)) throw new ServiceException("SKU价格必须为非负整数分"); if (!codes.add(sku.getSkuCode())) throw new ServiceException("同一商品内SKU编码不能重复"); } }
    private void validateCategory(Long categoryId) { MallCategory category = categoryMapper.selectCategoryById(categoryId); if (category == null || category.getLevel() != 2 || !"0".equals(category.getStatus())) throw new ServiceException("商品必须选择启用的二级分类"); }
    private void replaceSkus(MallProduct product) { saveSkus(product); }
    private void saveSkus(MallProduct product) { List<MallProductSku> skus = product.getSkus() == null ? Collections.<MallProductSku>emptyList() : product.getSkus(); for (MallProductSku sku : skus) { sku.setProductId(product.getProductId()); sku.setMarketPrice(sku.getMarketPrice() == null ? 0L : sku.getMarketPrice()); sku.setWarningStock(sku.getWarningStock() == null ? 0L : sku.getWarningStock()); sku.setStatus(sku.getStatus() == null ? "0" : sku.getStatus()); if (sku.getSkuId() == null) { sku.setAvailableStock(0L); sku.setLockedStock(0L); productMapper.insertSku(sku); } else if (productMapper.updateSku(sku) == 0) { throw new ServiceException("SKU不存在或不属于当前商品"); } } }
}
