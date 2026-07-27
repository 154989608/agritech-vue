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
import com.ruoyi.system.domain.shop.MallOrder;
import com.ruoyi.system.domain.shop.MallOrderCreateRequest;
import com.ruoyi.system.service.IMallOrderService;
@RestController @RequestMapping("/shop/order") public class MallOrderController extends BaseController {
 @Autowired private IMallOrderService orderService;
 @PreAuthorize("@ss.hasPermi('shop:order:list')") @GetMapping("/list") public TableDataInfo list(MallOrder order){startPage();List<MallOrder> list=orderService.selectOrderList(order);return getDataTable(list);}
 @Log(title="商城订单",businessType=BusinessType.EXPORT) @PreAuthorize("@ss.hasPermi('shop:order:export')") @PostMapping("/export") public void export(HttpServletResponse response,MallOrder order){List<MallOrder> list=orderService.selectOrderList(order);new ExcelUtil<MallOrder>(MallOrder.class).exportExcel(response,list,"商城订单数据");}
 @PreAuthorize("@ss.hasPermi('shop:order:add')") @Log(title="商城订单",businessType=BusinessType.INSERT) @PostMapping public AjaxResult create(@RequestBody MallOrderCreateRequest request){return success(orderService.createOrder(request));}
 @PreAuthorize("@ss.hasPermi('shop:order:query')") @GetMapping("/{orderId}") public AjaxResult getInfo(@PathVariable Long orderId){return success(orderService.selectOrderById(orderId));}
 @PreAuthorize("@ss.hasPermi('shop:order:sensitive')") @GetMapping("/{orderId}/sensitive") public AjaxResult getSensitiveInfo(@PathVariable Long orderId){return success(orderService.selectOrderSensitiveById(orderId));}
 @PreAuthorize("@ss.hasPermi('shop:order:pay')") @Log(title="商城订单",businessType=BusinessType.UPDATE) @PutMapping("/{orderId}/pay-success") public AjaxResult paySuccess(@PathVariable Long orderId,@RequestBody MallOrder order){return toAjax(orderService.paySuccess(orderId,order.getPayChannel(),order.getChannelTradeNo(),order.getPaidAmount(),getUsername()));}
 @PreAuthorize("@ss.hasPermi('shop:order:cancel')") @Log(title="商城订单",businessType=BusinessType.UPDATE) @PutMapping("/{orderId}/cancel") public AjaxResult cancel(@PathVariable Long orderId,@RequestBody MallOrder order){return toAjax(orderService.cancelOrder(orderId,order.getCancelReason(),getUsername()));}
 @PreAuthorize("@ss.hasPermi('shop:order:ship')") @Log(title="商城订单",businessType=BusinessType.UPDATE) @PutMapping("/{orderId}/ship") public AjaxResult ship(@PathVariable Long orderId,@RequestBody MallOrder order){return toAjax(orderService.shipOrder(orderId,order.getLogisticsCompany(),order.getLogisticsNo(),getUsername()));}
 @PreAuthorize("@ss.hasPermi('shop:order:ship')") @Log(title="商城订单",businessType=BusinessType.UPDATE) @PutMapping("/{orderId}/complete") public AjaxResult complete(@PathVariable Long orderId){return toAjax(orderService.completeOrder(orderId,getUsername()));}
}
