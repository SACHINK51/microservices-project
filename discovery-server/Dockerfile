FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/discovery-server-*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]
