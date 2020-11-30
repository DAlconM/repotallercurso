FROM openjdk:latest

WORKDIR /turbine-server
COPY ./target/turbine-server-0.0.1-SNAPSHOT.jar /turbine-server

EXPOSE 8083

CMD ["java", "-jar", "turbine-server-0.0.1-SNAPSHOT.jar"]