FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY build/libs/transaction-processing-with-kafka-*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
