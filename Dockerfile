FROM openjdk:21-jdk-slim

COPY target/OnlineMarketPlace-0.0.1-SNAPSHOT.jar /usr/app/OnlineMarketPlace.jar

WORKDIR /usr/app


ENTRYPOINT ["java", "-jar", "OnlineMarketPlace.jar"]

