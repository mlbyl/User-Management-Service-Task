# Stage 1: build
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
COPY src ./src
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# Stage 2: runtime
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=builder /app/build/libs/User-Management-Service-Task-0.0.1-SNAPSHOT.jar User-Management-Service-Task.jar
CMD ["java", "-jar", "User-Management-Service-Task.jar"]