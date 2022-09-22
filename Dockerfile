FROM openjdk:8-jdk-oraclelinux8

COPY /usr/share/tomcat/.jenkins/workspace/TGHFinancialBE/target/curated-banking-spring-1.0-SNAPSHOT.jar curated-banking-spring-1.0-SNAPSHOT.jar

EXPOSE 8083
CMD ["java","-jar","curated-banking-spring-1.0-SNAPSHOT.jar"]