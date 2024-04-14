# pull image Java 17
FROM eclipse-temurin:17

# install Maven
RUN apt-get update && apt-get install -y maven

# set the working directory inside the container
WORKDIR /backend

# copy the content of the current directory into the work directory inside the container
COPY . .

# run the app
CMD mvn spring-boot:run
