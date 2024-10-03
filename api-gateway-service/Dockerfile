FROM openjdk:17-jdk-alpine
ARG JAR_FILE=/build/libs/*-bootjar.jar
COPY /build/libs/*-bootjar.jar /app/app.jar
EXPOSE 8094
ENTRYPOINT ["java", "-jar", "/app/app.jar"]