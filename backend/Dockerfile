# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# The line below forces Docker to ignore the cache for the download step
ARG CACHEBUST=1

RUN apt-get update && apt-get install -y curl

# Verified binary link - v1.2.14
RUN curl -L -o instana-agent.jar https://github.com/instana/instana-java-sensor/releases/download/v1.2.14/instana-java-sensor-1.2.14.jar

# Verify the file size in logs (should be ~19MB, not bytes)
RUN ls -lh instana-agent.jar

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/instana-agent.jar /app/instana-agent.jar
COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar

EXPOSE 8080

# Attach the agent
ENV JAVA_TOOL_OPTIONS="-javaagent:/app/instana-agent.jar"

CMD ["java", "-jar", "app.jar"]
