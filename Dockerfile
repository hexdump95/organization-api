FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

COPY ./target/*.jar ./app.jar

LABEL maintainer="sergiovillanueva@protonmail.com"

CMD java $JAVA_OPTS -jar -Dserver.port=$PORT -Dspring.profiles.active=prod app.jar
