# Use an official Maven image as a build stage
FROM maven:3.8.5-openjdk-18 AS build

# Set the working directory
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Build the application (skip tests)
RUN mvn clean package -DskipTests

# Create the final image using a minimal JRE image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/SPS-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]