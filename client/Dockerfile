#Step 1: use maven for building
FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app

COPY ./client ./client
COPY ./commonservice ./commonservice

# build commonservice first
WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

WORKDIR /app/client
RUN mvn clean package -DskipTests && \
    cp target/*SNAPSHOT.jar target/app.jar


#Step 2: use openjdk for running
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/client/target/app.jar app.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","app.jar"]
