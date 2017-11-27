# Dockerfile for cassandra

FROM            debugroom/wedding:centos7
MAINTAINER      debugroom

RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel \
       wget tar iproute

ENV JAVA_HOME /etc/alternatives/java_sdk
ADD datastax.repo /etc/yum.repos.d/
RUN yum -y install dsc30
RUN yum -y install cassandra30-tools
RUN sed -i s/\#-Xms4G/-Xms1G/g /etc/cassandra/conf/jvm.options
RUN sed -i s/\#-Xmx4G/-Xmx2G/g /etc/cassandra/conf/jvm.options
# RUN systemctl enable cassandra

EXPOSE 7199 7000 7001 9160 9042 22 8012 61621
