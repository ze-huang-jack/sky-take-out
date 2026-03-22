import { defineStore } from 'pinia'
import type { EmployeeLoginVO } from '@/api/admin'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('sky_admin_token') || '',
    user: JSON.parse(localStorage.getItem('sky_admin_user') || 'null') as EmployeeLoginVO | null
  }),
  getters: {
    isAuthed: (state) => Boolean(state.token)
  },
  actions: {
    setAuth(user: EmployeeLoginVO) {
      this.token = user.token
      this.user = user
      localStorage.setItem('sky_admin_token', user.token)
      localStorage.setItem('sky_admin_user', JSON.stringify(user))
    },
    clearAuth() {
      this.token = ''
      this.user = null
      localStorage.removeItem('sky_admin_token')
      localStorage.removeItem('sky_admin_user')
    }
  }
})
