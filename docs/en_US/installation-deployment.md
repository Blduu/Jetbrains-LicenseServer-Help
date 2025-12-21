# Installation and Deployment Guide

This document provides a detailed guide for installing and deploying the JetBrains License Server Help project, including pre-installation preparations, project acquisition, building, and various deployment methods.

## 1. Pre-Installation Preparation

Before starting the installation and deployment, please ensure you have completed the following preparations:

### 1.1 Environment Check

Please confirm that your system meets the following environmental requirements (for detailed configuration, please refer to the [Environment Configuration Guide](./environment-configuration.md)):

- ✅ JDK 17 or above installed and configured
- ✅ Maven 3.6 or above installed and configured
- ✅ Git (optional, for cloning project code)
- ✅ Docker (optional, for containerized deployment)
- ✅ Sufficient disk space (recommended 1 GB or more)
- ✅ Stable network connection

### 1.2 Port Preparation

Ensure that the ports required by the project are not occupied:

| Port | Purpose | Default Value |
|------|---------|---------------|
| 10768 | License server port | 10768 |

You can change the port by modifying the `server.port` configuration item in the `src/main/resources/application.yml` file.

## 2. Obtain Project Code

### 2.1 Clone Code from GitHub

Use Git to clone the project code to your local machine:

```bash
git clone https://github.com/Blduu/Jetbrains-LicenseServer-Help.git
cd Jetbrains-LicenseServer-Help
```

### 2.2 Download Release Package

If you don't want to clone the entire project, you can directly download the latest release package from the GitHub Releases page:

1. Visit the project's GitHub Releases page
2. Download the latest version of `Jetbrains-LicenseServer-Help.jar` file

## 3. Build the Project

If you cloned the code from GitHub, you need to build the project first to generate the executable JAR file.

### 3.1 Compile the Project

Execute the following command to compile the project:

```bash
mvn compile
```

### 3.2 Run Tests

Optional step, execute test cases:

```bash
mvn test
```

### 3.3 Package the Project

Execute the following command to package the project and generate the executable JAR file:

```bash
mvn package -DskipTests
```

After packaging is complete, the executable JAR file will be generated in the `target` directory with a filename format of `Jetbrains-LicenseServer-Help-<version>.jar`.

## 4. Deployment Methods

### 4.1 Run JAR File Directly

The simplest deployment method is to directly run the generated JAR file.

#### 4.1.1 Basic Run

```bash
java -jar target/Jetbrains-LicenseServer-Help-<version>.jar
```

Or use the downloaded release package:

```bash
java -jar Jetbrains-LicenseServer-Help.jar
```

#### 4.1.2 Run with Custom Configuration

You can customize the configuration through command line parameters or environment variables:

```bash
# Use command line parameters to specify port
java -jar target/Jetbrains-LicenseServer-Help-<version>.jar --server.port=8080

# Use environment variables to specify configuration
SERVER_PORT=8080 java -jar target/Jetbrains-LicenseServer-Help-<version>.jar
```

#### 4.1.3 Run in Background

**Linux/macOS Systems:**

```bash
nohup java -jar target/Jetbrains-LicenseServer-Help-<version>.jar > server.log 2>&1 &
```

**Windows Systems:**

Use the `start` command to run in the background:

```cmd
start /B java -jar target\Jetbrains-LicenseServer-Help-<version>.jar > server.log 2>&1
```

### 4.2 Deploy with Docker

If you have Docker installed, you can deploy the project using containerization.

#### 4.2.1 Create Dockerfile

Create a `Dockerfile` in the project root directory:

```dockerfile
# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy JAR file to working directory
COPY target/Jetbrains-LicenseServer-Help-<version>.jar app.jar

# Expose port
EXPOSE 10768

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### 4.2.2 Build Docker Image

```bash
docker build -t jetbrains-license-server-help .
```

#### 4.2.3 Run Docker Container

```bash
docker run -d -p 10768:10768 --name license-server jetbrains-license-server-help
```

#### 4.2.4 Mount External Configuration

If you need to persist data or use external configuration files, you can mount volumes:

```bash
docker run -d -p 10768:10768 \
  -v ./external:/app/external \
  -v ./application.yml:/app/application.yml \
  --name license-server jetbrains-license-server-help
```

### 4.3 Deploy with Docker Compose

Using Docker Compose can make containerized deployment more convenient.

#### 4.3.1 Create docker-compose.yml

Create a `docker-compose.yml` file in the project root directory:

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

#### 4.3.2 Build and Start Service

```bash
docker-compose up -d
```

#### 4.3.3 Stop Service

```bash
docker-compose down
```

#### 4.3.4 View Logs

```bash
docker-compose logs -f
```

## 5. Verify Deployment Results

### 5.1 Check Service Status

After deployment is complete, you can check if the service is running normally through the following methods:

#### 5.1.1 Check Process Status

**Linux/macOS Systems:**

```bash
ps aux | grep Jetbrains-LicenseServer-Help
```

**Windows Systems:**

```cmd
tasklist | findstr java
```

#### 5.1.2 Test API Interface

Use `curl` or a browser to access the following URL to test if the service responds normally:

```bash
# Test license server status
curl http://localhost:10768/rpc/ping.action

# Test web management interface
curl http://localhost:10768/
```

If the service is running normally, you will receive the corresponding XML response or HTML content.

#### 5.1.3 View Logs

**Run JAR file directly:**

View console output or the specified log file (such as `server.log`).

**Docker container:**

```bash
docker logs -f license-server
```

**Docker Compose:**

```bash
docker-compose logs -f
```

## 6. Configure Firewall/Security Group

### 6.1 Linux Systems (Using iptables)

```bash
# Allow TCP connections on port 10768
sudo iptables -A INPUT -p tcp --dport 10768 -j ACCEPT

# Save rules
sudo iptables-save > /etc/iptables/rules.v4
```

### 6.2 Windows Systems (Using Firewall)

1. Open "Control Panel" → "System and Security" → "Windows Defender Firewall"
2. Click "Advanced Settings"
3. Click "Inbound Rules" → "New Rule"
4. Select "Port" → "Next"
5. Select "TCP" → enter "10768" as the specific local port → "Next"
6. Select "Allow the connection" → "Next"
7. Select application scenarios (Domain, Private, Public) → "Next"
8. Enter rule name (such as "JetBrains License Server") → "Finish"

### 6.3 Cloud Server Security Group

If you deploy the project on a cloud server, you need to open port 10768 in the security group:

1. Log in to the cloud service provider console
2. Find the corresponding security group configuration
3. Add inbound rules to allow TCP protocol access on port 10768
4. Save configuration

## 7. Automated Deployment

### 7.1 Deploy with Shell Script

Create a deployment script `deploy.sh` to automate the deployment process:

```bash
#!/bin/bash

# Deployment script

# Project version
VERSION="1.0.0"

# Stop old service
pkill -f "Jetbrains-LicenseServer-Help"

# Clean up old files
rm -rf target/

# Pull latest code
git pull

# Build project
mvn package -DskipTests

# Start service
nohup java -jar target/Jetbrains-LicenseServer-Help-${VERSION}.jar > server.log 2>&1 &

echo "Deployment completed!"
echo "Service started, log file: server.log"
```

Add execution permission and run:

```bash
chmod +x deploy.sh
./deploy.sh
```

### 7.2 Use CI/CD Tools

You can use CI/CD tools such as GitHub Actions, GitLab CI, etc. to achieve automated build and deployment.

#### 7.2.1 GitHub Actions Example

Create the following configuration in `.github/workflows/deploy.yml`:

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

## 8. Common Deployment Issues and Solutions

### 8.1 Port Already in Use

**Problem**: Port already in use error when starting service

**Solution**:

1. Check port usage:
   - Linux/macOS: `lsof -i :10768`
   - Windows: `netstat -ano | findstr :10768`

2. Terminate the process occupying the port or modify the configuration file to use another port:
   ```bash
   # Modify configuration file
   sed -i 's/server.port: 10768/server.port: 8080/' src/main/resources/application.yml
   ```

### 8.2 Maven Build Failure

**Problem**: Build failure when executing `mvn package`

**Solution**:

1. Check if the network connection is normal
2. Clean Maven cache:
   ```bash
   mvn clean install -U
   ```
3. Check if the pom.xml file has errors
4. Try using a domestic Maven mirror:
   ```bash
   mvn package -Dmaven.repo.local=/path/to/local/repo
   ```

### 8.3 Docker Build Failure

**Problem**: Build failure when executing `docker build`

**Solution**:

1. Check if Docker is correctly installed and running
2. Check if Dockerfile has syntax errors
3. Ensure the JAR file path is correct
4. Try rebuilding with `--no-cache` parameter:
   ```bash
   docker build --no-cache -t jetbrains-license-server-help .
   ```

### 8.4 Service Unreachable

**Problem**: Service starts but cannot be accessed externally

**Solution**:

1. Check if firewall/security group has opened the corresponding port
2. Check if the service is bound to the correct IP address (default is bound to all addresses 0.0.0.0)
3. Check if network connection is normal
4. Test port connectivity using `curl` or `telnet`:
   ```bash
   curl http://localhost:10768/rpc/ping.action
   telnet your-server-ip 10768
   ```

### 8.5 Errors in Logs

**Problem**: Error messages appear in logs after service starts

**Solution**:

1. View the complete log file to get detailed error information
2. Check if the configuration file is correct
3. Check if dependencies are complete
4. Refer to the Common Issues section in the [Environment Configuration Guide](./environment-configuration.md)

## 9. Upgrade Guide

### 9.1 Upgrade from Old Version

1. Back up existing configuration and data
2. Get the latest version code or download the latest release package
3. Stop the old version service
4. Deploy the new version service (refer to the deployment steps above)
5. Verify that the new version service is running normally

### 9.2 Configuration Migration

If there are configuration file changes in the new version, please update your configuration file according to the release notes.

## 10. Summary

This document provides a detailed guide for installing and deploying the JetBrains License Server Help project, including:

- ✅ Pre-installation preparation and environment checking
- ✅ Project code acquisition methods
- ✅ Project building steps
- ✅ Multiple deployment methods (direct run, Docker, Docker Compose)
- ✅ Deployment verification methods
- ✅ Firewall/security group configuration
- ✅ Automated deployment solutions
- ✅ Common deployment issues and solutions
- ✅ Version upgrade guide

Choose a deployment method suitable for your environment and follow the steps in the document to successfully deploy the JetBrains License Server Help project.

If you encounter any problems during the deployment process, please refer to the Common Issues section or submit an Issue for help.
