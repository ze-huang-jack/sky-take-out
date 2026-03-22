<script setup lang="ts">
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const route = useRoute()
const auth = useAuthStore()

const logout = () => {
  auth.clearAuth()
  router.push('/login')
}
</script>

<template>
  <el-container class="layout">
    <el-aside width="220px" class="aside">
      <h3>Sky Admin</h3>
      <el-menu :default-active="route.path" router>
        <el-menu-item index="/categories">分类管理</el-menu-item>
        <el-menu-item index="/dishes">菜品管理</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <span>你好，{{ auth.user?.name || auth.user?.userName }}</span>
        <el-button text type="danger" @click="logout">退出</el-button>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout {
  min-height: 100vh;
}
.aside {
  background: #fff;
  border-right: 1px solid #ebeef5;
  padding: 20px 10px;
}
.header {
  border-bottom: 1px solid #ebeef5;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
