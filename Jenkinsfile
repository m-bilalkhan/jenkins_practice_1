@Library("jenkins-shared-library")
def gv //define a variable
pipeline {
    agent any
    stages {
        stage("Init") {
            steps{
                script {
                    gv = load "script.groovy" // loading script and assigining to variable
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    gv.testApplication()
                }
            }
        }
        stage('Build') {
            when {
                expression{
                    BRANCH_NAME == "main"
                }
            }
            steps {
                script {
                    buildImage()
                }
            }
        }
        stage('Deploy') {
            when {
                expression{
                    BRANCH_NAME == "main"
                }
            }
            steps {
                script {
                    deployApplication()
                }
            }
        }
    }
}
