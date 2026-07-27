<template>
  <main class="mall-operations-screen">
    <header class="screen-header">
      <div class="screen-header__brand">
        <span class="brand-mark" aria-hidden="true"></span>
        <div>
          <p>QIANSHAN SELECTED</p>
          <h1>商城运营态势大屏</h1>
        </div>
      </div>
      <div class="screen-header__status">
        <span class="live-indicator"><i></i>数据实时汇总</span>
        <time>{{ currentTime }}</time>
        <el-button :icon="Refresh" :loading="loading" circle title="刷新经营数据" aria-label="刷新经营数据" @click="loadDashboard" />
      </div>
    </header>

    <el-alert v-if="error" class="screen-alert" type="error" :title="error" show-icon :closable="false" />

    <section v-if="canViewDashboard" v-loading="loading" class="screen-content">
      <div class="signal-strip" aria-label="商城核心经营指标">
        <article v-for="metric in metrics" :key="metric.label" class="signal-item">
          <div class="signal-item__icon" :class="metric.tone"><el-icon><component :is="metric.icon" /></el-icon></div>
          <div>
            <span>{{ metric.label }}</span>
            <strong>{{ metric.value }}</strong>
          </div>
          <i class="signal-item__rail" :class="metric.tone"></i>
        </article>
      </div>

      <div class="screen-matrix">
        <section class="screen-panel trend-panel">
          <div class="panel-heading">
            <div>
              <span class="panel-heading__code">GMV TREND</span>
              <h2>近 7 日成交趋势</h2>
            </div>
            <div class="panel-heading__stat"><strong>{{ trendOrderCount }}</strong><span>支付订单</span></div>
          </div>
          <el-empty v-if="!dashboard.salesTrend.length && !loading" description="暂无成交数据" :image-size="76" />
          <div v-else ref="trendChart" class="screen-chart trend-chart" aria-label="近七日成交趋势图"></div>
        </section>

        <section class="screen-panel focus-panel">
          <div class="panel-heading">
            <div>
              <span class="panel-heading__code">OPERATION FOCUS</span>
              <h2>运营焦点</h2>
            </div>
          </div>
          <div ref="focusChart" class="screen-chart focus-chart" aria-label="运营焦点分布图"></div>
          <div class="focus-legend">
            <span v-for="focus in focusItems" :key="focus.label"><i :style="{ background: focus.color }"></i>{{ focus.label }}<strong>{{ focus.value }}</strong></span>
          </div>
        </section>

        <section class="screen-panel todo-panel">
          <div class="panel-heading">
            <div>
              <span class="panel-heading__code">ACTION QUEUE</span>
              <h2>待办处置队列</h2>
            </div>
            <router-link to="/shop/order" class="panel-link">订单中心<el-icon><ArrowRight /></el-icon></router-link>
          </div>
          <router-link v-for="todo in todos" :key="todo.label" :to="todo.path" class="queue-item">
            <span class="queue-item__status" :class="todo.tone"></span>
            <span>{{ todo.label }}</span>
            <strong>{{ todo.value }}</strong>
            <el-icon><ArrowRight /></el-icon>
          </router-link>
        </section>

        <section class="screen-panel hot-panel">
          <div class="panel-heading">
            <div>
              <span class="panel-heading__code">TOP SELLING SKU</span>
              <h2>近 30 日热销 SKU</h2>
            </div>
            <router-link to="/shop/product" class="panel-link">商品管理<el-icon><ArrowRight /></el-icon></router-link>
          </div>
          <el-table :data="dashboard.hotSkus" size="small" empty-text="暂无销售数据">
            <el-table-column type="index" label="#" width="54" />
            <el-table-column prop="productName" label="商品" min-width="180" show-overflow-tooltip />
            <el-table-column prop="skuName" label="SKU 规格" min-width="130" show-overflow-tooltip />
            <el-table-column prop="salesQuantity" label="销量" width="88" align="right" />
            <el-table-column label="成交额" width="128" align="right"><template #default="scope">{{ formatAmount(scope.row.paidAmount) }}</template></el-table-column>
          </el-table>
        </section>

        <section class="screen-panel order-panel">
          <div class="panel-heading">
            <div>
              <span class="panel-heading__code">ORDER VOLUME</span>
              <h2>近 7 日支付订单</h2>
            </div>
            <div class="panel-heading__stat"><strong>{{ trendOrderCount }}</strong><span>7 日累计</span></div>
          </div>
          <el-empty v-if="!dashboard.salesTrend.length && !loading" description="暂无订单数据" :image-size="66" />
          <div v-else ref="orderChart" class="screen-chart analysis-chart" aria-label="近七日支付订单柱状图"></div>
        </section>

        <section class="screen-panel unit-price-panel">
          <div class="panel-heading">
            <div>
              <span class="panel-heading__code">AVERAGE ORDER VALUE</span>
              <h2>近 7 日客单价</h2>
            </div>
            <div class="panel-heading__stat"><strong>{{ formatAmount(averageOrderValue) }}</strong><span>7 日均值</span></div>
          </div>
          <el-empty v-if="!dashboard.salesTrend.length && !loading" description="暂无客单价数据" :image-size="66" />
          <div v-else ref="unitPriceChart" class="screen-chart analysis-chart" aria-label="近七日客单价走势图"></div>
        </section>

        <section class="screen-panel rank-panel">
          <div class="panel-heading">
            <div>
              <span class="panel-heading__code">SKU REVENUE RANKING</span>
              <h2>热销商品成交额排行</h2>
            </div>
          </div>
          <el-empty v-if="!dashboard.hotSkus.length && !loading" description="暂无商品销售数据" :image-size="66" />
          <div v-else ref="hotSkuChart" class="screen-chart analysis-chart" aria-label="热销商品成交额排行图"></div>
        </section>
      </div>

      <nav class="operation-dock" aria-label="商城管理快捷入口">
        <span class="operation-dock__label">商城管理</span>
        <router-link v-for="entry in quickEntries" :key="entry.path" v-hasPermi="[entry.permission]" :to="entry.path" class="dock-entry">
          <el-icon><component :is="entry.icon" /></el-icon>
          <span>{{ entry.label }}</span>
        </router-link>
      </nav>
    </section>

    <el-empty v-else class="permission-empty" description="暂无商城经营总览权限" :image-size="96" />
  </main>
</template>

<script setup name="Index">
import { computed, nextTick, onBeforeUnmount, onMounted, reactive, ref } from 'vue'
import { ArrowRight, CollectionTag, DataAnalysis, Goods, Picture, Refresh, Tickets, User } from '@element-plus/icons-vue'
import echarts from '@/utils/echarts'
import { shopApi } from '@/api/shop'
import useUserStore from '@/store/modules/user'

const userStore = useUserStore()
const canViewDashboard = userStore.permissions.includes('*:*:*') || userStore.permissions.includes('shop:dashboard:query')
const loading = ref(false)
const error = ref('')
const currentTime = ref('')
const trendChart = ref()
const focusChart = ref()
const orderChart = ref()
const unitPriceChart = ref()
const hotSkuChart = ref()
let trendChartInstance
let focusChartInstance
let orderChartInstance
let unitPriceChartInstance
let hotSkuChartInstance
let clockTimer

const dashboard = reactive({
  todayPaidOrderCount: 0,
  todayPaidAmount: 0,
  pendingShipmentCount: 0,
  todayNewMemberCount: 0,
  pendingPaymentCount: 0,
  lowStockSkuCount: 0,
  salesTrend: [],
  hotSkus: []
})

const quickEntries = [
  { label: '商品', path: '/shop/product', permission: 'shop:product:list', icon: Goods },
  { label: '分类', path: '/shop/category', permission: 'shop:category:list', icon: CollectionTag },
  { label: '订单', path: '/shop/order', permission: 'shop:order:list', icon: Tickets },
  { label: '会员', path: '/shop/member', permission: 'shop:member:list', icon: User },
  { label: 'Banner', path: '/shop/banner', permission: 'shop:banner:list', icon: Picture },
  { label: '优惠券', path: '/shop/coupon', permission: 'shop:coupon:list', icon: DataAnalysis }
]

const metrics = computed(() => [
  { label: '今日已支付订单', value: dashboard.todayPaidOrderCount, icon: Tickets, tone: 'lime' },
  { label: '今日成交金额', value: formatAmount(dashboard.todayPaidAmount), icon: DataAnalysis, tone: 'coral' },
  { label: '待发货订单', value: dashboard.pendingShipmentCount, icon: Goods, tone: 'gold' },
  { label: '今日新增会员', value: dashboard.todayNewMemberCount, icon: User, tone: 'sky' }
])

const focusItems = computed(() => [
  { label: '待支付', value: dashboard.pendingPaymentCount, color: '#d79e27' },
  { label: '待发货', value: dashboard.pendingShipmentCount, color: '#e5484d' },
  { label: '库存预警', value: dashboard.lowStockSkuCount, color: '#0f9d76' }
])

const todos = computed(() => [
  { label: '待支付订单', value: dashboard.pendingPaymentCount, path: '/shop/order', tone: 'gold' },
  { label: '待发货订单', value: dashboard.pendingShipmentCount, path: '/shop/order', tone: 'coral' },
  { label: '库存预警 SKU', value: dashboard.lowStockSkuCount, path: '/shop/product', tone: 'lime' }
])

const trendOrderCount = computed(() => dashboard.salesTrend.reduce((total, item) => total + Number(item.paidOrderCount || 0), 0))
const averageOrderValue = computed(() => trendOrderCount.value ? dashboard.salesTrend.reduce((total, item) => total + Number(item.paidAmount || 0), 0) / trendOrderCount.value : 0)

function formatAmount(cents) {
  return `¥${(Number(cents || 0) / 100).toFixed(2)}`
}

function updateClock() {
  currentTime.value = new Intl.DateTimeFormat('zh-CN', {
    year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false
  }).format(new Date()).replaceAll('/', '.')
}

function renderTrendChart() {
  if (!trendChart.value || !dashboard.salesTrend.length) return
  trendChartInstance?.dispose()
  trendChartInstance = echarts.init(trendChart.value)
  trendChartInstance.setOption({
    tooltip: { trigger: 'axis', valueFormatter: value => formatAmount(value) },
    grid: { top: 30, right: 20, bottom: 30, left: 62 },
    xAxis: { type: 'category', boundaryGap: false, data: dashboard.salesTrend.map(item => item.day?.slice(5)), axisLine: { lineStyle: { color: '#dbe1e8' } }, axisLabel: { color: '#6c7580' } },
    yAxis: { type: 'value', axisLabel: { color: '#6c7580', formatter: value => `¥${(value / 100).toFixed(0)}` }, splitLine: { lineStyle: { color: '#edf0f3' } } },
    series: [{ name: '成交额', type: 'line', smooth: true, symbolSize: 7, data: dashboard.salesTrend.map(item => item.paidAmount || 0), lineStyle: { color: '#e5484d', width: 3 }, itemStyle: { color: '#f26a6d' }, areaStyle: { color: 'rgba(229, 72, 77, 0.15)' } }]
  })
}

function renderFocusChart() {
  if (!focusChart.value) return
  focusChartInstance?.dispose()
  focusChartInstance = echarts.init(focusChart.value)
  focusChartInstance.setOption({
    color: focusItems.value.map(item => item.color),
    tooltip: { trigger: 'item', valueFormatter: value => `${value} 项` },
    series: [{
      type: 'pie', radius: ['52%', '75%'], center: ['50%', '48%'], avoidLabelOverlap: true,
      label: { show: true, color: '#697380', formatter: '{b}\n{c} 项', fontSize: 11 },
      labelLine: { lineStyle: { color: '#b7c0ca' } },
      data: focusItems.value.map(item => ({ name: item.label, value: item.value }))
    }]
  })
}

function renderOrderChart() {
  if (!orderChart.value || !dashboard.salesTrend.length) return
  orderChartInstance?.dispose()
  orderChartInstance = echarts.init(orderChart.value)
  orderChartInstance.setOption({
    tooltip: { trigger: 'axis', valueFormatter: value => `${value} 单` },
    grid: { top: 24, right: 18, bottom: 30, left: 42 },
    xAxis: { type: 'category', data: dashboard.salesTrend.map(item => item.day?.slice(5)), axisLine: { lineStyle: { color: '#dbe1e8' } }, axisLabel: { color: '#6c7580' } },
    yAxis: { type: 'value', minInterval: 1, axisLabel: { color: '#6c7580' }, splitLine: { lineStyle: { color: '#edf0f3' } } },
    series: [{ name: '支付订单', type: 'bar', barMaxWidth: 26, data: dashboard.salesTrend.map(item => item.paidOrderCount || 0), itemStyle: { color: '#3478f6', borderRadius: [3, 3, 0, 0] } }]
  })
}

function renderUnitPriceChart() {
  if (!unitPriceChart.value || !dashboard.salesTrend.length) return
  unitPriceChartInstance?.dispose()
  unitPriceChartInstance = echarts.init(unitPriceChart.value)
  unitPriceChartInstance.setOption({
    tooltip: { trigger: 'axis', valueFormatter: value => formatAmount(value) },
    grid: { top: 24, right: 18, bottom: 30, left: 58 },
    xAxis: { type: 'category', boundaryGap: false, data: dashboard.salesTrend.map(item => item.day?.slice(5)), axisLine: { lineStyle: { color: '#dbe1e8' } }, axisLabel: { color: '#6c7580' } },
    yAxis: { type: 'value', axisLabel: { color: '#6c7580', formatter: value => `¥${(value / 100).toFixed(0)}` }, splitLine: { lineStyle: { color: '#edf0f3' } } },
    series: [{ name: '客单价', type: 'line', smooth: true, symbolSize: 6, data: dashboard.salesTrend.map(item => {
      const orders = Number(item.paidOrderCount || 0)
      return orders ? Number(item.paidAmount || 0) / orders : 0
    }), lineStyle: { color: '#0f9d76', width: 3 }, itemStyle: { color: '#0f9d76' }, areaStyle: { color: 'rgba(15, 157, 118, 0.12)' } }]
  })
}

function renderHotSkuChart() {
  if (!hotSkuChart.value || !dashboard.hotSkus.length) return
  hotSkuChartInstance?.dispose()
  hotSkuChartInstance = echarts.init(hotSkuChart.value)
  const skus = dashboard.hotSkus.slice(0, 6).reverse()
  hotSkuChartInstance.setOption({
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' }, valueFormatter: value => formatAmount(value) },
    grid: { top: 14, right: 28, bottom: 14, left: 104 },
    xAxis: { type: 'value', axisLabel: { color: '#6c7580', formatter: value => `¥${(value / 100).toFixed(0)}` }, splitLine: { lineStyle: { color: '#edf0f3' } } },
    yAxis: { type: 'category', data: skus.map(item => item.productName), axisLine: { show: false }, axisTick: { show: false }, axisLabel: { color: '#59636f', width: 88, overflow: 'truncate' } },
    series: [{ name: '成交额', type: 'bar', barMaxWidth: 18, data: skus.map(item => item.paidAmount || 0), itemStyle: { color: '#e5484d', borderRadius: [0, 3, 3, 0] } }]
  })
}

async function loadDashboard() {
  loading.value = true
  error.value = ''
  try {
    const response = await shopApi.dashboard()
    Object.assign(dashboard, response.data || {})
    await nextTick()
    renderTrendChart()
    renderFocusChart()
    renderOrderChart()
    renderUnitPriceChart()
    renderHotSkuChart()
  } catch {
    error.value = '经营数据加载失败，请稍后重试'
  } finally {
    loading.value = false
  }
}

function resizeCharts() {
  trendChartInstance?.resize()
  focusChartInstance?.resize()
  orderChartInstance?.resize()
  unitPriceChartInstance?.resize()
  hotSkuChartInstance?.resize()
}

onMounted(() => {
  updateClock()
  clockTimer = window.setInterval(updateClock, 1000)
  if (canViewDashboard) loadDashboard()
  window.addEventListener('resize', resizeCharts)
})

onBeforeUnmount(() => {
  window.clearInterval(clockTimer)
  window.removeEventListener('resize', resizeCharts)
  trendChartInstance?.dispose()
  focusChartInstance?.dispose()
  orderChartInstance?.dispose()
  unitPriceChartInstance?.dispose()
  hotSkuChartInstance?.dispose()
})
</script>

<style scoped lang="scss">
.mall-operations-screen {
  min-height: calc(100vh - 84px);
  padding: 18px 24px 24px;
  background: #f4f6f8;
  color: #20252b;
}
.screen-header, .screen-content, .screen-alert { width: 100%; margin: 0; }
.screen-header {
  display: flex; align-items: center; justify-content: space-between; min-height: 76px; padding: 0 4px 16px; border-bottom: 1px solid #dfe5eb;
  &__brand { display: flex; align-items: center; gap: 12px; }
  &__brand p { margin: 0 0 3px; color: #e5484d; font-size: 10px; font-weight: 700; letter-spacing: 2.3px; }
  &__brand h1 { margin: 0; font-size: 24px; font-weight: 650; letter-spacing: 1px; }
  &__status { display: flex; align-items: center; gap: 16px; color: #6c7580; font-size: 12px; }
  &__status time { font-variant-numeric: tabular-nums; letter-spacing: .6px; }
  :deep(.el-button) { border-color: #d5dce5; background: #fff; color: #e5484d; }
}
.brand-mark { width: 12px; height: 32px; border: 2px solid #e5484d; border-radius: 2px; box-shadow: inset 0 -12px 0 #3478f6; }
.live-indicator { display: inline-flex; align-items: center; gap: 7px; }
.live-indicator i { width: 7px; height: 7px; border-radius: 50%; background: #0f9d76; box-shadow: 0 0 0 4px rgba(15, 157, 118, .12); }
.screen-alert { margin-top: 14px; }
.screen-content { padding-top: 18px; }
.signal-strip { display: grid; grid-template-columns: repeat(4, minmax(0, 1fr)); border: 1px solid #dfe5eb; background: #fff; }
.signal-item {
  position: relative; display: flex; align-items: center; gap: 13px; min-width: 0; padding: 17px 18px; border-right: 1px solid #e8ecf0;
  &:last-child { border-right: 0; }
  &__icon { display: grid; width: 34px; height: 34px; place-items: center; border: 1px solid currentColor; border-radius: 4px; font-size: 17px; }
  span { display: block; color: #727c88; font-size: 12px; }.signal-item strong { display: block; margin-top: 5px; color: #20252b; font-size: 22px; font-variant-numeric: tabular-nums; }
  &__rail { position: absolute; left: 18px; bottom: 0; width: 42px; height: 2px; }
  .lime { color: #0f9d76; background: #e7f6f1; }.coral { color: #e5484d; background: #fff0f0; }.gold { color: #c1830e; background: #fff7e4; }.sky { color: #3478f6; background: #edf4ff; }
  .signal-item__rail.lime { background: #0f9d76; }.signal-item__rail.coral { background: #e5484d; }.signal-item__rail.gold { background: #d79e27; }.signal-item__rail.sky { background: #3478f6; }
}
.screen-matrix { display: grid; grid-template-columns: repeat(12, minmax(0, 1fr)); gap: 12px; margin-top: 12px; }
.screen-panel { border: 1px solid #dfe5eb; background: #fff; overflow: hidden; }
.trend-panel { grid-column: span 7; min-height: 330px; }.focus-panel { grid-column: span 5; min-height: 330px; }.todo-panel { grid-column: span 4; }.hot-panel { grid-column: span 8; }.order-panel, .unit-price-panel, .rank-panel { grid-column: span 4; min-height: 282px; }
.panel-heading { display: flex; justify-content: space-between; align-items: flex-start; min-height: 57px; padding: 17px 18px 0; }
.panel-heading__code { display: block; margin-bottom: 5px; color: #8c96a3; font-size: 10px; letter-spacing: 1.4px; }.panel-heading h2 { margin: 0; color: #20252b; font-size: 16px; font-weight: 600; }
.panel-heading__stat { display: flex; flex-direction: column; text-align: right; }.panel-heading__stat strong { color: #e5484d; font-size: 21px; font-variant-numeric: tabular-nums; }.panel-heading__stat span { color: #7a8490; font-size: 11px; }
.screen-chart { width: 100%; }.trend-chart { height: 254px; }.focus-chart { height: 205px; }.analysis-chart { height: 205px; }
.focus-legend { display: flex; justify-content: center; gap: 15px; padding: 0 12px 14px; }.focus-legend span { display: inline-flex; align-items: center; gap: 5px; color: #6e7782; font-size: 11px; }.focus-legend i { width: 7px; height: 7px; border-radius: 50%; }.focus-legend strong { color: #2a3038; font-weight: 600; }
.panel-link { display: inline-flex; align-items: center; gap: 4px; color: #e5484d; font-size: 12px; text-decoration: none; }
.queue-item { display: grid; grid-template-columns: 8px 1fr auto 16px; align-items: center; gap: 10px; margin: 0 18px; padding: 17px 0; border-top: 1px solid #edf0f3; color: #59636f; text-decoration: none; font-size: 13px; }.queue-item strong { color: #252b33; font-size: 19px; font-variant-numeric: tabular-nums; }.queue-item > .el-icon { color: #9ca6b1; }.queue-item__status { width: 7px; height: 7px; border-radius: 50%; }.queue-item__status.gold { background: #d79e27; }.queue-item__status.coral { background: #e5484d; }.queue-item__status.lime { background: #0f9d76; }
.hot-panel :deep(.el-table) { margin: 10px 18px 14px; width: calc(100% - 36px); --el-table-bg-color: transparent; --el-table-tr-bg-color: transparent; --el-table-header-bg-color: #f7f9fb; --el-table-border-color: #e6ebf0; --el-table-text-color: #4c5662; --el-table-header-text-color: #77818d; --el-table-row-hover-bg-color: #fff5f5; }
.operation-dock { display: flex; align-items: center; gap: 5px; margin-top: 12px; padding: 8px 12px; border: 1px solid #dfe5eb; background: #fff; }.operation-dock__label { margin-right: 10px; color: #7a8490; font-size: 12px; white-space: nowrap; }.dock-entry { display: inline-flex; align-items: center; gap: 6px; padding: 8px 11px; border: 1px solid transparent; border-radius: 4px; color: #4e5864; font-size: 12px; text-decoration: none; }.dock-entry:hover { border-color: #f3b4b7; background: #fff5f5; color: #e5484d; }
.permission-empty { min-height: 360px; color: #68727e; }
@media (max-width: 1100px) { .signal-strip { grid-template-columns: repeat(2, minmax(0, 1fr)); }.signal-item:nth-child(2) { border-right: 0; }.signal-item:nth-child(-n+2) { border-bottom: 1px solid #dfe5eb; }.trend-panel, .focus-panel, .todo-panel, .hot-panel, .order-panel, .unit-price-panel, .rank-panel { grid-column: span 6; }.operation-dock { flex-wrap: wrap; } }
@media (max-width: 760px) { .mall-operations-screen { padding: 14px; }.screen-header { align-items: flex-start; gap: 12px; }.screen-header__status { flex-wrap: wrap; justify-content: end; gap: 9px; }.screen-header__brand h1 { font-size: 19px; }.screen-header__status time { display: none; }.trend-panel, .focus-panel, .todo-panel, .hot-panel, .order-panel, .unit-price-panel, .rank-panel { grid-column: span 12; }.signal-item { padding: 14px; }.signal-item strong { font-size: 18px; }.dock-entry { padding: 7px 8px; }.dock-entry span { display: none; } }
</style>
