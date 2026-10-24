
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app
COPY target/devmode-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
