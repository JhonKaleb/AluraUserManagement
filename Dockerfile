# Build stage
FROM maven:3-amazoncorretto-21 AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean package -Dmaven.test.skip

# Run stage
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/UserManagement-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
