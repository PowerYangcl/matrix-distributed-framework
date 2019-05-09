#!/bin/sh

# 引入变量定义脚本
. k8s/env_var.sh

# 检查Image是否已部署到Kubernetes中

# TODO: 


# 获取上一次部署的版本
lastDeploy=$(kubectl -n $k8s_namespace rollout history deploy/$k8s_deployment | tail -n 2 | head -n 1 | awk '{print $1}')

echo "上次部署Deployment版本:" $lastDeploy

# 更新Pod中容器的Image
kubectl -n $k8s_namespace set image deploy/$k8s_deployment $k8s_container=$image_full_name --record

if [ $? -ne 0 ]
then
    echo "更新Pod中容器Image失败！"

    exit 113
fi

update_result=0

# 等待时间
wait_time=0

while (( $update_result == 0 ))
do
    # 等待5秒，以便Kubernetes完成容器的更新
    sleep 5

    wait_time=`expr $wait_time + 5`

    # 检查更新是否失败
    update_result=$(kubectl -n $k8s_namespace describe deploy/$k8s_deployment | grep ProgressDeadlineExceeded | wc -l)

    # 等待超过60秒，就结束等待
    if [ $wait_time -gt 60 ]
    then 
        break
    fi   
    
    # Image更新成功，结束等待
    if [ $update_result == 0 ]
    then
        break
    fi
done

echo "容器Image更新用时："$wait_time"秒"

# 如果更新失败，回滚到上一版本
if [ "$update_result"x != "0"x ]; 
then
    echo 'Kubernetes 容器Image更新失败！'
    
    kubectl -n $k8s_namespace rollout undo deploy/$k8s_deployment --to-revision=$lastDeploy

    exit 1
else
    echo 'Kubernetes 容器Image更新成功！'   
 
    exit 0
fi
