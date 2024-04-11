FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV DB_PORT 5432
ENV DB_NAME dbname
ENV DB_USER username
ENV DB_PASSWORD pass
ENV TOKEN token
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.datasource.url=jdbc:postgresql://postgres:${DB_PORT}/${DB_NAME}", "-Dspring.datasource.username=${DB_USER}", "-Dspring.datasource.password=${DB_PASSWORD}", "-Dtelegram.bot.token=${TOKEN}", "-jar","/app.jar"]