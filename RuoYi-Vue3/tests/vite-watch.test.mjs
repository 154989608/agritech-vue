import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

test('development server uses polling when inotify watchers are exhausted', async () => {
  const config = await readFile(new URL('../vite.config.js', import.meta.url), 'utf8')

  assert.match(config, /watch:\s*{\s*usePolling:\s*true/)
})
