# 环境配置指南

本文档详细介绍了 JetBrains License Server Help 项目的环境要求、系统环境配置、项目配置和可选配置等内容。

## 1. 环境要求

### 1.1 硬件要求

| 硬件类型 | 最低配置 | 推荐配置 |
|---------|---------|---------|
| CPU | 2 核 | 4 核或以上 |
| 内存 | 2 GB | 4 GB 或以上 |
| 存储空间 | 500 MB | 1 GB 或以上 |
| 网络 | 可访问互联网 | 稳定的网络连接 |

### 1.2 软件要求

| 软件类型 | 版本要求 | 用途 |
|---------|---------|------|
| Java Development Kit (JDK) | 17 或以上 | 项目开发和运行环境 |
| Maven | 3.6 或以上 | 项目构建和依赖管理 |
| Git | 2.0 或以上 | 版本控制（可选） |
| Docker | 20.0 或以上 | 容器化部署（可选） |

## 2. 系统环境配置

### 2.1 JDK 安装与配置

#### 2.1.1 下载 JDK

请从 Oracle 官网或 OpenJDK 官网下载 Java 17 或以上版本的 JDK：

- Oracle JDK：[https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/)
- OpenJDK：[https://adoptium.net/](https://adoptium.net/)

#### 2.1.2 安装 JDK

**Windows 系统：**
1. 双击下载的 JDK 安装文件
2. 按照安装向导进行安装，选择合适的安装路径
3. 完成安装

**Linux 系统：**
```bash
# 使用 apt 安装 OpenJDK 17（Ubuntu/Debian）
sudo apt update
sudo apt install openjdk-17-jdk

# 使用 yum 安装 OpenJDK 17（CentOS/RHEL）
sudo yum install java-17-openjdk-devel
```

**macOS 系统：**
```bash
# 使用 Homebrew 安装 OpenJDK 17
brew install openjdk@17

# 配置环境变量
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

#### 2.1.3 验证 JDK 安装

打开终端或命令提示符，执行以下命令验证 JDK 是否安装成功：

```bash
java -version
```

如果安装成功，将显示类似以下输出：

```
java version "17.0.10"
Java(TM) SE Runtime Environment (build 17.0.10+11-LTS-240)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.10+11-LTS-240, mixed mode, sharing)
```

#### 2.1.4 配置环境变量（Windows 系统）

1. 右键点击「此电脑」或「我的电脑」，选择「属性」
2. 点击「高级系统设置」
3. 点击「环境变量」
4. 在「系统变量」中，点击「新建」，添加以下环境变量：
   - 变量名：`JAVA_HOME`
   - 变量值：JDK 安装路径（例如：`C:\Program Files\Java\jdk-17.0.10`）
5. 在「系统变量」中找到 `Path` 变量，点击「编辑」
6. 点击「新建」，添加 `%JAVA_HOME%\bin`
7. 点击「确定」保存所有设置

### 2.2 Maven 安装与配置

#### 2.2.1 下载 Maven

从 Apache Maven 官网下载最新版本的 Maven：[https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

#### 2.2.2 安装 Maven

**Windows 系统：**
1. 解压下载的 Maven 压缩包到合适的目录（例如：`C:\Program Files\Apache\maven`）
2. 配置环境变量

**Linux/macOS 系统：**
```bash
# 解压 Maven 压缩包
tar -zxvf apache-maven-3.9.6-bin.tar.gz
mv apache-maven-3.9.6 /usr/local/maven
```

#### 2.2.3 配置环境变量

**Windows 系统：**
1. 在「系统变量」中，点击「新建」，添加以下环境变量：
   - 变量名：`MAVEN_HOME`
   - 变量值：Maven 安装路径（例如：`C:\Program Files\Apache\maven`）
2. 在「系统变量」中找到 `Path` 变量，点击「编辑」
3. 点击「新建」，添加 `%MAVEN_HOME%\bin`
4. 点击「确定」保存所有设置

**Linux/macOS 系统：**
```bash
# 编辑 .bashrc 或 .zshrc 文件
echo 'export MAVEN_HOME=/usr/local/maven' >> ~/.bashrc
echo 'export PATH="$MAVEN_HOME/bin:$PATH"' >> ~/.bashrc

# 重新加载配置文件
source ~/.bashrc
```

#### 2.2.4 验证 Maven 安装

打开终端或命令提示符，执行以下命令验证 Maven 是否安装成功：

```bash
mvn -version
```

如果安装成功，将显示类似以下输出：

```
Apache Maven 3.9.6 (bc0240f3c744dd6b6ec2920b3cd08dcc295161ae)
Maven home: C:\Program Files\Apache\maven
Java version: 17.0.10, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-17.0.10
Default locale: zh_CN, platform encoding: UTF-8
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

## 3. 项目配置

### 3.1 主要配置文件

项目的主要配置文件为 `src/main/resources/application.yml`，包含以下配置项：

```yaml
spring:
  application:
    name: BlueSky-Jetbrains-LicenseServer-Help
xbase64:
  domain: BlueSky.cc
server:
  port: 10768
  # 插件信息获取配置
  plugins:
    # 是否启用定时刷新任务（true/false）
    refresh-enabled: true
    # 分页大小（每次请求获取的插件数量，建议不超过20）
    page-size: 20
    # 并发线程数（用于并行请求不同页面的插件数据）
    thread-count: 20
    # 请求超时时间（毫秒）
    timeout: 30000
```

### 3.2 配置项说明

#### 3.2.1 基本配置

| 配置项 | 默认值 | 说明 |
|-------|-------|------|
| `spring.application.name` | BlueSky-Jetbrains-LicenseServer-Help | 应用名称 |
| `xbase64.domain` | BlueSky.cc | xbase64 域名配置 |
| `server.port` | 10768 | 服务器端口 |

#### 3.2.2 插件配置

| 配置项 | 默认值 | 说明 |
|-------|-------|------|
| `server.plugins.refresh-enabled` | true | 是否启用插件信息定时刷新任务 |
| `server.plugins.page-size` | 20 | 每次请求获取的插件数量，建议不超过20 |
| `server.plugins.thread-count` | 20 | 用于并行请求不同页面插件数据的并发线程数 |
| `server.plugins.timeout` | 30000 | 请求超时时间（毫秒） |

### 3.3 扩展配置

除了 `application.yml` 中的配置，项目还支持通过以下方式进行扩展配置：

#### 3.3.1 Java 系统属性

可以通过 Java 系统属性覆盖配置项，例如：

```bash
java -jar Jetbrains-LicenseServer-Help.jar --server.port=8080
```

#### 3.3.2 命令行参数

可以通过命令行参数覆盖配置项，例如：

```bash
java -jar Jetbrains-LicenseServer-Help.jar --spring.application.name=MyLicenseServer
```

#### 3.3.3 环境变量

可以通过环境变量覆盖配置项，例如：

```bash
# Linux/macOS
export SERVER_PORT=8080
java -jar Jetbrains-LicenseServer-Help.jar

# Windows
set SERVER_PORT=8080
java -jar Jetbrains-LicenseServer-Help.jar
```

## 4. 可选配置

### 4.1 Docker 配置

如果您选择使用 Docker 部署项目，可以创建以下 `Dockerfile`：

```dockerfile
# 使用 OpenJDK 17 作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制 JAR 文件到工作目录
COPY target/Jetbrains-LicenseServer-Help.jar app.jar

# 暴露端口
EXPOSE 10768

# 运行应用
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 4.2 Docker Compose 配置

可以使用 Docker Compose 简化 Docker 部署，创建以下 `docker-compose.yml` 文件：

```yaml
version: '3'
services:
  license-server:
    build: .
    ports:
      - "10768:10768"
    volumes:
      - ./external:/app/external
    restart: always
    environment:
      - SERVER_PORT=10768
```

### 4.3 HTTPS 配置

如果需要启用 HTTPS，可以在 `application.yml` 中添加以下配置：

```yaml
server:
  port: 8443
  ssl:
    key-store: classpath:keystore.p12
    key-store-password: password
    key-store-type: PKCS12
    key-alias: tomcat
```

### 4.4 日志配置

项目使用 Spring Boot 默认的日志框架（Logback），日志配置文件为 `src/main/resources/logback-spring.xml`。可以根据需要修改日志配置，例如：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <property name="LOG_PATH" value="logs" />
    <property name="LOG_FILE" value="${LOG_PATH}/application.log" />
    
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_FILE}</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_PATH}/application.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="info">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

## 5. 配置示例

### 5.1 基本配置示例

```yaml
spring:
  application:
    name: BlueSky-Jetbrains-LicenseServer-Help
xbase64:
  domain: BlueSky.cc
server:
  port: 10768
  plugins:
    refresh-enabled: true
    page-size: 20
    thread-count: 20
    timeout: 30000
```

### 5.2 自定义端口配置示例

```yaml
spring:
  application:
    name: MyLicenseServer
xbase64:
  domain: MyDomain.com
server:
  port: 8080
  plugins:
    refresh-enabled: true
    page-size: 10
    thread-count: 10
    timeout: 60000
```

### 5.3 禁用插件刷新配置示例

```yaml
spring:
  application:
    name: BlueSky-Jetbrains-LicenseServer-Help
xbase64:
  domain: BlueSky.cc
server:
  port: 10768
  plugins:
    refresh-enabled: false
    page-size: 20
    thread-count: 20
    timeout: 30000
```

## 6. 常见问题

### 6.1 JDK 版本不兼容

**问题**：运行项目时出现 "UnsupportedClassVersionError" 错误。

**解决方案**：确保使用的 JDK 版本为 17 或以上，可以通过 `java -version` 命令检查 JDK 版本。

### 6.2 Maven 依赖下载失败

**问题**：执行 `mvn install` 时依赖下载失败。

**解决方案**：
1. 检查网络连接是否正常
2. 尝试使用国内 Maven 镜像源，例如阿里云镜像：

```xml
<!-- 在 ~/.m2/settings.xml 文件中添加 -->
<mirrors>
    <mirror>
        <id>aliyunmaven</id>
        <mirrorOf>*</mirrorOf>
        <name>阿里云公共仓库</name>
        <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
</mirrors>
```

### 6.3 端口被占用

**问题**：运行项目时出现 "Address already in use" 错误。

**解决方案**：
1. 检查端口是否被其他进程占用：
   - Linux/macOS：`lsof -i :10768`
   - Windows：`netstat -ano | findstr :10768`
2. 终止占用端口的进程，或修改项目配置使用其他端口。

### 6.4 插件信息刷新失败

**问题**：插件信息定时刷新任务失败。

**解决方案**：
1. 检查网络连接是否正常
2. 检查 JetBrains 插件 API 是否可访问
3. 调整插件配置参数，例如增加超时时间或减少并发线程数：

```yaml
server:
  plugins:
    timeout: 60000
    thread-count: 10
```

## 7. 总结

本文档详细介绍了 JetBrains License Server Help 项目的环境要求、系统环境配置、项目配置和可选配置等内容。通过正确配置环境和项目参数，可以确保项目正常运行并发挥最佳性能。

如果您在配置过程中遇到任何问题，请参考常见问题部分，或提交 Issue 寻求帮助。