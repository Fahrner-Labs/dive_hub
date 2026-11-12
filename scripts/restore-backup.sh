#!/bin/sh
# Restore a Dive Hub backup from the command line (plan FAH-175):
#   scripts/restore-backup.sh <archive.divehub-backup> [--force]
# The archive stays on the host and is mounted read-only; the data volume receives the restored files.
# The CLI refuses while the app is running, unless --force says the operator knows it is stopped.
set -eu

if [ "$#" -lt 1 ]; then
  echo "usage: $0 <archive.divehub-backup> [--force]" >&2
  exit 2
fi

archive=$1
shift
case "$archive" in
  /*) ;;
  *) archive="$(pwd)/$archive" ;;
esac
if [ ! -f "$archive" ]; then
  echo "$0: no such archive: $archive" >&2
  exit 2
fi

if [ -z "${DIVEHUB_BACKUP_PASSPHRASE:-}" ]; then
  echo "$0: set DIVEHUB_BACKUP_PASSPHRASE first:" >&2
  echo "  read -rs DIVEHUB_BACKUP_PASSPHRASE; export DIVEHUB_BACKUP_PASSPHRASE" >&2
  exit 2
fi

# docker compose run starts the database dependency; the app itself must be stopped (the lock probe refuses otherwise).
exec docker compose run --rm \
  -v "$archive:/restore/backup.divehub-backup:ro" \
  -e DIVEHUB_BACKUP_PASSPHRASE \
  app node dist/main.js restore-backup /restore/backup.divehub-backup "$@"
