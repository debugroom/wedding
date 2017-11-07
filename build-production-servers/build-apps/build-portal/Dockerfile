# Dockerfile for wedding portal service using embedded tomcat server

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
RUN mvn install -f /var/local/framework/framework-isntall/pom.xml
RUN git clone -b develop https://github.com/debugroom/wedding.git /var/local/wedding
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-portal/wedding-web-portal/pom.xml

ENV DBSERVER_APP_USERNAME=app
ENV DBSERVER_APP_PASSWORD=app

EXPOSE 8080

CMD java -jar -Dspring.profiles.active=production,jpa /var/local/wedding/wedding-micorserve/wedding-portal/target/wedding-portal-1.0-SNAPSHOT.jar
