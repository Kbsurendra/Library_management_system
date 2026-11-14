
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
        COMPOSE_PROJECT_NAME = 'library_management_system'
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

        stage('Deploy using Docker Compose') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'mysql-root-password',
                        usernameVariable: 'MYSQL_USER',
                        passwordVariable: 'MYSQL_ROOT_PASSWORD'
                    )
                ]) {
                    sh '''
                        set -eu
                        set +x

                        docker compose \
                            -p ${COMPOSE_PROJECT_NAME} \
                            -f /home/ubuntu/Library_management_system/docker-compose.yml \
                            config --quiet

                        docker compose \
                            -p ${COMPOSE_PROJECT_NAME} \
                            -f /home/ubuntu/Library_management_system/docker-compose.yml \
                            pull springboot

                        docker compose \
                            -p ${COMPOSE_PROJECT_NAME} \
                            -f /home/ubuntu/Library_management_system/docker-compose.yml \
                            up -d --no-deps --force-recreate springboot
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
