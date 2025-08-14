#Step 1: use maven for building
FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app

COPY ./notification ./notification
COPY ./commonservice ./commonservice

WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

WORKDIR /app/notification
RUN mvn clean package -DskipTests

#Step 2: use openjdk for running
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/notification/target/*.jar notification.jar
EXPOSE 9003
ENTRYPOINT ["java","-jar","notification.jar"]