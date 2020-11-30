FROM openjdk:latest

WORKDIR /eureka-server
COPY ./target/eureka.server-0.0.1-SNAPSHOT.jar /eureka-server

EXPOSE 8761

CMD ["java", "-jar", "eureka.server-0.0.1-SNAPSHOT.jar"]