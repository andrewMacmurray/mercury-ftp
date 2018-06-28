resource "aws_instance" "mercury" {
  ami = "ami-a36f8dc4"
  instance_type = "t2.micro"
  key_name = "mercury-aws-key"
  iam_instance_profile = "${aws_iam_instance_profile.ec2_s3_access.name}"
  vpc_security_group_ids = ["${aws_security_group.mercury-security.id}"]
  user_data = "${data.template_file.boot_sequence.rendered}"

  tags {
    Name = "mercury-terraform"
  }
}

data "template_file" "boot_sequence" {
  template = "${file("./boot-sequence.sh")}"
}

output "public_ip" {
  value = "${aws_instance.mercury.public_ip}"
}
