package com.bluesky.jetbrainslicenseserverhelp.controller;

import com.bluesky.jetbrainslicenseserverhelp.context.AgentContextHolder;
import com.bluesky.jetbrainslicenseserverhelp.context.ProductsContextHolder;
import com.bluesky.jetbrainslicenseserverhelp.context.plugin.PluginConfig;
import com.bluesky.jetbrainslicenseserverhelp.context.plugin.model.PluginCache;
import com.bluesky.jetbrainslicenseserverhelp.context.plugin.model.PluginUpdateTimeCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据接口控制器
 *
 * <p>此控制器提供前端所需的基础数据API，包括产品列表、插件列表等。
 * 这些数据用于前端界面的动态展示和用户选择。
 *
 * <p>主要功能：
 * <ul>
 *   <li>提供JetBrains产品列表数据</li>
 *   <li>提供付费插件列表数据</li>
 *   <li>支持前端动态加载数据</li>
 *   <li>返回JSON格式的结构化数据</li>
 * </ul>
 *
 * @author BlueSky
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j(topic = "数据接口")
@RestController
@RequestMapping("/api")
public class DataController {

    /**
     * 获取JetBrains产品列表
     *
     * <p>返回所有支持的JetBrains IDE产品信息，包括产品名称、产品代码和描述等。
     * 前端可以使用这些数据构建产品选择下拉框。
     *
     * <p>返回的产品信息包含：
     * <ul>
     *   <li>name - 产品显示名称</li>
     *   <li>productCode - 产品代码（用于许可证生成）</li>
     *   <li>iconClass - 图标CSS类名</li>
     * </ul>
     *
     * <p>请求示例：
     * <pre>
     * GET /api/products
     * </pre>
     *
     * <p>响应示例：
     * <pre>
     * [
     *   {
     *     "name": "IntelliJ IDEA Ultimate",
     *     "productCode": "II",
     *     "iconClass": "icon-idea"
     *   },
     *   {
     *     "name": "PhpStorm",
     *     "productCode": "PS", 
     *     "iconClass": "icon-phpstorm"
     *   }
     * ]
     * </pre>
     *
     * @return JetBrains产品信息列表
     */
    @GetMapping("/products")
    public List<ProductsContextHolder.ProductCache> getProducts() {
        log.debug("获取产品列表，产品数量: {}", ProductsContextHolder.productCacheList().size());
        return ProductsContextHolder.productCacheList();
    }

    /**
     * 获取JetBrains付费插件列表
     *
     * <p>返回所有支持的JetBrains付费插件信息，包括插件名称、ID和产品代码等。
     * 前端可以使用这些数据构建插件选择下拉框。
     *
     * <p>返回的插件信息包含：
     * <ul>
     *   <li>id - 插件唯一标识符</li>
     *   <li>name - 插件显示名称</li>
     *   <li>productCode - 产品代码（用于许可证生成）</li>
     *   <li>pricingModel - 定价模式</li>
     *   <li>icon - 插件图标URL</li>
     * </ul>
     *
     * <p>请求示例：
     * <pre>
     * GET /api/plugins
     * </pre>
     *
     * <p>响应示例：
     * <pre>
     * [
     *   {
     *     "id": 7973,
     *     "name": "SonarLint",
     *     "productCode": "SONAR_LINT",
     *     "pricingModel": "PAID",
     *     "icon": "https://plugins.jetbrains.com/files/7973/icon.svg"
     *   }
     * ]
     * </pre>
     *
     * @return JetBrains付费插件信息列表
     */
    @GetMapping("/plugins")
    public List<PluginCache> getPlugins() {
        log.debug("获取插件列表，插件数量: {}", PluginConfig.pluginCacheList.size());
        return PluginConfig.pluginCacheList;
    }

    /**
     * 获取插件列表最后更新时间
     *
     * <p>返回插件信息缓存的最后一次更新时间，
     * 用于前端展示数据的新鲜度。
     *
     * <p>请求示例：
     * <pre>
     * GET /api/plugins/lastUpdateTime
     * </pre>
     *
     * <p>响应示例：
     * <pre>
     * {
     *   "lastUpdateTime": "2024-01-15 12:00:00"
     * }
     * </pre>
     *
     * @return 包含更新时间的JSON对象
     */
    @GetMapping("/plugins/lastUpdateTime")
    public List<PluginUpdateTimeCache> getPluginLastUpdateTime() {
        return PluginConfig.pluginUpdateTimeCacheList;
    }


    /**
     * 查看 power.conf 配置文件内容接口
     *
     * <p>此接口返回 ja-netfilter 的 power.conf 配置文件内容。
     * power.conf 是 ja-netfilter 的核心配置文件，包含：
     * <ul>
     *   <li>激活码规则配置</li>
     *   <li>许可证服务器规则配置</li>
     * </ul>
     *
     * <p>配置文件基于证书信息动态生成，用于 ja-netfilter 代理的验证规则。
     *
     * @return power.conf 文件内容
     */
    @GetMapping("/power-conf")
    public String getPowerConf() {
        log.info("接收到 power.conf 查看请求");
        String content = AgentContextHolder.getPowerConfContent();
        log.info("power.conf 查看成功");
        return content;
    }

}
