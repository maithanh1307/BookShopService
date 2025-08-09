#use maven for building
FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app
COPY ./server ./server
WORKDIR /app/server
RUN mvn clean package -DskipTests

# use openjdk for running
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/server/target/*.jar server.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","server.jar"]