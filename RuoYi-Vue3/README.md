# 黔山优选运营管理端

管理端面向农产品商城的日常运营，提供经营总览、商品、订单、用户和营销内容管理，并保留权限配置、日志查询和运行监控等系统管理功能。

## 前端运行

本目录是独立的 Vue 3 前端工程，使用 Vite 5 和 Node.js。建议使用 Node.js 20 LTS 或更高版本。

```bash
# 进入项目目录
cd RuoYi-Vue3

# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 前端访问地址
# http://localhost:5173
```

开发环境中的 `/dev-api` 请求会代理到 `http://localhost:8080`，后端服务请先启动。生产构建使用：

```bash
npm run build:prod
```

页面标题和 API 前缀由 `.env.development`、`.env.staging` 和 `.env.production` 控制。

## 开发排障

- `listen EACCES ...:80`：项目默认端口为 `5173`，不要使用 root 运行 Node.js。
- `ENOSPC: System limit for number of file watchers reached`：Linux 下提高 inotify 配额后重启终端：

  ```bash
  sudo sysctl -w fs.inotify.max_user_instances=1024
  sudo sysctl -w fs.inotify.max_user_watches=1048576
  ```

- `DEP0060 util._extend`：这是 Node.js 24 对旧传递依赖的弃用提示，不影响开发服务器运行。

## 系统功能

1. 经营总览：展示订单、成交额、待发货、新增用户、销售趋势、热销商品和运营待办。
2. 商品管理：维护商品、分类、售价、库存和上下架状态。
3. 订单管理：处理订单履约、退款和售后进度。
4. 用户管理：查询小程序用户、联系方式、订单和收货地址。
5. 营销内容：管理首页 Banner、优惠券和活动入口。
6. 权限配置：维护后台用户、部门、岗位、角色、菜单和数据权限。
7. 系统配置：维护字典、参数、通知公告和定时任务。
8. 运行监控：查询操作日志、登录日志、在线用户、服务、缓存和数据库连接池状态。
