# Wedding App

## Deploy

### 1. DB Server on CentOS7 with docker

アプリケーションのデータベースとして、PostgreSQLとApache Cassandraを環境構築する。なお、Cassandraはクラスタ構成するため、Dockerを利用し、マルチコンテナ構成でデータベースを構築する。

#### 1-1. Install Docker in host machine

ホストマシンとしてCentOS7を用意し、Dockerをインストールし、加えてコンテナ内で動かすCentOS7のイメージを取得する。[Dockerのインストール](http://debugroom.github.io/doc/memo/work/docker/article/install.html) を参考にDockerをインストールすること。なお、当環境ではAWS上のEC2インスタンスで実行した例。

1. CentOS7上で、パッケージとパッケージキャッシュを更新する。
2. Dockerをインストール
3. dockerグループの作成とユーザの追加
4. Docker Serviceの起動
5. 一度ログアウト(以降sudoなしでdockerコマンドを実行するため)

```bash:Dockerのインストール

[1の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo yum update -y
//omit

[2の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo yum install -y docker
//omit

[3の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo groupadd docker
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo gpasswd -a $USER docker
//omit

[4の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo systemctl enable docker.service
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo systemctl start docker.service

[5の手順]
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ exit
//omit
```

#### 1-2. Install PostgreSQL on CentOS7 in docker container

PostgreSQLをコンテナ内のCentOS7へインストールする。構築した環境はイメージとしてDocker Hubに保存しておく。

##### 1-2-1. CentOS7コンテナイメージの作成

CentOS7コンテナイメージを取得し、コンテナを実行して、Gitのインストールを行い、環境イメージを保存する。

1. CentOSイメージの取得
2. CentOS7コンテナをLaunch
3. Launchしたコンテナにgitをインストールする。yumコマンドでは古いバージョン(1.8.x)がインストールされるため、ソースコードから最新版をインストールする。最新版は[こちら](https://www.kernel.org/pub/software/scm/git/)から確認すること。また、事前にwget make gcc perl-ExtUtils-MakeMaker curl-devel expat-devel gettext-devel openssl-devel zlib-devel autoconfのインストールが必要なため、yumコマンドにてインストールしておく。
4. インストール済みのコンテナからイメージを作成し、Docker Hubにプッシュする。

```bash:CentOS7コンテナの構築
[1の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker pull centos
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
docker.io/centos    latest              196e0ce0c9fb        7 weeks ago         196.6 MB

[2の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker run -it --name centos7 centos:latest /bin/bash

[3の手順]
[root@08b670819a55 /]# yum -y install wget make gcc perl-ExtUtils-MakeMaker curl-devel expat-devel gettext-devel openssl-devel zlib-devel autoconf
//omit
[root@08b670819a55 /]# cd /var/local/
[root@08b670819a55 local]# wget https://www.kernel.org/pub/software/scm/git/git-2.9.5.tar.gz
//omit
[root@08b670819a55 local]# tar xvzf git-2.9.5.tar.gz
//omit
[root@08b670819a55 local]# cd git-2.9.5
[root@08b670819a55 git-2.9.5]# make configure
//omit
[root@08b670819a55 git-2.9.5]# ./configure --prefix=/usr
[root@08b670819a55 git-2.9.5]# make install
//omit
[root@08b670819a55 git-2.9.5]# git --version
git version 2.9.5

[4の手順]

ctl+p+qでコンテナからエスケープする。

[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker ps -a
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
08b670819a55        centos:latest       "/bin/bash"         6 minutes ago       Up 36 minutes                           centos7

[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker commit centos7 debugroom/wedding:centos7
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker login
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username (debugroom): debugroom
Password:
Login Succeededdocker commit centos7 debugroom/wedding:centos7
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker push debugroom/wedding

```

##### 1-2-2. PostgreSQLのインストール・コンテナイメージ作成。

[2-1](https://github.com/debugroom/wedding/tree/develop#2-1-centos7コンテナイメージの作成)で作成したCentOS7コンテナイメージ内にPostgreSQLをインストールする。インストールはDockerfileを利用して行う。なお、Dockerfileはgit cloneして取得する。

1. Dockerfileの取得のために、[2-1](https://github.com/debugroom/wedding/tree/develop#2-1-centos7コンテナイメージの作成)と同様、ホストマシンのCentOS7にgitをインストールし、Dockerfileを含むプロジェクトをクローンする。
2. 取得したDockerfileをdocker buildコマンドから実行し、コンテナイメージを作成して、DockerHubにプッシュする。

```bash:ホストマシンへGitをインストール・プロジェクトのクローン

[1の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo yum -y install wget make gcc perl-ExtUtils-MakeMaker curl-devel expat-devel gettext-devel openssl-devel zlib-devel autoconf
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd /var/local/
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo wget https://www.kernel.org/pub/software/scm/git/git-2.9.5.tar.gz
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo tar xvzf git-2.9.5.tar.gz
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd git-2.9.5
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo make configure
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo ./configure --prefix=/usr
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo make install
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ git --version
git version 2.9.5
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd /var/local/
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo git clone -b  develop https://github.com/debugroom/wedding.git

[2の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd /var/local/wedding
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker build -t debugroom/wedding:postgres build-production-servers/build-postgresql/
// omit
server stopped
 ---> 2b26796c73a1
Removing intermediate container d8e9df315afe
Successfully built 2b26796c73a1
[centos@ip-XXXX-XXX-XXX-XXX wedding]$ docker images
REPOSITORY                    TAG                         IMAGE ID            CREATED             SIZE
debugroom/wedding             postgres                    2b26796c73a1        41 seconds ago      1.058 GB
[centos@ip-XXXX-XXX-XXX-XXX wedding]$ docker push debugroom/wedding:postgres
The push refers to a repository [docker.io/debugroom/wedding]
// omit
postgres: digest: sha256:91465546bc6c19cee7baca2aa6f096bcdc270bb91e0c7fd36e28521d564a9e5a size: 5336
```

なお、[指定したDockerfile](https://github.com/debugroom/wedding/blob/develop/build-production-servers/build-postgresql/Dockerfile)は以下である。Dockerfileの記述内容に関しての説明は[こちら](http://debugroom.github.io/doc/memo/work/docker/article/usage.html#postgresql-db)も参考のこと。

```
FROM            debugroom/wedding:centos7
MAINTAINER      debugroom
ENV             container docker
RUN             git clone -b develop https://github.com/debugroom/wedding.git /var/local/wedding
RUN             yum update -y && yum clean all
RUN             yum install -y postgresql postgresql-server && yum clean all
RUN             su - postgres -c "initdb --encoding=UTF8 --no-locale --pgdata=/var/lib/pgsql/data --auth=ident"
RUN             systemctl enable postgresql
RUN             cp -piv /var/lib/pgsql/data/postgresql.conf /var/lib/pgsql/data/postgresql.conf.bk
RUN             sed -e "s/#listen_addresses = 'localhost'/listen_addresses = '*'/g" /var/lib/pgsql/data/postgresql.conf > /var/lib/pgsql/data/postgresql.tmp
RUN             sed -e "s/log_filename = 'postgresql-%a.log'/log_filename = 'postgresql-%Y%m%d.log'/g" /var/lib/pgsql/data/postgresql.tmp > /var/lib/pgsql/data/postgresql.conf
RUN             sed -e "s/log_truncate_on_rotation = on/log_truncate_on_rotation = off/g" /var/lib/pgsql/data/postgresql.conf > /var/lib/pgsql/data/postgresql.tmp
RUN             sed -e "s/log_rotation_age = 1d/log_rotation_age = 7d/g" /var/lib/pgsql/data/postgresql.tmp > /var/lib/pgsql/data/postgresql.conf
RUN             sed -e "s/#log_line_prefix = ''/log_line_prefix = '%t [%p] '/g" /var/lib/pgsql/data/postgresql.conf > /var/lib/pgsql/data/postgresql.tmp
RUN             mv /var/lib/pgsql/data/postgresql.tmp /var/lib/pgsql/data/postgresql.conf
RUN             su - postgres -c "pg_ctl start -w;psql -c \"alter role postgres with password 'postgres';\";pg_ctl stop -m fast"
RUN             cp -piv /var/lib/pgsql/data/pg_hba.conf /var/lib/pgsql/data/pg_hba.conf.bk
RUN             sed -e "s/^host/#host/g" /var/lib/pgsql/data/pg_hba.conf > /var/lib/pgsql/data/pg_hba.tmp
RUN             sed -e "s/^local/#local/g" /var/lib/pgsql/data/pg_hba.tmp > /var/lib/pgsql/data/pg_hba.conf
RUN             echo "local    all             postgres                                peer" >> /var/lib/pgsql/data/pg_hba.conf
RUN             echo "local    all             all                                     md5" >> /var/lib/pgsql/data/pg_hba.conf
RUN             echo "host     all             all             0.0.0.0/0               md5" >> /var/lib/pgsql/data/pg_hba.conf
RUN             rm -f /var/lib/pgsql/data/pg_hba.tmp
EXPOSE          5432
RUN             chmod 755 /var/local/wedding/build-production-servers/build-postgresql/scripts/init_db.sh
RUN             /var/local/wedding/build-production-servers/build-postgresql/scripts/init_db.sh

```

<details><summary>構築したPostgreSQLの確認方法</summary>
docker runで作成したイメージからコンテナを実行し、データベースのデータを確認する場合は、docker run -it --name dbserver debugroom/wedding:postgres /bin/bashにて、コンテナを実行し、su - postgresにて、ユーザを切り替え、pg_ctl start -w;psql -d weddingにて、データベースに接続する。
</details>

<details><summary>APサーバからアクセス許可</summary>
APサーバからのアクセス許可を設定する場合は、AWSコンソールを用いて、セキュリティグループで、APサーバからのアクセスを許可する設定を行うこと。
</details>

<details><summary>APサーバからPostgreSQLへのコネクション</summary>
アプリケーションサービスを起動していくと、設定次第ではPostgreSQLコネクションが枯渇する(SpringBoot1アプリケーションあたりデフォルト10で、PostgreSQLのデフォルト閾値100を越えるとアプリが起動できないエラーが発生する。)
アプリケーションサービスx10コネクションxAPサーバ台数分PostgreSQLコネクションのプールが必要となるため、/var/lib/pgsql/data/postgresql.confのmax_connections及び、shared_buffersを変更する。なお、
　shared_bufferesの設定値の目安としては、コネクション数x2MBとなるよう、設定変更を行うこと。
</details>

<details><summary>CentOS7におけるPostgreSQLのサービス再起動</summary>
CentOS7では、設定ファイルを変更した後、以下のコマンドでサービスの再起動を行う。
```
systemctl restart postgresql
```
</details>

<details><summary>PostgreSQLにおけるコネクション状況の確認方法</summary>
設定の最大コネクション数は、以下で確認できる。
```
psql> SHOW max_connections;
```
PostgreSQLのコネクションの現在の占有数は、以下で確認できる。
```
psql> SELECT count(*) FROM pg_stat_activity;
```
占有しているクライアントを確認するには、以下のSQLを実行すると良い。
```
SELECT client_addr, query FROM pg_stat_activity;
```
</details>


#### 1-3. Install Apache Cassandra on CentOS7 in docker container

Cassandraをコンテナ内のCentOS7へインストールする。コンテナのサーバは3台のクラスタ構成とし、構築した環境はイメージとしてDocker Hubに保存しておく。[1-2-2](https://github.com/debugroom/wedding/tree/develop#1-2-2-postgresqlのインストールコンテナイメージ作成)と同様、git clonseしたプロジェクト内にあるDockerfileから構築を行う。[Cassadraクラスタサーバを構築](http://debugroom.github.io/doc/memo/work/docker/article/usage.html#cassandra)も合わせて参照のこと。

1. Git cloneしてDockerファイルからコンテナイメージを構築し、DockerHubへプッシュ。
2. 作成したコンテナイメージをcassandra-server1という名前で実行し、cassandraの設定ファイルにSeedサーバ、listen_address、rpc_address、auto_bootstrapを変更(Seed Serverのみauto_bootstrapにfalseを設定する。)、サービスの起動を行う。なお、IPアドレスはipコマンドで確認すること。
3. クラスタ化するサーバ分、2を繰り返す(Seedサーバは同じく、listern_addressとrpc_addressのみ変更)。

```bash:Dockerfileによるイメージの作成・DockerHubへのプッシュ
[1の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd /var/local/
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo git clone -b  develop https://github.com/debugroom/wedding.git
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd wedding
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker build -t debugroom/wedding:cassandra build-production-servers/build-cassandra/
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker push debugroom/wedding:cassandra
//omit

[2の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker run -d --privileged --name cassandra-server1 debugroom/wedding:cassandra /sbin/init
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker exec -ti cassandra-server1 /bin/bash
[root@dba8e9491f01 /]# vi /etc/cassandra/conf/cassandra.yaml

seed_provider:
    - class_name: org.apache.cassandra.locator.SimpleSeedProvider
      parameters:
          # seeds is actually a comma-delimited list of addresses.
          # Ex: "<ip1>,<ip2>,<ip3>"
          - seeds: "XXX.XXX.XXX.XXX"
listen_address: XXX.XXX.XXX.XXX
rpc_address: XXX.XXX.XXX.XXX
auto_bootstrap: false

[root@dba8e9491f01 /]# systemctl enable cassandra
[root@dba8e9491f01 /]# systemctl start cassandra

```

サービス起動後は、nodetoolでクラスタ環境を確認できる。

```
[root@dba8e9491f01 /]# nodetool status

Datacenter: datacenter1
=======================
Status=Up/Down
|/ State=Normal/Leaving/Joining/Moving
--  Address          Load       Tokens       Owns (effective)  Host ID                               Rack
UN  XXX.XXX.XXX.XXX  107.64 KB  256          100.0%            53a4ab03-8348-4d73-9ebb-2f75fa34f727  rack1
UN  XXX.XXX.XXX.YYY  132.42 KB  256          100.0%            088658da-a49a-4078-9400-9de35fc77d2d  rack1
UJ  XXX.XXX.XXX.ZZZ  69.46 KB   256          ?                 315309c4-8892-4dae-94eb-a79205900ef1  rack1

```

なお、[実行するDockerfile](https://github.com/debugroom/wedding/blob/develop/build-production-servers/build-cassandra/Dockerfile)は以下の通りである。

```bash:Cassandra構築のDockerfile

# Dockerfile for cassandra

FROM            debugroom/wedding:centos7
MAINTAINER      debugroom

RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel \
       wget tar

ENV JAVA_HOME /etc/alternatives/java_sdk
ADD datastax.repo /etc/yum.repos.d/
RUN yum -y install dsc30
RUN yum -y install cassandra30-tools
RUN sed -i s/\#-Xms4G/-Xms1G/g /etc/cassandra/conf/jvm.options
RUN sed -i s/\#-Xmx4G/-Xmx2G/g /etc/cassandra/conf/jvm.options
# RUN systemctl enable cassandra

EXPOSE 7199 7000 7001 9160 9042 22 8012 61621

```

### 2.AP Server on CentOS7 with docker

アプリケーションサーバでも、同様にDockerを利用し、マルチコンテナ構成でサービスベースでアプリを構築する。

#### 2-1. Install Docker and Git in Host Machine

DBサーバの構築と同様、ホストマシンとしてCentOS7を用意し、Dockerをインストールし、加えてコンテナ内で動かすCentOS7のイメージを取得する。[Dockerのインストール](http://debugroom.github.io/doc/memo/work/docker/article/install.html) を参考にDockerをインストールすること。なお、当環境ではAWS上のEC2インスタンスで実行した例。

1. CentOS7上で、パッケージとパッケージキャッシュを更新する。
2. Dockerをインストール
3. dockerグループの作成とユーザの追加
4. Docker Serviceの起動
5. 一度ログアウト(以降sudoなしでdockerコマンドを実行するため)
6. 再度ログイン後、Gitのインストール

```bash:Dockerのインストール

[1の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo yum update -y
//omit

[2の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo yum install -y docker
//omit

[3の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo groupadd docker
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo gpasswd -a $USER docker
//omit

[4の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo systemctl enable docker.service
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo systemctl start docker.service

[5の手順]
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ exit
//omit

[6の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo yum -y install wget make gcc perl-ExtUtils-MakeMaker curl-devel expat-devel gettext-devel openssl-devel zlib-devel autoconf
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd /var/local/
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo wget https://www.kernel.org/pub/software/scm/git/git-2.9.5.tar.gz
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo tar xvzf git-2.9.5.tar.gz
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd git-2.9.5
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo make configure
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo ./configure --prefix=/usr
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo make install
// omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ git --version
git version 2.9.5
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd /var/local/
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo git clone -b  develop https://github.com/debugroom/wedding.git

```

#### 2. App on CentOS7 in docker container

##### 2-1. PostgreSQL、Cassandraへ接続するバックエンドサービスアプリケーションの構築

1. GitクローンしたDockerfileを使用して、バックエンドのサービスアプリケーション用のコンテナイメージを作成する。なお、アプリケーションからPostgreSQLに接続するために、構築したDBサーバのIPアドレスとポートを環境変数としてコンテナに設定する(同一のホストマシンではLINKを使用すればよいが、アプリケーションを異なるホストマシン上に配置するため、直接環境変数でIPとポートを設定する。なお、Docker1.9以降では異なるホスト間でもIPを共有する方法がある模様だが、ここでは実施していない)
2. 作成したコンテナイメージを実行する。ポート8081がコンテナ内で実行されているAPサーバへ8080で繋がるようにオプション指定する。また、他のコンテナで実行されているバックエンドサービスを参照する場合など、LINKオプションを使って、対象とするコンテナの名称と環境変数を紐づけて実行する。
3. サービスの数だけ、1、２を繰り返す。なお、フォワーディングするポートだけ変更して実行すること。

```bash:

[1の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd /var/local/wedding
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker build --build-arg DBSERVER_PORT_5432_TCP_ADDR=YYY.YYY.YYY.YYY --build-arg DBSERVER_PORT_5432_TCP_PORT=ZZZ --build-arg DBSERVER_PORT_9042_TCP_ADDR=YYY.YYY.YYY.YYY --build-arg DBSERVER_PORT_9042_TCP_PORT=ZZZ -t debugroom/wedding:portal build-production-servers/build-apps/build-portal/

//omit

[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker images
REPOSITORY                    TAG                 IMAGE ID            CREATED             SIZE
debugroom/wedding             portal              ef2c28cf7acb        6 seconds ago       1.516 GB

[2の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker run -itd --name apserver1 --link apserver7:operation -p 8081:8080 debugroom/wedding:portal

//omit

[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker exec -ti apserver1 /bin/bash
[root@e5c37433ae42 /]# curl http://localhost:8080/api/v1/portal/00000000
{"user":{"userId":"00000000","authorityLevel":9,"imageFilePath":"resources/app/img/debugroom.png","lastLoginDate":1420070400000,"lastUpdatedDate":1420070400000,"loginId":"org.debugroom","firstName":"システム管理者","lastName":"(ΦωΦ)","ver":0,"address":{"userId":"00000000","address":"東京都千代田区1-1-1","lastUpdatedDate":1420070400000,"postCd":"123-4567","ver":0},"affiliations":[],"credentials":[{"id":{"userId":"00000000","credentialType":"PASSWORD"},"credentialKey":"$2a$11$5knhINqfA8BgXY1Xkvdhvu0kOhdlAeN1H/TlJbTbuUPDdqq.H.zzi","lastUpdatedDate":1420070400000,"validDate":1546300800000,"ver":0},{"id":{"userId":"00000000","credentialType":"ACCESSTOKEN"},"credentialKey":"$2a$11$5knhINqfA8BgXY1Xkvdhvu0kOhdlAeN1H/TlJbTbuUPDdqq.H.zzi","lastUpdatedDate":1420070400000,"validDate":1546300800000,"ver":0}],"emails":[{"id":{"userId":"00000000","emailId":0},"email":"00000000abc@debugroom.org","lastUpdatedDate":1420070400000,"ver":0},{"id":{"userId":"00000000","emailId":1},"email":"00000000efg@debugroom.org","lastUpdatedDate":1420070400000,"ver":0}],"notifications":[],"grps":[],"brideSide":false},"informationList":[]}

[root@e5c37433ae42 /]# ctl + p + qで抜ける
[centos@ip-XXXX-XXX-XXX-XXX ~]$ curl http://localhost:8081/api/v1/portal/00000000
{"user":{"userId":"00000000","authorityLevel":9,"imageFilePath":"resources/app/img/debugroom.png","lastLoginDate":1420070400000,"lastUpdatedDate":1420070400000,"loginId":"org.debugroom","firstName":"システム管理者","lastName":"(ΦωΦ)","ver":0,"address":{"userId":"00000000","address":"東京都千代田区1-1-1","lastUpdatedDate":1420070400000,"postCd":"123-4567","ver":0},"affiliations":[],"credentials":[{"id":{"userId":"00000000","credentialType":"PASSWORD"},"credentialKey":"$2a$11$5knhINqfA8BgXY1Xkvdhvu0kOhdlAeN1H/TlJbTbuUPDdqq.H.zzi","lastUpdatedDate":1420070400000,"validDate":1546300800000,"ver":0},{"id":{"userId":"00000000","credentialType":"ACCESSTOKEN"},"credentialKey":"$2a$11$5knhINqfA8BgXY1Xkvdhvu0kOhdlAeN1H/TlJbTbuUPDdqq.H.zzi","lastUpdatedDate":1420070400000,"validDate":1546300800000,"ver":0}],"emails":[{"id":{"userId":"00000000","emailId":0},"email":"00000000abc@debugroom.org","lastUpdatedDate":1420070400000,"ver":0},{"id":{"userId":"00000000","emailId":1},"email":"00000000efg@debugroom.org","lastUpdatedDate":1420070400000,"ver":0}],"notifications":[],"grps":[],"brideSide":false},"informationList":[]}

```

また、実行するDockerfileの概要は以下の通りである。

```bash:バックエンドApp構築用Dockerfile

# Dockerfile for service app using embedded tomcat server

FROM       docker.io/debugroom/wedding:centos7
MAINTAINER debugroom

# JDK1.8、wget、tar、iprouteのインストール
RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel \
       wget tar iproute

# Apache Mavenのインストール
RUN wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
RUN sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
RUN yum install -y apache-maven

# 環境変数JAVA_HOMEの設定
ENV JAVA_HOME /etc/alternatives/jre

# ライブラリ資材をクローン(MavenCentralに公開されてないライブラリ)
RUN git clone -b feature/framework-spring https://github.com/debugroom/framework.git /var/local/framework

# ローカルレポジトリへ資材をインストール
RUN mvn install -f /var/local/framework/pom.xml

# アプリケーション資材をクローン
RUN git clone -b develop https://github.com/debugroom/wedding.git /var/local/wedding

# アプリケーションをビルド
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-infra-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-boot-parent/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-domain-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-web-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-portal/pom.xml

# アプリから参照する環境変数を設定
ENV DBSERVER_APP_USERNAME=XXX
ENV DBSERVER_APP_PASSWORD=XXX
ARG DBSERVER_PORT_5432_TCP_ADDR
ARG DBSERVER_PORT_5432_TCP_PORT
ARG DBSERVER_PORT_9042_TCP_ADDR
ARG DBSERVER_PORT_9042_TCP_PORT
ENV DBSERVER_PORT_5432_TCP_ADDR ${DBSERVER_PORT_5432_TCP_ADDR:-localhost}
ENV DBSERVER_PORT_5432_TCP_PORT ${DBSERVER_PORT_5432_TCP_PORT:-localhost}
ENV DBSERVER_PORT_9042_TCP_ADDR ${DBSERVER_PORT_9042_TCP_ADDR:-localhost}
ENV DBSERVER_PORT_9042_TCP_PORT ${DBSERVER_PORT_9042_TCP_PORT:-localhost}

# TimezoneをJSTに変更
RUN cp /etc/localtime /etc/localtime.org
RUN ln -sf  /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

# ポートを公開。
EXPOSE 8080

# 組み込みTomcatを内包するアプリケーションをプロファイルを指定して起動。
CMD java -jar -Dspring.profiles.active=production,jpa /var/local/wedding/wedding-microservice/wedding-portal/wedding-web-portal/target/wedding-web-portal-1.0-SNAPSHOT.jar

```

##### 2-2. フロントエンドアプリケーションの構築

バックエンドと同様、GitクローンしたDockerfileを使用して、バックエンドのサービスアプリケーション用のコンテナイメージを作成する。本アプリケーションでは、画面にJSPを使用しているため、組み込みTomcatを内包したアプリケーションで実行できないため、アプリケーションをWarファイル化し、Tomcatにデプロイして実行する。

1. Dockerfileを使用して、フロントエンドアプリケーション用のコンテナイメージを作成する。なお、DBサーバのIPとポート以外にもアプリケーション内で参照する環境変数を指定して実行している。フロントエンドアプリケーションはロードバランサを設定して振り分けたときのためにドメイン名を設定しておく。
2. 作成したコンテナイメージを実行する。ポート80がコンテナ内で実行されているAPサーバへ8080で繋がるようにオプション指定する。加えて、同一ホストマシン上で実行されているバックエンドサービスアプリケーションへ接続するためにリンクオプションで環境変数名と実行コンテナ名を紐付ける。
3. アプリケーションが必要とする環境変数を適宜設定し、アプリケーションを起動する。

```bash:

[1の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd /var/local/wedding
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker build --build-arg DBSERVER_PORT_5432_TCP_ADDR=YYY.YYY.YYY.YYY --build-arg DBSERVER_PORT_5432_TCP_PORT=ZZZ --build-arg DBSERVER_PORT_9042_TCP_ADDR=YYY.YYY.YYY.YYY --build-arg DBSERVER_PORT_9042_TCP_PORT=ZZZ --build-arg FRONTEND_PORT_8080_TCP_ADDR=www.debugroom.org --build-arg FRONTEND_PORT_8080_TCP_PORT=ZZZ --build-arg LOGIN_PORT_8080_TCP_ADDR=www.debugroom.org --build-arg LOGIN_PORT_8080_TCP_PORT=ZZZ -t debugroom/wedding:frontend build-production-servers/build-apps/build-frontend/

//omit

[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker images
REPOSITORY                    TAG                 IMAGE ID            CREATED             SIZE
debugroom/wedding             frontend            472f152b6527        3 hours ago         2.456 GB

[2の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker ps -a
CONTAINER ID        IMAGE                                   COMMAND                  CREATED             STATUS              PORTS                                                                      NAMES
817b9c59d3cb        debugroom/wedding:frontend              "/bin/bash"              3 hours ago         Up 3 hours          0.0.0.0:80->8080/tcp                                                       apserver6
a6e70307f7c8        debugroom/wedding:message               "/bin/sh -c 'java -ja"   27 hours ago        Up 27 hours         0.0.0.0:8085->8080/tcp                                                     apserver5
5aa38a74cf0c        debugroom/wedding:gallery               "/bin/sh -c 'java -ja"   30 hours ago        Up 30 hours         0.0.0.0:8084->8080/tcp                                                     apserver4
479ea44caf13        debugroom/wedding:management            "/bin/sh -c 'java -ja"   30 hours ago        Up 30 hours         0.0.0.0:8083->8080/tcp                                                     apserver3
ba1632161f71        debugroom/wedding:profile               "/bin/sh -c 'java -ja"   30 hours ago        Up 30 hours         0.0.0.0:8082->8080/tcp                                                     apserver2
e5c37433ae42        debugroom/wedding:portal                "/bin/sh -c 'java -ja"   31 hours ago        Up 31 hours         0.0.0.0:8081->8080/tcp                                                     apserver1

[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker run -itd --name apserver6 -p 80:8080 --link apserver1:portal --link apserver2:profile --link apserver3:management --link apserver4:gallery --link apserver5:message --link apserver7:operation debugroom/wedding:frontend
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker exec -ti apserver6 /bin/bash
[root@755de24a6e22 /]# export CLOUD_AWS_CREDENTIALS_ACCESSKEY=XXXX
[root@755de24a6e22 /]# export CLOUD_AWS_CREDENTIALS_SECRETKEY=XXXX
[root@755de24a6e22 /]# export CLOUD_AWS_REGION_STATIC=XXXX
[root@755de24a6e22 /]# /var/local/apache-tomcat/bin/startup.sh
Tomcat started.

```

<details><summary>フロントエンドアプリケーションで設定する環境変数</summary>
ここでは、Spring Cloud AWSにて使用するIAMアカウントのCredentialsとリージョンを環境変数として設定している。
</details>

なお、実行するDockerfileは以下の通りである。

```bash:

# Dockerfile for wedding frontend app using embedded tomcat server

FROM       docker.io/debugroom/wedding:centos7
MAINTAINER debugroom

# JDKのインストール
RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel \
       wget tar iproute

# Mavenのインストール
RUN wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
RUN sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
RUN yum install -y apache-maven

# JAVA_HOME環境変数を設定
ENV JAVA_HOME /etc/alternatives/jre

# Tomcatのインストール
RUN useradd -s /sbin/nologin tomcat
RUN wget http://ftp.yz.yamagata-u.ac.jp/pub/network/apache/tomcat/tomcat-8/v8.5.23/bin/apache-tomcat-8.5.23.tar.gz -O /var/local/apache-tomcat-8.5.23.tar.gz  
RUN tar xvzf /var/local/apache-tomcat-8.5.23.tar.gz -C /var/local
RUN ln -s /var/local/apache-tomcat-8.5.23 /var/local/apache-tomcat
RUN chown -R tomcat:tomcat /var/local/apache-tomcat

# アプリケーションで使用する環境変数の設定
ENV DBSERVER_APP_USERNAME=app
ENV DBSERVER_APP_PASSWORD=app
ARG DBSERVER_PORT_5432_TCP_ADDR
ARG DBSERVER_PORT_5432_TCP_PORT
ARG DBSERVER_PORT_9042_TCP_ADDR
ARG DBSERVER_PORT_9042_TCP_PORT
ARG FRONTEND_PORT_8080_TCP_PORT
ARG FRONTEND_PORT_8080_TCP_ADDR
ARG LOGIN_PORT_8080_TCP_PORT
ARG LOGIN_PORT_8080_TCP_ADDR
ENV DBSERVER_PORT_5432_TCP_ADDR ${DBSERVER_PORT_5432_TCP_ADDR:-localhost}
ENV DBSERVER_PORT_5432_TCP_PORT ${DBSERVER_PORT_5432_TCP_PORT:-localhost}
ENV DBSERVER_PORT_9042_TCP_ADDR ${DBSERVER_PORT_9042_TCP_ADDR:-localhost}
ENV DBSERVER_PORT_9042_TCP_PORT ${DBSERVER_PORT_9042_TCP_PORT:-localhost}
ENV FRONTEND_PORT_8080_TCP_ADDR ${LOGIN_PORT_8080_TCP_ADDR:-localhost}
ENV FRONTEND_PORT_8080_TCP_PORT ${LOGIN_PORT_8080_TCP_PORT:-localhost}
ENV LOGIN_PORT_8080_TCP_ADDR ${LOGIN_PORT_8080_TCP_ADDR:-localhost}
ENV LOGIN_PORT_8080_TCP_PORT ${LOGIN_PORT_8080_TCP_PORT:-localhost}

# アプリケーションで必要になるMavenCentralにないライブラリの取得とローカルレポジトリへインストール
RUN git clone -b feature/framework-spring https://github.com/debugroom/framework.git /var/local/framework
RUN mvn install -f /var/local/framework/pom.xml

# アプリケーションをクローンし、デフォルトのプロファイルをproduction, awsに変更
RUN git clone -b develop https://github.com/debugroom/wedding.git /var/local/wedding
RUN sed -i s/LocalAws\,jpa/production\,jpa\,aws/g /var/local/wedding/wedding-microservice/wedding-frontend/wedding-web-frontend/src/main/resources/application.yml

# アプリケーションをビルド
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-infra-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-boot-parent/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-domain-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-web-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-frontend/pom.xml

＃ アプリケーションが必要なディレクトリ環境や資材を構築
RUN mkdir -p /usr/local/app/profile/image
RUN mkdir -p /usr/local/app/info
RUN mkdir -p /usr/local/app/gallery
ADD human-icon.png /usr/local/app/profile/image/
RUN chown -R tomcat:tomcat /usr/local/app

EXPOSE 8080

＃ ServerのTimezoneをJSTに変更
RUN cp /etc/localtime /etc/localtime.org
RUN ln -sf  /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

# アプリケーションをデプロイ
RUN cp /var/local/wedding/wedding-microservice/wedding-frontend/wedding-web-frontend/target/wedding.war /var/local/apache-tomcat/webapps/

```

##### 2-3. バッチアプリケーションの構築

Dockerfileを使用して、定時時刻に起動するバッチアプリケーションのコンテナイメージを作成する。本アプリケーションでは、実行するアプリケーションはExecutableJarで実行するため、バッチアプリケーションのpom.xmlはspring-boot-maven-pluginで実行するMainクラスを指定して、ビルドしておく。

```xml:wedding-opearation-batchのpom.xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.debugroom</groupId>
    <artifactId>wedding-boot-parent</artifactId>
    <relativePath>../../wedding-boot-parent</relativePath>
    <version>1.0-SNAPSHOT</version>
  </parent>
  <artifactId>wedding-batch-operation</artifactId>
  <name>wedding-batch-operation</name>
  <description>wedding-batch-operation</description>

  <!-- omit -->

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <executions>
          <execution>
            <goals>
              <goal>repackage</goal>
            </goals>
            <configuration>
              <mainClass>org.debugroom.wedding.app.batch.operation.launcher.BatchAppLauncher</mainClass>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>

</project>
```

1. オンラインアプリケーション同様、Dockerfileを使用して、バッチアプリケーション用のコンテナイメージを作成する。DBサーバのIPとポート以外にもアプリケーション内で参照する環境変数を指定して実行している。
2. 作成したコンテナイメージを実行する。

```bash:

[1の手順]
[centos@ip-XXXX-XXX-XXX-XXX ~]$ cd /var/local/wedding
[centos@ip-XXX-XXX-XXX-XXX wedding]$ docker build --build-arg DBSERVER_PORT_5432_TCP_ADDR=YYY.YYY.YYY.YYY --build-arg DBSERVER_PORT_5432_TCP_PORT=ZZZ --build-arg DBSERVER_PORT_9042_TCP_ADDR=YYY.YYY.YYY.YYY --build-arg DBSERVER_PORT_9042_TCP_PORT=ZZZ --build-arg CLOUD_AWS_CREDENTIALS_ACCESSKEY=XXX --build-arg CLOUD_AWS_CREDENTIALS_SECRETKEY=XXX --build-arg CLOUD_AWS_REGION_STATIC=XXX -t debugroom/wedding_app-operation-batch:1.0.0.SNAPSHOT  build-production-servers/build-apps/build-operation-batch

//omit

[centos@ip-XXXX-XXX-XXX-XXX wedding]$ docker images
REPOSITORY                              TAG                 IMAGE ID            CREATED             SIZE
debugroom/wedding_app-operation-batch   1.0.0.SNAPSHOT      eeb5854def41        4 hours ago         1.69 GB

[centos@ip-XXX-XXX-XXX-XXX wedding]$ docker run -itd --name batchserver1 debugroom/wedding_app-operation-batch:1.0.0.SNAPSHOT

```
実行するDockerfileは以下の通りである。バッチ実行はCronを使用して指定した時間に起動するため、アプリケーションを起動する際に環境変数を指定するには外部ファイル等から取得する必要がある。

<details><summary>Cronから実行されるスクリプトでのコマンドや環境変数</summary>
Cronでスクリプトを実行する場合、ターミナルで設定していた環境変数やコマンドは引き継がれないため、ここでは、スクリプト(backup.sh)内で外部ファイル(.bashrc)などをsourceコマンドを実行し、パスや環境変数を取得する。
</details>

```bash:

FROM       docker.io/debugroom/wedding:centos7
MAINTAINER debugroom

# JDKのインストール
RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel \
       wget tar iproute

# Mavenのインストール
RUN wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
RUN sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
RUN yum install -y apache-maven

# JAVA_HOME環境変数を設定
ENV JAVA_HOME /etc/alternatives/jre

# アプリケーションで必要になるMavenCentralにないライブラリの取得とローカルレポジトリへインストール
RUN git clone -b feature/framework-spring https://github.com/debugroom/framework.git /var/local/framework
RUN mvn install -f /var/local/framework/pom.xml

# アプリケーションをGitHubから取得
RUN git clone -b develop https://github.com/debugroom/wedding.git /var/local/wedding

# アプリケーションで使用する環境変数の設定
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

# アプリケーションをビルド
RUN mvn install -Dmaven.test.skip=true -f /var/local/wedding/wedding-microservice/wedding-infra-cassandra/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-infra-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-boot-parent/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-domain-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-batch-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-web-common/pom.xml
RUN mvn install -f /var/local/wedding/wedding-microservice/wedding-operation/pom.xml

# Cronのインストール
RUN yum install -y cronie-noanacron

# バッチ実行環境の作成
RUN mkdir -p /usr/local/app/operation
RUN mkdir -p /logs
ADD scripts/backup.sh /usr/local/app/operation/
RUN chmod 755 /usr/local/app/operation/backup.sh

# Cronの実行指定。１時間に1度スクリプトを実行するよう設定
RUN echo "0 * * * * /usr/local/app/operation/backup.sh" > /var/spool/cron/root

# Cronから実行する場合、実行するスクリプト内で、環境変数を外部ファイル(.bashrc)から取得するよう、変数を.bashrcへ書き込む。
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

# 実行するJavaコマンドのコマンドパスを.bashrcへ追加しておく。
RUN echo "PATH="$PATH >> ~/.bashrc

＃ サーバのTimezoneをJSTに変更。
RUN cp /etc/localtime /etc/localtime.org
RUN ln -sf  /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

＃ Cronがコンテナ起動時に実行されるようCMDコマンドでcronデーモンを起動。
CMD crond -n

```
