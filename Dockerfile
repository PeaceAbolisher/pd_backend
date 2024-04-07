# Pull image with Java 17
FROM eclipse-temurin:17

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/backend-0.0.1.jar /app/backend.jar

# Specify the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "backend.jar"]
