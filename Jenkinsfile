pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Gelsendorf/qa-autotests-docker.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'docker build -t my-autotests .'
                sh 'docker run --rm my-autotests'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished'
        }
    }
}

