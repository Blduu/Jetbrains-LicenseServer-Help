# 常见问题解答 (FAQ)

本文档收集了使用 JetBrains License Server Help 过程中常见的问题和解答，帮助您快速解决遇到的问题。

## 1. 安装和配置

### 1.1 如何安装 JDK？

JetBrains License Server Help 需要 JDK 17 或更高版本。您可以按照以下步骤安装：

1. 访问 [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) 或 [OpenJDK](https://adoptium.net/) 下载页面
2. 选择适合您操作系统的 JDK 17+ 版本并下载
3. 按照安装向导完成安装
4. 配置 `JAVA_HOME` 环境变量指向 JDK 安装目录
5. 将 JDK 的 `bin` 目录添加到系统的 `PATH` 环境变量中

验证安装是否成功：

```bash
java -version
```

### 1.2 如何配置 Maven？

1. 访问 [Maven 官网](https://maven.apache.org/download.cgi) 下载 Maven 3.6+ 版本
2. 解压下载的压缩包到指定目录
3. 配置 `M2_HOME` 环境变量指向 Maven 安装目录
4. 将 Maven 的 `bin` 目录添加到系统的 `PATH` 环境变量中

验证安装是否成功：

```bash
mvn -version
```

### 1.3 如何修改服务器端口？

您可以通过修改 `src/main/resources/application.yml` 文件中的 `server.port` 配置来更改服务器端口：

```yaml
server:
  port: 10768  # 修改为您需要的端口号
```

### 1.4 如何配置插件刷新设置？

在 `application.yml` 文件中可以配置插件刷新相关的设置：

```yaml
server:
  plugins:
    refresh-enabled: true  # 是否启用插件刷新
    page-size: 20  # 每次请求的页面大小
    thread-count: 20  # 刷新线程数
    timeout: 30000  # 请求超时时间（毫秒）
```

## 2. 部署

### 2.1 如何使用 Docker 部署？

1. 确保已安装 Docker
2. 在项目根目录创建 `Dockerfile`（如果不存在）
3. 构建 Docker 镜像：

```bash
docker build -t jetbrains-license-server-help .
```

4. 运行 Docker 容器：

```bash
docker run -d -p 10768:10768 --name license-server-help jetbrains-license-server-help
```

### 2.2 如何配置防火墙？

如果您的服务器启用了防火墙，需要开放相应的端口（默认 10768）：

- **Windows**：在 Windows 防火墙设置中添加入站规则，允许端口 10768
- **Linux (iptables)**：
  ```bash
iptables -A INPUT -p tcp --dport 10768 -j ACCEPT
  ```
- **Linux (firewalld)**：
  ```bash
firewall-cmd --add-port=10768/tcp --permanent
firewall-cmd --reload
  ```

### 2.3 如何在不同操作系统上部署？

- **Windows**：使用 `java -jar target/Jetbrains-LicenseServer-Help-1.0-SNAPSHOT.jar` 命令运行
- **Linux**：使用 `nohup java -jar target/Jetbrains-LicenseServer-Help-1.0-SNAPSHOT.jar > server.log 2>&1 &` 命令后台运行
- **Mac**：与 Linux 类似，可以使用 `nohup` 命令后台运行

## 3. 使用

### 3.1 如何生成许可证？

1. 访问 web 管理界面（默认地址：http://localhost:10768）
2. 点击 "生成许可证" 按钮
3. 填写许可证信息（许可证名称、分配者名称、过期日期等）
4. 选择产品（可选）
5. 点击 "生成" 按钮
6. 复制生成的许可证代码

### 3.2 如何在 JetBrains IDE 中配置许可证服务器？

1. 打开 JetBrains IDE（如 IntelliJ IDEA）
2. 进入 "Help" -> "Register" 或 "Configure" -> "Settings" -> "Appearance & Behavior" -> "System Settings" -> "License"
3. 选择 "License Server"
4. 输入许可证服务器地址（如：http://your-server-ip:10768）
5. 点击 "Activate" 按钮

### 3.3 ja-netfilter 工具如何使用？

1. 从 web 管理界面或通过 API（http://localhost:10768/ja-netfilter）下载 ja-netfilter 工具
2. 解压下载的 ZIP 文件
3. 根据需要修改 `config` 目录下的配置文件
4. 在 IDE 的启动脚本中添加 ja-netfilter 代理：

```bash
-javaagent:/path/to/ja-netfilter/ja-netfilter.jar
```

### 3.4 如何更新产品和插件列表？

产品和插件列表会自动从 JetBrains 官方 API 获取并更新。您可以在 `application.yml` 中配置刷新设置：

```yaml
server:
  plugins:
    refresh-enabled: true  # 启用自动刷新
```

## 4. 故障排除

### 4.1 服务器无法启动怎么办？

1. 检查 JDK 版本是否符合要求（JDK 17+）
2. 检查端口是否被占用：
   - Windows：`netstat -ano | findstr 10768`
   - Linux/Mac：`lsof -i :10768` 或 `netstat -tuln | grep 10768`
3. 检查日志文件（如果使用了日志输出）
4. 尝试使用 `mvn clean package` 重新构建项目

### 4.2 IDE 无法连接到许可证服务器怎么办？

1. 检查服务器是否正在运行：`curl http://localhost:10768`
2. 检查服务器地址是否正确（IP 和端口）
3. 检查网络连接和防火墙设置
4. 检查 IDE 中的代理设置
5. 查看 IDE 的日志文件获取更多信息

### 4.3 许可证无法激活怎么办？

1. 检查许可证服务器是否正常运行
2. 检查许可证代码是否正确
3. 检查许可证是否已过期
4. 检查 IDE 的网络连接
5. 尝试重新生成许可证

### 4.4 日志文件在哪里？

如果您使用了日志框架，可以在项目的 `logback.xml` 或 `log4j2.xml` 配置文件中查看日志输出位置。默认情况下，日志可能输出到控制台或项目根目录下的日志文件中。

如果使用命令行运行，可以将日志重定向到文件：

```bash
java -jar target/Jetbrains-LicenseServer-Help-1.0-SNAPSHOT.jar > server.log 2>&1
```

## 5. 其他问题

### 5.1 项目支持哪些 JetBrains 产品？

项目支持所有 JetBrains 商业产品，包括但不限于：
- IntelliJ IDEA Ultimate
- PhpStorm
- PyCharm Professional
- WebStorm
- Rider
- DataGrip
- CLion
- GoLand
- RubyMine
- AppCode

### 5.2 如何更新项目版本？

1. 从 GitHub 拉取最新代码：`git pull origin main`
2. 重新构建项目：`mvn clean package`
3. 停止旧版本的服务器
4. 启动新版本的服务器

### 5.3 如何贡献代码？

请参考 [贡献指南](./contributing.md) 文档了解如何为项目贡献代码。

### 5.4 如何获取帮助？

如果您遇到了本 FAQ 中未提及的问题，可以通过以下方式获取帮助：

- 在 GitHub 项目页面提交 [Issue](https://github.com/yourusername/Jetbrains-LicenseServer-Help/issues)
- 查看项目文档
- 联系项目维护者

## 6. 更新日志

### 1.0.0 (2023-10-01)

- 初始版本发布
- 支持许可证服务器模拟
- 支持产品和插件管理
- 支持许可证代码生成
- 支持 ja-netfilter 集成

### 1.1.0 (2023-11-15)

- 优化了 web 管理界面
- 增加了产品自动更新功能
- 修复了已知 Bug

## 7. 联系我们

如果您有任何问题或建议，欢迎联系我们：

- GitHub Issue: [https://github.com/yourusername/Jetbrains-LicenseServer-Help/issues](https://github.com/yourusername/Jetbrains-LicenseServer-Help/issues)
- Email: your-email@example.com
