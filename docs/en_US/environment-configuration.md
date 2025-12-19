# Environment Configuration Guide

This document provides detailed information about the environment requirements, system configuration, project configuration, and optional configurations for the JetBrains License Server Help project.

## 1. Environment Requirements

### 1.1 Hardware Requirements

| Hardware Type | Minimum Configuration | Recommended Configuration |
|--------------|----------------------|---------------------------|
| CPU | 2 cores | 4 cores or more |
| Memory | 2 GB | 4 GB or more |
| Storage | 500 MB | 1 GB or more |
| Network | Internet access | Stable network connection |

### 1.2 Software Requirements

| Software Type | Version Requirement | Purpose |
|--------------|--------------------|---------|
| Java Development Kit (JDK) | 17 or above | Development and runtime environment |
| Maven | 3.6 or above | Project build and dependency management |
| Git | 2.0 or above | Version control (optional) |
| Docker | 20.0 or above | Containerized deployment (optional) |

## 2. System Environment Configuration

### 2.1 JDK Installation and Configuration

#### 2.1.1 Download JDK

Download JDK 17 or above from Oracle or OpenJDK website:

- Oracle JDK: [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/)
- OpenJDK: [https://adoptium.net/](https://adoptium.net/)

#### 2.1.2 Install JDK

**Windows System:**
1. Double-click the downloaded JDK installation file
2. Follow the installation wizard and choose an appropriate installation path
3. Complete the installation

**Linux System:**
```bash
# Install OpenJDK 17 using apt (Ubuntu/Debian)
sudo apt update
sudo apt install openjdk-17-jdk

# Install OpenJDK 17 using yum (CentOS/RHEL)
sudo yum install java-17-openjdk-devel
```

**macOS System:**
```bash
# Install OpenJDK 17 using Homebrew
brew install openjdk@17

# Configure environment variables
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

#### 2.1.3 Verify JDK Installation

Open a terminal or command prompt and execute the following command to verify JDK installation:

```bash
java -version
```

If installed successfully, you will see output similar to:

```
java version "17.0.10"
Java(TM) SE Runtime Environment (build 17.0.10+11-LTS-240)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.10+11-LTS-240, mixed mode, sharing)
```

#### 2.1.4 Configure Environment Variables (Windows System)

1. Right-click on "This PC" or "My Computer" and select "Properties"
2. Click "Advanced system settings"
3. Click "Environment Variables"
4. In "System variables", click "New" to add the following environment variable:
   - Variable name: `JAVA_HOME`
   - Variable value: JDK installation path (e.g., `C:\Program Files\Java\jdk-17.0.10`)
5. In "System variables", find the `Path` variable and click "Edit"
6. Click "New" and add `%JAVA_HOME%\bin`
7. Click "OK" to save all settings

### 2.2 Maven Installation and Configuration

#### 2.2.1 Download Maven

Download the latest version of Maven from Apache Maven website: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

#### 2.2.2 Install Maven

**Windows System:**
1. Extract the downloaded Maven archive to an appropriate directory (e.g., `C:\Program Files\Apache\maven`)
2. Configure environment variables

**Linux/macOS System:**
```bash
# Extract Maven archive
tar -zxvf apache-maven-3.9.6-bin.tar.gz
mv apache-maven-3.9.6 /usr/local/maven
```

#### 2.2.3 Configure Environment Variables

**Windows System:**
1. In "System variables", click "New" to add the following environment variable:
   - Variable name: `MAVEN_HOME`
   - Variable value: Maven installation path (e.g., `C:\Program Files\Apache\maven`)
2. In "System variables", find the `Path` variable and click "Edit"
3. Click "New" and add `%MAVEN_HOME%\bin`
4. Click "OK" to save all settings

**Linux/macOS System:**
```bash
# Edit .bashrc or .zshrc file
echo 'export MAVEN_HOME=/usr/local/maven' >> ~/.bashrc
echo 'export PATH="$MAVEN_HOME/bin:$PATH"' >> ~/.bashrc

# Reload configuration file
source ~/.bashrc
```

#### 2.2.4 Verify Maven Installation

Open a terminal or command prompt and execute the following command to verify Maven installation:

```bash
mvn -version
```

If installed successfully, you will see output similar to:

```
Apache Maven 3.9.6 (bc0240f3c744dd6b6ec2920b3cd08dcc295161ae)
Maven home: C:\Program Files\Apache\maven
Java version: 17.0.10, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-17.0.10
Default locale: zh_CN, platform encoding: UTF-8
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

## 3. Project Configuration

### 3.1 Main Configuration File

The main configuration file for the project is `src/main/resources/application.yml`, which contains the following configuration items:

```yaml
spring:
  application:
    name: BlueSky-Jetbrains-LicenseServer-Help
xbase64:
  domain: BlueSky.cc
server:
  port: 10768
  # Plugin information retrieval configuration
  plugins:
    # Whether to enable scheduled refresh task (true/false)
    refresh-enabled: true
    # Page size (number of plugins to retrieve per request, recommended not to exceed 20)
    page-size: 20
    # Number of concurrent threads (for parallel requests to different plugin pages)
    thread-count: 20
    # Request timeout (milliseconds)
    timeout: 30000
```

### 3.2 Configuration Item Description

#### 3.2.1 Basic Configuration

| Configuration Item | Default Value | Description |
|-------------------|---------------|-------------|
| `spring.application.name` | BlueSky-Jetbrains-LicenseServer-Help | Application name |
| `xbase64.domain` | BlueSky.cc | xbase64 domain configuration |
| `server.port` | 10768 | Server port |

#### 3.2.2 Plugin Configuration

| Configuration Item | Default Value | Description |
|-------------------|---------------|-------------|
| `server.plugins.refresh-enabled` | true | Whether to enable plugin information scheduled refresh task |
| `server.plugins.page-size` | 20 | Number of plugins to retrieve per request, recommended not to exceed 20 |
| `server.plugins.thread-count` | 20 | Number of concurrent threads for parallel requests to different plugin pages |
| `server.plugins.timeout` | 30000 | Request timeout in milliseconds |

### 3.3 Extended Configuration

In addition to the configuration in `application.yml`, the project also supports extended configuration through the following methods:

#### 3.3.1 Java System Properties

You can override configuration items through Java system properties, for example:

```bash
java -jar Jetbrains-LicenseServer-Help.jar --server.port=8080
```

#### 3.3.2 Command Line Parameters

You can override configuration items through command line parameters, for example:

```bash
java -jar Jetbrains-LicenseServer-Help.jar --spring.application.name=MyLicenseServer
```

#### 3.3.3 Environment Variables

You can override configuration items through environment variables, for example:

```bash
# Linux/macOS
export SERVER_PORT=8080
java -jar Jetbrains-LicenseServer-Help.jar

# Windows
set SERVER_PORT=8080
java -jar Jetbrains-LicenseServer-Help.jar
```

## 4. Optional Configuration

### 4.1 Docker Configuration

If you choose to deploy the project using Docker, you can create the following `Dockerfile`:

```dockerfile
# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy JAR file to working directory
COPY target/Jetbrains-LicenseServer-Help.jar app.jar

# Expose port
EXPOSE 10768

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 4.2 Docker Compose Configuration

You can use Docker Compose to simplify Docker deployment by creating the following `docker-compose.yml` file:

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

### 4.3 HTTPS Configuration

If you need to enable HTTPS, you can add the following configuration to `application.yml`:

```yaml
server:
  port: 8443
  ssl:
    key-store: classpath:keystore.p12
    key-store-password: password
    key-store-type: PKCS12
    key-alias: tomcat
```

### 4.4 Log Configuration

The project uses Spring Boot's default logging framework (Logback), and the logging configuration file is `src/main/resources/logback-spring.xml`. You can modify the logging configuration as needed, for example:

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

## 5. Configuration Examples

### 5.1 Basic Configuration Example

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

### 5.2 Custom Port Configuration Example

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

### 5.3 Disable Plugin Refresh Configuration Example

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

## 6. Common Issues

### 6.1 JDK Version Incompatibility

**Problem**: "UnsupportedClassVersionError" occurs when running the project.

**Solution**: Ensure that JDK version 17 or above is used. You can check the JDK version using the `java -version` command.

### 6.2 Maven Dependency Download Failure

**Problem**: Dependency download fails when executing `mvn install`.

**Solution**:
1. Check if the network connection is normal
2. Try using a domestic Maven mirror, such as Alibaba Cloud Mirror:

```xml
<!-- Add to ~/.m2/settings.xml file -->
<mirrors>
    <mirror>
        <id>aliyunmaven</id>
        <mirrorOf>*</mirrorOf>
        <name>Alibaba Cloud Public Repository</name>
        <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
</mirrors>
```

### 6.3 Port Already in Use

**Problem**: "Address already in use" error occurs when running the project.

**Solution**:
1. Check if the port is occupied by other processes:
   - Linux/macOS: `lsof -i :10768`
   - Windows: `netstat -ano | findstr :10768`
2. Terminate the process occupying the port, or modify the project configuration to use a different port.

### 6.4 Plugin Information Refresh Failure

**Problem**: Plugin information scheduled refresh task fails.

**Solution**:
1. Check if the network connection is normal
2. Check if the JetBrains plugin API is accessible
3. Adjust plugin configuration parameters, such as increasing timeout or reducing concurrent threads:

```yaml
server:
  plugins:
    timeout: 60000
    thread-count: 10
```

## 7. Summary

This document provides detailed information about the environment requirements, system configuration, project configuration, and optional configurations for the JetBrains License Server Help project. By correctly configuring the environment and project parameters, you can ensure the project runs normally and achieves optimal performance.

If you encounter any issues during the configuration process, please refer to the Common Issues section or submit an Issue for help.