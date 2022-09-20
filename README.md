# TGHFinancialBE
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Getting Started](#getting-started)

## General info
This is the backend portion of the TGH Financial banking application project. This project was generated using Eclipse IDE for Enterprise Java and Web Developers, Version: 2022-06 (4.24.0)
	
## Technologies
Project is created with:
* h2: 2.1.214
* Java: 1.8
* lombok: 1.18.24
* maven: 3.2.0
* spring-boot-devtools: 2.7.3
* spring-boot-starter-actuator: 2.7.3
* spring-boot-starter-data-jpa: 2.7.3
* spring-boot-starter-mail: 2.7.3
* spring-boot-starter-test: 2.7.3
* spring-boot-starter-web: 2.7.3
* spring-security-core: 5.7.3
	
## Getting started

Properties in the application.yml should reflect the following:
```
server:
  port: 8080
spring:
  jpa:
    hibernate:
      ddl-auto: create-drop
    database-platform: org.hibernate.dialect.H2Dialect
    defer-datasource-initialization: true
  datasource:
    url: jdbc:h2:mem:memdb
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  h2:
    console.enabled: true
  mail: 
    host: smtp.office365.com
    port: 587  
    username: TGHFinancial@outlook.com
    password: 1drowassP
    protocol: smtp
    tls: true
    properties.mail.smtp:
        auth: true
        starttls.enable: true
        ssl.trust: smtp.office365.com
```

To run this project, choose "Run As Spring Boot App".
