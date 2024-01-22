FROM eclipse-temurin:17
WORKDIR /app
COPY target/ReelRemarks.jar /app/ReelRemarks.jar
EXPOSE 8080
CMD ["java", "-jar", "ReelRemarks.jar"]