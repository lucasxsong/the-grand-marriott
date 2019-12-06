#! /bin/bash
echo "creating db named ... "$USER"_DB"
createdb -h localhost -p $PGPORT $USER"_DB"
/usr/lib/postgresql/10/bin/pg_ctl status
