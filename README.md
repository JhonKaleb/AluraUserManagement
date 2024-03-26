# Alura User Management
Alura User Management is a Spring Boot application designed to manage users. It utilizes a MySQL database for storage and runs as a containerized application with Docker.

## Prerequisites
Before you begin, ensure you have met the following requirements:

Docker and Docker Compose are installed on your system.
You have basic knowledge of Docker, Spring Boot, and MySQL.

## Project Structure

- src/ - Contains the project source code.

- pom.xml - Maven configuration file.

- Dockerfile - Instructions for building the application's Docker image.

- docker-compose.yml - Docker Compose file to orchestrate the application and its MySQL database.

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

This command starts the services defined in docker-compose.yml, setting up the database and the application. The application will be accessible at http://localhost:8080.

## Volumes
The docker-compose.yml file specifies a volume for MySQL data persistence:

- db-data - Used to persist MySQL database data.

## Ports
The application runs on port 8080, and the MySQL database is accessible on port 3306 on your local machine.

## Additional Information
For more information on Spring Boot, visit the Spring Boot documentation.

For Docker and Docker Compose usage, refer to the Docker documentation.