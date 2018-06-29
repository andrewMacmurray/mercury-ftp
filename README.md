# Mercury FTP

[![CircleCI](https://circleci.com/gh/andrewMacmurray/mercury-ftp.svg?style=svg)](https://circleci.com/gh/andrewMacmurray/mercury-ftp)

![1_fkapdcuastrczqakefqvka](https://user-images.githubusercontent.com/14013616/42080037-44c2705e-7b79-11e8-93bc-e608c9255628.png)

Mercury is a partial implementation of an FTP server in Java - (named after the [winged messenger](https://en.wikipedia.org/wiki/Mercury_(mythology)))

## Implemented Commands

https://www.iana.org/assignments/ftp-commands-extensions/ftp-commands-extensions.xhtml

- APPE - Append 
- CWD - Change Working Directory 
- LIST - List 
- NLST - Name List 
- PASS - Password 
- PASV - Passive Mode 
- PORT - Data Port 
- QUIT - Logout 
- RETR - Retrieve 
- STOR - Store 
- STOU - Store Unique 
- USER - User Name


## Get up and running

Clone the repo and make sure you have Java 8 installed

```$xslt
> git clone https://github.com/andrewMacmurray/mercury-ftp
```

Run the tests with

```$xslt
> ./gradew test
```

Build the application with

```$xslt
> ./gradlew jar
```

You may need to run the app with root privileges (as the server opens a socket on port 21)

```$xslt
> sudo java -jar build/libs/mercury-ftp-1.0-SNAPSHOT.jar
```

Alternatively for dev purposes you can change the `commandSocketPort` in `Main.java` to a non privileged port (ports above `1023`)

## Connect to the server

A lightweight client I used to connect to the server was the `ftp` client from [inetutils package](https://www.gnu.org/software/inetutils/). On MacOS install this with

```$xslt
> brew install inetutils
```

Running the server locally you can connect to it using:

```$xslt
> ftp --ipv4 --no-login
> open localhost 21
```

You can view the ftp client commands you can issue using

```$xslt
ftp> help
```

But be aware that not all of them have been implemented yet!

## Infrastructure Setup

### CircleCI / Terraform / AWS

The project has been set up with with a Continuous Deployment pipeline via [CircleCI](https://circleci.com/) and [Terraform](https://www.terraform.io/).

+ CircleCI runs the tests for every commit pushed to Github
+ When a pull request is merged into master CircleCI then runs a terraform script to provision infrastructure and deploy the server
+ Terraform is set up to create and manage AWS resources that include -
    + A single ec2 instance
    + A security group
    + An IAM role for the ec2 instance
    + A managed version of the default VPC

