FROM tomcat:9.0.65-jre8

COPY /usr/share/tomcat/.jenkins/workspace/TGHFinancialBE/target/curated-banking-spring-1.0-SNAPSHOT.jar target/curated-banking-spring.jar 