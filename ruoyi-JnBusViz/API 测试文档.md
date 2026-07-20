# 公交数据可视化大屏接口测试说明

## 一、接口列表汇总

### 1. 客流统计相关接口（FactStationFlow）

#### 1.1 基础查询接口
| 接口路径 | 方法 | 说明 | 参数 |
|---------|------|------|------|
| `/bus/station/flow/list` | GET | 查询站点客流列表 | stopName, lineName, zoneType, timePeriod, statYear, statMonth |
| `/bus/station/flow/totalFlow` | GET | 获取总客流量 | year, month |
| `/bus/station/flow/dashboard` | GET | 获取综合统计数据 | year, month |

#### 1.2 统计分析接口
| 接口路径 | 方法 | 说明 | 参数 |
|---------|------|------|------|
| `/bus/station/flow/byLine` | GET | 按线路统计客流量 | year, month |
| `/bus/station/flow/byStation` | GET | 按站点统计客流量 | year, month, limit(默认 10) |
| `/bus/station/flow/byZone` | GET | 按区域统计客流量 | year, month |
| `/bus/station/flow/byTimePeriod` | GET | 按时段统计客流量 | year, month |

#### 1.3 趋势分析接口
| 接口路径 | 方法 | 说明 | 参数 |
|---------|------|------|------|
| `/bus/station/flow/trend` | GET | 获取客流趋势数据 | days(默认 30) |
| `/bus/station/flow/realTime` | GET | 获取实时客流数据 | hours(默认 24) |

#### 1.4 排名分析接口
| 接口路径 | 方法 | 说明 | 参数 |
|---------|------|------|------|
| `/bus/station/flow/topLines` | GET | 获取热门线路 TOP10 | year, month |
| `/bus/station/flow/topStations` | GET | 获取热门站点 TOP10 | year, month |
| `/bus/station/flow/peakHours` | GET | 获取客流高峰时段 | year, month |
| `/bus/station/flow/loadFactorRanking` | GET | 获取线路满载率排名 | year, month |

#### 1.5 指标分析接口
| 接口路径 | 方法 | 说明 | 参数 |
|---------|------|------|------|
| `/bus/station/flow/avgLoadFactor` | GET | 获取平均满载率 | year, month |
| `/bus/station/flow/yoyRate` | GET | 获取同比变化率 | year, month |

---

### 2. 站点维度相关接口（DimStops）

| 接口路径 | 方法 | 说明 | 参数 |
|---------|------|------|------|
| `/bus/stops/list` | GET | 查询站点列表 | stopName, zoneType, zoneCode |
| `/bus/stops/map/all` | GET | 获取所有站点（GIS 地图用） | 无 |
| `/bus/stops/zoneType/count` | GET | 按区域类型统计站点数量 | 无 |
| `/bus/stops/location` | GET | 获取站点经纬度信息 | stopName(必填) |
| `/bus/stops/byZoneType` | GET | 获取指定区域的站点 | zoneType(必填) |

---

### 3. IC 卡交易相关接口（FactCardTransactions）

| 接口路径 | 方法 | 说明 | 参数 |
|---------|------|------|------|
| `/bus/card/transactions/list` | GET | 查询 IC 卡交易列表 | cardId, lineName, stopName, cardType, statYear, statMonth |
| `/bus/card/transactions/byCardType` | GET | 按卡种统计客流量 | year, month |
| `/bus/card/transactions/transfer/top10` | GET | 获取换乘率 TOP10 站点 | limit(默认 10) |
| `/bus/card/transactions/station/cardTypeDist` | GET | 按站点统计卡种分布 | stopName(必填), year, month |
| `/bus/card/transactions/line/cardTypeFlow` | GET | 获取线路卡种客流对比 | lineName(必填), year, month |
| `/bus/card/transactions/trend/daily` | GET | 获取日均交易量趋势 | days(默认 30) |

---

### 4. 线路维度相关接口（DimLines）

| 接口路径 | 方法 | 说明 | 参数 |
|---------|------|------|------|
| `/bus/lines/list` | GET | 查询线路列表 | lineName, startStop, endStop |
| `/bus/lines/all` | GET | 获取所有线路基本信息 | 无 |
| `/bus/lines/detail` | GET | 获取线路详细信息 | lineName(必填) |

---

### 5. 综合测试接口

| 接口路径 | 方法 | 说明 | 参数 |
|---------|------|------|------|
| `/bus/test/dashboard` | GET | 获取大屏综合统计数据 | year, month |
| `/bus/test/gis/map` | GET | 测试 GIS 地图数据 | 无 |
| `/bus/test/transfer/analysis` | GET | 测试换乘率分析 | limit(默认 10) |
| `/bus/test/line/detail` | GET | 测试线路详细数据 | lineName(必填) |

---

## 二、Postman 测试用例

### 测试前准备

1. **启动项目**：确保 RuoYi-Vue 项目正常运行
2. **数据库连接**：确认 ClickHouse 从库配置正确且数据已导入
3. **端口确认**：默认端口为 8080，根据实际情况调整

---

### Postman 请求示例

#### 1. 测试客流统计接口

```http
GET http://localhost:8080/bus/station/flow/totalFlow?year=2025&month=5
Content-Type: application/json
```

**预期响应：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": 125800
}
```

---

#### 2. 测试按线路统计

```http
GET http://localhost:8080/bus/station/flow/byLine?year=2025&month=5
Content-Type: application/json
```

**预期响应：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "lineName": "K239",
      "totalFlow": 45200,
      "avgLoadFactor": 0.75,
      "stationCount": 28
    },
    {
      "lineName": "692",
      "totalFlow": 38500,
      "avgLoadFactor": 0.68,
      "stationCount": 22
    }
  ]
}
```

---

#### 3. 测试 GIS 地图数据

```http
GET http://localhost:8080/bus/stops/map/all
Content-Type: application/json
```

**预期响应：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "stopName": "商职学院",
      "longitude": 116.990553,
      "latitude": 36.666514,
      "zoneType": "高校",
      "stopAddress": "历城区经十东路"
    },
    {
      "stopName": "营而村",
      "longitude": 117.025234,
      "latitude": 36.564012,
      "zoneType": "居民区",
      "stopAddress": "市中区经十西路"
    }
  ]
}
```

---

#### 4. 测试 IC 卡交易统计

```http
GET http://localhost:8080/bus/card/transactions/byCardType?year=2025&month=5
Content-Type: application/json
```

**预期响应：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "cardType": "普通卡",
      "transactionCount": 85000,
      "totalAmount": 170000.00,
      "avgAmount": 2.00
    },
    {
      "cardType": "学生卡",
      "transactionCount": 25000,
      "totalAmount": 25000.00,
      "avgAmount": 1.00
    }
  ]
}
```

---

#### 5. 测试换乘率分析

```http
GET http://localhost:8080/bus/card/transactions/transfer/top10?limit=10
Content-Type: application/json
```

**预期响应：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "stopName": "洪家楼",
      "lineName": "K239",
      "totalCount": 5200,
      "transferCount": 2080,
      "transferRate": 40.00
    },
    {
      "stopName": "火车站",
      "lineName": "692",
      "totalCount": 4800,
      "transferCount": 1680,
      "transferRate": 35.00
    }
  ]
}
```

---

#### 6. 测试综合大屏数据

```http
GET http://localhost:8080/bus/test/dashboard?year=2025&month=5
Content-Type: application/json
```

**预期响应：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "stationFlow": {
      "totalFlow": 125800,
      "averageLoadFactor": 0.65,
      "yoyRate": 8.5,
      "flowByLine": [...],
      "topLines": [...]
    },
    "stopsDist": [
      {
        "zoneType": "高校",
        "count": 126
      },
      {
        "zoneType": "居民区",
        "count": 258
      }
    ],
    "cardTransactions": {
      "cardTypeDist": [...],
      "transferTop10": [...],
      "dailyTrend": [...]
    },
    "lines": {
      "allLines": [...]
    }
  }
}
```

---

## 三、自动化测试脚本

### 使用 cURL 测试

创建测试脚本 `test_bus_api.sh`：

```bash
#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== 测试公交数据可视化大屏接口 ==="

# 1. 测试总客流量
echo -e "\n1. 测试总客流量接口"
curl -X GET "${BASE_URL}/bus/station/flow/totalFlow?year=2025&month=5"

# 2. 测试站点地图数据
echo -e "\n2. 测试站点地图数据接口"
curl -X GET "${BASE_URL}/bus/stops/map/all"

# 3. 测试 IC 卡交易统计
echo -e "\n3. 测试 IC 卡交易统计接口"
curl -X GET "${BASE_URL}/bus/card/transactions/byCardType?year=2025&month=5"

# 4. 测试换乘率分析
echo -e "\n4. 测试换乘率分析接口"
curl -X GET "${BASE_URL}/bus/card/transactions/transfer/top10?limit=10"

# 5. 测试综合大屏数据
echo -e "\n5. 测试综合大屏数据接口"
curl -X GET "${BASE_URL}/bus/test/dashboard?year=2025&month=5"

echo -e "\n=== 测试完成 ==="
```

---

## 四、前端集成示例

### Vue3 + Axios 调用示例

```javascript
// api/busViz.js
import request from '@/utils/request'

// 获取总客流量
export function getTotalFlow(year, month) {
  return request({
    url: '/bus/station/flow/totalFlow',
    method: 'get',
    params: { year, month }
  })
}

// 获取 GIS 地图数据
export function getGisMapData() {
  return request({
    url: '/bus/stops/map/all',
    method: 'get'
  })
}

// 获取换乘率 TOP10
export function getTransferTop10(limit = 10) {
  return request({
    url: '/bus/card/transactions/transfer/top10',
    method: 'get',
    params: { limit }
  })
}

// 获取综合大屏数据
export function getDashboardStats(year, month) {
  return request({
    url: '/bus/test/dashboard',
    method: 'get',
    params: { year, month }
  })
}
```

### Vue 组件使用示例

```vue
<template>
  <div class="dashboard">
    <!-- 总客流量卡片 -->
    <el-card>
      <template #header>总客流量</template>
      <div class="stat-value">{{ totalFlow }}</div>
    </el-card>
    
    <!-- GIS 地图 -->
    <div ref="mapContainer" style="height: 600px"></div>
    
    <!-- 换乘率 TOP10 表格 -->
    <el-table :data="transferTop10">
      <el-table-column prop="stopName" label="站点名称" />
      <el-table-column prop="lineName" label="线路" />
      <el-table-column prop="transferRate" label="换乘率 (%)" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getTotalFlow, getGisMapData, getTransferTop10 } from '@/api/busViz'

const totalFlow = ref(0)
const transferTop10 = ref([])

onMounted(async () => {
  // 加载总客流量
  const flowRes = await getTotalFlow(2025, 5)
  totalFlow.value = flowRes.data
  
  // 加载 GIS 地图
  const mapRes = await getGisMapData()
  initEChartsMap(mapRes.data)
  
  // 加载换乘率 TOP10
  const transferRes = await getTransferTop10()
  transferTop10.value = transferRes.data
})

function initEChartsMap(stops) {
  // ECharts 地图初始化逻辑
}
</script>
```

---

## 五、常见问题排查

### 1. 返回空数据

**可能原因：**
- 数据库中没有对应年份/月份的数据
- ClickHouse 从库连接失败
- 表名或字段名不匹配

**解决方案：**
- 检查数据库中是否有数据：`SELECT COUNT(*) FROM fact_station_flow`
- 检查 Druid 配置中的从库连接信息
- 对比实体类和数据库表结构

---

### 2. 接口 404

**可能原因：**
- Controller 未被 Spring 扫描到
- 请求路径拼写错误
- 项目未重新编译

**解决方案：**
- 确认 Controller 类上有 `@RestController` 注解
- 确认请求路径以 `/bus` 开头
- 执行 `mvn clean install` 重新编译

---

### 3. 数据库连接失败

**可能原因：**
- ClickHouse 服务未启动
- 防火墙阻止连接
- 用户名密码错误

**解决方案：**
- 检查 ClickHouse 服务状态
- 确认端口（默认 8123）可访问
- 检查 `application-druid.yml` 配置

---

## 六、性能优化建议

1. **添加 Redis 缓存**：对高频访问的聚合数据进行缓存
   - 实时数据：30 秒过期
   - 小时数据：1 小时过期
   - 日数据：1 天过期

2. **数据库索引优化**：
   - `fact_station_flow(stat_time, line_name, stop_name)` 联合索引
   - `fact_card_transactions(tx_time, card_type, stop_name)` 联合索引

3. **分页查询**：对大数据量列表接口添加分页参数

4. **异步加载**：前端采用组件懒加载，先加载核心图表

---

## 七、后续扩展建议

1. **增加班次维度接口**（DimSchedules）
   - 班次统计
   - 班次作废率分析
   - 运力匹配度分析

2. **增加公交专用道接口**（DimBusLanes）
   - 专用道覆盖分析
   - GIS 地图展示

3. **增加路线站点详情接口**（DimRouteStationDetails）
   - 站点间距分析
   - 首末班车匹配度

4. **导出功能**：支持 Excel 格式导出统计数据

5. **预警功能**：对满载率超标的线路进行预警

---

## 八、联系方式

如有问题，请联系开发团队或查看若依框架官方文档。
