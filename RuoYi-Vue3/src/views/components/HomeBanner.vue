<template>
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
          <router-link to="/visualization/spaceTimeEvolution" class="btn-primary">进入大屏分析</router-link>
          <span class="version-tag">v{{ version }}</span>
        </div>
      </div>
      <div class="banner-right">
        <div class="banner-stats">
          <div class="bstat" v-for="s in stats" :key="s.label">
            <div class="bstat-value" :style="{ color: s.color }">{{ s.value }}</div>
            <div class="bstat-label">{{ s.label }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  version: { type: String, default: '2.1.0' }
})

const stats = [
  { value: '127', label: '运营线路', color: '#00d4ff' },
  { value: '1,234', label: '全网站点', color: '#ff9f43' },
  { value: '56', label: 'REST 接口', color: '#00e5a0' },
  { value: '8', label: '数据维度', color: '#a78bfa' },
]
</script>

<style scoped lang="scss">
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
</style>
