FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

ARG JAR_FILE

EXPOSE 8080

COPY target/SNB-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]