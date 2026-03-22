<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import {
  createCategoryApi,
  deleteCategoryApi,
  getCategoryPageApi,
  setCategoryStatusApi,
  updateCategoryApi,
  type Category
} from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const list = ref<Category[]>([])
const total = ref(0)
const query = reactive({ page: 1, pageSize: 10, name: '', type: 1 })

const dialogVisible = ref(false)
const editing = ref<Category | null>(null)
const form = reactive({ name: '', sort: 1, type: 1 })

const fetchData = async () => {
  loading.value = true
  try {
    const res = await getCategoryPageApi(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openCreate = () => {
  editing.value = null
  form.name = ''
  form.sort = 1
  form.type = query.type
  dialogVisible.value = true
}

const openEdit = (row: Category) => {
  editing.value = row
  form.name = row.name
  form.sort = row.sort
  form.type = row.type
  dialogVisible.value = true
}

const submit = async () => {
  if (!form.name) return ElMessage.warning('请输入分类名称')
  if (editing.value) {
    await updateCategoryApi({
      id: editing.value.id,
      name: form.name,
      sort: form.sort,
      type: form.type
    })
  } else {
    await createCategoryApi(form)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  fetchData()
}

const toggleStatus = async (row: Category) => {
  await setCategoryStatusApi(row.status === 1 ? 0 : 1, row.id)
  ElMessage.success('状态已更新')
  fetchData()
}

const remove = async (row: Category) => {
  await ElMessageBox.confirm(`确认删除分类「${row.name}」吗？`, '提示')
  await deleteCategoryApi(row.id)
  ElMessage.success('已删除')
  fetchData()
}

onMounted(fetchData)
</script>

<template>
  <el-card>
    <template #header>
      <div class="toolbar">
        <div>
          <el-input v-model="query.name" placeholder="分类名" style="width: 200px; margin-right: 8px" />
          <el-select v-model="query.type" style="width: 160px">
            <el-option :value="1" label="菜品分类" />
            <el-option :value="2" label="套餐分类" />
          </el-select>
          <el-button style="margin-left: 8px" @click="fetchData">查询</el-button>
        </div>
        <el-button type="primary" @click="openCreate">新增分类</el-button>
      </div>
    </template>

    <el-table v-loading="loading" :data="list" border>
      <el-table-column prop="id" label="ID" width="90" />
      <el-table-column prop="name" label="分类名" />
      <el-table-column prop="type" label="类型" width="120">
        <template #default="{ row }">{{ row.type === 1 ? '菜品' : '套餐' }}</template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">{{ row.status === 1 ? '启用' : '禁用' }}</template>
      </el-table-column>
      <el-table-column label="操作" width="240" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button link @click="toggleStatus(row)">{{ row.status === 1 ? '禁用' : '启用' }}</el-button>
          <el-button link type="danger" @click="remove(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pager">
      <el-pagination
        v-model:current-page="query.page"
        v-model:page-size="query.pageSize"
        background
        layout="total, prev, pager, next"
        :total="total"
        @current-change="fetchData"
      />
    </div>
  </el-card>

  <el-dialog v-model="dialogVisible" :title="editing ? '编辑分类' : '新增分类'" width="420px">
    <el-form label-width="80px">
      <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
      <el-form-item label="类型">
        <el-select v-model="form.type" style="width: 100%">
          <el-option :value="1" label="菜品分类" />
          <el-option :value="2" label="套餐分类" />
        </el-select>
      </el-form-item>
      <el-form-item label="排序"><el-input-number v-model="form.sort" :min="1" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submit">保存</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.pager {
  margin-top: 12px;
  display: flex;
  justify-content: flex-end;
}
</style>
