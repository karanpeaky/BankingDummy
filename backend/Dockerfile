# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
# Using a different workdir to bypass corrupted cache layers
WORKDIR /bank_app

# Install curl to download the agent
RUN apt-get update && apt-get install -y curl

# Download to a NEW filename "instana-sensor-fresh.jar"
# This forces Render to ignore the old, broken "instana-agent.jar" cache
RUN curl -Lk -o instana-sensor-fresh.jar "https://artifact-public.instana.io/artifactory/rel-oss-repo/com/instana/instana-java-sensor/1.2.14/instana-java-sensor-1.2.14.jar"

# VALIDATION: Check if the file is actually a JAR (should be ~19MB)
RUN if [ $(stat -c%s "instana-sensor-fresh.jar") -lt 10000000 ]; then echo "DOWNLOAD FAILED"; exit 1; fi

COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar

EXPOSE 8080

# Update the path to match the NEW filename
ENV JAVA_TOOL_OPTIONS="-javaagent:/bank_app/instana-sensor-fresh.jar"

CMD ["java", "-jar", "app.jar"]
