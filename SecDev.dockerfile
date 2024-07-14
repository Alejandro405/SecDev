FROM openjdk:17-slim


RUN apt-get update && apt-get install -y maven

WORKDIR /app


# Copy the jar file from the target directory to the container
COPY target/SecDev*.jar /app/app.jar
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

