package com.ruoyi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.DimLines;
import com.ruoyi.service.DimLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 公交线路维度 Controller
 * 
 * @author 若依
 */
@RestController
@RequestMapping("/bus/lines")
public class DimLinesController extends BaseController {
    
    @Autowired
    private DimLinesService dimLinesService;
    
    /**
     * 查询线路列表
     */
    @GetMapping("/list")
    public AjaxResult list(DimLines dimLines) {
        List<DimLines> list = dimLinesService.selectDimLinesList(dimLines);
        return success(list);
    }
    
    /**
     * 获取所有线路基本信息
     */
    @GetMapping("/all")
    public AjaxResult getAllLinesBasicInfo() {
        List<Map<String, Object>> lines = dimLinesService.getAllLinesBasicInfo();
        return success(lines);
    }
    
    /**
     * 获取线路详细信息
     */
    @GetMapping("/detail")
    public AjaxResult getLineDetail(@RequestParam String lineName) {
        Map<String, Object> detail = dimLinesService.getLineDetail(lineName);
        return success(detail);
    }
}
