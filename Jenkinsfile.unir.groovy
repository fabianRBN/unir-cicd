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
            script{
                 // Extraer el número de build
                def buildNumber = currentBuild.number
                def nombreBuild = env.JOB_NAME
                
                echo "========pipeline executed successfully ========"
                mail bcc: '', body: 'Success Build ', cc: '', from: '', replyTo: '', subject: "Build Exitoso: #${buildNumber} - ${nombreBuild}", to: 'fabianRBN_95@hotmail.com'
            }
        }
           
        failure{

            script{
                 // Extraer el número de build
                def buildNumber = currentBuild.number
                def nombreBuild = env.JOB_NAME
                
                echo "========pipeline execution failed========"
                mail bcc: '', body: 'Faild Build ', cc: '', from: '', replyTo: '', subject: "Build Exitoso: #${buildNumber} - ${nombreBuild}", to: 'fabianRBN_95@hotmail.com'
            }
        }
        unstable{
            script{
                 // Extraer el número de build
                def buildNumber = currentBuild.number
                def nombreBuild = env.JOB_NAME
                
                echo "========pipeline execution failed========"
                mail bcc: '', body: 'Faild Build ', cc: '', from: '', replyTo: '', subject: "Build Exitoso: #${buildNumber} - ${nombreBuild}", to: 'fabianRBN_95@hotmail.com'
            }
        }
    }
}