// ECharts 按需引入，替代全量 import * as echarts from 'echarts'
// ponetial: 打包体积从 ~1MB 降至 ~200KB（仅含实际使用的图表类型）
import * as echarts from 'echarts/core'
import {
  LineChart,
  BarChart,
  PieChart,
  RadarChart,
  HeatmapChart,
  GaugeChart
} from 'echarts/charts'
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  VisualMapComponent,
  MarkAreaComponent,
  MarkLineComponent,
  MarkPointComponent,
  RadarComponent
} from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'

echarts.use([
  LineChart, BarChart, PieChart, RadarChart, HeatmapChart, GaugeChart,
  TitleComponent, TooltipComponent, LegendComponent, GridComponent,
  VisualMapComponent, MarkAreaComponent, MarkLineComponent, MarkPointComponent,
  RadarComponent,
  CanvasRenderer
])

export default echarts
export { echarts }
