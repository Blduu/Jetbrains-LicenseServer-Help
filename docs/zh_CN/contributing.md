# 贡献指南

欢迎对 JetBrains License Server Help 项目进行贡献！本指南将帮助您了解如何参与到项目的开发和维护中来。

## 1. 贡献概述

我们欢迎各种形式的贡献，包括但不限于：

- 修复 Bug
- 添加新功能
- 改进文档
- 优化代码
- 报告问题和提出建议

## 2. 贡献方式

### 2.1 报告问题

如果您发现了 Bug 或有功能建议，可以通过以下步骤报告：

1. 首先在 [Issue](https://github.com/Blduu/Jetbrains-LicenseServer-Help/issues) 页面搜索是否已经有人报告了相同的问题
2. 如果没有找到相关 Issue，请创建一个新的 Issue，包含以下信息：
   - 问题描述：清晰、详细地描述您遇到的问题
   - 复现步骤：提供详细的操作步骤来复现问题
   - 预期结果：您期望的正常行为
   - 实际结果：实际发生的错误或异常行为
   - 环境信息：操作系统、JDK 版本、项目版本等
   - 截图或日志：如果可能，提供相关截图或错误日志

### 2.2 提交代码

如果您想直接贡献代码，可以按照以下流程操作：

1. Fork 本项目到您的 GitHub 账号
2. Clone 您的 Fork 仓库到本地
3. 创建一个新的分支（遵循命名规范）
4. 在新分支上进行开发
5. 提交代码并推送到您的 Fork 仓库
6. 创建 Pull Request 到主仓库

## 3. 开发环境设置

### 3.1 环境要求

- JDK 17+
- Maven 3.6+
- Git
- IDE：IntelliJ IDEA 推荐（其他 IDE 也可以）

### 3.2 项目初始化

1. 克隆项目到本地：

```bash
git clone https://github.com/Blduu/Jetbrains-LicenseServer-Help.git
cd Jetbrains-LicenseServer-Help
```

2. 使用 Maven 安装依赖：

```bash
mvn clean install -DskipTests
```

3. 导入项目到您的 IDE：
   - IntelliJ IDEA：选择 File -> Open，然后选择项目根目录下的 pom.xml 文件
   - Eclipse：选择 File -> Import，然后选择 Maven -> Existing Maven Projects

4. 启动项目：
   - 直接运行 `JetbrainsLicenseServerHelpApplication.java` 类
   - 或使用 Maven 命令：`mvn spring-boot:run`

## 4. 编码规范

为了保持代码的一致性和可读性，我们遵循以下编码规范：

### 4.1 Java 编码规范

- 遵循 Java 命名约定：
  - 类名：使用帕斯卡命名法（PascalCase），如 `LicenseServerController`
  - 方法名：使用驼峰命名法（camelCase），如 `generateLicenseCode`
  - 变量名：使用驼峰命名法（camelCase），如 `licenseName`
  - 常量名：全部大写，单词间用下划线分隔，如 `SERVER_PORT`

- 代码缩进：使用 4 个空格
- 行宽：不超过 120 个字符
- 注释：使用 Javadoc 注释类和方法，关键代码段添加必要的单行注释

### 4.2 项目结构规范

- 控制器类放置在 `controller` 包下
- 服务类放置在 `service` 包下
- 数据访问类放置在 `repository` 包下
- 实体类放置在 `entity` 包下
- 工具类放置在 `utils` 包下
- 配置类放置在 `config` 包下

### 4.3 Git 提交规范

- 提交信息应简洁明了，使用英文
- 提交信息格式：`type(scope): subject`
  - type：提交类型（feat, fix, docs, style, refactor, test, chore）
  - scope：可选，影响的模块或文件
  - subject：提交内容的简短描述

- 示例：
  - `feat(license): add license code generation feature`
  - `fix(controller): fix null pointer exception in DataController`
  - `docs(zh_CN): update installation guide`

## 5. 提交流程

1. 在开始开发前，确保您的本地主分支是最新的：

```bash
git checkout main
git pull origin main
```

2. 创建一个新的分支，分支名应遵循以下规范：
   - 修复 Bug：`fix/issue-number-description`
   - 新功能：`feat/feature-name`
   - 文档更新：`docs/language/topic`

```bash
git checkout -b feat/new-license-feature
```

3. 在新分支上进行开发，确保遵循编码规范

4. 运行测试确保代码质量：

```bash
mvn test
```

5. 提交代码，使用规范的提交信息：

```bash
git add .
git commit -m "feat(license): add new license feature"
```

6. 推送到您的 Fork 仓库：

```bash
git push origin feat/new-license-feature
```

## 6. Pull Request 流程

1. 登录 GitHub，进入您的 Fork 仓库
2. 点击 "New Pull Request" 按钮
3. 选择您的分支和主仓库的 `main` 分支进行比较
4. 填写 Pull Request 描述，包括：
   - 解决的问题或实现的功能
   - 主要修改内容
   - 相关的 Issue 编号（如果有）
5. 点击 "Create Pull Request" 按钮提交

## 7. 测试要求

- 所有新功能都必须添加对应的单元测试
- 修复 Bug 后必须添加测试用例以防止回归
- 提交代码前确保所有现有测试都能通过
- 使用 JUnit 5 编写单元测试

## 8. 文档贡献

- 文档使用 Markdown 格式编写
- 保持中英文文档的一致性
- 文档应清晰、准确、易于理解
- 为新功能或更改添加必要的文档说明

## 9. 行为准则

参与本项目的所有贡献者都应遵守以下行为准则：

- 尊重他人，友好交流
- 接受建设性的批评和反馈
- 专注于项目的最佳利益
- 不使用攻击性或歧视性的语言

## 10. 贡献者名单

感谢所有为项目做出贡献的人！

如果您有任何疑问或需要帮助，可以通过 Issue 或邮件与我们联系。

再次感谢您的支持和贡献！
