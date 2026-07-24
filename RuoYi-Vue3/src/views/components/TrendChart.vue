<template>
  <div class="chart-card">
    <div class="card-header">
      <span class="card-title">近7日客流趋势</span>
      <span class="card-badge">实时</span>
    </div>
    <div ref="chartRef" class="mini-chart"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import echarts from '@/utils/echarts'

const chartRef = ref(null)
let chart = null

const formatAxis = (v) => v >= 10000 ? `${(v / 10000).toFixed(1)}万` : Number(v).toLocaleString()
const formatTooltip = (v) => `${Number(v).toLocaleString()} 人次`

onMounted(() => {
  if (!chartRef.value) return
  chart = echarts.init(chartRef.value)
  chart.setOption({
    backgroundColor: 'transparent',
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(10,20,50,0.95)',
      borderColor: '#00d4ff',
      textStyle: { color: '#e0e8ff' },
      axisPointer: { type: 'line', lineStyle: { color: 'rgba(0, 212, 255, 0.25)' } },
      formatter: (params) => `${params[0].axisValueLabel}<br/>客流量：${formatTooltip(params[0].value)}`,
    },
    grid: { left: 10, right: 16, top: 26, bottom: 16, containLabel: true },
    xAxis: {
      type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'],
      axisLine: { lineStyle: { color: 'rgba(0,212,255,0.2)' } },
      axisTick: { show: false },
      axisLabel: { color: '#8a9bb5', fontSize: 12, margin: 12 },
      splitLine: { show: false },
    },
    yAxis: {
      type: 'value', axisLine: { show: false }, axisTick: { show: false }, splitNumber: 4,
      axisLabel: { color: '#8a9bb5', fontSize: 11, formatter: formatAxis, margin: 10 },
      splitLine: { lineStyle: { color: 'rgba(0,212,255,0.08)', type: 'dashed' } },
    },
    series: [{
      data: [118500, 125800, 121300, 132500, 145600, 168900, 142300],
      type: 'line', smooth: true, symbol: 'circle', symbolSize: 7, showSymbol: false,
      lineStyle: { color: '#00d4ff', width: 2.5 },
      itemStyle: { color: '#00d4ff', borderColor: '#fff', borderWidth: 1.5 },
      emphasis: { focus: 'series', scale: true },
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: 'rgba(0,212,255,0.3)' },
          { offset: 1, color: 'rgba(0,212,255,0.02)' },
        ]),
      },
      markPoint: {
        symbol: 'circle', symbolSize: 10,
        label: { color: '#fff', fontSize: 10, formatter: ({ value }) => formatAxis(value) },
        itemStyle: { color: '#ff9f43', borderColor: '#fff', borderWidth: 1 },
        data: [{ type: 'max', name: '峰值' }],
      },
    }],
  })
  window.addEventListener('resize', () => chart?.resize())
})

onBeforeUnmount(() => {
  chart?.dispose()
})
</script>

<style scoped lang="scss">
.chart-card {
  background: #fff; border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.06);
  border: 1px solid rgba(0,0,0,0.05);
  overflow: hidden;
}

.card-header {
  display: flex; align-items: center; justify-content: space-between;
  padding: 14px 18px; border-bottom: 1px solid #f0f2f6;
  .card-title { font-size: 14px; font-weight: bold; color: #2c3e60; }
  .card-badge {
    font-size: 11px; color: #00b87a;
    background: rgba(0,184,122,0.1); border: 1px solid rgba(0,184,122,0.25);
    padding: 1px 8px; border-radius: 10px;
  }
}

.mini-chart { height: 240px; width: 100%; }
</style>
