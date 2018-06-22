#!/bin/bash

source ~/.bashrc

DATETIME=`date +"%Y-%m-%d-%I-%M-%S"`

APPLOG_FILENAME=/logs/app-$DATETIME.log
ERRORLOG_FILENAME=/logs/app-error-$DATETIME.log

touch $APPLOG_FILENAME
touch $ERRORLOG_FILENAME

export DBSERVER_APP_USERNAME
export DBSERVER_APP_PASSWORD
export DBSERVER_PORT_5432_TCP_ADDR
export DBSERVER_PORT_5432_TCP_PORT
export DBSERVER_PORT_9042_TCP_ADDR
export DBSERVER_PORT_9042_TCP_PORT
export CLOUD_AWS_CREDENTIALS_ACCESSKEY
export CLOUD_AWS_CREDENTIALS_SECRETKEY
export CLOUD_AWS_REGION_STATIC

java -jar -Dspring.profiles.active=production,cassandra,jpa,cluster,message-cluster /var/local/wedding/wedding-microservice/wedding-operation/wedding-batch-operation/target/wedding-batch-operation-1.2.0.RELEASE.jar 1>> $APPLOG_FILENAME 2>> $ERRORLOG_FILENAME
