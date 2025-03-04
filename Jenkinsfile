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

        stage('provision server') {
            when {
                expression{
                    BRANCH_NAME == "main"
                }
            }
            enviroment {
                AWS_ACCESS_KEY_ID = credientials("jenkins_aws_access_key_id")
                AWS_ACCESS_KEY_ID = credientials("jenkins_aws_secret_access_key")
                TF_VAR_env_prefix = "test"
            }
            steps{
                script {
                    dir('terraform') {
                        sh "terraform init"
                        sh "terraform apply --auto-approve"
                        EC2_PUBLIC_IP = sh(
                            script: "terraform output ec2_public_ip",
                            returnStdout: true
                        ).trim()
                    }
                }
            }
        }


        stage('Deploy') {
            when {
                expression{
                    BRANCH_NAME == "main"
                }
            }
            enviroment {
                DOCKER_CREDS = credientials("docker-hub-repo-creds")
            }
            steps {
                script {
                    echo "Waiting for ec2 to initialize"
                    sleep(time: 90, unit: "SECONDS")

                    echo "deploying docker image to EC2..."
                    echo "${EC2_PUBLIC_IP}"

                    def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME} ${DOCKER_CREDS_USER} ${DOCKER_CREDS_PSW}"
                    def ec2Instance = "ec2-user@${EC2_PUBLIC_IP}"

                    sshagent(['server-ssh-key']) {
                        sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ec2-user"
                        sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ec2-user"
                        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                    }
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
                    sh 'git status'
                    sh 'git branch'
                    sh 'git config --list'

                    withCredentials([string(credentialsId: 'github-creds', variable: 'GIT_TOKEN')]) {
                        sh '''git remote set-url origin https://$GIT_TOKEN@github.com/m-bilalkhan/jenkins_practice_1.git'''
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump"'
                        sh 'git push origin HEAD:refs/heads/main'
                    }

                }
            }
        }
    }
}
