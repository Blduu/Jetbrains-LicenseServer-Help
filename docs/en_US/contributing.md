# Contributing Guide

Welcome to contribute to the JetBrains License Server Help project! This guide will help you understand how to participate in the development and maintenance of the project.

## 1. Contribution Overview

We welcome various forms of contributions, including but not limited to:

- Bug fixes
- New features
- Documentation improvements
- Code optimizations
- Issue reporting and suggestions

## 2. Ways to Contribute

### 2.1 Reporting Issues

If you find a bug or have a feature suggestion, you can report it through the following steps:

1. First, search in the [Issue](https://github.com/yourusername/Jetbrains-LicenseServer-Help/issues) page to see if someone has already reported the same issue
2. If no relevant issue is found, create a new Issue with the following information:
   - Issue description: Clearly and detailedly describe the problem you encountered
   - Reproduction steps: Provide detailed operating steps to reproduce the issue
   - Expected result: The normal behavior you expect
   - Actual result: The error or abnormal behavior that actually occurred
   - Environment information: Operating system, JDK version, project version, etc.
   - Screenshots or logs: If possible, provide relevant screenshots or error logs

### 2.2 Submitting Code

If you want to directly contribute code, you can follow the following process:

1. Fork this project to your GitHub account
2. Clone your Fork repository to your local machine
3. Create a new branch (following naming conventions)
4. Develop on the new branch
5. Commit code and push to your Fork repository
6. Create a Pull Request to the main repository

## 3. Development Environment Setup

### 3.1 Environment Requirements

- JDK 17+
- Maven 3.6+
- Git
- IDE: IntelliJ IDEA is recommended (other IDEs can also be used)

### 3.2 Project Initialization

1. Clone the project to your local machine:

```bash
git clone https://github.com/yourusername/Jetbrains-LicenseServer-Help.git
cd Jetbrains-LicenseServer-Help
```

2. Install dependencies using Maven:

```bash
mvn clean install -DskipTests
```

3. Import the project into your IDE:
   - IntelliJ IDEA: Select File -> Open, then select the pom.xml file in the project root directory
   - Eclipse: Select File -> Import, then select Maven -> Existing Maven Projects

4. Start the project:
   - Directly run the `JetbrainsLicenseServerHelpApplication.java` class
   - Or use Maven command: `mvn spring-boot:run`

## 4. Coding Standards

To maintain code consistency and readability, we follow the following coding standards:

### 4.1 Java Coding Standards

- Follow Java naming conventions:
  - Class names: Use PascalCase, such as `LicenseServerController`
  - Method names: Use camelCase, such as `generateLicenseCode`
  - Variable names: Use camelCase, such as `licenseName`
  - Constant names: All uppercase, words separated by underscores, such as `SERVER_PORT`

- Code indentation: Use 4 spaces
- Line width: No more than 120 characters
- Comments: Use Javadoc comments for classes and methods, and add necessary single-line comments for key code segments

### 4.2 Project Structure Standards

- Controller classes are placed under the `controller` package
- Service classes are placed under the `service` package
- Data access classes are placed under the `repository` package
- Entity classes are placed under the `entity` package
- Utility classes are placed under the `utils` package
- Configuration classes are placed under the `config` package

### 4.3 Git Commit Standards

- Commit messages should be concise and clear, using English
- Commit message format: `type(scope): subject`
  - type: Commit type (feat, fix, docs, style, refactor, test, chore)
  - scope: Optional, affected module or file
  - subject: Brief description of the commit content

- Examples:
  - `feat(license): add license code generation feature`
  - `fix(controller): fix null pointer exception in DataController`
  - `docs(en_US): update installation guide`

## 5. Submission Process

1. Before starting development, ensure that your local main branch is up-to-date:

```bash
git checkout main
git pull origin main
```

2. Create a new branch, following the naming convention:
   - Bug fixes: `fix/issue-number-description`
   - New features: `feat/feature-name`
   - Documentation updates: `docs/language/topic`

```bash
git checkout -b feat/new-license-feature
```

3. Develop on the new branch, ensuring compliance with coding standards

4. Run tests to ensure code quality:

```bash
mvn test
```

5. Commit code with standardized commit messages:

```bash
git add .
git commit -m "feat(license): add new license feature"
```

6. Push to your Fork repository:

```bash
git push origin feat/new-license-feature
```

## 6. Pull Request Process

1. Log in to GitHub and enter your Fork repository
2. Click the "New Pull Request" button
3. Select your branch and the main repository's `main` branch for comparison
4. Fill in the Pull Request description, including:
   - The problem solved or feature implemented
   - Main modification content
   - Related Issue number (if any)
5. Click the "Create Pull Request" button to submit

## 7. Testing Requirements

- All new features must add corresponding unit tests
- After fixing a bug, add test cases to prevent regression
- Ensure all existing tests pass before submitting code
- Use JUnit 5 to write unit tests

## 8. Documentation Contributions

- Documentation is written in Markdown format
- Maintain consistency between Chinese and English documentation
- Documentation should be clear, accurate, and easy to understand
- Add necessary documentation explanations for new features or changes

## 9. Code of Conduct

All contributors participating in this project should abide by the following code of conduct:

- Respect others and communicate friendly
- Accept constructive criticism and feedback
- Focus on the best interests of the project
- Do not use offensive or discriminatory language

## 10. Contributors List

Thanks to everyone who has contributed to the project!

If you have any questions or need help, you can contact us through Issue or email.

Thank you again for your support and contributions!
