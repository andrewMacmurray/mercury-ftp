#!/usr/bin/env bash

# Update system
sudo yum update -y

# Install Java 8 / uninstall Java 7
sudo yum install java-1.8.0 -y
sudo yum remove java-1.7.0-openjdk -y

# Create directory for user files
USER_ROOT_DIR=ftp

cd /home/ec2-user
mkdir $USER_ROOT_DIR

# Copy Jar from s3
aws s3 cp s3://mercury-terraform/build-artifacts/mercury-ftp-1.0-SNAPSHOT.jar mercury.jar

# Get Instance's public IP address
PUBLIC_IP=$(curl http://169.254.169.254/latest/meta-data/public-ipv4)

# Run server
sudo java -jar mercury.jar $PUBLIC_IP $USER_ROOT_DIR
