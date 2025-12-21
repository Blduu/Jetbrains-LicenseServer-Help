# 安装部署流程

本文档详细介绍了 JetBrains License Server Help 项目的安装和部署流程，包括安装前准备、项目获取、构建和多种部署方式。

## 1. 安装前准备

在开始安装部署前，请确保您已完成以下准备工作：

### 1.1 环境检查

请确认您的系统已满足以下环境要求（详细配置请参考[环境配置指南](./environment-configuration.md)）：

- ✅ JDK 17 或以上版本已安装并配置
- ✅ Maven 3.6 或以上版本已安装并配置
- ✅ Git（可选，用于克隆项目代码）
- ✅ Docker（可选，用于容器化部署）
- ✅ 足够的磁盘空间（推荐 1 GB 以上）
- ✅ 稳定的网络连接

### 1.2 端口准备

确保项目所需的端口未被占用：

| 端口 | 用途 | 默认值 |
|------|------|--------|
| 10768 | 许可证服务器端口 | 10768 |

可以通过修改 `src/main/resources/application.yml` 文件中的 `server.port` 配置项来更改端口。

## 2. 获取项目代码

### 2.1 从 GitHub 克隆代码

使用 Git 克隆项目代码到本地：

```bash
git clone https://github.com/Blduu/Jetbrains-LicenseServer-Help.git
cd Jetbrains-LicenseServer-Help
```

### 2.2 下载发布包

如果您不想克隆整个项目，可以直接从 GitHub Releases 页面下载最新的发布包：

1. 访问项目的 GitHub Releases 页面
2. 下载最新版本的 `Jetbrains-LicenseServer-Help.jar` 文件

## 3. 构建项目

如果您从 GitHub 克隆了代码，需要先构建项目生成可执行 JAR 文件。

### 3.1 编译项目

执行以下命令编译项目：

```bash
mvn compile
```

### 3.2 运行测试

可选步骤，执行测试用例：

```bash
mvn test
```

### 3.3 打包项目

执行以下命令打包项目生成可执行 JAR 文件：

```bash
mvn package -DskipTests
```

打包完成后，可执行 JAR 文件将生成在 `target` 目录下，文件名格式为 `Jetbrains-LicenseServer-Help-<version>.jar`。

## 4. 部署方式

### 4.1 直接运行 JAR 文件

最简单的部署方式是直接运行生成的 JAR 文件。

#### 4.1.1 基本运行

```bash
java -jar target/Jetbrains-LicenseServer-Help-<version>.jar
```

或使用下载的发布包：

```bash
java -jar Jetbrains-LicenseServer-Help.jar
```

#### 4.1.2 自定义配置运行

可以通过命令行参数或环境变量自定义配置：

```bash
# 使用命令行参数指定端口
java -jar target/Jetbrains-LicenseServer-Help-<version>.jar --server.port=8080

# 使用环境变量指定配置
SERVER_PORT=8080 java -jar target/Jetbrains-LicenseServer-Help-<version>.jar
```

#### 4.1.3 后台运行

**Linux/macOS 系统：**

```bash
nohup java -jar target/Jetbrains-LicenseServer-Help-<version>.jar > server.log 2>&1 &
```

**Windows 系统：**

使用 `start` 命令在后台运行：

```cmd
start /B java -jar target\Jetbrains-LicenseServer-Help-<version>.jar > server.log 2>&1
```

### 4.2 使用 Docker 部署

如果您已安装 Docker，可以使用 Docker 容器化部署项目。

#### 4.2.1 创建 Dockerfile

在项目根目录下创建 `Dockerfile`：

```dockerfile
# 使用 OpenJDK 17 作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制 JAR 文件到工作目录
COPY target/Jetbrains-LicenseServer-Help-<version>.jar app.jar

# 暴露端口
EXPOSE 10768

# 运行应用
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### 4.2.2 构建 Docker 镜像

```bash
docker build -t jetbrains-license-server-help .
```

#### 4.2.3 运行 Docker 容器

```bash
docker run -d -p 10768:10768 --name license-server jetbrains-license-server-help
```

#### 4.2.4 挂载外部配置

如果需要持久化数据或使用外部配置文件，可以挂载卷：

```bash
docker run -d -p 10768:10768 \
  -v ./external:/app/external \
  -v ./application.yml:/app/application.yml \
  --name license-server jetbrains-license-server-help
```

### 4.3 使用 Docker Compose 部署

使用 Docker Compose 可以更方便地管理容器化部署。

#### 4.3.1 创建 docker-compose.yml

在项目根目录下创建 `docker-compose.yml` 文件：

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
      - SPRING_APPLICATION_NAME=BlueSky-Jetbrains-LicenseServer-Help
```

#### 4.3.2 构建并启动服务

```bash
docker-compose up -d
```

#### 4.3.3 停止服务

```bash
docker-compose down
```

#### 4.3.4 查看日志

```bash
docker-compose logs -f
```

## 5. 验证部署结果

### 5.1 检查服务状态

部署完成后，可以通过以下方式检查服务是否正常运行：

#### 5.1.1 查看进程状态

**Linux/macOS 系统：**

```bash
ps aux | grep Jetbrains-LicenseServer-Help
```

**Windows 系统：**

```cmd
tasklist | findstr java
```

#### 5.1.2 测试 API 接口

使用 `curl` 或浏览器访问以下 URL 测试服务是否正常响应：

```bash
# 测试许可证服务器状态
curl http://localhost:10768/rpc/ping.action

# 测试 Web 管理界面
curl http://localhost:10768/
```

如果服务正常运行，您将收到相应的 XML 响应或 HTML 内容。

#### 5.1.3 查看日志

**直接运行 JAR 文件：**

查看控制台输出或指定的日志文件（如 `server.log`）。

**Docker 容器：**

```bash
docker logs -f license-server
```

**Docker Compose：**

```bash
docker-compose logs -f
```

## 6. 配置防火墙/安全组

### 6.1 Linux 系统（使用 iptables）

```bash
# 允许 10768 端口的 TCP 连接
sudo iptables -A INPUT -p tcp --dport 10768 -j ACCEPT

# 保存规则
sudo iptables-save > /etc/iptables/rules.v4
```

### 6.2 Windows 系统（使用防火墙）

1. 打开「控制面板」→「系统和安全」→「Windows Defender 防火墙」
2. 点击「高级设置」
3. 点击「入站规则」→「新建规则」
4. 选择「端口」→「下一步」
5. 选择「TCP」→ 输入「10768」作为特定本地端口 →「下一步」
6. 选择「允许连接」→「下一步」
7. 选择应用场景（域、专用、公用）→「下一步」
8. 输入规则名称（如 "JetBrains License Server"）→「完成」

### 6.3 云服务器安全组

如果您在云服务器上部署项目，需要在安全组中开放 10768 端口：

1. 登录云服务提供商控制台
2. 找到对应的安全组配置
3. 添加入站规则，允许 TCP 协议的 10768 端口访问
4. 保存配置

## 7. 自动化部署

### 7.1 使用 Shell 脚本部署

创建一个部署脚本 `deploy.sh` 自动化部署过程：

```bash
#!/bin/bash

# 部署脚本

# 项目版本
VERSION="1.0.0"

# 停止旧服务
pkill -f "Jetbrains-LicenseServer-Help"

# 清理旧文件
rm -rf target/

# 拉取最新代码
git pull

# 构建项目
mvn package -DskipTests

# 启动服务
nohup java -jar target/Jetbrains-LicenseServer-Help-${VERSION}.jar > server.log 2>&1 &

echo "部署完成！"
echo "服务已启动，日志文件：server.log"
```

添加执行权限并运行：

```bash
chmod +x deploy.sh
./deploy.sh
```

### 7.2 使用 CI/CD 工具

可以使用 GitHub Actions、GitLab CI 等 CI/CD 工具实现自动化构建和部署。

#### 7.2.1 GitHub Actions 示例

在 `.github/workflows/deploy.yml` 中创建以下配置：

```yaml
name: Deploy JetBrains License Server Help

on:
  push:
    branches: [ main ]

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'adopt'
        cache: maven
    
    - name: Build with Maven
      run: mvn -B package --file pom.xml
    
    - name: Deploy to server
      uses: easingthemes/ssh-deploy@v2
      with:
        SSH_PRIVATE_KEY: ${{ secrets.SSH_PRIVATE_KEY }}
        REMOTE_HOST: ${{ secrets.REMOTE_HOST }}
        REMOTE_USER: ${{ secrets.REMOTE_USER }}
        TARGET: /opt/jetbrains-license-server
        SOURCE: target/Jetbrains-LicenseServer-Help-*.jar
```

## 8. 常见部署问题及解决方法

### 8.1 端口被占用

**问题**：启动服务时提示端口已被占用

**解决方法**：

1. 检查端口占用情况：
   - Linux/macOS: `lsof -i :10768`
   - Windows: `netstat -ano | findstr :10768`

2. 终止占用端口的进程或修改配置文件使用其他端口：
   ```bash
   # 修改配置文件
   sed -i 's/server.port: 10768/server.port: 8080/' src/main/resources/application.yml
   ```

### 8.2 Maven 构建失败

**问题**：执行 `mvn package` 时构建失败

**解决方法**：

1. 检查网络连接是否正常
2. 清理 Maven 缓存：
   ```bash
   mvn clean install -U
   ```
3. 检查 pom.xml 文件是否存在错误
4. 尝试使用国内 Maven 镜像：
   ```bash
   mvn package -Dmaven.repo.local=/path/to/local/repo
   ```

### 8.3 Docker 构建失败

**问题**：执行 `docker build` 时构建失败

**解决方法**：

1. 检查 Docker 是否已正确安装并运行
2. 检查 Dockerfile 是否存在语法错误
3. 确保 JAR 文件路径正确
4. 尝试使用 `--no-cache` 参数重新构建：
   ```bash
   docker build --no-cache -t jetbrains-license-server-help .
   ```

### 8.4 服务无法访问

**问题**：服务已启动但无法从外部访问

**解决方法**：

1. 检查防火墙/安全组是否已开放对应端口
2. 检查服务是否绑定到了正确的 IP 地址（默认绑定到所有地址 0.0.0.0）
3. 检查网络连接是否正常
4. 使用 `curl` 或 `telnet` 测试端口连通性：
   ```bash
   curl http://localhost:10768/rpc/ping.action
   telnet your-server-ip 10768
   ```

### 8.5 日志显示错误

**问题**：服务启动后日志中显示错误信息

**解决方法**：

1. 查看完整日志文件获取详细错误信息
2. 检查配置文件是否正确
3. 检查依赖是否完整
4. 参考[环境配置指南](./environment-configuration.md)中的常见问题部分

## 9. 升级指南

### 9.1 从旧版本升级

1. 备份现有配置和数据
2. 获取最新版本代码或下载最新发布包
3. 停止旧版本服务
4. 部署新版本服务（参考前面的部署步骤）
5. 验证新版本服务是否正常运行

### 9.2 配置迁移

如果新版本有配置文件变化，请根据发布说明更新您的配置文件。

## 10. 总结

本文档详细介绍了 JetBrains License Server Help 项目的安装部署流程，包括：

- ✅ 安装前准备和环境检查
- ✅ 项目代码获取方式
- ✅ 项目构建步骤
- ✅ 多种部署方式（直接运行、Docker、Docker Compose）
- ✅ 部署验证方法
- ✅ 防火墙/安全组配置
- ✅ 自动化部署方案
- ✅ 常见部署问题及解决方法
- ✅ 版本升级指南

选择适合您环境的部署方式，并按照文档中的步骤进行操作，即可成功部署 JetBrains License Server Help 项目。

如果您在部署过程中遇到任何问题，请参考常见问题部分或提交 Issue 寻求帮助。
