FROM openjdk:17-alpine
MAINTAINER mrozycki
COPY target/ARFormatter-1.0-SNAPSHOT.jar ARFormatter-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ARFormatter-1.0-SNAPSHOT.jar"]
EXPOSE 8081