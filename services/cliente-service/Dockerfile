FROM openjdk:latest

WORKDIR /cliente-service
COPY ./target/cliente-service-0.0.1-SNAPSHOT.jar /cliente-service

EXPOSE 8090

CMD ["java", "-jar", "cliente-service-0.0.1-SNAPSHOT.jar"]