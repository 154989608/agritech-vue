package com.ruoyi.web.controller.shop;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.shop.MallBanner;
import com.ruoyi.system.service.IMallBannerService;
@RestController @RequestMapping("/shop/banner") public class MallBannerController extends BaseController { @Autowired private IMallBannerService service; @PreAuthorize("@ss.hasPermi('shop:banner:list')") @GetMapping("/list") public TableDataInfo list(MallBanner b){startPage();List<MallBanner> list=service.selectBannerList(b);return getDataTable(list);}@Log(title="商城Banner",businessType=BusinessType.EXPORT) @PreAuthorize("@ss.hasPermi('shop:banner:export')") @PostMapping("/export") public void export(HttpServletResponse response,MallBanner b){List<MallBanner> list=service.selectBannerList(b);new ExcelUtil<MallBanner>(MallBanner.class).exportExcel(response,list,"商城Banner数据");}@PreAuthorize("@ss.hasPermi('shop:banner:query')") @GetMapping("/{id}") public AjaxResult get(@PathVariable Long id){return success(service.selectBannerById(id));}@PreAuthorize("@ss.hasPermi('shop:banner:add')") @Log(title="商城Banner",businessType=BusinessType.INSERT) @PostMapping public AjaxResult add(@RequestBody MallBanner b){b.setCreateBy(getUsername());return toAjax(service.insertBanner(b));}@PreAuthorize("@ss.hasPermi('shop:banner:edit')") @Log(title="商城Banner",businessType=BusinessType.UPDATE) @PutMapping public AjaxResult edit(@RequestBody MallBanner b){b.setUpdateBy(getUsername());return toAjax(service.updateBanner(b));}@PreAuthorize("@ss.hasPermi('shop:banner:status')") @PutMapping("/{id}/status") public AjaxResult status(@PathVariable Long id,@RequestBody MallBanner b){return toAjax(service.updateStatus(id,b.getStatus(),getUsername()));}@PreAuthorize("@ss.hasPermi('shop:banner:remove')") @Log(title="商城Banner",businessType=BusinessType.DELETE) @DeleteMapping("/{id}") public AjaxResult remove(@PathVariable Long id){return toAjax(service.deleteBanner(id));}}
