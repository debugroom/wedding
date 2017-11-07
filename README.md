# Wedding App

## Deploy

### DB Server on CentOS7 with docker

アプリケーションのデータベースとして、PostgreSQLとApache Cassandraを環境構築する。なお、Cassandraはクラスタ構成するため、Dockerを利用し、マルチコンテナ構成でデータベースを構築する。

#### 1. Install Docker

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

#### 2. Install PostgreSQL on CentOS7 in docker container

PostgreSQLをコンテナ内のCentOS7へインストールする。構築した環境はイメージとしてDocker Hubに保存しておく。

##### 2-1. CentOS7コンテナイメージの作成

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

##### 2-2. PostgreSQLのインストール・コンテナイメージ作成。

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

#### 3. Install Apache Cassandra on CentOS7 in docker container

Cassandraをコンテナ内のCentOS7へインストールする。コンテナのサーバは3台のクラスタ構成とし、構築した環境はイメージとしてDocker Hubに保存しておく。[2-2](https://github.com/debugroom/wedding/tree/develop#2-2-postgresqlのインストールコンテナイメージ作成)と同様、git clonseしたプロジェクト内にあるDockerfileから構築を行う。[Cassadraクラスタサーバを構築](http://debugroom.github.io/doc/memo/work/docker/article/usage.html#cassandra)も合わせて参照のこと。

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

### AP Server on CentOS7 with docker
