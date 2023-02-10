FROM amazoncorretto:11
ARG JAR_FILE=target/stats-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} stats-server.jar
ENTRYPOINT ["java","-jar","stats-server.jar"]