FROM tomcat:10.1.25-jdk17

WORKDIR /usr/local/tomcat

COPY target/Weather-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/Weather.war

EXPOSE 8080