# Import base image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Expose port 8080
EXPOSE 8080

# Assign the jar file path
ARG JAR_PATH=target/quizzical-0.0.1-SNAPSHOT.jar

# Copy the dependencies package to container
COPY ${JAR_PATH} app.jar

CMD ["java", "-jar", "app.jar"]