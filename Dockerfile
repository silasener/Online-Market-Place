FROM openjdk:21-jdk-slim

COPY target/OnlineMarketPlace-0.0.1-SNAPSHOT.jar /usr/app/OnlineMarketPlace.jar

WORKDIR /usr/app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "OnlineMarketPlace.jar"]

