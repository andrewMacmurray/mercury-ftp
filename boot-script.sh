#!/usr/bin/env bash

sudo yum update -y
sudo yum install java-1.8.0 -y
sudo yum remove java-1.7.0-openjdk -y

