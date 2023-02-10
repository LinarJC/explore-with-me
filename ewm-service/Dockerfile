FROM amazoncorretto:11
ARG JAR_FILE=target/ewm-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ewm-service.jar
ENTRYPOINT ["java","-jar","ewm-service.jar"]