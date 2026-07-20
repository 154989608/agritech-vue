# 公交站点客流可视化大屏API接口文档

## 接口概述
本系统提供15个统计接口，用于公交站点客流数据的可视化大屏展示。

## 基础信息
- **基础路径**: `/bus/station/flow`
- **返回格式**: JSON
- **状态码**: 200表示成功，500表示失败

## 接口列表

### 1. 查询站点客流列表
```
GET /bus/station/flow/list
```
**参数:**
- stopName: 站点名称（可选）
- lineName: 线路名称（可选）
- zoneType: 区域类型（可选）
- timePeriod: 时段分类（可选）
- statYear: 统计年份（可选）
- statMonth: 统计月份（可选）

**返回示例:**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "statTime": "2024-01-01T08:00:00",
      "stopName": "火车站",
      "lineName": "K1路",
      "zoneType": "一般区域",
      "timePeriod": "早高峰",
      "boardingCount": 150,
      "alightingCount": 120,
      "netFlow": 30,
      "loadFactor": 0.75,
      "yoyRate": 0.12
    }
  ]
}
```

### 2. 获取总客流量统计
```
GET /bus/station/flow/totalFlow
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

### 3. 按线路统计客流量
```
GET /bus/station/flow/byLine
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

**返回数据包含:**
- lineName: 线路名称
- totalFlow: 总客流量
- avgLoadFactor: 平均满载率
- stationCount: 站点数量

### 4. 按站点统计客流量
```
GET /bus/station/flow/byStation
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）
- limit: 限制数量，默认10

### 5. 按区域统计客流量
```
GET /bus/station/flow/byZone
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

### 6. 按时段统计客流量
```
GET /bus/station/flow/byTimePeriod
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

### 7. 获取客流趋势数据
```
GET /bus/station/flow/trend
```
**参数:**
- days: 天数，默认30

### 8. 获取热门线路TOP10
```
GET /bus/station/flow/topLines
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

### 9. 获取热门站点TOP10
```
GET /bus/station/flow/topStations
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

### 10. 获取平均满载率
```
GET /bus/station/flow/avgLoadFactor
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

### 11. 获取同比变化率
```
GET /bus/station/flow/yoyRate
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

### 12. 获取实时客流数据
```
GET /bus/station/flow/realTime
```
**参数:**
- hours: 小时数，默认24

### 13. 获取客流高峰时段
```
GET /bus/station/flow/peakHours
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

### 14. 获取各线路满载率排名
```
GET /bus/station/flow/loadFactorRanking
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

### 15. 获取大屏综合统计数据（推荐）
```
GET /bus/station/flow/dashboard
```
**参数:**
- year: 年份（可选）
- month: 月份（可选）

**返回所有统计数据的综合结果，包含:**
- totalFlow: 总客流量
- averageLoadFactor: 平均满载率
- yoyRate: 同比变化率
- flowByLine: 按线路统计
- flowByZone: 按区域统计
- flowByTimePeriod: 按时段统计
- topLines: 热门线路TOP10
- topStations: 热门站点TOP10
- peakHours: 高峰时段
- loadFactorRanking: 满载率排名
- flowTrend: 趋势数据
- realTimeFlow: 实时数据

## 使用建议

1. **大屏首页**: 推荐使用 `/dashboard` 接口一次性获取所有需要的数据
2. **实时监控**: 使用 `/realTime` 和 `/peakHours` 接口
3. **数据分析**: 使用 `/byLine`、`/byZone`、`/byTimePeriod` 等细分统计接口
4. **排行榜**: 使用 `/topLines` 和 `/topStations` 接口

## 错误处理
所有接口在出错时会返回:
```json
{
  "code": 500,
  "msg": "错误信息"
}
```

## 性能优化建议
1. 合理使用缓存机制
2. 对于大量数据的接口建议添加分页参数
3. 及时清理历史数据
4. 建立适当的数据库索引