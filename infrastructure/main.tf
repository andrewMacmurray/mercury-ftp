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

resource "aws_instance" "mercury" {
  ami = "ami-a36f8dc4"
  instance_type = "t2.micro"
  key_name = "mercury-aws-key"
  iam_instance_profile = "ec2-s3-access"
  vpc_security_group_ids = ["${aws_security_group.mercury-security.id}"]
  user_data = "${data.template_file.boot_sequence.rendered}"

  tags {
    Name = "mercury-terraform"
  }
}

data "template_file" "boot_sequence" {
  template = "${file("./boot-sequence.sh")}"
}

resource "aws_security_group" "mercury-security" {
  name = "mercury-security-group"

  ingress {
    from_port = 21
    protocol = "tcp"
    to_port = 21
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 2022
    protocol = "tcp"
    to_port = 2026
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22
    protocol    = "tcp"
    to_port     = 22
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    protocol = "-1"
    to_port = 0
    cidr_blocks = ["0.0.0.0/0"]
  }

}

output "public_ip" {
  value = "${aws_instance.mercury.public_ip}"
}
