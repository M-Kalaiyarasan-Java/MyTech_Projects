FROM openjdk:17-jdk-slim

WORKDIR /app

COPY hrms-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9000

CMD ["java", "-jar", "app.jar"]
