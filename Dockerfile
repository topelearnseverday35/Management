FROM eclipse-temurin:17-alpine
RUN mkdir /opt/app
COPY /target/lms.jar /opt/app
ENTRYPOINT ["java", "-jar", "/opt/app/lms.jar"]