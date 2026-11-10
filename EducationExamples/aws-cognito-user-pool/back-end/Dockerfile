FROM amazoncorretto:21-alpine-jdk
LABEL authors="maksymdobrynin"

WORKDIR /app
COPY build/libs/back-end-*.jar /app/app.jar
EXPOSE 8080
EXPOSE 5005

CMD ["java", "-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n", "-jar", "app.jar"]
