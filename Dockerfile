# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine as build

# Set the working directory in the container
WORKDIR /workspace/app

# Copy the Gradle wrapper files (gradlew and gradle-wrapper.properties)
COPY gradlew .

COPY gradle/wrapper/* gradle/wrapper/

# Copy the Gradle build files (build.gradle and settings.gradle)
COPY build.gradle .
COPY settings.gradle .

# Copy the entire project (excluding files listed in .dockerignore) into the container
COPY . .

# Set gradlew executeable
RUN chmod +x gradlew
# Run the Gradle build using the gradlew script
RUN ./gradlew build -x test

# Create a directory for the application's JAR file
RUN mkdir -p build/dependency

# Copy the application JAR and its dependencies to the build/dependency directory
RUN (cd build/libs && cp *.jar ../dependency)

# Second stage of the build
FROM eclipse-temurin:17-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR and its dependencies from the build stage
COPY --from=build /workspace/app/build/dependency/* ./

# Specify the command to run your application (update this with your main class)
ENTRYPOINT ["java", "-jar", "_2048-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080/tcp
EXPOSE 8080/udp
EXPOSE 8009/tcp
EXPOSE 8009/udp
