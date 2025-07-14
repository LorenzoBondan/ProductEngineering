FROM openjdk:17
VOLUME /tmp
ADD ./target/user-service-webapi-0.0.1-SNAPSHOT.jar user-service-webapi.jar
ENTRYPOINT ["java","-jar","/user-service-webapi.jar"]