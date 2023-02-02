FROM maven:3.8.7-amazoncorretto-19 AS build
WORKDIR /app
COPY . .
RUN mvn clean package


FROM openjdk:19 AS runtime
WORKDIR /app
ARG JAR_FILE=/app/target/*.jar
COPY --from=build ${JAR_FILE} /app/app.jar
ENTRYPOINT [ "java","-jar","app.jar" ]
