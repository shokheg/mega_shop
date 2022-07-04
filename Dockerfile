FROM amazoncorretto:11.0.15-alpine3.15

WORKDIR /javaApp

EXPOSE 8888

COPY ./target .

ENTRYPOINT ["java", "-jar", "project-0.0.1-SNAPSHOT.jar"]