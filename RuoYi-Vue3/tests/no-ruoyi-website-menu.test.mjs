import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

test('database bootstrap does not create the RuoYi website menu', async () => {
  const bootstrapSql = await readFile(new URL('../../sql/ry_20250522.sql', import.meta.url), 'utf8')

  assert.doesNotMatch(bootstrapSql, /'若依官网'/)
})
