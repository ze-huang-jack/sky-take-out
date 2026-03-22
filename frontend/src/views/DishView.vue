<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import {
  deleteDishApi,
  getCategoryListApi,
  getDishPageApi,
  setDishStatusApi,
  type Category,
  type Dish
} from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const list = ref<Dish[]>([])
const total = ref(0)
const categories = ref<Category[]>([])
const query = reactive({ page: 1, pageSize: 10, name: '', categoryId: undefined as number | undefined })

const fetchCategories = async () => {
  const res = await getCategoryListApi(1)
  categories.value = res.data
}

const fetchData = async () => {
  loading.value = true
  try {
    const res = await getDishPageApi(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const toggleStatus = async (row: Dish) => {
  await setDishStatusApi(row.status === 1 ? 0 : 1, row.id)
  ElMessage.success('状态已更新')
  fetchData()
}

const remove = async (row: Dish) => {
  await ElMessageBox.confirm(`确认删除菜品「${row.name}」吗？`, '提示')
  await deleteDishApi([row.id])
  ElMessage.success('已删除')
  fetchData()
}

onMounted(async () => {
  await fetchCategories()
  await fetchData()
})
</script>

<template>
  <el-card>
    <template #header>
      <div class="toolbar">
        <div>
          <el-input v-model="query.name" placeholder="菜品名" style="width: 200px; margin-right: 8px" />
          <el-select v-model="query.categoryId" clearable placeholder="分类" style="width: 180px">
            <el-option v-for="c in categories" :key="c.id" :value="c.id" :label="c.name" />
          </el-select>
          <el-button style="margin-left: 8px" @click="fetchData">查询</el-button>
        </div>
      </div>
    </template>

    <el-alert type="info" :closable="false" show-icon>
      当前是 MVP：支持查询、启停、删除。新增/编辑菜品可在下一迭代补齐。
    </el-alert>

    <el-table v-loading="loading" :data="list" border style="margin-top: 12px">
      <el-table-column prop="id" label="ID" width="90" />
      <el-table-column prop="name" label="菜品名" />
      <el-table-column prop="categoryName" label="分类" width="160" />
      <el-table-column prop="price" label="价格" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">{{ row.status === 1 ? '起售' : '停售' }}</template>
      </el-table-column>
      <el-table-column label="操作" width="220" fixed="right">
        <template #default="{ row }">
          <el-button link @click="toggleStatus(row)">{{ row.status === 1 ? '停售' : '起售' }}</el-button>
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
