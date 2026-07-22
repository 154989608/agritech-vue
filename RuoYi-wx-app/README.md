# 黔山优选微信小程序

这是一个不依赖构建工具的原生微信小程序端，使用 `JavaScript + WXML + WXSS`，包含商品浏览、购物车、订单和个人中心等页面。

## 环境要求

- 微信开发者工具（建议使用稳定版）
- 微信小程序 AppID；当前 `project.config.json` 使用 `touristappid`，仅适合本地预览
- 可访问的后端 API 地址

## 运行

1. 打开微信开发者工具，导入仓库中的 `RuoYi-wx-app` 目录。
2. 在 `app.js` 的 `globalData.apiBaseUrl` 中配置后端地址。当前值是占位地址 `https://example.com/api`，必须替换后才能访问真实 API。
3. 本地联调时可配置 `http://localhost:8080`，并在开发者工具中关闭不校验合法域名；真机和生产环境应使用 HTTPS 域名。
4. 编译并预览 `pages/home/index`。

请求封装位于 `utils/request.js`，会自动把相对路径拼接到 `apiBaseUrl`，并携带本地保存的 token。

## 页面结构

- `pages/home`：首页
- `pages/category`：分类
- `pages/cart`：购物车
- `pages/profile`：个人中心
- `pages/search`：搜索
- `pages/product/detail`：商品详情
- `pages/order/list`：订单列表
- `pages/order/confirm`：订单确认
- `pages/address/list`：地址管理
- `pages/login`：登录

## 配置文件

- `app.js`：应用名称、API 地址和本地登录状态
- `app.json`：页面注册、窗口样式和入口配置
- `project.config.json`：微信开发者工具项目配置
- `utils/mock.js`：本地演示数据，未连接后端时可用于页面预览

## 常见问题

- 页面可以打开但请求失败：检查 `apiBaseUrl`，确认后端端口和域名校验配置正确。
- 真机无法请求 localhost：真机不能访问开发机的 `localhost`，请使用同一局域网 IP 或 HTTPS 测试域名。
- 登录状态异常：在开发者工具中清理缓存和本地存储后重新登录。
