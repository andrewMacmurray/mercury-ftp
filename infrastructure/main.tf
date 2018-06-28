resource "aws_instance" "mercury" {
  ami = "ami-a36f8dc4"
  instance_type = "t2.micro"
  key_name = "mercury-aws-key"
  iam_instance_profile = "${aws_iam_instance_profile.ec2_s3_access.name}"
  vpc_security_group_ids = ["${aws_security_group.mercury-security.id}"]
  user_data = "${file("boot-sequence.sh")}"

  tags {
    Name = "mercury-terraform"
  }
}

output "public_ip" {
  value = "${aws_instance.mercury.public_ip}"
}
