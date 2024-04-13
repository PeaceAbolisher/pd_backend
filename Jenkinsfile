pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // build docker image
                script {
                    docker.build('a21280323/pd-backend:latest')
                }
            }
        }
        stage('Deploy') {
            steps {
                // deploy using docker compose; recreate containers and run in detached mode
                script {
                    dockerCompose(buildFile: 'docker-compose.yml', upOptions: '--force-recreate -d')
                }
            }
        }
    }
}

