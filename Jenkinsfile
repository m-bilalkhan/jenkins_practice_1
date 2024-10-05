pipeline {
    agent any
    parameters {
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'])
        booleanParam(name: 'executeTests', defaultValue: true)
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building the project...'
                // Insert build steps here
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
            steps {
                echo 'Deploying...'
                echo "deploying version ${params.VERSION}"
                // Insert deployment steps here
            }
        }
    }
}
