# Core Features Documentation

This document provides detailed information about the core features, working principles, and usage methods of JetBrains License Server Help.

## 📋 Feature Modules Overview

| Feature Module | Main Responsibility | Core Class/Interface |
|---------------|--------------------|----------------------|
| License Server Simulation | Simulate official license server API | LicenseServerController |
| Product Management | Manage JetBrains product information | ProductsContextHolder |
| Plugin Management | Manage JetBrains plugin information | PluginsContextHolder |
| Certificate Management | Manage RSA keys and certificates | CertificateContextHolder |
| ja-netfilter Integration | Proxy tool download and configuration | AgentContextHolder |
| Visual Interface | Provide web management interface | Frontend HTML/JS/CSS files |

## 1. License Server Simulation

### 1.1 Feature Overview

License server simulation is the core functionality of this project. It implements the API protocol compatible with JetBrains official license server, supporting license ticket generation, validation, and prolongation.

### 1.2 Working Principle

1. **Protocol Compatibility**: Fully compatible with JetBrains official license server XML-RPC protocol
2. **Request Processing**: Receive license requests from JetBrains products
3. **Ticket Generation**: Generate unique license tickets for each request
4. **RSA Signing**: Sign response data using RSA private key
5. **XML Response**: Return XML response data in official format

### 1.3 Core API Interfaces

#### 1.3.1 Obtain License Ticket

```
POST /rpc/obtainTicket.action
```

**Request Parameters**:
- `hostName`: Client hostname
- `machineId`: Client machine unique identifier
- `salt`: Encryption salt value

**Response Example**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ObtainTicketResponse>
    <action>NONE</action>
    <confirmationStamp>1234567890</confirmationStamp>
    <leaseSignature>...</leaseSignature>
    <message></message>
    <prolongationPeriod>600000</prolongationPeriod>
    <responseCode>OK</responseCode>
    <salt>...</salt>
    <serverLease>...</serverLease>
    <serverUid>...</serverUid>
    <ticketId>...</ticketId>
    <ticketProperties>...</ticketProperties>
    <validationDeadlinePeriod>-1</validationDeadlinePeriod>
    <validationPeriod>600000</validationPeriod>
</ObtainTicketResponse>
```

#### 1.3.2 Prolong License Ticket

```
POST /rpc/prolongTicket.action
```

**Request Parameters**:
- `ticketId`: License ticket ID
- `machineId`: Client machine unique identifier
- `salt`: Encryption salt value

**Response Example**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ProlongTicketResponse>
    <action>NONE</action>
    <confirmationStamp>1234567890</confirmationStamp>
    <leaseSignature>...</leaseSignature>
    <message></message>
    <prolongationPeriod>600000</prolongationPeriod>
    <responseCode>OK</responseCode>
    <salt>...</salt>
    <serverLease>...</serverLease>
    <serverUid>...</serverUid>
    <ticketId>...</ticketId>
    <ticketProperties>...</ticketProperties>
    <validationDeadlinePeriod>-1</validationDeadlinePeriod>
    <validationPeriod>600000</validationPeriod>
</ProlongTicketResponse>
```

#### 1.3.3 Release License Ticket

```
POST /rpc/releaseTicket.action
```

**Request Parameters**:
- `ticketId`: License ticket ID
- `machineId`: Client machine unique identifier
- `salt`: Encryption salt value

**Response Example**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ReleaseTicketResponse>
    <action>NONE</action>
    <confirmationStamp>1234567890</confirmationStamp>
    <message></message>
    <responseCode>OK</responseCode>
    <salt>...</salt>
    <serverUid>...</serverUid>
</ReleaseTicketResponse>
```

#### 1.3.4 Server Status Check

```
POST /rpc/ping.action
```

**Response Example**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<PingResponse>
    <serverVersion>...</serverVersion>
    <serverUid>...</serverUid>
    <licensee>...</licensee>
    <licenseType>...</licenseType>
    <validThrough>...</validThrough>
    <gracePeriod>...</gracePeriod>
</PingResponse>
```

### 1.4 Usage Method

1. **Configure JetBrains Product**:
   - Open JetBrains product (e.g., IntelliJ IDEA)
   - Select "Help" -> "Register"
   - Select "License server"
   - Enter license server address: `http://your-server-ip:10768`
   - Click "Activate" to complete activation

2. **Verify Activation Status**:
   - After successful activation, JetBrains product will display "License server activated"
   - You can check activation status in product's "Help" -> "Register" page

## 2. Product and Plugin Management

### 2.1 Feature Overview

Product and plugin management is responsible for maintaining the JetBrains product and plugin information database, providing information query and scheduled update functions.

### 2.2 Working Principle

1. **Data Storage**: Product information is stored in `resources/external/data/product.json` file
2. **Data Loading**: Load product and plugin information from JSON files when application starts
3. **Scheduled Update**: Automatically update plugin information from JetBrains official API daily
4. **Information Query**: Provide REST API interfaces for frontend and other modules to query

### 2.3 Product Management

#### 2.3.1 Product Information Structure

Product information includes the following fields:
- `name`: Product name
- `code`: Product code
- `description`: Product description
- `version`: Product version
- `releaseDate`: Release date

#### 2.3.2 Product Query API

```
GET /data/products
```

**Response Example**:
```json
[
    {
        "name": "IntelliJ IDEA Ultimate",
        "code": "IIU",
        "description": "The Most Intelligent IDE for Java Developers",
        "version": "2024.1",
        "releaseDate": "2024-04-01"
    },
    {
        "name": "PyCharm Professional",
        "code": "PCC",
        "description": "Python IDE for Professional Developers",
        "version": "2024.1",
        "releaseDate": "2024-04-01"
    }
]
```

### 2.4 Plugin Management

#### 2.4.1 Plugin Information Structure

Plugin information includes the following fields:
- `id`: Plugin ID
- `name`: Plugin name
- `version`: Plugin version
- `description`: Plugin description
- `vendor`: Plugin vendor
- `category`: Plugin category
- `downloadUrl`: Plugin download URL

#### 2.4.2 Plugin Query API

```
GET /data/plugins
```

**Response Example**:
```json
[
    {
        "id": "com.intellij.ideolog",
        "name": "Ideolog",
        "version": "203.0.30",
        "description": "A plugin for viewing and analyzing logs",
        "vendor": "JetBrains",
        "category": "Developer Tools",
        "downloadUrl": "https://plugins.jetbrains.com/plugin/9746-ideolog"
    }
]
```

#### 2.4.3 Plugin Scheduled Update

Plugin information is automatically updated from JetBrains official API daily. Update time can be customized through configuration file:

```yaml
help:
  plugins:
    refresh-enabled: true  # Whether to enable scheduled refresh
    refresh-cron: "0 0 12 * * ?"  # Refresh time (every day at 12:00 noon)
    page-size: 20  # Page size
    thread-count: 5  # Number of concurrent threads
    timeout: 30000  # Request timeout (milliseconds)
```

## 3. Certificate Management

### 3.1 Feature Overview

Certificate management is responsible for generating and managing RSA key pairs and X.509 certificates to ensure the security of license data.

### 3.2 Working Principle

1. **Key Generation**: Automatically generate RSA key pairs when application starts (if not exists)
2. **Certificate Creation**: Create X.509 certificate based on RSA key pairs
3. **Key Storage**: Store keys and certificates in `resources/external/certificate/` directory
4. **Signature Verification**: Use private key to sign response data, and public key to verify

### 3.3 Certificate Query API

```
GET /data/certificate
```

**Response Example**:
```json
{
    "publicKey": "-----BEGIN PUBLIC KEY-----...-----END PUBLIC KEY-----",
    "privateKey": "-----BEGIN PRIVATE KEY-----...-----END PRIVATE KEY-----",
    "certificate": "-----BEGIN CERTIFICATE-----...-----END CERTIFICATE-----",
    "fingerprint": "12:34:56:78:90:AB:CD:EF:12:34:56:78:90:AB:CD:EF:12:34:56:78"
}
```

## 4. ja-netfilter Integration

### 4.1 Feature Overview

ja-netfilter integration provides download and configuration of ja-netfilter proxy tool for assisting JetBrains product license verification.

### 4.2 Working Principle

1. **Tool Download**: Download the latest version of ja-netfilter tool from official address
2. **Configuration Generation**: Generate custom proxy configuration based on user needs
3. **One-click Deployment**: Provide compressed package download, users can directly decompress and use

### 4.3 Usage Method

1. **Download ja-netfilter**:
   - Click "ja-netfilter download" button in management interface
   - Or directly access API interface: `GET /zip/ja-netfilter`

2. **Configuration and Usage**:
   - Decompress the downloaded package to any directory
   - Run the startup script in the directory (`start.sh` or `start.bat`)
   - Configure JetBrains product to use ja-netfilter proxy

3. **Proxy Configuration**:
   - Default proxy address: `http://localhost:8000`
   - You can customize proxy rules in `ja-netfilter/config` directory

### 4.4 API Interface

```
GET /zip/ja-netfilter
```

**Response**: ja-netfilter compressed package (ZIP format)

## 5. Visual Management Interface

### 5.1 Feature Overview

Visual management interface provides a friendly web interface for managing licenses, querying product information, and downloading ja-netfilter tool.

### 5.2 Interface Composition

1. **Home Page**: Project introduction and function entrance
2. **License Management**: License generation and management
3. **Product List**: View all supported JetBrains products
4. **Plugin List**: View all available JetBrains plugins
5. **ja-netfilter Download**: Download and configure ja-netfilter proxy tool
6. **System Settings**: Configure system parameters

### 5.3 Usage Method

1. **Access Interface**: Access `http://your-server-ip:10768` in browser
2. **Navigation Menu**: Use top navigation menu to access various functional modules
3. **Operation Buttons**: Click buttons on the interface to perform corresponding operations
4. **View Information**: View product, plugin and license information on the interface

## 6. Other Auxiliary Functions

### 6.1 File Download Function

Provide download function for license files and configuration files:

```
GET /zip/license
```

**Response**: License file compressed package

### 6.2 System Information Query

Provide system information query function:

```
GET /data/system
```

**Response Example**:
```json
{
    "version": "0.0.1-SNAPSHOT",
    "javaVersion": "17.0.10",
    "os": "Windows 10",
    "memory": {
        "total": "512MB",
        "used": "256MB",
        "free": "256MB"
    },
    "uptime": "1h 30m 45s"
}
```

## 🔧 Feature Configuration

All features can be customized through `application.yml` configuration file:

```yaml
server:
  port: 10768  # Server port

help:
  # License server configuration
  license:
    server-uid: "12345678-1234-1234-1234-123456789012"  # Server unique identifier
    lease-duration: 365  # Lease validity period (days)
    validation-period: 600000  # Validation period (milliseconds)
    
  # Product configuration
  products:
    refresh-enabled: true  # Whether to enable product information refresh
    refresh-interval: 86400000  # Refresh interval (milliseconds)
    
  # Plugin configuration
  plugins:
    refresh-enabled: true  # Whether to enable plugin information refresh
    refresh-cron: "0 0 12 * * ?"  # Refresh time
    page-size: 20  # Page size
    thread-count: 5  # Number of concurrent threads
    timeout: 30000  # Request timeout (milliseconds)
    
  # ja-netfilter configuration
  agent:
    enabled: true  # Whether to enable ja-netfilter integration
    download-url: "https://github.com/ja-netfilter/ja-netfilter/releases/latest/download/ja-netfilter.zip"  # Download URL
    config-dir: "agent/config"  # Configuration directory
    
  # Certificate configuration
  certificate:
    key-size: 2048  # Key size
    validity: 3650  # Certificate validity period (days)
    alias: "license-server"  # Certificate alias
    keystore-password: "password"  # Keystore password
```

## 📊 Feature Usage Statistics

The system automatically collects usage statistics of various features, which can be queried through the following API:

```
GET /data/stats
```

**Response Example**:
```json
{
    "obtainTicketCount": 100,  # Number of license ticket obtaining
    "prolongTicketCount": 50,  # Number of license ticket prolonging
    "releaseTicketCount": 20,  # Number of license ticket releasing
    "pingCount": 150,  # Number of server status checking
    "productQueryCount": 80,  # Number of product querying
    "pluginQueryCount": 30,  # Number of plugin querying
    "certificateQueryCount": 10,  # Number of certificate querying
    "jaNetfilterDownloadCount": 25,  # Number of ja-netfilter downloading
    "uptime": 3600000  # System running time (milliseconds)
}
```

## 🎯 Feature Best Practices

1. **License Server Deployment**:
   - It is recommended to deploy license server on an independent server
   - Ensure server has stable network connection
   - Regularly backup key and certificate files

2. **Product Activation**:
   - Configure all JetBrains products with unified license server address
   - Regularly check activation status to ensure licenses are valid
   - Avoid activating the same product multiple times on the same machine

3. **Plugin Management**:
   - Enable scheduled update function for plugin information
   - Filter and search plugins as needed
   - Regularly clean up unnecessary plugins

4. **ja-netfilter Usage**:
   - Ensure ja-netfilter is compatible with JetBrains product version
   - Regularly update ja-netfilter to the latest version
   - Customize proxy rules to meet specific needs

## 🔍 Feature Troubleshooting

### License Server Failure

**Problem**: JetBrains product cannot connect to license server

**Troubleshooting Steps**:
1. Check if server is running normally
2. Check network connection and firewall settings
3. View server logs to find error information
4. Verify if server port is open
5. Check if license server address is correct

### Product Activation Failure

**Problem**: JetBrains product activation fails

**Troubleshooting Steps**:
1. Check if license server is running normally
2. Verify server address and port are correct
3. View server logs to find error information
4. Check if product is in support list
5. Try restarting JetBrains product and license server

### Plugin Update Failure

**Problem**: Plugin information cannot be updated

**Troubleshooting Steps**:
1. Check if network connection is normal
2. Verify if JetBrains official API is accessible
3. View server logs to find error information
4. Adjust plugin update configuration parameters
5. Try manually updating plugin information

## 📌 Feature Limitations

1. **Product Support**: Only supports officially released JetBrains products
2. **Version Compatibility**: Supports latest versions of JetBrains products
3. **Concurrent Limit**: Default supports 1000 concurrent connections
4. **License Quantity**: No explicit limit (depends on server performance)
5. **Network Dependency**: Plugin updates require access to JetBrains official API

---

Through the collaborative work of the above functional modules, JetBrains License Server Help provides a complete JetBrains product license management solution.