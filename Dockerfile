# Stage 1: Build stage
FROM maven:3.8.4-openjdk-17-slim AS build

# Copy Maven files for dependency resolution
COPY pom.xml ./
COPY .mvn .mvn

# Copy application source code
COPY src src

# Build the project and create the executable JAR
RUN mvn clean install

# Stage 2: Run stage
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR jpastreamer

# Copy the JAR file from the build stage
COPY --from=build target/*.jar /app/jpastreamer.jar


# Set the entrypoint command for running the application
ENTRYPOINT ["java", "-jar", "/app/jpastreamer.jar"]