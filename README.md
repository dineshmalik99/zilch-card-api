# zilch-card-api

## Introduction

The purpose of this api is to add card details sent in request in DB.
for test purposes we are using in-memory DB H2, so that we can run this application on any system
without requiring system to have DB installed.

This api has below 2 endpoints:

**POST** : **/zilch/cards** , endpoint to save created Card Details in DB , details sent in body. 

**GET** : **/zilch/cards/card** , endpoint to retrieve save card details using 2 request header.

for more reference on contract plz refer **zilch-card-api.yaml** file in **src/main/resources** file

## Development Guide

This API is developed using the contract first approach, where we have finalized the contract first
and then have our controller test cases ready.
Required classes including model and endpoints are created using **openapi-generator-maven-plugin**

### Run Guide
This is simple spring-boot application which requires java 16.
To run this application locally you need either Java 16 or docker with Maven.

If you have Java 16 on system please run this as spring-boot application, 
you can use below maven command on terminal also after going in project root directory:

**mvn spring-boot:run**

If you don't have Java 16 on system please use Docker, 
docker command are below, run below commands after going in project root directory:

**docker build -t card-api .**

**docker run --name card-api -it -p 8080:8080 card-api:latest**

**Note:** for running docker first create binaries and jar file of project using maven command:

**mvn clean install**

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.3/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/#boot-features-developing-web-applications)

