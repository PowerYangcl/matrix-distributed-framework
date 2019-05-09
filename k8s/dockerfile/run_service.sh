#!/bin/bash
#
echo "options timeout:1 attempts:1 rotate" > /etc/resolv.conf
echo "nameserver 127.0.0.1" >> /etc/resolv.conf
rpcbind
/usr/bin/bash /apps/tomcat/bin/startup.sh
/usr/share/filebeat/bin/filebeat  -c  /etc/filebeat/filebeat.yml &
/usr/sbin/dnsmasq -d
