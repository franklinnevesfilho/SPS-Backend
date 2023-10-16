FROM maven:latest AS build
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:18.0.2-jdk-slim

COPY --from=build /target/SNB=0.0.1-SNAPSHOT.jar sps.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sps.jar"]