FROM harbor.master.online.local/new-shushang/centos-base-tomcat:20181107

MAINTAINER alren "alren@300.cn"


ADD filebeat.yml /etc/filebeat/filebeat.yml
ADD matrix-admin.war /data/tomcat/webapps/
RUN echo 'JAVA_OPTS="$JAVA_OPTS -Duser.timezone=Asia/Shanghai"' >> /apps/tomcat/bin/catalina.sh
RUN cd /data/tomcat/webapps/ && unzip matrix-admin.war -d matrix-admin
ADD run_service.sh /apps
ENV LANG en_US.utf8

EXPOSE 8080
ENTRYPOINT ["/bin/bash","/apps/run_service.sh"]
