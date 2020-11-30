FROM openjdk:latest

WORKDIR /pago-service
COPY ./target/pago-service-0.0.1-SNAPSHOT.jar /pago-service

EXPOSE 8092

CMD ["java", "-jar", "pago-service-0.0.1-SNAPSHOT.jar"]