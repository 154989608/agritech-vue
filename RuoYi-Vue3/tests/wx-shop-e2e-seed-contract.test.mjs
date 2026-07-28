import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

const root = new URL('../../', import.meta.url)
const source = (path) => readFile(new URL(path, root), 'utf8').catch(() => '')

test('e2e seed script creates idempotent public, member, coupon, and order-flow data', async () => {
  const sql = await source('sql/update/2026-07-28-新增小程序商城闭环验收数据.sql')

  assert.match(sql, /SET @tag := 'E2E20260728A'/)
  assert.match(sql, /mall_category/)
  assert.match(sql, /mall_product/)
  assert.match(sql, /mall_product_sku/)
  assert.match(sql, /mall_banner/)
  assert.match(sql, /mall_coupon/)
  assert.match(sql, /mall_member/)
  assert.match(sql, /WHERE NOT EXISTS/)
  assert.match(sql, /9900/)
  assert.doesNotMatch(sql, /\bDELETE\b|\bTRUNCATE\b|\bDROP\b/i)
})
