# SpringBoot To-Do List

A simple to-do list API with Basic Auth, written in Spring Boot.
It's currently hosted in [Render](https://render.com) and is accessible at https://springboot-todolist.onrender.com/

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

The things you need before installing the software.

- [JDK 17](https://www.oracle.com/br/java/technologies/downloads/#java17)
- [Maven](https://maven.apache.org/download.cgi)

### Installation

A step by step guide that will tell you how to get the development environment up and running.

```
$ git clone https://github.com/emiliosanches/SpringBootTodoList.git
$ cd SpringBootTodoList

# Compile and start using maven CLI
$ mvn clean install
$ java -jar ./target/todolist-1.0.0.jar   # JAR file name may change

# Or start using your IDE tools
```

## Usage

The app exposes the port 8080 (free this port or change the app's port in src/main/resources/application.properties) using HTTP protocol.
You can use the app by sending HTTP requests to the open port. [Here](endpoints.md) is a list of the available endpoints.

## Deployment

The [Dockerfile](Dockerfile) in this project is designed for deploying the application in [Render](https://render.com).
If you are trying to deploy it to another hosting provider, you may need to do some changes.
