def gv //define a variable
pipeline {
    agent any
    parameters {
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'])
        booleanParam(name: 'executeTests', defaultValue: true)
    }
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
        stage('Test') {
            when {
                expression {
                    params.executeTests
                }
            }
            steps {
                echo 'Running tests...'
                // Insert test execution steps here
            }
        }
        stage('Deploy') {
            input {
                message "Select a Eniroment For Deploying..."
                ok "Done"
                parameters {
                    choice(name: 'DEPLOYMENT_SERVER', choices: ['Dev', 'Test', 'Prod'])
                }
            }
            steps {
                script {
                    gv.deployApplication(DEPLOYMENT_SERVER)
                }
            }
        }
    }
}
