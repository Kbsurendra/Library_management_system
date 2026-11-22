
pipeline {
    agent any

    tools {
        jdk 'JDK17'
    }

    options {
        skipDefaultCheckout(true)
    }

    environment {
        IMAGE_NAME = 'surendra187/library-management'
        IMAGE_TAG = 'latest'
       // COMPOSE_PROJECT_NAME = 'library_management_system'
        K8S_DIR = 'Kuberneties'
        K8S_NAMESPACE = 'spring-boot'  
    }

    stages {

        stage('Check Java Environment') {
            steps {
                sh '''
                    echo "===== JAVA HOME ====="
                    echo "$JAVA_HOME"

                    echo "===== JAVA VERSION ====="
                    java -version

                    echo "===== JAVAC VERSION ====="
                    javac -version

                    echo "===== MAVEN VERSION ====="
                    mvn -version

                    echo "===== JAVA PATH ====="
                    which java
                    which javac

                    echo "===== JAVA 17 COMPILER ====="
                    "$JAVA_HOME/bin/javac" -version
                '''
            }
        }

        stage('GitHub Checkout') {
            steps {
                checkout scm
                echo 'Source code downloaded successfully'
            }
        }

        stage('Maven Build') {
            steps {
                sh '''
                    echo "Building with Java 17"
                    mvn -B clean package -DskipTests
                '''
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
                    docker image inspect ${IMAGE_NAME}:${IMAGE_TAG}
                '''
            }
        }

        stage('Docker Hub Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_TOKEN'
                    )
                ]) {
                    sh '''
                        set +x

                        echo "$DOCKER_TOKEN" | docker login \
                            -u "$DOCKER_USER" --password-stdin

                        docker push ${IMAGE_NAME}:${IMAGE_TAG}

                        docker logout
                    '''
                }
            }
        }
        
        
        // stage('Deploy using Docker Compose') {
        //     steps {
        //         withCredentials([
        //             usernamePassword(
        //                 credentialsId: 'mysql-root-password',
        //                 usernameVariable: 'MYSQL_USER',
        //                 passwordVariable: 'MYSQL_ROOT_PASSWORD'
        //             )
        //         ]) {
        //             sh '''
        //                 set -eu
        //                 set +x
        
        //                 echo "Checking Docker Compose file..."
        
        //                 test -f "$WORKSPACE/docker-compose.yml"
        
        //                 echo "Starting MySQL and MongoDB..."
        
        //                 docker compose \
        //                     -p ${COMPOSE_PROJECT_NAME} \
        //                     -f "$WORKSPACE/docker-compose.yml" \
        //                     up -d mysql mongodb
        
        //                 echo "Checking database containers..."
        
        //                 docker compose \
        //                     -p ${COMPOSE_PROJECT_NAME} \
        //                     -f "$WORKSPACE/docker-compose.yml" \
        //                     ps
        
        //                 echo "Pulling Spring Boot image..."
        
        //                 docker compose \
        //                     -p ${COMPOSE_PROJECT_NAME} \
        //                     -f "$WORKSPACE/docker-compose.yml" \
        //                     pull springboot
        
        //                 echo "Starting Spring Boot application..."
        
        //                 docker compose \
        //                     -p ${COMPOSE_PROJECT_NAME} \
        //                     -f "$WORKSPACE/docker-compose.yml" \
        //                     up -d --force-recreate springboot
        
        //                 echo "Final container status..."
        
        //                 docker compose \
        //                     -p ${COMPOSE_PROJECT_NAME} \
        //                     -f "$WORKSPACE/docker-compose.yml" \
        //                     ps
        
        //                 echo "Deployment completed!"
        //             '''
        //         }
        //     }
        // }
        
        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([
                    file(
                        credentialsId: 'kubeconfig-file',
                        variable: 'KUBECONFIG'
                    )
                ]) {
                    sh '''
                        set -eu
        
                        echo "===== CHECK KUBERNETES CONNECTION ====="
        
                        kubectl cluster-info
        
                        echo "===== CHECK KUBERNETES FILES ====="
        
                        test -f "$WORKSPACE/$K8S_DIR/namespace.yaml"
        
                        echo "===== CREATE NAMESPACE ====="
        
                        kubectl apply \
                            -f "$WORKSPACE/$K8S_DIR/namespace.yaml"
        
                        echo "===== DEPLOY MYSQL ====="
        
                        kubectl apply \
                            -n "$K8S_NAMESPACE" \
                            -f "$WORKSPACE/$K8S_DIR/mysql/"
        
                        echo "===== DEPLOY MONGODB ====="
        
                        kubectl apply \
                            -n "$K8S_NAMESPACE" \
                            -f "$WORKSPACE/$K8S_DIR/mongodb/"
        
                        echo "===== DEPLOY SPRING BOOT ====="
        
                        kubectl apply \
                            -n "$K8S_NAMESPACE" \
                            -f "$WORKSPACE/$K8S_DIR/springboot/"
        
                        echo "===== UPDATE SPRING BOOT IMAGE ====="
        
                        kubectl set image \
                            deployment/springboot \
                            springboot=${IMAGE_NAME}:${IMAGE_TAG} \
                            -n "$K8S_NAMESPACE"
        
                        echo "===== WAIT FOR DEPLOYMENT ====="
        
                        kubectl rollout status \
                            deployment/springboot \
                            -n "$K8S_NAMESPACE" \
                            --timeout=180s
        
                        echo "===== CHECK PODS ====="
        
                        kubectl get pods -n "$K8S_NAMESPACE"
        
                        echo "===== CHECK SERVICES ====="
        
                        kubectl get services -n "$K8S_NAMESPACE"
        
                        echo "===== DEPLOYMENT COMPLETED ====="
                    '''
                }
            }
        }



    }

    post {
        success {
            echo 'Library Management application deployed successfully!'
        }

        failure {
            echo 'Pipeline failed. Check Jenkins Console Output.'
        }
    }
}
