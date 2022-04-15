## Build jar
FROM maven:3.8.2-openjdk-17-slim as build

WORKDIR /app
COPY . .
RUN mvn package -DskipTests


## Run
FROM openjdk:17-jdk-alpine as run

ENV JAR_NAME="backend-0.0.1-SNAPSHOT.jar"

WORKDIR /opt/app
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

CMD java -jar ${JAR_NAME}

