# Stage 1 - Build Stage
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Gradle wrapper
COPY gradlew .
COPY gradlew.bat .
# gradle/ to /app/gradle
COPY gradle gradle

# Copy Gradle build files
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src src

# Give execute permission to Gradle Wrapper
# Make gradlew executable.
RUN chmod +x gradlew

# Build the application
RUN ./gradlew clean bootJar --no-daemon


# Stage 2 - Runtime Stage
# Application is already built.We don't need compiler.We only need
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar notification-service.jar

EXPOSE 8084

ENTRYPOINT ["java","-jar","notification-service.jar"]