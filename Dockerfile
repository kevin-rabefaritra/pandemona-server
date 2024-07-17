FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/pandemona-0.0.1-SNAPSHOT.jar pandemonium-server.jar

EXPOSE 443

ENTRYPOINT ["java", "-jar", "pandemonium-server.jar"]