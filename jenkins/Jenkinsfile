pipeline {
    agent any

    environment {
        POM_VERSION = ""
    }

    stages {
        stage('Build') {
            agent {
                docker { image 'maven' }
            }
            steps {
                sh "mvn package -Dmaven.test.skip=true"
                script {
                    pom = readMavenPom file: 'pom.xml'
                    POM_VERSION = pom.version
                }
                stash 'source'
            }
        }

        stage('Docker build') {
            steps {
                unstash 'source'
                sh "docker build . -t netodevel/netodevel-api:${POM_VERSION}"
            }
        }

        stage('Docker push to registry') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'docker_hub_pat',variable: 'dockerHubPat')]) {
                        sh "docker login -u netodevel -p ${dockerHubPat}"
                        sh "docker push netodevel/netodevel-api:${POM_VERSION}"
                    }
                }
            }
        }

        stage('Apply Kubernetes files') {
            steps {
                withKubeConfig([credentialsId: 'kind-cluster-config', serverUrl: 'https://127.0.0.1:35433']) {
                  sh 'kubectl apply -f k8s'
                  sh 'kubectl rollout restart deployment netodevel-api'
                }
            }
        }
    }
}
