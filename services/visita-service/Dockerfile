FROM openjdk:latest

WORKDIR /visita-service
COPY ./target/visita-service-0.0.1-SNAPSHOT.jar /visita-service

EXPOSE 8093

CMD ["java", "-jar", "visita-service-0.0.1-SNAPSHOT.jar"]