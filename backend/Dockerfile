# Stage 1: Get the official Instana Agent binary from their registry
FROM icr.io/instana/instana-java-sensor:latest AS instana-sensor

# Stage 2: Build your Spring Boot application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 3: Final Runtime Image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the agent from Stage 1 (Guaranteed to be valid)
COPY --from=instana-sensor /instana-java-sensor.jar /app/instana-agent.jar

# Copy your application from Stage 2
COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar

EXPOSE 8080

# Attach the agent
ENV JAVA_TOOL_OPTIONS="-javaagent:/app/instana-agent.jar"

CMD ["java", "-jar", "app.jar"]
