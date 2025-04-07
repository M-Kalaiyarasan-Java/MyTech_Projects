# Use lightweight OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy your actual JAR file without renaming
COPY target/hrms-0.0.1-SNAPSHOT.jar hrms-0.0.1-SNAPSHOT.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the app using same name
CMD ["java", "-jar", "hrms-0.0.1-SNAPSHOT.jar"]
