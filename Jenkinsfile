pipeline {
    agent any

    environment {
        REPO = 'ecommerce_microservices'
    }

    stages {
        stage('Clone Repository') {
            steps {
                echo '📥 Cloning repository from GitHub...'
                git 'https://github.com/Pranav128/ecommerce-microservices.git'
                echo '✅Repository cloned...'
            }
        }

        stage('Build Java Services and images') {
            steps {
                echo '🔨 Building all microservices using Maven and JIB...'
                bat 'mvn clean install -DskipTests jib:dockerBuild'
                echo "✅ Build successfully"
            }
        }

        stage('test'){
            steps{
                echo "💻 Testing application...."
                //other testing stuff
            }
        }

        stage('Push Docker images') {
            steps {
                echo '🚀 Pushing images to Docker Hub...'
                withCredentials([
                    usernamePassword(credentialsId: 'dockerHub',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS')
                ]) {
                    bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASS%'
                    echo '🚀 LoggedIn to DockerHub...'
                    script {
                        def services = ['config-server', 'service-registry', 'api-gateway', 'user-service', 'product-service', 'cart-service', 'order-service', 'payment-service', 'product-review', 'notification-service']
                        services.each { service ->
                            echo "📤 Pushing ${service} image to Docker Hub..."
                            bat "docker push ${DOCKER_USER}/${REPO}:${service}-v1"
                        }
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo '🚢 Deploying application using Docker Compose...'
                bat 'docker-compose down || true'
                bat 'docker-compose up -d'
            }
        }
    }

    post {
        success {
            echo '✅ CI/CD Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed. Check logs for details.'
        }
    }
}