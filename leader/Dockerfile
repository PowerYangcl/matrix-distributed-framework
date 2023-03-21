# 使用哪个基础镜像
FROM poweryangcl/leader:centos7.9-pre-jre-v1.0

# 谁创建了这个镜像
LABEL maintainer=Yangcl

#【整体含义】将当前宿主机target文件夹下的所有jar包复制到基础镜像的/usr/local/目录下，同时重命名为app.jar，无论其原来的名字是什么。
#【target/*.war】target目录下的任意jar包，实际上只有一个jar包，但使用*.jar更具有通用性，省的再次修改
#【/usr/local/app.war】/usr/local：在Linux中代表用户级的程序目录，可以理解为C:/Progrem Files/；用户自己编译的软件默认会安装到这个目录下。
COPY target/*.war   /usr/local/app.war

# 容器启动后所执行的命令，java程序还可以将虚拟机调优的参数放到此处。
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=pre","/usr/local/app.war"]