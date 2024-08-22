FROM maven:3.9.4-eclipse-temurin-17-alpine AS build

WORKDIR /app
COPY . .
RUN mvn package

FROM tomcat:10.1.25-jdk17
WORKDIR /usr/local/tomcat
COPY --from=build /app/target/Weather-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/Weather.war