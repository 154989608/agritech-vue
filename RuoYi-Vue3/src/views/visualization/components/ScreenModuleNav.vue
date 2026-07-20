<template>
  <div class="screen-module-nav">
    <router-link to="/index" class="nav-home">
      <span class="nav-home-arrow">←</span>
      <span>返回首页</span>
    </router-link>

    <div class="nav-track">
      <router-link
        v-for="item in modules"
        :key="item.key"
        :to="item.path"
        class="nav-item"
        :class="{ active: isActive(item.path) }"
        :style="{ '--accent': item.color }"
      >
        <span class="nav-icon">{{ item.icon }}</span>
        <span class="nav-copy">
          <span class="nav-title">{{ item.title }}</span>
          <span class="nav-desc">{{ item.description }}</span>
        </span>
        <span class="nav-state">{{ isActive(item.path) ? '当前模块' : '切换查看' }}</span>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { visualizationModules as modules } from '../modules'

const route = useRoute()

const isActive = (path) => route.path === path
</script>

<style scoped lang="scss">
.screen-module-nav {
  display: flex;
  align-items: stretch;
  gap: 12px;
}

.nav-home {
  width: 124px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  text-decoration: none;
  color: #d7ecff;
  font-size: 13px;
  font-weight: 600;
  letter-spacing: 0.5px;
  border-radius: 10px;
  border: 1px solid rgba(0, 212, 255, 0.18);
  background: linear-gradient(135deg, rgba(0, 32, 78, 0.9), rgba(0, 12, 34, 0.82));
  box-shadow: inset 0 0 0 1px rgba(255, 255, 255, 0.02);
  transition: transform 0.2s ease, border-color 0.2s ease, box-shadow 0.2s ease;

  &:hover {
    transform: translateY(-2px);
    border-color: rgba(0, 212, 255, 0.36);
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2);
  }
}

.nav-home-arrow {
  color: #00d4ff;
  font-size: 14px;
}

.nav-track {
  flex: 1;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
}

.nav-item {
  position: relative;
  display: flex;
  align-items: center;
  gap: 14px;
  min-width: 0;
  padding: 14px 16px;
  text-decoration: none;
  color: #d8e7ff;
  border-radius: 10px;
  border: 1px solid rgba(0, 212, 255, 0.12);
  background:
    radial-gradient(circle at top right, rgba(255, 255, 255, 0.04), transparent 48%),
    linear-gradient(135deg, rgba(0, 22, 60, 0.9), rgba(0, 10, 28, 0.82));
  overflow: hidden;
  transition: transform 0.2s ease, border-color 0.2s ease, box-shadow 0.2s ease;

  &::before {
    content: '';
    position: absolute;
    inset: 0 auto 0 0;
    width: 3px;
    background: linear-gradient(180deg, var(--accent), transparent 85%);
  }

  &:hover {
    transform: translateY(-2px);
    border-color: color-mix(in srgb, var(--accent) 60%, rgba(255, 255, 255, 0.15));
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.22);
  }

  &.active {
    border-color: color-mix(in srgb, var(--accent) 70%, rgba(255, 255, 255, 0.1));
    box-shadow:
      0 16px 30px rgba(0, 0, 0, 0.26),
      inset 0 0 24px color-mix(in srgb, var(--accent) 10%, transparent);

    .nav-icon {
      background: color-mix(in srgb, var(--accent) 24%, rgba(255, 255, 255, 0.04));
      box-shadow: 0 0 18px color-mix(in srgb, var(--accent) 22%, transparent);
    }

    .nav-state {
      color: #ffffff;
      background: color-mix(in srgb, var(--accent) 28%, rgba(255, 255, 255, 0.05));
      border-color: color-mix(in srgb, var(--accent) 40%, rgba(255, 255, 255, 0.08));
    }
  }
}

.nav-icon {
  width: 42px;
  height: 42px;
  flex-shrink: 0;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 22px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.03);
  transition: background 0.2s ease, box-shadow 0.2s ease;
}

.nav-copy {
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.nav-title {
  font-size: 14px;
  font-weight: 700;
  color: #eef7ff;
}

.nav-desc {
  font-size: 11px;
  line-height: 1.5;
  color: #7d9bb8;
}

.nav-state {
  margin-left: auto;
  flex-shrink: 0;
  padding: 4px 10px;
  border-radius: 999px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  background: rgba(255, 255, 255, 0.03);
  color: #89aac8;
  font-size: 11px;
  letter-spacing: 0.3px;
}

@media (max-width: 1280px) {
  .screen-module-nav {
    flex-direction: column;
  }

  .nav-home {
    width: 100%;
    min-height: 52px;
  }
}

@media (max-width: 960px) {
  .nav-track {
    grid-template-columns: 1fr;
  }

  .nav-item {
    align-items: flex-start;
  }

  .nav-state {
    margin-left: 0;
  }
}
</style>
