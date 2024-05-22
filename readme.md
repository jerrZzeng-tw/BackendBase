# 後端 Spring Security + JWT 權限管控

## 系統使用framework列表

| 名稱              | 說明       |
|:----------------|:---------|
| JAVA            | 2.2      |
| Spring Boot     | 3版       |
| Spring Security | 6版       |
| JPA             | 資料庫ORM套件 |
| Lombok          | 自動生成代碼套件 |
| Jackson         | json套件   |
| Caffeine cache  | 快取元件     |
| H2 DataBase     | H2資料庫    |

## 系統說明

- 使用H2資料庫可以直接運行
- 使用Spring Security + JWT做權限管控
- DB動態權限控管
- 統一封裝JSON結果

## 系統測試

### 1. 登入

http://127.0.0.1:8080/BackendBase/login

```json
{
  "username": "admin",
  "password": "123456"
}
```

```json
{
  "username": "user",
  "password": "234567"
}
```