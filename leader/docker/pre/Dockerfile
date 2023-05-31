FROM poweryangcl/centos7.9:v2.0.1-jre1.8
 
LABEL maintainer=Yangcl

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

COPY target/*.war /app.war

EXPOSE 8080

ENTRYPOINT ["sh","-c","java -jar \
-Dspring.profiles.active=pre \
-Dfile.encoding=utf8 \
-Dspring.cloud.nacos.discovery.server-addr=mdf-pre-nacos.mdf-pre:8848 \
-Dspring.cloud.nacos.discovery.namespace=mdf-pre \
-Dspring.cloud.nacos.discovery.group=leader \
-Dspring.cloud.nacos.config.server-addr=mdf-pre-nacos.mdf-pre:8848 \
-Dspring.cloud.nacos.config.namespace=mdf-pre \
-Dspring.cloud.nacos.config.group=leader \
-Dspring.cloud.nacos.config.file-extension=yaml  \
-Dspring.cloud.nacos.config.shared-configs[0].dataId=application.yaml \
-Dspring.cloud.nacos.config.shared-configs[0].group=leader \
-Dspring.cloud.nacos.config.shared-configs[0].refresh=true /app.war"]
