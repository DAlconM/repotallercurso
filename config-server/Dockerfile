FROM openjdk:latest

WORKDIR /config-server
COPY ./target/server-config-0.0.1-SNAPSHOT.jar /config-server

EXPOSE 8888

CMD ["java", "-jar", "server-config-0.0.1-SNAPSHOT.jar"]