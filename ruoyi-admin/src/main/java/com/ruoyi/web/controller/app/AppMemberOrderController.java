package com.ruoyi.web.controller.app;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.framework.security.context.AppMemberContext;
import com.ruoyi.system.domain.shop.MallOrder;
import com.ruoyi.system.domain.shop.app.AppMemberOrderCancelRequest;
import com.ruoyi.system.domain.shop.app.AppMemberOrderRequest;
import com.ruoyi.system.service.IAppMemberOrderService;

@Validated @RestController @RequestMapping("/app/shop") public class AppMemberOrderController extends BaseController {
 @Autowired private IAppMemberOrderService orderService;
 @PostMapping("/orders/preview") public AjaxResult preview(@Valid @RequestBody AppMemberOrderRequest request){return AjaxResult.success(orderService.preview(AppMemberContext.getMemberId(),request));}
 @RateLimiter(time=60,count=10,limitType=LimitType.IP) @PostMapping("/orders") public AjaxResult create(@Valid @RequestBody AppMemberOrderRequest request){return AjaxResult.success(orderService.createOrder(AppMemberContext.getMemberId(),request));}
 @GetMapping("/orders") public TableDataInfo list(@RequestParam(required=false) String status,@RequestParam(defaultValue="1") @Min(1) Integer pageNum,@RequestParam(defaultValue="20") @Min(1) @Max(100) Integer pageSize){if(status!=null&&!Arrays.asList("PENDING_PAYMENT","PENDING_SHIPMENT","SHIPPED","COMPLETED","CANCELED").contains(status))throw new ServiceException("订单状态非法");PageHelper.startPage(pageNum,pageSize);List<MallOrder> orders=orderService.list(AppMemberContext.getMemberId(),status);TableDataInfo result=getDataTable(orders);result.setRows(orderService.toDtos(orders));return result;}
 @GetMapping("/orders/{orderNo}") public AjaxResult detail(@PathVariable @Size(max=64) String orderNo){return AjaxResult.success(orderService.detail(AppMemberContext.getMemberId(),orderNo));}
 @PostMapping("/orders/{orderNo}/cancel") public AjaxResult cancel(@PathVariable @Size(max=64) String orderNo,@Valid @RequestBody(required=false) AppMemberOrderCancelRequest request){return AjaxResult.success(orderService.cancel(AppMemberContext.getMemberId(),orderNo,request==null?null:request.getCancelReason()));}
 @PostMapping("/orders/{orderNo}/confirm-receipt") public AjaxResult confirmReceipt(@PathVariable @Size(max=64) String orderNo){return AjaxResult.success(orderService.confirmReceipt(AppMemberContext.getMemberId(),orderNo));}
}
