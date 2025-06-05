FROM openjdk:16-slim AS builder
WORKDIR /app

# Instalacja gradle ręcznie
RUN apt-get update && apt-get install -y wget unzip \
 && wget https://services.gradle.org/distributions/gradle-8.5-bin.zip -P /tmp \
 && unzip -d /opt/gradle /tmp/gradle-8.5-bin.zip \
 && ln -s /opt/gradle/gradle-8.5/bin/gradle /usr/bin/gradle

COPY . .
RUN gradle build -x test

FROM openjdk:16-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "server.jar"]
