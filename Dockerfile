# Build stage
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .

# Grant execution permission to gradlew for Linux environments
RUN chmod +x gradlew

# Build with --no-daemon to save memory during build
RUN ./gradlew clean build -x test --no-daemon

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/board-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

# Optimize JVM memory for 512MB RAM environment
ENTRYPOINT ["java", "-Xmx384m", "-Xms384m", "-jar", "app.jar"]
