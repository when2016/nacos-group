

##参考文档
https://www.jianshu.com/p/a723009526b7

##github代码
https://github.com/lanshiqin/cloud-project


2.修改操作系统的host文件
  添加如下配置：
#配置服务注册中心
127.0.0.1 service-registry1
127.0.0.1 service-registry2
127.0.0.1 service-registry3

3.打包
gradle build

4.运行
java -jar service-registry-0.0.1-SNAPSHOT.jar --spring.profiles.active=registry1
java -jar service-registry-0.0.1-SNAPSHOT.jar --spring.profiles.active=registry2
java -jar service-registry-0.0.1-SNAPSHOT.jar --spring.profiles.active=registry3

打开浏览器
输入 http://localhost:9001/
或者 http://localhost:9002/
或者 http://localhost:9003/
都可以看到如下图所示信息
