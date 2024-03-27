# Alura User Management

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-%2300758F.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

Alura User Management is a Spring Boot application designed to manage users. It utilizes a MySQL database for storage and runs as a containerized application with Docker.

Tasks board: https://trello.com/b/DihjAjwJ/alura-user-management-system

## Prerequisites
Before you begin, ensure you have met the following requirements:

Docker and Docker Compose are installed on your system.
You have basic knowledge of Docker, Spring Boot, and MySQL.

## Project Structure

- src/ - Contains the project source code.

- pom.xml - Maven configuration file.

- Dockerfile - Instructions for building the application's Docker image.

- docker-compose.yml - Docker Compose file to orchestrate the application and its MySQL database.

### Database schema ERD
[![ERD]()

## Setup

To set up the project for development or testing purposes, follow these steps:

Clone the repository to your local machine:
``` bash
git clone https://github.com/JhonKaleb/AluraUserManagement.git
```
Navigate to the project directory:
```bash
cd AluraUserManagement
```

## Building the Application
To build the Docker image for the Alura User Management application, run:
``` bash
docker build -t alura-user-management .
```
This command builds the application using Maven and packages it into a Docker image named alura-user-management.

## Running the Application
To start the application along with its MySQL database, use Docker Compose:

``` bash
docker-compose up
```

This command starts the services defined in docker-compose.yml, setting up the database and the application.

- The application will be accessible at http://localhost:8080.
- The MySQL database will be accessible at localhost:3306.

*Makes sure these ports are available on your local machine before running the command.*

## Endpoints

For endpoits, import into Postman the file `Alura User Management.postman_collection.json` in the root of the project.

Or use the link: https://app.getpostman.com/join-team?invite_code=fcdbe9ee4e9d88db7e7f3010203a5844&target_code=21f184138f51a67d554dd9250c9fd44d

## Volumes
The docker-compose.yml file specifies a volume for MySQL data persistence:

- db-data - Used to persist MySQL database data.

## Ports
The application runs on port 8080, and the MySQL database is accessible on port 3306 on your local machine.

## Additional Information
For more information on Spring Boot, visit the Spring Boot documentation.

For Docker and Docker Compose usage, refer to the Docker documentation.