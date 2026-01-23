FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
RUN mvn dependency:get -DrepoUrl=https://artifact-public.instana.io/artifactory/rel-oss-repo -Dartifact=com.instana:instana-java-sensor:1.2.14:jar -Ddest=instana-agent.jar
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/instana-agent.jar /app/instana-agent.jar
COPY --from=build /app/target/instana-bank-mongo-1.0.0.jar app.jar
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-javaagent:/app/instana-agent.jar"
CMD ["java", "-jar", "app.jar"]
