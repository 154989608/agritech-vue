import assert from 'node:assert/strict'
import { access, readFile } from 'node:fs/promises'
import test from 'node:test'

const source = (path) => readFile(new URL(`../../${path}`, import.meta.url), 'utf8')

test('backend configuration requires secrets and disables diagnostics by default', async () => {
  const [application, druid] = await Promise.all([
    source('ruoyi-admin/src/main/resources/application.yml'),
    source('ruoyi-admin/src/main/resources/application-druid.yml')
  ])

  assert.match(application, /profile:\s*\$\{RUOYI_PROFILE:/)
  assert.match(application, /secret:\s*\$\{TOKEN_SECRET\}/)
  assert.match(application, /enabled:\s*\$\{SPRING_DEVTOOLS_RESTART_ENABLED:false\}/)
  const swagger = application.match(/swagger:\s*\n(?:.*\n){0,3}?\s*enabled:\s*([^\n]+)/)?.[1]
  assert.equal(swagger?.trim(), '${SWAGGER_ENABLED:false}')
  assert.match(application, /xss:\s*\n(?:.*\n){0,3}?\s*enabled:\s*\$\{XSS_ENABLED:true\}/)
  assert.match(druid, /username:\s*\$\{MYSQL_USERNAME\}/)
  assert.match(druid, /password:\s*\$\{MYSQL_PASSWORD\}/)
  assert.match(druid, /webStatFilter:\s*[\s\S]*enabled:\s*\$\{DRUID_MONITORING_ENABLED:false\}/)
  assert.match(druid, /statViewServlet:\s*[\s\S]*enabled:\s*\$\{DRUID_MONITORING_ENABLED:false\}/)
  assert.doesNotMatch(`${application}\n${druid}`, /abcdefghijklmnopqrstuvwxyz|654zwhs321|login-password:\s*123456/)

  await assert.rejects(access(new URL('../../ruoyi-admin/src/main/resources/application-druid-copy.yml', import.meta.url)))
})

test('diagnostic endpoints are not anonymously allowed by Spring Security', async () => {
  const security = await source('ruoyi-framework/src/main/java/com/ruoyi/framework/config/SecurityConfig.java')

  assert.doesNotMatch(security, /antMatchers\([^)]*(swagger|api-docs|druid)[^)]*\)\.permitAll\(\)/s)
})

test('member order creation has request throttling in addition to service limits', async () => {
  const controller = await source('ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppMemberOrderController.java')

  assert.match(controller, /@RateLimiter\(time=60,count=10,limitType=LimitType\.IP\)\s*@PostMapping\("\/orders"\)/)
})

test('manual payment confirmation is disabled unless explicitly enabled', async () => {
  const [application, controller] = await Promise.all([
    source('ruoyi-admin/src/main/resources/application.yml'),
    source('ruoyi-admin/src/main/java/com/ruoyi/web/controller/shop/MallOrderController.java')
  ])

  assert.match(application, /manual-confirm-enabled:\s*\$\{MALL_MANUAL_PAYMENT_ENABLED:false\}/)
  assert.match(controller, /@Value\("\$\{mall\.payment\.manual-confirm-enabled:false\}"\)/)
  assert.match(controller, /if\(!manualPaymentEnabled\)throw new ServiceException\("人工支付确认未启用"\)/)
})

test('production clients use the mall brand and contain no placeholder API domains', async () => {
  const [development, staging, production, html, login, miniConfig, miniApp, miniManifest] = await Promise.all([
    source('RuoYi-Vue3/.env.development'),
    source('RuoYi-Vue3/.env.staging'),
    source('RuoYi-Vue3/.env.production'),
    source('RuoYi-Vue3/index.html'),
    source('RuoYi-Vue3/src/views/login.vue'),
    source('RuoYi-wx-app/utils/config.js'),
    source('RuoYi-wx-app/app.js'),
    source('RuoYi-wx-app/app.json')
  ])

  const brandedSources = `${development}\n${staging}\n${production}\n${html}\n${miniApp}\n${miniManifest}`
  assert.match(brandedSources, /黔山优选商城/)
  assert.doesNotMatch(brandedSources, /公交客流|城市公交|若依管理系统|RuoYi Shop/)
  assert.doesNotMatch(login, /password:\s*["']admin123["']/)
  assert.doesNotMatch(miniConfig, /example\.com/)
})
