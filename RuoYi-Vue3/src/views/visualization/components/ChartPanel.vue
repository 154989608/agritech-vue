<template>
  <div class="chart-panel" :class="$attrs.class">
    <div class="panel-header">
      <span class="panel-title">◆ {{ title }}</span>
      <span v-if="badge" class="panel-badge" :class="{ warn: badgeWarn }">{{ badge }}</span>
    </div>
    <div ref="chartRef" class="chart-box" :style="{ height: height }"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
  title: { type: String, required: true },
  badge: { type: String, default: '' },
  badgeWarn: { type: Boolean, default: false },
  height: { type: String, default: '220px' }
})

const chartRef = ref(null)

defineExpose({ chartRef })
</script>
