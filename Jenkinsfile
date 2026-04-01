pipeline {
    agent { label 'jenkins_agent' }

    stages {
        stage('Prepare Environment') {
            steps {
                script {
                    sh 'chmod +x ./gradlew'
                }
            }
        }
        parallel {
            stage('Checkstyle Main') {
                steps {
                    script {
                        sh './gradlew checkstyleMain -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop"'
                    }
                }
            }

            stage('Checkstyle Test') {
                steps {
                    script {
                        sh './gradlew checkstyleTest -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop"'
                    }
                }
            }
        }
        stage('Compile') {
            steps {
                script {
                    sh './gradlew compileJava -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop"'
                }
            }
        }
        parallel {
            stage('Test') {
                steps {
                    script {
                        sh './gradlew test -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop"'
                    }
                }
            }
            stage('JaCoCo Report') {
                steps {
                    script {
                        sh './gradlew jacocoTestReport -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop"'
                    }
                }
            }
            stage('JaCoCo Verification') {
                steps {
                    script {
                        sh './gradlew jacocoTestCoverageVerification -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop"'
                    }
                }
            }
            stage('Docker Build') {
                steps {
                   sh 'docker build -t job4j_devops .'
                }
            }
            stage('Update DB') {
                steps {
                    script {
                        sh './gradlew update -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop"'
                    }
                }
            }
        }

        stage('Check Git Tag') {
            steps {
                script {
                    // Получаем текущий тег, если он есть
                    def gitTag = sh(script: 'git describe --tags --exact-match 2>/dev/null || true', returnStdout: true).trim()

                    // Если тег существует, публикуем образ
                    if (gitTag) {
                        echo "Tag found: ${gitTag}. Proceeding with Docker build."

                        def imageName = "192.168.0.200:8082/job4j_devops:${gitTag}"

                        withCredentials([usernamePassword(
                          credentialsId: 'nexus-docker-creds',
                          usernameVariable: 'NEXUS_USER',
                          passwordVariable: 'NEXUS_PASS'
                        )]) {
                            sh """
                              echo "\$NEXUS_PASS" | docker login 192.168.0.200:8082 -u "\$NEXUS_USER" --password-stdin
                              docker build -t ${imageName} .
                              docker push ${imageName}
                              docker logout 192.168.0.200:8082
                            """
                        }
                    } else {
                        echo "No Git tag found. Skipping Docker build."
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                def buildInfo = "Build number: ${currentBuild.number}\n" +
                                "Build status: ${currentBuild.currentResult}\n" +
                                "Started at: ${new Date(currentBuild.startTimeInMillis)}\n" +
                                "Duration so far: ${currentBuild.durationString}"
                telegramSend(message: buildInfo)
            }
        }
    }

}
