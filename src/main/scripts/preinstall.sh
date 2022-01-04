#!/bin/sh

/usr/sbin/useradd -c "Space Station Application" -U \
  -s /sbin/nologin -r \
  -d /var/spacestation spacestation 2> /dev/null || :

systemctl is-active --quiet spacestation
STATUS=$?

if [ ${STATUS} -eq 0]; then
  systemctl stop spacestation
fi