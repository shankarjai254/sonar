pipeline {
    agent any
    tools {
        maven 'Maven'
    }

    environment {
        // Telegram credential IDs
        TELEGRAM_TOKEN = credentials('telegram-credentials')  // Ganti dengan ID kredensial Anda
        TELEGRAM_CHAT_ID = credentials('Telegram_ChatID')  // Ganti dengan ID kredensial Anda
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/ZudaPradana/sonar']])
                echo 'Git Checkout Completed'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Tambahkan penundaan untuk memberikan waktu kepada server Spring Boot
                sleep time: 1, unit: 'MINUTES'
                withSonarQubeEnv('sq1') {
                    sh '''mvn clean verify sonar:sonar -Dsonar.projectKey=sonar-analysis -Dsonar.projectName='sonar-analysis' -Dsonar.host.url=http://localhost:9000'''
                    echo 'SonarQube Analysis Completed'
                }
            }
        }
    }

    post {
    success {
        script {
            def TEXT_SUCCESS_BUILD = "Build Success: ${BUILD_URL}"
            def crumb = bat(script: 'curl -u "${JENKINS_USER}:${JENKINS_TOKEN}" -s "http://localhost:9090/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)"', returnStatus: true).trim()
            bat "curl -u ${JENKINS_USER}:${JENKINS_TOKEN} --location --request POST 'http://localhost:9090/job/sonar/${BUILD_NUMBER}/input/YourInput/submit?delay=0sec' --header '${crumb}' --form text='${TEXT_SUCCESS_BUILD}' --form chat_id='${TELEGRAM_CHAT_ID}'"
        }
    }
    failure {
        script {
            def TEXT_FAILURE_BUILD = "Build Failure: ${BUILD_URL}"
            def crumb = bat(script: 'curl -u "${JENKINS_USER}:${JENKINS_TOKEN}" -s "http://localhost:9090/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)"', returnStatus: true).trim()
            bat "curl -u ${JENKINS_USER}:${JENKINS_TOKEN} --location --request POST 'http://localhost:9090/job/sonar/${BUILD_NUMBER}/input/YourInput/submit?delay=0sec' --header '${crumb}' --form text='${TEXT_FAILURE_BUILD}' --form chat_id='${TELEGRAM_CHAT_ID}'"
        }
    }
}

}
