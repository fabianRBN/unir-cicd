pipeline {
    agent {
        label 'docker'
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building stage!'
                sh 'make build'
            }
        }
        stage('Unit tests') {
            steps {
                sh 'make test-unit'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }

        stage('test-api') {
            steps {
                sh 'make test-api'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }

        stage('test-e2e') {
            steps {
                sh 'make test-e2e'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
    }
    post {
        always {
            echo "========always========"
            junit 'results/*_result.xml'
        }
        success{
            echo "========pipeline executed successfully ========"
            mail bcc: '', body: 'Success Build unir', cc: '', from: '', replyTo: '', subject: 'Notification Jenkins', to: 'fabianRBN_95@hotmail.com'
        }
        failure{
            echo "========pipeline execution failed========"
            mail bcc: '', body: 'Faild Build unir', cc: '', from: '', replyTo: '', subject: 'Notification Jenkins', to: 'fabianRBN_95@hotmail.com'

        }
    }
}