FROM openjdk:17
VOLUME /tmp
ADD ./target/lixeira-service-webapi-0.0.1-SNAPSHOT.jar lixeira-service-webapi.jar
ENTRYPOINT ["java","-jar","/lixeira-service-webapi.jar"]