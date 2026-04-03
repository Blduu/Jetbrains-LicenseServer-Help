# 核心功能文档

本文档详细介绍JetBrains License Server Help的核心功能模块、工作原理和使用方法。

## 📋 功能模块概览

| 功能模块 | 主要职责 | 核心类/接口 |
|---------|---------|------------|
| 许可证服务器模拟 | 模拟官方许可证服务器API | LicenseServerController |
| 产品管理 | 管理JetBrains产品信息 | ProductsContextHolder |
| 插件管理 | 管理JetBrains插件信息 | PluginsContextHolder |
| 证书管理 | 管理RSA密钥和证书 | CertificateContextHolder |
| ja-netfilter集成 | 代理工具下载和配置 | AgentContextHolder |
| 可视化界面 | 提供Web管理界面 | 前端HTML/JS/CSS文件 |

## 1. 许可证服务器模拟

### 1.1 功能概述

许可证服务器模拟是本项目的核心功能，它实现了与JetBrains官方许可证服务器兼容的API协议，支持许可证凭证的生成、验证和延长。

### 1.2 工作原理

1. **协议兼容**：完全兼容JetBrains官方许可证服务器的XML-RPC协议
2. **请求处理**：接收来自JetBrains产品的许可证请求
3. **凭证生成**：为每个请求生成唯一的许可证凭证（ticket）
4. **RSA签名**：使用RSA私钥对响应数据进行签名
5. **XML响应**：返回符合官方格式的XML响应数据

### 1.3 核心API接口

#### 1.3.1 获取许可证凭证

```
POST /rpc/obtainTicket.action
```

**请求参数**：
- `hostName`: 客户端主机名
- `machineId`: 客户端机器唯一标识符
- `salt`: 加密盐值

**响应示例**：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ObtainTicketResponse>
    <action>NONE</action>
    <confirmationStamp>1234567890</confirmationStamp>
    <leaseSignature>...</leaseSignature>
    <message></message>
    <prolongationPeriod>600000</prolongationPeriod>
    <responseCode>OK</responseCode>
    <salt>...</salt>
    <serverLease>...</serverLease>
    <serverUid>...</serverUid>
    <ticketId>...</ticketId>
    <ticketProperties>...</ticketProperties>
    <validationDeadlinePeriod>-1</validationDeadlinePeriod>
    <validationPeriod>600000</validationPeriod>
</ObtainTicketResponse>
```

#### 1.3.2 延长许可证有效期

```
POST /rpc/prolongTicket.action
```

**请求参数**：
- `ticketId`: 许可证凭证ID
- `machineId`: 客户端机器唯一标识符
- `salt`: 加密盐值

**响应示例**：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ProlongTicketResponse>
    <action>NONE</action>
    <confirmationStamp>1234567890</confirmationStamp>
    <leaseSignature>...</leaseSignature>
    <message></message>
    <prolongationPeriod>600000</prolongationPeriod>
    <responseCode>OK</responseCode>
    <salt>...</salt>
    <serverLease>...</serverLease>
    <serverUid>...</serverUid>
    <ticketId>...</ticketId>
    <ticketProperties>...</ticketProperties>
    <validationDeadlinePeriod>-1</validationDeadlinePeriod>
    <validationPeriod>600000</validationPeriod>
</ProlongTicketResponse>
```

#### 1.3.3 释放许可证凭证

```
POST /rpc/releaseTicket.action
```

**请求参数**：
- `ticketId`: 许可证凭证ID
- `machineId`: 客户端机器唯一标识符
- `salt`: 加密盐值

**响应示例**：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ReleaseTicketResponse>
    <action>NONE</action>
    <confirmationStamp>1234567890</confirmationStamp>
    <message></message>
    <responseCode>OK</responseCode>
    <salt>...</salt>
    <serverUid>...</serverUid>
</ReleaseTicketResponse>
```

#### 1.3.4 服务器状态检查

```
POST /rpc/ping.action
```

**响应示例**：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<PingResponse>
    <serverVersion>...</serverVersion>
    <serverUid>...</serverUid>
    <licensee>...</licensee>
    <licenseType>...</licenseType>
    <validThrough>...</validThrough>
    <gracePeriod>...</gracePeriod>
</PingResponse>
```

### 1.4 使用方法

1. **配置JetBrains产品**：
   - 打开JetBrains产品（如IntelliJ IDEA）
   - 选择 "Help" -> "Register"
   - 选择 "License server"
   - 输入许可证服务器地址：`http://your-server-ip:10768`
   - 点击 "Activate" 完成激活

2. **验证激活状态**：
   - 激活成功后，JetBrains产品会显示 "License server activated"
   - 可以在产品的 "Help" -> "Register" 页面查看激活状态

## 2. 产品和插件管理

### 2.1 功能概述

产品和插件管理功能负责维护JetBrains产品和插件的信息数据库，提供信息查询和定时更新功能。

### 2.2 工作原理

1. **数据存储**：产品信息存储在 `resources/external/data/product.json` 文件中
2. **数据加载**：应用启动时从JSON文件加载产品和插件信息
3. **定时更新**：每天自动从JetBrains官方API更新插件信息
4. **信息查询**：提供REST API接口供前端和其他模块查询

### 2.3 产品管理

#### 2.3.1 产品信息结构

产品信息包含以下字段：
- `name`: 产品名称
- `code`: 产品代码
- `description`: 产品描述
- `version`: 产品版本
- `releaseDate`: 发布日期

#### 2.3.2 产品查询API

```
GET /data/products
```

**响应示例**：
```json
[
    {
        "name": "IntelliJ IDEA Ultimate",
        "code": "IIU",
        "description": "The Most Intelligent IDE for Java Developers",
        "version": "2024.1",
        "releaseDate": "2024-04-01"
    },
    {
        "name": "PyCharm Professional",
        "code": "PCC",
        "description": "Python IDE for Professional Developers",
        "version": "2024.1",
        "releaseDate": "2024-04-01"
    }
]
```

### 2.4 插件管理

#### 2.4.1 插件信息结构

插件信息包含以下字段：
- `id`: 插件ID
- `name`: 插件名称
- `version`: 插件版本
- `description`: 插件描述
- `vendor`: 插件供应商
- `category`: 插件类别
- `downloadUrl`: 插件下载地址

#### 2.4.2 插件查询API

```
GET /data/plugins
```

**响应示例**：
```json
[
    {
        "id": "com.intellij.ideolog",
        "name": "Ideolog",
        "version": "203.0.30",
        "description": "A plugin for viewing and analyzing logs",
        "vendor": "JetBrains",
        "category": "Developer Tools",
        "downloadUrl": "https://plugins.jetbrains.com/plugin/9746-ideolog"
    }
]
```

#### 2.4.3 插件定时更新

插件信息会每天自动从JetBrains官方API更新，更新时间可以通过配置文件自定义：

```yaml
help:
  plugins:
    refresh-enabled: true  # 是否启用定时刷新
    refresh-cron: "0 0 12 * * ?"  # 刷新时间（每天中午12点）
    page-size: 20  # 分页大小
    thread-count: 5  # 并发线程数
    timeout: 30000  # 请求超时时间（毫秒）
```

## 3. 证书管理

### 3.1 功能概述

证书管理功能负责生成和管理RSA密钥对和X.509证书，用于保证许可证数据的安全性。

### 3.2 工作原理

1. **密钥生成**：应用启动时自动生成RSA密钥对（如果不存在）
2. **证书创建**：基于RSA密钥对创建X.509证书
3. **密钥存储**：密钥和证书存储在 `resources/external/certificate/` 目录下
4. **签名验证**：使用私钥对响应数据进行签名，使用公钥进行验证

### 3.3 证书查询API

```
GET /data/certificate
```

**响应示例**：
```json
{
    "publicKey": "-----BEGIN PUBLIC KEY-----...-----END PUBLIC KEY-----",
    "privateKey": "-----BEGIN PRIVATE KEY-----...-----END PRIVATE KEY-----",
    "certificate": "-----BEGIN CERTIFICATE-----...-----END CERTIFICATE-----",
    "fingerprint": "12:34:56:78:90:AB:CD:EF:12:34:56:78:90:AB:CD:EF:12:34:56:78"
}
```

## 4. ja-netfilter集成

### 4.1 功能概述

ja-netfilter集成功能提供ja-netfilter代理工具的下载和配置，用于辅助JetBrains产品的许可证验证。

### 4.2 工作原理

1. **工具下载**：从官方地址下载最新版本的ja-netfilter工具
2. **配置生成**：根据用户需求生成自定义的代理配置
3. **一键部署**：提供压缩包下载，用户可以直接解压使用

### 4.3 使用方法

1. **下载ja-netfilter**：
   - 在管理界面中点击 "ja-netfilter下载" 按钮
   - 或直接访问API接口：`GET /zip/ja-netfilter`

2. **配置使用**：
   - 解压下载的压缩包到任意目录
   - 运行目录中的启动脚本（`start.sh` 或 `start.bat`）
   - 配置JetBrains产品使用ja-netfilter代理

3. **代理配置**：
   - 默认代理地址：`http://localhost:8000`
   - 可以在 `ja-netfilter/config` 目录中自定义代理规则

### 4.4 API接口

```
GET /zip/ja-netfilter
```

**响应**：ja-netfilter压缩包（ZIP格式）

## 5. 可视化管理界面

### 5.1 功能概述

可视化管理界面提供友好的Web界面，用于管理许可证、查询产品信息和下载ja-netfilter工具。

### 5.2 界面组成

1. **首页**：项目介绍和功能入口
2. **许可证管理**：许可证生成和管理
3. **产品列表**：查看所有支持的JetBrains产品
4. **插件列表**：查看所有可用的JetBrains插件
5. **ja-netfilter下载**：下载和配置ja-netfilter代理工具
6. **系统设置**：配置系统参数

### 5.3 使用方法

1. **访问界面**：在浏览器中访问 `http://your-server-ip:10768`
2. **导航菜单**：使用顶部导航菜单访问各个功能模块
3. **操作按钮**：点击界面中的按钮执行相应操作
4. **查看信息**：在界面中查看产品、插件和许可证信息

## 6. 其他辅助功能

### 6.1 文件下载功能

提供许可证文件和配置文件的下载功能：

```
GET /zip/license
```

**响应**：许可证文件压缩包

### 6.2 系统信息查询

提供系统信息查询功能：

```
GET /data/system
```

**响应示例**：
```json
{
    "version": "0.0.1-SNAPSHOT",
    "javaVersion": "17.0.10",
    "os": "Windows 10",
    "memory": {
        "total": "512MB",
        "used": "256MB",
        "free": "256MB"
    },
    "uptime": "1h 30m 45s"
}
```

## 🔧 功能配置

所有功能都可以通过 `application.yml` 配置文件进行自定义：

```yaml
server:
  port: 10768  # 服务器端口

help:
  # 许可证服务器配置
  license:
    server-uid: "12345678-1234-1234-1234-123456789012"  # 服务器唯一标识符
    lease-duration: 365  # 租约有效期（天）
    validation-period: 600000  # 验证周期（毫秒）
    
  # 产品配置
  products:
    refresh-enabled: true  # 是否启用产品信息刷新
    refresh-interval: 86400000  # 刷新间隔（毫秒）
    
  # 插件配置
  plugins:
    refresh-enabled: true  # 是否启用插件信息刷新
    refresh-cron: "0 0 12 * * ?"  # 刷新时间
    page-size: 20  # 分页大小
    thread-count: 5  # 并发线程数
    timeout: 30000  # 请求超时时间（毫秒）
    
  # ja-netfilter配置
  agent:
    enabled: true  # 是否启用ja-netfilter集成
    download-url: "https://github.com/ja-netfilter/ja-netfilter/releases/latest/download/ja-netfilter.zip"  # 下载地址
    config-dir: "agent/config"  # 配置目录
    
  # 证书配置
  certificate:
    key-size: 2048  # 密钥大小
    validity: 3650  # 证书有效期（天）
    alias: "license-server"  # 证书别名
    keystore-password: "password"  # 密钥库密码
```

## 📊 功能使用统计

系统会自动统计各个功能的使用情况，可以通过以下API查询：

```
GET /data/stats
```

**响应示例**：
```json
{
    "obtainTicketCount": 100,  # 获取许可证凭证次数
    "prolongTicketCount": 50,  # 延长许可证有效期次数
    "releaseTicketCount": 20,  # 释放许可证凭证次数
    "pingCount": 150,  # 服务器状态检查次数
    "productQueryCount": 80,  # 产品查询次数
    "pluginQueryCount": 30,  # 插件查询次数
    "certificateQueryCount": 10,  # 证书查询次数
    "jaNetfilterDownloadCount": 25,  # ja-netfilter下载次数
    "uptime": 3600000  # 系统运行时间（毫秒）
}
```

## 🎯 功能最佳实践

1. **许可证服务器部署**：
   - 建议使用独立服务器部署许可证服务器
   - 确保服务器有稳定的网络连接
   - 定期备份密钥和证书文件

2. **产品激活**：
   - 使用统一的许可证服务器地址配置所有JetBrains产品
   - 定期检查激活状态，确保许可证有效
   - 避免在同一台机器上多次激活相同产品

3. **插件管理**：
   - 启用插件信息的定时更新功能
   - 根据需要过滤和搜索插件
   - 定期清理不需要的插件

4. **ja-netfilter使用**：
   - 确保ja-netfilter与JetBrains产品版本兼容
   - 定期更新ja-netfilter到最新版本
   - 自定义代理规则以满足特定需求

## 🔍 功能故障排查

### 许可证服务器故障

**问题**：JetBrains产品无法连接到许可证服务器

**排查步骤**：
1. 检查服务器是否正常运行
2. 检查网络连接和防火墙设置
3. 查看服务器日志，查找错误信息
4. 验证服务器端口是否开放
5. 检查许可证服务器地址是否正确

### 产品激活失败

**问题**：JetBrains产品激活失败

**排查步骤**：
1. 检查许可证服务器是否正常运行
2. 验证服务器地址和端口是否正确
3. 查看服务器日志，查找错误信息
4. 检查产品是否在支持列表中
5. 尝试重启JetBrains产品和许可证服务器

### 插件更新失败

**问题**：插件信息无法更新

**排查步骤**：
1. 检查网络连接是否正常
2. 验证JetBrains官方API是否可访问
3. 查看服务器日志，查找错误信息
4. 调整插件更新配置参数
5. 尝试手动更新插件信息

## 📌 功能限制

1. **产品支持**：仅支持JetBrains官方发布的产品
2. **版本兼容性**：支持JetBrains产品的最新版本
3. **并发限制**：默认支持1000个并发连接
4. **许可证数量**：无明确限制（取决于服务器性能）
5. **网络依赖**：插件更新需要访问JetBrains官方API

---

通过以上功能模块的协同工作，JetBrains License Server Help提供了完整的JetBrains产品许可证管理解决方案。