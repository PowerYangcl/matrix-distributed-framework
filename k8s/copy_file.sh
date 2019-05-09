#!/bin/sh

# 拷贝应用程序压缩包
/bin/cp -fr ../build/libs/*.jar  k8s/dockerfile/

# 拷贝各种配置文件
