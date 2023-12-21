FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Groceries-0.0.1-SNAPSHOT.jar Groceries.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","Groceries.jar"]