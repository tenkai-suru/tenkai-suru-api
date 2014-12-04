#!/bin/bash

set -e
set -o pipefail

root="$(dirname $0)/.."
timestamp=$(date +%Y%m%d%H%M%S)

up=$root/migrations/$timestamp-$1.up.sql
down=$root/migrations/$timestamp-$1.down.sql

if [ ! -z $1 ]
then
  touch $up
  touch $down
  echo "Created ${timestamp}-${1}.up.sql and ${timestamp}-${1}.down.sql"
else
  echo "ERROR: You must provide a name for the migration"
fi
