terraform {
  required_providers {
    heroku = {
      source  = "heroku/heroku"
      version = "~> 4.0"
    }
  }

  backend "remote" {
    organization = "sergiovillanueva"

    workspaces {
      name = "organization-api"
    }
  }
}

provider "heroku" {}

variable "jwt_secret" {}
variable "app_name" {}

resource "heroku_app" "app" {
  name   = var.app_name
  region = "us"

  sensitive_config_vars = {
    JWT_SECRET = var.jwt_secret
  }

}

# resource "heroku_addon" "postgres" {
#   app  = heroku_app.app.name
#   plan = "heroku-postgresql:hobby-dev"
# }
