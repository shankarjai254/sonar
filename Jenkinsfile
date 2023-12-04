pipeline {
    agent any
    tools {
        maven 'Maven'
    }

    environment {
        // Telegram configuration
        TOKEN = credentials('telegram-credentials')
        CHAT_ID = credentials('Telegram_ChatID')
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scm
                echo 'Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Add a delay to give time to the Spring Boot server
                sleep time: 1, unit: 'MINUTES'
                withSonarQubeEnv('sq1') {
                    sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=sonar-analysis -Dsonar.projectName="sonar-analysis" -Dsonar.host.url=http://localhost:9000'
                    echo 'SonarQube Analysis Completed'
                }
            }
        }
    }

    post {
        success {
            // Use 'bat' directly within the 'success' block
            bat """curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"chat_id\\\":${CHAT_ID}, \\\"text\\\": \\\"Pipeline Succeded!\\\", \\\"disable_notification\\\": false}\" https://api.telegram.org/bot${TOKEN}/sendMessage"""

        }
        failure {
            // Use 'bat' directly within the 'failure' block
            bat """curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"chat_id\\\":${CHAT_ID}, \\\"text\\\": \\\"Pipeline failed!\\\", \\\"disable_notification\\\": false}\" https://api.telegram.org/bot${TOKEN}/sendMessage"""

        }
    }
}
