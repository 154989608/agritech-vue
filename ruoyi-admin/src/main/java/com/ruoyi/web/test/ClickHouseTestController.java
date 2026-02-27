package com.ruoyi.web.test;

import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * ClickHouse 测试接口
 * 注意：开发环境使用，生产环境请删除或加权限控制
 */
@RestController
@RequestMapping("/test/ch") // 统一前缀
public class ClickHouseTestController {

    @Autowired
    private ClickHouseUtil clickHouseUtil;

    /**
     * 测试最基础连接 (SELECT 1)
     */
    @GetMapping("/ping")
    public AjaxResult ping() {
        try {
            List<Map<String, Object>> data = clickHouseUtil.query("SELECT 1 as status");
            return AjaxResult.success("ClickHouse 连接正常", data);
        } catch (Exception e) {
            return AjaxResult.error("连接失败: " + e.getMessage());
        }
    }

    /**
     * 获取版本和时间信息
     */
    @GetMapping("/info")
    public AjaxResult info() {
        try {
            List<Map<String, Object>> data = clickHouseUtil.query("SELECT version() as version, now() as currentTime");
            return AjaxResult.success("获取信息成功", data);
        } catch (Exception e) {
            return AjaxResult.error("获取信息失败: " + e.getMessage());
        }
    }


    /**
     * 查询业务表 Bill_Meeting 统计
     */
    @GetMapping("/bill/stats")
    public AjaxResult getBillStats() {
        try {
            // 修改点：将 IS_DELETE 改为 is_delete (与建表时的字段名保持一致)
            String sql = "SELECT * FROM Bill_Meeting WHERE is_delete = 0";

            List<Map<String, Object>> data = clickHouseUtil.query(sql);
            return AjaxResult.success("查询账单统计成功", data);
        } catch (Exception e) {
            return AjaxResult.error("查询账单表失败: " + e.getMessage());
        }
    }

    /**
     * 检查数据源状态
     */
    @GetMapping("/status")
    public AjaxResult status() {
        boolean alive = clickHouseUtil.isAlive();
        if (alive) {
            return AjaxResult.success("数据源运行中");
        } else {
            return AjaxResult.error("数据源未初始化或已断开 (请查看后台日志)");
        }
    }
}