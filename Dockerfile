# Use an official OpenJDK runtime as a base image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your local machine to the container
COPY target/ReelRemarks.jar /app/ReelRemarks.jar

# Expose the port your app will run on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "ReelRemarks.jar"]