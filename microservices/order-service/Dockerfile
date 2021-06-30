FROM openjdk:14

EXPOSE 8080

ADD ./build/libs/order-service-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
