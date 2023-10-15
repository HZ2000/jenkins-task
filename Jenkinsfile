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

        stage('Deploy to Tomcat') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }

            steps {
                script {
                    def warFile = "${JENKINS_HOME}/workspace/Task_Final/product-service/target/*.war"
                    if (warFile != null) {
                        echo "Deploying ${warFile} to Tomcat..."

                        def tomcat = tomcat9(credentialsId: "${env.TOMCAT_CREDENTIALS}", url: "${env.TOMCAT_URL}")
                        tomcat.deploy contextPath: '/your-app', war: warFile
                    } else {
                        error 'No WAR file found to deploy!'
                    }
                }
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed!'
        }
    }
}
