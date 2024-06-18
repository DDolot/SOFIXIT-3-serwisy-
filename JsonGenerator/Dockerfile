FROM maven:3.8.4-openjdk-17 AS buildstage
WORKDIR /app
COPY . /app
RUN mvn clean install

FROM openjdk:17-jdk-alpine
COPY --from=buildstage /app/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","app.jar"]





