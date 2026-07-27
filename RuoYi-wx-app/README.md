# 黔山优选微信小程序

这是一个不依赖构建工具的原生微信小程序端，使用 `JavaScript + WXML + WXSS`，包含商品浏览、购物车、订单和个人中心等页面。

## 环境要求

- 微信开发者工具（建议使用稳定版）
- 微信小程序 AppID；当前 `project.config.json` 使用 `touristappid`，仅适合本地预览
- 可访问的后端 API 地址

## 运行

1. 打开微信开发者工具，导入仓库中的 `RuoYi-wx-app` 目录。
2. 在 `utils/config.js` 配置 `develop`、`trial`、`release` 三个环境的 API 域名；该文件按小程序环境自动选择地址。
3. 本地联调默认使用 `http://127.0.0.1:8080`，并在开发者工具中关闭不校验合法域名；真机和生产环境应使用已登记的 HTTPS 域名。
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
- `pages/order/detail`：订单详情、取消和确认收货
- `pages/address/list`：地址管理
- `pages/address/edit`：地址编辑
- `pages/coupon/list`：优惠券领取
- `pages/login`：登录

## 配置文件

- `utils/config.js`：按 develop / trial / release 选择 API 地址
- `app.js`：应用名称和本地登录状态
- `app.json`：页面注册、窗口样式和入口配置
- `project.config.json`：微信开发者工具项目配置

## 联调边界

- 登录需要后端配置 `WECHAT_MINI_APP_ID` 与 `WECHAT_MINI_APP_SECRET`，小程序包不保存任何服务端密钥。
- 先执行并复核 `sql/update/2026-07-27-新增小程序商城配置.sql`，再按实际运费和配送范围调整后台 `sys_config`。
- 当前订单会创建真实的待支付订单并锁定库存。微信支付依赖已关联的小程序 AppID、商户号、证书和公网通知地址；这些凭据尚未提供前不得把订单伪造为已支付。

## 常见问题

- 页面可以打开但请求失败：检查 `apiBaseUrl`，确认后端端口和域名校验配置正确。
- 真机无法请求 localhost：真机不能访问开发机的 `localhost`，请使用同一局域网 IP 或 HTTPS 测试域名。
- 登录状态异常：在开发者工具中清理缓存和本地存储后重新登录。
