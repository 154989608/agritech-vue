<template>
  <div class="screen-container">
    <ScreenHeader sub="URBAN BUS SYSTEM" title="城市公交客流时空演化分析" :currentTime="currentTime" />
    <ScreenModuleNav />

    <!-- 顶部统计卡片 -->
    <div class="stats-row">
      <div class="stat-card" v-for="item in statCards" :key="item.label">
        <div class="stat-icon-wrap">
          <div class="stat-ring"></div>
          <span class="stat-icon">{{ item.icon }}</span>
        </div>
        <div class="stat-body">
          <div class="stat-value" :style="{ color: item.color }">{{ item.value }}</div>
          <div class="stat-label">{{ item.label }}</div>
        </div>
        <div class="stat-trend" :class="item.trend > 0 ? 'up' : 'down'">
          {{ item.trend > 0 ? '▲' : '▼' }} {{ Math.abs(item.trend) }}%
        </div>
      </div>
    </div>

    <!-- 第一行：24小时趋势 + 周对比 -->
    <div class="charts-row1">
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 24小时客流趋势</span>
          <span class="panel-badge">实时更新</span>
        </div>
        <div ref="hourlyChart" class="chart-box" style="height:300px"></div>
      </div>
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 本周 / 上周客流对比</span>
        </div>
        <div ref="weeklyChart" class="chart-box" style="height:300px"></div>
      </div>
    </div>

    <!-- 第二行：OD分析 + 热力图 + 区域分布 -->
    <div class="charts-row2">
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 客流 OD 分析</span>
        </div>
        <div ref="odChart" class="chart-box" style="height:280px"></div>
      </div>
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 客流密度热力图</span>
          <span class="panel-badge">周维度</span>
        </div>
        <div ref="heatmapChart" class="chart-box" style="height:280px"></div>
      </div>
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 区域客流分布</span>
        </div>
        <div ref="zoneChart" class="chart-box" style="height:280px"></div>
      </div>
    </div>
  </div>
</template>

<script setup name="SpaceTimeEvolution">
import { ref, onMounted } from 'vue'
import echarts from '@/utils/echarts'
import ScreenHeader from '../components/ScreenHeader.vue'
import ScreenModuleNav from '../components/ScreenModuleNav.vue'
import { useScreen, darkTheme, formatAxisValue, formatTooltipValue } from '../composables/useScreen'

const { currentTime, initChart } = useScreen()

const hourlyChart = ref(null)
const weeklyChart = ref(null)
const zoneChart = ref(null)
const odChart = ref(null)
const heatmapChart = ref(null)

const statCards = ref([
  { label: '今日总客流', value: '125,800', icon: '👥', color: '#00d4ff', trend: 8.5 },
  { label: '实时在线车辆', value: '856', icon: '🚌', color: '#ff9f43', trend: 3.2 },
  { label: '运营线路数', value: '127', icon: '🛣️', color: '#00e5a0', trend: 0 },
  { label: '平均满载率', value: '78.3%', icon: '📊', color: '#ff6b6b', trend: -2.1 },
])

onMounted(() => {
  // 24小时趋势
  const c1 = initChart(hourlyChart.value)
  if (c1) {
    const hours = Array.from({ length: 24 }, (_, i) => `${String(i).padStart(2, '0')}:00`)
    c1.setOption({
      ...darkTheme,
      tooltip: {
        trigger: 'axis', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff',
        textStyle: { color: '#e0e8ff' },
        axisPointer: { type: 'line', lineStyle: { color: 'rgba(0, 212, 255, 0.28)' } },
        formatter: (params) => `${params[0].axisValueLabel}<br/>客流量：${formatTooltipValue(params[0].value)}`,
      },
      grid: { left: 12, right: 16, top: 30, bottom: 20, containLabel: true },
      xAxis: {
        type: 'category', data: hours,
        axisLine: { lineStyle: { color: '#1a3a5c' } },
        axisTick: { show: false },
        axisLabel: { color: '#6a8caa', fontSize: 11, interval: 2, margin: 12 },
        splitLine: { show: false }
      },
      yAxis: {
        type: 'value', name: '人次', nameTextStyle: { color: '#6a8caa' },
        axisLine: { show: false }, axisTick: { show: false }, splitNumber: 4,
        axisLabel: { color: '#6a8caa', formatter: formatAxisValue, margin: 10 },
        splitLine: { lineStyle: { color: '#0d2240', type: 'dashed' } }
      },
      series: [{
        data: [800,500,350,280,320,600,2800,5200,4800,3200,2800,3100,
          3500,3200,2900,3100,3800,5100,4900,3800,3000,2400,1800,1200],
        type: 'line', smooth: true, symbol: 'circle', symbolSize: 6, showSymbol: false,
        lineStyle: { color: '#00d4ff', width: 2 },
        itemStyle: { color: '#00d4ff', borderColor: '#fff', borderWidth: 1 },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(0,212,255,0.5)' },
            { offset: 1, color: 'rgba(0,212,255,0.02)' }
          ])
        },
        markArea: {
          silent: true,
          data: [
            [{ xAxis: '07:00', itemStyle: { color: 'rgba(255,159,67,0.08)' } }, { xAxis: '09:00' }],
            [{ xAxis: '17:00', itemStyle: { color: 'rgba(255,107,107,0.08)' } }, { xAxis: '19:00' }],
          ]
        },
        markLine: {
          symbol: 'none', lineStyle: { color: '#ff9f43', type: 'dashed' },
          label: { color: '#ff9f43', formatter: '均值' },
          data: [{ type: 'average', name: '均值' }],
        }
      }]
    })
  }

  // 周对比
  const c2 = initChart(weeklyChart.value)
  if (c2) {
    c2.setOption({
      ...darkTheme,
      tooltip: { trigger: 'axis', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' }, axisPointer: { type: 'shadow' } },
      legend: { data: ['本周', '上周'], textStyle: { color: '#a0b4d0' }, top: 0, right: 0 },
      grid: { left: 12, right: 10, top: 36, bottom: 16, containLabel: true },
      xAxis: {
        type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'],
        axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false },
        axisLabel: { color: '#6a8caa', margin: 12 }, splitLine: { show: false }
      },
      yAxis: {
        type: 'value', axisLine: { show: false }, axisTick: { show: false }, splitNumber: 4,
        axisLabel: { color: '#6a8caa', formatter: formatAxisValue, margin: 10 },
        splitLine: { lineStyle: { color: '#0d2240', type: 'dashed' } }
      },
      series: [
        { name: '本周', type: 'bar', barWidth: '35%', data: [125800,118200,132500,128900,145600,168900,142300],
          itemStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:'#00d4ff'},{offset:1,color:'#0050ff'}]), borderRadius: [3,3,0,0] } },
        { name: '上周', type: 'bar', barWidth: '35%', data: [118500,112000,125800,121300,138200,162500,135600],
          itemStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:'#ff9f43'},{offset:1,color:'#e05c00'}]), borderRadius: [3,3,0,0] } }
      ]
    })
  }

  // OD分析
  const c3 = initChart(odChart.value)
  if (c3) {
    const zones = ['高校区','居民区','商业区','工业区','医院区','交通枢纽','景区','行政区']
    c3.setOption({
      ...darkTheme,
      tooltip: { trigger: 'item', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' } },
      legend: { data: ['上车','下车'], textStyle: { color: '#a0b4d0' }, top: 0, right: 0 },
      radar: {
        indicator: zones.map(name => ({ name, max: 10000 })), shape: 'polygon', splitNumber: 4,
        axisName: { color: '#a0b4d0', fontSize: 11 },
        splitLine: { lineStyle: { color: 'rgba(0,212,255,0.15)' } },
        splitArea: { areaStyle: { color: ['rgba(0,212,255,0.03)', 'rgba(0,212,255,0.06)'] } },
        axisLine: { lineStyle: { color: 'rgba(0,212,255,0.2)' } }
      },
      series: [{ type: 'radar', data: [
        { value: [8500,6200,7800,4300,5600,9200,3800,5100], name: '上车', lineStyle: { color: '#00d4ff', width: 2 }, itemStyle: { color: '#00d4ff' }, areaStyle: { color: 'rgba(0,212,255,0.15)' } },
        { value: [7200,7800,6500,3900,6800,8500,4200,4800], name: '下车', lineStyle: { color: '#ff9f43', width: 2 }, itemStyle: { color: '#ff9f43' }, areaStyle: { color: 'rgba(255,159,67,0.15)' } }
      ]}]
    })
  }

  // 热力图
  const c4 = initChart(heatmapChart.value)
  if (c4) {
    const hours = Array.from({ length: 24 }, (_, i) => `${String(i).padStart(2, '0')}:00`)
    const days = ['周一','周二','周三','周四','周五','周六','周日']
    const peakData = (d, h) => {
      let base = Math.random() * 20 + 5
      if (h >= 7 && h <= 9) base += 60 + Math.random() * 30
      else if (h >= 17 && h <= 19) base += 55 + Math.random() * 25
      else if (h >= 11 && h <= 13) base += 25 + Math.random() * 15
      if (d >= 5) base *= 0.7
      return Math.floor(Math.min(base, 100))
    }
    const data = []
    days.forEach((_, d) => hours.forEach((_, h) => data.push([h, d, peakData(d, h)])))
    c4.setOption({
      ...darkTheme,
      tooltip: { position: 'top', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' },
        formatter: p => `${days[p.value[1]]} ${hours[p.value[0]]}<br/>客流强度：${p.value[2]}` },
      grid: { left: 16, right: 20, top: 10, bottom: 42, containLabel: true },
      xAxis: { type: 'category', data: hours, axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', fontSize: 10, interval: 2, margin: 10 }, splitArea: { show: false } },
      yAxis: { type: 'category', data: days, axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', margin: 10 }, splitArea: { show: false } },
      visualMap: { min: 0, max: 100, calculable: true, orient: 'horizontal', left: 'center', bottom: 0, textStyle: { color: '#6a8caa' }, inRange: { color: ['#0a1628','#0d3a6e','#1a6eb5','#00d4ff','#ff9f43','#ff6b6b'] } },
      series: [{ type: 'heatmap', data, label: { show: false }, itemStyle: { borderColor: '#0a0e27', borderWidth: 1 } }]
    })
  }

  // 区域分布
  const c5 = initChart(zoneChart.value)
  if (c5) {
    c5.setOption({
      ...darkTheme,
      tooltip: { trigger: 'item', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' } },
      legend: { orient: 'vertical', right: 5, top: 'center', textStyle: { color: '#a0b4d0', fontSize: 11 } },
      series: [{ type: 'pie', radius: ['45%','70%'], center: ['38%','50%'],
        data: [
          { value: 36, name: '高校区域', itemStyle: { color: '#00d4ff' } },
          { value: 28, name: '居民区域', itemStyle: { color: '#ff9f43' } },
          { value: 22, name: '商业区域', itemStyle: { color: '#00e5a0' } },
          { value: 14, name: '其他区域', itemStyle: { color: '#a78bfa' } },
        ],
        label: { show: false }, itemStyle: { borderColor: '#0a0e27', borderWidth: 2 },
        emphasis: { itemStyle: { shadowBlur: 15, shadowColor: 'rgba(0,212,255,0.5)' } }
      }]
    })
  }
})
</script>

<style lang="scss" scoped>
@import '../components/screen-common.scss';

.stat-card {
  background: linear-gradient(135deg, rgba(0,30,80,0.8) 0%, rgba(0,15,50,0.6) 100%);
  border: 1px solid rgba(0,212,255,0.2);
  border-radius: 6px;
  padding: 14px 18px;
  display: flex;
  align-items: center;
  gap: 14px;
  position: relative;
  overflow: hidden;
  transition: border-color 0.3s;

  &::before {
    content: '';
    position: absolute;
    top: 0; left: 0;
    width: 3px; height: 100%;
    background: linear-gradient(180deg, #00d4ff, transparent);
  }

  &:hover { border-color: rgba(0,212,255,0.5); }

  .stat-icon-wrap {
    position: relative;
    width: 40px; height: 40px;
    display: flex; align-items: center; justify-content: center;
    flex-shrink: 0;

    .stat-ring {
      position: absolute; inset: 0;
      border-radius: 50%;
      border: 1px solid rgba(0,212,255,0.3);
    }
    .stat-icon { font-size: 20px; }
  }

  .stat-body {
    flex: 1;
    .stat-value { font-size: 22px; font-weight: bold; font-family: 'Courier New', monospace; line-height: 1; }
    .stat-label { font-size: 12px; color: #6a8caa; margin-top: 4px; }
  }

  .stat-trend {
    font-size: 12px; font-weight: bold;
    &.up { color: #00e5a0; }
    &.down { color: #ff6b6b; }
  }
}

.charts-row1 { display: grid; grid-template-columns: 2fr 1fr; gap: 12px; }
.charts-row2 { display: grid; grid-template-columns: 1fr 2fr 1fr; gap: 12px; }
</style>
