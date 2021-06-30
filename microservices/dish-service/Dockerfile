FROM openjdk:14

EXPOSE 8080

ADD ./build/libs/dish-service-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
