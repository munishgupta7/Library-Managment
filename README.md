#### Library Management System

#### Installation

Ensure that Java 8 and Maven 3.2 are installed Clone this
repo:https://gitlab.com/mungali/springboot-rest-library-management-system

#### How to Build the Project - Use the below maven command

mvn clean install

#### Running the Spring Boot app

Navigate to the directory into which you cloned the repo and execute this: mvn spring-boot:run

Once started you can access the APIs/Swagger UI on port 8082, e.g. http://localhost:8082/swagger-ui/index.html

The port number can be changed by editing the port property in src/main/resources/application.properties

#### In Swagger UI test the rest API

#### Create Book API Inputs

{ "author": "test1", "description": "test1", "isbn": "test1", "tag1": "test1", "tag2": "test1", "title": "test1" }

#### Update Book API Inputs

{ "author": "jack", "description": "power of focus", "isbn": "test1", "tag1": "power of focus", "tag2": "Confidence", "
title": "The 7 habits of effective people" }

In the path variable, provide the ISBN value : test1

#### Get All Books API - No Input click execute

Below is the output

{ "author": "jack", "description": "power of focus", "isbn": "test1", "tag1": "power of focus", "tag2": "Confidence", "
title": "The 7 habits of effective people" }

#### Get Book by ISBN API - Input

In the path variable, provide the ISBN value : test1

Below is the output

{ "author": "jack", "description": "power of focus", "isbn": "test1", "tag1": "power of focus", "tag2": "Confidence", "
title": "The 7 habits of effective people" }

#### Delete Book by ISBN API - Input

In the path variable, provide the ISBN value : test1

After delete again hit Get All API, there will be no data or result

#### Search Book by Title API - Input

Add the book - Create API

{ "author": "test1", "description": "test1", "isbn": "test1", "tag1": "test1", "tag2": "test1", "title": "test1" }

Then use the search APi to get the record by title

In the path variable, provide the title value : test1

Below is the output

{ "author": "test1", "description": "test1", "isbn": "test1", "tag1": "test1", "tag2": "test1", "title": "test1" }