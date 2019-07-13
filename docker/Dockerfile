FROM jrottenberg/ffmpeg:3.1-centos
MAINTAINER wanghongen <329016414@qq.com>

RUN yum install -y \
    java-1.8.0-openjdk \
    java-1.8.0-openjdk-devel

#ENV JAVA_HOME=/etc/alternatives/jre
ENV JAVA_HOME /usr/lib/jvm/java-1.8.0-openjdk/ \
    FFMPEG_VERSION=3.0.2

RUN mkdir /srv/silk2wav

WORKDIR /srv/silk2wav

ADD audit-converter-service-0.0.1-SNAPSHOT.jar /srv/silk2wav/silk2wav.jar
ADD silk-v3-decoder.tar.gz /srv/silk2wav/
#ADD silk-v3-decoder /srv/silk2wav/silk-v3-decoder

ENTRYPOINT ["java", "-Xms1024m", "-Xmx1024m",  "-jar", "silk2wav.jar"]

EXPOSE 9100