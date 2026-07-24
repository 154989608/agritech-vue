<template>
  <div class="screen-container">
    <ScreenHeader sub="CAPACITY DIAGNOSIS & DECISION" title="城市公交运力诊断决策分析" :currentTime="currentTime" />
    <ScreenModuleNav />

    <!-- 统计卡片行 -->
    <div class="stats-row">
      <div class="stat-card" v-for="item in statCards" :key="item.label">
        <div class="stat-icon">{{ item.icon }}</div>
        <div class="stat-body">
          <div class="stat-value" :style="{ color: item.color }">{{ item.value }}</div>
          <div class="stat-label">{{ item.label }}</div>
        </div>
        <div class="stat-gauge">
          <svg width="52" height="52" viewBox="0 0 52 52">
            <circle cx="26" cy="26" r="20" fill="none" stroke="rgba(255,255,255,0.05)" stroke-width="4"/>
            <circle cx="26" cy="26" r="20" fill="none" :stroke="item.color" stroke-width="4"
              stroke-linecap="round"
              :stroke-dasharray="`${item.percent * 1.257} 125.7`"
              stroke-dashoffset="31.4"
              transform="rotate(-90 26 26)"
            />
            <text x="26" y="30" text-anchor="middle" :fill="item.color" font-size="11" font-weight="bold">{{ item.percent }}%</text>
          </svg>
        </div>
      </div>
    </div>

    <!-- 主图区 -->
    <div class="charts-main">
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 线路运力供需对比</span>
        </div>
        <div ref="capacityChart" class="chart-box" style="height:220px"></div>
      </div>
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 近30天班次作废率</span>
        </div>
        <div ref="cancelRateChart" class="chart-box" style="height:220px"></div>
      </div>
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 车辆运营状态分布</span>
        </div>
        <div ref="vehicleChart" class="chart-box" style="height:220px"></div>
      </div>
    </div>

    <!-- 中图区 -->
    <div class="charts-mid">
      <div class="chart-panel panel-wide">
        <div class="panel-header">
          <span class="panel-title">◆ 本周准点率 / 满载率趋势</span>
          <span class="panel-badge">综合诊断</span>
        </div>
        <div ref="diagnosisChart" class="chart-box" style="height:180px"></div>
      </div>
      <div class="chart-panel">
        <div class="panel-header">
          <span class="panel-title">◆ IC卡种客流分布</span>
        </div>
        <div ref="cardTypeChart" class="chart-box" style="height:180px"></div>
      </div>
    </div>

    <!-- 底部 -->
    <div class="charts-bottom">
      <div class="alert-panel">
        <div class="panel-header">
          <span class="panel-title">⚡ 运力缺口预警</span>
          <span class="panel-badge warn">{{ issues.length }} 条</span>
        </div>
        <div class="alert-list">
          <div class="alert-item" v-for="(item, i) in issues" :key="i" :class="'alert-' + item.level">
            <div class="alert-dot"></div>
            <div class="alert-body">
              <div class="alert-title">{{ item.line }}</div>
              <div class="alert-desc">{{ item.desc }}</div>
            </div>
            <div class="alert-meta">
              <div class="alert-load">{{ item.load }}%</div>
              <div class="alert-tag">满载率</div>
            </div>
          </div>
        </div>
      </div>

      <div class="suggest-panel">
        <div class="panel-header">
          <span class="panel-title">✅ 调度优化建议</span>
        </div>
        <div class="suggest-list">
          <div class="suggest-item" v-for="(s, i) in suggestions" :key="i">
            <div class="suggest-num">{{ String(i + 1).padStart(2, '0') }}</div>
            <div class="suggest-content">{{ s.content }}</div>
            <div class="suggest-priority" :class="'p-' + s.priority">{{ s.priorityText }}</div>
          </div>
        </div>
      </div>

      <div class="table-panel">
        <div class="panel-header">
          <span class="panel-title">◆ 线路运力详情</span>
        </div>
        <div class="table-wrap">
          <table class="data-table">
            <thead>
              <tr><th>线路</th><th>配车数</th><th>间隔(分)</th><th>满载率</th><th>准点率</th><th>状态</th></tr>
            </thead>
            <tbody>
              <tr v-for="(row, i) in lineData" :key="i">
                <td class="line-name">{{ row.lineName }}</td>
                <td class="val-num">{{ row.vehicleCount }}</td>
                <td class="val-num">{{ row.frequency }}</td>
                <td><span class="val-tag" :class="getLoadClass(row.loadFactor)">{{ row.loadFactor }}%</span></td>
                <td>
                  <div class="mini-bar-wrap">
                    <div class="mini-bar" :style="{ width: row.onTimeRate + '%', background: getOnTimeColor(row.onTimeRate) }"></div>
                    <span class="mini-val">{{ row.onTimeRate }}%</span>
                  </div>
                </td>
                <td><span class="status-tag" :class="row.status === '正常' ? 'ok' : 'warn'">{{ row.status }}</span></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup name="CapacityDiagnosis">
import { ref, onMounted } from 'vue'
import echarts from '@/utils/echarts'
import ScreenHeader from '../components/ScreenHeader.vue'
import ScreenModuleNav from '../components/ScreenModuleNav.vue'
import { useScreen, darkTheme, formatAxisValue } from '../composables/useScreen'

const { currentTime, initChart } = useScreen()

const capacityChart = ref(null)
const cancelRateChart = ref(null)
const vehicleChart = ref(null)
const diagnosisChart = ref(null)
const cardTypeChart = ref(null)

const statCards = ref([
  { label: '运营车辆', value: '856', icon: '🚌', color: '#00d4ff', percent: 85 },
  { label: '运营线路', value: '127', icon: '🛣️', color: '#ff9f43', percent: 78 },
  { label: '准点率', value: '92.5%', icon: '⏱️', color: '#00e5a0', percent: 92 },
  { label: '平均满载率', value: '78.3%', icon: '📦', color: '#ff6b6b', percent: 78 },
])

const issues = ref([
  { line: 'K239路', desc: '早高峰7-9点持续拥挤，建议增开2班', load: 92, level: 'high' },
  { line: '692路', desc: '满载率连续3天超95%，运力严重不足', load: 95, level: 'high' },
  { line: 'K148路', desc: '晚高峰17-19点满载率偏高', load: 87, level: 'mid' },
  { line: '101路', desc: '平峰期班次密度偏低，乘客等待时间长', load: 82, level: 'mid' },
  { line: 'K56路', desc: '周末运力配置不足，建议增加班次', load: 78, level: 'low' },
])

const suggestions = ref([
  { content: 'K239路早高峰(7:00-9:00)建议增开2班次，发车间隔缩短至5分钟', priority: 'high', priorityText: '紧急' },
  { content: '692路满载率持续偏高，建议考虑更换大容量车型或增派车辆', priority: 'high', priorityText: '紧急' },
  { content: 'K148路晚高峰增加1班次，17:30-19:00期间加密发车', priority: 'mid', priorityText: '建议' },
  { content: '平峰期(10:00-16:00)可适当减少空载率高的线路班次，优化资源配置', priority: 'low', priorityText: '参考' },
])

const lineData = ref([
  { lineName: 'K239路', vehicleCount: 25, frequency: 8, loadFactor: 92, onTimeRate: 88, status: '拥挤' },
  { lineName: '692路', vehicleCount: 18, frequency: 10, loadFactor: 95, onTimeRate: 85, status: '拥挤' },
  { lineName: 'K148路', vehicleCount: 20, frequency: 9, loadFactor: 87, onTimeRate: 90, status: '拥挤' },
  { lineName: '101路', vehicleCount: 22, frequency: 12, loadFactor: 78, onTimeRate: 93, status: '正常' },
  { lineName: 'K56路', vehicleCount: 15, frequency: 15, loadFactor: 75, onTimeRate: 95, status: '正常' },
  { lineName: '102路', vehicleCount: 28, frequency: 7, loadFactor: 68, onTimeRate: 96, status: '正常' },
])

const getLoadClass = (v) => v >= 90 ? 'tag-red' : v >= 80 ? 'tag-orange' : 'tag-green'
const getOnTimeColor = (v) => v >= 95 ? '#00e5a0' : v >= 85 ? '#ff9f43' : '#ff6b6b'

onMounted(() => {
  // 运力对比
  const c1 = initChart(capacityChart.value)
  if (c1) {
    c1.setOption({
      ...darkTheme,
      tooltip: { trigger: 'axis', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' } },
      legend: { data: ['计划运力','实际运力','需求运力'], textStyle: { color: '#a0b4d0' }, top: 0 },
      grid: { left: 12, right: 20, top: 35, bottom: 18, containLabel: true },
      xAxis: { type: 'category', data: ['K239路','692路','K148路','101路','K56路','102路','K7路'],
        axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', fontSize: 11, margin: 10 }, splitLine: { show: false } },
      yAxis: { type: 'value', name: '人次/小时', nameTextStyle: { color: '#6a8caa' }, axisLine: { show: false }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', formatter: formatAxisValue, margin: 10 }, splitLine: { lineStyle: { color: '#0d2240', type: 'dashed' } } },
      series: [
        { name: '计划运力', type: 'bar', barWidth: '25%', data: [3000,2800,2500,2200,2000,2600,2400], itemStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:'#00d4ff'},{offset:1,color:'#0050ff'}]), borderRadius: [3,3,0,0] } },
        { name: '实际运力', type: 'bar', barWidth: '25%', data: [2850,2650,2300,2100,1900,2450,2280], itemStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:'#00e5a0'},{offset:1,color:'#00805a'}]), borderRadius: [3,3,0,0] } },
        { name: '需求运力', type: 'line', data: [3400,3100,2900,2000,2200,2350,2500], lineStyle: { color: '#ff6b6b', width: 2, type: 'dashed' }, itemStyle: { color: '#ff6b6b' }, symbol: 'circle', symbolSize: 5 }
      ]
    })
  }

  // 作废率
  const c2 = initChart(cancelRateChart.value)
  if (c2) {
    const days = Array.from({ length: 30 }, (_, i) => `${i + 1}日`)
    const data = days.map(() => +(Math.random() * 6 + 1).toFixed(1))
    const avgData = data.map((_, index) => {
      const window = data.slice(Math.max(0, index - 6), index + 1)
      return +(window.reduce((sum, item) => sum + item, 0) / window.length).toFixed(1)
    })
    c2.setOption({
      ...darkTheme,
      tooltip: { trigger: 'axis', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' }, formatter: p => `${p[0].name}<br/>作废率：${p[0].value}%` },
      legend: { data: ['作废率','7日均值'], textStyle: { color: '#a0b4d0', fontSize: 11 }, top: 0, right: 0 },
      grid: { left: 12, right: 15, top: 36, bottom: 20, containLabel: true },
      xAxis: { type: 'category', data: days, axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', fontSize: 10, interval: 4, margin: 10 }, splitLine: { show: false } },
      yAxis: { type: 'value', name: '%', nameTextStyle: { color: '#6a8caa' }, axisLine: { show: false }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', margin: 10 }, splitLine: { lineStyle: { color: '#0d2240', type: 'dashed' } } },
      visualMap: { show: false, min: 0, max: 8, inRange: { color: ['#00d4ff','#ff9f43','#ff6b6b'] } },
      series: [
        { name: '作废率', type: 'bar', data, barWidth: '58%', itemStyle: { borderRadius: [2,2,0,0] } },
        { name: '7日均值', type: 'line', data: avgData, smooth: true, symbol: 'none', lineStyle: { color: '#ff9f43', width: 2 },
          areaStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:'rgba(255,159,67,0.16)'},{offset:1,color:'rgba(255,159,67,0.01)'}]) }
        }
      ]
    })
  }

  // 车辆状态
  const c3 = initChart(vehicleChart.value)
  if (c3) {
    c3.setOption({
      ...darkTheme,
      tooltip: { trigger: 'item', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' } },
      legend: { orient: 'vertical', right: 8, top: 'center', textStyle: { color: '#a0b4d0', fontSize: 11 } },
      series: [{ type: 'pie', radius: ['45%','68%'], center: ['40%','50%'],
        data: [
          { value: 658, name: '运营中', itemStyle: { color: '#00e5a0' } },
          { value: 120, name: '维修保养', itemStyle: { color: '#ff9f43' } },
          { value: 45, name: '待命', itemStyle: { color: '#00d4ff' } },
          { value: 33, name: '故障', itemStyle: { color: '#ff6b6b' } },
        ],
        label: { show: false }, itemStyle: { borderColor: '#060d1f', borderWidth: 2 },
        emphasis: { itemStyle: { shadowBlur: 15, shadowColor: 'rgba(0,212,255,0.5)' } }
      }]
    })
  }

  // 准点率/满载率
  const c4 = initChart(diagnosisChart.value)
  if (c4) {
    c4.setOption({
      ...darkTheme,
      tooltip: { trigger: 'axis', backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' } },
      legend: { data: ['准点率','满载率'], textStyle: { color: '#a0b4d0' }, top: 0, right: 0 },
      grid: { left: 12, right: 15, top: 30, bottom: 18, containLabel: true },
      xAxis: { type: 'category', boundaryGap: false, data: ['周一','周二','周三','周四','周五','周六','周日'],
        axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', margin: 10 }, splitLine: { show: false } },
      yAxis: { type: 'value', min: 50, max: 100, axisLine: { show: false }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', formatter: v => `${v}%`, margin: 10 }, splitLine: { lineStyle: { color: '#0d2240', type: 'dashed' } } },
      series: [
        { name: '准点率', type: 'line', smooth: true, data: [92,94,91,93,89,95,96], lineStyle: { color: '#00d4ff', width: 2 }, itemStyle: { color: '#00d4ff' },
          areaStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:'rgba(0,212,255,0.2)'},{offset:1,color:'rgba(0,212,255,0.02)'}]) } },
        { name: '满载率', type: 'line', smooth: true, data: [78,82,85,80,88,75,72], lineStyle: { color: '#ff9f43', width: 2 }, itemStyle: { color: '#ff9f43' },
          areaStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:'rgba(255,159,67,0.2)'},{offset:1,color:'rgba(255,159,67,0.02)'}]) } }
      ]
    })
  }

  // 卡种分布
  const c5 = initChart(cardTypeChart.value)
  if (c5) {
    c5.setOption({
      ...darkTheme,
      tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' }, backgroundColor: 'rgba(10,20,50,0.9)', borderColor: '#00d4ff', textStyle: { color: '#e0e8ff' } },
      legend: { data: ['普通卡','学生卡','老年卡','其他'], textStyle: { color: '#a0b4d0', fontSize: 11 }, top: 0 },
      grid: { left: 14, right: 15, top: 35, bottom: 18, containLabel: true },
      xAxis: { type: 'category', data: ['K239路','692路','K148路','101路','K56路'],
        axisLine: { lineStyle: { color: '#1a3a5c' } }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', fontSize: 11, margin: 10 }, splitLine: { show: false } },
      yAxis: { type: 'value', name: '人次', nameTextStyle: { color: '#6a8caa' }, axisLine: { show: false }, axisTick: { show: false }, axisLabel: { color: '#6a8caa', formatter: formatAxisValue, margin: 10 }, splitLine: { lineStyle: { color: '#0d2240', type: 'dashed' } } },
      series: [
        { name: '普通卡', type: 'bar', stack: 'total', barWidth: '45%', data: [1200,980,850,760,620], itemStyle: { color: '#00d4ff' } },
        { name: '学生卡', type: 'bar', stack: 'total', data: [800,450,320,280,210], itemStyle: { color: '#a78bfa' } },
        { name: '老年卡', type: 'bar', stack: 'total', data: [350,280,260,230,180], itemStyle: { color: '#00e5a0' } },
        { name: '其他', type: 'bar', stack: 'total', data: [180,150,130,110,90], itemStyle: { color: '#ff9f43', borderRadius: [2,2,0,0] } },
      ]
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

  .stat-icon { font-size: 24px; flex-shrink: 0; }
  .stat-body { flex: 1;
    .stat-value { font-size: 22px; font-weight: bold; font-family: 'Courier New', monospace; line-height: 1; }
    .stat-label { font-size: 12px; color: #6a8caa; margin-top: 4px; }
  }
  .stat-gauge { flex-shrink: 0; }
}

.charts-main { display: grid; grid-template-columns: repeat(3, 1fr); gap: 12px; }
.charts-mid { display: grid; grid-template-columns: 1fr 320px; gap: 12px; }

.charts-bottom {
  display: grid; grid-template-columns: 260px 280px 1fr; gap: 12px;

  .alert-panel, .suggest-panel, .table-panel {
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
      .panel-badge {
        font-size: 11px; color: #00e5a0; background: rgba(0,229,160,0.1); border: 1px solid rgba(0,229,160,0.3); padding: 1px 8px; border-radius: 10px;
        &.warn { color: #ff9f43; background: rgba(255,159,67,0.1); border-color: rgba(255,159,67,0.3); }
      }
    }
  }

  .alert-list {
    padding: 8px 12px; display: flex; flex-direction: column; gap: 8px;
    .alert-item {
      display: flex; align-items: center; gap: 10px; padding: 8px 10px; border-radius: 4px; border-left: 3px solid;
      &.alert-high { background: rgba(255,107,107,0.08); border-color: #ff6b6b; }
      &.alert-mid  { background: rgba(255,159,67,0.08);  border-color: #ff9f43; }
      &.alert-low  { background: rgba(0,212,255,0.05);   border-color: #00d4ff; }
      .alert-dot { width: 8px; height: 8px; border-radius: 50%; flex-shrink: 0;
        .alert-high & { background: #ff6b6b; box-shadow: 0 0 6px #ff6b6b; }
        .alert-mid & { background: #ff9f43; box-shadow: 0 0 6px #ff9f43; }
        .alert-low & { background: #00d4ff; box-shadow: 0 0 6px #00d4ff; }
      }
      .alert-body { flex: 1; .alert-title { font-size: 13px; color: #e0e8ff; font-weight: 500; } .alert-desc { font-size: 11px; color: #6a8caa; margin-top: 2px; } }
      .alert-meta { text-align: right; .alert-load { font-size: 16px; font-weight: bold; color: #ff9f43; font-family: 'Courier New', monospace; } .alert-tag { font-size: 10px; color: #6a8caa; } }
    }
  }

  .suggest-list {
    padding: 8px 12px; display: flex; flex-direction: column; gap: 8px;
    .suggest-item {
      display: flex; align-items: flex-start; gap: 10px; padding: 8px 10px;
      background: rgba(0,212,255,0.03); border-radius: 4px; border: 1px solid rgba(0,212,255,0.08);
      .suggest-num { font-size: 18px; font-weight: bold; color: rgba(0,212,255,0.3); font-family: 'Courier New', monospace; flex-shrink: 0; line-height: 1.4; }
      .suggest-content { flex: 1; font-size: 12px; color: #a0b4d0; line-height: 1.6; }
      .suggest-priority {
        font-size: 11px; padding: 1px 6px; border-radius: 3px; flex-shrink: 0; white-space: nowrap; margin-top: 2px;
        &.p-high { color: #ff6b6b; background: rgba(255,107,107,0.1); border: 1px solid rgba(255,107,107,0.3); }
        &.p-mid  { color: #ff9f43; background: rgba(255,159,67,0.1);  border: 1px solid rgba(255,159,67,0.3); }
        &.p-low  { color: #6a8caa; background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.1); }
      }
    }
  }

  .table-wrap { padding: 0 12px 10px; }
  .data-table {
    width: 100%; border-collapse: collapse;
    th { font-size: 12px; color: #6a8caa; font-weight: normal; padding: 7px 8px; text-align: left; border-bottom: 1px solid rgba(0,212,255,0.15); white-space: nowrap; }
    td { font-size: 12px; color: #c0d0e0; padding: 7px 8px; border-bottom: 1px solid rgba(0,212,255,0.06); }
    tr:last-child td { border-bottom: none; }
    .line-name { color: #e0e8ff; font-weight: 500; }
    .val-num { color: #a0b4d0; font-family: 'Courier New', monospace; }
    .val-tag {
      display: inline-block; padding: 1px 8px; border-radius: 3px; font-size: 11px; font-weight: bold;
      &.tag-red    { color: #ff6b6b; background: rgba(255,107,107,0.1); border: 1px solid rgba(255,107,107,0.3); }
      &.tag-orange { color: #ff9f43; background: rgba(255,159,67,0.1);  border: 1px solid rgba(255,159,67,0.3); }
      &.tag-green  { color: #00e5a0; background: rgba(0,229,160,0.1);   border: 1px solid rgba(0,229,160,0.3); }
    }
    .mini-bar-wrap { display: flex; align-items: center; gap: 6px;
      .mini-bar { height: 5px; border-radius: 3px; }
      .mini-val { font-size: 11px; color: #a0b4d0; white-space: nowrap; }
    }
    .status-tag {
      display: inline-block; padding: 1px 8px; border-radius: 3px; font-size: 11px; font-weight: bold;
      &.ok   { color: #00e5a0; background: rgba(0,229,160,0.1); border: 1px solid rgba(0,229,160,0.3); }
      &.warn { color: #ff9f43; background: rgba(255,159,67,0.1); border: 1px solid rgba(255,159,67,0.3); }
    }
  }
}
</style>
