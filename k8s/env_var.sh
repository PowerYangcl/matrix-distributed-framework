#!/bin/sh
#
# author: chengongwei
# team:   nanhaigroup-edp
#
# function: 定义用于Build、Test、Deploy等场景的环境变量
#

#------------------------
# 加载配置文件
#------------------------

# 读取 登录Docker Registry的账号密码配置
source k8s/registry_account.conf


#------------------------
# 定义相关变量
#------------------------

# Git代码当前的Tag标签值
git_tag=$(git rev-parse --short HEAD)

# 应用名称  (根据实际情况进行修改)
app_name=matrix-admin-leader


# Docker Registry 地址
docker_registry_addr=harbor.master.online.local

# Docker Registry 项目名称
docker_registry_project=new-shushang

# Docker Registry 登录用户名
docker_registry_user=$username

# Docker Registry 登录账号
docker_registry_passwd=$password

# Docker Image 名称
docker_image_name=$app_name

# Docker Image 全名
image_full_name=$docker_registry_addr/$docker_registry_project/$docker_image_name:$git_tag

# Kubernetes命名空间
k8s_namespace=new-shushang

# Kubernetes Deployment 名称
k8s_deployment=matrix-admin-leader

# Kubernetes Container 名称
k8s_container=matrix-admin-leader-spec