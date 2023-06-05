pipeline {
  agent {
    node {
      label 'maven'
    }

  }
  stages {
    stage('拉取代码') {
      agent none
      steps {
        container('maven') {
          git(url: 'https://gitee.com/PowerYangcl/matrix-distributed-framework.git', branch: 'master', credentialsId: 'gitee-power-matrix', changelog: true, poll: false)
          sh 'ls -al'
        }

      }
    }

    stage('项目编译') {
      agent none
      steps {
        container('maven') {
          sh '''ls -al
mvn clean package -Dmaven.test.skip=true'''
        }

      }
    }

    stage('default-2') {
      parallel {
        stage('构建leader镜像') {
          agent none
          steps {
            container('maven') {
              sh '''alias docker=podman
docker build --tag leader:latest -f leader/Dockerfile ./leader/
podman ps -a
podman -v'''
            }

          }
        }

        stage('构建matrix-jsp-demo镜像') {
          agent none
          steps {
            container('maven') {
              sh '''alias docker=podman
docker build --tag matrix-jsp-demo:latest -f matrix-jsp-demo/Dockerfile ./matrix-jsp-demo/
podman ps -a
podman -v'''
            }

          }
        }

      }
    }

    stage('default-3') {
      parallel {
        stage('推送leader镜像') {
          agent none
          steps {
            container('maven') {
              withCredentials([usernamePassword(credentialsId : 'aliyun-docker-registry' ,usernameVariable : 'DOCKER_USER_VAR' ,passwordVariable : 'DOCKER_PWD_VAR' ,)]) {
                sh '''alias docker=podman
echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'''
                sh '''alias docker=podman
docker tag leader:latest $REGISTRY/$DOCKERHUB_NAMESPACE/leader:snapshot-$BUILD_NUMBER
docker images'''
                sh ''' alias docker=podman
docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/leader:snapshot-$BUILD_NUMBER'''
              }

            }

          }
        }

        stage('推送matrix-jsp-demo镜像') {
          agent none
          steps {
            container('maven') {
              withCredentials([usernamePassword(credentialsId : 'aliyun-docker-registry' ,usernameVariable : 'DOCKER_USER_VAR' ,passwordVariable : 'DOCKER_PWD_VAR' ,)]) {
                sh '''alias docker=podman
echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'''
                sh '''alias docker=podman
docker tag matrix-jsp-demo:latest $REGISTRY/$DOCKERHUB_NAMESPACE/matrix-jsp-demo:snapshot-$BUILD_NUMBER
docker images'''
              }

            }

          }
        }

      }
    }

    stage('default-4') {
      parallel {
        stage('部署到Prod环境(线上环境)') {
          agent none
          steps {
            container('maven') {
              kubernetesDeploy(enableConfigSubstitution: true, deleteResource: false, kubeconfigId: 'demo-kubeconfig', configs: 'leader/deploy/deploy-prod.yaml')
            }

          }
        }

        stage('demo-jps部署到Prod环境') {
          agent none
          steps {
            sh 'ls'
          }
        }

      }
    }

    stage('deploy to production') {
      steps {
        container('maven') {
          input(id: 'deploy-to-production', message: 'deploy to production?')
          withCredentials([kubeconfigContent(credentialsId : 'KUBECONFIG_CREDENTIAL_ID' ,variable : 'KUBECONFIG_CONFIG' ,)]) {
            sh 'mkdir -p ~/.kube/'
            sh 'echo "$KUBECONFIG_CONFIG" > ~/.kube/config'
            sh 'envsubst < deploy/prod-ol/deploy.yaml | kubectl apply -f -'
          }

        }

      }
    }

  }
  environment {
    DOCKER_CREDENTIAL_ID = 'dockerhub-id'
    GITHUB_CREDENTIAL_ID = 'github-id'
    KUBECONFIG_CREDENTIAL_ID = 'demo-kubeconfig'
    REGISTRY = 'registry.cn-beijing.aliyuncs.com'
    DOCKERHUB_NAMESPACE = 'power-matrix'
    GITHUB_ACCOUNT = 'kubesphere'
    APP_NAME = 'devops-java-sample'
    ALIYUNHUB_NAMESPACE = 'power-matrix'
  }
  parameters {
    string(name: 'TAG_NAME', defaultValue: '', description: '')
  }
}