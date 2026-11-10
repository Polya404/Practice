# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the project JAR file into the container at /app
COPY /build/libs/snowflake-id-generator-0.0.1-SNAPSHOT.jar /app/snowflake-id-generator.jar

# Run the JAR file
ENTRYPOINT ["java","-jar","/app/snowflake-id-generator.jar"]
