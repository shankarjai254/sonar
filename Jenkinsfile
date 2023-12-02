pipeline {
    agent any
    tools {
        maven 'Maven'
    }

    stages {
        stage('Start') {
            steps {
                script {
                    echo 'Pipeline has started'
                    // Add Telegram Notification Step at the beginning
                    telegramSend(
                        message: "Pipeline has started for SonarQube analysis",
                        chatId: "your_chat_id",  // Replace with your Telegram chat ID
                        token: env.token_telegram
                    )
                }
            }
        }

        stage('Git Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/ZudaPradana/sonar']])
                echo 'Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sq1') {
                    bat '''mvn clean verify sonar:sonar -Dsonar.projectKey=sonar-analysis -Dsonar.projectName='sonar-analysis' -Dsonar.host.url=http://localhost:9000'''
                    echo 'SonarQube Analysis Completed'
                }
            }
        }

        stage('End') {
            steps {
                script {
                    // Add Telegram Notification Step at the end
                    telegramSend(
                        message: "SonarQube analysis pipeline completed: ${currentBuild.result}",
                        chatId: "725260461",  // Replace with your Telegram chat ID
                        token: env.token_telegram
                    )
                }
            }
        }
    }
}
