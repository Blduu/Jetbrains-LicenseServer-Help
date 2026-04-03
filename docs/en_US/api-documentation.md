# API Documentation

This document provides detailed information about all API interfaces of the JetBrains License Server Help project, including REST API and XML-RPC interfaces. Each interface includes request method, path, parameters, response examples, and usage instructions.

## 1. REST API Interfaces

### 1.1 Data Interface (DataController)

#### 1.1.1 Get JetBrains Product List

**Interface Description**: Get a list of all supported JetBrains product information for dynamic display and user selection in the frontend interface.

**Request Method**: GET
**Request Path**: `/api/products`
**Request Parameters**: None

**Response Example**:

```json
[
  {
    "name": "IntelliJ IDEA Ultimate",
    "productCode": "II",
    "iconClass": "icon-idea"
  },
  {
    "name": "PhpStorm",
    "productCode": "PS", 
    "iconClass": "icon-phpstorm"
  },
  {
    "name": "PyCharm Professional",
    "productCode": "PY",
    "iconClass": "icon-pycharm"
  }
]
```

**Response Parameter Description**:

| Parameter    | Type   | Description                       |
|--------------|--------|-----------------------------------|
| name         | String | Product display name              |
| productCode  | String | Product code (used for license generation) |
| iconClass    | String | Icon CSS class name               |

#### 1.1.2 Get JetBrains Paid Plugin List

**Interface Description**: Get a list of all supported JetBrains paid plugin information for dynamic display and user selection in the frontend interface.

**Request Method**: GET
**Request Path**: `/api/plugins`
**Request Parameters**: None

**Response Example**:

```json
[
  {
    "id": 7973,
    "name": "SonarLint",
    "productCode": "SONAR_LINT",
    "pricingModel": "PAID",
    "icon": "https://plugins.jetbrains.com/files/7973/icon.svg"
  },
  {
    "id": 12894,
    "name": "Key Promoter X",
    "productCode": "KEY_PROMOTER_X",
    "pricingModel": "PAID",
    "icon": "https://plugins.jetbrains.com/files/12894/icon.svg"
  }
]
```

**Response Parameter Description**:

| Parameter    | Type   | Description                       |
|--------------|--------|-----------------------------------|
| id           | Long   | Plugin unique identifier          |
| name         | String | Plugin display name               |
| productCode  | String | Product code (used for license generation) |
| pricingModel | String | Pricing model (PAID indicates paid) |
| icon         | String | Plugin icon URL                   |

### 1.2 License Code Generation Interface (LicenseCodeController)

#### 1.2.1 Generate License Code

**Interface Description**: Generate activation codes (license codes) for JetBrains products, supporting personal or enterprise licenses.

**Request Method**: POST
**Request Path**: `/license-code/generate`
**Request Parameters**:

| Parameter    | Type   | Required | Description                       |
|--------------|--------|----------|-----------------------------------|
| licenseName  | String | Yes      | License name (company or organization name) |
| assigneeName | String | Yes      | Assignee name (user name)         |
| expiryDate   | String | Yes      | Expiry date (format: yyyy-MM-dd)  |
| productCode  | String | No       | Product code (multiple codes separated by commas, includes all products if empty) |

**Request Example**:

```json
{
  "licenseName": "BlueSky Technology",
  "assigneeName": "Zhang San",
  "expiryDate": "2025-12-31",
  "productCode": "II,PS,WS,RM,PCC,PC,CLN"
}
```

**Response Example**:

```json
{
  "licenseCode": "QMT-XXXXXXXX-XXXXXXXX-XXXXXXXX-XXXXXXXX",
  "message": "License generated successfully"
}
```

**Response Parameter Description**:

| Parameter    | Type   | Description                       |
|--------------|--------|-----------------------------------|
| licenseCode  | String | Generated license code            |
| message      | String | Operation result message          |

### 1.3 File Download Interface (ZipController)

#### 1.3.1 Download ja-netfilter Proxy Tool

**Interface Description**: Provide pre-configured ja-netfilter tool package download service for use in JetBrains IDEs.

**Request Method**: GET
**Request Path**: `/ja-netfilter`
**Request Parameters**: None

**Response**: ZIP file attachment containing ja-netfilter tool

**Return Content**:
- ja-netfilter.jar - Core program
- plugins/ - Various functional plugins
- config/ - Configuration files (pre-configured with JetBrains product parameters)
- README.md - Usage instructions

### 1.4 JRebel Controller Interface (JRebelController)

#### 1.4.1 Get GUID

**Interface Description**: Generate and return a unique identifier (GUID) for JRebel-related functions.

**Request Method**: GET
**Request Path**: `/guid`
**Request Parameters**: None

**Response Example**:

```
550e8400-e29b-41d4-a716-446655440000
```

#### 1.4.2 JRebel Lease Management

**Interface Description**: Handle JRebel-related lease requests.

**Request Method**: POST
**Request Path**: `/jrebel/leases`
**Request Parameters**: Automatically generated by JRebel client
**Response**: JRebel lease response

#### 1.4.3 JRebel Lease Management 1

**Interface Description**: Handle JRebel-related lease requests (version 1).

**Request Method**: POST
**Request Path**: `/jrebel/leases/1`
**Request Parameters**: Automatically generated by JRebel client
**Response**: JRebel lease response

#### 1.4.4 Agent Lease Management

**Interface Description**: Handle Agent-related lease requests.

**Request Method**: POST
**Request Path**: `/agent/leases`
**Request Parameters**: Automatically generated by Agent client
**Response**: Agent lease response

#### 1.4.5 Agent Lease Management 1

**Interface Description**: Handle Agent-related lease requests (version 1).

**Request Method**: POST
**Request Path**: `/agent/leases/1`
**Request Parameters**: Automatically generated by Agent client
**Response**: Agent lease response

#### 1.4.6 JRebel Connection Validation

**Interface Description**: Validate the connection between JRebel client and server.

**Request Method**: GET
**Request Path**: `/jrebel/validate-connection`
**Request Parameters**: None
**Response**: JRebel connection validation response

## 2. XML-RPC Interfaces (LicenseServerController)

### 2.1 Obtain License Ticket

**Interface Description**: Called by JetBrains products when first connecting to the license server to obtain a license ticket.

**Request Method**: POST
**Request Path**: `/rpc/obtainTicket.action`
**Request Parameters**:

| Parameter    | Type   | Required | Description                       |
|--------------|--------|----------|-----------------------------------|
| hostName     | String | Yes      | Client host name, used to identify license users |
| machineId    | String | Yes      | Client machine unique identifier, used for hardware binding |
| salt         | String | Yes      | Encryption salt value, used to enhance communication security |

**Response Example**:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<methodResponse>
    <params>
        <param>
            <value>
                <struct>
                    <member>
                        <name>action</name>
                        <value><string>NONE</string></value>
                    </member>
                    <member>
                        <name>confirmationStamp</name>
                        <value><string>20231001T120000Z</string></value>
                    </member>
                    <member>
                        <name>leaseSignature</name>
                        <value><string>signature_content_here</string></value>
                    </member>
                    <member>
                        <name>message</name>
                        <value><string></string></value>
                    </member>
                    <member>
                        <name>responseCode</name>
                        <value><string>OK</string></value>
                    </member>
                    <member>
                        <name>salt</name>
                        <value><string>original_salt_here</string></value>
                    </member>
                </struct>
            </value>
        </param>
    </params>
</methodResponse>
```

### 2.2 License Server Connectivity Check

**Interface Description**: Regularly called by JetBrains products to check the availability of the license server.

**Request Method**: POST
**Request Path**: `/rpc/ping.action`
**Request Parameters**:

| Parameter    | Type   | Required | Description                       |
|--------------|--------|----------|-----------------------------------|
| hostName     | String | Yes      | Client host name, used to identify license users |
| machineId    | String | Yes      | Client machine unique identifier, used for hardware binding |
| salt         | String | Yes      | Encryption salt value, used to enhance communication security |

**Response Example**:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<methodResponse>
    <params>
        <param>
            <value>
                <struct>
                    <member>
                        <name>action</name>
                        <value><string>NONE</string></value>
                    </member>
                    <member>
                        <name>confirmationStamp</name>
                        <value><string>20231001T120000Z</string></value>
                    </member>
                    <member>
                        <name>leaseSignature</name>
                        <value><string>signature_content_here</string></value>
                    </member>
                    <member>
                        <name>message</name>
                        <value><string></string></value>
                    </member>
                    <member>
                        <name>responseCode</name>
                        <value><string>OK</string></value>
                    </member>
                    <member>
                        <name>salt</name>
                        <value><string>original_salt_here</string></value>
                    </member>
                </struct>
            </value>
        </param>
    </params>
</methodResponse>
```

### 2.3 Prolong License Validity

**Interface Description**: Regularly called by JetBrains products to prolong the validity of the license.

**Request Method**: POST
**Request Path**: `/rpc/prolongTicket.action`
**Request Parameters**:

| Parameter    | Type   | Required | Description                       |
|--------------|--------|----------|-----------------------------------|
| hostName     | String | Yes      | Client host name, used to identify license users |
| machineId    | String | Yes      | Client machine unique identifier, used for hardware binding |
| salt         | String | Yes      | Encryption salt value, used to enhance communication security |

**Response Example**:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<methodResponse>
    <params>
        <param>
            <value>
                <struct>
                    <member>
                        <name>action</name>
                        <value><string>NONE</string></value>
                    </member>
                    <member>
                        <name>confirmationStamp</name>
                        <value><string>20231001T120000Z</string></value>
                    </member>
                    <member>
                        <name>leaseSignature</name>
                        <value><string>signature_content_here</string></value>
                    </member>
                    <member>
                        <name>message</name>
                        <value><string></string></value>
                    </member>
                    <member>
                        <name>responseCode</name>
                        <value><string>OK</string></value>
                    </member>
                    <member>
                        <name>salt</name>
                        <value><string>original_salt_here</string></value>
                    </member>
                </struct>
            </value>
        </param>
    </params>
</methodResponse>
```

### 2.4 Release License Ticket

**Interface Description**: Called when JetBrains products are closed or users actively exit to release occupied license tickets.

**Request Method**: POST
**Request Path**: `/rpc/releaseTicket.action`
**Request Parameters**:

| Parameter    | Type   | Required | Description                       |
|--------------|--------|----------|-----------------------------------|
| hostName     | String | Yes      | Client host name, used to identify license users |
| machineId    | String | Yes      | Client machine unique identifier, used for hardware binding |
| salt         | String | Yes      | Encryption salt value, used to enhance communication security |

**Response Example**:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<methodResponse>
    <params>
        <param>
            <value>
                <struct>
                    <member>
                        <name>action</name>
                        <value><string>NONE</string></value>
                    </member>
                    <member>
                        <name>confirmationStamp</name>
                        <value><string>20231001T120000Z</string></value>
                    </member>
                    <member>
                        <name>leaseSignature</name>
                        <value><string>signature_content_here</string></value>
                    </member>
                    <member>
                        <name>message</name>
                        <value><string></string></value>
                    </member>
                    <member>
                        <name>responseCode</name>
                        <value><string>OK</string></value>
                    </member>
                    <member>
                        <name>salt</name>
                        <value><string>original_salt_here</string></value>
                    </member>
                </struct>
            </value>
        </param>
    </params>
</methodResponse>
```

## 2. API Usage Examples

### 2.1 Using cURL to Call REST API

#### 2.1.1 Get Product List

```bash
curl -X GET http://localhost:10768/api/products
```

#### 2.1.2 Get Plugin List

```bash
curl -X GET http://localhost:10768/api/plugins
```

#### 2.1.3 Generate License Code

```bash
curl -X POST http://localhost:10768/license-code/generate \
  -H "Content-Type: application/json" \
  -d '{"licenseName": "BlueSky Technology", "assigneeName": "Zhang San", "expiryDate": "2025-12-31"}'
```

#### 2.1.4 Download ja-netfilter Tool

```bash
curl -O http://localhost:10768/ja-netfilter
```

### 2.2 Using XML-RPC Client to Call RPC Interfaces

The following is an example of using Python's xmlrpc.client module to call the ping.action interface:

```python
import xmlrpc.client

# Create XML-RPC client
client = xmlrpc.client.ServerProxy('http://localhost:10768/rpc/')

# Call ping.action interface
response = client.ping(
    hostName='my-computer',
    machineId='abc123',
    salt='xyz789'
)

print(response)
```

## 3. Error Handling

All API interfaces follow a unified error handling mechanism. When a request fails, the corresponding error code and error message will be returned:

### 3.1 REST API Error Response

```json
{
  "code": "ERROR_CODE",
  "message": "Error message description",
  "timestamp": "2023-10-01T12:00:00Z"
}
```

### 3.2 XML-RPC Error Response

```xml
<?xml version="1.0" encoding="UTF-8"?>
<methodResponse>
    <params>
        <param>
            <value>
                <struct>
                    <member>
                        <name>action</name>
                        <value><string>ERROR</string></value>
                    </member>
                    <member>
                        <name>confirmationStamp</name>
                        <value><string>20231001T120000Z</string></value>
                    </member>
                    <member>
                        <name>leaseSignature</name>
                        <value><string></string></value>
                    </member>
                    <member>
                        <name>message</name>
                        <value><string>Error message description</string></value>
                    </member>
                    <member>
                        <name>responseCode</name>
                        <value><string>ERROR</string></value>
                    </member>
                    <member>
                        <name>salt</name>
                        <value><string>original_salt_here</string></value>
                    </member>
                </struct>
            </value>
        </param>
    </params>
</methodResponse>
```

## 4. Best Practices

1. **API Version Control**: It is recommended to use API version control in production environments to allow API updates without affecting existing clients.

2. **Request Frequency Limitation**: Avoid frequent API calls, especially for interfaces that may contain a large amount of data, such as `/api/plugins` and `/api/products`.

3. **Parameter Validation**: Before calling API interfaces, ensure all required parameters are provided and in the correct format.

4. **Error Handling**: Implement appropriate error handling logic in client code to handle API request failures gracefully.

5. **HTTPS Usage**: In production environments, it is recommended to use the HTTPS protocol to encrypt API communications and improve security.

## 5. Summary

This document provides detailed descriptions of all API interfaces of the JetBrains License Server Help project, including REST API and XML-RPC interfaces. By using these API interfaces, you can:

- Get lists of JetBrains products and plugins
- Generate license codes
- Download the ja-netfilter proxy tool
- Manage JRebel-related functions
- Communicate with the license server (automatically called by JetBrains IDEs)

If you encounter any problems when using API interfaces, please refer to the [FAQ](./faq.md) document or submit an Issue for assistance.
