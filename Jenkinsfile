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
        stage('Build') {
            steps {
                script {
                    gv.buildApplication()
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    gv.deployApplication()
                }
            }
        }
    }
}
