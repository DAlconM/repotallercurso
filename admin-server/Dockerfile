FROM openjdk:latest

WORKDIR /admin-server
COPY ./target/adminserver-0.0.1-SNAPSHOT.jar /admin-server

EXPOSE 8081

CMD ["java", "-jar", "adminserver-0.0.1-SNAPSHOT.jar"]