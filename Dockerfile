# Используем официальный образ OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

# Создаем рабочую директорию
WORKDIR /app

# Указываем аргумент для имени JAR-файла
ARG JAR_FILE=target/api-gateway*.jar

# Копируем JAR-файл в контейнер
COPY ${JAR_FILE} app.jar

# Открываем порт
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]