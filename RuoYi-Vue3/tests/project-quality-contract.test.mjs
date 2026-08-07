import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

test('frontend exposes one runnable test command and no broken lint command', async () => {
  const packageJson = JSON.parse(await readFile(new URL('../package.json', import.meta.url), 'utf8'))

  assert.equal(packageJson.scripts.test, 'node --test tests/*.test.mjs')
  assert.equal(packageJson.scripts.lint, undefined)
})

test('continuous integration runs backend and frontend tests and production build', async () => {
  const workflow = await readFile(new URL('../../.github/workflows/verify.yml', import.meta.url), 'utf8')

  assert.match(workflow, /npm ci/)
  assert.match(workflow, /npm test/)
  assert.match(workflow, /npm run build:prod/)
  assert.match(workflow, /mvn -B test -DskipTests=false/)
})
