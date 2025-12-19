# API接口文档

本文档详细介绍了 JetBrains License Server Help 项目提供的所有 API 接口，包括 REST API 和 XML-RPC 接口。每个接口都包含请求方法、路径、参数、响应示例和使用说明等信息。

## 1. REST API 接口

### 1.1 数据接口 (DataController)

#### 1.1.1 获取JetBrains产品列表

**接口说明**：获取所有支持的JetBrains产品信息列表，用于前端界面的动态展示和用户选择。

**请求方法**：GET
**请求路径**：`/api/products`
**请求参数**：无

**响应示例**：

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

**返回参数说明**：

| 参数名       | 类型   | 描述                           |
|--------------|--------|--------------------------------|
| name         | String | 产品显示名称                   |
| productCode  | String | 产品代码（用于许可证生成）     |
| iconClass    | String | 图标CSS类名                    |

#### 1.1.2 获取JetBrains付费插件列表

**接口说明**：获取所有支持的JetBrains付费插件信息列表，用于前端界面的动态展示和用户选择。

**请求方法**：GET
**请求路径**：`/api/plugins`
**请求参数**：无

**响应示例**：

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

**返回参数说明**：

| 参数名         | 类型   | 描述                           |
|----------------|--------|--------------------------------|
| id             | Long   | 插件唯一标识符                 |
| name           | String | 插件显示名称                   |
| productCode    | String | 产品代码（用于许可证生成）     |
| pricingModel   | String | 定价模式（PAID表示付费）       |
| icon           | String | 插件图标URL                    |

### 1.2 许可证代码生成接口 (LicenseCodeController)

#### 1.2.1 生成许可证代码

**接口说明**：生成JetBrains产品的激活码（许可证代码），支持个人或企业版许可证。

**请求方法**：POST
**请求路径**：`/license-code/generate`
**请求参数**：

| 参数名         | 类型   | 必填 | 描述                           |
|----------------|--------|------|--------------------------------|
| licenseName    | String | 是   | 许可证名称（公司或组织名称）   |
| assigneeName   | String | 是   | 被授权人名称（使用者名称）     |
| expiryDate     | String | 是   | 过期日期（格式：yyyy-MM-dd）   |
| productCode    | String | 否   | 产品代码（多个代码用逗号分隔，为空时包含所有产品） |

**请求示例**：

```json
{
  "licenseName": "QiuMo Technology",
  "assigneeName": "张三",
  "expiryDate": "2025-12-31",
  "productCode": "II,PS,WS,RM,PCC,PC,CLN"
}
```

**响应示例**：

```json
{
  "licenseCode": "QMT-XXXXXXXX-XXXXXXXX-XXXXXXXX-XXXXXXXX",
  "message": "许可证生成成功"
}
```

**返回参数说明**：

| 参数名         | 类型   | 描述                           |
|----------------|--------|--------------------------------|
| licenseCode    | String | 生成的许可证代码               |
| message        | String | 操作结果消息                   |

### 1.3 文件下载接口 (ZipController)

#### 1.3.1 下载ja-netfilter代理工具

**接口说明**：提供预配置的ja-netfilter工具包下载服务，用于在JetBrains IDE中使用。

**请求方法**：GET
**请求路径**：`/ja-netfilter`
**请求参数**：无

**响应**：包含ja-netfilter工具的ZIP文件附件

**返回内容**：
- ja-netfilter.jar - 核心程序
- plugins/ - 各种功能插件
- config/ - 配置文件（已预配置JetBrains产品相关参数）
- README.md - 使用说明

### 1.4 JRebel控制器接口 (JRebelController)

#### 1.4.1 获取GUID

**接口说明**：生成并返回一个唯一标识符（GUID），用于JRebel相关功能。

**请求方法**：GET
**请求路径**：`/guid`
**请求参数**：无

**响应示例**：

```
550e8400-e29b-41d4-a716-446655440000
```

#### 1.4.2 JRebel租约管理

**接口说明**：处理JRebel相关的租约请求。

**请求方法**：POST
**请求路径**：`/jrebel/leases`
**请求参数**：由JRebel客户端自动生成
**响应**：JRebel租约响应

#### 1.4.3 JRebel租约管理1

**接口说明**：处理JRebel相关的租约请求（版本1）。

**请求方法**：POST
**请求路径**：`/jrebel/leases/1`
**请求参数**：由JRebel客户端自动生成
**响应**：JRebel租约响应

#### 1.4.4 Agent租约管理

**接口说明**：处理Agent相关的租约请求。

**请求方法**：POST
**请求路径**：`/agent/leases`
**请求参数**：由Agent客户端自动生成
**响应**：Agent租约响应

#### 1.4.5 Agent租约管理1

**接口说明**：处理Agent相关的租约请求（版本1）。

**请求方法**：POST
**请求路径**：`/agent/leases/1`
**请求参数**：由Agent客户端自动生成
**响应**：Agent租约响应

#### 1.4.6 JRebel连接验证

**接口说明**：验证JRebel客户端与服务器的连接。

**请求方法**：GET
**请求路径**：`/jrebel/validate-connection`
**请求参数**：无
**响应**：JRebel连接验证响应

## 2. XML-RPC 接口 (LicenseServerController)

### 2.1 获取许可证凭证

**接口说明**：JetBrains产品首次连接许可证服务器时调用的接口，用于获取许可证凭证。

**请求方法**：POST
**请求路径**：`/rpc/obtainTicket.action`
**请求参数**：

| 参数名         | 类型   | 必填 | 描述                           |
|----------------|--------|------|--------------------------------|
| hostName       | String | 是   | 客户端主机名，用于标识许可证使用者 |
| machineId      | String | 是   | 客户端机器唯一标识符，用于硬件绑定 |
| salt           | String | 是   | 加密盐值，用于增强通信安全性     |

**响应示例**：

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

### 2.2 许可证服务器连通性检查

**接口说明**：JetBrains产品定期调用此接口来检查许可证服务器的可用性。

**请求方法**：POST
**请求路径**：`/rpc/ping.action`
**请求参数**：

| 参数名         | 类型   | 必填 | 描述                           |
|----------------|--------|------|--------------------------------|
| hostName       | String | 是   | 客户端主机名，用于标识许可证使用者 |
| machineId      | String | 是   | 客户端机器唯一标识符，用于硬件绑定 |
| salt           | String | 是   | 加密盐值，用于增强通信安全性     |

**响应示例**：

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

### 2.3 延长许可证有效期

**接口说明**：JetBrains产品定期调用此接口来延长许可证的有效期。

**请求方法**：POST
**请求路径**：`/rpc/prolongTicket.action`
**请求参数**：

| 参数名         | 类型   | 必填 | 描述                           |
|----------------|--------|------|--------------------------------|
| hostName       | String | 是   | 客户端主机名，用于标识许可证使用者 |
| machineId      | String | 是   | 客户端机器唯一标识符，用于硬件绑定 |
| salt           | String | 是   | 加密盐值，用于增强通信安全性     |

**响应示例**：

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

### 2.4 释放许可证凭证

**接口说明**：当JetBrains产品关闭或用户主动退出时，会调用此接口来释放占用的许可证凭证。

**请求方法**：POST
**请求路径**：`/rpc/releaseTicket.action`
**请求参数**：

| 参数名         | 类型   | 必填 | 描述                           |
|----------------|--------|------|--------------------------------|
| hostName       | String | 是   | 客户端主机名，用于标识许可证使用者 |
| machineId      | String | 是   | 客户端机器唯一标识符，用于硬件绑定 |
| salt           | String | 是   | 加密盐值，用于增强通信安全性     |

**响应示例**：

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

## 2. API 使用示例

### 2.1 使用 cURL 调用 REST API

#### 2.1.1 获取产品列表

```bash
curl -X GET http://localhost:10768/api/products
```

#### 2.1.2 获取插件列表

```bash
curl -X GET http://localhost:10768/api/plugins
```

#### 2.1.3 生成许可证代码

```bash
curl -X POST http://localhost:10768/license-code/generate \
  -H "Content-Type: application/json" \
  -d '{"licenseName": "QiuMo Technology", "assigneeName": "张三", "expiryDate": "2025-12-31"}'
```

#### 2.1.4 下载ja-netfilter工具

```bash
curl -O http://localhost:10768/ja-netfilter
```

### 2.2 使用 XML-RPC 客户端调用 RPC 接口

以下是使用 Python 的 xmlrpc.client 模块调用 ping.action 接口的示例：

```python
import xmlrpc.client

# 创建XML-RPC客户端
client = xmlrpc.client.ServerProxy('http://localhost:10768/rpc/')

# 调用ping.action接口
response = client.ping(
    hostName='my-computer',
    machineId='abc123',
    salt='xyz789'
)

print(response)
```

## 3. 错误处理

所有 API 接口都遵循统一的错误处理机制，当请求失败时，会返回相应的错误码和错误信息：

### 3.1 REST API 错误响应

```json
{
  "code": "ERROR_CODE",
  "message": "错误信息描述",
  "timestamp": "2023-10-01T12:00:00Z"
}
```

### 3.2 XML-RPC 错误响应

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
                        <value><string>错误信息描述</string></value>
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

## 4. 最佳实践

1. **API 版本控制**：建议在生产环境中使用 API 版本控制，以便在不影响现有客户端的情况下进行 API 更新。

2. **请求频率限制**：避免频繁调用 API 接口，特别是 `/api/plugins` 和 `/api/products` 等可能包含大量数据的接口。

3. **参数验证**：在调用 API 接口之前，确保所有必填参数都已提供且格式正确。

4. **错误处理**：在客户端代码中实现适当的错误处理逻辑，以便在 API 请求失败时能够优雅地处理。

5. **HTTPS 使用**：在生产环境中，建议使用 HTTPS 协议来加密 API 通信，提高安全性。

## 5. 总结

本文档提供了 JetBrains License Server Help 项目所有 API 接口的详细说明，包括 REST API 和 XML-RPC 接口。通过使用这些 API 接口，您可以：

- 获取 JetBrains 产品和插件列表
- 生成许可证代码
- 下载 ja-netfilter 代理工具
- 管理 JRebel 相关功能
- 与许可证服务器进行通信（JetBrains IDE 自动调用）

如果您在使用 API 接口时遇到任何问题，请参考[常见问题解答](./faq.md)文档或提交 Issue 寻求帮助。
