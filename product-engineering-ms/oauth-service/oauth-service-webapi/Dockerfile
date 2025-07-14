FROM openjdk:17
VOLUME /tmp
ADD ./target/oauth-service-webapi-0.0.1-SNAPSHOT.jar oauth-service-webapi.jar
ENTRYPOINT ["java","-jar","/oauth-service-webapi.jar"]