FROM openjdk:17
VOLUME /tmp
ADD ./target/item-service-webapi-0.0.1-SNAPSHOT.jar item-service-webapi.jar
ENTRYPOINT ["java","-jar","/item-service-webapi.jar"]