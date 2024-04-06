# Image with Java 17
FROM eclipse-temurin:17

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/backend-0.0.1.jar /app/backend.jar

# Expose the port that the Spring Boot application uses (default is 8080)
EXPOSE 8080

# Specify the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "backend.jar"]
