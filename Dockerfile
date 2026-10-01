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
    netcat-openbsd \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/final-skill-verse-*.jar /app/app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "while true; do { echo -e 'HTTP/1.1 200 OK\r\nContent-Length: 2\r\n\r\nOK'; } | nc -l -p 8080 -q 1; done & Xvfb :99 -screen 0 1024x768x16 & export DISPLAY=:99 && java -cp /app/app.jar com.skillverse.AppLauncher"]
