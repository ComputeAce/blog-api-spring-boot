# Stage 1: Build
FROM maven:3.8.6-openjdk-17 AS build

COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /app/target/blank.jar /app/blank.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/blank.jar"]
