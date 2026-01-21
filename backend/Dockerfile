# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# We now COPY the file from your repository instead of downloading it
COPY instana-agent.jar /app/instana-agent.jar

COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar
EXPOSE 8080

# Attach the agent
ENV JAVA_TOOL_OPTIONS="-javaagent:/app/instana-agent.jar"

CMD ["java", "-jar", "app.jar"]
