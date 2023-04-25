pipeline {
    agent any

     environment {

            DOCKER_PASSWORD = credentials("f6475a88-da1d-4b1a-bbe0-4e0e90741114")
            GITHUB_TOKEN = credentials("6486d247-483a-46a1-90f8-123278354784")
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

                    sh "docker build -t lsbogdan/hello-img:${env.IMAGE_TAG} ."

                    sh "docker login docker.io -u lsbogdan -p $DOCKER_PASSWORD"
                    
                    sh "docker push lsbogdan/hello-img:${env.IMAGE_TAG}"


                    sh "git tag ${env.IMAGE_TAG}"
                    sh "git push https://$GITHUB_TOKEN@github.com/TeamOltenia/service.git ${env.IMAGE_TAG}"
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
