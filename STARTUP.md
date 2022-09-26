# Setting up Docker Containerization on EC2 Instance for TGH Back-End
## Table of contents
* [AWS (EC2)](#aws-ec2)
* [Jenkins](#jenkins)

## AWS (EC2)
* Install(Tomcat/Jenkins, Maven, Docker)
	- sudo amazon-linux-extras install tomcat9 -y
	- cd /usr/share/tomcat/webapps/
	- sudo wget https://get.jenkins.io/war-stable/2.346.3/jenkins.war
* Manually install Maven
	- Maven (https://maven.apache.org/download.cgi)
	- Maven used in our project build (apache-maven-3.8.6-bin.tar.gz)
	- on EC2 wget https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz
	- tar -xf apache-maven-3.8.6-bin.tar
	- yum install docker	
		
* Start services and add tomcat to docker usergroup
	- sudo systemctl start tomcat
	- sudo systemctl start docker
	- sudo usermod -aG docker tomcat
		
	- For EC2's with limited resources use swap space
		- https://aws.amazon.com/premiumsupport/knowledge-center/ec2-memory-swap-file/
			- Our instance
				- sudo dd if=/dev/zero of=/swapfile bs=128M count=8
				- sudo chmod 600 /swapfile
				- sudo mkswap /swapfile
				- sudo swapon /swapfile
				- sudo swapon -s
				- sudo vi /etc/fstab
				- vim -> /swapfile swap swap defaults 0 0
					
## Jenkins
* Jenkins (Instance public IP Address):8080/jenkins)
	- Create FreeStyle Project
	- Link GitHub Project URL: (https://github.com/project)
	- Branches to Build (*/main)
	- Build Triggers (GitHub hook trigger for GITScm polling)
	- Source Code Management
	- Repository URL: (https://github.com/project.git)
		
* Jenkins Main Dashboard
	- *Manage Jenkins
	- Global Tool Configuration
	- Maven Installations (add Maven)
	- Give it a name and under MAVEN_HOME give it the path where maven was installed
		- *inside maven installation on the EC2 we can type (pwd) to get the direct path

* Build
	- Add Build step
		- Invoke top-level Maven targets
		- Maven Version (the name that was given in Maven Installations)
		- Goals:
			- clean
			- package (*optional to skip test include -package-Dmaven.test.skip)
			
	- Add Build step
		- Execute Shell
		- docker build -t tghmvc .
		- docker run -d -p 8083:8080 --name tghmvc_cont tghmvc