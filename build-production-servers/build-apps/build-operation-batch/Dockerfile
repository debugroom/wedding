# Dockerfile for wedding operation batch

FROM       docker.io/debugroom/wedding:centos7
MAINTAINER debugroom

RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel \
       wget tar iproute

RUN wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
RUN sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
RUN yum install -y apache-maven
ENV JAVA_HOME /etc/alternatives/jre
RUN git clone -b feature/framework-spring https://github.com/debugroom/framework.git /var/local/framework
RUN mvn install -f /var/local/framework/pom.xml

RUN git clone -b develop https://github.com/debugroom/wedding.git /var/local/wedding

ENV DBSERVER_APP_USERNAME=app
ENV DBSERVER_APP_PASSWORD=app
ARG DBSERVER_PORT_5432_TCP_ADDR
ARG DBSERVER_PORT_5432_TCP_PORT
ARG DBSERVER_PORT_9042_TCP_ADDR
ARG DBSERVER_PORT_9042_TCP_PORT
ENV DBSERVER_PORT_5432_TCP_ADDR ${DBSERVER_PORT_5432_TCP_ADDR:-localhost}
ENV DBSERVER_PORT_5432_TCP_PORT ${DBSERVER_PORT_5432_TCP_PORT:-localhost}
ENV DBSERVER_PORT_9042_TCP_ADDR ${DBSERVER_PORT_9042_TCP_ADDR:-localhost}
ENV DBSERVER_PORT_9042_TCP_PORT ${DBSERVER_PORT_9042_TCP_PORT:-localhost}

RUN mvn install -Dmaven.test.skip=true -f /var/local/wedding/wedding-microservice/wedding-infra-cassandra/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-infra-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-boot-parent/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-domain-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-batch-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-web-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-operation/pom.xml

RUN yum install -y cronie-noanacron
RUN mkdir -p /usr/local/app/operation
RUN mkdir -p /logs
ADD scripts/backup.sh /usr/local/app/operation/
RUN chmod 755 /usr/local/app/operation/backup.sh
RUN echo "0 * * * * /usr/local/app/operation/backup.sh" > /var/spool/cron/root

ARG CLOUD_AWS_CREDENTIALS_ACCESSKEY 
ARG CLOUD_AWS_CREDENTIALS_SECRETKEY
ARG CLOUD_AWS_REGION_STATIC
ENV CLOUD_AWS_CREDENTIALS_ACCESSKEY ${CLOUD_AWS_CREDENTIALS_ACCESSKEY:-XXX}
ENV CLOUD_AWS_CREDENTIALS_SECRETKEY ${CLOUD_AWS_CREDENTIALS_SECRETKEY:-XXX}
ENV CLOUD_AWS_REGION_STATIC ${CLOUD_AWS_REGION_STATIC:-XXX}
RUN echo "DBSERVER_APP_USERNAME="$DBSERVER_APP_USERNAME >> ~/.bashrc
RUN echo "DBSERVER_APP_PASSWORD="$DBSERVER_APP_PASSWORD >> ~/.bashrc
RUN echo "DBSERVER_PORT_5432_TCP_ADDR="$DBSERVER_PORT_5432_TCP_ADDR >> ~/.bashrc
RUN echo "DBSERVER_PORT_5432_TCP_PORT="$DBSERVER_PORT_5432_TCP_PORT >> ~/.bashrc
RUN echo "DBSERVER_PORT_9042_TCP_ADDR="$DBSERVER_PORT_9042_TCP_ADDR >> ~/.bashrc
RUN echo "DBSERVER_PORT_9042_TCP_PORT="$DBSERVER_PORT_9042_TCP_PORT >> ~/.bashrc
RUN echo "CLOUD_AWS_CREDENTIALS_ACCESSKEY="$CLOUD_AWS_CREDENTIALS_ACCESSKEY >> ~/.bashrc
RUN echo "CLOUD_AWS_CREDENTIALS_SECRETKEY="$CLOUD_AWS_CREDENTIALS_SECRETKEY >> ~/.bashrc
RUN echo "CLOUD_AWS_REGION_STATIC="$CLOUD_AWS_REGION_STATIC >> ~/.bashrc
RUN echo "PATH="$PATH >> ~/.bashrc

CMD crond -n
