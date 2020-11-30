FROM openjdk:latest

WORKDIR /hystrix-server
COPY ./target/hystrix-server-0.0.1-SNAPSHOT.jar /hystrix-server

EXPOSE 8082

CMD ["java", "-jar", "hystrix-server-0.0.1-SNAPSHOT.jar"]