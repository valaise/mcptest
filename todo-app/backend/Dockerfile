FROM maven:3.9.5-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy the POM file
COPY pom.xml .

# Download all required dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Create the final image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/target/*.jar app.jar

# Set the entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the port
EXPOSE 8080 