FROM maven:3.8.5-openjdk-18 AS build
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim

COPY --from=build /target/SNB-0.0.1-SNAPSHOT.jar sps.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sps.jar"]
