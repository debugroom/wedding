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

[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo yum update -y
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo yum install -y docker
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo groupadd docker
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo gpasswd -a $USER docker
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo systemctl enable docker.service
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ sudo systemctl start docker.service
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
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker pull centos
//omit
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
docker.io/centos    latest              196e0ce0c9fb        7 weeks ago         196.6 MB
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker run -it --name centos7 centos:latest /bin/bash
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

ctl+p+qでコンテナからエスケープする。

[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker ps -a
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
08b670819a55        centos:latest       "/bin/bash"         36 minutes ago      Up 36 minutes                           centos7
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker commit centos7 debugroom/wedding:centos7
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker login
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username (debugroom): debugroom
Password:
Login Succeededdocker commit centos7 debugroom/wedding:centos7
[centos@ip-XXXX-XXX-XXX-XXX ~]$ docker push debugroom/wedding

```

##### 2-2. PostgreSQLのインストール・コンテナイメージ作成。

2-1で作成したCentOS7コンテナイメージ内にPostgreSQLをインストールする。インストールはDockerfileを利用して行う。なお、Dockerfileはgit cloneして取得する。
