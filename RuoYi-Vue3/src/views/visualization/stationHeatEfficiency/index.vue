<template>
  <div class="screen-container">
    <ScreenHeader sub="STATION HEAT EFFICIENCY" title="城市公交站点热力效能分析" :currentTime="currentTime" />
    <ScreenModuleNav />

    <!-- 统计卡片行 -->
    <div class="stats-row">
      <div class="stat-card" v-for="item in statCards" :key="item.label">
        <div class="stat-icon-wrap">
          <span class="stat-icon">{{ item.icon }}</span>
        </div>
        <div class="stat-body">
          <div class="stat-value" :style="{ color: item.color }">{{ item.value }}</div>
          <div class="stat-label">{{ item.label }}</div>
        </div>
        <div class="stat-bar-wrap">
          <div class="stat-bar" :style="{ width: item.percent + '%', background: item.color }"></div>
        </div>
      </div>
    </div>

    <!-- 主图区 -->
    <div class="charts-main">
      <div class="chart-panel panel-large">
        <div class="panel-header">
          <span class="panel-title">◆ 站点 × 线路客流热力分布</span>
          <span class="panel-badge">多维分析</span>
        </div>
        <div ref="stationHeatmap" class="chart-box"></div>
      </div>
      <div class="chart-panel panel-small">
        <div class="panel-header">
          <span class="panel-title">◆ TOP10 热门站点</span>
        </div>
        <div ref="topStationsChart" class="chart-box"></div>
      </div>
    </div>

    <!-- 中图区 -->
    <div class="charts-mid">
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 站点效能评分分布</span>
        </div>
        <div ref="efficiencyChart" class="chart-box"></div>
      </div>
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 时段客流分布</span>
        </div>
        <div ref="periodChart" class="chart-box"></div>
      </div>
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 站点类型分布</span>
        </div>
        <div ref="zoneTypeChart" class="chart-box"></div>
      </div>
    </div>

    <!-- 底部表格 -->
    <div class="table-panel">
      <div class="panel-header">
        <span class="panel-title">◆ 站点效能详情排行</span>
        <span class="panel-badge">实时数据</span>
      </div>
      <div class="table-wrap">
        <table class="data-table">
          <thead>
            <tr><th>排名</th><th>站点名称</th><th>日均客流</th><th>效能评分</th><th>负荷率</th><th>高峰时段</th><th>等级</th></tr>
          </thead>
          <tbody>
            <tr v-for="(row, i) in stationData" :key="i" :class="{ 'row-highlight': i < 3 }">
              <td><span class="rank-badge" :class="'rank-' + (i + 1)">{{ i + 1 }}</span></td>
              <td class="station-name">{{ row.stationName }}</td>
              <td class="val-blue">{{ row.flow.toLocaleString() }}</td>
              <td>
                <div class="score-bar-wrap">
                  <div class="score-bar" :style="{ width: row.efficiency + '%', background: getScoreColor(row.efficiency) }"></div>
                  <span class="score-text" :style="{ color: getScoreColor(row.efficiency) }">{{ row.efficiency }}</span>
                </div>
              </td>
              <td>
                <div class="load-bar-wrap">
                  <div class="load-bar" :style="{ width: row.load + '%', background: getLoadColor(row.load) }"></div>
                  <span class="load-text">{{ row.load }}%</span>
                </div>
              </td>
              <td class="val-muted">{{ row.peakTime }}</td>
              <td><span class="level-tag" :class="'level-' + row.level">{{ row.level }}</span></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup name="StationHeatEfficiency">
import { ref, onMounted } from 'vue'
import echarts from '@/utils/echarts'
import ScreenHeader from '../components/ScreenHeader.vue'
import ScreenModuleNav from '../components/ScreenModuleNav.vue'
import { useScreen, darkTheme, formatAxisValue } from '../composables/useScreen'

const { currentTime, initChart } = useScreen()

const stationHeatmap = ref(null)
const topStationsChart = ref(null)
const efficiencyChart = ref(null)
const periodChart = ref(null)
const zoneTypeChart = ref(null)

const statCards = ref([
  { label: '全网站点总数', value: '1,234', icon: '📍', color: '#00d4ff', percent: 75 },
  { label: '高客流站点', value: '268', icon: '🔥', color: '#ff6b6b', percent: 22 },
  { label: '日均客流量', value: '45,678', icon: '👥', color: '#ff9f43', percent: 68 },
  { label: '站点覆盖率', value: '92.3%', icon: '📡', color: '#00e5a0', percent: 92 },
])

const stationData = ref([
  { stationName: '商职学院', flow: 18500, efficiency: 95, load: 92, peakTime: '07:00-09:00', level: 'A' },
  { stationName: '洪家楼', flow: 16800, efficiency: 92, load: 88, peakTime: '07:30-09:30', level: 'A' },
  { stationName: '燕子山', flow: 15200, efficiency: 89, load: 85, peakTime: '08:00-10:00', level: 'A' },
  { stationName: '历山路', flow: 13600, efficiency: 86, load: 80, peakTime: '07:00-09:00', level: 'B' },
  { stationName: '泉城路', flow: 12300, efficiency: 84, load: 78, peakTime: '10:00-22:00', level: 'B' },
  { stationName: '山大南路', flow: 11500, efficiency: 81, load: 75, peakTime: '07:30-09:30', level: 'B' },
  { stationName: '解放路', flow: 9800, efficiency: 76, load: 68, peakTime: '08:00-20:00', level: 'B' },
  { stationName: '二环东路', flow: 8600, efficiency: 72, load: 62, peakTime: '07:00-09:00', level: 'C' },
])

const getScoreColor = (v) => v >= 90 ? '#00e5a0' : v >= 80 ? '#00d4ff' : v >= 70 ? '#ff9f43' : '#ff6b6b'
const getLoadColor = (v) => v >= 90 ? '#ff6b6b' : v >= 70 ? '#ff9f43' : '#00e5a0'

onMounted(() => {
  // 站点热力
  const c1 = initChart(stationHeatmap.value)
  if (c1) {
    const zones = ['高校区','居民区','商业区','工业区','交通枢纽','医院区','景区','行政区']
    const lines = ['K1路','K2路','K3路','K5路','K7路','K10路','K12路','101路','102路','103路']
    const data = []
    lines.forEach((_, l) => zones.forEach((_, z) => {
      let v = Math.floor(Math.random() * 60) + 10
      if (z === 0 && l <= 2) v += 30
      if (z === 2 && l >= 5) v += 20
      data.push([z, l, Math.min(v, 100)])
    }))
    c1.setOption({
      ...darkTheme,
      tooltip: { position: 'top', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' },
        formatter: p => `${lines[p.value[1]]} × ${zones[p.value[0]]}<br/>客流强度：${p.value[2]}` },
      grid: { left: 16, right: 20, top: 10, bottom: 55, containLabel: true },
      xAxis: { type: 'category', data: zones, axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', fontSize: 11, interval: 0, margin: 12 }, splitArea: { show: false } },
      yAxis: { type: 'category', data: lines, axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', fontSize: 11, margin: 10 }, splitArea: { show: false } },
      visualMap: { min: 0, max: 100, calculable: true, orient: 'horizontal', left: 'center', bottom: 0, textStyle: { color: '#6a8caa', fontSize: 11 }, inRange: { color: ['#0a1628','#0d3a6e','#1a6eb5','#00d4ff','#ff9f43','#ff6b6b'] } },
      series: [{ type: 'heatmap', data, itemStyle: { borderColor: '#060d1f', borderWidth: 1 } }]
    })
  }

  // TOP10
  const c2 = initChart(topStationsChart.value)
  if (c2) {
    c2.setOption({
      ...darkTheme,
      tooltip: { trigger: 'axis', axisPointer: { type: 'none' }, backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' } },
      grid: { left: 12, right: 50, top: 10, bottom: 12, containLabel: true },
      xAxis: { type: 'value', show: false },
      yAxis: { type: 'category', data: ['二环东路','解放路','山大南路','泉城路','历山路','燕子山','洪家楼','北园大街','经十路','商职学院'], axisLine: { show: false }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', fontSize: 11 } },
      series: [{ type: 'bar', data: [8600,9800,11500,12300,13600,15200,16800,17200,17800,18500], barWidth: 12,
        itemStyle: { color: new echarts.graphic.LinearGradient(0,0,1,0,[{offset:0,color:'#0050ff'},{offset:1,color:'#00d4ff'}]), borderRadius: [0,6,6,0] },
        label: { show: true, position: 'right', color: '#a0b4d0', fontSize: 11, formatter: v => v.value.toLocaleString() }
      }]
    })
  }

  // 效能评分
  const c3 = initChart(efficiencyChart.value)
  if (c3) {
    c3.setOption({
      ...darkTheme,
      tooltip: { trigger: 'item', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' } },
      legend: { orient: 'vertical', right: 5, top: 'center', textStyle: { color: '#a0b4d0', fontSize: 11 } },
      series: [{ type: 'pie', radius: ['45%','68%'], center: ['40%','50%'],
        data: [
          { value: 35, name: 'A级 (≥90)', itemStyle: { color: '#00e5a0' } },
          { value: 40, name: 'B级 (80-89)', itemStyle: { color: '#00d4ff' } },
          { value: 20, name: 'C级 (70-79)', itemStyle: { color: '#ff9f43' } },
          { value: 5, name: 'D级 (<70)', itemStyle: { color: '#ff6b6b' } },
        ],
        label: { show: false }, itemStyle: { borderColor: '#060d1f', borderWidth: 2 },
        emphasis: { itemStyle: { shadowBlur: 15, shadowColor: 'rgba(0,212,255,0.5)' } }
      }]
    })
  }

  // 时段分布
  const c4 = initChart(periodChart.value)
  if (c4) {
    c4.setOption({
      ...darkTheme,
      tooltip: { trigger: 'axis', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' } },
      grid: { left: 12, right: 15, top: 18, bottom: 18, containLabel: true },
      xAxis: { type: 'category', boundaryGap: false, data: ['00:00','04:00','06:00','08:00','10:00','12:00','14:00','16:00','18:00','20:00','22:00'],
        axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', fontSize: 10, margin: 10 }, splitLine: { show: false } },
      yAxis: { type: 'value', axisLine: { show: false }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', formatter: formatAxisValue, margin: 10 }, splitLine: { lineStyle: { color: '#0d2240', type: 'dashed' } } },
      series: [{ data: [800,450,1200,6800,4200,3800,3500,3800,6200,5100,2800], type: 'line', smooth: true,
        lineStyle: { color: '#ff9f43', width: 2 }, itemStyle: { color: '#ff9f43' },
        areaStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:'rgba(255,159,67,0.4)'},{offset:1,color:'rgba(255,159,67,0.02)'}]) }
      }]
    })
  }

  // 站点类型
  const c5 = initChart(zoneTypeChart.value)
  if (c5) {
    c5.setOption({
      ...darkTheme,
      tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' }, backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' } },
      grid: { left: 16, right: 15, top: 15, bottom: 30, containLabel: true },
      xAxis: { type: 'category', data: ['高校区','居民区','商业区','工业区','医院区','交通枢纽'],
        axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', fontSize: 10, margin: 10 }, splitLine: { show: false } },
      yAxis: { type: 'value', name: '站点数', nameTextStyle: { color: '#6a8caa' }, axisLine: { show: false }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', formatter: formatAxisValue, margin: 10 }, splitLine: { lineStyle: { color: '#0d2240', type: 'dashed' } } },
      series: [{ type: 'bar', barWidth: '50%',
        data: [
          { value: 312, itemStyle: { color: '#00d4ff' } },
          { value: 428, itemStyle: { color: '#a78bfa' } },
          { value: 256, itemStyle: { color: '#ff9f43' } },
          { value: 128, itemStyle: { color: '#00e5a0' } },
          { value: 64, itemStyle: { color: '#ff6b6b' } },
          { value: 46, itemStyle: { color: '#f9d423' } },
        ],
        itemStyle: { borderRadius: [3,3,0,0] }
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
  padding: 12px 16px;
  display: flex;
  align-items: center;
  gap: 12px;
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: 0; left: 0;
    width: 3px; height: 100%;
    background: linear-gradient(180deg, #00d4ff, transparent);
  }

  .stat-icon-wrap {
    width: 38px; height: 38px;
    display: flex; align-items: center; justify-content: center;
    flex-shrink: 0;
    .stat-icon { font-size: 22px; }
  }

  .stat-body {
    flex: 1;
    .stat-value { font-size: 20px; font-weight: bold; font-family: 'Courier New', monospace; line-height: 1; }
    .stat-label { font-size: 12px; color: #6a8caa; margin-top: 4px; }
  }

  .stat-bar-wrap {
    width: 60px; height: 4px; background: rgba(255,255,255,0.05); border-radius: 2px; overflow: hidden;
    .stat-bar { height: 100%; border-radius: 2px; transition: width 1s; }
  }
}

.charts-main {
  display: grid; grid-template-columns: 1fr 320px; gap: 12px;
  .panel-large .chart-box { height: 240px; }
  .panel-small .chart-box { height: 240px; }
}

.charts-mid {
  display: grid; grid-template-columns: repeat(3, 1fr); gap: 12px;
  .chart-panel .chart-box { height: 180px; }
}

.table-panel {
  background: linear-gradient(135deg, rgba(0,20,60,0.8) 0%, rgba(0,10,35,0.6) 100%);
  border: 1px solid rgba(0,212,255,0.15);
  border-radius: 6px;
  overflow: hidden;
  position: relative;

  &::after {
    content: '';
    position: absolute;
    top: 0; left: 0; right: 0;
    height: 1px;
    background: linear-gradient(90deg, transparent, rgba(0,212,255,0.5), transparent);
  }

  .panel-header {
    display: flex; align-items: center; justify-content: space-between;
    padding: 10px 16px; border-bottom: 1px solid rgba(0,212,255,0.1);
    .panel-title { font-size: 13px; color: #00d4ff; font-weight: bold; letter-spacing: 1px; }
    .panel-badge { font-size: 11px; color: #00e5a0; background: rgba(0,229,160,0.1); border: 1px solid rgba(0,229,160,0.3); padding: 1px 8px; border-radius: 10px; }
  }

  .table-wrap { padding: 0 16px 12px; }
}

.data-table {
  width: 100%; border-collapse: collapse;
  th { font-size: 12px; color: #6a8caa; font-weight: normal; padding: 8px 12px; text-align: left; border-bottom: 1px solid rgba(0,212,255,0.15); white-space: nowrap; }
  td { font-size: 13px; color: #c0d0e0; padding: 8px 12px; border-bottom: 1px solid rgba(0,212,255,0.06); }
  tr:last-child td { border-bottom: none; }
  .row-highlight td { background: rgba(0,212,255,0.03); }

  .rank-badge {
    display: inline-flex; align-items: center; justify-content: center;
    width: 22px; height: 22px; border-radius: 50%; font-size: 12px; font-weight: bold;
    &.rank-1 { background: linear-gradient(135deg, #ffd700, #ff9f43); color: #1a0000; }
    &.rank-2 { background: linear-gradient(135deg, #c0c0c0, #a0a0a0); color: #1a1a1a; }
    &.rank-3 { background: linear-gradient(135deg, #cd7f32, #a0522d); color: #fff; }
  }
  .station-name { color: #e0e8ff; font-weight: 500; }
  .val-blue { color: #00d4ff; font-family: 'Courier New', monospace; }
  .val-muted { color: #6a8caa; }

  .score-bar-wrap {
    display: flex; align-items: center; gap: 8px;
    .score-bar { height: 6px; border-radius: 3px; min-width: 4px; flex-shrink: 0; }
    .score-text { font-size: 12px; font-weight: bold; white-space: nowrap; }
  }
  .load-bar-wrap {
    display: flex; align-items: center; gap: 8px;
    .load-bar { height: 6px; border-radius: 3px; min-width: 4px; flex-shrink: 0; }
    .load-text { font-size: 12px; color: #a0b4d0; white-space: nowrap; }
  }
  .level-tag {
    display: inline-block; padding: 1px 10px; border-radius: 3px; font-size: 12px; font-weight: bold;
    &.level-A { background: rgba(0,229,160,0.15); color: #00e5a0; border: 1px solid rgba(0,229,160,0.4); }
    &.level-B { background: rgba(0,212,255,0.15); color: #00d4ff; border: 1px solid rgba(0,212,255,0.4); }
    &.level-C { background: rgba(255,159,67,0.15); color: #ff9f43; border: 1px solid rgba(255,159,67,0.4); }
  }
}
</style>
