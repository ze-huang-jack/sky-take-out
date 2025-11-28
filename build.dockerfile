FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/sky-takeout.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

<<<<<<< Updated upstream


# 根目录 执行
# docker build -t sky-takeout:latest
=======
# docker-compose up -d
>>>>>>> Stashed changes
