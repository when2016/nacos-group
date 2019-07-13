##七牛开发文档说明https://developer.qiniu.com/dora/manual/3687/ufop-directions-for-use

##自定义处理数据
http://localhost:9100/handler?cmd=audioconvert&url=http://static.cdn.longmaosoft.com/103501/887284/AtIVEuM9Ap8X2W7r8rsrEVbXf4Ui6n6I.silk
##数据访问方法
http://static.cdn.longmaosoft.com/103501/887284/AtIVEuM9Ap8X2W7r8rsrEVbXf4Ui6n6I.silk?audioconvert


##1.镜像文件Dockerfile在doc/docker/Dockerfile
##2.下载转换脚本 https://github.com/kn007/silk-v3-decoder
####注意，特别注意::客户要求是1600bit,一定要修silk-v3-docoder目录下的converter.sh第70行的内容
##3.详细流程，请参考https://www.jianshu.com/p/91d0df420baf
##4.QQ服务器docker路径/home/work/silk2wav
##5.ffmpeg参数说明（转载）https://www.cnblogs.com/chen1987lei/archive/2010/12/03/1895242.html

##4.七牛上已发布版本为v7
v7===使用1024m
v5===600m


docker build -t audioconverter:v7 .
./qdoractl release --config .
./qdoractl release doraufoptest -d


##构建镜像
docker build -t audioconverter:v1 .
##测试运行方法
docker run -d -i -t -p9100:9100 --name silk2wav audioconverter:v1 /bin/bash
##访问转化链接
http://188.131.169.24:9100/handler?cmd=audioconvert&url=http://static.cdn.longmaosoft.com/103501/887284/AtIVEuM9Ap8X2W7r8rsrEVbXf4Ui6n6I.silk
##将镜像上传到阿里云仓库
[root@VM_0_6_centos qiniu]# sudo docker login --username=wanghongen@sina.cn registry.cn-qingdao.aliyuncs.com
Password:
Login Succeeded
[root@VM_0_6_centos qiniu]# docker images
REPOSITORY                                          TAG                 IMAGE ID            CREATED             SIZE
audioconverter                                      v1                  6c46836f209f        18 minutes ago      635 MB
docker.io/jrottenberg/ffmpeg                        3.1-centos          b566f026d54c        2 days ago          280 MB
docker.io/mariadb                                   latest              f1e4084965e5        12 days ago         356 MB
docker.io/redis                                     latest              3c41ce05add9        3 weeks ago         95 MB
docker.io/nginx                                     latest              719cd2e3ed04        3 weeks ago         109 MB
[root@VM_0_6_centos qiniu]# docker tag 6c46836f209f registry.cn-qingdao.aliyuncs.com/wanghongen/silk2wav:v1
[root@VM_0_6_centos qiniu]# docker push registry.cn-qingdao.aliyuncs.com/wanghongen/silk2wav:v1

