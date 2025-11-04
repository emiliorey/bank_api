# Use the official OpenJDK 17 image as a base image
FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the application JAR file
COPY target/bank_api-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose the port on which the application will run
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "application.jar"]