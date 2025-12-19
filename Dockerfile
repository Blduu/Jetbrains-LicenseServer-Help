# 构建阶段：使用 Maven 3 + OpenJDK 17 编译项目（匹配 pom 中 Java 17 版本）
FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY . .
# 强制更新依赖并打包（解决依赖下载缓存问题）
RUN mvn clean package -U

# 运行阶段：使用轻量的 Temurin 17 JRE（减少镜像体积）
FROM eclipse-temurin:17-jre
WORKDIR /app
# 从构建阶段复制打包好的 JAR 包
COPY --from=build /app/target/Jetbrains-LicenseServer-Help.jar Jetbrains-LicenseServer-Help.jar
# 时区配置（保持原有逻辑）
ENV TZ=Asia/Shanghai
# 业务环境变量（保持原有配置）
ENV XBASE64_DOMAIN=mihuyo.cc
# 时区同步
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 暴露端口（匹配 docker-compose 中的端口映射）
EXPOSE 10768
# 启动命令（保持原有逻辑）
ENTRYPOINT ["java", "-jar", "Jetbrains-LicenseServer-Help.jar"]
