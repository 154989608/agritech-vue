package com.ruoyi.system.domain.shop.app;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AppCartPreviewRequest {
    @NotEmpty(message = "购物车不能为空") @Size(max=50,message="购物车商品条目不能超过50条") @Valid private List<Item> items;
    public List<Item> getItems(){return items;} public void setItems(List<Item> value){items=value;}
    public static class Item { @NotNull(message = "SKU不能为空") private Long skuId; @NotNull(message = "数量不能为空") @Min(value=1,message="数量必须大于0") @Max(value=99,message="数量不能超过99") private Integer quantity;
        public Long getSkuId(){return skuId;} public void setSkuId(Long value){skuId=value;} public Integer getQuantity(){return quantity;} public void setQuantity(Integer value){quantity=value;} }
}
