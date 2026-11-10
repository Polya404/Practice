FROM amazoncorretto:21

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY build/libs/kubernetes-demo-*.jar app.jar

# Expose the port the app runs on
EXPOSE 80

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
