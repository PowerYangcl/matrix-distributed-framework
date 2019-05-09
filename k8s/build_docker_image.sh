#!/bin/sh

# 引入变量定义脚本
. k8s/env_var.sh

echo "----------------------------"
echo "构建Docker Image:" $image_full_name
echo "----------------------------"


# 将编译后的程序包放到Docker Image构建目录下
#. k8s/copy_file.sh

# 构建Docker Image
cd k8s/dockerfile/

docker build -t $image_full_name .

if [ $? -ne 0 ]
then
    echo "创建Docker Image失败！"
    
    exit 101
fi

# 登录到内部Docker Registry
docker login -u $docker_registry_user -p $docker_registry_passwd $docker_registry_addr

if [ $? -ne 0 ]
then
    echo "登录到Docker Registry失败！"
    
    exit 102
fi

# 将Docker Image推送到内部Docker Registry中
docker push $image_full_name

if [ $? -ne 0 ]
then
    echo "推送Docker Image到Registry失败！"
    
    exit 103
else
    exit 0
fi
