#!/bin/sh

SYSTEMD_FILE="/etc/systemd/system/spacestation.service"
JAR_FILE=$(ls -r /com/api/spacestation/spacestation.jar | head -n1)

echo "[Unit]" > ${SYSTEMD_FILE}
echo "Description=Space Station REST Service" >> ${SYSTEMD_FILE}
echo "After=syslog.target" >> ${SYSTEMD_FILE}
echo "" >> ${SYSTEMD_FILE}
echo "[Service]" >> ${SYSTEMD_FILE}
echo "User=spacestation" >> ${SYSTEMD_FILE}
echo "EnvironmentFile=/etc/environment" >> ${SYSTEMD_FILE}
echo "ExecStart=/usr/bin/java -jar /com/api/spacestation/spacestation.jar" >> ${SYSTEMD_FILE}
echo "SuccessExitStatus=143" >> ${SYSTEMD_FILE}
echo "" >> ${SYSTEMD_FILE}
echo "[Install]" >> ${SYSTEMD_FILE}
echo "WantedBy=multi-user.target" >> ${SYSTEMD_FILE}

# Initial installation
systemctl daemon-reload
systemctl enable spacestation.service
systemctl start spacestation.service
