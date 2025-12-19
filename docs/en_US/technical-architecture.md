# Technical Architecture Documentation

This document provides detailed information about the technical architecture, system design, core modules, and data flow of JetBrains License Server Help.

## 📋 Architecture Overview

JetBrains License Server Help is built based on the Spring Boot 4.0 framework, adopting a modern layered architecture design. It implements API protocol compatibility with the official JetBrains license server, while providing product/plugin management, certificate management, ja-netfilter integration, and other functions.

### 1.1 Architecture Layers

| Architecture Layer | Main Responsibility | Core Components |
|------------------|--------------------|----------------|
| Presentation Layer | Handle HTTP requests and provide API interfaces | Controller classes |
| Business Layer | Implement business logic | Service classes |
| Data Access Layer | Data loading and storage | ContextHolder classes |
| Utility Layer | Provide common tool support | Util classes |
| Resource Layer | Static resources and configuration files | HTML/CSS/JS, application.yml |

### 1.2 Core Technology Stack

| Technology Category | Technology Selection | Version | Purpose |
|------------------|---------------------|--------|--------|
| Backend Framework | Spring Boot | 4.0 | Application development framework |
| Programming Language | Java | 17 | Development language |
| Build Tool | Maven | 3.6+ | Project build and dependency management |
| Utility Library | Hutool | 5.8.28 | General utility class library |
| Code Simplification | Lombok | 1.18.34 | Reduce boilerplate code |
| Cryptography Library | BouncyCastle | 1.78.1 | Cryptography and certificate processing |
| XML Processing | JAXB | - | XML data parsing and generation |
| Frontend Technology | HTML5 + CSS3 + JavaScript | - | Frontend page development |
| Containerization | Docker | - | Application containerization deployment |

## 2. Core Module Architecture

### 2.1 License Server Simulation Module

The license server simulation module is the core functionality of the project, implementing API protocol compatibility with the official JetBrains license server.

#### 2.1.1 Module Architecture

```
┌─────────────────────────┐     ┌─────────────────────────┐
│  LicenseServerController │────▶│  LicenseContextHolder   │
└─────────────────────────┘     └─────────────────────────┘
          ▲                              │
          │                              ▼
┌─────────────────────────┐     ┌─────────────────────────┐
│  CertificateContextHolder │◀────│  LicenseServerUtils    │
└─────────────────────────┘     └─────────────────────────┘
```

#### 2.1.2 Workflow

1. Receive license requests from JetBrains products
2. Verify the validity of request parameters
3. Generate unique license tickets
4. Sign response data using RSA private key
5. Return XML responses in official format

### 2.2 Product and Plugin Management Module

The product and plugin management module is responsible for maintaining the JetBrains product and plugin information database, providing information query and scheduled update functions.

#### 2.2.1 Module Architecture

```
┌─────────────────────────┐     ┌─────────────────────────┐
│  DataController         │────▶│  ProductsContextHolder  │
└─────────────────────────┘     └─────────────────────────┘
          ▲                              │
          │                              ▼
┌─────────────────────────┐     ┌─────────────────────────┐
│  PluginsContextHolder   │◀────│  Product/Plugin JSON    │
└─────────────────────────┘     └─────────────────────────┘
          │
          ▼
┌─────────────────────────┐     ┌─────────────────────────┐
│  PluginProcessService   │────▶│  PluginApiService       │
└─────────────────────────┘     └─────────────────────────┘
```

#### 2.2.2 Workflow

1. Load product and plugin information from JSON files when the application starts
2. Provide REST API interfaces for frontend and other modules to query
3. Regularly update plugin information from the official JetBrains API
4. Store updated information to JSON files

### 2.3 Certificate Management Module

The certificate management module is responsible for generating and managing RSA key pairs and X.509 certificates to ensure the security of license data.

#### 2.3.1 Module Architecture

```
┌─────────────────────────┐     ┌─────────────────────────┐
│  DataController         │────▶│  CertificateContextHolder│
└─────────────────────────┘     └─────────────────────────┘
          │                              │
          │                              ▼
┌─────────────────────────┐     ┌─────────────────────────┐
│  Certificate Files      │◀────│  BouncyCastle API       │
└─────────────────────────┘     └─────────────────────────┘
```

#### 2.3.2 Workflow

1. Check if key and certificate files exist when the application starts
2. Automatically generate RSA key pairs and X.509 certificates if they don't exist
3. Store keys and certificates to the file system
4. Provide API interfaces for other modules to obtain certificate information

### 2.4 ja-netfilter Integration Module

The ja-netfilter integration module provides download and configuration functions for the ja-netfilter proxy tool, assisting with license verification for JetBrains products.

#### 2.4.1 Module Architecture

```
┌─────────────────────────┐     ┌─────────────────────────┐
│  ZipController          │────▶│  AgentContextHolder     │
└─────────────────────────┘     └─────────────────────────┘
          │                              │
          │                              ▼
┌─────────────────────────┐     ┌─────────────────────────┐
│  ja-netfilter.zip       │◀────│  Download Service       │
└─────────────────────────┘     └─────────────────────────┘
```

#### 2.4.2 Workflow

1. Download the latest version of the ja-netfilter tool from the official address
2. Generate custom proxy configuration files
3. Package the ja-netfilter tool and configuration files into ZIP format
4. Provide download API interfaces for users to obtain

### 2.5 Visual Management Interface

The visual management interface provides a user-friendly web interface for managing licenses, querying product information, and downloading the ja-netfilter tool.

#### 2.5.1 Module Architecture

```
┌─────────────────────────┐     ┌─────────────────────────┐
│  Browser                │────▶│  Static Resources       │
└─────────────────────────┘     └─────────────────────────┘
          │                              │
          │                              ▼
┌─────────────────────────┐     ┌─────────────────────────┐
│  JavaScript             │────▶│  REST API               │
└─────────────────────────┘     └─────────────────────────┘
```

#### 2.5.2 Workflow

1. Users access the application homepage through a browser
2. Load HTML, CSS, and JavaScript static resources
3. JavaScript interacts with the backend through REST API
4. Display product lists, plugin lists, and license information

## 3. Data Flow

### 3.1 License Request Processing Flow

```
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│ JetBrains Product │────▶│ LicenseServer    │────▶│ LicenseContext   │
│                  │     │ Controller       │     │ Holder           │
└──────────────────┘     └──────────────────┘     └──────────────────┘
                                 │                         │
                                 ▼                         ▼
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│ XML Response     │◀────│ LicenseServer    │◀────│ Certificate      │
│                  │     │ Utils            │     │ Context Holder   │
└──────────────────┘     └──────────────────┘     └──────────────────┘
```

### 3.2 Product/Plugin Information Loading Flow

```
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│ Application      │────▶│ ProductsContext  │────▶│ product.json     │
│ Start            │     │ Holder           │     │                  │
└──────────────────┘     └──────────────────┘     └──────────────────┘
          │
          ▼
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│ PluginsContext   │────▶│ PluginProcess    │────▶│ JetBrains Plugin │
│ Holder           │     │ Service          │     │ API              │
└──────────────────┘     └──────────────────┘     └──────────────────┘
          │                         │
          ▼                         ▼
┌──────────────────┐     ┌──────────────────┐
│ plugin.json      │◀────│ PluginApiService │
│                  │     │                  │
└──────────────────┘     └──────────────────┘
```

### 3.3 ja-netfilter Download Flow

```
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│ User Browser     │────▶│ ZipController    │────▶│ AgentContext     │
│                  │     │                  │     │ Holder           │
└──────────────────┘     └──────────────────┘     └──────────────────┘
                                 │                         │
                                 ▼                         ▼
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│ ZIP File         │◀────│ FileTools        │◀────│ ja-netfilter     │
│                  │     │                  │     │ Download         │
└──────────────────┘     └──────────────────┘     └──────────────────┘
```

## 4. System Architecture Diagram

```
┌────────────────────────────────────────────────────────────────────┐
│                        User Layer                                  │
│  ┌─────────────┐     ┌─────────────┐     ┌─────────────┐           │
│  │ JetBrains   │     │ Web Browser │     │ REST Client │           │
│  │ Products    │     │             │     │             │           │
│  └──────┬──────┘     └──────┬──────┘     └──────┬──────┘           │
│         │                   │                   │                   │
└─────────┼───────────────────┼───────────────────┼──────────────────┘
          │                   │                   │
┌─────────▼───────────────────▼───────────────────▼──────────────────┐
│                        Presentation Layer                          │
│  ┌─────────────┐     ┌─────────────┐     ┌─────────────┐           │
│  │ LicenseServer│     │ DataController│     │ ZipController│         │
│  │ Controller  │     │             │     │             │           │
│  └──────┬──────┘     └──────┬──────┘     └──────┬──────┘           │
│         │                   │                   │                   │
└─────────┼───────────────────┼───────────────────┼──────────────────┘
          │                   │                   │
┌─────────▼───────────────────▼───────────────────▼──────────────────┐
│                        Business Layer                              │
│  ┌─────────────┐     ┌─────────────┐     ┌─────────────┐           │
│  │ LicenseContext│    │ ProductsContext│    │ PluginsContext│        │
│  │ Holder       │    │ Holder       │    │ Holder       │           │
│  └──────┬──────┘     └──────┬──────┘     └──────┬──────┘           │
│         │                   │                   │                   │
│  ┌──────▼──────┐     ┌──────▼──────┐     ┌──────▼──────┐           │
│  │ Certificate │     │ AgentContext│     │ PluginProcess│           │
│  │ ContextHolder│    │ Holder      │    │ Service      │           │
│  └──────┬──────┘     └──────┬──────┘     └──────┬──────┘           │
│         │                   │                   │                   │
└─────────┼───────────────────┼───────────────────┼──────────────────┘
          │                   │                   │
┌─────────▼───────────────────▼───────────────────▼──────────────────┐
│                        Data Layer                                  │
│  ┌─────────────┐     ┌─────────────┐     ┌─────────────┐           │
│  │ product.json│     │ plugin.json │     │ Certificate │           │
│  │             │     │             │     │ Files        │           │
│  └──────┬──────┘     └──────┬──────┘     └──────┬──────┘           │
│         │                   │                   │                   │
└─────────┼───────────────────┼───────────────────┼──────────────────┘
          │                   │                   │
┌─────────▼───────────────────▼───────────────────▼──────────────────┐
│                        Utility Layer                               │
│  ┌─────────────┐     ┌─────────────┐     ┌─────────────┐           │
│  │ LicenseServer│     │ FileTools   │     │ JAXB Utils  │           │
│  │ Utils        │     │             │     │             │           │
│  └──────┬──────┘     └──────┬──────┘     └──────┬──────┘           │
│         │                   │                   │                   │
└─────────┼───────────────────┼───────────────────┼──────────────────┘
          │                   │                   │
┌─────────▼───────────────────▼───────────────────▼──────────────────┐
│                        Resource Layer                              │
│  ┌─────────────┐     ┌─────────────┐     ┌─────────────┐           │
│  │ HTML/CSS/JS │     │ application.yml│   │ logback-spring.xml│    │
│  │             │     │             │     │             │           │
│  └─────────────┘     └─────────────┘     └─────────────┘           │
└────────────────────────────────────────────────────────────────────┘
```

## 5. Core Component Design

### 5.1 Controller Components

#### 5.1.1 LicenseServerController

- **Responsibility**: Handle license requests from JetBrains products
- **Core Methods**:
  - `obtainTicket()`: Obtain license tickets
  - `prolongTicket()`: Prolong license ticket validity
  - `releaseTicket()`: Release license tickets
  - `ping()`: Check server status

#### 5.1.2 DataController

- **Responsibility**: Provide query interfaces for product, plugin, and certificate information
- **Core Methods**:
  - `getProducts()`: Get product list
  - `getPlugins()`: Get plugin list
  - `getCertificate()`: Get certificate information
  - `getSystemInfo()`: Get system information

#### 5.1.3 ZipController

- **Responsibility**: Provide file download functionality
- **Core Methods**:
  - `getJaNetfilter()`: Download ja-netfilter tool
  - `getLicenseFiles()`: Download license files

### 5.2 ContextHolder Components

#### 5.2.1 LicenseContextHolder

- **Responsibility**: Manage license-related context information
- **Core Functions**:
  - Generate unique server UID
  - Manage license lease and validation periods
  - Provide license ticket generation services

#### 5.2.2 ProductsContextHolder

- **Responsibility**: Manage JetBrains product information
- **Core Functions**:
  - Load product information from JSON files
  - Provide product information query interfaces
  - Support scheduled updates of product information

#### 5.2.3 PluginsContextHolder

- **Responsibility**: Manage JetBrains plugin information
- **Core Functions**:
  - Load plugin information from JSON files
  - Provide plugin information query interfaces
  - Support scheduled updates of plugin information

#### 5.2.4 CertificateContextHolder

- **Responsibility**: Manage RSA key pairs and X.509 certificates
- **Core Functions**:
  - Generate RSA key pairs
  - Create X.509 certificates
  - Store and load keys and certificates

#### 5.2.5 AgentContextHolder

- **Responsibility**: Manage ja-netfilter proxy tool
- **Core Functions**:
  - Download ja-netfilter tool
  - Generate proxy configuration files
  - Package and provide download services

### 5.3 Service Components

#### 5.3.1 PluginProcessService

- **Responsibility**: Handle scheduled updates of plugin information
- **Core Functions**:
  - Call JetBrains official API to obtain plugin information
  - Process plugin data with multiple threads
  - Update plugin information to JSON files

#### 5.3.2 PluginApiService

- **Responsibility**: Interact with JetBrains plugin API
- **Core Functions**:
  - Send HTTP requests to obtain plugin lists
  - Parse API response data
  - Convert to internal data structures

#### 5.3.3 JrebelService

- **Responsibility**: Provide JRebel-related functions
- **Core Functions**:
  - JRebel license generation
  - JRebel license signing

### 5.4 Util Components

#### 5.4.1 LicenseServerUtils

- **Responsibility**: Provide utility methods related to license server
- **Core Functions**:
  - XML response generation
  - RSA signing and verification
  - License ticket processing

#### 5.4.2 FileTools

- **Responsibility**: Provide utility methods related to file operations
- **Core Functions**:
  - File reading and writing
  - ZIP packaging and decompression
  - Directory operations

## 6. Configuration Management

The project uses Spring Boot's configuration file mechanism, with the main configuration file being `application.yml`, containing the following configuration items:

### 6.1 Server Configuration

```yaml
server:
  port: 10768  # Server port
```

### 6.2 License Server Configuration

```yaml
help:
  license:
    server-uid: "12345678-1234-1234-1234-123456789012"  # Server unique identifier
    lease-duration: 365  # Lease validity period (days)
    validation-period: 600000  # Validation period (milliseconds)
```

### 6.3 Product Configuration

```yaml
help:
  products:
    refresh-enabled: true  # Whether to enable product information refresh
    refresh-interval: 86400000  # Refresh interval (milliseconds)
```

### 6.4 Plugin Configuration

```yaml
help:
  plugins:
    refresh-enabled: true  # Whether to enable plugin information refresh
    refresh-cron: "0 0 12 * * ?"  # Refresh time
    page-size: 20  # Page size
    thread-count: 5  # Number of concurrent threads
    timeout: 30000  # Request timeout (milliseconds)
```

### 6.5 ja-netfilter Configuration

```yaml
help:
  agent:
    enabled: true  # Whether to enable ja-netfilter integration
    download-url: "https://github.com/ja-netfilter/ja-netfilter/releases/latest/download/ja-netfilter.zip"  # Download URL
    config-dir: "agent/config"  # Configuration directory
```

### 6.6 Certificate Configuration

```yaml
help:
  certificate:
    key-size: 2048  # Key size
    validity: 3650  # Certificate validity period (days)
    alias: "license-server"  # Certificate alias
    keystore-password: "password"  # Keystore password
```

## 7. Technology Selection Rationale

### 7.1 Spring Boot

- **Rationale**: Provides a rapid development framework that simplifies configuration and deployment processes
- **Advantages**: Auto-configuration, embedded server, rich ecosystem

### 7.2 Java 17

- **Rationale**: Provides modern Java feature support while maintaining good performance and stability
- **Advantages**: Enhanced type inference, sealed classes, pattern matching, etc.

### 7.3 Maven

- **Rationale**: Mature build tool with rich plugin and dependency management features
- **Advantages**: Standardized build process, dependency version management, multi-module support

### 7.4 Hutool

- **Rationale**: Provides rich utility classes that simplify development work
- **Advantages**: Comprehensive functionality, friendly API, easy to use

### 7.5 BouncyCastle

- **Rationale**: Provides powerful cryptography support to meet certificate generation and signing needs
- **Advantages**: Supports multiple encryption algorithms, rich certificate processing functions

### 7.6 Frontend Technology Stack (HTML5 + CSS3 + JavaScript)

- **Rationale**: Lightweight frontend technology stack that meets simple visual management needs
- **Advantages**: No additional build tools required, fast loading speed, easy maintenance

## 8. Extensibility Design

### 8.1 Module Extension

The project adopts a modular design, with each functional module interacting through interfaces, facilitating the addition of new functional modules in the future.

### 8.2 Configuration Extension

Using Spring Boot's configuration file mechanism, it supports extending and customizing functions through configuration files without modifying code.

### 8.3 API Extension

Provides REST API interfaces that facilitate integration and extension with other systems.

### 8.4 Data Extension

Uses JSON files to store data, facilitating future expansion to database storage.

## 9. Security Design

### 9.1 Data Encryption

- Uses RSA key pairs and X.509 certificates to sign license data, ensuring data integrity and authenticity

### 9.2 Access Control

- Provides a simple access control mechanism to restrict access to sensitive APIs

### 9.3 Input Validation

- Validates all input parameters to prevent malicious input and attacks

### 9.4 Security Configuration

- Supports HTTPS configuration to ensure data transmission security

## 10. Performance Optimization

### 10.1 Cache Mechanism

- Caches product and plugin information to reduce file reading and network requests

### 10.2 Multi-threading

- Uses multi-threading to process plugin information updates, improving processing efficiency

### 10.3 Lazy Loading

- Adopts lazy loading mechanism, only loading related resources when needed

### 10.4 Connection Pool

- Uses connection pool to manage HTTP connections, reducing connection establishment overhead

## 11. Summary

JetBrains License Server Help adopts a modern layered architecture design, built on the Spring Boot 4.0 framework. It implements API protocol compatibility with the official JetBrains license server, while providing product/plugin management, certificate management, ja-netfilter integration, and other functions. The project has good extensibility, security, and performance optimization, meeting user needs.