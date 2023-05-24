FROM poweryangcl/centos7.9:v2.0.1-jre1.8
 
LABEL maintainer=Yangcl

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

COPY target/*.war /app.war

EXPOSE 8080

ENTRYPOINT ["sh","-c","java -jar \
-Dspring.profiles.active=prod \
-Dfile.encoding=utf8 \
-Dspring.cloud.nacos.discovery.server-addr=mdf-prod-nacos.matrix-distributed-framework:8848 \
-Dspring.cloud.nacos.discovery.namespace=mdf-prod \
-Dspring.cloud.nacos.discovery.group=matrix-jsp-demo \
-Dspring.cloud.nacos.config.server-addr=mdf-prod-nacos.matrix-distributed-framework:8848 \
-Dspring.cloud.nacos.config.namespace=mdf-prod \
-Dspring.cloud.nacos.config.group=matrix-jsp-demo \
-Dspring.cloud.nacos.config.file-extension=yaml  \
-Dspring.cloud.nacos.config.shared-configs[0].dataId=application.yaml \
-Dspring.cloud.nacos.config.shared-configs[0].group=matrix-jsp-demo \
-Dspring.cloud.nacos.config.shared-configs[0].refresh=true /app.war"]
