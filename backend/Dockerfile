# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Download Instana Agent - Verified URL with version v1.2.14
ADD https://github.com/instana/instana-java-sensor/releases/download/v1.2.14/instana-java-sensor-1.2.14.jar /app/instana-agent.jar

COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar
EXPOSE 8080

# Attach the agent automatically
ENV JAVA_TOOL_OPTIONS="-javaagent:/app/instana-agent.jar"

CMD ["java", "-jar", "app.jar"]

