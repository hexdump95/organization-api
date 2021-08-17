pipeline {
  agent any

  tools {
    maven 'maven-3.8.1'
  }

  environment {
    HEROKU_APP_NAME = 'organization-spring-api'
  }

  stages {
    stage('build jar') {
      steps {
        sh 'mvn clean package -DskipTests'
      }
    }

    stage('run tests and SonarCloud') {
      environment {
        SONAR_TOKEN = credentials("36ae2fe9-e961-4e19-bb13-b6edc1b8fd91")
      }
      steps {
        sh 'mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=hexdump95_organization-api'
      }
    }

    stage('prepare heroku app') {
      steps {
        sh 'echo app_name=\\"$HEROKU_APP_NAME\\" > .auto.tfvars'
        sh 'terraform init'
        sh 'terraform plan'
        sh 'terraform apply -auto-approve'
      }
    }

  }
}
