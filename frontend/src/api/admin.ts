import http from './http'

export interface EmployeeLoginVO {
  id: number
  userName: string
  name: string
  token: string
}

export interface PageResult<T> {
  total: number
  records: T[]
}

export interface Category {
  id: number
  type: 1 | 2
  name: string
  sort: number
  status: 0 | 1
  createTime?: string
  updateTime?: string
}

export interface Dish {
  id: number
  name: string
  categoryId: number
  price: number
  image?: string
  description?: string
  status: 0 | 1
  categoryName?: string
  updateTime?: string
}

export const loginApi = (payload: { username: string; password: string }) =>
  http.post('/employee/login', payload)

export const getCategoryPageApi = (params: {
  page: number
  pageSize: number
  name?: string
  type?: number
}) => http.get('/category/page', { params })

export const createCategoryApi = (payload: { name: string; sort: number; type: number }) =>
  http.post('/category', payload)

export const updateCategoryApi = (payload: {
  id: number
  name: string
  sort: number
  type: number
}) => http.put('/category', payload)

export const setCategoryStatusApi = (status: 0 | 1, id: number) =>
  http.post(`/category/status/${status}`, null, { params: { id } })

export const deleteCategoryApi = (id: number) =>
  http.delete('/category', { params: { id } })

export const getCategoryListApi = (type = 1) =>
  http.get('/category/list', { params: { type } })

export const getDishPageApi = (params: {
  page: number
  pageSize: number
  name?: string
  categoryId?: number
  status?: number
}) => http.get('/dish/page', { params })

export const setDishStatusApi = (status: 0 | 1, id: number) =>
  http.post(`/dish/status/${status}`, null, { params: { id } })

export const deleteDishApi = (ids: number[]) =>
  http.delete('/dish', { params: { ids } })
