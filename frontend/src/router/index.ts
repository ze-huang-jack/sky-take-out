import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import LoginView from '@/views/LoginView.vue'
import AdminLayout from '@/views/AdminLayout.vue'
import CategoryView from '@/views/CategoryView.vue'
import DishView from '@/views/DishView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: LoginView },
    {
      path: '/',
      component: AdminLayout,
      redirect: '/categories',
      children: [
        { path: 'categories', component: CategoryView },
        { path: 'dishes', component: DishView }
      ]
    }
  ]
})

router.beforeEach((to) => {
  const auth = useAuthStore()
  if (to.path !== '/login' && !auth.isAuthed) {
    return '/login'
  }
  if (to.path === '/login' && auth.isAuthed) {
    return '/'
  }
  return true
})

export default router
