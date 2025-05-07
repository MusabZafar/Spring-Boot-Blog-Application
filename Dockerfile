FROM eclipse-temurin:17

LABEL mentainer="musabzafar03@gmail.com"
WORKDIR /app
COPY target/SpringBoot-Blog-App-0.0.1-SNAPSHOT.jar /app/SpringBoot-Blog-App.jar
ENTRYPOINT["java", "-jar", "SpringBoot-Blog-App.jar"]