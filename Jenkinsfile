pipeline {
    agent any
    tools {
        maven 'Maven'
    }

    environment {
        // Define your Telegram Token and Chat ID as credentials in Jenkins
        // These are the IDs of the stored credentials, not the actual token and chat ID values
        TELEGRAM_TOKEN_ID = 'telegram-credentials'
        TELEGRAM_CHAT_ID_ID = 'Telegram_ChatID'
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
                // Tambahkan penundaan untuk memberikan waktu kepada server Spring Boot
                sleep time: 1, unit: 'MINUTES'
                withSonarQubeEnv('sq1') {
                    sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=sonar-analysis -Dsonar.projectName="sonar-analysis" -Dsonar.host.url=http://localhost:9000'
                    echo 'SonarQube Analysis Completed'
                }
            }
        }
    }

    post {
        always {
            withCredentials([string(credentialsId: TELEGRAM_TOKEN_ID, variable: 'TELEGRAM_TOKEN'), string(credentialsId: TELEGRAM_CHAT_ID_ID, variable: 'TELEGRAM_CHAT_ID')]) {
                sh 'curl --location --request POST "https://api.telegram.org/bot$TELEGRAM_TOKEN/sendMessage" --form text="Pipeline finished: ${currentBuild.result}" --form chat_id="$TELEGRAM_CHAT_ID"'
            }
        }
    }
}
