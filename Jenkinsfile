pipeline {
    agent {
        label 'slave1'
    }
    stages {
        stage('打包代码到工作目录 target 文件夹中') {            
            steps {                
                echo '构建release分支代码...'
                sh 'mvn clean package -U -Prelease -Dmaven.test.skip=true'
		sh 'cp -rf ${WORKSPACE}/matrix-admin/target/matrix-admin.war  ${WORKSPACE}/k8s/dockerfile/'
		sh 'rm -rf ${WORKSPACE}/matrix-admin/target/matrix-admin.war'
            }        
        }
        stage('构建 Docker 镜像') {            
            steps {                
                echo '构建Docker Image文件...'  
                sh './k8s/build_docker_image.sh'
            }      
        }   
    
        stage('更新镜像到 k8s 环境') {            
            steps {                
                echo '部署到Kubernetes环境...'  

                sh './k8s/deploy_docker_image.sh'
            }        
        }  
    }
 
    post {        
        always {            
            echo 'Clean up workspace'            
            deleteDir() /* clean up our workspace */        
        }        
        success {            
            echo '系统上线成功!'        
        }        
   
        failure {            
            echo '系统上线失败， 回滚!'        
        }        
  
    }
}
