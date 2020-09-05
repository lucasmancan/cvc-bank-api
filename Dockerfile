FROM maven:3.6.3-openjdk-11 AS builder

COPY . .

RUN mvn package -Dmaven.test.skip=true

COPY /target/*.jar /app.jar

# Ports exposing
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]