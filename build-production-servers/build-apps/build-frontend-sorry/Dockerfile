# Dockerfile for wedding frontend sorry using embedded tomcat server

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

RUN git clone -b develop https://github.com/debugroom/wedding.git /var/local/wedding

RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-frontend/wedding-web-frontend-sorry/pom.xml

RUN cp /etc/localtime /etc/localtime.org
RUN ln -sf  /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

EXPOSE 8080

CMD java -jar /var/local/wedding/wedding-microservice/wedding-frontend/wedding-web-frontend-sorry/target/wedding-web-frontend-sorry-1.2.0.RELEASE.jar
