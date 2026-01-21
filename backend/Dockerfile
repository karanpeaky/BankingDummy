# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
ADD https://github.com/instana/instana-java-sensor/releases/latest/download/instana-java-sensor.jar /app/instana-agent.jar
COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-javaagent:/app/instana-agent.jar"
CMD ["java", "-jar", "app.jar"]

