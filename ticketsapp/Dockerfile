# Используем базовый образ Amazon Corretto 21 с Alpine Linux
FROM amazoncorretto:21-alpine3.19

# Указываем рабочую директорию в контейнере
WORKDIR /app

# Копируем файл jar в контейнер
ADD build/libs/*.jar /app/app.jar

# Указываем команду для запуска Spring Boot приложения
CMD ["java", "-jar", "app.jar"]
