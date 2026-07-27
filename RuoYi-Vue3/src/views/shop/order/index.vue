<template>
  <div class="app-container">
    <el-form :model="queryParams" :inline="true" v-show="showSearch">
      <el-form-item label="订单号"><el-input v-model="queryParams.orderNo" clearable @keyup.enter="getList" /></el-form-item>
      <el-form-item label="状态"><el-select v-model="queryParams.orderStatus" clearable><el-option v-for="item in orderStatuses" :key="item.value" :label="item.label" :value="item.value" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" icon="Search" @click="getList">查询</el-button><el-button icon="Refresh" @click="resetQuery">重置</el-button></el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8"><el-col :span="1.5"><el-button type="warning" plain icon="Download" v-hasPermi="['shop:order:export']" @click="handleExport">导出</el-button></el-col><right-toolbar v-model:showSearch="showSearch" @queryTable="getList" /></el-row>
    <el-table v-loading="loading" :data="orders">
      <el-table-column prop="orderNo" label="订单号" min-width="180" /><el-table-column prop="memberId" label="会员ID" width="100" />
      <el-table-column prop="payableAmount" label="应付金额" width="110"><template #default="scope">{{ money(scope.row.payableAmount) }}</template></el-table-column>
      <el-table-column prop="orderStatus" label="状态" width="120"><template #default="scope"><dict-tag :options="orderStatuses" :value="scope.row.orderStatus" /></template></el-table-column>
      <el-table-column prop="createTime" label="下单时间" width="180"><template #default="scope">{{ parseTime(scope.row.createTime) }}</template></el-table-column>
      <el-table-column label="操作" width="270" fixed="right"><template #default="scope"><el-button link type="primary" v-hasPermi="['shop:order:query']" @click="showDetail(scope.row)">详情</el-button><el-button v-if="scope.row.orderStatus === 'PENDING_PAYMENT'" link type="danger" v-hasPermi="['shop:order:cancel']" @click="doCancel(scope.row)">取消</el-button><el-button v-if="scope.row.orderStatus === 'PENDING_SHIPMENT'" link type="primary" v-hasPermi="['shop:order:ship']" @click="openShip(scope.row)">发货</el-button><el-button v-if="scope.row.orderStatus === 'SHIPPED'" link type="success" v-hasPermi="['shop:order:ship']" @click="complete(scope.row)">确认收货</el-button></template></el-table-column>
    </el-table>
    <pagination v-show="total > 0" v-model:total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    <el-dialog v-model="detailOpen" title="订单详情" width="760px"><el-descriptions :column="2" border><el-descriptions-item label="订单号">{{ detail.orderNo }}</el-descriptions-item><el-descriptions-item label="状态">{{ detail.orderStatus }}</el-descriptions-item><el-descriptions-item label="应付金额">{{ money(detail.payableAmount) }}</el-descriptions-item><el-descriptions-item label="收货信息">{{ maskedAddress }}</el-descriptions-item></el-descriptions><el-button class="mt10" link type="primary" :loading="sensitiveLoading" v-hasPermi="['shop:order:sensitive']" @click="showSensitive">查看明文收货信息</el-button><el-table :data="detail.items || []" class="mt10"><el-table-column prop="productNameSnapshot" label="商品" /><el-table-column prop="skuNameSnapshot" label="SKU" /><el-table-column prop="quantity" label="数量" width="80" /><el-table-column prop="payableAmount" label="金额" width="110"><template #default="scope">{{ money(scope.row.payableAmount) }}</template></el-table-column></el-table></el-dialog>
    <el-dialog v-model="shipOpen" title="订单发货" width="420px"><el-form :model="shipForm" label-width="85px"><el-form-item label="物流公司" required><el-input v-model="shipForm.logisticsCompany" /></el-form-item><el-form-item label="运单号" required><el-input v-model="shipForm.logisticsNo" /></el-form-item></el-form><template #footer><el-button type="primary" :loading="shipping" @click="submitShip">确定发货</el-button><el-button @click="shipOpen = false">取消</el-button></template></el-dialog>
  </div>
</template>
<script setup name="ShopOrder">
import { computed, getCurrentInstance, reactive, ref } from 'vue'
import { cancelOrder, completeOrder, getOrder, getOrderSensitive, listOrder, shipOrder } from '@/api/shop/order'
const { proxy } = getCurrentInstance(); const loading = ref(false), showSearch = ref(true), orders = ref([]), total = ref(0), detailOpen = ref(false), shipOpen = ref(false), shipping = ref(false), detail = ref({})
const sensitiveLoading = ref(false)
const shipForm = reactive({ orderId: null, logisticsCompany: '', logisticsNo: '' }), queryParams = reactive({ pageNum: 1, pageSize: 10, orderNo: undefined, orderStatus: undefined })
const orderStatuses = [{ label: '待支付', value: 'PENDING_PAYMENT', elTagType: 'warning' }, { label: '待发货', value: 'PENDING_SHIPMENT', elTagType: 'primary' }, { label: '已发货', value: 'SHIPPED', elTagType: 'primary' }, { label: '已完成', value: 'COMPLETED', elTagType: 'success' }, { label: '已取消', value: 'CANCELED', elTagType: 'info' }]
const money = value => `¥${(Number(value || 0) / 100).toFixed(2)}`
const maskedAddress = computed(() => detail.value.receiverName ? `${detail.value.receiverName} ${detail.value.receiverPhone || ''} ${detail.value.provinceName || ''}${detail.value.cityName || ''}${detail.value.districtName || ''}${detail.value.detailAddress || ''}` : '-')
function getList() { loading.value = true; listOrder(queryParams).then(res => { orders.value = res.rows; total.value = res.total }).finally(() => { loading.value = false }) }
function resetQuery() { queryParams.orderNo = undefined; queryParams.orderStatus = undefined; queryParams.pageNum = 1; getList() }
function showDetail(row) { getOrder(row.orderId).then(res => { detail.value = res.data; detailOpen.value = true }) }
function showSensitive() { if (!detail.value.orderId) return; sensitiveLoading.value = true; getOrderSensitive(detail.value.orderId).then(res => { detail.value = res.data }).finally(() => { sensitiveLoading.value = false }) }
function doCancel(row) { proxy.$prompt('请输入取消原因', '取消订单', { inputPattern: /\S+/, inputErrorMessage: '取消原因不能为空' }).then(({ value }) => cancelOrder(row.orderId, value)).then(() => { proxy.$modal.msgSuccess('订单已取消'); getList() }).catch(() => {}) }
function handleExport() { proxy.download('shop/order/export', { ...queryParams }, `order_${new Date().getTime()}.xlsx`) }
function openShip(row) { Object.assign(shipForm, { orderId: row.orderId, logisticsCompany: '', logisticsNo: '' }); shipOpen.value = true }
function submitShip() { if (!shipForm.logisticsCompany || !shipForm.logisticsNo) { proxy.$modal.msgError('请填写物流公司和运单号'); return }; shipping.value = true; shipOrder(shipForm.orderId, shipForm).then(() => { proxy.$modal.msgSuccess('发货成功'); shipOpen.value = false; getList() }).finally(() => { shipping.value = false }) }
function complete(row) { proxy.$modal.confirm(`确认订单“${row.orderNo}”已收货完成？`).then(() => completeOrder(row.orderId)).then(() => { proxy.$modal.msgSuccess('订单已完成'); getList() }).catch(() => {}) }
getList()
</script>
