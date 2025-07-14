FROM openjdk:17
VOLUME /tmp
ADD ./target/config-service-0.0.1-SNAPSHOT.jar config-service.jar
ENTRYPOINT ["java","-jar","/config-service.jar"]