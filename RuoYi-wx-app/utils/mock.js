const dashboardStats = [
  { label: '今日订单', value: '86', trend: '+12' },
  { label: '待发货', value: '24', trend: '+5' },
  { label: '新访客', value: '1.8k', trend: 'new' }
]

const homePromos = [
  { title: '黄精原产地直供', desc: '九蒸九晒，山里现采现发', button: '立即选购', tint: 'green' },
  { title: '山货礼盒专区', desc: '送礼自用都合适', button: '查看礼盒', tint: 'orange' },
  { title: '新人优惠券', desc: '新客专享，满减可领', button: '立即领取', tint: 'rose' }
]

const homeCategories = [
  { id: 'huangjing', name: '黄精专区', desc: '滋补山货', icon: '黄', color: '#d9f99d' },
  { id: 'shanhuo', name: '山货干货', desc: '土特产', icon: '山', color: '#fef3c7' },
  { id: 'grain', name: '粮油调味', desc: '厨房必备', icon: '粮', color: '#dbeafe' },
  { id: 'gift', name: '礼盒专区', desc: '送礼体面', icon: '礼', color: '#ffe4e6' }
]

const featuredStore = {
  name: '黔山黄精·公司经营',
  sold: '45082',
  minOrder: '起订 ¥39.80',
  badge: '九蒸九晒',
  tip: '公司经营，山里原料直发',
  products: [
    { id: 'p1', title: '黄精切片', price: 39.8, bg: 'linear-gradient(135deg, #b7e4a5 0%, #ecfccb 100%)' },
    { id: 'p2', title: '黄精茶', price: 29.9, bg: 'linear-gradient(135deg, #8fd38b 0%, #d9f99d 100%)' },
    { id: 'p3', title: '黄精粉', price: 49.8, bg: 'linear-gradient(135deg, #a3e635 0%, #ecfccb 100%)' },
    { id: 'p4', title: '黄精干', price: 36.8, bg: 'linear-gradient(135deg, #cffafe 0%, #ecfccb 100%)' }
  ]
}

const quickActions = [
  { title: '我的订单', desc: '查看待付款、待发货和售后', path: '/pages/order/list/index' },
  { title: '地址管理', desc: '维护收货地址', path: '/pages/address/list/index' },
  { title: '登录入口', desc: '演示账号登录状态', path: '/pages/login/index' }
]

const updates = [
  { title: '首页改版', desc: '增加了黄精专区和山货礼盒入口' },
  { title: '购物车联动', desc: '已接入本地选中状态和结算结构' },
  { title: '详情页骨架', desc: '支持商品图、规格和推荐列表' }
]

const workItems = [
  { title: '用户反馈工单', status: '待处理', owner: '客服组', progress: 20 },
  { title: '巡检异常确认', status: '处理中', owner: '运维组', progress: 65 },
  { title: '月报数据整理', status: '待审核', owner: '统计组', progress: 80 }
]

const categoryTree = [
  { id: 'huangjing', name: '黄精专区', bg: 'linear-gradient(135deg, #d9f99d 0%, #f0fdf4 100%)', children: ['p1', 'p2', 'p3', 'p4'] },
  { id: 'shanhuo', name: '山货干货', bg: 'linear-gradient(135deg, #fef3c7 0%, #fff7ed 100%)', children: ['p5', 'p6'] },
  { id: 'grain', name: '粮油调味', bg: 'linear-gradient(135deg, #dbeafe 0%, #eff6ff 100%)', children: ['p7'] },
  { id: 'gift', name: '礼盒专区', bg: 'linear-gradient(135deg, #ffe4e6 0%, #fff1f2 100%)', children: ['p8'] }
]

const featuredBanners = [
  { title: '黄精原产地直供', desc: '九蒸九晒，山里现采现发', tag: '基地源头直供', bg: 'linear-gradient(135deg, #c8f29a 0%, #eaffd7 100%)' },
  { title: '山货礼盒专区', desc: '送礼自用两相宜', tag: '立即送达', bg: 'linear-gradient(135deg, #e2f7c3 0%, #f8ffe8 100%)' }
]

const products = [
  { id: 'p1', title: '黄精切片', desc: '九蒸九晒，冲泡煲汤都合适', price: 39.8, originPrice: 49.9, sales: 12865, tag: '热卖', categoryId: 'huangjing', bg: 'linear-gradient(135deg, #b7e4a5 0%, #ecfccb 100%)', highlights: ['九蒸九晒', '原产地', '无硫熏'] },
  { id: 'p2', title: '黄精茶', desc: '日常代茶饮，香气柔和', price: 29.9, originPrice: 36.8, sales: 9860, tag: '新品', categoryId: 'huangjing', bg: 'linear-gradient(135deg, #8fd38b 0%, #d9f99d 100%)', highlights: ['袋泡', '便携', '养生'] },
  { id: 'p3', title: '黄精粉', desc: '细腻易冲，早餐更方便', price: 49.8, originPrice: 59.8, sales: 7612, tag: '推荐', categoryId: 'huangjing', bg: 'linear-gradient(135deg, #a3e635 0%, #ecfccb 100%)', highlights: ['速溶', '冲泡', '礼盒'] },
  { id: 'p4', title: '黄精干', desc: '干货入汤，口感饱满', price: 36.8, originPrice: 44.8, sales: 6942, tag: '爆款', categoryId: 'huangjing', bg: 'linear-gradient(135deg, #cffafe 0%, #ecfccb 100%)', highlights: ['煲汤', '干货', '每日更新'] },
  { id: 'p5', title: '土蜂蜜', desc: '高山花蜜，香甜浓郁', price: 32.8, originPrice: 39.8, sales: 5210, tag: '热卖', categoryId: 'shanhuo', bg: 'linear-gradient(135deg, #d9f99d 0%, #fef3c7 100%)', highlights: ['高山', '纯蜜', '包邮'] },
  { id: 'p6', title: '山核桃仁', desc: '香脆饱满，追剧零食', price: 26.8, originPrice: 32.8, sales: 4188, tag: '秒杀', categoryId: 'shanhuo', bg: 'linear-gradient(135deg, #bbf7d0 0%, #ecfccb 100%)', highlights: ['零食', '香脆', '冷藏'] },
  { id: 'p7', title: '菌菇礼盒', desc: '山珍干货，送礼体面', price: 58.0, originPrice: 68.0, sales: 3021, tag: '精选', categoryId: 'grain', bg: 'linear-gradient(135deg, #c4b5fd 0%, #f5f3ff 100%)', highlights: ['礼盒', '山珍', '净含量'] },
  { id: 'p8', title: '红枣枸杞', desc: '泡茶煲汤都实用', price: 19.8, originPrice: 24.8, sales: 2576, tag: '新品', categoryId: 'gift', bg: 'linear-gradient(135deg, #bbf7d0 0%, #dcfce7 100%)', highlights: ['干货', '养生', '礼盒'] }
]

const cartItems = [
  { id: 'p1', title: '黄精切片', sku: '500g / 九蒸九晒', price: 39.8, count: 2, selected: true, bg: products[0].bg },
  { id: 'p2', title: '黄精茶', sku: '1盒 / 日常代茶饮', price: 29.9, count: 1, selected: true, bg: products[1].bg }
]

const orders = [
  { sn: '202607180001', status: '待付款', statusText: '等待支付', total: 109.5, count: 3, items: [products[0], products[1]] },
  { sn: '202607170876', status: '待发货', statusText: '已下单', total: 88.8, count: 2, items: [products[2], products[5]] },
  { sn: '202607160543', status: '已完成', statusText: '交易成功', total: 78.6, count: 2, items: [products[6], products[7]] }
]

const addresses = [
  { id: 'a1', name: '张三', phone: '138****0001', detail: '云南省普洱市宁洱县深山村 8 号', isDefault: true },
  { id: 'a2', name: '李四', phone: '139****0008', detail: '贵州省黔西南州山货合作社 12 号', isDefault: false }
]

const profileMenus = [
  { title: '我的订单', desc: '查看全部订单和售后进度', action: 'order' },
  { title: '地址管理', desc: '新增、编辑收货信息', action: 'address' },
  { title: '优惠券', desc: '查看可用优惠券', action: 'coupon' },
  { title: '联系客服', desc: '在线咨询和订单协助', action: 'service' },
  { title: '退出登录', desc: '清理本地登录状态', action: 'logout' }
]

const searchHistory = ['黄精', '山货', '礼盒', '土蜂蜜']

function findProductById(id) {
  return products.find((item) => item.id === id) || products[0]
}

function findCategoryById(id) {
  return categoryTree.find((item) => item.id === id) || categoryTree[0]
}

function getProductsByCategory(categoryId) {
  return products.filter((item) => item.categoryId === categoryId)
}

function searchProducts(keyword) {
  const value = String(keyword || '').trim().toLowerCase()
  if (!value) return products

  return products.filter((item) => [item.title, item.desc, item.tag, ...(item.highlights || [])]
    .join(' ')
    .toLowerCase()
    .includes(value))
}

module.exports = {
  dashboardStats, homePromos, homeCategories, featuredStore, quickActions, updates, workItems,
  categoryTree, featuredBanners, products, cartItems, orders, addresses, profileMenus, searchHistory,
  findProductById, findCategoryById, getProductsByCategory, searchProducts
}
