# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Install curl to download the agent
RUN apt-get update && apt-get install -y curl

# Download the Instana Agent JAR directly from GitHub Releases
# Using -L to follow redirects and -o to name the file
RUN curl -L -o instana-agent.jar https://github.com/instana/instana-java-sensor/releases/download/v1.2.14/instana-java-sensor-1.2.14.jar

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the agent and the built jar from the build stage
COPY --from=build /app/instana-agent.jar /app/instana-agent.jar
COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar

EXPOSE 8080

# Attach the agent
ENV JAVA_TOOL_OPTIONS="-javaagent:/app/instana-agent.jar"

CMD ["java", "-jar", "app.jar"]
