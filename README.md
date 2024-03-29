# 简介

此 demo 主要为了演示 NutzBoot 如何通过 JustAuth 快速集成第三方平台的登录。

MainLauncher是入口,启动即可

## 环境要求

* 必须JDK8+
* eclipse或idea等IDE开发工具,可选

## 配置信息位置

第三方授权配置信息,jetty端口等配置信息,均位于src/main/resources/application.properties

## 命令下启动

仅供测试用,使用mvn命令即可

```
// for windows
set MAVEN_OPTS="-Dfile.encoding=UTF-8"
mvn compile nutzboot:run

// for *uix
export MAVEN_OPTS="-Dfile.encoding=UTF-8"
mvn compile nutzboot:run
```

## 项目打包

```
mvn clean package nutzboot:shade
```

请注意,当前需要package + nutzboot:shade, 单独执行package或者nutzboot:shade是不行的


### 跳过测试
```
mvn clean package nutzboot:shade -Dmaven.test.skip=true
```

## 相关资源

* Nutzboot开源地址: https://gitee.com/nutz/nutzboot  (给颗星啊啊啊啊啊)
* 论坛: https://nutz.cn
* 官网: https://nutz.io
* 一键生成NB的项目: https://get.nutz.io
* JustAuth: https://gitee.com/yadong.zhang/JustAuth
