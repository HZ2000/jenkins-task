pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar-server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy to Tomcat') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                echo 'Deploying to Tomcat...'
                def container = tomcat(
                    credentialsId: 'tomcat-cred', // The ID of your Tomcat credentials in Jenkins
                    url: 'http://localhost:7070', // The URL of your Tomcat server
                    path: '/Microservices-new', // The context path for your application
                    war: "${JENKINS_HOME}/workspace/Task_Final/target/Microservices-new.war" // Path to the WAR file built by your job
                )
                container.deploy()
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed!'
        }
    }
}