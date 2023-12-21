FROM maven:latest AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY ==from=build /target/Groceries-0.0.1-SNAPSHOT.jar Groceries.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","Groceries.jar"]