# Use an official Temurin (AdoptOpenJDK) runtime as a parent image
FROM eclipse-temurin:17-jdk

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the Maven wrapper files (pom.xml and the wrapper scripts)
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY src src
COPY pom.xml .

# Grant execution permissions to the Maven wrapper script
RUN chmod +x mvnw

# Copy the project source code
COPY src src

# Build the application
RUN ./mvnw install -DskipTests

# Expose the port your application runs on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "target/ReelRemarks.jar"]
