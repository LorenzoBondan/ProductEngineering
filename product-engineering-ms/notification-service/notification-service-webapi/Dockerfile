FROM openjdk:17
VOLUME /tmp
ADD ./target/notification-service-webapi-0.0.1-SNAPSHOT.jar notification-service-webapi.jar
ENTRYPOINT ["java","-jar","/notification-service-webapi.jar"]