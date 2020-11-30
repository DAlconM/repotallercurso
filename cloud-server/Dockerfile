FROM openjdk:latest

WORKDIR /cloud-server
COPY ./target/cloud-server-0.0.1-SNAPSHOT.jar /cloud-server

EXPOSE 8080

CMD ["java", "-jar", "cloud-server-0.0.1-SNAPSHOT.jar"]