package com.ruoyi.web.controller.app;

import java.util.List;
import javax.validation.Valid;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.shop.app.AppCartPreviewRequest;
import com.ruoyi.system.domain.shop.app.AppProductDto;
import com.ruoyi.system.service.IAppShopService;

@RestController @RequestMapping("/app/shop") public class AppShopController extends BaseController {
    @Autowired private IAppShopService appShopService;
    @Anonymous @GetMapping("/home") public AjaxResult home(){return success(appShopService.getHome());}
    @Anonymous @GetMapping("/categories") public AjaxResult categories(){return success(appShopService.getCategories());}
    @Anonymous @GetMapping("/products") public TableDataInfo products(@RequestParam(required=false) Long categoryId,@RequestParam(required=false) String keyword,@RequestParam(defaultValue="1") Integer pageNum,@RequestParam(defaultValue="20") Integer pageSize){if(pageNum<1||pageSize<1||pageSize>100)throw new IllegalArgumentException("分页参数非法");PageHelper.startPage(pageNum,pageSize);List<AppProductDto> products=appShopService.getProducts(categoryId,keyword);return getDataTable(products);}
    @Anonymous @GetMapping("/products/{productId}") public AjaxResult product(@PathVariable Long productId){return success(appShopService.getProduct(productId));}
    @Anonymous @PostMapping("/cart/preview") public AjaxResult previewCart(@Valid @RequestBody AppCartPreviewRequest request){return success(appShopService.previewCart(request));}
}
