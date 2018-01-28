#!/bin/bash

set PASSWORD=postgres

su - postgres -c "pg_ctl start -w;psql -f /var/local/wedding/build-production-servers/build-postgresql/scripts/create_role.sql;pg_ctl stop -m fast"
su - postgres -c "pg_ctl start -w;psql -f /var/local/wedding/build-production-servers/build-postgresql/scripts/create_db.sql;pg_ctl stop -m fast"
su - postgres -c "pg_ctl start -w;psql -f /var/local/wedding/build-production-servers/build-postgresql/scripts/create_table.sql -d wedding;pg_ctl stop -m fast"
su - postgres -c "pg_ctl start -w;psql -f /var/local/wedding/build-production-servers/build-postgresql/scripts/grant_role.sql -d wedding;pg_ctl stop -m fast"
su - postgres -c "pg_ctl start -w;psql -f /var/local/wedding/build-production-servers/build-postgresql/scripts/init_data.sql -d wedding;pg_ctl stop -m fast"
