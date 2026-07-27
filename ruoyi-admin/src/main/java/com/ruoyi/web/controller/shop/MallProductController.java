package com.ruoyi.web.controller.shop;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.shop.MallProduct;
import com.ruoyi.system.service.IMallProductService;

@RestController
@RequestMapping("/shop/product")
public class MallProductController extends BaseController
{
    @Autowired private IMallProductService productService;
    @PreAuthorize("@ss.hasPermi('shop:product:list')") @GetMapping("/list") public TableDataInfo list(MallProduct product) { startPage(); List<MallProduct> list=productService.selectProductList(product); return getDataTable(list); }
    @PreAuthorize("@ss.hasPermi('shop:product:query')") @GetMapping("/{productId}") public AjaxResult getInfo(@PathVariable Long productId) { return success(productService.selectProductById(productId)); }
    @PreAuthorize("@ss.hasPermi('shop:product:add')") @Log(title="商城商品", businessType=BusinessType.INSERT) @PostMapping public AjaxResult add(@RequestBody MallProduct product) { product.setCreateBy(getUsername()); return toAjax(productService.insertProduct(product)); }
    @PreAuthorize("@ss.hasPermi('shop:product:edit')") @Log(title="商城商品", businessType=BusinessType.UPDATE) @PutMapping public AjaxResult edit(@RequestBody MallProduct product) { product.setUpdateBy(getUsername()); return toAjax(productService.updateProduct(product)); }
    @PreAuthorize("@ss.hasPermi('shop:product:remove')") @Log(title="商城商品", businessType=BusinessType.DELETE) @DeleteMapping("/{productId}") public AjaxResult remove(@PathVariable Long productId) { return toAjax(productService.deleteProductById(productId)); }
    @PreAuthorize("@ss.hasPermi('shop:product:status')") @Log(title="商城商品", businessType=BusinessType.UPDATE) @PutMapping("/{productId}/status") public AjaxResult status(@PathVariable Long productId, @RequestBody MallProduct product) { return toAjax(productService.updateProductStatus(productId, product.getStatus(), getUsername())); }
}
