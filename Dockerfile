# Step 1: Build using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Runtime stage with GUI libs for JavaFX
FROM eclipse-temurin:17-jre
WORKDIR /app

RUN apt-get update && apt-get install -y \
    xvfb \
    libgl1 \
    libglx-mesa0 \
    libgtk-3-0 \
    libxtst6 \
    libxrender1 \
    libasound2t64 \
    && rm -rf /var/lib/apt/lists/*

# Specifically copy the shaded executable jar
COPY --from=build /app/target/*shaded.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
