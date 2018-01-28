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
