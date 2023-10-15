pipeline {
    agent any

    environment {
        TOMCAT_URL = 'http://localhost:7070'  // URL of your Tomcat server
        TOMCAT_CREDENTIALS = credentials('tomcat-cred')  // Jenkins credentials ID for Tomcat
    }

    tools {
        maven 'Maven-3.9.5'
    }

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

        stage('Archieve Artifacts') {
            steps {
                archiveArtifacts artifacts: 'product-service/target/*.war'
            }
        }

        stage('Deploy to Tomcat') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }

            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat-cred', url: '${env.TOMCAT_URL}')], contextPath: '/product-service', war: 'product-service/target/*.war'
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed!'
        }
    }
}
