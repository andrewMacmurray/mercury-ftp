resource "aws_iam_instance_profile" "ec2_s3_access" {
  name = "ec2_s3_access"
  role = "${aws_iam_role.ec2_role.name}"
}

resource "aws_iam_role" "ec2_role" {
  name = "ec2_role"
  assume_role_policy = "${file("policies/assume-role.json")}"
}

resource "aws_iam_policy_attachment" "s3-attach" {
  name = "s3-attach"
  roles = ["${aws_iam_role.ec2_role.name}"]
  policy_arn = "${aws_iam_policy.mercury_bucket_access.arn}"
}

resource "aws_iam_policy" "mercury_bucket_access" {
  name = "mercury_bucket_access"
  policy = "${file("policies/s3-access.json")}"
}
