<template>
  <div class="app-container">
    <el-form ref="queryRef" :model="queryParams" :inline="true" v-show="showSearch">
      <el-form-item label="分类名称" prop="categoryName"><el-input v-model="queryParams.categoryName" placeholder="请输入分类名称" clearable @keyup.enter="getList" /></el-form-item>
      <el-form-item label="状态" prop="status"><el-select v-model="queryParams.status" placeholder="全部状态" clearable><el-option label="正常" value="0" /><el-option label="停用" value="1" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" icon="Search" @click="getList">查询</el-button><el-button icon="Refresh" @click="resetQuery">重置</el-button></el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" v-hasPermi="['shop:category:add']" @click="handleAdd()">新增一级分类</el-button></el-col>
      <el-col :span="1.5"><el-button type="warning" plain icon="Download" v-hasPermi="['shop:category:export']" @click="handleExport">导出</el-button></el-col>
      <el-col :span="1.5"><el-button plain icon="Sort" @click="toggleExpand">展开/折叠</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>
    <el-table v-if="refreshTable" v-loading="loading" :data="categoryList" row-key="categoryId" :default-expand-all="expandAll" :tree-props="{ children: 'children' }">
      <el-table-column prop="categoryName" label="分类名称" min-width="200" />
      <el-table-column prop="level" label="层级" width="90"><template #default="scope">{{ scope.row.level === 1 ? '一级' : '二级' }}</template></el-table-column>
      <el-table-column prop="sortNum" label="排序" width="90" />
      <el-table-column prop="status" label="状态" width="90"><template #default="scope"><el-tag :type="scope.row.status === '0' ? 'success' : 'info'">{{ scope.row.status === '0' ? '正常' : '停用' }}</el-tag></template></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180"><template #default="scope">{{ parseTime(scope.row.createTime) }}</template></el-table-column>
      <el-table-column label="操作" width="220" fixed="right"><template #default="scope">
        <el-button v-if="scope.row.level === 1" link type="primary" icon="Plus" v-hasPermi="['shop:category:add']" @click="handleAdd(scope.row)">新增子分类</el-button>
        <el-button link type="primary" icon="Edit" v-hasPermi="['shop:category:edit']" @click="handleUpdate(scope.row)">修改</el-button>
        <el-button link type="danger" icon="Delete" v-hasPermi="['shop:category:remove']" @click="handleDelete(scope.row)">删除</el-button>
      </template></el-table-column>
    </el-table>
    <el-dialog v-model="open" :title="title" width="520px" append-to-body>
      <el-form ref="categoryRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item v-if="form.level === 2" label="上级分类" prop="parentId"><el-select v-model="form.parentId" :disabled="form.categoryId"><el-option v-for="item in parentOptions" :key="item.categoryId" :label="item.categoryName" :value="item.categoryId" /></el-select></el-form-item>
        <el-form-item label="分类名称" prop="categoryName"><el-input v-model="form.categoryName" maxlength="64" show-word-limit /></el-form-item>
        <el-form-item label="显示排序" prop="sortNum"><el-input-number v-model="form.sortNum" :min="0" controls-position="right" /></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="form.status"><el-radio label="0">正常</el-radio><el-radio label="1">停用</el-radio></el-radio-group></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" maxlength="500" show-word-limit /></el-form-item>
      </el-form>
      <template #footer><el-button type="primary" :loading="submitting" @click="submitForm">确定</el-button><el-button @click="open = false">取消</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup name="ShopCategory">
import { getCurrentInstance, nextTick, reactive, ref, toRefs } from 'vue'
import { addCategory, delCategory, getCategory, listCategory, updateCategory } from '@/api/shop/category'

const { proxy } = getCurrentInstance()
const loading = ref(false), showSearch = ref(true), open = ref(false), title = ref(''), submitting = ref(false), expandAll = ref(true), refreshTable = ref(true)
const categoryList = ref([]), parentOptions = ref([])
const data = reactive({ form: {}, queryParams: { categoryName: undefined, status: undefined }, rules: { categoryName: [{ required: true, message: '分类名称不能为空', trigger: 'blur' }], parentId: [{ required: true, message: '请选择上级分类', trigger: 'change' }] } })
const { form, queryParams, rules } = toRefs(data)

function getList() { loading.value = true; listCategory(queryParams.value).then(res => { categoryList.value = proxy.handleTree(res.data, 'categoryId', 'parentId'); parentOptions.value = res.data.filter(item => item.level === 1 && item.status === '0'); }).finally(() => { loading.value = false }) }
function reset() { form.value = { categoryId: undefined, parentId: undefined, categoryName: '', level: 1, sortNum: 0, status: '0', remark: '' }; proxy.resetForm('categoryRef') }
function resetQuery() { proxy.resetForm('queryRef'); getList() }
function handleAdd(parent) { reset(); if (parent) { form.value.level = 2; form.value.parentId = parent.categoryId }; title.value = parent ? '新增二级分类' : '新增一级分类'; open.value = true }
function handleUpdate(row) { reset(); getCategory(row.categoryId).then(res => { form.value = res.data; title.value = '修改分类'; open.value = true }) }
function handleDelete(row) { proxy.$modal.confirm(`是否确认删除分类“${row.categoryName}”？`).then(() => delCategory(row.categoryId)).then(() => { proxy.$modal.msgSuccess('删除成功'); getList() }).catch(() => {}) }
function handleExport() { proxy.download('shop/category/export', { ...queryParams.value }, `category_${new Date().getTime()}.xlsx`) }
function submitForm() { proxy.$refs.categoryRef.validate(valid => { if (!valid) return; submitting.value = true; const request = form.value.categoryId ? updateCategory(form.value) : addCategory(form.value); request.then(() => { proxy.$modal.msgSuccess(form.value.categoryId ? '修改成功' : '新增成功'); open.value = false; getList() }).finally(() => { submitting.value = false }) }) }
function toggleExpand() { refreshTable.value = false; expandAll.value = !expandAll.value; nextTick(() => { refreshTable.value = true }) }
getList()
</script>
