# 使用说明

本文档详细介绍了 JetBrains License Server Help 项目的使用方法，包括 Web 管理界面操作、JetBrains IDE 配置、高级用法和常见问题排查等内容。

## 1. 快速开始

### 1.1 启动服务

按照[安装部署流程](./installation-deployment.md)文档的步骤启动服务后，您可以通过以下方式访问服务：

- **Web 管理界面**：`http://localhost:10768/`
- **许可证服务器 API**：`http://localhost:10768/rpc/ping.action`

### 1.2 验证服务状态

在浏览器中访问 `http://localhost:10768/rpc/ping.action`，如果看到类似以下 XML 响应，说明服务已正常启动：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<methodResponse>
    <params>
        <param>
            <value>
                <struct>
                    <member>
                        <name>status</name>
                        <value>
                            <string>UP</string>
                        </value>
                    </member>
                </struct>
            </value>
        </param>
    </params>
</methodResponse>
```

## 2. Web 管理界面

Web 管理界面是用户与系统交互的主要方式，提供了直观的操作界面来管理许可证、产品、插件等信息。

### 2.1 访问管理界面

在浏览器中输入 `http://localhost:10768/` 即可访问 Web 管理界面的登录页面。

### 2.2 登录系统

**注意**：目前系统默认使用简单认证，用户名为 `admin`，密码为 `admin`。生产环境建议修改默认密码。

1. 在登录页面输入用户名和密码
2. 点击「登录」按钮进入管理界面

### 2.3 仪表盘

登录后首先看到的是仪表盘页面，显示了系统的关键信息：

- **系统状态**：显示服务启动时间、版本信息和当前状态
- **许可证统计**：显示当前活跃的许可证数量和使用情况
- **产品信息**：显示已管理的 JetBrains 产品数量
- **插件信息**：显示已管理的插件数量
- **服务器负载**：显示当前系统的 CPU、内存和磁盘使用情况

### 2.4 许可证管理

许可证管理页面允许您查看和管理当前的许可证信息：

#### 2.4.1 查看许可证列表

在左侧菜单中点击「许可证管理」，可以查看所有当前有效的许可证信息：

- **许可证 ID**：唯一标识符
- **产品名称**：使用许可证的 JetBrains 产品名称
- **用户信息**：使用许可证的用户信息
- **开始时间**：许可证生效时间
- **结束时间**：许可证过期时间
- **状态**：许可证当前状态（活跃/已过期/已释放）

#### 2.4.2 释放许可证

如果需要手动释放某个许可证，可以点击对应的「释放」按钮。

#### 2.4.3 导出许可证记录

点击「导出记录」按钮可以将许可证使用记录导出为 CSV 文件。

### 2.5 产品管理

产品管理页面显示了系统已收集的 JetBrains 产品信息：

#### 2.5.1 查看产品列表

在左侧菜单中点击「产品管理」，可以查看所有已管理的 JetBrains 产品：

- **产品名称**：JetBrains 产品名称（如 IntelliJ IDEA Ultimate）
- **产品代码**：产品的唯一标识符（如 IDEA）
- **版本信息**：产品的最新版本
- **发布日期**：产品的发布日期
- **描述**：产品的简要描述

#### 2.5.2 刷新产品信息

点击「刷新产品信息」按钮可以手动触发产品信息的更新。

#### 2.5.3 搜索产品

使用页面顶部的搜索框可以根据产品名称或代码搜索特定产品。

### 2.6 插件管理

插件管理页面显示了系统已收集的 JetBrains 插件信息：

#### 2.6.1 查看插件列表

在左侧菜单中点击「插件管理」，可以查看所有已管理的插件：

- **插件名称**：JetBrains 插件名称
- **插件 ID**：插件的唯一标识符
- **版本信息**：插件的最新版本
- **下载次数**：插件的下载次数
- **评分**：插件的用户评分
- **发布日期**：插件的发布日期

#### 2.6.2 刷新插件信息

点击「刷新插件信息」按钮可以手动触发插件信息的更新。

#### 2.6.3 搜索插件

使用页面顶部的搜索框可以根据插件名称或 ID 搜索特定插件。

### 2.7 证书管理

证书管理页面允许您管理系统生成的 RSA 密钥对和 X.509 证书：

#### 2.7.1 查看证书信息

在左侧菜单中点击「证书管理」，可以查看当前证书的详细信息：

- **证书类型**：证书的类型（如 X.509）
- **证书有效期**：证书的开始和结束时间
- **颁发者**：证书的颁发者信息
- **主题**：证书的主题信息
- **密钥长度**：RSA 密钥对的长度

#### 2.7.2 重新生成证书

点击「重新生成证书」按钮可以生成新的 RSA 密钥对和 X.509 证书。

**注意**：重新生成证书会导致现有许可证失效，请谨慎操作。

#### 2.7.3 下载证书

点击「下载证书」按钮可以将当前证书下载为 PEM 格式的文件。

### 2.8 Ja-netfilter 管理

Ja-netfilter 管理页面允许您配置和下载 ja-netfilter 代理工具：

#### 2.8.1 配置代理

在「代理配置」部分，您可以设置以下参数：

- **代理地址**：ja-netfilter 代理的地址
- **代理端口**：ja-netfilter 代理的端口
- **过滤规则**：自定义过滤规则

#### 2.8.2 下载代理工具

点击「下载代理工具」按钮可以下载配置好的 ja-netfilter 工具包。

#### 2.8.3 查看使用说明

点击「使用说明」按钮可以查看 ja-netfilter 工具的详细使用指南。

### 2.9 系统设置

系统设置页面允许您修改系统的基本配置：

#### 2.9.1 服务器设置

- **服务器端口**：修改服务的监听端口
- **超时设置**：修改请求超时时间

#### 2.9.2 插件设置

- **自动刷新**：启用/禁用插件信息的自动刷新
- **刷新间隔**：设置插件信息的自动刷新间隔
- **并发线程数**：设置插件信息刷新的并发线程数

#### 2.9.3 安全设置

- **修改密码**：修改管理员密码
- **启用 HTTPS**：启用/禁用 HTTPS 协议

## 3. JetBrains IDE 配置

要将 JetBrains IDE 配置为使用本许可证服务器，需要按照以下步骤操作：

### 3.1 打开许可证配置

1. 启动 JetBrains IDE（以 IntelliJ IDEA 为例）
2. 在欢迎界面点击「Configure」→「Manage License」
3. 或者在 IDE 中点击「Help」→「Register」

### 3.2 选择许可证服务器

1. 在注册窗口中选择「License server」
2. 在「License server address」输入框中填写许可证服务器地址：
   ```
   http://your-server-ip:10768
   ```
3. 点击「Activate」按钮

### 3.3 验证激活状态

如果配置正确，IDE 将显示激活成功的消息，并显示许可证有效期。

### 3.4 配置多个 IDE

对于多个团队成员使用的情况，每个团队成员都需要在各自的 IDE 中进行相同的配置。

## 4. 高级用法

### 4.1 API 调用

系统提供了 RESTful API 接口，可以用于自动化管理和集成到其他系统中。

#### 4.1.1 获取系统状态

```bash
curl http://localhost:10768/api/status
```

**响应示例**：

```json
{
  "status": "UP",
  "version": "1.0.0",
  "startTime": "2023-10-01T12:00:00Z",
  "licenseCount": 10,
  "productCount": 25,
  "pluginCount": 1000
}
```

#### 4.1.2 获取产品列表

```bash
curl http://localhost:10768/api/products
```

**响应示例**：

```json
{
  "products": [
    {
      "name": "IntelliJ IDEA Ultimate",
      "code": "IDEA",
      "version": "2023.2",
      "releaseDate": "2023-07-27"
    },
    {
      "name": "PyCharm Professional",
      "code": "PY",
      "version": "2023.2",
      "releaseDate": "2023-07-27"
    }
  ],
  "total": 25
}
```

#### 4.1.3 获取插件列表

```bash
curl http://localhost:10768/api/plugins?page=1&size=10
```

**响应示例**：

```json
{
  "plugins": [
    {
      "id": "com.intellij.java",
      "name": "Java",
      "version": "232.8660.185",
      "downloads": 1000000,
      "rating": 4.8
    }
  ],
  "total": 1000,
  "page": 1,
  "size": 10
}
```

### 4.2 命令行操作

系统支持通过命令行参数进行配置和操作。

#### 4.2.1 自定义配置启动

```bash
java -jar Jetbrains-LicenseServer-Help.jar \
  --server.port=8080 \
  --server.plugins.refresh-enabled=true \
  --server.plugins.page-size=30
```

#### 4.2.2 使用环境变量配置

```bash
export SERVER_PORT=8080
export SERVER_PLUGINS_REFRESH_ENABLED=true
export SERVER_PLUGINS_PAGE_SIZE=30

java -jar Jetbrains-LicenseServer-Help.jar
```

### 4.3 自定义配置

您可以通过修改 `src/main/resources/application.yml` 文件来进行更详细的配置：

```yaml
spring:
  application:
    name: BlueSky-Jetbrains-LicenseServer-Help
xbase64:
  domain: BlueSky.cc
server:
  port: 10768
  tomcat:
    threads:
      max: 200
    connection-timeout: 20000
  plugins:
    refresh-enabled: true
    page-size: 20
    thread-count: 20
    timeout: 30000
    refresh-interval: 3600000

logging:
  level:
    com.bluesky: INFO
    org.springframework: WARN
```

## 5. 常见问题排查

### 5.1 IDE 无法连接到许可证服务器

**问题**：IDE 显示无法连接到许可证服务器

**解决方法**：

1. 检查服务器是否正在运行
   ```bash
   curl http://localhost:10768/rpc/ping.action
   ```

2. 检查防火墙/安全组是否已开放 10768 端口

3. 检查 IDE 中输入的许可证服务器地址是否正确
   - 确保地址格式为：`http://your-server-ip:10768`
   - 确保 IP 地址或域名正确

### 5.2 许可证激活失败

**问题**：IDE 显示许可证激活失败

**解决方法**：

1. 检查服务器日志是否有错误信息
   ```bash
   tail -f server.log
   ```

2. 检查 IDE 的网络连接是否正常

3. 尝试重启许可证服务器

### 5.3 插件信息不完整

**问题**：Web 界面显示的插件信息不完整或过时

**解决方法**：

1. 手动刷新插件信息
   - 在 Web 管理界面的插件管理页面点击「刷新插件信息」

2. 检查网络连接是否正常

3. 调整插件配置参数
   ```yaml
   server:
     plugins:
       refresh-enabled: true
       page-size: 20
       thread-count: 20
       timeout: 60000
   ```

### 5.4 服务性能问题

**问题**：许可证服务器响应缓慢

**解决方法**：

1. 检查服务器资源使用情况
   ```bash
   top  # Linux
   tasklist  # Windows
   ```

2. 增加服务器资源（CPU、内存）

3. 优化配置参数
   ```yaml
   server:
     tomcat:
       threads:
         max: 500
     plugins:
       thread-count: 10  # 减少并发线程数
   ```

## 6. 最佳实践

### 6.1 生产环境配置

1. **修改默认密码**：生产环境必须修改默认的管理员密码

2. **启用 HTTPS**：生产环境建议启用 HTTPS 协议

3. **备份数据**：定期备份证书和配置文件

4. **监控服务**：部署监控工具（如 Prometheus + Grafana）监控服务状态

### 6.2 性能优化

1. **合理配置线程数**：根据服务器性能调整插件刷新的并发线程数

2. **设置缓存**：启用数据缓存减少数据库访问

3. **定期清理**：定期清理过期的许可证记录

### 6.3 安全建议

1. **限制访问**：配置防火墙/安全组只允许特定 IP 访问

2. **定期更新**：及时更新系统和依赖库

3. **审计日志**：启用审计日志记录所有重要操作

## 7. 总结

本文档详细介绍了 JetBrains License Server Help 项目的使用方法，包括：

- ✅ Web 管理界面的各项功能操作
- ✅ JetBrains IDE 的配置方法
- ✅ 高级用法（API 调用、命令行操作、自定义配置）
- ✅ 常见问题排查
- ✅ 最佳实践建议

通过本文档的指导，您可以轻松上手使用 JetBrains License Server Help 项目，并充分发挥其功能。

如果您在使用过程中遇到任何问题，请参考[常见问题解答](./faq.md)文档或提交 Issue 寻求帮助。