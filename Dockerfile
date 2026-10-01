# Step 1: Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Runtime stage
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

# Copy the built jar
COPY --from=build /app/target/final-skill-verse-*.jar /app/app.jar

ENV PORT=8080
EXPOSE 8080

# Run with virtual display (Xvfb) and AppLauncher main class
ENTRYPOINT ["sh", "-c", "Xvfb :99 -screen 0 1024x768x16 & export DISPLAY=:99 && java -cp /app/app.jar com.skillverse.AppLauncher"]
