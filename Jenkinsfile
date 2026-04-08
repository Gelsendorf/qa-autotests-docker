pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Gelsendorf/qa-autotests-docker.git', branch: 'main'
            }
        }

        stage('Build Docker image') {
            steps {
                script {
                    sh 'docker build -t my-autotests .'
                }
            }
        }

        stage('Run tests') {
            steps {
                sh 'mvn test -Dselenide.headless=true'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished'
        }
    }
}
