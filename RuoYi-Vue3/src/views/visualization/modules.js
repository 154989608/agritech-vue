export const visualizationModules = [
  {
    key: 'spaceTimeEvolution',
    title: '客流时空演化',
    path: '/visualization/spaceTimeEvolution',
    icon: '🌊',
    color: '#00d4ff',
    description: '24小时趋势、OD流向与热力识别',
  },
  {
    key: 'stationHeatEfficiency',
    title: '站点热力效能',
    path: '/visualization/stationHeatEfficiency',
    icon: '📍',
    color: '#ff9f43',
    description: '站点热度排行、负荷评估与效能分层',
  },
  {
    key: 'capacityDiagnosis',
    title: '运力诊断决策',
    path: '/visualization/capacityDiagnosis',
    icon: '🚦',
    color: '#00e5a0',
    description: '供需缺口预警、班次诊断与调度建议',
  },
]

export const primaryVisualizationPath = visualizationModules[0].path
