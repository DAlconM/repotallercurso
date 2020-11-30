FROM openjdk:latest

WORKDIR /factura-service
COPY ./target/factura-service-0.0.1-SNAPSHOT.jar /factura-service

EXPOSE 8091

CMD ["java", "-jar", "factura-service-0.0.1-SNAPSHOT.jar"]