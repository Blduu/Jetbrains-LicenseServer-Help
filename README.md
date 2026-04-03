# JetBrains License Server Help

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-blue.svg)](https://spring.io/projects/spring-boot)

一个功能强大的JetBrains产品许可证服务器帮助工具，提供许可证生成、验证和服务器模拟功能。

## 📚 文档导航

### 中文文档
- [项目概述](docs/zh_CN/README.md#项目概述)
- [核心功能](docs/zh_CN/README.md#核心功能)
- [技术架构](docs/zh_CN/README.md#技术架构)
- [环境配置](docs/zh_CN/README.md#环境配置)
- [安装部署](docs/zh_CN/README.md#安装部署)
- [使用说明](docs/zh_CN/README.md#使用说明)
- [API接口文档](docs/zh_CN/README.md#api接口文档)
- [贡献指南](docs/zh_CN/README.md#贡献指南)
- [常见问题](docs/zh_CN/README.md#常见问题)
- [许可证信息](docs/zh_CN/README.md#许可证信息)

### 英文文档
- [Project Overview](docs/en_US/README.md#project-overview)
- [Core Features](docs/en_US/README.md#core-features)
- [Technology Stack](docs/en_US/README.md#technology-stack)
- [Environment Configuration](docs/en_US/README.md#environment-configuration)
- [Installation and Deployment](docs/en_US/README.md#installation-and-deployment)
- [Usage Instructions](docs/en_US/README.md#usage-instructions)
- [API Documentation](docs/en_US/README.md#api-documentation)
- [Contributing Guide](docs/en_US/README.md#contributing-guide)
- [Frequently Asked Questions](docs/en_US/README.md#frequently-asked-questions)
- [License Information](docs/en_US/README.md#license-information)

### 语言切换
- [中文文档](docs/zh_CN/README.md)
- [English Documentation](docs/en_US/README.md)

## ✨ 核心功能

- 🎯 **许可证生成**：支持生成JetBrains全系列产品的许可证
- 🖥️ **服务器模拟**：兼容JetBrains官方许可证服务器协议
- 📦 **产品管理**：内置完整的JetBrains产品和插件信息
- 🔒 **安全保障**：使用RSA签名保证数据安全
- 📱 **ja-netfilter集成**：内置代理工具下载和配置
- 🎨 **可视化界面**：提供友好的Web管理界面

## 🛠️ 技术栈

- **后端框架**：Spring Boot 4.0
- **开发语言**：Java 17
- **核心依赖**：
  - Hutool 工具类库
  - Lombok 简化代码
  - BouncyCastle 加密库
  - JAXB XML处理
- **前端技术**：HTML5 + CSS3 + JavaScript

## 🚀 快速开始

### 环境要求

- JDK 17 或更高版本
- Maven 3.6 或更高版本（可选，用于编译）

### 运行方式

#### 方式一：直接运行JAR包

```bash
# 下载最新版本JAR包
java -jar Jetbrains-LicenseServer-Help.jar
```

#### 方式二：使用Docker

```bash
docker run -d -p 10768:10768 --name license-server-help your-image-name
```

#### 方式三：本地编译运行

```bash
# 克隆仓库
git clone https://github.com/Blduu/Jetbrains-LicenseServer-Help.git

# 进入项目目录
cd Jetbrains-LicenseServer-Help

# 编译项目
mvn clean package -DskipTests

# 运行项目
java -jar target/Jetbrains-LicenseServer-Help.jar
```

## 📖 使用说明

1. **访问管理界面**：运行项目后，在浏览器中访问 `http://localhost:10768`
2. **生成许可证**：根据需要选择产品类型和版本，生成对应的许可证
3. **配置JetBrains产品**：
   - 打开JetBrains产品（如IntelliJ IDEA）
   - 选择 "Help" -> "Register"
   - 选择 "License server"
   - 输入许可证服务器地址：`http://localhost:10768`
   - 点击 "Activate" 完成激活

## 📝 贡献指南

欢迎提交Issue和Pull Request！请阅读[贡献指南](docs/zh_CN/contributing.md)了解更多信息。

## 📄 许可证

本项目采用MIT许可证，详见[LICENSE](LICENSE)文件。

## 🤝 支持

如果您在使用过程中遇到问题，请：

1. 查看[常见问题解答](docs/zh_CN/faq.md)
2. 提交[Issue](https://github.com/Blduu/Jetbrains-LicenseServer-Help/issues)
3. 发送邮件至：3205480565@qq.com

## 🙏 致谢

感谢所有为本项目做出贡献的开发者和用户！

---

**注意**：本项目仅供学习和研究使用，请遵守JetBrains的软件许可协议。
