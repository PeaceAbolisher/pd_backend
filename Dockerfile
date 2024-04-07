# Pull image Java 17
FROM eclipse-temurin:17

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run
