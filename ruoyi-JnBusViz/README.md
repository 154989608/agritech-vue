# 公交数据可视化大屏 - 后端接口

## 📋 项目简介

本项目基于 **若依框架 (RuoYi-Vue)** 和 **ClickHouse** 数据库，为公交数据可视化大屏提供完整的后端 API 接口支持。

### 核心功能
- ✅ 客流统计分析（按线路/站点/区域/时段）
- ✅ IC 卡交易分析（卡种分布/换乘率/趋势）
- ✅ 站点维度管理（GIS 地图/区域分布）
- ✅ 线路维度管理（线路信息/详情查询）
- ✅ 综合统计数据（大屏展示数据聚合）

---

## 🏗️ 技术架构

| 层级 | 技术栈 | 说明 |
|------|--------|------|
| **后端框架** | Spring Boot 2.x + MyBatis | 若依框架 |
| **数据库** | ClickHouse (从库) + MySQL (主库) | 多数据源配置 |
| **缓存** | Redis (可选) | 性能优化 |
| **前端** | Vue3 + ECharts 5.x + Element Plus | 大屏展示 |

---

## 📁 目录结构

```
ruoyi-JnBusViz/
├── src/main/java/com/ruoyi/
│   ├── controller/          # Controller 层（REST API）
│   │   ├── FactStationFlowController.java    # 客流统计接口
│   │   ├── DimStopsController.java           # 站点维度接口
│   │   ├── FactCardTransactionsController.java # IC 卡交易接口
│   │   ├── DimLinesController.java           # 线路维度接口
│   │   └── BusVizTestController.java         # 综合测试接口
│   ├── service/             # Service 层（业务逻辑）
│   │   ├── impl/
│   ├── mapper/              # Mapper 层（数据访问）
│   └── domain/              # 实体类
├── src/main/resources/
│   └── mapper/              # MyBatis XML 映射文件
├── API 测试文档.md           # 详细接口文档
├── Postman 测试集合.json      # Postman 测试用例
├── 快速启动指南.md           # 新手入门指南
└── 接口开发完成总结.md       # 完整开发总结
```

---

## 🚀 快速开始

### 1. 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- ClickHouse 20.x+
- Redis (可选)

### 2. 数据库配置

编辑 `application-druid.yml`，配置 ClickHouse 从库：

```yaml
spring:
  datasource:
    slave:
      enabled: true
      driverClassName: ru.yandex.clickhouse.ClickHouseDriver
      url: jdbc:clickhouse://localhost:8123/your_database
      username: your_username
      password: your_password
```

### 3. 启动项目

```bash
# 方法 1: IDEA 直接运行 RuoYiApplication.java
# 方法 2: 命令行启动
cd ruoyi-admin
mvn spring-boot:run

# 方法 3: 使用脚本
bin/run.bat  # Windows
```

### 4. 测试接口

#### 浏览器测试
```
http://localhost:8080/bus/station/flow/totalFlow?year=2025&month=5
http://localhost:8080/bus/test/dashboard?year=2025&month=5
```

#### Postman 测试
导入 `Postman 测试集合.json` 到 Postman，设置 `baseUrl = http://localhost:8080`

#### 快速测试脚本
```bash
cd ruoyi-JnBusViz
test-api.bat  # Windows
```

---

## 📊 接口分类

### 1. 客流统计接口 (`/bus/station/flow/*`)

| 接口 | 说明 | 参数 |
|------|------|------|
| `GET /totalFlow` | 获取总客流量 | year, month |
| `GET /byLine` | 按线路统计 | year, month |
| `GET /byStation` | 按站点统计 | year, month, limit |
| `GET /byZone` | 按区域统计 | year, month |
| `GET /trend` | 客流趋势 | days |
| `GET /dashboard` | 综合统计 | year, month |

**示例响应：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "lineName": "K239",
    "totalFlow": 45200,
    "avgLoadFactor": 0.75,
    "stationCount": 28
  }
}
```

---

### 2. 站点维度接口 (`/bus/stops/*`)

| 接口 | 说明 | 参数 |
|------|------|------|
| `GET /list` | 查询站点列表 | stopName, zoneType |
| `GET /map/all` | GIS 地图数据 | 无 |
| `GET /zoneType/count` | 区域类型统计 | 无 |
| `GET /location` | 站点经纬度 | stopName |

**示例响应：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "stopName": "商职学院",
      "longitude": 116.990553,
      "latitude": 36.666514,
      "zoneType": "高校"
    }
  ]
}
```

---

### 3. IC 卡交易接口 (`/bus/card/transactions/*`)

| 接口 | 说明 | 参数 |
|------|------|------|
| `GET /byCardType` | 按卡种统计 | year, month |
| `GET /transfer/top10` | 换乘率 TOP10 | limit |
| `GET /trend/daily` | 日均交易量趋势 | days |

**示例响应：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "cardType": "普通卡",
      "transactionCount": 85000,
      "totalAmount": 170000.00
    },
    {
      "cardType": "学生卡",
      "transactionCount": 25000,
      "totalAmount": 25000.00
    }
  ]
}
```

---

### 4. 线路维度接口 (`/bus/lines/*`)

| 接口 | 说明 | 参数 |
|------|------|------|
| `GET /list` | 查询线路列表 | lineName |
| `GET /all` | 所有线路信息 | 无 |
| `GET /detail` | 线路详情 | lineName |

---

### 5. 综合测试接口 (`/bus/test/*`)

| 接口 | 说明 | 参数 |
|------|------|------|
| `GET /dashboard` | 大屏综合数据 | year, month |
| `GET /gis/map` | GIS 地图数据 | 无 |
| `GET /transfer/analysis` | 换乘率分析 | limit |

---

## 💻 前端集成示例

### Vue3 + Axios 调用

```javascript
// api/busViz.js
import request from '@/utils/request'

export function getDashboardStats(year, month) {
  return request({
    url: '/bus/test/dashboard',
    method: 'get',
    params: { year, month }
  })
}

export function getGisMapData() {
  return request({
    url: '/bus/stops/map/all',
    method: 'get'
  })
}
```

### Vue 组件使用

```vue
<script setup>
import { ref, onMounted } from 'vue'
import { getDashboardStats } from '@/api/busViz'

const dashboardData = ref({})

onMounted(async () => {
  const res = await getDashboardStats(2025, 5)
  dashboardData.value = res.data.stationFlow
})
</script>

<template>
  <div>
    <h1>总客流量：{{ dashboardData.totalFlow }}</h1>
  </div>
</template>
```

---

## 📦 数据库表

### 事实表
- `fact_station_flow` - 站点客流数据
- `fact_card_transactions` - IC 卡交易数据

### 维度表
- `dim_stops` - 站点维度
- `dim_lines` - 线路维度
- `dim_route_station_details` - 路线站点详情
- `dim_schedules` - 班次维度
- `dim_bus_lanes` - 公交专用道
- `dim_ic_points` - IC 服务网点

---

## 🔧 性能优化

### Redis 缓存示例

```java
@Service
public class FactStationFlowServiceImpl implements FactStationFlowService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public Long getTotalFlow(Integer year, Integer month) {
        String key = "bus:flow:total:" + year + "-" + month;
        
        // 先查缓存
        Long cached = (Long) redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return cached;
        }
        
        // 查数据库并存入缓存
        Long result = factStationFlowMapper.getTotalFlow(year, month);
        redisTemplate.opsForValue().set(key, result, 30, TimeUnit.MINUTES);
        
        return result;
    }
}
```

---

## 📖 文档索引

| 文档 | 说明 |
|------|------|
| [API 测试文档.md](./API 测试文档.md) | 详细接口文档和测试用例 |
| [快速启动指南.md](./快速启动指南.md) | 新手入门和故障排查 |
| [接口开发完成总结.md](./接口开发完成总结.md) | 完整开发总结和规划 |
| [Postman 测试集合.json](./Postman 测试集合.json) | Postman 测试用例集合 |

---

## ❓ 常见问题

### Q: 接口返回 404？
**A:** 确认项目已启动成功，检查端口是否为 8080

### Q: 返回空数据？
**A:** 检查 ClickHouse 是否有数据，验证数据库连接配置

### Q: 如何切换数据源？
**A:** 使用 `@DataSource(value = DataSourceType.SLAVE)` 注解

---

## 📝 开发计划

### 已完成 ✅
- 客流统计模块
- 站点维度模块
- IC 卡交易模块
- 线路维度模块
- 综合测试模块

### 待开发 🚧
- 班次维度接口
- 公交专用道接口
- 路线站点详情接口
- IC 服务网点接口
- Redis 缓存层
- 数据导出功能

---

## 📄 许可证

本项目基于若依框架开发，遵循若依框架的开源协议。

---

## 👥 联系方式

- 若依官方文档：http://doc.ruoyi.vip/
- 项目位置：`ruoyi-JnBusViz/`

---

**最后更新**: 2025 年 3 月 8 日  
**版本**: v1.0
