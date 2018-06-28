terraform {
  backend "s3" {
    bucket = "mercury-terraform"
    key = "terraform-state"
    region = "eu-west-2"
  }
}

provider "aws" {
  region = "eu-west-2"
}

