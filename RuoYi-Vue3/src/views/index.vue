<template>
  <div class="home-container">

    <!-- ===== 顶部 Banner ===== -->
    <div class="home-banner">
      <div class="banner-bg-lines">
        <div class="bg-line" v-for="i in 6" :key="i"></div>
      </div>
      <div class="banner-content">
        <div class="banner-left">
          <div class="system-badge">PUBLIC TRANSIT BIG DATA</div>
          <h1 class="system-title">城市公交客流数据分析系统</h1>
          <p class="system-desc">基于 SpringBoot + Vue3 + MySQL 构建的全栈公交数据可视化与智能分析平台，涵盖客流时空演化、站点热力效能、运力诊断决策三大核心模块。</p>
          <div class="banner-actions">
            <router-link :to="primaryModulePath" class="btn-primary">进入大屏分析</router-link>
            <span class="version-tag">v{{ version }}</span>
          </div>
        </div>
        <div class="banner-right">
          <div class="banner-stats">
            <div class="bstat" v-for="s in bannerStats" :key="s.label">
              <div class="bstat-value" :style="{ color: s.color }">{{ s.value }}</div>
              <div class="bstat-label">{{ s.label }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="home-body">

      <!-- ===== 核心指标卡 ===== -->
      <div class="section-title">
        <span class="section-line"></span>
        <span>核心指标概览</span>
        <span class="section-line"></span>
      </div>
      <div class="kpi-grid">
        <div class="kpi-card" v-for="item in kpiCards" :key="item.label">
          <div class="kpi-icon" :style="{ background: item.bg }">{{ item.icon }}</div>
          <div class="kpi-body">
            <div class="kpi-value" :style="{ color: item.color }">{{ item.value }}</div>
            <div class="kpi-label">{{ item.label }}</div>
          </div>
          <div class="kpi-trend" :class="item.trend > 0 ? 'up' : 'down'">
            {{ item.trend > 0 ? '▲' : '▼' }} {{ Math.abs(item.trend) }}%
            <div class="kpi-trend-label">较昨日</div>
          </div>
        </div>
      </div>

      <!-- ===== 三大模块入口 ===== -->
      <div class="section-title" style="margin-top:32px">
        <span class="section-line"></span>
        <span>公交智看 · 三大分析模块</span>
        <span class="section-line"></span>
      </div>
      <div class="module-grid">
        <router-link
          v-for="mod in modules"
          :key="mod.title"
          :to="mod.path"
          class="module-card"
          :style="{ '--accent': mod.color }"
        >
          <div class="module-icon">{{ mod.icon }}</div>
          <div class="module-info">
            <div class="module-title">{{ mod.title }}</div>
            <div class="module-desc">{{ mod.desc }}</div>
            <div class="module-tags">
              <span v-for="t in mod.tags" :key="t" class="module-tag">{{ t }}</span>
            </div>
          </div>
          <div class="module-arrow">→</div>
          <div class="module-glow"></div>
        </router-link>
      </div>

      <!-- ===== 图表 + 更新日志 ===== -->
      <div class="bottom-grid">

        <!-- 迷你趋势图 -->
        <div class="chart-card">
          <div class="card-header">
            <span class="card-title">近7日客流趋势</span>
            <span class="card-badge">实时</span>
          </div>
          <div ref="trendChart" class="mini-chart"></div>
        </div>

        <!-- 技术栈 -->
        <div class="tech-card">
          <div class="card-header">
            <span class="card-title">技术架构</span>
          </div>
          <div class="tech-grid">
            <div class="tech-item" v-for="t in techStack" :key="t.name">
              <div class="tech-dot" :style="{ background: t.color }"></div>
              <div class="tech-info">
                <div class="tech-name">{{ t.name }}</div>
                <div class="tech-role">{{ t.role }}</div>
              </div>
            </div>
          </div>
        </div>

        <!-- 更新日志 -->
        <div class="log-card">
          <div class="card-header">
            <span class="card-title">更新日志</span>
          </div>
          <div class="log-list">
            <div class="log-item" v-for="log in updateLogs" :key="log.version">
              <div class="log-version">{{ log.version }}</div>
              <div class="log-body">
                <div class="log-date">{{ log.date }}</div>
                <ul class="log-items">
                  <li v-for="item in log.items" :key="item">{{ item }}</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup name="Index">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
import { primaryVisualizationPath, visualizationModules } from './visualization/modules'

const version = ref('2.1.0')
const trendChart = ref(null)
let chart = null
const primaryModulePath = primaryVisualizationPath

const bannerStats = ref([
  { value: '127', label: '运营线路', color: '#00d4ff' },
  { value: '1,234', label: '全网站点', color: '#ff9f43' },
  { value: '56', label: 'REST 接口', color: '#00e5a0' },
  { value: '8', label: '数据维度', color: '#a78bfa' },
])

const kpiCards = ref([
  { label: '今日总客流量', value: '125,800', icon: '👥', color: '#00d4ff', bg: 'rgba(0,212,255,0.12)', trend: 8.5 },
  { label: '在线运营车辆', value: '856',     icon: '🚌', color: '#ff9f43', bg: 'rgba(255,159,67,0.12)', trend: 3.2 },
  { label: '当日运营班次', value: '4,328',   icon: '📅', color: '#00e5a0', bg: 'rgba(0,229,160,0.12)', trend: 1.8 },
  { label: '平均准点率',   value: '92.5%',   icon: '⏱️', color: '#a78bfa', bg: 'rgba(167,139,250,0.12)', trend: -0.5 },
  { label: '平均满载率',   value: '78.3%',   icon: '📊', color: '#ff6b6b', bg: 'rgba(255,107,107,0.12)', trend: -2.1 },
  { label: '运力缺口线路', value: '12条',    icon: '⚡', color: '#f9d423', bg: 'rgba(249,212,35,0.12)',  trend: 5.0 },
])

const moduleDetails = {
  spaceTimeEvolution: {
    desc: '分析全网24小时客流趋势、区域分布热力图、OD出行流向，识别早晚高峰规律。',
    tags: ['时序分析', '热力图', 'OD流向', '高峰识别'],
  },
  stationHeatEfficiency: {
    desc: '评估各站点客流承载能力与运营效能，输出站点效能评级，辅助站点优化决策。',
    tags: ['效能评级', 'TOP10', '换乘分析', '负荷率'],
  },
  capacityDiagnosis: {
    desc: '诊断各线路运力供需匹配情况，输出运力缺口预警与智能调度优化建议。',
    tags: ['运力对比', '缺口预警', '班次优化', '调度建议'],
  },
}

const modules = visualizationModules.map((item) => ({
  ...item,
  ...moduleDetails[item.key],
}))

const techStack = ref([
  { name: 'Spring Boot 2.x', role: '后端核心框架', color: '#6db33f' },
  { name: 'MySQL 5.7+',      role: '业务与分析数据', color: '#4479a1' },
  { name: 'Redis',           role: '缓存层',       color: '#d82c20' },
  { name: 'Vue 3.4',         role: '前端框架',     color: '#42b883' },
  { name: 'ECharts 5.x',    role: '数据可视化',   color: '#e43c59' },
  { name: 'MyBatis',         role: 'ORM 框架',     color: '#e03c31' },
  { name: 'Element Plus',    role: 'UI 组件库',    color: '#409eff' },
])

const updateLogs = ref([
  {
    version: 'v2.1.0',
    date: '2026-03-09',
    items: ['新增三大可视化大屏模块', '升级深色科技风UI设计', '修复表格时间为空的异常'],
  },
  {
    version: 'v2.0.0',
    date: '2026-02-26',
    items: ['添加公交可视化数据接口支持', '新增公交智看可视化目录', '上线 56 个 REST 接口'],
  },
  {
    version: 'v1.1.0',
    date: '2025-11-11',
    items: ['新增在线用户管理', '接入 Swagger 全局 Token', '新增后端参数校验'],
  },
  {
    version: 'v1.0.0',
    date: '2025-11-08',
    items: ['城市公交客流大数据分析系统正式启动'],
  },
])

const formatPassengerAxis = (value) => {
  if (value >= 10000) {
    return `${(value / 10000).toFixed(1)}万`
  }
  return Number(value).toLocaleString()
}

const formatPassengerTooltip = (value) => `${Number(value).toLocaleString()} 人次`

const initTrendChart = () => {
  if (!trendChart.value) return
  chart = echarts.init(trendChart.value)
  const days = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
  const data = [118500, 125800, 121300, 132500, 145600, 168900, 142300]
  chart.setOption({
    backgroundColor: 'transparent',
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(10,20,50,0.95)',
      borderColor: '#00d4ff',
      textStyle: { color: '#e0e8ff' },
      axisPointer: { type: 'line', lineStyle: { color: 'rgba(0, 212, 255, 0.25)' } },
      formatter: (params) => {
        const point = params[0]
        return `${point.axisValueLabel}<br/>客流量：${formatPassengerTooltip(point.value)}`
      },
    },
    grid: { left: 10, right: 16, top: 26, bottom: 16, containLabel: true },
    xAxis: {
      type: 'category', data: days,
      axisLine: { lineStyle: { color: 'rgba(0,212,255,0.2)' } },
      axisTick: { show: false },
      axisLabel: { color: '#8a9bb5', fontSize: 12, margin: 12 },
      splitLine: { show: false },
    },
    yAxis: {
      type: 'value',
      axisLine: { show: false },
      axisTick: { show: false },
      splitNumber: 4,
      axisLabel: { color: '#8a9bb5', fontSize: 11, formatter: formatPassengerAxis, margin: 10 },
      splitLine: { lineStyle: { color: 'rgba(0,212,255,0.08)', type: 'dashed' } },
    },
    series: [{
      data,
      type: 'line',
      smooth: true,
      symbol: 'circle',
      symbolSize: 7,
      showSymbol: false,
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
        symbol: 'circle',
        symbolSize: 10,
        label: {
          color: '#fff',
          fontSize: 10,
          formatter: ({ value }) => formatPassengerAxis(value),
        },
        itemStyle: { color: '#ff9f43', borderColor: '#fff', borderWidth: 1 },
        data: [
          { type: 'max', name: '峰值' },
        ],
      },
    }],
  })
}

const handleResize = () => chart && chart.resize()

onMounted(() => {
  initTrendChart()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  chart && chart.dispose()
  window.removeEventListener('resize', handleResize)
})
</script>

<style scoped lang="scss">
// ====== 全局容器 ======
.home-container {
  min-height: calc(100vh - 84px);
  background: #f4f6fa;
  font-family: 'Microsoft YaHei', 'PingFang SC', sans-serif;
}

// ====== Banner ======
.home-banner {
  position: relative;
  background: linear-gradient(135deg, #0a0e27 0%, #0d1f4d 50%, #0a1628 100%);
  padding: 48px 40px 40px;
  overflow: hidden;

  .banner-bg-lines {
    position: absolute; inset: 0; pointer-events: none;
    .bg-line {
      position: absolute; width: 100%; height: 1px;
      background: rgba(0, 212, 255, 0.05);
      &:nth-child(1) { top: 20%; } &:nth-child(2) { top: 40%; }
      &:nth-child(3) { top: 60%; } &:nth-child(4) { top: 80%; }
      &:nth-child(5) { top: 15%; transform: rotate(-8deg); width: 150%; left: -25%; }
      &:nth-child(6) { top: 65%; transform: rotate(5deg);  width: 150%; left: -25%; }
    }
  }

  .banner-content {
    position: relative; z-index: 1;
    display: flex; align-items: center; justify-content: space-between; gap: 40px;
    max-width: 1200px;
  }

  .banner-left {
    flex: 1;
    .system-badge {
      display: inline-block; font-size: 11px; letter-spacing: 3px;
      color: rgba(0,212,255,0.7); border: 1px solid rgba(0,212,255,0.3);
      padding: 3px 12px; border-radius: 20px; margin-bottom: 16px;
      background: rgba(0,212,255,0.05);
    }
    .system-title {
      font-size: 32px; font-weight: bold; color: #e0f4ff;
      margin: 0 0 14px; letter-spacing: 1px;
      text-shadow: 0 0 30px rgba(0,212,255,0.4);
    }
    .system-desc {
      font-size: 14px; color: rgba(160,180,210,0.9); line-height: 1.8;
      max-width: 560px; margin: 0 0 24px;
    }
    .banner-actions {
      display: flex; align-items: center; gap: 16px;
      .btn-primary {
        display: inline-block; padding: 10px 28px;
        background: linear-gradient(135deg, #00d4ff, #0050ff);
        color: #fff; font-size: 14px; font-weight: bold;
        border-radius: 6px; text-decoration: none;
        box-shadow: 0 4px 20px rgba(0,212,255,0.35);
        transition: transform 0.2s, box-shadow 0.2s;
        &:hover { transform: translateY(-2px); box-shadow: 0 8px 28px rgba(0,212,255,0.5); }
      }
      .version-tag {
        font-size: 12px; color: rgba(0,212,255,0.6);
        background: rgba(0,212,255,0.08); border: 1px solid rgba(0,212,255,0.2);
        padding: 4px 12px; border-radius: 12px;
      }
    }
  }

  .banner-right {
    flex-shrink: 0;
    .banner-stats {
      display: grid; grid-template-columns: repeat(2, 1fr); gap: 20px;
      .bstat {
        text-align: center; padding: 16px 24px;
        background: rgba(0,212,255,0.05); border: 1px solid rgba(0,212,255,0.15);
        border-radius: 8px;
        .bstat-value { font-size: 28px; font-weight: bold; font-family: 'Courier New', monospace; }
        .bstat-label { font-size: 12px; color: rgba(160,180,210,0.7); margin-top: 4px; }
      }
    }
  }
}

// ====== 主体 ======
.home-body {
  padding: 28px 32px 40px;
  max-width: 1280px;
  margin: 0 auto;
}

// ====== 区块标题 ======
.section-title {
  display: flex; align-items: center; gap: 14px;
  font-size: 15px; font-weight: bold; color: #2c3e60;
  margin-bottom: 18px;
  .section-line {
    flex: 1; height: 1px;
    background: linear-gradient(90deg, rgba(0,80,200,0.15), transparent);
    &:last-child { background: linear-gradient(90deg, transparent, rgba(0,80,200,0.15)); }
  }
}

// ====== KPI卡片 ======
.kpi-grid {
  display: grid; grid-template-columns: repeat(6, 1fr); gap: 14px;

  .kpi-card {
    background: #fff; border-radius: 10px; padding: 18px 16px;
    display: flex; flex-direction: column; gap: 10px;
    box-shadow: 0 2px 12px rgba(0,0,0,0.06);
    border: 1px solid rgba(0,0,0,0.05);
    transition: transform 0.2s, box-shadow 0.2s;
    &:hover { transform: translateY(-3px); box-shadow: 0 8px 24px rgba(0,0,0,0.1); }

    .kpi-icon {
      width: 42px; height: 42px; border-radius: 10px;
      display: flex; align-items: center; justify-content: center;
      font-size: 20px;
    }
    .kpi-body {
      .kpi-value { font-size: 22px; font-weight: bold; line-height: 1; }
      .kpi-label { font-size: 12px; color: #8a9bb5; margin-top: 5px; }
    }
    .kpi-trend {
      font-size: 12px; font-weight: bold;
      .kpi-trend-label { font-size: 10px; color: #aaa; font-weight: normal; margin-top: 1px; }
      &.up { color: #00b87a; }
      &.down { color: #ff5252; }
    }
  }
}

// ====== 三大模块 ======
.module-grid {
  display: grid; grid-template-columns: repeat(3, 1fr); gap: 18px;

  .module-card {
    position: relative; overflow: hidden;
    background: #fff; border-radius: 12px; padding: 28px 24px;
    display: flex; align-items: center; gap: 20px;
    text-decoration: none;
    border: 1px solid rgba(0,0,0,0.06);
    box-shadow: 0 2px 16px rgba(0,0,0,0.06);
    transition: transform 0.25s, box-shadow 0.25s;
    cursor: pointer;

    &:hover {
      transform: translateY(-4px);
      box-shadow: 0 12px 32px rgba(0,0,0,0.12);
      .module-arrow { transform: translateX(5px); }
      .module-glow { opacity: 1; }
    }

    &::before {
      content: ''; position: absolute; top: 0; left: 0;
      width: 4px; height: 100%;
      background: var(--accent);
    }

    .module-icon {
      font-size: 36px; flex-shrink: 0;
      width: 60px; height: 60px;
      background: color-mix(in srgb, var(--accent) 12%, transparent);
      border-radius: 14px;
      display: flex; align-items: center; justify-content: center;
    }

    .module-info {
      flex: 1;
      .module-title {
        font-size: 16px; font-weight: bold; color: #1a2a4a; margin-bottom: 6px;
      }
      .module-desc {
        font-size: 12px; color: #7a8ca0; line-height: 1.7;
      }
      .module-tags {
        margin-top: 10px; display: flex; flex-wrap: wrap; gap: 6px;
        .module-tag {
          font-size: 11px; padding: 2px 8px; border-radius: 10px;
          background: color-mix(in srgb, var(--accent) 10%, transparent);
          color: var(--accent);
          border: 1px solid color-mix(in srgb, var(--accent) 25%, transparent);
        }
      }
    }

    .module-arrow {
      font-size: 18px; color: var(--accent); flex-shrink: 0;
      transition: transform 0.25s;
    }

    .module-glow {
      position: absolute; inset: 0; opacity: 0; transition: opacity 0.25s;
      background: radial-gradient(ellipse at top right, color-mix(in srgb, var(--accent) 8%, transparent), transparent 70%);
      pointer-events: none;
    }
  }
}

// ====== 底部三栏 ======
.bottom-grid {
  margin-top: 24px;
  display: grid; grid-template-columns: 1fr 280px 320px; gap: 18px;

  .chart-card, .tech-card, .log-card {
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

  .mini-chart {
    height: 240px; width: 100%;
  }

  // 技术栈
  .tech-grid {
    padding: 12px 16px;
    display: grid; grid-template-columns: 1fr 1fr; gap: 8px;
    .tech-item {
      display: flex; align-items: center; gap: 8px; padding: 8px 10px;
      background: #f8f9fc; border-radius: 6px;
      .tech-dot { width: 8px; height: 8px; border-radius: 50%; flex-shrink: 0; }
      .tech-info {
        .tech-name { font-size: 12px; font-weight: 600; color: #2c3e60; }
        .tech-role { font-size: 10px; color: #8a9bb5; margin-top: 1px; }
      }
    }
  }

  // 更新日志
  .log-list {
    padding: 12px 18px; display: flex; flex-direction: column; gap: 14px;
    max-height: 260px; overflow-y: auto;

    &::-webkit-scrollbar { width: 4px; }
    &::-webkit-scrollbar-track { background: transparent; }
    &::-webkit-scrollbar-thumb { background: #d0d8e8; border-radius: 2px; }

    .log-item {
      display: flex; gap: 12px;
      .log-version {
        font-size: 11px; font-weight: bold; color: #fff;
        background: linear-gradient(135deg, #0050ff, #00d4ff);
        padding: 2px 8px; border-radius: 10px;
        white-space: nowrap; height: fit-content; margin-top: 2px;
      }
      .log-body {
        flex: 1;
        .log-date { font-size: 11px; color: #aab0c0; margin-bottom: 5px; }
        .log-items {
          margin: 0; padding: 0 0 0 14px;
          li { font-size: 12px; color: #5a6a80; line-height: 1.8; }
        }
      }
    }
  }
}
</style>
