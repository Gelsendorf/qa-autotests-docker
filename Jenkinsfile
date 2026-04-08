pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Gelsendorf/qa-autotests-docker.git'
            }
        }

        stage('Build Docker image') {
            steps {
                // Використовуємо Docker, доступний через сокет хоста
                sh 'docker build -t my-autotests .'
            }
        }

        stage('Run tests') {
            steps {
                // Запуск контейнера для тестів
                sh '''
                docker run --rm \
                  -v $PWD:/app \
                  my-autotests \
                  mvn test -Dselenide.headless=true
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished'
        }
    }
}

