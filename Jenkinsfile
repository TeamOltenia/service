pipeline {
    agent any

    environment {
        DOCKER_TOKEN = credentials("18c4d6eb-2986-4fe6-b6ee-bd772e2d6fc7")
        GITHUB_TOKEN = credentials("6486d247-483a-46a1-90f8-123278354784")
    }

    stages {

         stage('Install Docker Compose') {
              steps {
                   sh 'curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose'
                   sh 'chmod +x /usr/local/bin/docker-compose'
              }
         }

        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Tag image') {
            steps {
                script {
                    sh([script: 'git fetch --tag', returnStdout: true]).trim()
                    env.MAJOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 1', returnStdout: true]).trim()
                    env.MINOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2', returnStdout: true]).trim()
                    env.PATCH_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3', returnStdout: true]).trim()
                    env.IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
                    env.LATEST_TAG = "latest"
                }

                sh "docker build -t lsbogdan/hello-img:${env.IMAGE_TAG} ."
                sh "docker tag lsbogdan/hello-img:${env.IMAGE_TAG} lsbogdan/hello-img:${env.LATEST_TAG}"
                sh "docker login -u lsbogdan -p $DOCKER_TOKEN"
                sh "docker push lsbogdan/hello-img:${env.IMAGE_TAG}"
                sh "docker push lsbogdan/hello-img:${env.LATEST_TAG}"

                sh "git tag ${env.IMAGE_TAG}"
                sh "git push https://$GITHUB_TOKEN@github.com/TeamOltenia/service.git ${env.IMAGE_TAG}"
            }

    stage('Compose') {
         steps {
            script {
                env.IMAGE_TAG="${env.IMAGE_TAG}"
                sh "export IMAGE_TAG=${env.IMAGE_TAG}"
                sh "/usr/local/bin/docker-compose up -d hello"
                }
            }
        }
}
