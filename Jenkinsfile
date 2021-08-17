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
