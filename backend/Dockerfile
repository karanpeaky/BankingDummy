# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Download the REAL Java Sensor directly from Instana's Public Repository
# We use curl with -L to follow redirects and -k to skip SSL issues if they occur
RUN apt-get update && apt-get install -y curl && \
    curl -Lk -o instana-agent.jar "https://artifact-public.instana.io/artifactory/rel-oss-repo/com/instana/instana-java-sensor/1.2.14/instana-java-sensor-1.2.14.jar"

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy agent and app
COPY --from=build /app/instana-agent.jar /app/instana-agent.jar
COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar

EXPOSE 8080

# Attach the agent properly
ENV JAVA_TOOL_OPTIONS="-javaagent:/app/instana-agent.jar"

CMD ["java", "-jar", "app.jar"]
