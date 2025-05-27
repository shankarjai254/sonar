pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/shankarjai254/sonar.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}
