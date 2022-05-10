FROM postgres:14-alpine
COPY init/create_dbs.sql /docker-entrypoint-initdb.d/