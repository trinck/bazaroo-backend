FROM openjdk:17-oracle
LABEL authors="trinck"
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
