import { ref, onMounted, onBeforeUnmount } from 'vue'
import echarts from '@/utils/echarts'

export function useScreen() {
  const currentTime = ref('')
  const charts = []
  let timer = null

  const updateTime = () => {
    const now = new Date()
    currentTime.value = now.toLocaleString('zh-CN', {
      year: 'numeric', month: '2-digit', day: '2-digit',
      hour: '2-digit', minute: '2-digit', second: '2-digit'
    })
  }

  const initChart = (el) => {
    if (!el) return null
    const chart = echarts.init(el, 'dark')
    charts.push(chart)
    return chart
  }

  const handleResize = () => charts.forEach(c => c.resize())

  onMounted(() => {
    updateTime()
    timer = setInterval(updateTime, 1000)
    window.addEventListener('resize', handleResize)
  })

  onBeforeUnmount(() => {
    clearInterval(timer)
    charts.forEach(c => c.dispose())
    window.removeEventListener('resize', handleResize)
  })

  return { currentTime, initChart, charts }
}

export const darkTheme = { backgroundColor: 'transparent', textStyle: { color: '#a0b4d0' } }

export const formatAxisValue = (value) => {
  if (value >= 10000) return `${(value / 10000).toFixed(1)}万`
  if (value >= 1000) return `${(value / 1000).toFixed(1)}k`
  return `${value}`
}

export const formatTooltipValue = (value) => `${Number(value).toLocaleString()} 人次`
