pipeline {
    agent any

     environment {

            DOCKER_PASSWORD = credentials("af01d311-cfe5-4bbf-9032-3f0c1f3cdfb2")
            GITHUB_TOKEN = credentials("0607d1ee-5e83-4620-a56c-80812cf117c4")
        }

    stages {
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
                    }

                    sh 'echo $DOCKER_PASSWORD'
                    
                    sh "docker login docker.io -u lsbogdan -p $DOCKER_PASSWORD"

                    sh "docker build -t lsbogdan/hello-img:$IMAGE_VERSION ."


                    sh "git tag ${env.IMAGE_TAG}"
                    sh "git push https://$GITHUB_TOKEN@github.com/lsbogdan/service.git ${env.IMAGE_TAG}"
              }
        }
      stage('Compose_IMAGE') {
            steps{
                script{
                    env.IMAGE_TAG="${env.IMAGE_TAG} docker-compose up -d hello"
                }
            }
      }
    }
}
