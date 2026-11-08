
pipeline {
    agent any

    environment {
        IMAGE_NAME = 'surendra187/library-management'
        IMAGE_TAG = 'latest'
        COMPOSE_PROJECT_NAME = 'library_management_system'
    }

    stages {

        stage('GitHub Checkout') {
            steps {
                checkout scm
                echo 'Source code downloaded successfully'
            }
        }

        stage('Maven Build') {
            steps {
                sh 'mvn -B clean package -DskipTests'
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

        stage('Deploy using Docker Compose') {
            steps {
                withCredentials([
                    string(
                        credentialsId: 'mysql-root-password',
                        variable: 'MYSQL_ROOT_PASSWORD'
                    )
                ]) {
                    sh '''
                        set -eu
                        export MYSQL_ROOT_PASSWORD
        
                        docker compose \
                            -p library_management_system \
                            -f /home/ubuntu/Library_management_system/docker-compose.yml \
                            pull springboot
        
                        docker compose \
                            -p library_management_system \
                            -f /home/ubuntu/Library_management_system/docker-compose.yml \
                            up -d --no-deps --force-recreate springboot
                    '''
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
