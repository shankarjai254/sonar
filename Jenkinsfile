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
        CURL_PATH = 'C:\\curl-8.4.0_7-win64-mingw\\curl-8.4.0_7-win64-mingw\\bin\\curl.exe'
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

                withSonarQubeEnv('sq1') {
                    sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=sonar-analysis -Dsonar.projectName="sonar-analysis" -Dsonar.host.url=http://localhost:9000'
                    echo 'SonarQube Analysis Completed'
                }
            }
        }
    }
    post {
    always {
        script {
            try {
                sh 'curl --location --request POST "https://api.telegram.org/bot6356002838:AAE48btgtfdOlX-Zz0RYI9tC7Rhg4a43Sf4/sendMessage" --form "text=Hello Zydd" --form "chat_id=725260461"'
            } catch (Exception e) {
                currentBuild.result = 'FAILURE'
                echo "Error: ${e.message}"
            }
        }
    }
}


}
