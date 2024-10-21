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
    tools {
        nodejs 'NodeJS' // or the name you gave the NodeJS installation
    }
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
        stage('Increment Version') {
            when {
                expression{
                    BRANCH_NAME == "main"
                }
            }
            steps {
                script {
                    echo "Incrementing version"
                    dir('app') {
                        sh "npm version patch"
                        def version = sh(script: "grep '\"version\"' package.json | sed -E 's/.*\"([^\"]+)\".*/\\1/'", returnStdout: true).trim() 
                        env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                    }
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
                    buildImage("mbilalkhan/practice-repo:$IMAGE_NAME")
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
        stage('Commit package.json back to github') {
            when {
                expression{
                    BRANCH_NAME == "main"
                }
            }
            steps {
                script {
                    sh 'git config --g user.email "Jenkisuser@test.com"'
                    sh 'git config --g user.name "Jenkis"'

                    sh 'git status'
                    sh 'git branch'
                    sh 'git config --list'

                    sh 'git remote set-url origin https://github.com/m-bilalkhan/jenkins_practice_1.git'
                    sh 'git add .'
                    sh 'git commit -m "ci: version bump"'
                    sh 'git push origin HEAD:jenkins_practice_1'
                }
            }
        }
    }
}
