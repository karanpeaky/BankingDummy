# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Install curl
RUN apt-get update && apt-get install -y curl

# Download the REAL Instana Java Sensor binary
# We add a random query string (?cb=...) to bypass any Render/ISP caching
RUN curl -L -o instana-agent.jar "https://artifact-public.instana.io/artifactory/rel-oss-repo/com/instana/instana-java-sensor/1.2.14/instana-java-sensor-1.2.14.jar?cb=$(date +%s)"

# VERIFICATION: If the file is smaller than 10MB, the download failed (it's likely an error page)
RUN if [ $(stat -c%s "instana-agent.jar") -lt 10000000 ]; then echo "Download failed or file corrupted"; exit 1; fi

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the verified agent and the app
COPY --from=build /app/instana-agent.jar /app/instana-agent.jar
COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar

EXPOSE 8080

# Use the verified agent
ENV JAVA_TOOL_OPTIONS="-javaagent:/app/instana-agent.jar"

CMD ["java", "-jar", "app.jar"]
