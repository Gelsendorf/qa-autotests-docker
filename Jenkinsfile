pipeline {
    agent any

    stages {
        stage('Clean workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Checkout') {
            steps {
                git 'https://github.com/Gelsendorf/qa-autotests-docker.git'
            }
        }

        stage('Build Docker image') {
            steps {
                sh 'docker build -t my-autotests .'
            }
        }

        stage('Run tests') {
            steps {
                sh 'docker run --rm my-autotests'
            }
        }
    }
}