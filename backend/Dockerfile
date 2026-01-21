# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /bank

# Install wget to download the agent
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 1. Use a NEW filename to bypass Render's corrupted cache
# 2. Use a verified direct link
RUN wget -q https://github.com/instana/instana-java-sensor/releases/download/v1.2.14/instana-java-sensor-1.2.14.jar -O /bank/instana-fixed-agent.jar

# 3. VERIFICATION: Ensure the file is a valid JAR (Zip) by checking the first two bytes are 'PK'
# If this fails, the build stops here instead of crashing at startup
RUN head -c 2 /bank/instana-fixed-agent.jar | grep 'PK' || (echo "ERROR: Downloaded file is not a valid JAR!" && exit 1)

COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar

EXPOSE 8080

# Update the path to the NEW filename
ENV JAVA_TOOL_OPTIONS="-javaagent:/bank/instana-fixed-agent.jar"

CMD ["java", "-jar", "app.jar"]
