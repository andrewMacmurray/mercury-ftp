locals {
  all_ips = "0.0.0.0/0"
}

resource "aws_security_group" "mercury-security" {
  name = "mercury-security-group"
  vpc_id = "${aws_default_vpc.default.id}"

  ingress {
    from_port = 21
    protocol = "tcp"
    to_port = 21
    cidr_blocks = ["${local.all_ips}"]
  }

  ingress {
    from_port = 2022
    protocol = "tcp"
    to_port = 2026
    cidr_blocks = ["${local.all_ips}"]
  }

  ingress {
    from_port = 22
    protocol = "tcp"
    to_port = 22
    cidr_blocks = ["${local.all_ips}"]
  }

  egress {
    from_port = 0
    protocol = "-1"
    to_port = 0
    cidr_blocks = ["${local.all_ips}"]
  }
}

resource "aws_default_vpc" "default" {
  tags {
    Name = "Default VPC"
  }
}
