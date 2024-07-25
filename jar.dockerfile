#基础镜像
FROM cr-demo-cn-beijing.cr.volces.com/tools/openjdk:8-jdk-alpine
#将编译构建好的 jar 包，拷贝到镜像中，默认构建好的 jar 包在 target 目录下
COPY target/application.jar app.jar
#对应用实际监听的端口进行暴露，本文为 8080 端口
EXPOSE 8000
#配置镜像的启动命令
ENTRYPOINT ["java","-jar","/app.jar"]