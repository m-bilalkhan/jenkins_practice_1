// @Library("jenkins-shared-library")
//This above is how we import a global shared library in jenkins

//If we want to use shared library but specific to a project we can use below syntax to import a library
library identifier: 'jenkins-shared-library-project-base@main', retriever: modernSCM(
    [
        $class: 'GitSCMSource',
        remote: 'https://github.com/m-bilalkhan/jenkins-shared-library-project.git'
        // credientialsId: '' for creds
    ]
)
//tttt

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
                    buildImage('mbilalkhan/practice-repo:2.0.0')
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
