# Frequently Asked Questions (FAQ)

This document collects common questions and answers about using JetBrains License Server Help, helping you quickly solve problems you encounter.

## 1. Installation and Configuration

### 1.1 How to install JDK?

JetBrains License Server Help requires JDK 17 or higher. You can install it by following these steps:

1. Visit the [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/) download page
2. Select and download JDK 17+ version suitable for your operating system
3. Follow the installation wizard to complete the installation
4. Configure the `JAVA_HOME` environment variable to point to the JDK installation directory
5. Add the JDK `bin` directory to the system's `PATH` environment variable

Verify the installation was successful:

```bash
java -version
```

### 1.2 How to configure Maven?

1. Visit the [Maven official website](https://maven.apache.org/download.cgi) to download Maven 3.6+ version
2. Extract the downloaded compressed package to a specified directory
3. Configure the `M2_HOME` environment variable to point to the Maven installation directory
4. Add the Maven `bin` directory to the system's `PATH` environment variable

Verify the installation was successful:

```bash
mvn -version
```

### 1.3 How to modify the server port?

You can change the server port by modifying the `server.port` configuration in the `src/main/resources/application.yml` file:

```yaml
server:
  port: 10768  # Modify to your desired port number
```

### 1.4 How to configure plugin refresh settings?

You can configure plugin refresh related settings in the `application.yml` file:

```yaml
server:
  plugins:
    refresh-enabled: true  # Whether to enable plugin refresh
    page-size: 20  # Page size for each request
    thread-count: 20  # Number of refresh threads
    timeout: 30000  # Request timeout (milliseconds)
```

## 2. Deployment

### 2.1 How to deploy using Docker?

1. Ensure Docker is installed
2. Create a `Dockerfile` in the project root directory (if it doesn't exist)
3. Build the Docker image:

```bash
docker build -t jetbrains-license-server-help .
```

4. Run the Docker container:

```bash
docker run -d -p 10768:10768 --name license-server-help jetbrains-license-server-help
```

### 2.2 How to configure firewall?

If your server has a firewall enabled, you need to open the corresponding port (default 10768):

- **Windows**: Add an inbound rule in Windows Firewall settings to allow port 10768
- **Linux (iptables)**: 
  ```bash
iptables -A INPUT -p tcp --dport 10768 -j ACCEPT
  ```
- **Linux (firewalld)**: 
  ```bash
firewall-cmd --add-port=10768/tcp --permanent
firewall-cmd --reload
  ```

### 2.3 How to deploy on different operating systems?

- **Windows**: Run using the command `java -jar target/Jetbrains-LicenseServer-Help-1.0-SNAPSHOT.jar`
- **Linux**: Run in the background using `nohup java -jar target/Jetbrains-LicenseServer-Help-1.0-SNAPSHOT.jar > server.log 2>&1 &`
- **Mac**: Similar to Linux, you can use the `nohup` command to run in the background

## 3. Usage

### 3.1 How to generate a license?

1. Access the web management interface (default address: http://localhost:10768)
2. Click the "Generate License" button
3. Fill in the license information (license name, assignee name, expiry date, etc.)
4. Select products (optional)
5. Click the "Generate" button
6. Copy the generated license code

### 3.2 How to configure the license server in JetBrains IDE?

1. Open JetBrains IDE (such as IntelliJ IDEA)
2. Go to "Help" -> "Register" or "Configure" -> "Settings" -> "Appearance & Behavior" -> "System Settings" -> "License"
3. Select "License Server"
4. Enter the license server address (e.g., http://your-server-ip:10768)
5. Click the "Activate" button

### 3.3 How to use the ja-netfilter tool?

1. Download the ja-netfilter tool from the web management interface or via API (http://localhost:10768/ja-netfilter)
2. Extract the downloaded ZIP file
3. Modify the configuration files in the `config` directory as needed
4. Add the ja-netfilter agent to the IDE's startup script:

```bash
-javaagent:/path/to/ja-netfilter/ja-netfilter.jar
```

### 3.4 How to update the product and plugin list?

The product and plugin lists are automatically obtained and updated from JetBrains official API. You can configure refresh settings in `application.yml`:

```yaml
server:
  plugins:
    refresh-enabled: true  # Enable automatic refresh
```

## 4. Troubleshooting

### 4.1 What to do if the server fails to start?

1. Check if the JDK version meets the requirements (JDK 17+)
2. Check if the port is occupied:
   - Windows: `netstat -ano | findstr 10768`
   - Linux/Mac: `lsof -i :10768` or `netstat -tuln | grep 10768`
3. Check the log file (if logging is enabled)
4. Try rebuilding the project with `mvn clean package`

### 4.2 What to do if IDE cannot connect to the license server?

1. Check if the server is running: `curl http://localhost:10768`
2. Check if the server address is correct (IP and port)
3. Check network connection and firewall settings
4. Check proxy settings in IDE
5. View IDE log files for more information

### 4.3 What to do if the license cannot be activated?

1. Check if the license server is running normally
2. Check if the license code is correct
3. Check if the license has expired
4. Check IDE network connection
5. Try regenerating the license

### 4.4 Where are the log files?

If you are using a logging framework, you can check the log output location in the project's `logback.xml` or `log4j2.xml` configuration files. By default, logs may be output to the console or log files in the project root directory.

If running from the command line, you can redirect logs to a file:

```bash
java -jar target/Jetbrains-LicenseServer-Help-1.0-SNAPSHOT.jar > server.log 2>&1
```

## 5. Other Questions

### 5.1 Which JetBrains products does the project support?

The project supports all JetBrains commercial products, including but not limited to:
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

### 5.2 How to update the project version?

1. Pull the latest code from GitHub: `git pull origin main`
2. Rebuild the project: `mvn clean package`
3. Stop the old version server
4. Start the new version server

### 5.3 How to contribute code?

Please refer to the [Contributing Guide](./contributing.md) document to learn how to contribute code to the project.

### 5.4 How to get help?

If you encounter a problem not mentioned in this FAQ, you can get help through the following methods:

- Submit an [Issue](https://github.com/Blduu/Jetbrains-LicenseServer-Help/issues) on the GitHub project page
- Check the project documentation
- Contact the project maintainers

## 6. Changelog

### 1.0.0 (2023-10-01)

- Initial version released
- Support license server simulation
- Support product and plugin management
- Support license code generation
- Support ja-netfilter integration

### 1.1.0 (2023-11-15)

- Optimized web management interface
- Added product automatic update feature
- Fixed known bugs

## 7. Contact Us

If you have any questions or suggestions, please feel free to contact us:

- GitHub Issue: [https://github.com/Blduu/Jetbrains-LicenseServer-Help/issues](https://github.com/yourusername/Jetbrains-LicenseServer-Help/issues)
- Email: your-email@example.com
