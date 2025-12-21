# JetBrains License Server Help

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-blue.svg)](https://spring.io/projects/spring-boot)

一个功能强大的JetBrains产品许可证服务器帮助工具，提供许可证生成、验证和服务器模拟功能。

## 🌐 语言切换

- [中文文档](README.md)
- [English Documentation](../en_US/README.md)

## 📖 文档导航

- [项目概述](#项目概述)
- [核心功能](core-features.md)
- [技术架构](technical-architecture.md)
- [环境配置](environment-configuration.md)
- [安装部署](installation-deployment.md)
- [使用说明](usage-guide.md)
- [API接口文档](api-documentation.md)
- [贡献指南](contributing.md)
- [常见问题](faq.md)
- [许可证信息](license.md)

## 📋 项目概述

JetBrains License Server Help是一个基于Spring Boot开发的许可证服务器辅助工具，旨在帮助用户管理和使用JetBrains产品的许可证。该工具提供了完整的许可证生成、验证和服务器模拟功能，兼容JetBrains官方许可证服务器协议。

### 项目特点

- ✅ **开源免费**：基于MIT许可证开源
- ✅ **易于使用**：提供友好的Web管理界面
- ✅ **安全可靠**：使用RSA签名保证数据安全
- ✅ **功能完整**：支持JetBrains全系列产品
- ✅ **轻量级**：无外部依赖，单个JAR包即可运行
- ✅ **跨平台**：支持Windows、Linux、macOS等主流操作系统

## ✨ 核心功能

### 1. 许可证服务器模拟

- 兼容JetBrains官方许可证服务器API协议
- 支持许可证凭证的生成、验证和延长
- 提供完整的XML格式响应，使用RSA签名保证数据安全

### 2. 产品和插件管理

- 内置完整的JetBrains产品信息数据库
- 支持插件信息的定时更新
- 提供产品和插件信息的查询接口

### 3. ja-netfilter集成

- 内置ja-netfilter代理工具下载功能
- 提供代理工具的自动配置
- 支持自定义代理规则

### 4. 可视化管理界面

- 友好的Web管理界面
- 实时监控许可证使用情况
- 支持许可证信息的导出和导入

## 🏗️ 技术架构

### 系统架构

```
┌─────────────────────────────────────────────────────────┐
│                    前端Web界面                          │
└─────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────┐
│                    Spring Boot后端                      │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     │
│  │  控制器层   │  │  服务层     │  │  数据层     │     │
│  └─────────────┘  └─────────────┘  └─────────────┘     │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     │
│  │  API接口    │  │  业务逻辑   │  │  缓存管理   │     │
│  └─────────────┘  └─────────────┘  └─────────────┘     │
└─────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────┐
│                    外部系统集成                          │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     │
│  │ JetBrains   │  │ ja-netfilter│  │  证书管理   │     │
│  │  产品       │  │  代理工具   │  │  系统       │     │
│  └─────────────┘  └─────────────┘  └─────────────┘     │
└─────────────────────────────────────────────────────────┘
```

### 核心模块

| 模块 | 主要职责 | 关键类/接口 |
|------|---------|------------|
| 许可证服务器 | 模拟官方许可证服务器 | LicenseServerController |
| 产品管理 | 管理JetBrains产品信息 | ProductsContextHolder |
| 插件管理 | 管理JetBrains插件信息 | PluginsContextHolder |
| 证书管理 | 管理RSA密钥和证书 | CertificateContextHolder |
| 代理管理 | 管理ja-netfilter代理 | AgentContextHolder |
| 工具类 | 提供通用工具方法 | LicenseServerUtils, FileTools |

### 技术栈

| 类别 | 技术/框架 | 版本 |
|------|----------|------|
| 后端框架 | Spring Boot | 4.0.0 |
| 开发语言 | Java | 17 |
| 构建工具 | Maven | 3.6+ |
| 工具类库 | Hutool | 5.8.28 |
| 代码简化 | Lombok | 1.18.34 |
| 加密库 | BouncyCastle | 1.78.1 |
| XML处理 | JAXB | 4.0.1 |
| 前端技术 | HTML5 + CSS3 + JavaScript | - |

## 🛠️ 环境配置

### 硬件要求

- CPU：至少1核
- 内存：至少512MB
- 存储空间：至少100MB

### 软件要求

- JDK 17 或更高版本
- Maven 3.6 或更高版本（可选，用于编译）
- Docker（可选，用于容器化部署）

### 网络要求

- 服务器需要开放10768端口（默认）
- 客户端需要能够访问服务器的10768端口

## 🚀 安装部署

### 方式一：直接运行JAR包

1. **下载JAR包**

   从GitHub Releases页面下载最新版本的JAR包：
   ```bash
   wget https://github.com/Blduu/Jetbrains-LicenseServer-Help/releases/latest/download/Jetbrains-LicenseServer-Help.jar
   ```

2. **运行JAR包**

   ```bash
   java -jar Jetbrains-LicenseServer-Help.jar
   ```

3. **访问管理界面**

   在浏览器中访问：`http://localhost:10768`

### 方式二：使用Docker

1. **拉取Docker镜像**

   ```bash
   docker pull your-username/jetbrains-license-server-help:latest
   ```

2. **运行Docker容器**

   ```bash
   docker run -d -p 10768:10768 --name license-server-help your-username/jetbrains-license-server-help:latest
   ```

3. **访问管理界面**

   在浏览器中访问：`http://localhost:10768`

### 方式三：本地编译运行

1. **克隆仓库**

   ```bash
   git clone https://github.com/Blduu/Jetbrains-LicenseServer-Help.git
   ```

2. **进入项目目录**

   ```bash
   cd Jetbrains-LicenseServer-Help
   ```

3. **编译项目**

   ```bash
   mvn clean package -DskipTests
   ```

4. **运行项目**

   ```bash
   java -jar target/Jetbrains-LicenseServer-Help.jar
   ```

5. **访问管理界面**

   在浏览器中访问：`http://localhost:10768`

### 自定义配置

可以通过命令行参数或配置文件自定义项目配置：

```bash
# 自定义端口
sudo java -jar Jetbrains-LicenseServer-Help.jar --server.port=8080

# 自定义日志级别
sudo java -jar Jetbrains-LicenseServer-Help.jar --logging.level.root=DEBUG
```

## 📖 使用说明

### 1. 许可证服务器配置

#### 配置JetBrains产品

1. 打开JetBrains产品（如IntelliJ IDEA）
2. 选择 "Help" -> "Register"
3. 选择 "License server"
4. 输入许可证服务器地址：`http://your-server-ip:10768`
5. 点击 "Activate" 完成激活

#### 配置多个许可证服务器

如果需要配置多个许可证服务器，可以在JetBrains产品中输入多个地址，用逗号分隔：

```
http://server1:10768,http://server2:10768
```

### 2. ja-netfilter使用

1. 在管理界面中点击 "ja-netfilter下载" 按钮
2. 下载完成后解压到任意目录
3. 运行ja-netfilter目录中的启动脚本
4. 配置JetBrains产品使用ja-netfilter代理

### 3. 许可证管理

- **查看许可证信息**：在管理界面中查看当前许可证的使用情况
- **导出许可证信息**：支持将许可证信息导出为JSON格式
- **导入许可证信息**：支持从JSON文件导入许可证信息
- **重置许可证**：可以重置所有许可证信息

## 📊 API接口文档

### 1. 许可证服务器接口

#### 获取许可证凭证

```
POST /rpc/obtainTicket.action
```

**请求参数**：
- hostName: 客户端主机名
- machineId: 客户端机器ID
- salt: 加密盐值

**响应**：XML格式的许可证凭证信息

#### 延长许可证有效期

```
POST /rpc/prolongTicket.action
```

**请求参数**：
- ticketId: 许可证凭证ID
- machineId: 客户端机器ID
- salt: 加密盐值

**响应**：XML格式的延长结果信息

#### 释放许可证凭证

```
POST /rpc/releaseTicket.action
```

**请求参数**：
- ticketId: 许可证凭证ID
- machineId: 客户端机器ID
- salt: 加密盐值

**响应**：XML格式的释放结果信息

#### 服务器状态检查

```
POST /rpc/ping.action
```

**响应**：XML格式的服务器状态信息

### 2. 数据接口

#### 获取产品列表

```
GET /data/products
```

**响应**：JSON格式的产品列表

#### 获取插件列表

```
GET /data/plugins
```

**响应**：JSON格式的插件列表

#### 获取证书信息

```
GET /data/certificate
```

**响应**：JSON格式的证书信息

### 3. 下载接口

#### 下载ja-netfilter

```
GET /zip/ja-netfilter
```

**响应**：ja-netfilter压缩包

## 🤝 贡献指南

欢迎提交Issue和Pull Request！请阅读[贡献指南](contributing.md)了解更多信息。

## ❓ 常见问题

### 1. 无法连接到许可证服务器

**解决方案**：
- 检查服务器是否正常运行
- 检查网络连接是否正常
- 检查防火墙是否开放了10768端口
- 检查许可证服务器地址是否正确

### 2. 许可证激活失败

**解决方案**：
- 检查许可证服务器地址是否正确
- 检查网络连接是否正常
- 检查服务器日志，查看具体错误信息
- 尝试重新生成许可证

### 3. 产品无法识别

**解决方案**：
- 检查产品是否在支持列表中
- 尝试更新产品信息数据库
- 联系项目维护者获取帮助

### 4. ja-netfilter无法正常工作

**解决方案**：
- 检查ja-netfilter是否正确安装
- 检查代理配置是否正确
- 尝试重新下载ja-netfilter
- 查看ja-netfilter日志，了解具体错误信息

更多常见问题请查看[FAQ文档](faq.md)。

## 📄 许可证信息

本项目采用MIT许可证开源，详见[LICENSE](../LICENSE)文件。

### 第三方依赖许可证

- Spring Boot: Apache License 2.0
- Hutool: Apache License 2.0
- Lombok: MIT License
- BouncyCastle: MIT License
- JAXB: CDDL + GPLv2 License

## 📞 联系方式

- **项目地址**：https://github.com/Blduu/Jetbrains-LicenseServer-Help
- **Issue跟踪**：https://github.com/Blduu/Jetbrains-LicenseServer-Help/issues
- **邮件联系**：3205480565@qq.com

## 🙏 致谢

感谢所有为本项目做出贡献的开发者和用户！

---

**注意**：本项目仅供学习和研究使用，请遵守JetBrains的软件许可协议。
