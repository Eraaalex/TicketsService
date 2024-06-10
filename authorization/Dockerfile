FROM amazoncorretto:21-alpine3.19
WORKDIR /app
ADD build/libs/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]
