FROM openjdk:17
VOLUME /tmp
ADD ./target/mdp-service-webapi-0.0.1-SNAPSHOT.jar mdp-service-webapi.jar
ENTRYPOINT ["java","-jar","/mdp-service-webapi.jar"]