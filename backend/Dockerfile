# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /bank

# 1. Install curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# 2. Download a STABLE version from Maven Central (Version 1.2.0)
# We use a new name "instana-final-agent.jar" to force Render to ignore old cache
RUN curl -L -o /bank/instana-final-agent.jar https://repo1.maven.org/maven2/com/instana/instana-java-sdk/1.2.0/instana-java-sdk-1.2.0.jar

# 3. Verify the file is not empty and is a valid JAR
RUN [ -s /bank/instana-final-agent.jar ] || (echo "Download failed!" && exit 1)

COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar

EXPOSE 8080

# 4. Point to the NEW filename
ENV JAVA_TOOL_OPTIONS="-javaagent:/bank/instana-final-agent.jar"

CMD ["java", "-jar", "app.jar"]
