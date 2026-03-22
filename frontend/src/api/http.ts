import axios from 'axios'
import { ElMessage } from 'element-plus'

interface ApiResponse<T> {
  code: number
  msg: string
  data: T
}

const http = axios.create({
  baseURL: '/api',
  timeout: 10000
})

http.interceptors.request.use((config) => {
  const token = localStorage.getItem('sky_admin_token')
  if (token) {
    config.headers.token = token
  }
  return config
})

http.interceptors.response.use(
  (response) => {
    const payload = response.data as ApiResponse<unknown>
    if (payload.code !== 1) {
      ElMessage.error(payload.msg || '请求失败')
      return Promise.reject(new Error(payload.msg || '请求失败'))
    }
    return payload
  },
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('sky_admin_token')
      localStorage.removeItem('sky_admin_user')
      window.location.href = '/login'
    }
    ElMessage.error(error.response?.data?.msg || error.message || '网络异常')
    return Promise.reject(error)
  }
)

export type { ApiResponse }
export default http
