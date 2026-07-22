# 黔山优选商城系统

本系统面向农产品电商经营，包含微信小程序和运营管理端。用户可以浏览、搜索和购买商品，管理购物车、订单与收货地址；运营人员可以查看经营数据，维护商品与库存，处理订单、售后、用户和营销内容。

## 本仓库说明

本仓库包含后端服务、Vue 3 运营管理端和微信小程序端：

| 目录 | 用途 |
| --- | --- |
| `ruoyi-admin` | Spring Boot Web 服务入口 |
| `RuoYi-Vue3` | Vue 3 + Element Plus + Vite 管理端 |
| `RuoYi-wx-app` | 原生微信小程序端 |

## 本地运行

### 环境要求

- JDK 8 或更高版本（项目编译目标为 Java 8）
- Maven 3.6 或更高版本
- Node.js 20 LTS 或更高版本、npm
- MySQL 5.7+，数据库名默认为 `ry_vue`
- Redis 6+（登录和缓存功能需要）

### 1. 启动后端

在仓库根目录执行：

```bash
# 编译所有模块并生成可运行 JAR
mvn package -DskipTests

# 启动 Web 服务
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

后端默认监听 `http://localhost:8080`。也可以在 IDEA 中运行 `com.ruoyi.RuoYiApplication`。

默认数据源来自 `ruoyi-admin/src/main/resources/application-druid.yml`，支持使用环境变量覆盖：

```bash
export MYSQL_URL='jdbc:mysql://localhost:3306/ry_vue?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8'
export MYSQL_USERNAME='root'
export MYSQL_PASSWORD='your-password'
export REDIS_HOST='localhost'
export REDIS_PORT='6379'
```

日志默认写入当前工作目录的 `logs/`，也可以通过 `LOG_PATH` 指定目录。

### 2. 启动 Vue 3 前端

```bash
cd RuoYi-Vue3
npm install
npm run dev
```

前端默认地址为 `http://localhost:5173`，`/dev-api` 请求会代理到后端 `http://localhost:8080`。生产构建使用：

```bash
npm run build:prod
```

### 3. 启动微信小程序

使用微信开发者工具导入 `RuoYi-wx-app`，并在 `app.js` 的 `apiBaseUrl` 中配置可访问的后端地址。开发者工具可以关闭域名校验；生产环境应使用 HTTPS 域名。

### 常见启动问题

- `listen EACCES ...:80`：开发服务器不要绑定 Linux 特权端口，项目默认使用 `5173`。
- `ENOSPC: System limit for number of file watchers reached`：提高 Linux inotify 配额后重新启动终端：

  ```bash
  sudo sysctl -w fs.inotify.max_user_instances=1024
  sudo sysctl -w fs.inotify.max_user_watches=1048576
  ```

- 日志目录创建失败：确认 `LOG_PATH` 指向当前用户可写目录。

## 系统功能

### 微信小程序

1. 商品浏览：展示首页推荐、商品分类、搜索结果和商品详情。
2. 购物车：支持商品加入、数量调整、选中结算和金额汇总。
3. 订单服务：支持订单确认、订单列表和订单状态查询。
4. 用户中心：支持登录、个人信息查看和收货地址管理。

### 运营管理

1. 经营总览：展示订单、成交额、待发货、新增用户、销售趋势和待办事项。
2. 商品管理：维护商品、分类、售价、库存和上下架状态。
3. 订单管理：处理订单履约、退款和售后进度。
4. 用户管理：查询小程序用户、联系方式、订单和收货地址。
5. 营销内容：管理首页 Banner、优惠券和活动入口。
6. 系统管理：维护用户、角色、菜单、字典、参数、通知和任务。
7. 运行监控：查询操作日志、登录日志、在线用户、服务、缓存和数据库连接池状态。
