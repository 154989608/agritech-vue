import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

test('home is a shop operations visual screen without transit analysis content', async () => {
  const page = await readFile(new URL('../src/views/index.vue', import.meta.url), 'utf8')

  assert.match(page, /商城运营态势大屏/)
  assert.match(page, /mall-operations-screen/)
  assert.match(page, /screen-matrix/)
  assert.match(page, /近 7 日成交趋势/)
  assert.match(page, /近 7 日支付订单/)
  assert.match(page, /近 7 日客单价/)
  assert.match(page, /热销商品成交额排行/)
  assert.match(page, /热销 SKU/)
  assert.match(page, /运营焦点/)
  assert.match(page, /renderFocusChart/)
  assert.match(page, /renderOrderChart/)
  assert.match(page, /renderUnitPriceChart/)
  assert.match(page, /renderHotSkuChart/)
  assert.match(page, /orderChartInstance\?\.dispose\(\)/)
  assert.match(page, /unitPriceChartInstance\?\.dispose\(\)/)
  assert.match(page, /hotSkuChartInstance\?\.dispose\(\)/)
  assert.match(page, /min-height:\s*calc\(100vh - 84px\)/)
  assert.doesNotMatch(page, /position:\s*fixed|z-index:\s*2001/)
  assert.match(page, /background:\s*#f4f6f8/)
  assert.match(page, /#e5484d/)
  assert.match(page, /#3478f6/)
  assert.doesNotMatch(page, /#132018|#192a20/)
  assert.doesNotMatch(page, /公交智看|今日总客流量|在线运营车辆|HomeBanner|visualizationModules/)
})
