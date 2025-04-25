# Stage 1: Build
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/blank-0.0.1-SNAPSHOT.jar /app/blank.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/blank.jar"]
