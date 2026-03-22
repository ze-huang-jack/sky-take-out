# frontend（Sky 管理端重建版）

> 技术栈：Vue 3 + Vite + TypeScript + Element Plus。

## 已实现（MVP）

- 管理员登录（`/api/employee/login` -> 后端 `/admin/employee/login`）
- 分类管理：分页查询 / 新增 / 编辑 / 启停 / 删除
- 菜品管理：分页查询 / 状态切换 / 删除
- 登录态：`token` 请求头自动注入，401 自动回登录页

## 目录结构

```txt
frontend/
  src/
    api/         # axios 封装与接口
    router/      # 路由与鉴权
    stores/      # pinia 登录态
    views/       # 页面
```

## 启动

```bash
cd frontend
npm install
npm run dev
```

默认地址：<http://localhost:5173>

<<<<<<< codex/request-custom-frontend-code-setup-a78i5j
默认测试账号（已在登录页自动填充）：

- 用户名：`admin`
- 密码：`123456`
>>>>>>> main
## 接口约定

开发环境使用 Vite 代理：

- 前端请求：`/api/**`
- 代理目标：`http://localhost:8080/admin/**`

对应你仓库中 `nginx.conf` 的管理端反向代理语义。

## 下一步建议

1. 补齐菜品新增/编辑（含口味、图片上传）
2. 补齐员工管理、套餐管理、订单管理
3. 增加权限粒度和统一错误页
